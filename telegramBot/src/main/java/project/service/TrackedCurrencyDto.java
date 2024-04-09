package project.service;

import project.factory.enums.ApiEnum;
import project.factory.enums.Currency;

import java.util.Objects;

public class TrackedCurrencyDto {

    private final Currency currency;
    private final ApiEnum api;

    public TrackedCurrencyDto(Currency currency, ApiEnum api) {
        this.currency = currency;
        this.api = api;
    }

    public TrackedCurrencyDto(String callBack) throws IllegalArgumentException{
        String[] parts = callBack.split(" ");
        this.currency = Currency.valueOf(parts[1]);
        this.api = ApiEnum.valueOf(parts[0]);
    }

    public String getText() {
        return currency.getName() + " (" + api.get().getName() + ")";
    }

    public String getCallback() {
        return api.toString() + " " + currency.toString();
    }

    public Currency getCurrency() {
        return currency;
    }

    public ApiEnum getApi() {
        return api;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackedCurrencyDto that = (TrackedCurrencyDto) o;
        return currency == that.currency && api == that.api;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, api);
    }
}