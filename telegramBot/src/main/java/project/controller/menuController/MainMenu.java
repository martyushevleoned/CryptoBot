package project.controller.menuController;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import project.controller.menuController.factory.Menu;
import project.controller.menuController.factory.MenuType;

import java.util.List;
import java.util.Objects;

@Component
public class MainMenu implements Menu {

    private final String text = "Главное меню";

    private final InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(
                    InlineKeyboardButton.builder().text("все валюты").callbackData(MenuType.CURRENCY_GROUP_MENU.name()).build()
            ))
            .keyboardRow(List.of(
                    InlineKeyboardButton.builder().text("отслеживаемые валюты").callbackData(MenuType.TRACKED_CURRENCIES_MENU.name()).build()
            ))
            .build();


    public boolean match(String text) {
        return Objects.equals(text, "/start") ||
                Objects.equals(text, MenuType.MAIN_MENU.name());
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

