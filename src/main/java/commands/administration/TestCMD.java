package commands.administration;

import commands.Command;
import core.Perms;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

/**
 * Created by Ringo Hoffmann on 20.02.2017.
 */

public class TestCMD implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (!Perms.isOwner(event.getAuthor(), event.getChannel())) return;

        System.out.println("TEST");

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.administration;
    }

    @Override
    public int permission() {
        return 4;
    }
}
