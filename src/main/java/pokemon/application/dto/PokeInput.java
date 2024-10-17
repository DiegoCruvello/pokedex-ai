package pokemon.application.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@Setter
public class PokeInput {
    private byte[] fileContent;
    private String originalFileName;

    public PokeInput(MultipartFile file) throws IOException {
        this.fileContent = file.getBytes();
        this.originalFileName = file.getOriginalFilename();
    }
}
