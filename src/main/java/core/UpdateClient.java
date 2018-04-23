package core;

import commands.administration.Restart;
import listeners.ReadyListener;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.STATICS;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by zekro on 22.03.2017 / 15:59
 * DiscordBot / core
 * © zekro 2017
 */


public class UpdateClient {

    private static String lastUpdate = "";

    private static final String API_URL = "https://api.github.com/repos/sakhalines/JDWTBot/releases";
    private static final String DOWNLOAD_URL = "https://github.com/sakhalines/JDWTBot/releases/download/";
    public static final Release PRE = new Release(getRelease(true));
    public static final Release STABLE = new Release(getRelease(false));

    public static class Release {

        public String tag;
        private String url;

        private Release(JSONObject object) {
                tag = object.getString("tag_name");
            url = object.getString("html_url");
        }

    }

    private static JSONObject getRelease(boolean prerelease) {

        Scanner sc;
        try {
            sc = new Scanner(new URL(API_URL).openStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder output = new StringBuilder();
        sc.forEachRemaining(output::append);

        JSONArray jsonarray = new JSONArray(output.toString());

        List<JSONObject> jsonobs = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++)
            jsonobs.add(jsonarray.getJSONObject(i));

        return jsonobs.stream().filter(o -> {
            try {
                return prerelease == o.getBoolean("prerelease");
            } catch (JSONException e) {
                return false;
            }
        }).findFirst().orElse(null);

    }

    public static void manualCheck(MessageChannel channel) {

        if (isUdate())
            sendUpdateMsg(channel);
        else
            channel.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription("Обновлений бота нет.").build()).queue();

    }


    public static void update(MessageChannel channel, boolean stable) throws IOException {
        if (isUdate()) {
            channel.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription("Загрузка обновления...").build()).queue();

            File targetFile = null;
//        String paramStr = null;
            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                targetFile = new File("JDWTBot.jar");
//            paramStr = "screen, -dmLS, JDWTBot, java, -jar," + targetFile.getName() + ",update";

                //Runtime.getRuntime().addShutdownHook(new RunNewInstanceHook("screen", "-dmLS", "JDWTBot", "java", "-jar", targetFile.getName(), "update"));
            } else {
                targetFile = new File("JDWTBot-new.jar");
//            paramStr = "java, -jar," + targetFile.getName() + ",update";

                //Runtime.getRuntime().addShutdownHook(new RunNewInstanceHook("java", "-jar", targetFile.getName(), "update"));
            }

//        List<String> paramList = Arrays.asList(paramStr.split("\\s*,\\s*"));

//        JarExecutor jarExe = new JarExecutor();
//        try {
//            jarExe.executeJar(targetFile.getName(), paramList);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        InputStream initialStream = new URL("https://github.com/sakhalines/JDWTBot/releases/download/1.0/JDWTBot.jar").openStream();

            if (!targetFile.exists()) {
                InputStream initialStream = null;
                if (stable)
                    initialStream = new URL(DOWNLOAD_URL + STABLE.tag + "/JDWTBot.jar").openStream();
                else
                    initialStream = new URL(DOWNLOAD_URL + PRE.tag + "/JDWTBot.jar").openStream();

                java.nio.file.Files.copy(
                        initialStream,
                        targetFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                initialStream.close();
            }

            channel.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription("Обновление загружено.\nОжидайте личное сообщение об успешном завершении обновления.\nПерезапуск...").build()).queue();

            Restart.restart("update", targetFile.getName());
        }
        else
            channel.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription("Обновлений бота нет.").build()).queue();

    }


    private static boolean isUdate() {
        return !PRE.tag.equals(STATICS.VERSION);
        //return !STABLE.tag.equals(STATICS.VERSION);
    }

    private static void sendUpdateMsg(Object channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.cyan)
                .setTitle("Доступно обновление!")
                .setDescription("Используйте команду `.checkupdate stable` или `.checkupdate pre` для обновления на стабильную или предварительную версию соответственно." +
                        "\nПодробнее `.help checkupdate`" +
                        "\n\n**Текущая версия:  ** `" + STATICS.VERSION + "`")
                .addField("Последняя предварительная версия", String.format(
                        "Версия:  `%s`\n" +
                                "Download & Changelogs:  [GitHub Release](%s)\n", PRE.tag, PRE.url
                ), false)
                .addField("Последняя стабильная версия", String.format(
                        "Версия:  `%s`\n" +
                                "Download & Changelogs:  [GitHub Release](%s)\n", STABLE.tag, STABLE.url
                ), false)
                .setFooter("Введите '.disable' чтобы отключить это сообщение об обновлении.", null);

        ReadyListener.sendMsg(channel, eb);


    }

    public static void checkIfUpdate(JDA jda) {

        if (new File("SERVER_SETTINGS/no_update_info").exists())
            return;

        if (STATICS.BOT_OWNER_ID != 0 && isUdate()) {
            jda.getUserById(STATICS.BOT_OWNER_ID).openPrivateChannel().queue(c -> sendUpdateMsg(c));
        }
    }
}
