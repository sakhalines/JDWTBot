package commands.administration;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import org.apache.commons.lang3.SystemUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.StandardCopyOption;

public class Update2 {
    public static void manualCheck2(TextChannel channel) throws IOException {

        channel.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription("Загрузка обновления...").build()).queue();

        InputStream initialStream = new URL("https://github.com/sakhalines/JDWTBot/releases/download/1.0/JDWTBot.jar").openStream();
        File targetFile = new File("JDWTBot.jar");

        java.nio.file.Files.copy(
                initialStream,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        initialStream.close();

        channel.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription("Обновление загружено. Перезапуск...").build()).queue();

        if (SystemUtils.IS_OS_WINDOWS)
            Runtime.getRuntime().addShutdownHook(new RunNewInstanceHook("java", "-jar", "JDWTBot.jar", "-update"));
        else
            Runtime.getRuntime().addShutdownHook(new RunNewInstanceHook("screen", "-L", "-S", "JDWTBot", "java", "-jar", "JDWTBot.jar", "-update"));

        System.exit(0);
    }
}
