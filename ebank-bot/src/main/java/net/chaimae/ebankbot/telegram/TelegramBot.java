package net.chaimae.ebankbot.telegram;

import jakarta.annotation.PostConstruct;


import net.chaimae.ebankbot.agents.EbankAIAgent;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.token}")
    private String token;

    private final EbankAIAgent agent;

    public TelegramBot(EbankAIAgent agent) {
        this.agent = agent;
    }

    @PostConstruct
    public void registerTelegramBot() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update telegramRequest) {
        if (!telegramRequest.hasMessage()) return;

        Message message = telegramRequest.getMessage();
        Long chatId = message.getChatId();

        try {
            String messageText = message.getText();
            String caption = null;
            List<Media> mediaList = new ArrayList<>();

            if (message.hasPhoto()) {
                caption = message.getCaption() != null
                        ? message.getCaption()
                        : "What do you see in this image?";

                // Telegram envoie plusieurs tailles : la dernière = meilleure résolution
                List<PhotoSize> photos = message.getPhoto();
                PhotoSize best = photos.get(photos.size() - 1);

                org.telegram.telegrambots.meta.api.objects.File tgFile =
                        execute(new GetFile(best.getFileId()));

                try (InputStream in = downloadFileAsStream(tgFile)) {
                    byte[] bytes = in.readAllBytes();
                    mediaList.add(new Media(MimeTypeUtils.IMAGE_JPEG, new ByteArrayResource(bytes)));
                }
            }

            String query = messageText != null ? messageText : caption;
            if (query == null) return; // sticker, audio, etc. : on ignore

            UserMessage userMessage = UserMessage.builder()
                    .text(query)
                    .media(mediaList)
                    .build();

            sendTypingQuestion(chatId);
            String answer = agent.chat(new Prompt(userMessage));
            sendTextMessage(chatId, answer);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                sendTextMessage(chatId, "Désolé, une erreur est survenue. Réessaie dans un instant.");
            } catch (Exception ignored) {
            }
        }
    }

    public void sendTypingQuestion(Long chatId) {
        SendChatAction sendChatAction = new SendChatAction();
        sendChatAction.setChatId(chatId.toString());
        sendChatAction.setAction(ActionType.TYPING);
        try {
            execute(sendChatAction);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "Ebank23Bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
