package currency.api;


import currency.enums.CurrencyEnum;
import currency.enums.TimeIntervalEnum;
import currency.interfaces.HistoryCurrencyApi;
import currency.interfaces.PriceCurrencyApi;
import currency.model.CurrencyResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;

public class CoinCapApi implements PriceCurrencyApi, HistoryCurrencyApi {

    private final HashMap<CurrencyEnum, String> currencies = new HashMap<>() {{
        put(CurrencyEnum.BITCOIN, "bitcoin");
        put(CurrencyEnum.ETHEREUM, "ethereum");
        put(CurrencyEnum.DOGECOIN, "dogecoin");
        put(CurrencyEnum.TETHER, "tether");
        put(CurrencyEnum.BINANCECOIN, "binance-coin");
    }};

    private final HashMap<TimeIntervalEnum, String> intervals = new HashMap<>() {{  //m1, m5, m15, m30, h1, h2, h6, h12, d1
        put(TimeIntervalEnum.HOUR, "m1");
        put(TimeIntervalEnum.DAY, "m30");
        put(TimeIntervalEnum.WEEK, "h2");
    }};


    @Override
    public Set<CurrencyEnum> getCurrencies() {
        return currencies.keySet();
    }

    @Override
    public double getPrice(CurrencyEnum currencyEnum) throws IOException {
        if (!currencies.containsKey(currencyEnum))
            throw new InvalidParameterException("Currency is not supported");

        Request request = new Request.Builder().url("https://api.coincap.io/v2/rates/" + currencies.get(currencyEnum)).build();
        String responseBody = query(request);

        JSONObject json = (JSONObject) JSONValue.parse(responseBody);
        JSONObject data = (JSONObject) json.get("data");
        String rateUsd = data.get("rateUsd").toString();

        return Double.parseDouble(rateUsd);
    }

    @Override
    public Set<TimeIntervalEnum> getIntervals() {
        return intervals.keySet();
    }

    @Override
    public List<CurrencyResponse> getHistory(CurrencyEnum currencyEnum, TimeIntervalEnum timeIntervalEnum) throws IOException {
        if (!currencies.containsKey(currencyEnum))
            throw new InvalidParameterException("Currency is not supported");
        if (!intervals.containsKey(timeIntervalEnum))
            throw new InvalidParameterException("TimeInterval is not supported");

        String urlCurrency = currencies.get(currencyEnum);
        String urlInterval = intervals.get(timeIntervalEnum);
        long urlEndTime = new Date().getTime();
        long urlStartTime = urlEndTime - timeIntervalEnum.toMillis();

        Request request = new Request.Builder().url("https://api.coincap.io/v2/assets/" + urlCurrency +
                        "/history?interval=" + urlInterval +
                        "&start=" + urlStartTime +
                        "&end=" + urlEndTime)
                .build();
        String responseBody = query(request);

        JSONObject json = (JSONObject) JSONValue.parse(responseBody);
        JSONArray data = (JSONArray) json.get("data");

        List<CurrencyResponse> historyDtoList = new LinkedList<>();
        for (Object i : data) {
            JSONObject object = (JSONObject) i;
            double priceUsd = Double.parseDouble((String) object.get("priceUsd"));
            long time = Long.parseLong(object.get("time").toString());
            historyDtoList.add(new CurrencyResponse(priceUsd, new Date(time)));
        }

        return historyDtoList;
    }

    private String query(Request request) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}