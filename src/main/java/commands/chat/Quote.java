package commands.chat;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.MSGS;
import utils.STATICS;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zekro on 05.06.2017 / 10:17
 * DiscordBot/commands.chat
 * © zekro 2017
 */

public class Quote implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if (args.length < 1) {
            event.getChannel().sendMessage(MSGS.error().setDescription(help()).build()).queue();
            return;
        }

        event.getMessage().delete().queue();

        Message chanMSG = event.getChannel().sendMessage(new EmbedBuilder().setDescription("Searching for message in text channels...").build()).complete();

        List<Message> msg = new ArrayList<>();
        event.getGuild().getTextChannels().forEach(c -> {
            try {
                msg.add(c.getMessageById(args[0]).complete());
            } catch (Exception e) {}
        });

        if (msg.size() < 1) {
            chanMSG.editMessage(MSGS.error().setDescription(
                    "На этом сервере нет сообщений с таким ID `" + args[0] + "`."
            ).build()).queue();
            return;
        }

        chanMSG.editMessage(new EmbedBuilder()
                .setAuthor(msg.get(0).getAuthor().getName(), null, msg.get(0).getAuthor().getAvatarUrl())
                .setDescription(msg.get(0).getContentDisplay())
                .setFooter(
                        msg.get(0).getCreationTime().getDayOfMonth() + ". " +
                                msg.get(0).getCreationTime().getMonth() + " " +
                                msg.get(0).getCreationTime().getYear() +
                                " at " + msg.get(0).getCreationTime().getHour() + ":" +
                                msg.get(0).getCreationTime().getMinute() + ":" +
                                msg.get(0).getCreationTime().getSecond() +
                                " в канале #" + msg.get(0).getTextChannel().getName(),
                        null)
                .build()
        ).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "**Использование:** `"+STATICS.PREFIX+"quote <msg id>`";
    }

    @Override
    public String description() {
        return "Цитирование сообщения из любого канала на сервере.";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.chatutils;
    }

    @Override
    public int permission() {
        return 1;
    }
}
