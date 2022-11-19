package com.prokopchuk.currencyrate.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ExchangeRateDto(
  @JsonProperty("5. Exchange Rate") BigDecimal exchangeRate,
  @JsonProperty("8. Bid Price") BigDecimal bidPrice,
  @JsonProperty("9. Ask Price") BigDecimal askPrice,
  @JsonProperty("6. Last Refreshed")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime lastRefreshed) {
}
