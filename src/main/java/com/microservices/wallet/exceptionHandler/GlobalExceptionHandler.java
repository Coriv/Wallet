package com.microservices.wallet.exceptionHandler;

import com.microservices.wallet.exception.NotEmptyWalletException;
import com.microservices.wallet.exception.NotEnoughFundsException;
import com.microservices.wallet.exception.WalletNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> cashWalletNotFoundHandler(WalletNotFoundException e) {
        return new ResponseEntity<>("Wallet with given user ID doest not exist.", NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    public ResponseEntity<Object> notEnoughFundHandler(NotEnoughFundsException e) {
        return new ResponseEntity<>("You don't have enough found to complete this transaction", BAD_REQUEST);
    }

    @ExceptionHandler(NotEmptyWalletException.class)
    public ResponseEntity<Object> notEmptyWalletExceptionHandler(NotEmptyWalletException e) {
        return new ResponseEntity<>("You can not remove wallet with funds.", BAD_REQUEST);
    }
}
