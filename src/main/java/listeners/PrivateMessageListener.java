package listeners;

import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * © zekro 2017
 *
 * @author zekro
 */

public class PrivateMessageListener extends ListenerAdapter {

    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {

//        if (event.getMessage().getContentDisplay().startsWith("token_"))
//            return;
//
//        if (event.getMessage().getContentDisplay().equalsIgnoreCase(".checkupdate pre")) {
//            //UpdateClient.update();
//        }
//
//        if (event.getMessage().getContentDisplay().equalsIgnoreCase(".checkupdate stable")) {
//
//        }

//        if (event.getMessage().getContentDisplay().equalsIgnoreCase(".disable")) {
//
//            try {
//                new File("SERVER_SETTINGS/no_update_info").createNewFile();
//                event.getChannel().sendMessage(new EmbedBuilder()
//                        .setColor(Color.red)
//                        .setDescription("Вы отключили сообщение об обновлении.\n" +
//                                        "Теперь Вы не будете уведомлятся о новых обновлениях бота.")
//                        .setFooter("Для включения используйте '.enable'.", null)
//                        .build()).queue();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return;
//        }
//
//        if (event.getMessage().getContentDisplay().equalsIgnoreCase(".enable")) {
//
//
//            File f = new File("SERVER_SETTINGS/no_update_info");
//            if (f.exists())
//                f.delete();
//
//            event.getChannel().sendMessage(new EmbedBuilder()
//                    .setColor(Color.green)
//                    .setDescription("Вы отключили сообщение об обновлении.")
//                    .setFooter("Для отключения используйте  '.disable'.", null)
//                    .build()).queue();
//
//            return;
//        }
//
//
//        String[] answers = {
//                "Hey, " + event.getAuthor().getName() + "! What's going on?",
//                "That's nice!",
//                "Good job!",
//                "I love it so much to eat cookies all day long...",
//                "I'm bored.",
//                "You are so smart, " + event.getAuthor().getName() + "!",
//                "You smell really good, you know? ^^",
//                "Tell me more, Senpai.",
//                "The weather here is quite nice.",
//                "lol",
//                "lel",
//                "xD",
//                ":^)",
//                "Did you had a nice day? ^^",
//                "I'm talking shit lul",
//                "Please get me out of that box... o.o",
//                "My real name is Thomas, but please don't tell it someone else... :)"
//        };
//
//        try {
//
//            if (!event.getAuthor().equals(event.getJDA().getSelfUser())) {
//
//                Random rand = new Random();
//                PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
//                if (event.getMessage().getContentDisplay().toLowerCase().contains("hey") || event.getMessage().getContentDisplay().toLowerCase().contains("hello")) {
//                    pc.sendTyping().queue();
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            pc.sendMessage("Hey, " + event.getAuthor().getName() + "! What's going on?").queue();
//                        }
//                    }, 1000);
//                } else {
//                    pc.sendTyping().queue();
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            pc.sendMessage(answers[rand.nextInt(answers.length)]).queue();
//                        }
//                    }, 1000);
//                }
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
