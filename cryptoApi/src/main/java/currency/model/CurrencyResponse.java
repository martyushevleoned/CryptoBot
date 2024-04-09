package currency.model;

import java.util.Date;

public class CurrencyResponse {

    private final double priceUsd;
    private final Date timestamp;

    public CurrencyResponse(double priceUsd, Date timestamp) {
        this.priceUsd = priceUsd;
        this.timestamp = timestamp;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
