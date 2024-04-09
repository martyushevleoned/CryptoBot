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
import project.service.CurrencyService;
import project.service.TrackedCurrencyDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyMenu implements Menu {

    @Autowired
    private CurrencyService currencyService;

    private String getText(TrackedCurrencyDto trackedCurrencyDto){
        return currencyService.getInfoByTrackedCurrencyDto(trackedCurrencyDto);
    }

    private InlineKeyboardMarkup generateInlineKeyboardMarkup() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        rows.add((List.of(InlineKeyboardButton.builder().text("назад").callbackData(MenuType.TRACKED_CURRENCIES_MENU.toString()).build())));
        return new InlineKeyboardMarkup(rows);
    }

    @Override
    public boolean match(String text) {
        try {
            new TrackedCurrencyDto(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SendMessage getSendMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(getText(new TrackedCurrencyDto(text)));
        sendMessage.setReplyMarkup(generateInlineKeyboardMarkup());
        return sendMessage;
    }

    @Override
    public EditMessageText getEditMessage(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callback = update.getCallbackQuery().getData();

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(getText(new TrackedCurrencyDto(callback)));
        editMessageText.setReplyMarkup(generateInlineKeyboardMarkup());
        return editMessageText;
    }
}
