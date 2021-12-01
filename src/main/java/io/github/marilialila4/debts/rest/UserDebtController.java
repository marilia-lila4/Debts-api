package io.github.marilialila4.debts.rest;

import io.github.marilialila4.debts.exception.RegisteredUserException;
import io.github.marilialila4.debts.model.entity.UserDebt;
import io.github.marilialila4.debts.service.UserDebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserDebtController {

    private final UserDebtService userDebtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid UserDebt userDebt){
        try {
            userDebtService.save(userDebt);
        } catch (RegisteredUserException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
