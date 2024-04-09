package currency.interfaces;

import currency.enums.CurrencyEnum;
import currency.enums.TimeIntervalEnum;
import currency.model.CurrencyResponse;

import java.util.List;
import java.util.Set;

public interface HistoryCurrencyApi {

    Set<TimeIntervalEnum> getIntervals();
    List<CurrencyResponse> getHistory(CurrencyEnum currencyEnum, TimeIntervalEnum timeIntervalEnum) throws Exception;
}
