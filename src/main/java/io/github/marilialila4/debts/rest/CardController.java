package io.github.marilialila4.debts.rest;

import io.github.marilialila4.debts.model.entity.Card;
import io.github.marilialila4.debts.model.entity.Debt;
import io.github.marilialila4.debts.model.repository.CardRepository;
import io.github.marilialila4.debts.model.repository.DebtRepository;
import io.github.marilialila4.debts.rest.dto.CardDTO;
import io.github.marilialila4.debts.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final DebtRepository debtRepository;
    private final CardRepository cardRepository;
    private final BigDecimalConverter bigDecimalConverter;

    /* - Ou coloca esse código ou adiciona o @RequiredArgsConstructor
    public CardController(DebtRepository debtRepository, CardRepository cardRepository) {
        this.debtRepository = debtRepository;
        this.cardRepository = cardRepository;
    }
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Card save(@RequestBody @Valid CardDTO dto){
        LocalDate date = LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer idDebts = dto.getIdDebt();

        Debt debt =
                debtRepository
                .findById(idDebts)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "Dívida inexistente"));


        Card card = new Card();
        card.setName(dto.getName());
        card.setDescription(dto.getDescription());
        card.setValue(bigDecimalConverter.converter(dto.getValue()));
        card.setInstallments(dto.getInstallments());
        card.setDate(date);
        card.setStatus(dto.getStatus());
        card.setTotal(bigDecimalConverter.converter(dto.getTotal()));
        card.setDebt(debt);

        return cardRepository.save(card);
    }

    @GetMapping
    public List<Card> search(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "month", required = false) Integer month
            ){
        return cardRepository.findByNameAndMonth("%" + name + "%", month);
    }

    @GetMapping("{id}")
    public Card findByIdCard(@PathVariable Integer id){
        return cardRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dívida não encontrada!"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable Integer id){
        cardRepository
                .findById(id)
                .map( card -> {
                    cardRepository.delete(card);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dívida não encontrada!"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uploadCard(@PathVariable Integer id, @RequestBody @Valid Card cardUpload) {
        cardRepository
                .findById(id)
                .map( card -> {
                    cardUpload.setId(card.getId());
                    return cardRepository.save(cardUpload);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dívida não encontrada!"));
    }
}
