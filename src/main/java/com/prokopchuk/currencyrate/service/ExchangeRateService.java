package com.prokopchuk.currencyrate.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import com.prokopchuk.currencyrate.dto.ApiResp;
import com.prokopchuk.currencyrate.exception.ExchangeRateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final RestTemplate restTemplate;

    @Value("${exchangeRate.url}")
    private String apiUrl;

    @Value("${exchangeRate.queryParam.toCurrency.name}")
    private String toCurrencyParamName;

    @Value("${exchangeRate.queryParam.toCurrency.value}")
    private String toCurrencyParamValue;

    @Value("${exchangeRate.queryParam.fromCurrency.name}")
    private String fromCurrencyParamName;

    @Value("${exchangeRate.queryParam.fromCurrency.value}")
    private String fromCurrencyParamValue;

    @Value("${exchangeRate.queryParam.function.name}")
    private String functionParamName;

    @Value("${exchangeRate.queryParam.function.value}")
    private String functionParamValue;

    @Value("${exchangeRate.header.apiKey.name}")
    private String apiKeyHeaderName;

    @Value("${exchangeRate.header.apiKey.value}")
    private String apiKeyHeaderValue;

    @Value("${exchangeRate.header.host.name}")
    private String hostHeaderName;

    @Value("${exchangeRate.header.host.value}")
    private String hostHeaderValue;

    public String getExchangeRate() {
        var uri = UriComponentsBuilder
          .fromHttpUrl(apiUrl)
          .queryParam(toCurrencyParamName, toCurrencyParamValue)
          .queryParam(functionParamName, functionParamValue)
          .queryParam(fromCurrencyParamName, fromCurrencyParamValue)
          .build().toUri();

        var headers = new HttpHeaders();
        headers.put(apiKeyHeaderName, List.of(apiKeyHeaderValue));
        headers.put(hostHeaderName, List.of(hostHeaderValue));
        var voidHttpEntity = new HttpEntity<>(headers);

        var responseEntity = restTemplate.exchange(uri, HttpMethod.GET, voidHttpEntity, ApiResp.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ExchangeRateException("Some api error");
        }

        var exchangeRate = Objects.requireNonNull(responseEntity.getBody()).exchangeRateDto();

        return String.format("Текущий курс: %.2f Средний курс: %.2f Дата: %s",
          exchangeRate.exchangeRate(), exchangeRate.askPrice(),
          exchangeRate.lastRefreshed().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
