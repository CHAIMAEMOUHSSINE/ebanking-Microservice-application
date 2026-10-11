package net.chaimae.ebankbot.controllers;

import net.chaimae.ebankbot.agents.EbanAiAgent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatbotController {
    private EbanAiAgent ebanAiAgent;

    public EbankChatbotController(ChatClient.Builder chatClient, ChatMemory chatMemory) {
        this.ebanAiAgent = new EbanAiAgent(chatClient, chatMemory);
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "query", defaultValue = "Bonjour") String query) {
        return ebanAiAgent.chat(query);
    }
}
