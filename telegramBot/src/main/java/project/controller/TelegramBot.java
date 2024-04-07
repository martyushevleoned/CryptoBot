package project.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.config.BotConfig;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private BotConfig botConfig;

    @Autowired
    private UpdateController updateController;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(updateController.processUpdate(update));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
