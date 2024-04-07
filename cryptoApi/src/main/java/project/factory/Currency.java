package project.factory;

public enum Currency {
    BITCOIN("₿"),
    ETHEREUM("ETH"),
    DOGECOIN("Ð"),
    TETHER("₮");

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
