package mariapiabaldoin.Giorno_2_19.controllers;


import mariapiabaldoin.Giorno_2_19.entities.Prenotazione;
import mariapiabaldoin.Giorno_2_19.exceptions.BadRequestException;
import mariapiabaldoin.Giorno_2_19.payloads.NewNoteDTO;
import mariapiabaldoin.Giorno_2_19.payloads.NewPrenotazioneDTO;
import mariapiabaldoin.Giorno_2_19.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;


    @GetMapping
    public Page<Prenotazione> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {

        return this.prenotazioniService.findAll(page, size, sortBy);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione save(@RequestBody @Validated NewPrenotazioneDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.prenotazioniService.save(body);
    }


    @GetMapping("/{prenotazioneId}")
    public Prenotazione findById(@PathVariable long prenotazioneId) {
        return this.prenotazioniService.findById(prenotazioneId);
    }


    @PutMapping("/{prenotazioneId}/note")
    public Prenotazione findByIdAndUpdateNote(@PathVariable long prenotazioneId, @RequestBody @Validated NewNoteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }

        return this.prenotazioniService.findByIdAndUpdateNote(prenotazioneId, body);
    }


    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long prenotazioneId) {
        this.prenotazioniService.findByIdAndDelete(prenotazioneId);
    }


}
