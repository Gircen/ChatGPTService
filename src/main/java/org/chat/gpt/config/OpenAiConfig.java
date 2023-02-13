package org.chat.gpt.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
public class OpenAiConfig {

    @Value("${openai.openai.url}")
    private String url;

    @Value("${openai.openai.key}")
    private String key;
}
