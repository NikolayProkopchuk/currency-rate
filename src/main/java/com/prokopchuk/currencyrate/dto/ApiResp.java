package com.prokopchuk.currencyrate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiResp(@JsonProperty("Realtime Currency Exchange Rate") ExchangeRateDto exchangeRateDto) {
}
