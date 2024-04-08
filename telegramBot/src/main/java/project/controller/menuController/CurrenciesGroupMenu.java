package project.controller.menuController;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import project.controller.menuController.factory.CurrencyType;
import project.controller.menuController.factory.Menu;
import project.controller.menuController.factory.MenuType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CurrenciesGroupMenu implements Menu {

    private final String text = "Выберите тип валюты";

//    private final InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
//            .keyboardRow(List.of(
//                    InlineKeyboardButton.builder().text(CurrencyType.CRYPTO).callbackData(CurrencyType.CRYPTO.name()).build()
//            ))
//            .keyboardRow(List.of(
//                    InlineKeyboardButton.builder().text("назад").callbackData(MenuType.MAIN_MENU.name()).build()
//            ))
//            .build();

    private final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup() {{

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (CurrencyType c : CurrencyType.values())
            rows.add(List.of(InlineKeyboardButton.builder().text(CurrencyType.CRYPTO.getType()).callbackData(CurrencyType.CRYPTO.name()).build()));

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
