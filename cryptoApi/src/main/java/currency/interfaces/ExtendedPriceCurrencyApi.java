package currency.interfaces;

import currency.enums.CurrencyEnum;

public interface ExtendedPriceCurrencyApi extends PriceCurrencyApi {

    String getExtraInfo(CurrencyEnum currencyEnum) throws Exception;
}
