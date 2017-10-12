package commands.administration;

import commands.Command;
import core.Perms;
import core.UpdateClient;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by zekro on 22.03.2017 / 17:13
 * DiscordBot / commands
 * © zekro 2017
 */


public class CheckUpdate implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if (!Perms.isOwner(event.getAuthor(), event.getTextChannel())) return;

        if (args.length == 1){
            if (args[0].equalsIgnoreCase("stable"))
                UpdateClient.update(event.getMessage().getTextChannel(), true);
            else if (args[0].equalsIgnoreCase("pre"))
                UpdateClient.update(event.getMessage().getTextChannel(), false);
        }

        else
            UpdateClient.manualCheck(event.getMessage().getTextChannel());

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "**Использование:** `.checkupdate [release]` или `.chkupd [release]`" +
                "Без параметров используется только для проверки доступных обновлений.\n" +
                "Параметр `[release]` служит для обновления и может быть двух видов:" +
                "       `stable` - обновить до последней стабильной версии.\n" +
                "       `pre` - обновить до последней предваритльной версии.\n" +
                "Примеры:" +
                "       `.chkupd stable`" +
                "       .checkupdate pre";
    }

    @Override
    public String description() {
        return "Проверка и обновление JDWTBot.";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.administration;
    }

    @Override
    public int permission() {
        return 4;
    }
}
