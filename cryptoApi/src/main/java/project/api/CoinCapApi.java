package project.api;

import project.factory.enums.Currency;
import project.factory.history.HistoryCurrencyApi;
import project.factory.history.HistoryDto;
import project.factory.history.TimeInterval;
import project.factory.price.PriceCurrencyApi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import okhttp3.*;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;

public class CoinCapApi implements PriceCurrencyApi, HistoryCurrencyApi {

    private final HashMap<Currency, String> currencies = new HashMap<>() {{
        put(Currency.BITCOIN, "bitcoin");
        put(Currency.ETHEREUM, "ethereum");
        put(Currency.DOGECOIN, "dogecoin");
        put(Currency.TETHER, "tether");
        put(Currency.BINANCECOIN, "binance-coin");
    }};

    private final HashMap<TimeInterval, String> intervals = new HashMap<>() {{  //m1, m5, m15, m30, h1, h2, h6, h12, d1
        put(TimeInterval.HOUR, "m1");
        put(TimeInterval.DAY, "m30");
        put(TimeInterval.WEEK, "h2");
    }};

    @Override
    public String getName() {
        return "Coin Cap";
    }

    @Override
    public Set<Currency> getCurrencies() {
        return currencies.keySet();
    }

    @Override
    public double getPrice(Currency currency) throws IOException {
        if (!currencies.containsKey(currency))
            throw new InvalidParameterException("Currency is not supported");

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.coincap.io/v2/rates/" + currencies.get(currency))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONObject json = (JSONObject) JSONValue.parse(responseBody);
        JSONObject data = (JSONObject) json.get("data");
        String rateUsd = data.get("rateUsd").toString();

        return Double.parseDouble(rateUsd);
    }

    @Override
    public Set<TimeInterval> getIntervals() {
        return intervals.keySet();
    }

    @Override
    public List<HistoryDto> getHistory(Currency currency, TimeInterval timeInterval) throws IOException {
        if (!currencies.containsKey(currency))
            throw new InvalidParameterException("Currency is not supported");

        if (!intervals.containsKey(timeInterval))
            throw new InvalidParameterException("TimeInterval is not supported");

        String urlCurrency = currencies.get(currency);
        String urlInterval = intervals.get(timeInterval);
        long urlEndTime = new Date().getTime();
        long urlStartTime = urlEndTime - timeInterval.toMillis();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.coincap.io/v2/assets/" + urlCurrency +
                        "/history?interval=" + urlInterval +
                        "&start=" + urlStartTime +
                        "&end=" + urlEndTime)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONObject json = (JSONObject) JSONValue.parse(responseBody);
        JSONArray data = (JSONArray) json.get("data");

        List<HistoryDto> historyDtoList = new LinkedList<>();
        for (Object i : data) {
            JSONObject object = (JSONObject) i;
            double priceUsd = Double.parseDouble((String) object.get("priceUsd"));
            long time = Long.parseLong(object.get("time").toString());
            historyDtoList.add(new HistoryDto(priceUsd, new Date(time)));
        }

        return historyDtoList;
    }
}