package currency.model;

import currency.enums.CurrencyEnum;
import currency.enums.CurrencyApiEnum;

public class CurrencyRequest {

    private final CurrencyEnum currencyEnum;
    private final CurrencyApiEnum currencyApiEnum;

    public CurrencyRequest(CurrencyEnum currencyEnum, CurrencyApiEnum currencyApiEnum) {
        this.currencyEnum = currencyEnum;
        this.currencyApiEnum = currencyApiEnum;
    }

    public CurrencyRequest(String callback) {
        String[] parts = callback.split(" ");
        this.currencyEnum = CurrencyEnum.valueOf(parts[0]);
        this.currencyApiEnum = CurrencyApiEnum.valueOf(parts[1]);
    }

    public CurrencyEnum getCurrencyEnum() {
        return currencyEnum;
    }

    public CurrencyApiEnum getCurrencyApiEnum() {
        return currencyApiEnum;
    }

    public String toCallback() {
        return currencyEnum + " " + currencyApiEnum;
    }
}
