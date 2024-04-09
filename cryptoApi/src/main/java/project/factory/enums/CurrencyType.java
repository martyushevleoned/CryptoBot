package project.factory.enums;

public enum CurrencyType {
    FIAT("фиатные валюты"),
    CRYPTO("криптовалюты"),
    METALS("металлы");

    private final String type;

    CurrencyType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
