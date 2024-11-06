package mariapiabaldoin.Giorno_2_19.services;


import mariapiabaldoin.Giorno_2_19.entities.Viaggio;
import mariapiabaldoin.Giorno_2_19.exceptions.BadRequestException;
import mariapiabaldoin.Giorno_2_19.exceptions.NotFoundException;
import mariapiabaldoin.Giorno_2_19.payloads.NewStatoDTO;
import mariapiabaldoin.Giorno_2_19.payloads.NewViaggioDTO;
import mariapiabaldoin.Giorno_2_19.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ViaggiService {

    @Autowired
    private ViaggiRepository viaggiRepository;


    public Viaggio save(NewViaggioDTO body) {

        if (LocalDate.now().isBefore(body.data())) {
            Viaggio newViaggio = new Viaggio(body.destinazione(), body.data(), body.stato());
            return this.viaggiRepository.save(newViaggio);
        } else {
            throw new BadRequestException("La data " + body.data() + " non può essere antecedente ad oggi");
        }


    }

    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.viaggiRepository.findAll(pageable);
    }

    public Viaggio findById(UUID viaggioId) {
        return this.viaggiRepository.findById(viaggioId).orElseThrow(() -> new NotFoundException(viaggioId));
    }

    public Viaggio findByIdAndUpdate(UUID viaggioId, NewViaggioDTO body) {

        Viaggio found = this.findById(viaggioId);

        if (LocalDate.now().isBefore(body.data())) {
            found.setDestinazione(body.destinazione());
            found.setData(body.data());
            found.setStato(body.stato());

        } else {
            throw new BadRequestException("La data " + body.data() + " non può essere antecedente ad oggi");
        }


        return this.viaggiRepository.save(found);
    }


    public Viaggio findByIdAndUpdateStato(UUID viaggioId, NewStatoDTO body) {

        Viaggio found = this.findById(viaggioId);
        found.setStato(body.stato());


        return this.viaggiRepository.save(found);
    }

    public void findByIdAndDelete(UUID viaggioId) {
        Viaggio found = this.findById(viaggioId);
        this.viaggiRepository.delete(found);
    }


}
