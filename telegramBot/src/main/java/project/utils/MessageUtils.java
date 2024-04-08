package project.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageUtils {

    public SendMessage unsupportedMessage(Message message, String cause) {
        return new SendMessage(String.valueOf(message.getChatId()), cause + " is unsupported");
    }
}
