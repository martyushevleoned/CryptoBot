package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import project.controller.menuController.MainMenu;
import project.utils.MessageUtils;

@Component
public class UpdateController {

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private MainMenu mainMenu;

    public SendMessage processUpdate(Update update) {
        Message message = update.getMessage();
        return distributeMessageByType(message);
    }

    private SendMessage distributeMessageByType(Message message) {

        if (message.hasText()) {


            if (mainMenu.match(message.getText()))
                return mainMenu.getMenu(message);

            return echo(message);
        }

        return messageUtils.unsupportedMessage(message, "Unsupported messageType");
    }

    private SendMessage echo(Message message) {
        return new SendMessage(String.valueOf(message.getChatId()), message.getText());
    }
}
