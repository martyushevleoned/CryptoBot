package currency.enums;

public enum CurrencyApiEnum {
    COIN_CAP("Coin cap");

    private final String name;

    CurrencyApiEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
