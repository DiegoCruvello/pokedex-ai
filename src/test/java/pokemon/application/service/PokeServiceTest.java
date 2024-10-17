package pokemon.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pokemon.application.dto.PokeInput;
import pokemon.application.dto.PokeOutput;
import pokemon.application.mapper.PokeMapper;
import pokemon.domain.adapter.SendImageAdapterInterface;
import pokemon.domain.model.Pokemon;
import pokemon.infra.database.PokemonRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PokeServiceTest {

    @Mock
    private SendImageAdapterInterface sendImageAdapter;

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokeService pokeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pokeService = new PokeService(pokemonRepository, sendImageAdapter);
    }

    @Test
    public void testCreate() throws Exception {
        PokeInput pokeInput = mock(PokeInput.class);
        PokeOutput pokeOutput = new PokeOutput("Pikachu", "Elétrico", "1");
        Pokemon pokemon = PokeMapper.toModel(pokeOutput);

        when(sendImageAdapter.analyzeImage(pokeInput)).thenReturn(pokeOutput);
        when(pokemonRepository.save(any(Pokemon.class))).thenReturn(pokemon);


        PokeOutput result = pokeService.create(pokeInput);


        assertEquals(pokeOutput, result);
        verify(sendImageAdapter, times(1)).analyzeImage(pokeInput);
        verify(pokemonRepository, times(1)).save(any(Pokemon.class));
    }
}
