package project.factory.price;

import org.json.simple.parser.ParseException;
import project.factory.enums.Currency;
import project.factory.CurrencyApi;

import java.io.IOException;

public interface PriceCurrencyApi extends CurrencyApi {

    double getPrice(Currency currency) throws IOException, ParseException;
}
