package core;


import com.moandjiezana.toml.Toml;
import utils.STATICS;

import java.io.*;

/**
 * © zekro 2017
 *
 * @author zekro
 */

public class SettingsCore {


    private static File sfile = new File("SETTINGS.txt");
    private static Toml toml;

    public static class SCONT {
        static final String TOKEN = "TOKEN";
        static final String PREFIX = "CMD_PREFIX";
        static final String CUSTOM_MESSAGE = "CUSTOM_PLAYING_MESSAGE";
        static final String PERMISSION_ROLES = "PERMISSION_ROLES";
        static final String COMMAND_CONSOLE_OUTPUT = "COMMAND_CONSOLE_OUTPUT";
        static final String KICK_VOICE_CHANNEL = "KICK_VOICE_CHANNEL";
        static final String UPDATE_INFO = "UPDATE_INFO";
        static final String GAME_CHANGELOG_UPDATE_INTERVAL = "GAME_CHANGELOG_UPDATE_INTERVAL";
        static final String GAME_CHANGELOG_CHANNEL_PREFIX = "GAME_CHANGELOG_CHANNEL_PREFIX";
        static final String GAME_NEWS_UPDATE_INTERVAL = "GAME_NEWS_UPDATE_INTERVAL";
        static final String GAME_NEWS_CHANNEL_PREFIX = "GAME_NEWS_CHANNEL_PREFIX";
        static final String GUILD_JOIN_ROLE = "GUILD_JOIN_ROLE";
        static final String DISCORD_JOIN_MESSAGE = "DISCORD_JOIN_MESSAGE";
        static final String FULL_PERMISSION_ROLES = "FULL_PERMISSION_ROLES";
        static final String MEMBER_PERMISSION_ROLES = "MEMBER_PERMISSION_ROLES";
        static final String BOT_OWNER_ID = "BOT_OWNER_ID";
    }

    public static boolean testForToken() {
        return (toml.getString(SCONT.TOKEN).length() > 0);
    }

    public static void loadSettings() throws IOException {

        if (!sfile.exists()) {

            BufferedWriter br = new BufferedWriter(new FileWriter(sfile));

            br.write(

                        "#########################\n" +
                            "# SETTINGS FILE FOR BOT #\n" +
                            "#  PLEASE DON'T DELETE  #\n" +
                            "#########################\n" +
                            "\n" +
                            "# GENERAL SERVER SETTINGS #\n" +
                            "\n" +
                            "    # Enter here your Discord API Token you'll get from here: https://discordapp.com/developers/applications/me\n" +
                            "        TOKEN = \"\"\n" +
                            "    # Prefix to send bot commands (like -help or ~help or .help, what ever you want)\n" +
                            "        CMD_PREFIX = \".\"\n" +
                            "    # Bot owners user ID\n" +
                            "        BOT_OWNER_ID = 0\n" +
                            "    # Custom message shown as \"Now Playing: ...\" text\n" +
                            "        CUSTOM_PLAYING_MESSAGE = \" \".help\" для справки\"\n" +
                            "    # Log entered command in console of the bot\n" +
                            "        COMMAND_CONSOLE_OUTPUT = true\n" +
                            "    # Automatically check for updates and inform you if there is a new update available\n" +
                            "        UPDATE_INFO = false\n" +
                            "\n" +
                            "# PERMISSION SETTINGS #\n" +
                            "\n" +
                            "    # команды доступные участникам\n" +
                            "       MEMBER_PERMISSION_ROLES = \"server admin, moder, admin, member, member+\"\n" +
                            "    # List roles that can use Moderator+ Commands\n" +
                            "        PERMISSION_ROLES = \"server admin, moder, admin\"\n" +
                            "    # Super Permission roles\n" +
                            "        FULL_PERMISSION_ROLES = \"server admin\"\n" +
                            "\n" +
                            "# CHANNEL SETTINGS #\n" +
                            "\n" +
                            "    # префикс для канала, в котором будут публиковаться обновления игры War Thunder. После префикса автоматически будет добавляться дата последнего обновления\n" +
                            "        GAME_CHANGELOG_CHANNEL_PREFIX = \"game_changelog_\"\n" +
                            "    # частота проверки информации об обновлении игры War Thunder в минутах, или \"OFF\" для отключения мониторинга\n" +
                            "       GAME_CHANGELOG_UPDATE_INTERVAL = \"OFF\"\n" +
                            "    # префикс для канала, в котором будут публиковаться новости игры War Thunder. После префикса автоматически будет добавляться дата последнего обновления\n" +
                            "        GAME_NEWS_CHANNEL_PREFIX = \"game_news_\"\n" +
                            "    # частота проверки информации об обновлении новостей игры War Thunder в минутах, или \"OFF\", для отключения мониторинга\n" +
                            "        GAME_NEWS_UPDATE_INTERVAL = \"OFF\"\n" +
                            "    # Alternative voice channel vor vkicks\n" +
                            "        KICK_VOICE_CHANNEL = \"Lobby\"\n" +
                            "\n" +
                            "# MESSAGE SETTINGS #\n" +
                            "\n" +
                            "       # Message, that appears, if user joins discord first time\n" +
                            "       # \"[USER]\" stand for the @user - mention\n" +
                            "       # \"[GUILD]\" stand for the guild name\n" +
                            "           DISCORD_JOIN_MESSAGE = \":heart: Привет, [USER]! Добро пожаловать на сервер [GUILD]! :heart:\"\n"

            );

            br.close();


        } else {

            toml = new Toml().read(new FileInputStream(sfile));

            STATICS.TOKEN = toml.getString(SCONT.TOKEN);
            STATICS.PREFIX = toml.getString(SCONT.PREFIX);
            STATICS.CUSTOM_MESSAGE = toml.getString(SCONT.CUSTOM_MESSAGE);
            STATICS.commandConsoleOutout = toml.getBoolean(SCONT.COMMAND_CONSOLE_OUTPUT);
            STATICS.KICK_VOICE_CHANNEL = toml.getString(SCONT.KICK_VOICE_CHANNEL);
            STATICS.autoUpdate = toml.getBoolean(SCONT.UPDATE_INFO);
            STATICS.gameChangelogChannelPrefix = toml.getString(SCONT.GAME_CHANGELOG_CHANNEL_PREFIX);
            STATICS.gameChangelogUpdateInterval = toml.getString(SCONT.GAME_CHANGELOG_UPDATE_INTERVAL);
            STATICS.gameNewsChannelPrefix = toml.getString(SCONT.GAME_NEWS_CHANNEL_PREFIX);
            STATICS.gameNewsUpdateInterval = toml.getString(SCONT.GAME_NEWS_UPDATE_INTERVAL);
            STATICS.guildJoinRole = toml.getString(SCONT.GUILD_JOIN_ROLE);
            STATICS.discordJoinMessage = toml.getString(SCONT.DISCORD_JOIN_MESSAGE);
            STATICS.MEMBERPERMS = toml.getString(SCONT.MEMBER_PERMISSION_ROLES).split(", ");
            STATICS.PERMS = toml.getString(SCONT.PERMISSION_ROLES).split(", ");
            STATICS.FULLPERMS = toml.getString(SCONT.FULL_PERMISSION_ROLES).split(", ");
            STATICS.BOT_OWNER_ID = toml.getLong(SCONT.BOT_OWNER_ID);
        }
    }

}
