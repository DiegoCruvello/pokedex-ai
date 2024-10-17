package pokemon.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokeOutput {
    private String name;
    private String type;
    private String generation;

    public PokeOutput(String name, String type, String generation) {
        this.name = name;
        this.type = type;
        this.generation = generation;
    }
}
