package mariapiabaldoin.Giorno_2_19.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record NewStatoDTO(
        @NotEmpty(message = "Lo stato è obbligatorio!")
        @Size(min = 2, max = 40, message = "Lo stato deve essere compreso tra 2 e 40 caratteri!")
        String stato) {
}
