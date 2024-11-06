package mariapiabaldoin.Giorno_2_19.services;


import mariapiabaldoin.Giorno_2_19.entities.Dipendente;
import mariapiabaldoin.Giorno_2_19.entities.Prenotazione;
import mariapiabaldoin.Giorno_2_19.entities.Viaggio;
import mariapiabaldoin.Giorno_2_19.exceptions.BadRequestException;
import mariapiabaldoin.Giorno_2_19.exceptions.NotFoundException;
import mariapiabaldoin.Giorno_2_19.payloads.NewNoteDTO;
import mariapiabaldoin.Giorno_2_19.payloads.NewPrenotazioneDTO;
import mariapiabaldoin.Giorno_2_19.repositories.DipendentiRepository;
import mariapiabaldoin.Giorno_2_19.repositories.PrenotazioniRepository;
import mariapiabaldoin.Giorno_2_19.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PrenotazioniService {

    @Autowired
    private ViaggiRepository viaggiRepository;

    @Autowired
    private PrenotazioniRepository prenotazioniRepository;

    @Autowired
    private DipendentiRepository dipendentiRepository;


    public Prenotazione save(NewPrenotazioneDTO body) {


        Dipendente dipendente = dipendentiRepository.findById(body.dipendenteId())
                .orElseThrow(() -> new NotFoundException(body.dipendenteId()));
        Viaggio viaggio = viaggiRepository.findById(body.viaggioId())
                .orElseThrow(() -> new NotFoundException(body.viaggioId()));


        boolean esiste = !prenotazioniRepository
                .findByDipendenteIdAndViaggioData(dipendente.getId(), viaggio.getData())
                .isEmpty();

        if (esiste) {
            throw new BadRequestException("Il dipendente ha già una prenotazione per la data " + viaggio.getData());
        }
        Prenotazione newPrenotazione = new Prenotazione(body.note(), dipendente, viaggio);
        return this.prenotazioniRepository.save(newPrenotazione);


    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.prenotazioniRepository.findAll(pageable);
    }

    public Prenotazione findById(long prenotazioneId) {
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public Prenotazione findByIdAndUpdateNote(long prenotazioneId, NewNoteDTO body) {

        Prenotazione found = this.findById(prenotazioneId);
        found.setNote(body.note());


        return this.prenotazioniRepository.save(found);
    }


    public void findByIdAndDelete(long prenotazioneId) {
        Prenotazione found = this.findById(prenotazioneId);
        this.prenotazioniRepository.delete(found);
    }


}
