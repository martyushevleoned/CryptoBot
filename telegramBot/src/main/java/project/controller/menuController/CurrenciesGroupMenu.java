package project.controller.menuController;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import project.controller.menuController.factory.Menu;
import project.controller.menuController.factory.MenuType;
import project.factory.enums.CurrencyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CurrenciesGroupMenu implements Menu {

    private final String text = "Выберите тип валюты";

    private final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup() {{

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (CurrencyType currencyType : CurrencyType.values())
            rows.add(List.of(InlineKeyboardButton.builder().text(currencyType.getType()).callbackData(currencyType.name()).build()));

        rows.add(List.of(InlineKeyboardButton.builder().text("назад").callbackData(MenuType.MAIN_MENU.name()).build()));
        setKeyboard(rows);
    }};

    @Override
    public boolean match(String text) {
        return Objects.equals(text, MenuType.CURRENCY_GROUP_MENU.name());
    }

    @Override
    public SendMessage getSendMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public EditMessageText getEditMessage(Update update) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setText(text);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }
}
