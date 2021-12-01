package io.github.marilialila4.debts.rest;

import io.github.marilialila4.debts.model.entity.Saving;
import io.github.marilialila4.debts.model.repository.SavingRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/savings")
public class SavingController {

    private final SavingRepository repository;

    @Autowired
    public SavingController(SavingRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Saving> getAllSaving(){
        return  repository.findAll();
    }

/*
    @GetMapping
    public List<Saving> getAllSaving(
            @RequestParam(value = "soma", required = false, defaultValue = "") Integer soma
    ){
        return repository.sumTotalSaving(soma);
    }
 */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Saving saveSaving(@RequestBody @Valid Saving saving) {
        return repository.save(saving);
    }

    @GetMapping("{id}")
    public Saving findByIdSaving(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Poupança não encontrada!"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSaving(@PathVariable Integer id){
        repository
                .findById(id)
                .map( saving -> {
                    repository.delete(saving);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Poupança não encontrada!"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uploadSaving(@PathVariable Integer id, @RequestBody @Valid Saving savingUpload) {
        repository
                .findById(id)
                .map( saving -> {
                    savingUpload.setId(saving.getId());
                    return repository.save(savingUpload);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Poupança não encontrada!"));
    }

    @PutMapping("{id}/photo")
    public byte[] addPhoto(@PathVariable Integer id, @RequestParam("photo") Part arquivo){
        Optional<Saving> saving = repository.findById(id);
        return saving.map( c -> {
            try {
                InputStream is = arquivo.getInputStream();
                byte[] bytes = new byte[(int) arquivo.getSize()];
                IOUtils.readFully(is, bytes);
                c.setPhoto(bytes);
                repository.save(c);
                is.close();
                return bytes;
            } catch (IOException e) {
                return  null;
            }
        }).orElse(null);
    }
}
