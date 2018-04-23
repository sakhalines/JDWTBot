package commands.chat;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by zekro on 09.08.2017 / 17:41
 * DiscordBot.commands.chat
 * dev.zekro.de - github.zekro.de
 * © zekro 2017
 */

public class Count implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        String content = String.join(" ", args);

        if (content.isEmpty()) {

        }

        int chars = content.length();
        int chars_only = content.replace(" ", "").length();
        int words = content.split(" ").length;
        int sentences = content.split("[.!?]").length;

        event.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.green)
                .setTitle("String Analyzer", null)
                .setDescription("```" + content + "```")
                .addField("", "Символы:\nСимволы (w/o spaces):\nСлова:\nПредложения:", true)
                .addField("", String.format("`%s`\n`%s`\n`%s`\n`%s`", chars, chars_only, words, sentences), true)
                .build()
        ).queue(message -> event.getMessage().delete().queue());

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "Использование:\n`.count <строка>`";
    }

    @Override
    public String description() {
        return "Подсчет символов, слов и предложений из входной строки";
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
