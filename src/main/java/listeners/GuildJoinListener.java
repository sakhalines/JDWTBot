package listeners;

import core.SSSS;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * © zekro 2017
 *
 * @author zekro
 */

public class GuildJoinListener extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        commands.settings.Botmessage.setSupplyingMessage(event.getJDA());

        if (event.getMember().getUser().isBot()) return;

        if (!SSSS.getSERVERJOINMESSAGE(event.getGuild()).toLowerCase().equals("off")) {
            event.getGuild().getTextChannelsByName("general", true).get(0).sendMessage(
                SSSS.getSERVERJOINMESSAGE(event.getGuild()).replace("[USER]", event.getMember().getAsMention()).replace("[GUILD]", event.getGuild().getName())
            ).queue();
        }
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {

        commands.settings.Botmessage.setSupplyingMessage(event.getJDA());

        if (event.getMember().getUser().isBot()) return;

        if (!SSSS.getSERVERLEAVEMESSAGE(event.getGuild()).toLowerCase().equals("off")) {
            event.getGuild().getTextChannelsByName("general", true).get(0).sendMessage(
                    SSSS.getSERVERLEAVEMESSAGE(event.getGuild()).replace("[USER]", event.getMember().getAsMention()).replace("[GUILD]", event.getGuild().getName())
            ).queue();
        }

    }


}
