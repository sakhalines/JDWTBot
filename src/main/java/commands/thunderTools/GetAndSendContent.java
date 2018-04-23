package commands.thunderTools;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sakhalines on 11.07.2017.
 */
public class GetAndSendContent {
    private static String gameChangelogURL = "https://warthunder.ru/game/changelog/";
    private static String gameNewsURL = "https://warthunder.ru/news/";
    private static String URL = "";
    static String dateContentLastUpdate, dateNews;
    static String getFullContent(int newsNumber, String contentType) {
        dateContentLastUpdate = null;
        dateNews = null;
        String urlFullNews = "";
        String result = "";
        switch (contentType){
            case "gameChangelog": URL = gameChangelogURL; break;
            case "gameNews": URL = gameNewsURL; break;
        }
        Document doc;
            try {
                doc = Jsoup.connect(URL)
                        .userAgent("Chrome")
                        .ignoreHttpErrors(true)
                        .timeout(10000)
                        .get();
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
                doc = Jsoup.connect(urlFullNews)
                        .userAgent("Chrome")
                        .ignoreHttpErrors(true)
                        .timeout(10000)
                        .get();
                Elements div = doc.select("div.news-item");
                switch (contentType) {
                    case "gameChangelog":
                        dateContentLastUpdate = div.select("span.date").first().text();
                        String title = div.select("div.news-item > h1").first().text();
                        Elements uls = div.select("div.g-col > ul");
                        Elements lis = uls.select("li");
                        result += ":small_blue_diamond: **" + dateContentLastUpdate + " | " + title +"**\n";
                        for (Element li : lis){
                            result += ":white_small_square: " + li.select("li").text() + "\n";
                        }
                        break;
                    case "gameNews":
                        dateContentLastUpdate = div.select("span.newsheadline__newsDate").first().text();
                        title = div.select("div.news-item.contentarea > h1").first().text();
                        //String title2 = div.select("h2").first().text();
                        Elements ps = div.select("div.g-col > p");
                        result += ":small_blue_diamond: **" + dateContentLastUpdate + " | " + title +"**\n";
                        for (Element p : ps){
                            result += ":white_small_square: " + p.select("p").text() + "\n";
                        }//Elements lis = uls.select("li");
                        break;
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        return result + "\n:link: " + urlFullNews;
    }
    static String getContent(int howMuchNewsSend, String contentType){
        dateContentLastUpdate = null;
        dateNews = null;
        switch (contentType){
            case "gameChangelog": URL = gameChangelogURL; break;
            case "gameNews": URL = gameNewsURL; break;
        }
        String result = "";
        //int count = 0;
        Document doc;
        try {
            doc = Jsoup.connect(URL)
                    .userAgent("Chrome")
                    .ignoreHttpErrors(true)
                    .timeout(10000)
                    .get();
            Elements fullNews = doc.select("div.news-item__anons");
//            Elements newsDates = doc.select("span.date");
//            Elements newsNames = doc.select("a.news-name");
//            Elements  newsContents = doc.select("div.news-text");
            int indexPosition = 0;
            String fullNewsCommand = "";
            for (Element fullNew : fullNews){
                indexPosition++;
                switch (contentType) {
                    case "gameChangelog":
                        //dateContentLastUpdate = fullNew.select("span.date").first().text();
                        if (dateContentLastUpdate == null) {
                            dateContentLastUpdate = fullNew.select("div.news-item__additional-date").first().text();
                            if (dateContentLastUpdate.contains("16 марта 2018")) // дата выхода мажора "Буря", которая торчит постоянно сверху страницы
                                dateContentLastUpdate  = null;
                        }
                        dateNews = fullNew.select("div.news-item__additional-date").first().text();
                        fullNewsCommand = ".chlog";
                        break;
                    case "gameNews":
                        if (dateContentLastUpdate == null){
                            dateContentLastUpdate = fullNew.select("p.news-item__additional-date").first().text();
                            if (dateContentLastUpdate.contains("13 апреля 2018"))// дата акции "Хроника Второй мировой", которая торчит постоянно сверху страницы
                                dateContentLastUpdate  = null;
                        }
                        dateNews = fullNew.select("p.news-item__additional-date").first().text();
                        fullNewsCommand = ".gnews";
                        break;
                }
                result += "\n\n:small_blue_diamond: **" + dateNews
                        + " | " + fullNew.select("a.news-item__title").text() + "**"+
                        "\n" + fullNew.select("div.news-item__text").text()+
                        //"\n\n:link: " + fullNew.select("a").first().attr("abs:href") +
                        "\n:heavy_check_mark: Для получения подробностей наберите: **" + fullNewsCommand + " cf" + String.valueOf(indexPosition + " **");
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

    static boolean sendContent(String[] args, MessageReceivedEvent event, String contentType){
        String msg = String.join(",", args);
        String[] sendReaply = null;
        int howMuchNewsSendOrNumberForGetFullNews = 1,
            newsNumberForGetFullNews;
        int indexPosition = 1; // нужна чтобы в подсказке выводить номер для полной новости

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.yellow);
        if (args.length == 0) {

            try {
                switch (contentType){
                    case "gameChangelog":
                        sendReaply = CommonClass.splitStringEvery(
                            GetAndSendContent.getContent(
                                    howMuchNewsSendOrNumberForGetFullNews, "gameChangelog"), 2000);
                        break;
                    case "gameNews":
                        sendReaply = CommonClass.splitStringEvery(
                            GetAndSendContent.getContent(
                                    howMuchNewsSendOrNumberForGetFullNews, "gameNews"), 2000);
                        break;

                }
                } catch (Exception e) {
                event.getChannel().sendMessage("Ошибка получения списка обновлений.").queue();
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
                if (args[0].length() > 1) {
                    for (int i = 0; i <= args[0].length() - 1; i++) {
                        switch (args[0].toCharArray()[i]) {
                            case 'c': sendAsChannelMsg = true; break;
                            case 'f': getFullNews = true; break;
                            case 'C': sendAsChannelMonitoringMsg = true; break; // обновление в канале для мониторинга обновлений
                        }
                        // поиск цифры в параметрах, указывающую количество новостей, или указывающую номер подробной новости
                        Pattern p = Pattern.compile("-?\\d+");
                        Matcher m = p.matcher(args[0]);
                        while (m.find()) {
                            try {
                                howMuchNewsSendOrNumberForGetFullNews = Integer.parseInt(m.group());
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                }
                else {
                    for (int i = 0; i <= args.length - 1; i++) {
                        switch (args[i]) {
                            case "c": sendAsChannelMsg = true; break;
                            case "f": getFullNews = true; break;
                            case "C": sendAsChannelMonitoringMsg = true; break; // обновление в канале для мониторинга обновлений

                        }
                        // поиск цифры в параметрах, указывающую количество новостей, или указывающую номер подробной новости
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
                }
            if (howMuchNewsSendOrNumberForGetFullNews > 10) {
                event.getChannel().sendMessage(":warning: Укажите номер новости или их количество от одного до десяти.").queue();
                return false;
            }
            if (getFullNews) {
                try {
                    switch (contentType) {
                        case "gameChangelog":
                            sendReaply = CommonClass.splitStringEvery(
                                    GetAndSendContent.getFullContent(
                                            howMuchNewsSendOrNumberForGetFullNews, "gameChangelog"), 2000);
                            break;
                        case "gameNews":
                            sendReaply = CommonClass.splitStringEvery(
                                    GetAndSendContent.getFullContent(
                                            howMuchNewsSendOrNumberForGetFullNews, "gameNews"), 2000);

                    }
                } catch (Exception e) {
                    event.getChannel().sendMessage("Ошибка получения списка обновлений.").queue();
                    return false;
                }

            } else { 
                try {
                    switch (contentType) {
                        case "gameChangelog":
                            sendReaply = CommonClass.splitStringEvery(GetAndSendContent.getContent(howMuchNewsSendOrNumberForGetFullNews, "gameChangelog"), 2000);
                            break;
                        case "gameNews":
                            sendReaply = CommonClass.splitStringEvery(GetAndSendContent.getContent(howMuchNewsSendOrNumberForGetFullNews, "gameNews"), 2000);
                            break;
                    }
                } catch (Exception e) {
                    event.getChannel().sendMessage("Ошибка получения списка обновлений.").queue();
                    return false;
                }
            }
            if (sendAsChannelMsg) {
                //int indexPosition = 1;
                for (String sendReap : sendReaply){
                    try {
                        event.getChannel().sendMessage(
                                //eb.setDescription(sendReaply[i])
                                eb.setDescription(sendReap).build()).queue();
                        indexPosition++;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
//            else if (sendAsChannelDescription) {
//                String reaply = GetAndSendContent.getContent(howMuchNewsSend);
//                queryLib.doCommand("channeledit cid=" + botHomeChannel + " channel_description=" +
//                        queryLib.encodeTS3String(reaply.replaceAll("--", "")));
//
//            }
            else if (sendAsChannelMonitoringMsg){
                if(!Monitoring.updateGameContentMonitoringChannel(event.getJDA(), "gameChangelog") ){
                    event.getChannel().sendMessage(":warning:Обновлений игры нет.").queue();
                }
                else {
                    event.getChannel().sendMessage(":new:Обновления игры доступны. Канал обновлён.").queue();
                }


            }
            else {
                try {
                    switch (contentType) {
                        case "gameChangelog":
                            sendReaply = CommonClass.splitStringEvery(GetAndSendContent.getContent(howMuchNewsSendOrNumberForGetFullNews, "gameChangelog"), 2000);
                            break;
                        case "gameNews":
                            sendReaply = CommonClass.splitStringEvery(GetAndSendContent.getContent(howMuchNewsSendOrNumberForGetFullNews, "gameNews"), 2000);
                            break;
                    }
                } catch (Exception e) {
                    event.getChannel().sendMessage("Ошибка получения списка обновлений.").queue();
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

//    @Override
//    public boolean called(String[] args, MessageReceivedEvent event) {
//        return false;
//    }
//
//    @Override
//    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
//        sendContent(args, event);
//    }
//
//    @Override
//    public void executed(boolean success, MessageReceivedEvent event) {
//
//    }
//
//    @Override
//    public String help() {
//        String helpText = "**Получения списка обновлений игры War Thunder c официального сайта" +
//                "\nИспользование:**" +
//                "\n.chlog [c] [f] [numder]" +
//                "\n**c** - послать ответ в текущий канал" +
//                "\n**C** - послать ответ в канал мониторинга обновлений" +
//                "\n**f** - показать подробности обновления" +
//                "\n**numder** - количество новостей для получения или порядковый номер новости при запросе подробностей (число от одного до десяти)." +
//                "\n\n**Примеры:**\n" +
//                "\n**'.chlog 5'** пришлёт пять крайних обновлений. \n" +
//                "**'.chlog c'** пришлёт только одно самое крайнее обновление в текущий канал.\n" +
//                "\n**'.chlog c f 3 или .chlog cf 3'** пришлёт подробности третьего обновления.";
//        return helpText;
//    }
//
//    @Override
//    public String description() {
//        return "Получения списка обновлений игры War Thunder c официального сайта";
//    }
//
//    @Override
//    public String commandType() {
//        return STATICS.CMDTYPE.thunderTools;
//    }
//
//    @Override
//    public int permission() {
//        return 1;
//    }
}
