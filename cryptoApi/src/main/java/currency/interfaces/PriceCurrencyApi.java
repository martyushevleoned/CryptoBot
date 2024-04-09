package currency.interfaces;

import currency.enums.CurrencyEnum;

public interface PriceCurrencyApi extends CurrencyApi {

    double getPrice(CurrencyEnum currencyEnum) throws Exception;
}