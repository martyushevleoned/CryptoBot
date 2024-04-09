package project.factory.enums;

import project.api.CoinCapApi;
import project.factory.CurrencyApi;

public enum ApiEnum {
    COIN_CAP(new CoinCapApi());

    private final CurrencyApi currencyApi;

    ApiEnum(CurrencyApi currencyApi) {
        this.currencyApi = currencyApi;
    }

    public CurrencyApi get() {
        return currencyApi;
    }
}
