package commands.thunderTools;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.STATICS;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sakhalines on 11.07.2017.
 */
public class ChangeLog implements Command {
    static String getFullNews(int newsNumber) {
        String urlFullNews = "";
        String result = "";
        Document doc;
            try {
                doc = Jsoup.connect("http://warthunder.ru/ru/game/changelog/").get();
                Elements fullNews = doc.select("div.news-item__anons");
                int i = 1;
                for (Element fullNew : fullNews) {
                    if (i == newsNumber){
                        urlFullNews = fullNew.select("a").attr("abs:href");
                        break;
                    }
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                doc = Jsoup.connect(urlFullNews).get();
                Elements div = doc.select("div.news-item");
                String date = div.select("span.date").first().text();
                String title = div.select("div.news-item > h1").first().text();
                Elements uls = div.select("div.news-item > ul");
                Elements lis = uls.select("li");
                result += ":small_blue_diamond: **" + date + " | " + title +"**\n";
                for (Element li : lis){
                    result += ":white_small_square: " + li.select("li").text() + "\n";
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        return result + "\n:link: " + urlFullNews;
    }
    static String dateLastUpdate = null;
    static String getChangelog(int howMuchNewsSend){
        String result = "";
        //int count = 0;
        Document doc;
        try {
            doc = Jsoup.connect("http://warthunder.ru/ru/game/changelog/").get();
            Elements fullNews = doc.select("div.news-item__anons");
//            Elements newsDates = doc.select("span.date");
//            Elements newsNames = doc.select("a.news-name");
//            Elements  newsContents = doc.select("div.news-text");
            int indexPosition = 0;
            for (Element fullNew : fullNews){
                indexPosition++;
                if (dateLastUpdate == null){
                    dateLastUpdate = fullNew.select("span.date").text();
                }
                result += "\n\n:small_blue_diamond: **" + fullNew.select("span.date").text()
                        + " | " + fullNew.select("a.news-name").text() + "**"+
                        "\n" + fullNew.select("div.news-text").text()+
                        //"\n\n:link: " + fullNew.select("a").first().attr("abs:href") +
                        "\n:heavy_check_mark: Для получения подробностей наберите: **.chlog cf" + String.valueOf(indexPosition + " **");
                        //.replace("Обновление ", ""); // вырежем слово "обновление" в каждом сообщении, оставив только номер обновы
                if(howMuchNewsSend > 0){
                    howMuchNewsSend --;
                    if (howMuchNewsSend == 0) break;
                }
                //if (firsOnly) break;
            }
//            result = fullNews.toString().substring(0, 2000);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;

    }

    static boolean sendChangelog(String[] args, MessageReceivedEvent event){
        String msg = String.join(",", args);
        String[] sendReaply = null;
        int howMuchNewsSendOrNumberForGetFullNews = 1,
            newsNumberForGetFullNews;
        int indexPosition = 1; // нужна чтобы в подсказке выводить номер для полной новости

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.yellow);
        if (args.length == 0) {

            try {
                sendReaply = CommonClass.splitStringEvery(ChangeLog.getChangelog(howMuchNewsSendOrNumberForGetFullNews), 2000);
            } catch (Exception e) {
                event.getTextChannel().sendMessage("Ошибка получения списка обновлений.").queue();
                return false;
            }

            for (String sendReap : sendReaply){
                try {
                    PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                    pc.sendMessage(
                            eb.setDescription(sendReap).build()).queue();
                    indexPosition++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            boolean sendAsChannelMsg = false, getFullNews = false, sendAsChannelMonitoringMsg = false;

            for (int i = 0; i <= args.length - 1; i++) {
                if (args[i].contains("c"))
                    sendAsChannelMsg = true;
                if (args[i].contains("f"))
                    getFullNews = true;
                if (args[i].contains("C")) // обновление в канале для мониторинга обновлений
                    sendAsChannelMonitoringMsg = true;

                Pattern p = Pattern.compile("-?\\d+");
                Matcher m = p.matcher(args[i]);
                while (m.find()) {
                    try {
                        howMuchNewsSendOrNumberForGetFullNews = Integer.parseInt(m.group());
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            if (howMuchNewsSendOrNumberForGetFullNews > 10) {
                event.getTextChannel().sendMessage(":warning: Укажите номер новости или их количество от одного до десяти.").queue();
                return false;
            }
            if (getFullNews) {
                try {
                    sendReaply = CommonClass.splitStringEvery(ChangeLog.getFullNews(howMuchNewsSendOrNumberForGetFullNews), 2000);
                } catch (Exception e) {
                    event.getTextChannel().sendMessage("Ошибка получения списка обновлений.").queue();
                    return false;
                }

            } else { 
                try {
                    sendReaply = CommonClass.splitStringEvery(ChangeLog.getChangelog(howMuchNewsSendOrNumberForGetFullNews), 2000);
                } catch (Exception e) {
                    event.getTextChannel().sendMessage("Ошибка получения списка обновлений.").queue();
                    return false;
                }
            }
            if (sendAsChannelMsg) {
                //int indexPosition = 1;
                for (String sendReap : sendReaply){
                    try {
                        event.getTextChannel().sendMessage(
                                //eb.setDescription(sendReaply[i])
                                eb.setDescription(sendReap).build()).queue();
                        indexPosition++;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
//            else if (sendAsChannelDescription) {
//                String reaply = ChangeLog.getChangelog(howMuchNewsSend);
//                queryLib.doCommand("channeledit cid=" + botHomeChannel + " channel_description=" +
//                        queryLib.encodeTS3String(reaply.replaceAll("--", "")));
//
//            }
            else if (sendAsChannelMonitoringMsg){
                if(!ChlogMonitoring.updateGameChangelogChannel(event.getJDA()) ){
                    event.getTextChannel().sendMessage(":warning:Обновлений игры нет.").queue();
                }
                else {
                    event.getTextChannel().sendMessage(":new:Обновления игры доступны. Канал обновлён.").queue();
                }


            }
            else {
                try {
                    sendReaply = CommonClass.splitStringEvery(ChangeLog.getChangelog(howMuchNewsSendOrNumberForGetFullNews), 2000);
                } catch (Exception e) {
                    event.getTextChannel().sendMessage("Ошибка получения списка обновлений.").queue();
                    return false;
                }
                    for (String sendReap : sendReaply) {
                        PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                        pc.sendMessage(
                                eb.setDescription(sendReap).build()).queue();
                        indexPosition++;
                    }
            }
        }
        return true;
//            }
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        sendChangelog(args, event);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        String helpText = "**Получения списка обновлений игры War Thunder c официального сайта" +
                "\nИспользование:**" +
                "\n.chlog [c] [f] [numder]" +
                "\n**c** - послать ответ в текущий канал" +
                "\n**C** - послать ответ в канал мониторинга обновлений" +
                "\n**f** - показать подробности обновления" +
                "\n**numder** - количество новостей для получения или порядковый номер новости при запросе подробностей (число от одного до десяти)." +
                "\n\n**Примеры:**\n" +
                "\n**'.chlog 5'** пришлёт пять крайних обновлений. \n" +
                "**'.chlog c'** пришлёт только одно самое крайнее обновление в текущий канал.\n" +
                "\n**'.chlog c f 3 или .chlog cf 3'** пришлёт подробности третьего обновления.";
        return helpText;
    }

    @Override
    public String description() {
        return "Получения списка обновлений игры War Thunder c официального сайта";
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
