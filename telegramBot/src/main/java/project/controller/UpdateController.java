package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.controller.menuController.*;
import project.controller.menuController.factory.Menu;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateController {

    private TelegramBot bot;
    private List<Menu> menuList;

    @Autowired
    private MainMenu mainMenu;
    @Autowired
    private CurrenciesGroupMenu currenciesGroupMenu;
    @Autowired
    private CurrenciesMenu currenciesMenu;
    @Autowired
    private TrackedCurrenciesMenu trackedCurrenciesMenu;
    @Autowired
    private CurrencyMenu currencyMenu;

    public void registerTelegramBot(TelegramBot telegramBot) {
        this.bot = telegramBot;
        menuList = new ArrayList<>();
        menuList.add(mainMenu);
        menuList.add(currenciesGroupMenu);
        menuList.add(currenciesMenu);
        menuList.add(trackedCurrenciesMenu);
        menuList.add(currencyMenu);
    }

    public void processUpdate(Update update) {
        try {
            if (update.hasCallbackQuery()) {
                processCallback(update);

            } else if (update.hasMessage() && update.getMessage().hasText()) {
                processMessage(update);

            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void processCallback(Update update) throws TelegramApiException {
        String callbackData = update.getCallbackQuery().getData();

        for (Menu m : menuList) {
            if (m.match(callbackData)) {
                bot.execute(m.getEditMessage(update));
                break;
            }
        }
    }

    private void processMessage(Update update) throws TelegramApiException {
        String messageText = update.getMessage().getText();

        for (Menu m : menuList) {
            if (m.match(messageText)) {
                bot.execute(m.getSendMessage(update));
                break;
            }
        }
    }
}
