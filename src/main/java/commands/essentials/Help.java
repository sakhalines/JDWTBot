package commands.essentials;

import commands.Command;
import core.Main;
import core.SSSS;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zekro on 24.03.2017 / 19:46
 * DiscordBot / commands
 * © zekro 2017
 */


public class Help implements Command {


    EmbedBuilder eb = new EmbedBuilder();

    private String getPermPre(int lvl) {
        switch (lvl) {
            case 1: return ":small_blue_diamond:";
            case 2: return ":small_orange_diamond:";
            case 3: return ":small_red_triangle_down:";
            case 4: return ":diamonds:";
            default: return ":white_small_square:";
        }
    }


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String cmdPrefix = SSSS.getPREFIX(event.getGuild());

        if (args.length > 0) {
            if (Main.commands.containsKey(args[0]))
                if (Main.commands.get(args[0]).help() != null)
                    event.getChannel().sendMessage(
                            eb.setColor(new Color(22, 138, 233)).setDescription(Main.commands.get(args[0]).help()).build()
                    ).queue();
                else
                    event.getChannel().sendMessage(
                            eb.setColor(Color.red).setDescription(":warning:  В настоящее время нет информации для команды  *" + cmdPrefix + args[0] + "* !").build()
                    ).queue();
            else
                event.getChannel().sendMessage(
                        eb.setColor(Color.red).setDescription(":warning:  Список команд не содержит информацию для команды *"  + cmdPrefix + args[0] + "* !").build()
                ).queue();
            return;
        }

        //event.getMessage().delete().queue();

        Map<String, String> cmds = new TreeMap<>();
        Main.commands.forEach((s, command) -> cmds.put(s, command.description()));

        StringBuilder ciams = new StringBuilder();

        String[] ignorers = {"bpoll", "test", "r", "c", "bj", "ttt", "userinfo", "dev", "nudge", "poll", "moveall", "purge", "info", "suggestion", "r6", };
        Arrays.stream(ignorers).forEach(s -> cmds.remove(s));


        try {

            PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
            pc.sendMessage(":clipboard:  __**СПИСОК КОМАНД**__  :clipboard: \n\n" +
                                        "***Легенда:***\n" +
                                        "  :white_small_square:  -  Доступно для всех.\n" +
                                        "  :small_blue_diamond:  -  Доступно для: `" + Arrays.toString(SSSS.getPERMROLES_1(event.getGuild())).replace("[", "").replace("]", "") + "`\n" +
                                        "  :small_orange_diamond:  -  Доступно для:`" + Arrays.toString(SSSS.getPERMROLES_2(event.getGuild())).replace("[", "").replace("]", "") + "`\n" +
                                        "  :small_red_triangle_down:  -  Доступно для:`" + Arrays.toString(SSSS.getPERMROLES_3(event.getGuild())).replace("[", "").replace("]", "") + "`\n" +
                                        "  :diamonds:  -  Доступно только для владельца сервера\n" +
                                        ":information_source: - Используйте **`'" + cmdPrefix + "help [имя_команды]'`** для получения подробной справки по команде\n"
                    + "\n\n___").queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + STATICS.CMDTYPE.thunderTools + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(STATICS.CMDTYPE.thunderTools))
                    .forEach(s1 -> ciams.append(
                            getPermPre(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(255, 170, 108)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + STATICS.CMDTYPE.administration + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(STATICS.CMDTYPE.administration))
                    .forEach(s1 -> ciams.append(
                            getPermPre(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(134, 255, 0)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + STATICS.CMDTYPE.chatutils + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(STATICS.CMDTYPE.chatutils))
                    .forEach(s1 -> ciams.append(
                            getPermPre(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(255, 97, 0)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + STATICS.CMDTYPE.essentials + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(STATICS.CMDTYPE.essentials))
                    .forEach(s1 -> ciams.append(
                            getPermPre(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(255, 0, 213)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + STATICS.CMDTYPE.etc + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(STATICS.CMDTYPE.etc))
                    .forEach(s1 -> ciams.append(
                            getPermPre(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(39, 0, 255)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + STATICS.CMDTYPE.guildadmin + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(STATICS.CMDTYPE.guildadmin))
                    .forEach(s1 -> ciams.append(
                            getPermPre(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(0, 233, 255)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
//            ciams.append("**" + STATICS.CMDTYPE.music + "**\n");
//            cmds.keySet().stream()
//                    .filter(s -> Main.commands.get(s).commandType().equals(STATICS.CMDTYPE.music))
//                    .forEach(s1 -> ciams.append(
//                            getPermPre(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
//                    ));
//            pc.sendMessage(new EmbedBuilder().setColor(new Color(0, 255, 126)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + STATICS.CMDTYPE.settings + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(STATICS.CMDTYPE.settings))
                    .forEach(s1 -> ciams.append(
                            getPermPre(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(255, 233, 0)).setDescription(ciams.toString()).build()).queue();

        } catch (Exception e) {
            e.printStackTrace();
        }


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
        return "Получение этой справки";
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
