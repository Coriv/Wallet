package com.microservices.wallet.exceptionHandler;

import com.microservices.wallet.exception.NotEnoughFoundsException;
import com.microservices.wallet.exception.WalletNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class exceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> cashWalletNotFoundHandler(WalletNotFoundException e) {
        return new ResponseEntity<>("Wallet with given user ID doest not exist.", NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughFoundsException.class)
    public ResponseEntity<Object> notEnoughFoundHandler(NotEnoughFoundsException e) {
        return new ResponseEntity<>("You don't have enough found to complete this transaction", BAD_REQUEST);
    }
}
