package mariapiabaldoin.Giorno_2_19.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record NewNoteDTO(@NotEmpty(message = "Le note sono obbligatorie!")
                         @Size(min = 2, max = 40, message = "Le note devono essere comprese tra 2 e 40 caratteri!")
                         String note
) {
}
