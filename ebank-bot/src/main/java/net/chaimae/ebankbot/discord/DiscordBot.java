package net.chaimae.ebankbot.discord;
import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.chaimae.ebankbot.agents.EbankAIAgent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import org.springframework.ai.chat.prompt.Prompt;

@DiscordController
public class DiscordBot {
    private EbankAIAgent ebankIAgent;

    public DiscordBot(EbankAIAgent ebankAIAgent) {
        this.ebankIAgent = ebankAIAgent;
    }

    @DiscordMapping
    public void perform(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;            // éviter de traiter ses propres messages
        String query = event.getMessage().getContentRaw();
        String response = ebankIAgent.chat(new Prompt(query));
        event.getChannel().sendMessage(response).queue();
    }



}