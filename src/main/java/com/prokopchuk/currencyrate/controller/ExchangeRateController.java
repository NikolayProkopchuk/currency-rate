package com.prokopchuk.currencyrate.controller;

import com.prokopchuk.currencyrate.exception.ExchangeRateException;
import com.prokopchuk.currencyrate.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/currency/rate")
    public ResponseEntity<String> getExchangeRate() {

        return ResponseEntity.ok(exchangeRateService.getExchangeRate());
    }

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(ExchangeRateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
