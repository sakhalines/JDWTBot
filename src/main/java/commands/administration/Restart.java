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
import java.util.Timer;
import java.util.TimerTask;

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

        if (!Perms.isOwner(event.getAuthor(), event.getChannel())) return;

        event.getChannel().sendMessage(":warning:  Бот перезапускается...").queue();
        restart("restart", new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName());
    }

    public static void restart(String restartOrUpdateParam, String fileName) throws IOException {
        if  (System.getProperty("os.name").toLowerCase().contains("linux")){
            Runtime.getRuntime().exec("screen -dmLS JDWTBot java -jar " + fileName + " " + restartOrUpdateParam);
        }
        else {
            System.out.println("Запускается " + fileName);
            Runtime.getRuntime().exec("java -jar " + fileName + " " + restartOrUpdateParam);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Ожидание...");
            }
        }, 2000);

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

                    try {
                        origFile.delete();
                       } catch (Exception e) {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                System.out.println("Ожидание доступности файла " + origFile.delete());
                                origFile.delete();
                            }
                        }, 2000, 1000);
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
           try {
               newFile.delete();
           } catch (Exception e) {
               new Timer().schedule(new TimerTask() {
                   @Override
                   public void run() {
                       System.out.println("Ожидание доступности файла " + newFile.getName());
                       newFile.delete();
                   }
                   }, 2000, 1000);
                }
        }
        return false;
    }
    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "Использование: " + STATICS.PREFIX + "restart";
    }

    @Override
    public String description() {
        return "Перезагрузить бота";
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
