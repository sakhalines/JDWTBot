package commands.essentials;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

/**
 * © zekro 2017
 *
 * @author zekro
 */
public class UserInfo implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        Member memb;

        if (args.length > 0) {
            memb = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
        } else {
            memb = event.getMember();
        }

        String NAME = memb.getEffectiveName();
        String TAG = memb.getUser().getName() + "#" + memb.getUser().getDiscriminator();
        String GUILD_JOIN_DATE = memb.getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String DISCORD_JOINED_DATE = memb.getUser().getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String ID = memb.getUser().getId();
        String STATUS = memb.getOnlineStatus().getKey();
        String ROLES = "";
        String GAME;
        String AVATAR = memb.getUser().getAvatarUrl();

        try {
            GAME = memb.getGame().getName();
        } catch (Exception e) {
            GAME = "-/-";
        }

        for ( Role r : memb.getRoles() ) {
            ROLES += r.getName() + ", ";
        }
        if (ROLES.length() > 0)
            ROLES = ROLES.substring(0, ROLES.length()-2);
        else
            ROLES = "Не имеет ролей на этом сервере.";

        if (AVATAR == null) {
            AVATAR = "Нет аватарки";
        }

        EmbedBuilder em = new EmbedBuilder().setColor(Color.GREEN);
        em.setDescription(":spy:   **Информация о пользователе " + memb.getUser().getName() + ":**")
                .addField("Имя / Никнейм", NAME, false)
                .addField("User Tag", TAG, false)
                .addField("ID", ID, false)
                .addField("Текущий статус", STATUS, false)
                .addField("Текущаяя игра", GAME, false)
                .addField("Роли", ROLES, false)
                .addField("Уровень разрешений", core.Perms.getLvl(memb) + "", false)
                .addField("Поключен к сереру", GUILD_JOIN_DATE, false)
                .addField("Поключен к Discord", DISCORD_JOINED_DATE, false)
                .addField("URL аватарки", AVATAR, false);

        if (AVATAR != "Нет аватарки") {
            em.setThumbnail(AVATAR);
        }

        event.getTextChannel().sendMessage(
                em.build()
        ).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "Использование: " + STATICS.PREFIX + "userinfo <@пользователь>";
    }

    @Override
    public String description() {
        return "Получение иформации о пользователе";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.essentials;
    }

    @Override
    public int permission() {
        return 0;
    }

}
