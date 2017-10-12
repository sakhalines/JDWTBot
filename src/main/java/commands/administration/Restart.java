package commands.administration;

import commands.Command;
import core.Main;
import core.Perms;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;

/**
 * Created by zekro on 24.03.2017 / 19:49
 * DiscordBot / commands.administration
 * © zekro 2017
 */


public class Restart implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if (!Perms.isOwner(event.getAuthor(), event.getTextChannel())) return;

        event.getTextChannel().sendMessage(":warning:  Бот перезапускается...").queue();
        restart("restart", new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName());
    }

    public static void restart(String restartOrUpdateParam, String fileName) throws IOException {
        if (System.getProperty("os.name").toLowerCase().contains("linux"))
            Runtime.getRuntime().exec("screen -dmLS JDWTBot java -jar " + fileName + " " + restartOrUpdateParam);
        else
            Runtime.getRuntime().exec("java -jar " + fileName + " " + restartOrUpdateParam);

        System.exit(0);
    }

    public static boolean restart2() throws IOException {
        File currentFile = new  File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()),
                newFile = new File("JDWTBot-new.jar"),
                origFile = new File("JDWTBot.jar");

        if (currentFile.getName().contains("classes")) // если запускаем из среды (не jar файлом)
            currentFile = origFile;

        System.out.println(currentFile.getName());
        if (!currentFile.getName().equalsIgnoreCase(origFile.getName())){
            if (origFile.exists()){
                System.out.println("delete " + origFile.getName());
                while (true) {
                    try {
                        origFile.delete();
                        break;
                    } catch (Exception e) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }


                if (!origFile.exists())
                    System.out.println("deleted");

                Files.copy(currentFile.toPath(), origFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                restart("update", origFile.getName());
//                Runtime.getRuntime().addShutdownHook(new RunNewInstanceHook("java", "-jar", origFile.getName(), "update"));
//                System.exit(0);
                return true;
            }
        }
        else if (newFile.exists()){
            newFile.delete();
        }
        return false;
    }
    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "Использование: -restart";
    }

    @Override
    public String description() {
        return "Restart the bot.";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.administration;
    }

    @Override
    public int permission() {
        return 4;
    }
}
