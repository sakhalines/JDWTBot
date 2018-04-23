package commands.thunderTools;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.awt.*;
import java.io.IOException;

public class Skill implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length == 0 || args.length > 6) {
            try {
                event.getChannel().sendMessage(CommonClass.sendHelpCommands("wrongPlayerName")).queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        Boolean arcade = false,
                real = false,
                simul = false,
                updateStat = false,
                sendAsChannelMsg = false;

        //if (StringUtils.isNotBlank(args[0])){

        if (args.length >= 2) {
            // если параметры не разделены пробелом
            if (args[0].length() > 1) {
                for (int i = 0; i <= args[0].length() - 1; i++) {
                    switch (args[0].toCharArray()[i]) {
                        case 'a': arcade = true; break;
                        case 'r': real = true; break;
                        case 's': simul = true; break;
                        case 'u': updateStat = true; break;
                        case 'c': sendAsChannelMsg = true; break;
                    }
                }
            }
            // если параметры разделены пробелом
            else {
                for (int i = 0; i <= args.length - 1; i++) {
                    switch (args[i]) {
                        case "a": arcade = true; break;
                        case "r": real = true; break;
                        case "s": simul = true; break;
                        case "u": updateStat = true; break;
                        case "c": sendAsChannelMsg = true; break;
                    }
                }
            }
        }
        //}
        // берем последний элемент, который должен содержать имя игрока
        String playerName = args[args.length - 1];

        if (updateStat) {
            try {
                CommonClass.sendPostRequest(playerName);
                EmbedBuilder eb = new EmbedBuilder();
                //if (sendAsChannelMsg) { //послать ответ в текущий канал
                    eb.setDescription("Статистика игрока ** " + playerName + " ** обновлена");
                    event.getChannel().sendMessage(eb.build()).queue();
                //}
                //else {
                //    PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                //    pc.sendMessage("Статистика игрока ** " + playerName + " ** обновлена");
                //}
            } catch (IOException e) { e.printStackTrace(); }
        }
        String[] stat = null;
        stat = StatRequest.getStatForPlayerHtml(playerName);

        //  ВНИМАНИЕ выход из функции, если статистика пуста
        if (!String.join(",", stat).contains("Последнее обновление")){
            event.getChannel().sendMessage(":warning: Игрок " + playerName + " не найден.").queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.yellow);
        eb.setTitle("Запрос статистики от **" + event.getAuthor().getName()+ "**");
        //eb.setImage("http://thunderskill.com/userbars/s/a/" + playerName + "/ru-1-combined-r.png");
        eb.setThumbnail("https://vignette.wikia.nocookie.net/warthunder/images/a/a3/WarThunder_icon.png/revision/latest?cb=20121222231347");

        if (arcade || real || simul) {  // если указан режим игры
            String titleStat = "", arcadeStat = "", realStat = "", simulStat = "";
            titleStat = stat[0].split("--")[0];
            if (arcade) {
                arcadeStat = stat[0].split("--")[1];
            }
            if (real) {
                realStat = stat[1].split("--")[1];
            }
            if (simul) {
                simulStat = stat[2].split("--")[1];
            }
            if (sendAsChannelMsg) {
                try {
                    eb.setDescription(titleStat + arcadeStat + realStat + simulStat);
                    event.getChannel().sendMessage(eb.build()).queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                pc.sendMessage(titleStat + arcadeStat + realStat + simulStat).queue();
            }
        }
        else { // если не указан режим игры
            if (sendAsChannelMsg) { //послать ответ в текущий канал
                try {
                    eb.setDescription(stat[0] +
                            stat[1].split("--")[1] +
                            stat[2].split("--")[1]
                    );

                    event.getChannel().sendMessage(eb.build()).queue();
                    //event.getChannel().sendMessage(stat[1].split("--")[1]).queue();// срезка -- необходима чтобы был заголовок когда указан режим, а без режима заголовок только в начале
                    //event.getChannel().sendMessage(stat[2].split("--")[1]).queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else { //послать ответ в личку
                PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                pc.sendMessage(stat[0]).queue();
                pc.sendMessage(stat[1].split("--")[1]).queue();
                pc.sendMessage(stat[2].split("--")[1]).queue();
            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        String helpText = CommonClass.sendHelpCommands("");
        return helpText;
    }

    @Override
    public String description() {
        return "Получение статистики игрока с сайта tunderskill.com";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.thunderTools;
    }

    @Override
    public int permission() {
        return 1;
    }
}
