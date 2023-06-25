package com.microservices.wallet;

import com.microservices.wallet.exception.NotEnoughFoundsException;
import com.microservices.wallet.exception.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class errorHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> cashWalletNotFoundHandler(WalletNotFoundException e) {
        return new ResponseEntity<>("Wallet with given user ID doest not exist.", NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughFoundsException.class)
    public ResponseEntity<Object> notEnoughFoundHandler(NotEnoughFoundsException e) {
        return new ResponseEntity<>("You don't have enough found to complete this transaction", BAD_REQUEST);
    }
}
