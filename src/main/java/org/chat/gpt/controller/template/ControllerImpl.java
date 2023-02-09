package org.chat.gpt.controller.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import j2html.tags.DomContent;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static j2html.TagCreator.*;

@Configuration
@AllArgsConstructor
@RestController
public abstract class ControllerImpl implements Controller{

    private enum AnnotationClassEnum {

        GET {
            @Override
            public String getValueAnnotation(Method method) {
                return Arrays.toString(method.getAnnotation(GetMapping.class).value());
            }
            @Override
            public String getType() {
                return "GET";
            }
        },
        POST {
            @Override
            public String getValueAnnotation(Method method) {
                return Arrays.toString(method.getAnnotation(PostMapping.class).value());
            }
            @Override
            public String getType() {
                return "POST";
            }
        },
        PUT {
            @Override
            public String getValueAnnotation(Method method) {
                return Arrays.toString(method.getAnnotation(PutMapping.class).value());
            }

            @Override
            public String getType() {
                return "PUT";
            }

        },
        DELETE {
            @Override
            public String getValueAnnotation(Method method) {
                return Arrays.toString(method.getAnnotation(DeleteMapping.class).value());
            }

            @Override
            public String getType() {
                return "DELETE";
            }

        };

        public abstract String getValueAnnotation(Method method);
        public abstract String getType();

    }

    private AnnotationClassEnum getAnnotationEnum(Method method) {

        if (!Objects.isNull(method.getAnnotation(GetMapping.class)))
            return AnnotationClassEnum.GET;
        if (!Objects.isNull(method.getAnnotation(PostMapping.class)))
            return AnnotationClassEnum.POST;
        if (!Objects.isNull(method.getAnnotation(PutMapping.class)))
            return AnnotationClassEnum.PUT;
        if (!Objects.isNull(method.getAnnotation(DeleteMapping.class)))
            return AnnotationClassEnum.DELETE;
        return null;
    }


    @GetMapping("/")
    public ResponseEntity<String> getMap() throws RestClientException, JsonProcessingException, ExecutionException {

        String result =
                html(
                head(
                        title("Методы сервиса")
                ),
                body(
                        table(
                                Arrays.stream(this.getClass().getMethods())
                                        .filter(method -> getAnnotationEnum(method) != null)
                                        .map(method -> tr(td(getAnnotationEnum(method).getType()),
                                                td(getAnnotationEnum(method).getValueAnnotation(method))))
                                        .toArray(DomContent[]::new)
                        )

                )).renderFormatted();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
