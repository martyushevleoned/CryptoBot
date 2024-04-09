package currency.enums;

public enum CurrencyTypeEnum {
    FIAT("фиатные валюты"),
    CRYPTO("криптовалюты"),
    METALS("ценные металлы");

    private final String name;

    CurrencyTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}