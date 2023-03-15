package com.github.wenqiglantz.service.openaiclient.service;

import com.github.wenqiglantz.service.openaiclient.openaiclient.OpenAIClient;
import com.github.wenqiglantz.service.openaiclient.openaiclient.OpenAIClientConfig;
import com.github.wenqiglantz.service.openaiclient.model.request.ChatGPTRequest;
import com.github.wenqiglantz.service.openaiclient.model.request.WhisperTranscriptionRequest;
import com.github.wenqiglantz.service.openaiclient.model.request.TranscriptionRequest;
import com.github.wenqiglantz.service.openaiclient.model.response.ChatGPTResponse;
import com.github.wenqiglantz.service.openaiclient.model.request.ChatRequest;
import com.github.wenqiglantz.service.openaiclient.model.request.Message;
import com.github.wenqiglantz.service.openaiclient.model.response.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OpenAIClientService {

    private final OpenAIClient openAIClient;
    private final OpenAIClientConfig openAIClientConfig;

    private final static String ROLE_USER = "user";

    public ChatGPTResponse chat(ChatRequest chatRequest){
        String finalQuestion=AddClarity(chatRequest.getQuestion());
        Message message = Message.builder()
                .role(ROLE_USER)
                .content(finalQuestion)
                .build();
        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(openAIClientConfig.getModel())
                .messages(Collections.singletonList(message))
                .build();
        return openAIClient.chat(chatGPTRequest);
    }

    public WhisperTranscriptionResponse createTranscription(TranscriptionRequest transcriptionRequest){
        WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest.builder()
                .model(openAIClientConfig.getAudioModel())
                .file(transcriptionRequest.getFile())
                .build();
        return openAIClient.createTranscription(whisperTranscriptionRequest);
    }

    public String AddClarity(String text) {
            return text.toLowerCase()
                    .replaceAll("\s+", " ")
                    .replaceAll(" i-9 ", " employment eligibility verification ")
                    .replaceAll(" i 9 ", " employment eligibility verification ")
                    .replaceAll(" i9 ", " employment eligibility verification ");
    }
}
