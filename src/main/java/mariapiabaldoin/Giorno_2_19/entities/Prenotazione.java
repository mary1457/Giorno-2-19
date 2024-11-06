package mariapiabaldoin.Giorno_2_19.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private LocalDate dataRichiesta;
    private String note;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    public Prenotazione(String note, Dipendente dipendente, Viaggio viaggio) {
        this.dataRichiesta = LocalDate.now();
        this.note = note;
        this.dipendente = dipendente;
        this.viaggio = viaggio;
    }
}
