package commands.administration;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class Update2 {
    public static void manualCheck2(TextChannel channel) throws IOException {

        channel.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription("Загрузка обновления...").build()).queue();

        File targetFile = null;
        String paramStr = null;
        if (System.getProperty("os.name").toLowerCase().contains("linux"))
        {
            targetFile = new File("JDWTBot.jar");
            paramStr = "screen, -dmLS, JDWTBot, java, -jar," + targetFile.getName() + ",update";

            //Runtime.getRuntime().addShutdownHook(new RunNewInstanceHook("screen", "-dmLS", "JDWTBot", "java", "-jar", targetFile.getName(), "update"));
        }
        else {
            targetFile = new File("JDWTBot-new.jar");
            paramStr = "java, -jar," + targetFile.getName() + ",update";

            //Runtime.getRuntime().addShutdownHook(new RunNewInstanceHook("java", "-jar", targetFile.getName(), "update"));
        }

        List<String> paramList = Arrays.asList(paramStr.split("\\s*,\\s*"));

//        JarExecutor jarExe = new JarExecutor();
//        try {
//            jarExe.executeJar(targetFile.getName(), paramList);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        InputStream initialStream = new URL("https://github.com/sakhalines/JDWTBot/releases/download/1.0/JDWTBot.jar").openStream();

        java.nio.file.Files.copy(
                initialStream,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        initialStream.close();

        channel.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription("Обновление загружено. Перезапуск...").build()).queue();

        Restart.restart("update", targetFile.getName());

//        System.exit(0);
    }
}
