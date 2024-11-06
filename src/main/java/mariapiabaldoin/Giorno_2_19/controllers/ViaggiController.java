package mariapiabaldoin.Giorno_2_19.controllers;


import mariapiabaldoin.Giorno_2_19.entities.Viaggio;
import mariapiabaldoin.Giorno_2_19.exceptions.BadRequestException;
import mariapiabaldoin.Giorno_2_19.payloads.NewStatoDTO;
import mariapiabaldoin.Giorno_2_19.payloads.NewViaggioDTO;
import mariapiabaldoin.Giorno_2_19.services.ViaggiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/viaggi")
public class ViaggiController {
    @Autowired
    private ViaggiService viaggiService;


    @GetMapping
    public Page<Viaggio> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {

        return this.viaggiService.findAll(page, size, sortBy);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio save(@RequestBody @Validated NewViaggioDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.viaggiService.save(body);
    }


    @GetMapping("/{viaggioId}")
    public Viaggio findById(@PathVariable UUID viaggioId) {
        return this.viaggiService.findById(viaggioId);
    }


    @PutMapping("/{viaggioId}")
    public Viaggio findByIdAndUpdate(@PathVariable UUID viaggioId, @RequestBody @Validated NewViaggioDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }

        return this.viaggiService.findByIdAndUpdate(viaggioId, body);
    }


    @PutMapping("/{viaggioId}/stato")
    public Viaggio findByIdAndUpdateStato(@PathVariable UUID viaggioId, @RequestBody @Validated NewStatoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }

        return this.viaggiService.findByIdAndUpdateStato(viaggioId, body);
    }


    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID viaggioId) {
        this.viaggiService.findByIdAndDelete(viaggioId);
    }


}
