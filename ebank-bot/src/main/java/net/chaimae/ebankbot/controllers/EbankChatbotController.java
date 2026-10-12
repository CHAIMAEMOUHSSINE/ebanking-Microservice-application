package net.chaimae.ebankbot.controllers;

import net.chaimae.ebankbot.agents.EbankAIAgent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatbotController {
    private EbankAIAgent ebankAIAgent;



    public EbankChatbotController(ChatClient.Builder chatClient, ChatMemory chatMemory, org.springframework.ai.tool.ToolCallbackProvider tools) {
        this.ebankAIAgent = new EbankAIAgent(chatClient, chatMemory , tools);

    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query,
            @RequestParam(name = "conversationId", defaultValue = "default-conversation") String conversationId
    ) {
        return ebankAIAgent.chat(query, conversationId);
    }
}
