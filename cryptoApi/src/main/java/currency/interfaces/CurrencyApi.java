package currency.interfaces;

import currency.enums.CurrencyEnum;
import java.util.Set;

public interface CurrencyApi {

    Set<CurrencyEnum> getCurrencies();
}
