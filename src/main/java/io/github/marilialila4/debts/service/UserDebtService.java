package io.github.marilialila4.debts.service;

import io.github.marilialila4.debts.exception.RegisteredUserException;
import io.github.marilialila4.debts.model.repository.UserDebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.marilialila4.debts.model.entity.UserDebt;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDebtService implements UserDetailsService {

    @Autowired
    private UserDebtRepository repository;

    public UserDebt save(UserDebt userDebt){
        boolean exists = repository.existsByUsername(userDebt.getUsername());
        if(exists){
            throw new RegisteredUserException(userDebt.getUsername());
        }
        return repository.save(userDebt);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDebt userDebt = repository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Login não encontrado"));
        return User
                .builder()
                .username(userDebt.getUsername())
                .password(userDebt.getPassword())
                .roles("USER")
                .build();
    }
}
