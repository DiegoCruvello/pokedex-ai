package pokemon.application.mapper;

import pokemon.application.dto.PokeOutput;
import pokemon.domain.model.Pokemon;

public class PokeMapper {

    public static Pokemon toModel(PokeOutput pokeOutput) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokeOutput.getName());
        pokemon.setType(pokeOutput.getType());
        pokemon.setGeneration(pokeOutput.getGeneration());

        return pokemon;
    }
}
