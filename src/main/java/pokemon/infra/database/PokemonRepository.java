package pokemon.infra.database;

import org.springframework.data.jpa.repository.JpaRepository;
import pokemon.domain.model.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
}
