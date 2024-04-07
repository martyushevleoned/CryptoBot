package project.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateController {

    public SendMessage processUpdate(Update update) {
        Message message = update.getMessage();
        return echo(message);
    }

    private SendMessage echo(Message message) {
        return new SendMessage(String.valueOf(message.getChatId()), message.getText());
    }
}
