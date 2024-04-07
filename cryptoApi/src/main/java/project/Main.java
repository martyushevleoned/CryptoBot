package project;

import project.api.coinCapApi.CoinCapApi;
import project.factory.Currency;
import project.factory.history.TimeInterval;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Currency currency = Currency.TETHER;
        TimeInterval interval = TimeInterval.WEEK;
        CoinCapApi currencyApi = new CoinCapApi();

        double price = currencyApi.getCurrentPrice(currency);
        System.out.println(price + " " + currency.getSymbol());

        currencyApi.getHistory(currency, interval).forEach(System.out::println);
    }
}
