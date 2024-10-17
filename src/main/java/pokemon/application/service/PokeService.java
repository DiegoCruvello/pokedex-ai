package pokemon.application.service;

import org.springframework.stereotype.Service;
import pokemon.application.dto.PokeInput;
import pokemon.application.dto.PokeOutput;
import pokemon.application.mapper.PokeMapper;
import pokemon.domain.adapter.SendImageAdapterInterface;
import pokemon.domain.model.Pokemon;
import pokemon.infra.database.PokemonRepository;

@Service
public class PokeService {

    private final PokemonRepository pokemonRepository;
    private final SendImageAdapterInterface sendImageAdapter;

    public PokeService(PokemonRepository pokemonRepository, SendImageAdapterInterface sendImageAdapter) {
        this.pokemonRepository = pokemonRepository;
        this.sendImageAdapter = sendImageAdapter;
    }

    public PokeOutput create(PokeInput dto){

        PokeOutput output = this.sendImageAdapter.analyzeImage(dto);
        Pokemon map = PokeMapper.toModel(output);
        pokemonRepository.save(map);

        return output;
    }
}
