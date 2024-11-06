package mariapiabaldoin.Giorno_2_19.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;


public record NewPrenotazioneDTO(
        @NotEmpty(message = "Le note sono obbligatorie!")
        @Size(min = 2, max = 40, message = "Le note devono essere comprese tra 2 e 40 caratteri!")
        String note,
        @NotNull(message = "Il dipendente è obbligatorio!")
        UUID dipendenteId,
        @NotNull(message = "Il viaggio è obbligatorio!")
        UUID viaggioId) {
}



