package commands.thunderTools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by sakhalines on 11.07.2017.
 */
public class CommonClass {

    static String setColorForDiff(String stringDiffForColor){
        String colorString = "";
        if (stringDiffForColor.contains("-"))
            colorString = stringDiffForColor + " :small_red_triangle_down:";
        else if (stringDiffForColor.contains("+"))
            colorString = stringDiffForColor + " :arrow_up_small:";
        return colorString;
    }
    static String sendHelpCommands(String errorDescription) {

        String helpText = "\n\n**Получение статистики игрока с сайта http://tunderskill.com**\n" +
                "\nИспользование:\n" +
                "    **.skill [a] [r] [s] [c] [u] PlayerName**\n" +
                "Параметры:\n" +
                "    **PlayerName** - никнейм игрока.\n" +
                "    **a** - Аркадный\n" +
                "    **r** - Реалистичный,\n" +
                "    **s** - Симуляторный\n" +
                "    **c** - послать ответ в текущий канал\n" +
                "    **u** - обновить статистику игрока перед ответом (доступно один раз в сутки)\n" +
                "Примеры:\n" +
                "    **.skill u s с SuperGamer** или **.skill usс SuperGamer**\n" +
                "пришлёт статистику симуляторных боёв игрока SuperGamer в текущий канал предварительно обновив её.";
        String errorType = "";
        if (errorDescription == "wrongParamLenght") {
            errorType += "\n\n**Не верное количество аргументов.**";
        }
        else if (errorDescription == "wrongPlayerName") {
            errorType = "\n\n**Не задан ник игрока.**";
        }
        else if (errorDescription == "wrongGameMode") {
            errorType = "\n\n**Не верный режим игры.**";
        }
        return errorType + helpText;
    }

    static void sendPostRequest(String playerNick) throws IOException {
        Document doc = Jsoup.connect("https://thunderskill.com/ru/stat/" + playerNick)
                .data("email", "myemailid")
                .userAgent("Mozilla")
                .post();
    }

    static boolean isNumberAndLetter(String s) {
        String n = ".*[0-9].*";
        String a = ".*[A-Z].*";
        return s.matches(n) && s.matches(a);
    }
    static String getDateMountNumber(String mountSting){
        String mountNumber = null;
        switch (mountSting){
            case "января": mountNumber = "01"; break;
            case "февраля": mountNumber = "02"; break;
            case "марта": mountNumber = "03"; break;
            case "апреля": mountNumber = "04"; break;
            case "мая": mountNumber = "05"; break;
            case "июня": mountNumber = "06"; break;
            case "июля": mountNumber = "07"; break;
            case "августа": mountNumber = "08"; break;
            case "сентября": mountNumber = "09"; break;
            case "октября": mountNumber = "10"; break;
            case "ноября": mountNumber = "11"; break;
            case "декабря": mountNumber = "12"; break;

        }
        return mountNumber;
    }

    public static String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

}
