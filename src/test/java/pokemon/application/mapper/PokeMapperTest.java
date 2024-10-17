package pokemon.application.mapper;

import org.junit.jupiter.api.Test;
import pokemon.application.dto.PokeOutput;
import pokemon.domain.model.Pokemon;

import static org.junit.jupiter.api.Assertions.*;

public class PokeMapperTest {

    @Test
    public void testToModel() {
        PokeOutput pokeOutput = new PokeOutput("Pikachu", "Elétrico", "1");

        new PokeMapper();
        Pokemon result = PokeMapper.toModel(pokeOutput);

        assertNotNull(result);
        assertEquals("Pikachu", result.getName());
        assertEquals("Elétrico", result.getType());
        assertEquals("1", result.getGeneration());
    }
}
