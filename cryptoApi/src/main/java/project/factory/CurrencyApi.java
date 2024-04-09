package project.factory;

import project.factory.enums.Currency;

import java.util.Set;

public interface CurrencyApi {

    String getName();
    Set<Currency> getCurrencies();
}
