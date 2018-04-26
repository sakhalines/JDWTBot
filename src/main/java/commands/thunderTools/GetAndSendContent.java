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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sakhalines on 11.07.2017.
 */
public class GetAndSendContent {
    private static String
            gameChangelogURL = "https://warthunder.ru/game/changelog/",
            gameNewsURL = "https://warthunder.ru/news/",
            URL, dateNews;
    static String dateContentLastUpdate;
    static String getFullContent(int newsNumber, String contentType) {
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
                        dateNews = div.select("span.date").first().text();
                        String title = div.select("div.news-item > h1").first().text(),
                                title2 = "",
                                title3 = "";
                        Elements uls = div.select("div.g-col > ul");
                        Elements lis = uls.select("li");
                        result += ":small_blue_diamond: **" + dateNews + " | " + title +"**\n";
                        for (Element li : lis){
                            result += ":white_small_square: " + li.text() + "\n";
                        }
                        break;
                    case "gameNews":
                        dateNews = div.select("span.newsheadline__newsDate").first().text();
                        Element contentarea = div.select("div.news-item.contentarea").first();
                        Elements g_cols = contentarea.select("div.g-col"),
                        elemsH2 = null, elemsH3 = null, elemsP;
                        title = contentarea.select("h1").first().text();
                        result += ":small_blue_diamond: **" + dateNews + " | " + title +"**\n\n";

                        Elements elemsVideoLink = g_cols.select("iframe[src*=youtube.com]");
                        for (Element elemVideoLink : elemsVideoLink){
                            result += elemVideoLink.attr("src").replace("www.youtube.com/embed", "youtu.be").replace("?rel=0&showinfo=0", "") +"\n\n";
                        }

//                        for (Element g_col : g_cols){
//                            if (!g_col.select("h2").isEmpty()) {
//                                int iter = 0;
//                                elemsH2 = g_col.select("h2");
//                                for (Element elemH2 : elemsH2){
//                                    result += ":grey_question: **" + elemH2.text() + "**\n";
//                                    Elements elemsUl = g_col.select("ul");
//                                    if (!elemsUl.isEmpty()) {
//                                        result += ":white_small_square: " + elemsUl.get(iter).select("li").text() + "\n\n";
//                                        iter++;
//                                    }
//
//                                }
//                            }
//                            if (!g_col.select("h3").isEmpty()) {
//                                int iter = 0;
//                                elemsH3 = g_col.select("h3");
//                                for (Element elemH3 : elemsH3){
//                                    result += ":grey_question: **" + elemH3.text() + "**\n";
//                                    Elements elemsUl = g_col.select("ul");
//                                    if (!elemsUl.isEmpty()) {
//                                        result += ":white_small_square: " + elemsUl.get(iter).select("li").text() + "\n\n";
//                                        iter++;
//                                    }
//
//                                }
//                            }
//                        }

//                        for (int i = 0; i < g_cols.size(); i++) {
//                            if (!g_cols.get(i).select("h2").isEmpty()) {
//                                title2 = g_cols.get(i).select("h2").text();
//                            }
//                            if (!g_cols.get(i).select("h3").isEmpty()) {
//                                for (int j = 0; i < g_cols.size(); j++) {
//                                    title2 = g_cols.get(i).select("h3").get(j).text();
//                                }
//                            }
//                        }

                        //String title2 = div.select("h2").first().text();
//                        Elements g_col_ps = div.select("div.g-col p");
//                        Elements g_col_h3s = div.select("div.g-col h3");
//                        Elements g_col_uls = div.select("div.g-col > ul");
                        //result += ":small_blue_diamond: **" + dateNews + " | " + title +"**\n\n";

//                        for (Element g_col_p : g_col_ps){
//                            int i=0;
//                            result += "**" + g_col_p.text() + "**\n";
//                            for (Element g_col_h3 : g_col_h3s){
//                                result += "***" + g_col_h3.text() + "***\n";
//                                result += ":white_small_square: " + g_col_uls.get(i).text() + "\n";
//                                i++;
//                            }
//                        }

                        //Elements lis = uls.select("li");
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
                indexPosition = 1; // нужна чтобы в подсказке выводить номер для полной новости

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.yellow);
        String updateContentErrorMsg = null;
        switch (contentType) {
            case "gameChangelog": updateContentErrorMsg = "Ошибка проверки обновлений игры!";
            case "gameNews": updateContentErrorMsg = "Ошибка проверки обновлений новостей игры!";
        }
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
                event.getChannel().sendMessage(updateContentErrorMsg).queue();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                System.out.println(simpleDateFormat.format(new Date()) + " | " + updateContentErrorMsg + "\n\n");
                e.printStackTrace();
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
            if (getFullNews || sendAsChannelMsg || sendAsChannelMonitoringMsg) {
                if (getFullNews){
                    try {
                        switch (contentType) {
                            case "gameChangelog":
                                sendReaply = CommonClass.splitStringEvery(GetAndSendContent.getFullContent(howMuchNewsSendOrNumberForGetFullNews, "gameChangelog"), 2000);
                                break;
                            case "gameNews":
                                sendReaply = CommonClass.splitStringEvery(GetAndSendContent.getFullContent(howMuchNewsSendOrNumberForGetFullNews, "gameNews"), 2000);

                        }
                    } catch (Exception e) {
                        event.getChannel().sendMessage(updateContentErrorMsg).queue();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        System.out.println(simpleDateFormat.format(new Date()) + " | " + updateContentErrorMsg + "\n\n");
                        e.printStackTrace();
                        return false;
                    }
                }
                else{
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
                        event.getChannel().sendMessage(updateContentErrorMsg).queue();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        System.out.println(simpleDateFormat.format(new Date()) + " | " + updateContentErrorMsg + "\n\n");
                        e.printStackTrace();
                        return false;
                    }
                }
                if (sendAsChannelMsg) {
                    //int indexPosition = 1;
                    for (String sendReap : sendReaply) {
                        try {
                            if (sendReap.contains("youtu.be")) { // чтобы видео инерпретировалось дискордом, посылаем без форматирования).
                                event.getChannel().sendMessage(sendReap).queue();
                                }
                            else {
                                event.getChannel().sendMessage(
                                        eb.setDescription(sendReap).build()).queue();
                            }
                            indexPosition++;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (sendAsChannelMonitoringMsg) {
                    if (!Monitoring.updateGameContentMonitoringChannel(event.getJDA(), "gameChangelog")) {
                        event.getChannel().sendMessage(":warning:Обновлений игры нет.").queue();
                    } else {
                        event.getChannel().sendMessage(":new:Обновления игры доступны. Канал обновлён.").queue();
                    }


                } else {
                    for (String sendReap : sendReaply) {
                        PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                        pc.sendMessage(
                                eb.setDescription(sendReap).build()).queue();
                        indexPosition++;
                    }
                }
            }
        }
        return true;
    }
}