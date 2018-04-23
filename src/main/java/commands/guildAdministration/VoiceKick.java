package commands.guildAdministration;


import commands.Command;
import core.SSSS;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.MSGS;
import utils.STATICS;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.List;

public class VoiceKick implements Command {

    public static HashMap<Member, VoiceChannel> kicks = new HashMap<>();

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if (core.Perms.check(1, event)) return;

        if (args[0].toLowerCase().equals("channel")) {
            if (args.length > 1) {

                StringBuilder sb = new StringBuilder();
                Arrays.stream(args).skip(1).forEach(s -> sb.append(" " + s));
                String vc = sb.toString().substring(1);
                if (event.getGuild().getVoiceChannelsByName(vc, true).size() > 0) {
                    SSSS.setVKICKCHANNEL(vc, event.getGuild());
                    event.getChannel().sendMessage(MSGS.success().setDescription("Successfully set voice kick channel to `" + vc + "`").build()).queue();
                    return;
                } else {
                    event.getChannel().sendMessage(MSGS.error().setDescription("Please enter a valid voice channel existing on this guild.").build()).queue();
                    return;
                }

            } else {
                event.getChannel().sendMessage(MSGS.error().setDescription("Please enter a valid voice channel existing on this guild.").build()).queue();
                return;
            }
        }

        if (event.getMessage().getMentionedUsers().size() > 0) {

            int timeout;
            try {
                StringBuilder sb = new StringBuilder();
                Arrays.stream(args).forEach(s -> sb.append(" " + s));
                timeout = Integer.parseInt(sb.toString().substring(1).replace("@" + event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)).getEffectiveName(), "").replaceAll(" ", ""));
            } catch (Exception e) {
                timeout = 0;
            }

            List<VoiceChannel> vc = event.getGuild().getVoiceChannelsByName(SSSS.getVKICKCHANNEL(event.getGuild()), true);

            if (vc.size() == 0) {
                event.getChannel().sendMessage(MSGS.error().setDescription("There is no voice channel set or it is no more existent on this guild.\nPlease set a valid voice channel with `-vkick channel <channel name>`.").build()).queue();
                return;
            }

            Member victim = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
            VoiceChannel current = event.getMember().getVoiceState().getChannel();
            event.getGuild().getController().moveVoiceMember(victim, vc.get(0)).queue();

            if (timeout > 0) {

                kicks.put(victim, current);

                event.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(new Color(0xFF0036))
                    .setDescription(":boot:  " + event.getAuthor().getAsMention() + " (*" + event.getMember().getRoles().get(0).getName() + "*) kicked " + victim.getAsMention() +
                            " out of the voice channel `" + current.getName() + "` with a rejoin timeout of **" + timeout + " seconds**.")
                    .build()
                ).queue();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        kicks.remove(event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)));
                        victim.getUser().openPrivateChannel().complete().sendMessage("Your voice kick timeout has expired. You can now rejoin the channel `" + current.getName() + "`.").queue();
                    }
                }, timeout * 1000);
            } else {
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(new Color(0xFF0036))
                        .setDescription(":boot:  " + event.getAuthor().getAsMention() + " (*" + event.getMember().getRoles().get(0).getName() + "*) kicked " + victim.getAsMention() +
                                " out of the voice channel `" + current.getName() + "`.")
                        .build()
                ).queue();
            }

        }


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "Использование: **" + STATICS.PREFIX +
                "vkick <@user> (<таймаут в секундах>)**  -  `Выгнать пользователя с вашего голосового канала (пользоатель не сможет присоединится до истечения таймаута)`\n" +
                "** " + STATICS.PREFIX + "vkick channel <название канала>**  -  `Установить голосовой канал для изгнанных пользователей`";
    }

    @Override
    public String description() {
        return "Выгнать пользователя с вашего голосового канала";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.guildadmin;
    }

    @Override
    public int permission() {
        return 3;
    }
}
