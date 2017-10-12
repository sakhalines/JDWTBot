package core;

import commands.Command;
import commands.administration.*;
import commands.chat.*;
import commands.essentials.*;
import commands.etc.*;
import commands.guildAdministration.*;
import commands.settings.*;
import commands.thunderTools.ChangeLog;
import commands.thunderTools.Skill;
import listeners.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import utils.STATICS;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class Main {

    static JDABuilder builder;

    public static final CommandParser parser = new CommandParser();

    public static HashMap<String, Command> commands = new HashMap<>();

    public static JDA jda;

    public static void main(String[] args) throws IOException {
//        if (!System.getProperty("os.name").toLowerCase().contains("linux"))
//            Restart.restart2();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Ожидание...");
            }
        }, 2000);

        if (!System.getProperty("os.name").toLowerCase().contains("linux")) {
            if (Restart.restart2()) // если запускается JDWTBot-new.jar
                System.exit(0);
        }


        StartArgumentHandler.args = args;

        SettingsCore.loadSettings();

        BotStats.load();

//        if (!new File("WILDCARDS.txt").exists())
//            ServerLimitListener.createTokenList(50);


        try {
            if (!SettingsCore.testForToken()) {
                System.out.println("[ОШИБКА] ПОЖАЛУЙСТА ВВЕДИТЕ ВАШ DISCORD API TOKEN ИЗ 'https://discordapp.com/developers/applications/me' В ТЕКСТОВЫЙ ФАЙЛ 'SETTINGS.txt' И ПЕРЕЗАПУСТИТЕ!");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("[ОШИБКА] ПОЖАЛУЙСТА ВВЕДИТЕ ВАШ DISCORD API TOKEN ИЗ 'https://discordapp.com/developers/applications/me' В ТЕКСТОВЫЙ ФАЙЛ 'SETTINGS.txt' И ПЕРЕЗАПУСТИТЕ!");
            System.exit(0);
        }

        builder = new JDABuilder(AccountType.BOT)
                .setToken(STATICS.TOKEN)
                .setAudioEnabled(true)
                .setAutoReconnect(true)
                .setStatus(STATICS.STATUS)
                .setGame(STATICS.GAME);

        initializeListeners();
        initializeCommands();

        try {
            builder.buildBlocking();
        } catch (InterruptedException | RateLimitedException | LoginException e) {
            e.printStackTrace();
        }

    }

    private static void initializeCommands() {
        commands.put("chlog", new ChangeLog());
        commands.put("skill", new Skill());
        commands.put("ping", new Ping());
        commands.put("clear", new Clear());
        commands.put("purge", new Clear());
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("test", new TestCMD());
        commands.put("say", new Say());
        commands.put("stats", new Stats());
        commands.put("userinfo", new UserInfo());
        commands.put("user", new UserInfo());
        commands.put("stups", new Stups());
        commands.put("checkupdate", new CheckUpdate());
        commands.put("chkupd", new CheckUpdate());
        commands.put("restart", new Restart());
        commands.put("kick", new Kick());
        commands.put("vkick", new VoiceKick());
        commands.put("dev", new Dev());
        commands.put("stop", new Stop());
        commands.put("moveall", new Moveall());
        commands.put("mvall", new Moveall());
        commands.put("uptime", new Uptime());
        commands.put("botmsg", new Botmessage());
        commands.put("prefix", new Prefix());
        commands.put("joinmsg", new ServerJoinMessage());
        commands.put("leavemsg", new ServerLeftMessage());
        commands.put("permlvl", new PermLvls());
        commands.put("settings", new commands.settings.Settings());
        commands.put("cmdlog", new CmdLog());
        commands.put("speed", new Speedtest());
        commands.put("speedtest", new Speedtest());
        commands.put("quote", new Quote());
        commands.put("mute", new Mute());
        commands.put("log", new Log());
        commands.put("broadcast", new Broadcast());
        commands.put("guilds", new Guilds());
        commands.put("report", new Report());
        commands.put("bug", new Bug());
        commands.put("suggestion", new Bug());
        commands.put("botstats", new BotStats());
        commands.put("blacklist", new Blacklist());
        commands.put("count", new Count());
        commands.put("counter", new Counter());
//        commands.put("spacer", new Spacer());
//        commands.put("autochannel", new Autochannel());
    }

    private static void initializeListeners() {

        builder.addEventListener(new ReadyListener());
        builder.addEventListener(new BotListener());
        builder.addEventListener(new ReconnectListener());
        builder.addEventListener(new VoiceChannelListener());
        builder.addEventListener(new GuildJoinListener());
        builder.addEventListener(new PrivateMessageListener());
        builder.addEventListener(new ReactionListener());
        builder.addEventListener(new VkickListener());
//        builder.addEventListener(new ServerLimitListener());
//        builder.addEventListener(new AutochannelHandler());
    }

    public static void handleCommand(CommandParser.CommandContainer cmd) throws ParseException, IOException {

        if (commands.containsKey(cmd.invoke)) {

            BotStats.commandsExecuted++;
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if (!safe) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }

        }
    }

}
