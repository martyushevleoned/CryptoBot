package project.factory.enums;

public enum Currency {
    BITCOIN(CurrencyType.CRYPTO, "BTC"),
    ETHEREUM(CurrencyType.CRYPTO, "ETH"),
    DOGECOIN(CurrencyType.CRYPTO, "DOGE"),
    TETHER(CurrencyType.CRYPTO, "USDT"),
    BINANCECOIN(CurrencyType.CRYPTO, "BNB");

    private final CurrencyType currencyType;
    private final String name;

    Currency(CurrencyType currencyType, String name) {
        this.currencyType = currencyType;
        this.name = name;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public String getName() {
        return name;
    }
}
