package io.github.marilialila4.debts.rest;

import io.github.marilialila4.debts.model.entity.Debt;
import io.github.marilialila4.debts.model.repository.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/debts")
public class DebtController {

    private final DebtRepository repository;

    @Autowired
    public DebtController(DebtRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Debt> getAllDebt(){
        return  repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Debt saveDebt(@RequestBody @Valid Debt debt) {
        return repository.save(debt);
    }

    @GetMapping("{id}")
    public Debt findByIdDebt(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dívida não encontrada!"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDebt(@PathVariable Integer id){
        repository
            .findById(id)
            .map( debt -> {
                repository.delete(debt);
                return Void.TYPE;
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dívida não encontrada!"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uploadDebt(@PathVariable Integer id, @RequestBody @Valid Debt debtUpload) {
        repository
            .findById(id)
            .map( debt -> {
                debtUpload.setId(debt.getId());
                return repository.save(debtUpload);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dívida não encontrada!"));
    }
}
