package commands.thunderTools;

import commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

import java.io.IOException;
import java.text.ParseException;

public class GameChangelog implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        GetAndSendContent.sendContent(args, event, "gameChangelog");
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        String prefix = STATICS.PREFIX;
        String helpText = "**Получение списка обновлений игры War Thunder c официального сайта" +
                "\nИспользование:\n\"" +
                prefix + "gameChangelog [c] [f] [numder]\"**\n" +
                "**c** - послать ответ в текущий канал\n" +
                "**C** - послать ответ в канал мониторинга обновлений\n" +
                "**f** - показать подробности обновления\n" +
                "**numder** - количество новостей для получения или порядковый номер новости при запросе подробностей (число от одного до десяти)." +
                "\n\n**Примеры:\n\"" +
                prefix + "gameChangelog 5\"** - пришлёт пять крайних обновлений.\n**\"" +
                prefix + "gameChangelog c\"** - пришлёт только одно самое крайнее обновление в текущий канал.\n\"**" +
                prefix + "gameChangelog c f 3\"** или  **\"" + prefix + "chlog cf3\"** - пришлёт подробности третьего обновления." +
                "\n\n**Алиасы** (сокращённая форма команды):    **\"" + prefix + "chlog\"**";
        return helpText;
    }

    @Override
    public String description() {
        return "Получение списка обновлений игры War Thunder c официального сайта";
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
