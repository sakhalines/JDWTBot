package commands.essentials;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.awt.*;
import java.util.Date;

public class Ping implements Command {

    private final String HELP = "Использование: ~ping";

    private static long inputTime;

    public static void setInputTime(long inputTimeLong) {
        inputTime = inputTimeLong;
    }

    private Color getColorByPing(long ping) {
        if (ping < 100)
            return Color.cyan;
        if (ping < 400)
            return Color.green;
        if (ping < 700)
            return Color.yellow;
        if (ping < 1000)
            return Color.orange;
        return Color.red;
    }



    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    public void action(String[] args, MessageReceivedEvent event) {
        long processing = new Date().getTime() - inputTime;
        long ping = event.getJDA().getPing();
        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(getColorByPing(ping)).setDescription(
                String.format(":ping_pong:   **Понг!**\n\nОтвет за`%s` мсек.\nИз них `%s` мсек. на обработку команды и `%s` мсек на пинг.",
                        processing + ping, processing, ping)
        ).build()).queue();
    }

    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public String help() {

        return HELP;
    }

    @Override
    public String description() {
        return "Pong!";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.essentials;
    }

    @Override
    public int permission() {
        return 0;
    }


}
