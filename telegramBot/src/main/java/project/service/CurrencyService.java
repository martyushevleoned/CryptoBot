package project.service;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import project.factory.enums.ApiEnum;
import project.factory.enums.Currency;
import project.factory.CurrencyApi;
import project.factory.enums.CurrencyType;
import project.factory.price.PriceCurrencyApi;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurrencyService {


    public List<TrackedCurrencyDto> getCurrenciesByCurrencyType(CurrencyType currencyType) {

        List<TrackedCurrencyDto> trackedCurrencyDtoList = new ArrayList<>();

        for (ApiEnum api : ApiEnum.values()) {
            Set<Currency> currencies = api.get().getCurrencies().stream().filter(c -> c.getCurrencyType() == currencyType).collect(Collectors.toSet());
            currencies.forEach(c -> trackedCurrencyDtoList.add(new TrackedCurrencyDto(c, api)));
        }

        return trackedCurrencyDtoList;
    }

    public String getInfoByTrackedCurrencyDto(TrackedCurrencyDto trackedCurrencyDto) {
        CurrencyApi currencyApi = trackedCurrencyDto.getApi().get();
        Currency currency = trackedCurrencyDto.getCurrency();

        StringBuilder sb = new StringBuilder();
        try {

            if (currencyApi instanceof PriceCurrencyApi) {

                PriceCurrencyApi priceCurrencyApi = (PriceCurrencyApi) currencyApi;
                float price = (float) priceCurrencyApi.getPrice(trackedCurrencyDto.getCurrency());
                sb.append("1 ").append(currency.getName()).append(" = ").append(price).append("$");
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
