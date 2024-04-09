package currency;

import currency.api.CoinCapApi;
import currency.enums.CurrencyApiEnum;
import currency.enums.CurrencyTypeEnum;
import currency.interfaces.CurrencyApi;
import currency.interfaces.PriceCurrencyApi;
import currency.model.CurrencyRequest;

import java.util.*;
import java.util.stream.Collectors;

public class ApiManager {

    private final Map<CurrencyApiEnum, CurrencyApi> currencyApiMap = new LinkedHashMap<>() {{
        put(CurrencyApiEnum.COIN_CAP, new CoinCapApi());
    }};

    private final List<CurrencyRequest> priceCurrencyRequests = new ArrayList<>() {{
        currencyApiMap.forEach((currencyApiEnum, currencyApi) -> {
            if (currencyApi instanceof PriceCurrencyApi) {
                currencyApi.getCurrencies().forEach(currencyEnum -> {
                    add(new CurrencyRequest(currencyEnum, currencyApiEnum));
                });
            }
        });
    }};

    private final Map<CurrencyTypeEnum, List<CurrencyRequest>> priceCurrencyRequestsMap = new LinkedHashMap<>() {{
       for (CurrencyTypeEnum currencyType: CurrencyTypeEnum.values()){
           put(currencyType, priceCurrencyRequests.stream().filter(cr -> cr.getCurrencyEnum().getCurrencyType() == currencyType).collect(Collectors.toList()));
       }
    }};

    public List<CurrencyRequest> getPriceCurrencyRequests() {
        return priceCurrencyRequests;
    }

    public List<CurrencyRequest> getPriceCurrencyRequestsByCurrencyType(CurrencyTypeEnum currencyTypeEnum){
        return priceCurrencyRequestsMap.get(currencyTypeEnum);
    }
}
