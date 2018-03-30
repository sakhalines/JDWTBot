package commands.thunderTools;

import core.SSSS;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChlogMonitoring{


//    public static void updateGameChangelogChannel(JDA jda){
//        int indexPosition = 1;
//        EmbedBuilder eb = new EmbedBuilder();
//        String sendReaply = ChangeLog.getChangelog(10);
//        TextChannel gameChannelUpdateMonitoring = jda.getTextChannelById(ChlogMonitoring.createGameChangelogChannel(jda));
//
//        //String date = sendReaply.split(" | ")[0].split(": ")[1].split(" | ")[0];
//        String date = ChangeLog.dateLastUpdate;
//        gameChannelUpdateMonitoring.getManager().setName("chlog_" + date).queue();
//        gameChannelUpdateMonitoring.getManager().setTopic("Мониторинг обновлений игры War Thunder (обновлено " + date).queue();
//        if (!gameChannelUpdateMonitoring.getName().contains(date)){
//            try {
//                gameChannelUpdateMonitoring.sendMessage(eb.setDescription(sendReaply).build()).queue();
//                indexPosition++;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static Boolean updateGameChangelogChannel(JDA jda){
//        final Channel[] tc = new Channel[1];
//        jda.getGuilds().forEach(guild ->
//                tc[0] = guild.getController().createTextChannel(prefix).complete());

//        Channel GameCangelogChannel = tc[0];

        Guild guild = null;
        String prefix = null;
                //String prefix = STATICS.gameChangelogChannelPrefix;
                Channel gameChangelogChannel = null;
        boolean gameChangelogChannelIsExist = false;
        for (Guild g : jda.getGuilds() ){
            guild = g;
            prefix = SSSS.getGAMECHANGELOGCHANNELPREFIX(guild);
            for (Channel channel : guild.getTextChannels()){
                if (channel.getName().contains(prefix)){
                    gameChangelogChannel = channel;
                    gameChangelogChannelIsExist = true;
                    break;
                }
            }
        }

        if (!gameChangelogChannelIsExist){
            gameChangelogChannel = guild.getController().createTextChannel(prefix).complete();
            System.out.println("Канал " + prefix + "создан");
        }

        int indexPosition = 1;

        TextChannel gameChannelUpdateMonitoring = jda.getTextChannelById(gameChangelogChannel.getId());

        EmbedBuilder eb = new EmbedBuilder();
        String [] sendReaply = CommonClass.splitStringEvery(ChangeLog.getChangelog(10), 2000);
        String fullDate = ChangeLog.dateLastUpdate;
        String day = fullDate.split(" ")[0],
                mount = fullDate.split(" ")[1],
                year = fullDate.split(" ")[2];
        mount = CommonClass.getDateMountNumber(mount);
        String date = String.join("-", day, mount, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

//        if (jda.getTextChannelsByName(prefix + date, true).isEmpty()){
//            guild.getController().createTextChannel(prefix + date).complete();
//            System.out.println("Канал " + prefix + date + " создан");
//        }

//        TextChannel gameChannelUpdateMonitoring = jda.getTextChannelsByName(prefix + date, true).get(0);

        if (!gameChannelUpdateMonitoring.getName().contains(date)) {
            // очистка канала
            MessageHistory history = new MessageHistory(jda.getTextChannelById(gameChangelogChannel.getId()));
            List<Message> msgs;
            try {
                while (true) {
                    msgs = history.retrievePast(1).complete();
                    if (!msgs.get(0).isPinned()) msgs.get(0).delete().queue();
                }
            } catch (Exception ex) {}


            for (String sendReap : sendReaply) {
               try {
                   gameChannelUpdateMonitoring.sendMessage(eb.setDescription(sendReap).build()).queue();
                   indexPosition++;
               } catch (Exception e) {
                   gameChannelUpdateMonitoring.sendMessage(eb.setDescription("Ошибка получения списка обновлений.").build()).queue();
                   e.printStackTrace();
               }
            }

            // (ВЫЯСНИТЬ ПРИЧИНУ) сначала установить топик, и только потом имя. Иначе канал сбрасывает имя в prefix без даты
            gameChannelUpdateMonitoring.getManager().setTopic("Мониторинг обновлений игры War Thunder (обновлено " + date).queue();
            gameChannelUpdateMonitoring.getManager().setName(prefix + date).queue();
            System.out.println(simpleDateFormat.format(new Date()) + " | Обновление доступны. Канал обновлён.");
            return true;
        }
        else {
            System.out.println(simpleDateFormat.format(new Date()) + " | Обновлений игры нет.");
            return false;
        }
    }
}
