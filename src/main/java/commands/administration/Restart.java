package commands.administration;

import commands.Command;
import core.Perms;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by zekro on 24.03.2017 / 19:49
 * DiscordBot / commands.administration
 * © zekro 2017
 */


public class Restart implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if (!Perms.isOwner(event.getAuthor(), event.getTextChannel())) return;

        event.getTextChannel().sendMessage(":warning:  Бот перезапускается...").queue();
        restart("restart");
    }

    public static void restart(String restartOrUpdateParam) throws IOException {
        if (System.getProperty("os.name").toLowerCase().contains("linux"))
            Runtime.getRuntime().exec("screen -dmLS JDWTBot java -jar JDWTBot.jar " + restartOrUpdateParam);
        else
            Runtime.getRuntime().exec("java -jar JDWTBot.jar " + restartOrUpdateParam);

        System.exit(0);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "Использование: -restart";
    }

    @Override
    public String description() {
        return "Restart the bot.";
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
