package project;

import org.json.simple.parser.ParseException;
import project.factory.enums.ApiEnum;
import project.factory.enums.Currency;
import project.factory.history.HistoryCurrencyApi;
import project.factory.history.TimeInterval;
import project.factory.price.PriceCurrencyApi;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        PriceCurrencyApi priceCurrencyApi = (PriceCurrencyApi) ApiEnum.COIN_CAP.get();
        HistoryCurrencyApi historyCurrencyApi = (HistoryCurrencyApi) ApiEnum.COIN_CAP.get();

        Currency currency = Currency.DOGECOIN;
        TimeInterval timeInterval = TimeInterval.HOUR;

        System.out.println(priceCurrencyApi.getPrice(currency));
        System.out.println(historyCurrencyApi.getHistory(currency, timeInterval));
    }
}
