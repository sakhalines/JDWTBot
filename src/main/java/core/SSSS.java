package core;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zekro on 17.05.2017 / 14:12
 * DiscordBot/core
 * © zekro 2017
 */

public class SSSS /* Stands for "SERVER SPECIFIC SETTINGS SYSTEM" :^) */ {

    public static void checkFolders(List<Guild> guilds) {

        guilds.forEach(guild -> {
            File f = new File("SERVER_SETTINGS/" + guild.getId());
            if (!f.exists() || !f.isDirectory()) {
                f.mkdirs();
            }
        });
    }

//    public static void listSettings(MessageReceivedEvent event) {
//
//        Guild g = event.getGuild();
//
//        StringBuilder keys = new StringBuilder();
//        StringBuilder values = new StringBuilder();
//
//        File[] files = new File("SERVER_SETTINGS/" + g.getId() + "/").listFiles();
//        System.out.println(files.length);
//
//        Arrays.stream(files).forEach(f -> {
//            try {
//                BufferedReader br = new BufferedReader(new FileReader(f));
//                keys.append("**" + f.getName() + "**\n");
//                values.append("`" + br.readLine() + "`\n");
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        event.getTextChannel().sendMessage(new EmbedBuilder()
//                .setTitle("SettingsCore for guild \"" + g.getName() + "\" (" + g.getId() + ")", null)
//                .addBlankField(false)
//                .addField("Keys", keys.toString(), true)
//                .addField("Values", values.toString(), true)
//                .build()
//        ).queue();
//
//    }

    public static void listSettings(MessageReceivedEvent event) {

        Guild g = event.getGuild();

        HashMap<String, String> sets = new HashMap<>();
        sets.put("PREFIX                ", getPREFIX(g));
        sets.put("SERVER_JOIN_MSG       ", getSERVERJOINMESSAGE(g));
        sets.put("SERVER_LEAVE_MSG      ", getSERVERJOINMESSAGE(g));
        sets.put("VKICK_CHANNEL         ", getVKICKCHANNEL(g));
        
        sets.put("GAME_CHANGELOG_CHANNEL_PREFIX     ", getGAMECHANGELOGCHANNELPREFIX(g));
        sets.put("GAME_CHANGELOG_UPDATE_INTERVAL    ", getGAMECHANGELOGUPDATEINTERVAL(g));

        HashMap<String, String> setsMulti = new HashMap<>();
        setsMulti.put("PERMROLES_LVL1", String.join(", ", getPERMROLES_1(g)));
        setsMulti.put("PERMROLES_LVL2", String.join(", ", getPERMROLES_2(g)));
        setsMulti.put("PERMROLES_LVL2", String.join(", ", getPERMROLES_3(g)));
        setsMulti.put("BLACKLIST", String.join(", ", getBLACKLIST(g)));

        StringBuilder sb = new StringBuilder()
                .append("**Список настроек этого сервера**\n\n")
                .append("```")
                .append("ПАРАМЕТР            -  ЗНАЧЕНИЕ\n\n");

        sets.forEach((k, v) -> sb.append(
                String.format("%s  -  \"%s\"\n", k, v))
        );

        sb.append("\n- - - - -\n\n");
        setsMulti.forEach((k, v) -> sb.append(
                String.format("%s:\n\"%s\"\n\n", k, v))
        );

        event.getTextChannel().sendMessage(new EmbedBuilder().setDescription(sb.append("```").toString()).build()).queue();
    }


    public static String getPREFIX(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/prefix");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATICS.PREFIX;
    }

    public static void setPREFIX(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/prefix");
        settingWrite(entry, f);
    }


    public static String getSERVERJOINMESSAGE(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/serverjoinmessage");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }  catch (Exception e) {}
        return "OFF";
    }

    public static void setSERVERJOINMESSAGE(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/serverjoinmessage");
        settingWrite(entry, f);
    }


    public static String getSERVERLEAVEMESSAGE(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/serverleavemessage");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return "OFF";
    }

    public static void setSERVERLEAVEMESSAGE(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/serverleavemessage");
        settingWrite(entry, f);
    }

    public static String[] getPERMROLES_1(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/permroles_1");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine().split(", ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATICS.MEMBERPERMS;

    }

    public static void setPERMROLES_1(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/permroles_1");
        settingWrite(entry, f);
    }


    public static String[] getPERMROLES_2(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/permroles_2");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine().split(", ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATICS.PERMS;
    }

    public static void setPERMROLES_2(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/permroles_2");
        settingWrite(entry, f);
    }

    public static String[] getPERMROLES_3(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/permroles_3");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine().split(", ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATICS.FULLPERMS;

    }

    public static void setPERMROLES_3(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/permroles_3");
        settingWrite(entry, f);
    }
    
    public static String getVKICKCHANNEL(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/vkickchannel");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return "";
    }

    public static void setVKICKCHANNEL(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/vkickchannel");
        settingWrite(entry, f);
    }

    public static List<String> getBLACKLIST(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/blacklist");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).lines().map(s -> s.replace("\n", "")).collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return new ArrayList<>();
    }

    public static void setBLACKLIST(List<String> entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/blacklist");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            entry.forEach(l -> {
                try {
                    r.write(l + "\n");
                } catch (IOException e) {}
            });
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getGAMECHANGELOGCHANNELPREFIX(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/gamechangelogchannel");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return "";
    }

    public static void setGAMECHANGELOGCHANNELPREFIX(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/gamechangelogchannel");
        settingWrite(entry, f);
    }

    public static String getGAMECHANGELOGUPDATEINTERVAL(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/gamechangelogupdateinterval");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return "";
    }

    public static void setGAMECHANGELOGUPDATEINTERVAL(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/gamechangelogupdateinterval");
        settingWrite(entry, f);
    }

    private static void settingWrite(String entry, File f){
        try {
            if (!f.exists())
                f.createNewFile();
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
