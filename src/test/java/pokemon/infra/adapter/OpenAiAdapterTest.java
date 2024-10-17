package pokemon.infra.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pokemon.application.dto.PokeInput;
import pokemon.application.dto.PokeOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OpenAiAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenAiAdapter openAiAdapter;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAnalyzeImageSuccessfulResponse() throws Exception {
        byte[] imageBytes = "fake image content".getBytes();
        when(multipartFile.getBytes()).thenReturn(imageBytes);
        when(multipartFile.getOriginalFilename()).thenReturn("test-image.jpg");

        PokeInput pokeInput = new PokeInput(multipartFile);

        Map<String, Object> mockResponseBody = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        message.put("content", "{\"nome\": \"Pikachu\", \"tipo\": \"Elétrico\", \"geracao\": \"1\"}");
        mockResponseBody.put("choices", List.of(Map.of("message", message)));

        ResponseEntity<Map> mockResponseEntity = new ResponseEntity<>(mockResponseBody, HttpStatus.OK);

        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(mockResponseEntity);

        PokeOutput result = openAiAdapter.analyzeImage(pokeInput);

        assertEquals("Pikachu", result.getName());
        assertEquals("Elétrico", result.getType());
        assertEquals("1", result.getGeneration());
    }

    @Test
    public void testAnalyzeImageRequestException() throws Exception {
        byte[] imageBytes = "fake image content".getBytes();
        when(multipartFile.getBytes()).thenReturn(imageBytes);
        when(multipartFile.getOriginalFilename()).thenReturn("test-image.jpg");

        PokeInput pokeInput = new PokeInput(multipartFile);

        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(Map.class)))
                .thenThrow(new RuntimeException("Erro de rede"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            openAiAdapter.analyzeImage(pokeInput);
        });
        assertTrue(exception.getMessage().contains("Falha ao realizar a requisição"));
    }

    @Test
    public void testParseOpenAiContent_InvalidJson() throws Exception {
        byte[] imageBytes = "fake image content".getBytes();
        when(multipartFile.getBytes()).thenReturn(imageBytes);
        when(multipartFile.getOriginalFilename()).thenReturn("test-image.jpg");

        PokeInput pokeInput = new PokeInput(multipartFile);

        Map<String, Object> mockResponseBody = new HashMap<>();
        Map<String, Object> message = new HashMap<>();
        message.put("content", "invalid-json-response");
        mockResponseBody.put("choices", List.of(Map.of("message", message)));

        ResponseEntity<Map> mockResponseEntity = new ResponseEntity<>(mockResponseBody, HttpStatus.OK);

        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(mockResponseEntity);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            openAiAdapter.analyzeImage(pokeInput);
        });
        assertTrue(exception.getMessage().contains("Erro ao processar a resposta da OpenAI"));
    }
}
