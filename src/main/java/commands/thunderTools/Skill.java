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
//        String[] args = msg.split(" ");
//        for(int j = 0; j < args.length; j++) {
//            System.out.println("in action" + args[j]);
//        }
        Boolean arcade = false,
                real = false,
                simul = false,
                updateStat = false,
                sendAsChannelMsg = false,
                sendAsChannelDescription;

        for (int i = 0; i <= args.length - 1; i++){
            switch (args[i]){
                case "a": arcade = true; break;
                case "r": real = true; break;
                case "s": simul = true; break;
                case "u": updateStat = true; break;
                case "c": sendAsChannelMsg = true; break;
            }
        }
//        String msg = String.join(",", args);
//            boolean arcade = msg.contains("-a"),
//                    real = msg.contains("-r"),
//                    simul = msg.contains("-s"),
//                    sendAsChannelMsg = msg.contains("-c"),
//                    sendAsChannelDescription = msg.contains("-C"),
//                    help = msg.contains("-h"),
//                    updateStat = msg.contains("-u");

            if (args.length != 0) {
                // берем последний элемент, который должен содержать имя игрока
                String playerName = args[args.length - 1];

                if (updateStat)
                    try {
                        CommonClass.sendPostRequest(playerName);
                        PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                        pc.sendMessage("Статистика игрока ** " + playerName + " ** обновлена");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                String[] stat = null;

                stat = StatRequest.getStatForPlayerHtml(playerName);

//  ВНИМАНИЕ выход из функции, если статистика пуста
                if (!String.join(",", stat).contains("Последнее обновление")){
                    event.getTextChannel().sendMessage(":warning: Игрок " + playerName + " не найден.").queue();
                    return;
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.yellow);
                eb.setTitle("Запрос статистики от **" + event.getAuthor().getName()+ "**");
                //eb.setImage("http://thunderskill.com/userbars/s/a/" + playerName + "/ru-1-combined-r.png");
                eb.setThumbnail("https://vignette.wikia.nocookie.net/warthunder/images/a/a3/WarThunder_icon.png/revision/latest?cb=20121222231347");
                //event.getTextChannel().sendMessage(eb.build()).queue();

                if (arcade || real || simul) { // если указан режим игры
                    if (arcade) {
//                        String statConacatinaed;
                        if (sendAsChannelMsg) {  //послать ответ в текущий канал
                            eb.setDescription(stat[0]);
                            event.getTextChannel().sendMessage(eb.build()).queue();
                        }
//                        else if (sendAsChannelDescription) {  //послать ответ как описание канала TREFoBOT
//                            queryLib.doCommand("channeledit cid=" + botHomeChannel + " channel_description=" +
//                                    queryLib.encodeTS3String(stat[0]).queue());
//                        }
                        else {    //послать ответ в личку
                            try {
                                PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                                pc.sendMessage(stat[0]).queue();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }else if (real) {
                        if (sendAsChannelMsg) {
                            try {
                                eb.setDescription(stat[1]);
                                event.getTextChannel().sendMessage(eb.build()).queue();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
//                        else if (sendAsChannelDescription) {
//                            queryLib.doCommand("channeledit cid=" + botHomeChannel + " channel_description=" +
//                                    queryLib.encodeTS3String(stat[1]));
//                        }
                        else {    //послать ответ в личку
                            PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                            pc.sendMessage(stat[1]).queue();
                        }
                    } else if (simul) {
                        if (sendAsChannelMsg) {
                            try {
                                eb.setDescription(stat[2]);
                                event.getTextChannel().sendMessage(eb.build()).queue();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
//                        else if (sendAsChannelDescription) {
//                            queryLib.doCommand("channeledit cid=" + botHomeChannel + " channel_description=" +
//                                    queryLib.encodeTS3String(stat[2]));
//                        }
                        else {    //послать ответ в личку
                            PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                            pc.sendMessage(stat[2]).queue();
                        }
                    }
                } else { // если НЕ указан режим игры
                    if (sendAsChannelMsg) {  //послать ответ в текущий канал
                        try {
                            eb.setDescription(stat[0] +
                                    stat[1].split("--")[1] +
                                    stat[2].split("--")[1]
                            );

                            event.getTextChannel().sendMessage(eb.build()).queue();
                            //event.getTextChannel().sendMessage(stat[1].split("--")[1]).queue();// срезка -- необходима чтобы был заголовок когда указан режим, а без режима заголовок только в начале
                            //event.getTextChannel().sendMessage(stat[2].split("--")[1]).queue();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                    else if (sendAsChannelDescription) {  //послать ответ как описание канала TREFoBOT
//                        queryLib.doCommand("channeledit cid=" + botHomeChannel + " channel_description=" +
//                                queryLib.encodeTS3String(stat[0] + stat[1].split("--")[1] + stat[2].split("--")[1]));
//                    }
                    else {    //послать ответ в личку
                        PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                        pc.sendMessage(stat[0]).queue();
                        pc.sendMessage(stat[1].split("--")[1]).queue();
                        pc.sendMessage(stat[2].split("--")[1]).queue();
                    }
                }

            } else {
//                if (sendAsChannelMsg) {  //послать ответ в текущий канал
                    try {
                        event.getTextChannel().sendMessage(CommonClass.sendHelpCommands("")).queue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                }
//                else {    //послать ответ в личку
//                    PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
//                    pc.sendMessage(CommonClass.sendHelpCommands("")).queue();
//                }
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
