package commands.administration;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class BotUpdateMessageEnable implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        File f = new File("SERVER_SETTINGS/no_update_info");
        if (f.exists())
            f.delete();

        event.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.green)
                .setDescription("Вы включили информационное сообщение о доступности обновления бота.")
                .setFooter("Для отключения используйте\"" + STATICS.PREFIX + "updmsgoff\"", null)
                .build()).queue();

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
        return "Включение информационного сообщения о доступности обновления бота";
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
