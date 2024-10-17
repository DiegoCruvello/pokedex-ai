package pokemon.infra.http.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import pokemon.application.dto.PokeInput;

import java.io.IOException;

@Getter
@Setter
public class PokemonRequest {

    private MultipartFile file;

    @NotNull(message = "O arquivo é obrigatório.")
    public PokeInput toDto() throws IOException {

        return new PokeInput(file);
    }
}
