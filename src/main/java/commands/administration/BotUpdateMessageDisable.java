package commands.administration;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class BotUpdateMessageDisable implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        try {
            new File("SERVER_SETTINGS/no_update_info").createNewFile();
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(Color.red)
                    .setDescription("Вы отключили информационное сообщение о доступности обновления бота.\n")
                    .setFooter("Для включения используйте\"" + STATICS.PREFIX + "updmsgon\"", null)
                    .build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String description() {
        return "Отключение информационного сообщения о доступности обновления бота";
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
