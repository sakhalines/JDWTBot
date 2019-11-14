package commands.thunderTools;

import core.SSSS;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Monitoring{


    public static Boolean updateGameContentMonitoringChannel(JDA jda, String contentType){
        Guild guild = null;
        String channelPrefix = null;
                //String channelPrefix = STATICS.gameChangelogChannelPrefix;
                Channel gameContentChannel = null,
                        gameNewsChannel = null;
        boolean сhannelIsExist = false;
        for (Guild g : jda.getGuilds() ){
            guild = g;
            switch (contentType) {
                case "gameChangelog": {channelPrefix = SSSS.getGAMECHANGELOGCHANNELPREFIX(guild); break;}
                case "gameNews": {channelPrefix = SSSS.getGAMENEWSCHANNELPREFIX(guild); break;}
            }
            for (Channel channel : guild.getTextChannels()){
                if (channel.getName().contains(channelPrefix)){
                    gameContentChannel = channel;
                    сhannelIsExist = true;
                    break;
                }
            }
        }

        if (!сhannelIsExist){
            gameContentChannel = guild.getController().createTextChannel(channelPrefix).complete();
            System.out.println("Канал " + channelPrefix + "создан");
        }

        int indexPosition = 1;

        TextChannel gameChannelMonitoring = jda.getTextChannelById(gameContentChannel.getId());

        EmbedBuilder eb = new EmbedBuilder();
        String [] sendReaply = null;
        String fullDate = null;
        switch (contentType) {
            case "gameChangelog":
                sendReaply = CommonClass.splitStringEvery(GetAndSendContent.getContent(10, "gameChangelog"), 2000);
                fullDate = GetAndSendContent.dateContentLastUpdate;
                break;
            case "gameNews":
                sendReaply = CommonClass.splitStringEvery(GetAndSendContent.getContent(10, "gameNews"), 2000);
                fullDate = GetAndSendContent.dateContentLastUpdate;
                break;
        }
        String day = fullDate.split(" ")[0],
        mount = fullDate.split(" ")[1],
        year = fullDate.split(" ")[2];
        mount = CommonClass.getDateMountNumber(mount);
        String date = String.join("-", day, mount, year);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        if (!gameChannelMonitoring.getName().contains(date)) {
            // очистка канала
            MessageHistory history = new MessageHistory(jda.getTextChannelById(gameContentChannel.getId()));
            List<Message> msgs;
            try {
                while (true) {
                    msgs = history.retrievePast(1).complete();
                    if (!msgs.get(0).isPinned()) msgs.get(0).delete().queue();
                }
            } catch (Exception ex) {}


            for (String sendReap : sendReaply) {
               try {
                   gameChannelMonitoring.sendMessage(eb.setDescription(sendReap).build()).queue();
                   indexPosition++;
               } catch (Exception e) {
                   gameChannelMonitoring.sendMessage(eb.setDescription("Ошибка получения списка обновлений.").build()).queue();
                   e.printStackTrace();
               }
            }

            // (ВЫЯСНИТЬ ПРИЧИНУ) сначала установить топик, и только потом имя. Иначе канал сбрасывает имя в channelPrefix без даты
            switch (contentType) {
                case "gameChangelog": {
                    gameChannelMonitoring.getManager().setTopic("Мониторинг обновлений игры War Thunder (обновлено " + date).queue();
                    gameChannelMonitoring.getManager().setName(channelPrefix + date).queue();
                    System.out.println(simpleDateFormat.format(new Date()) + " | Обновления игры доступны. Канал " + channelPrefix + date + " обновлён.");
                    break;
                }
                case "gameNews": {
                    gameChannelMonitoring.getManager().setTopic("Мониторинг обновлений новостей игры War Thunder (обновлено " + date).queue();
                    gameChannelMonitoring.getManager().setName(channelPrefix + date).queue();
                    System.out.println(simpleDateFormat.format(new Date()) + " | Обновления новостей игры доступны. Канал " + channelPrefix + date + " обновлён.");
                    break;
                }
            }
            return true;
        }
        else {
            switch (contentType) {
                case "gameChangelog": {
                    System.out.println(simpleDateFormat.format(new Date()) + " | Обновлений игры нет.");
                    break;
                }
                case "gameNews": {
                    System.out.println(simpleDateFormat.format(new Date()) + " | Обновлений новостей игры нет.");
                    break;
                }
            }
            return false;
        }
    }
}
