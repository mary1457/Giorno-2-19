package mariapiabaldoin.Giorno_2_19.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewViaggioDTO(
        @NotEmpty(message = "La destinazione è obbligatoria!")
        @Size(min = 2, max = 40, message = "La destinazione deve essere compresa tra 2 e 40 caratteri!")
        String destinazione,
        @NotNull(message = "La data è obbligatoria!")
        LocalDate data,
        @NotEmpty(message = "Lo stato è obbligatorio!")
        @Size(min = 2, max = 40, message = "Lo stato deve essere compreso tra 2 e 40 caratteri!")
        String stato) {
}



