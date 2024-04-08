package project.controller.menuController;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MainMenu {

    private final Set<String> matchMessages = new HashSet<>() {{
        add("/start");
    }};

    public boolean match(String text) {
        return matchMessages.contains(text);
    }

    private final List<KeyboardRow> keyboardRows = new ArrayList<>() {
        {
            add(new KeyboardRow() {{add("1");}});
            add(new KeyboardRow() {{add("2");}});
            add(new KeyboardRow() {{add("3");}});

        }
    };

    private final ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);


    public SendMessage getMenu(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Main menu");
        sendMessage.setReplyMarkup(keyboardMarkup);

        return sendMessage;
    }
}

