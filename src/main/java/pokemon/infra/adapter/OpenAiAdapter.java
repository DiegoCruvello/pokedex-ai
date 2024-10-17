package pokemon.infra.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import pokemon.application.dto.PokeInput;
import pokemon.application.dto.PokeOutput;
import pokemon.domain.adapter.SendImageAdapterInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiAdapter implements SendImageAdapterInterface {

    @Value("${openia.auth}")
    private String key;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenAiAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public PokeOutput analyzeImage(PokeInput dto) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-4-turbo");

            Map<String, Object> textContent = new HashMap<>();
            textContent.put("type", "text");
            textContent.put("text", """
                    Você é especialista em pokemon, preciso que a partir da imagem retorne o nome dele, geração e tipo.
                    Exemplo de resposta JSON:
                    {
                      "nome": "",
                      "geracao": "",
                      "tipo": ""
                    }
                    """);

            String base64Image = Base64Utils.encodeToString(dto.getFileContent());

            Map<String, Object> imageContent = new HashMap<>();
            imageContent.put("type", "image_url");

            Map<String, String> imageUrl = new HashMap<>();
            imageUrl.put("url", "data:image/jpeg;base64," + base64Image);
            imageContent.put("image_url", imageUrl);

            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", new Object[]{textContent, imageContent});

            body.put("messages", new Object[]{message});
            body.put("max_tokens", 450);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + key);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://api.openai.com/v1/chat/completions",
                    request,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                Map<String, Object> outputMap = (Map<String, Object>) choices.get(0).get("message");
                String content = (String) outputMap.get("content");

                return parseOpenAiContent(content);
            }

            throw new RuntimeException("Resposta inválida da OpenAI");

        } catch (Exception e) {
            throw new RuntimeException("Falha ao realizar a requisição: " + e.getMessage(), e);
        }
    }

    private PokeOutput parseOpenAiContent(String content) {
        try {
            Map<String, String> contentMap = objectMapper.readValue(content, Map.class);

            return new PokeOutput(
                    contentMap.get("nome"),
                    contentMap.get("tipo"),
                    contentMap.get("geracao")
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar a resposta da OpenAI: " + e.getMessage(), e);
        }
    }
}
