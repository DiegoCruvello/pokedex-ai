package pokemon.domain.adapter;

import pokemon.application.dto.PokeInput;
import pokemon.application.dto.PokeOutput;

public interface SendImageAdapterInterface {
    PokeOutput analyzeImage(PokeInput dto);
}