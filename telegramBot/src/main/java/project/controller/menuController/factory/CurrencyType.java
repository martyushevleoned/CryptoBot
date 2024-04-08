package project.controller.menuController.factory;

public enum CurrencyType {
    CRYPTO("криптовалюты");

    private final String type;

    CurrencyType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
