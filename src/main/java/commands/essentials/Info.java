package commands.essentials;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.awt.*;

public class Info implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String version = "NOT AVAILABLE";
        try {

            //URL url = new URL(UpdateClient.versionURL);
            //Scanner s = new Scanner(url.openStream());
            version = "null"; //s.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

        event.getTextChannel().sendMessage(
                new EmbedBuilder()
                        .setColor(Color.MAGENTA)
//                        .setThumbnail("https://raw.githubusercontent.com/zekroTJA/DiscordBot/master/.websrc/zekroBot%20Logo%20-%20round.png")
                        .setDescription(":robot:    __**JDWTBot** Java Discord War Thunder Bot на основе **zekroBot**__")
//                        .addField("Current Version", STATICS.VERSION, true)
//                        .addField("Latest Version", version, true)
//                        .addField("Build Type", STATICS.THISBUILD, true)
                        .addField("Copyright",
                                "**zekroBot** Coded by zekro Development Team.\n" +
                                        "© 2016 - 2017 Ringo Hoffmann and Sophie Lorenz.\n" +
                                        "**JDWTBot** Coded by sakhalines © 2017 .\n", false)
                        .addField("Информация и ссылки",
                                "GitHub Repository **zekroBot**: \n*http://github.zekro.de/DiscordBot*\n\n" +
                                        "GitHub Repository **JDWTBot**: \n*https://github.com/sakhalines/JDWTBot*\n\n", false)
                .build()

        ).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "Использование: -info";
    }

    @Override
    public String description() {
        return "Получить информацию о боте";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.essentials;
    }

    @Override
    public int permission() {
        return 1;
    }
}
