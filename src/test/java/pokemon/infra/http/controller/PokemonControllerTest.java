package pokemon.infra.http.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pokemon.application.dto.PokeOutput;
import pokemon.application.service.PokeService;
import pokemon.infra.http.request.PokemonRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PokemonControllerTest {

    @Mock
    private PokeService pokeService;

    @InjectMocks
    private PokemonController pokemonController;

    @Mock
    private MultipartFile mockFile;

    public PokemonControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdd() throws Exception {
        PokemonRequest request = new PokemonRequest();
        request.setFile(mockFile);

        when(mockFile.getBytes()).thenReturn("mock content".getBytes());

        PokeOutput mockOutput = new PokeOutput("name", "type", "generation");

        when(pokeService.create(any())).thenReturn(mockOutput);

        ResponseEntity<PokeOutput> response = pokemonController.add(request);

        assertEquals(ResponseEntity.ok(mockOutput), response);
        assertEquals(mockOutput, response.getBody());
        verify(pokeService, times(1)).create(any());
    }
}
