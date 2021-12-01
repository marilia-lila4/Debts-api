package io.github.marilialila4.debts.exception;

public class RegisteredUserException extends RuntimeException {

    public RegisteredUserException(String login){
        super("Usuário já cadastrado, não é possível criar outro usuário com o mesmo nome: " + login);
    }
}
