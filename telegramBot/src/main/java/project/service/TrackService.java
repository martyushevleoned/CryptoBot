package project.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Service
public class TrackService {

    private final Map<Long, Set<TrackedCurrencyDto>> trackedCurrenciesMap = new HashMap<>();

    public void addToTrack(long chatId, TrackedCurrencyDto currency) {

        if (!trackedCurrenciesMap.containsKey(chatId)) {
            trackedCurrenciesMap.put(chatId, new LinkedHashSet<>(Set.of(currency)));
            return;
        }

        if (trackedCurrenciesMap.get(chatId).contains(currency)) {
            trackedCurrenciesMap.get(chatId).remove(currency);
        } else {
            trackedCurrenciesMap.get(chatId).add(currency);
        }
    }

    public Set<TrackedCurrencyDto> getTrackedCurrencies(long chatId) {
        return trackedCurrenciesMap.getOrDefault(chatId, Set.of());
    }

}
