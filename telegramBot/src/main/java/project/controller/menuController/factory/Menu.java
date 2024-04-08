package project.controller.menuController.factory;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Menu {

    boolean match(String text);
    SendMessage getSendMessage(Update update);
    EditMessageText getEditMessage(Update update);
}
