package listeners;

import commands.thunderTools.ChlogMonitoring;
import core.StartArgumentHandler;
import core.UpdateClient;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.Logger;
import utils.STATICS;

import java.awt.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class ReadyListener extends ListenerAdapter {

    static ReadyEvent readyEvent;

    private static void handleStartArgs() {

        String[] args = StartArgumentHandler.args;

        if (args.length > 0) {
            switch (args[0]) {

                case "restart":
                    for (Guild g : readyEvent.getJDA().getGuilds()) {
//                        g.getPublicChannel().sendMessage(
//                                ":ok_hand:  Бот перезапущен!"
//                        ).queue();
                        g.getMemberById(STATICS.BOT_OWNER_ID).getUser().openPrivateChannel()
                                .queue(c -> sendRestartMsg(c));
                    }
                    break;

                case "update":
                    for (Guild g : readyEvent.getJDA().getGuilds()) {
//                        g.getPublicChannel().sendMessage(
//                                ":ok_hand:  Bot successfully updated to version v." + STATICS.VERSION + "!\n\n" +
//                                        "**Changelogs:** http://github.zekro.de/DiscordBot/blob/master/README.md#latest-changelogs\n" +
//                                        "Github Repository: http://github.zekro.de/DiscordBot"
//                        ).queue();
                        g.getMemberById(STATICS.BOT_OWNER_ID).getUser().openPrivateChannel()
                                .queue(c -> sendUpdateMsg(c));
                    }
                    break;

            }
        }

    }
    private static void sendRestartMsg(Object channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.cyan)
                .setTitle(":ok_hand:  Бот перезапущен.");
        sendMsg(channel, eb);
    }
    private static void sendUpdateMsg(Object channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.cyan)
                .setTitle(":ok_hand:  Обновление завершено. Бот перезапущен.");
        sendMsg(channel, eb);
    }

    public static void sendMsg(Object channel, EmbedBuilder eb){
        try {
            TextChannel tc = (TextChannel) channel;
            tc.sendMessage(eb.build()).queue();
        } catch (Exception e) {
            PrivateChannel pc = (PrivateChannel) channel;
            pc.sendMessage(eb.build()).queue();
        }
    }



    @Override
    public void onReady(ReadyEvent event) {

        StringBuilder sb = new StringBuilder();
        event.getJDA().getGuilds().forEach(guild -> sb.append("|  - \"" + guild.getName() + "\" - {@" + guild.getOwner().getUser().getName() + "#" + guild.getOwner().getUser().getDiscriminator() + "} - [" + guild.getId() + "] \n"));

        System.out.println(String.format(
                "\n\n" +
                "#------------------------------------------------------------------------- - - -  -  -  -   -\n" +
                "| %s - v.%s (JDA: v.%s)\n" +
                "#------------------------------------------------------------------------- - - -  -  -  -   -\n" +
                "| Running on %s guilds: \n" +
                "%s" +
                "#------------------------------------------------------------------------- - - -  -  -  -   -\n\n",
        Logger.Cyan + Logger.Bold + "JDWTBot" + Logger.Reset, STATICS.VERSION, "3.2.0_242", event.getJDA().getGuilds().size(), sb.toString()));

        if (STATICS.BOT_OWNER_ID == 0) {
            Logger.ERROR(
                    "#######################################################\n" +
                    "# PLEASE INSERT YOUR DISCORD USER ID IN SETTINGS.TXT  #\n" +
                    "# AS ENTRY 'BOT_OWNER_ID' TO SPECIFY THAT YOU ARE THE #\n" +
                    "# BOTS OWNER!                                         #\n" +
                    "#######################################################"
            );
        }

        commands.settings.Botmessage.setSupplyingMessage(event.getJDA());

        readyEvent = event;

        STATICS.lastRestart = new Date();

        handleStartArgs();

        if (STATICS.autoUpdate)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    UpdateClient.checkIfUpdate(event.getJDA());
                }
            }, 0, 60000);


        if (!STATICS.gameChangelogUpdateInterval.equalsIgnoreCase("OFF")){
            int intervalParse = Integer.parseInt(STATICS.gameChangelogUpdateInterval);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    ChlogMonitoring.updateGameChangelogChannel(event.getJDA());
                }
            }, 0, intervalParse * 60000);
        }

        commands.chat.Counter.loadAll(event.getJDA());
        commands.guildAdministration.Autochannel.load(event.getJDA());

    }
}
