package project.controller.menuController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import project.controller.menuController.factory.Menu;
import project.controller.menuController.factory.MenuType;
import project.factory.enums.ApiEnum;
import project.factory.enums.Currency;
import project.factory.enums.CurrencyType;
import project.service.TrackedCurrencyDto;
import project.service.CurrencyService;
import project.service.TrackService;

import java.util.*;

@Component
public class CurrenciesMenu implements Menu {

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private TrackService trackService;

    private final String text = "Выберите валюту";

    private InlineKeyboardMarkup generateInlineKeyboardMarkup(CurrencyType currencyType, long chatId) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<TrackedCurrencyDto> trackedCurrencyDtoList = currencyService.getCurrenciesByCurrencyType(currencyType);
        Set<TrackedCurrencyDto> trackedCurrencies = trackService.getTrackedCurrencies(chatId);

        trackedCurrencyDtoList.forEach(trackedCurrencyDto -> {
            String text = trackedCurrencyDto.getText();
            if (trackedCurrencies.contains(trackedCurrencyDto))
                text = "# " + text;
            String callback = currencyType.toString() + " " + trackedCurrencyDto.getCallback();
            rows.add((List.of(InlineKeyboardButton.builder().text(text).callbackData(callback).build())));
        });

        rows.add((List.of(InlineKeyboardButton.builder().text("назад").callbackData(MenuType.CURRENCY_GROUP_MENU.toString()).build())));

        return new InlineKeyboardMarkup(rows);
    }

    @Override
    public boolean match(String text) {
        if (text.contains(" ")) {
            return Arrays.stream(CurrencyType.values()).map(Enum::toString).toList().contains(text.split(" ")[0]);
        }
        return Arrays.stream(CurrencyType.values()).map(Enum::toString).toList().contains(text);
    }

    @Override
    public SendMessage getSendMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        CurrencyType currencyType = getCurrencyType(update.getMessage().getText(), chatId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(generateInlineKeyboardMarkup(currencyType, chatId));
        return sendMessage;
    }

    @Override
    public EditMessageText getEditMessage(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String callback = update.getCallbackQuery().getData();
        CurrencyType currencyType = getCurrencyType(callback, chatId);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setText(text);
        editMessageText.setReplyMarkup(generateInlineKeyboardMarkup(currencyType, chatId));
        return editMessageText;
    }

    private CurrencyType getCurrencyType(String callbackData, long chatId) {
        if (!callbackData.contains(" "))
            return CurrencyType.valueOf(callbackData);

        String[] callback = callbackData.split(" ");
        CurrencyType currencyType = CurrencyType.valueOf(callback[0]);
        ApiEnum apiEnum = ApiEnum.valueOf(callback[1]);
        Currency currency = Currency.valueOf(callback[2]);
        trackService.addToTrack(chatId, new TrackedCurrencyDto(currency, apiEnum));
        return currencyType;
    }
}
