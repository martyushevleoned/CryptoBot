package currency.enums;

public enum CurrencyEnum {
    BITCOIN(CurrencyTypeEnum.CRYPTO, "BTC"),
    ETHEREUM(CurrencyTypeEnum.CRYPTO, "ETH"),
    DOGECOIN(CurrencyTypeEnum.CRYPTO, "DOGE"),
    TETHER(CurrencyTypeEnum.CRYPTO, "USDT"),
    BINANCECOIN(CurrencyTypeEnum.CRYPTO, "BNB");

    private final CurrencyTypeEnum currencyTypeEnum;
    private final String name;

    CurrencyEnum(CurrencyTypeEnum currencyTypeEnum, String name) {
        this.currencyTypeEnum = currencyTypeEnum;
        this.name = name;
    }

    public CurrencyTypeEnum getCurrencyType() {
        return currencyTypeEnum;
    }

    public String getName() {
        return name;
    }
}