package commands.thunderTools;

import commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.io.IOException;
import java.text.ParseException;

public class GameNews implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        GetAndSendContent.sendContent(args, event, "gameNews");
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        String helpText = "**Получение новостей игры War Thunder c официального сайта" +
                "\nИспользование:**" +
                "\n.gameNews [c] [f] [numder]" +
                "\n**c** - послать ответ в текущий канал" +
                "\n**C** - послать ответ в канал мониторинга обновлений" +
                "\n**f** - показать подробности обновления" +
                "\n**numder** - количество новостей для получения или порядковый номер новости при запросе подробностей (число от одного до десяти)." +
                "\n\n**Примеры:**\n" +
                "\n**'.gameNews 5'** пришлёт пять крайних новостей. \n" +
                "**'.gameNews c'** пришлёт только одну самую крайнюю новость в текущий канал.\n" +
                "\n**'.gameNews c f 3 или .gameNews cf 3'** пришлёт подробности третьей новости." +
                "\n\n**Алиасы** (сокращённая форма команды): " + STATICS.PREFIX + "`gnews` + ";
        return helpText;
    }

    @Override
    public String description() {
        return "Получение новостей игры War Thunder c официального сайта";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.thunderTools;
    }

    @Override
    public int permission() {
        return 1;
    }
}
