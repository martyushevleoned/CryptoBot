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
import project.service.TrackService;
import project.service.TrackedCurrencyDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class TrackedCurrenciesMenu implements Menu {

    @Autowired
    private TrackService trackService;

    private final String text = "Список отслеживаемых валют";

    private InlineKeyboardMarkup generateInlineKeyboardMarkup(long chatId) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        Set<TrackedCurrencyDto> trackedCurrencies = trackService.getTrackedCurrencies(chatId);
        trackedCurrencies.forEach(trackedCurrencyDto -> {
            String text = trackedCurrencyDto.getText();
            String callback = trackedCurrencyDto.getCallback();
            rows.add((List.of(InlineKeyboardButton.builder().text(text).callbackData(callback).build())));
        });

        rows.add((List.of(InlineKeyboardButton.builder().text("назад").callbackData(MenuType.MAIN_MENU.toString()).build())));
        return new InlineKeyboardMarkup(rows);
    }

    @Override
    public boolean match(String text) {
        return Objects.equals(text, MenuType.TRACKED_CURRENCIES_MENU.name());
    }

    @Override
    public SendMessage getSendMessage(Update update) {
        long chatId = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(generateInlineKeyboardMarkup(chatId));
        return sendMessage;
    }

    @Override
    public EditMessageText getEditMessage(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(text);
        editMessageText.setReplyMarkup(generateInlineKeyboardMarkup(chatId));
        return editMessageText;
    }
}
