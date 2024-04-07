package project.factory.history;

import project.factory.Currency;
import project.factory.CurrencyApi;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface HistoryCurrencyApi extends CurrencyApi {

    Set<TimeInterval> getIntervals();
    List<HistoryDto> getHistory(Currency currency, TimeInterval timeInterval) throws IOException;
}
