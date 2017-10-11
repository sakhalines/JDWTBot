package utils;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import java.util.ArrayList;
import java.util.Date;

public class STATICS {

    public static String TOKEN = "";

    //######### GENERAL BOT SETTINGS #########//


    public static String VERSION = "1.0";
    public static String THISBUILD = BUILDTYPE.STABLE;

    public static class BUILDTYPE {
        public static final String STABLE = "STABLE";
        public static final String UNSTABLE = "UNSTABLE";
        public static final String UNTESTED = "UNTESTED";
        public static final String TESTING_BUILD = "TESTING_BUILD";
    }

    public static String PREFIX = ".";

    public static OnlineStatus STATUS = OnlineStatus.ONLINE;

    public static String CUSTOM_MESSAGE = " | .help для справки";

    public static Game GAME = new Game("")  {
        public String getName() {
            return CUSTOM_MESSAGE + " | -help | v." + VERSION + "_" + THISBUILD;
        }
        public String getUrl() {
            return "http://zekro.de";
        }
        public GameType getType() {
            return GameType.DEFAULT;
        }
    };


    //######### PERMISSION SETTINGS #########//

    public static String[] MEMBERPERMS = {"member", "member+", "server admin", "moder", "admin"};
    public static String[] PERMS = {"server admin", "moder", "admin"};
    public static String[] FULLPERMS = {"server admin"};

    public static String guildJoinRole = "";


    //########## GOOGLE DOCS ID'S ##########//

    //public static String DOCID_jokes = "1fWHPIrZKHSXBsF5SWO3ZEHmecItVppYvM39pm7Rvssk";

    public static String DOCID_jokes = "1f4ErJmnZFdhxNXUCFBjQN5P867Sihpx9P6vOREqmJU8";

    //########### OTHER SETTINGS ###########//

    public static String voiceLogChannel = "voicelog";

    public static String gameChangelogChannelPrefix = "game_changelog_";

    public static String gameChangelogUpdateInterval = "OFF";

    public static boolean commandConsoleOutout = true;

    public static String KICK_VOICE_CHANNEL = "";


    public static boolean autoUpdate = true;

    public static String input;

    public static String discordJoinMessage = ":heart: Hey, [USER]! Welcome on the [GUILD]! :heart:";

    public class CMDTYPE {
        public static final String administration = "Administration";
        public static final String chatutils = "Chat Utilities";
        public static final String essentials = "Essentials";
        public static final String etc = "Etc";
        public static final String music = "Music";
        public static final String guildadmin = "Guild Administration";
        public static final String settings = "SettingsCore";
        public static final String thunderTools = "ThunderTools";
    }

    public static Date lastRestart;

    public static int reconnectCount = 0;

    public static ArrayList<ArrayList<String>> cmdLog = new ArrayList<>();

    public static long BOT_OWNER_ID = 0;

    public static int SERVER_LIMIT = 250;

}
