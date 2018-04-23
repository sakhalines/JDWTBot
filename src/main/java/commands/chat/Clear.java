package commands.chat;

import commands.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.MSGS;
import utils.STATICS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zekro on 18.03.2017 / 01:29
 * DiscordBot / commands
 * © zekro 2017
 *
 * Contributor: Skillkiller
 */



public class Clear implements Command {

    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    public static String HELP = "Использование: ` ~clear <amount ob messages (>2) || all || TIMESTAMP>  to clear an amount of chat messages`";


    private int getInt(String arg) {

        try {
            return Integer.parseInt(arg);
        } catch (Exception e) {
            return 0;
        }

    }

    public void action(String[] args, MessageReceivedEvent event) {

        if (core.Perms.check(1, event)) return;

        try {
            MessageHistory history = new MessageHistory(event.getTextChannel());
            List<Message> msgs;
            if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
                try {
                    while (true) {
                        msgs = history.retrievePast(1).complete();
                        if (!msgs.get(0).isPinned()) msgs.get(0).delete().queue();
                    }
                } catch (Exception ex) {
                    //Nichts tun
                }

                Message answer = event.getChannel().sendMessage(MSGS.success().setDescription(
                        "Удалены все сообщения кроме закреплённых"
                ).build()).complete();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        answer.delete().queue();
                    }
                }, 3000);

            }else if (args.length < 1 || (args.length > 0 ? getInt(args[0]) : 1) == 1 && (args.length > 0 ? getInt(args[0]) : 1) < 2) {

                event.getMessage().delete().queue();
                msgs = history.retrievePast(2).complete();
                if (!msgs.get(0).isPinned()) msgs.get(0).delete().queue();

                Message answer = event.getChannel().sendMessage(MSGS.success().setDescription(
                        "Последнее сообщение удалено"
                ).build()).complete();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        answer.delete().queue();
                    }
                }, 3000);

            } else if(args.length == 2) {
                // 24/03/2013 21:54
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                StringBuilder builder = new StringBuilder();

                for (String arg: args) {
                    builder.append(" " + arg);
                }

                try
                {
                    Date date = simpleDateFormat.parse(builder.toString());

                    boolean weiter = true;
                    try {
                        while (weiter) {
                            msgs = history.retrievePast(1).complete();
                            if (date.before(Date.from(msgs.get(0).getCreationTime().toZonedDateTime().toInstant()))) {
                                if (!msgs.get(0).isPinned()) msgs.get(0).delete().queue();
                            } else {
                                weiter = false;
                            }

                        }

                        Message answer = event.getChannel().sendMessage(MSGS.success().setDescription(
                                "Удалено сообщений " + args[0]
                        ).build()).complete();

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                answer.delete().queue();
                            }
                        }, 3000);
                    } catch (Exception ex) {
                        //Nichts tun
                    }
                }
                catch (ParseException ex)
                {
                    event.getChannel().sendMessage(MSGS.error()
                            .addField("Error Type", "Не верный формат времени.", false)
                            .addField("Description", "Введите время в правильном формате, например:\n" + simpleDateFormat.format(new Date()), false)
                            .build()
                    ).queue();
                }

            } else if (getInt(args[0]) <= 100) {

                event.getMessage().delete().queue();
                msgs = history.retrievePast(getInt(args[0])).complete();
                event.getTextChannel().deleteMessages(msgs).queue();

                Message answer = event.getChannel().sendMessage(MSGS.success().setDescription(
                        "Сообщений удалено: **" + args[0] + "**!"
                ).build()).complete();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        answer.delete().queue();
                    }
                }, 3000);
            } else {
                event.getChannel().sendMessage(MSGS.error()
                        .addField("Тип ошибки", "Значение сообщения за пределами границ.", false)
                        .addField("Описание", "Максимальное количество удаляемых сообщений недолжно превышать 100!", false)
                        .build()
                ).queue();
            }


        } catch (Exception e) {
            event.getChannel().sendMessage(MSGS.error()
                    .addField("Тип ошибки", e.getLocalizedMessage(), false)
                    .addField("Сообщение", e.getMessage(), false)
                    .build()
            ).queue();
        }

    }

    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public String help() {
        return HELP;
    }

    @Override
    public String description() {
        return "Массовое удаление сообщений";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.chatutils;
    }

    @Override
    public int permission() {
        return 2;
    }
}
