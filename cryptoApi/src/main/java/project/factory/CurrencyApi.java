package project.factory;

import java.util.Set;

public interface CurrencyApi {

    String getApiName();

    String getApiDescription();

    Set<Currency> getCurrencies();
}
