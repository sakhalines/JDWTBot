package core;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public static void listSettings(MessageReceivedEvent event) {

        Guild g = event.getGuild();

        StringBuilder keys = new StringBuilder();
        StringBuilder values = new StringBuilder();

        File[] files = new File("SERVER_SETTINGS/" + g.getId() + "/").listFiles();
        System.out.println(files.length);

        Arrays.stream(files).forEach(f -> {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                keys.append("**" + f.getName() + "**\n");
                values.append("`" + br.readLine() + "`\n");
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        event.getTextChannel().sendMessage(new EmbedBuilder()
                .setTitle("SettingsCore for guild \"" + g.getName() + "\" (" + g.getId() + ")", null)
                .addBlankField(false)
                .addField("Keys", keys.toString(), true)
                .addField("Values", values.toString(), true)
                .build()
        ).queue();

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


//    public static String getMUSICCHANNEL(Guild guild) {
//
//        try {
//            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/musicchannel");
//            if (f.exists()) {
//                try {
//                    return new BufferedReader(new FileReader(f)).readLine();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {}
//        return "";
//    }

//    public static void setMUSICCHANNEL(String entry, Guild guild) {
//
//        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/musicchannel");
//        try {
//            BufferedWriter r = new BufferedWriter(new FileWriter(f));
//            r.write(entry);
//            r.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //    public static boolean getLOCKMUSICCHANNEL(Guild guild) {
//
//        try {
//            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/lockmusicchannel");
//            if (f.exists()) {
//                try {
//                    return new BufferedReader(new FileReader(f)).readLine().equals("true");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {}
//        return false;
//    }

//    public static void setLOCKMUSICCHANNEL(boolean entry, Guild guild) {
//
//        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/lockmusicchannel");
//        try {
//            BufferedWriter r = new BufferedWriter(new FileWriter(f));
//            r.write(entry + "");
//            r.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//


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
    
    private static void settingWrite(String entry, File f){
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public static String getR6OPSID(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/r6opsID");
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

}
