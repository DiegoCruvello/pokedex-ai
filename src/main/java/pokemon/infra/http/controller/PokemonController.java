package pokemon.infra.http.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pokemon.application.dto.PokeOutput;
import pokemon.application.service.PokeService;
import pokemon.infra.http.request.PokemonRequest;

import java.io.IOException;

@RestController
public class PokemonController {

    @Autowired
    private PokeService pokeService;

    @PostMapping("/create")
    public ResponseEntity<PokeOutput> add(@Valid @ModelAttribute PokemonRequest request) throws IOException {
        PokeOutput output = pokeService.create(request.toDto());

        return ResponseEntity.ok(output);
    }
}
