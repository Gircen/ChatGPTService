package org.chat.gpt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpenAiRequest extends AbstractOpenApiDto{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String model;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String prompt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Double temperature;

    @JsonProperty("max_tokens")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Short maxTokens;

    @JsonProperty("top_p")
    Short topP;

    @JsonProperty("frequency_penalty")
    Double frequencyPenalty;

    @JsonProperty("presence_penalty")
    Double presencePenalty;

    Set<String> stop;

}
