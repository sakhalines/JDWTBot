package core;

import listeners.ReadyListener;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.STATICS;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    private static final Release PRE = new Release(getRelease(true));
    private static final Release STABLE = new Release(getRelease(false));

    static class Release {

        private String tag;
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
//    public static void manualCheck2(TextChannel channel) throws IOException {
//
//        InputStream initialStream = new URL("https://github.com/sakhalines/JDWTBot/releases/download/1.0/JDWTBot.jar").openStream();
//        File targetFile = new File("JDWTBot1.jar");
//
//        java.nio.file.Files.copy(
//                initialStream,
//                targetFile.toPath(),
//                StandardCopyOption.REPLACE_EXISTING);
//        initialStream.close();
//
//    }

    public static void manualCheck(TextChannel channel) {

        if (isUdate())
            sendUpdateMsg(channel);
        else
            channel.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription("Your bor version is up to date!").build()).queue();

    }


    private static boolean isUdate() {
        return !PRE.tag.equals(STATICS.VERSION);
    }

    private static void sendUpdateMsg(Object channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.cyan)
                .setTitle("Доступно обновление!")
                .setDescription("Download the latest version and install it manually on your vServer.\n\n**Current version:  ** `" + STATICS.VERSION + "`")
                .addField("Latest Pre Release Build", String.format(
                        "Version:  `%s`\n" +
                                "Download & Changelogs:  [GitHub Release](%s)\n", PRE.tag, PRE.url
                ), false)
                .addField("Latest Stable Release Build", String.format(
                        "Version:  `%s`\n" +
                                "Download & Changelogs:  [GitHub Release](%s)\n", STABLE.tag, STABLE.url
                ), false)
                .setFooter("Enter '-disable' to disable this message on new updates.", null);

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
