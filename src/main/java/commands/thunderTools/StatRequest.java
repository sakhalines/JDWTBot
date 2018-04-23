package commands.thunderTools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by sakhalines on 11.07.2017.
 */
public class StatRequest {

    // устанавлеивает цветной квадратик в зависимости от ранга игрока
    private String setRankIcon(String rankValue){
        String result = "";
        switch (rankValue){
            case "Не играет":
                result = "";
        }
        return result;
    }

    static String[] getStatForPlayerHtml(String playerName) {
        String[] lastUpdateAndComparison = new String[2];
        String preferMode, // предпочитаемый режим (аркада/реал./симул.)
                lastUpdate,
                comparsion,

                rankA,
                rankR,
                rankS,

                kpdA,
                kpdR,
                kpdS,

                //предпочтитаемый режим (воздушный или наземный)
                preferAirOrGroundBattlesA,
                preferAirOrGroundBattlesR,
                preferAirOrGroundBattlesS,

                // количество воздушных боёв в процентном соотношении
                preferAirBattlesA,
                preferAirBattlesR,
                preferAirBattlesS,

                // количество наземных боёв в процентном соотношении
                preferGroundBattlesA,
                preferGroundBattlesR,
                preferGroundBattlesS,

                // процент побед и разница в сравнении по датам
                winrateA,
                winrateR,
                winrateS,
                winrateDiffA,
                winrateDiffR,
                winrateDiffS,

                //Соотношение убийств к смертям за бой и разница в сравнении по датам
                killDeathA,
                killDeathR,
                killDeathS,
                killDeathDiffA,
                killDeathDiffR,
                killDeathDiffS,

                killDeathAirA,
                killDeathAirR,
                killDeathAirS,
                killDeathAirDiffA,
                killDeathAirDiffR,
                killDeathAirDiffS,

                killDeathGroundA,
                killDeathGroundR,
                killDeathGroundS,
                killDeathGroundDiffA,
                killDeathGroundDiffR,
                killDeathGroundDiffS,

                //Убийств за бой и разница в сравнении по датам
                killBattleA,
                killBattleR,
                killBattleS,
                killBattleDiffA,
                killBattleDiffR,
                killBattleDiffS,

                killBattleAirA,
                killBattleAirR,
                killBattleAirS,
                killBattleAirDiffA,
                killBattleAirDiffR,
                killBattleAirDiffS,

                killBattleGroundA,
                killBattleGroundR,
                killBattleGroundS,
                killBattleGroundDiffA,
                killBattleGroundDiffR,
                killBattleGroundDiffS,


                // Продолжительность жизни и разница в сравнении по датам
                lifetimeA,
                lifetimeR,
                lifetimeS,
                lifetimeDiffA,
                lifetimeDiffR,
                lifetimeDiffS,


                // всего миссий и разница в сравнении по датам
                missionA,
                missionR,
                missionS,
                missionDiffA,
                missionDiffR,
                missionDiffS,

                testVar,
                resultA,
                resultR,
                resultS;

        String[] reaply = new String[4];

        try {
            Document doc = Jsoup.connect("http://thunderskill.com/ru/stat/" + playerName).get();
            preferMode = doc.select("p[class^=prefer ]").text();
            lastUpdateAndComparison = doc.select("p.stat_dt").text()
            .split(",");
            lastUpdate = lastUpdateAndComparison[0];
            comparsion = lastUpdateAndComparison[1].replace(" сравнение", "Сравнение");


            Elements elementsA = doc.select("div.four.columns.text-center").eq(0);
            Elements elementsR = doc.select("div.four.columns.text-center").eq(1);
            Elements elementsS = doc.select("div.four.columns.text-center").eq(2);

            rankA = elementsA.select("div[class^=resume ]").text();
            rankR = elementsR.select("div[class^=resume ]").text();
            rankS = elementsS.select("div[class^=resume ]").text();

            kpdA = elementsA.select("li.list-group-item").eq(0).select("div.kpd_value").text();
            kpdR = elementsR.select("li.list-group-item").eq(0).select("div.kpd_value").text();
            kpdS = elementsS.select("li.list-group-item").eq(0).select("div.kpd_value").text();

            //предпочтитаемый режим (воздушный или наземный).
            preferAirOrGroundBattlesA = elementsA.select("li.list-group-item").eq(1).text();
            preferAirOrGroundBattlesR = elementsA.select("li.list-group-item").eq(1).text();
            preferAirOrGroundBattlesS = elementsA.select("li.list-group-item").eq(1).text();

            // количество воздушных и наземных боёв в процентном соотношении
            preferAirBattlesA = elementsA.select("li.list-group-item").eq(2).select("span.badge").text();
            preferGroundBattlesA = elementsA.select("li.list-group-item").eq(3).select("span.badge").text();
            preferAirBattlesR = elementsR.select("li.list-group-item").eq(2).select("span.badge").text();
            preferGroundBattlesR = elementsR.select("li.list-group-item").eq(3).select("span.badge").text();
            preferAirBattlesS = elementsS.select("li.list-group-item").eq(2).select("span.badge").text();
            preferGroundBattlesS = elementsS.select("li.list-group-item").eq(3).select("span.badge").text();

            // процент побед и разница в сравнении по дата
            winrateA = elementsA.select("li.list-group-item").eq(4).select("span.badge").text();
            winrateR = elementsR.select("li.list-group-item").eq(4).select("span.badge").text();
            winrateS = elementsS.select("li.list-group-item").eq(4).select("span.badge").text();
            winrateDiffA = elementsA.select("li.list-group-item").eq(4).select("span.diff").text();
            winrateDiffR = elementsR.select("li.list-group-item").eq(4).select("span.diff").text();
            winrateDiffS = elementsS.select("li.list-group-item").eq(4).select("span.diff").text();


            //Соотношение убийств к смертям за бой и разница в сравнении по датам
            killDeathA = elementsA.select("li.list-group-item").eq(5).select("span.badge").text();
            killDeathR = elementsR.select("li.list-group-item").eq(5).select("span.badge").text();
            killDeathS = elementsS.select("li.list-group-item").eq(5).select("span.badge").text();
            killDeathDiffA = elementsA.select("li.list-group-item").eq(5).select("span.diff").text();
            killDeathDiffR = elementsR.select("li.list-group-item").eq(5).select("span.diff").text();
            killDeathDiffS = elementsS.select("li.list-group-item").eq(5).select("span.diff").text();

            //Убийств танков за бой и разница в сравнении по датам
            killDeathGroundA = elementsA.select("li.list-group-item").eq(6).select("span.badge").text();
            killDeathGroundR = elementsR.select("li.list-group-item").eq(6).select("span.badge").text();
            killDeathGroundS = elementsS.select("li.list-group-item").eq(6).select("span.badge").text();
            killDeathGroundDiffA = elementsA.select("li.list-group-item").eq(6).select("span.diff").text();
            killDeathGroundDiffR = elementsR.select("li.list-group-item").eq(6).select("span.diff").text();
            killDeathGroundDiffS = elementsS.select("li.list-group-item").eq(6).select("span.diff").text();

            //Убийств самолетов за бой и разница в сравнении по датам
            killDeathAirA = elementsA.select("li.list-group-item").eq(7).select("span.badge").text();
            killDeathAirR = elementsR.select("li.list-group-item").eq(7).select("span.badge").text();
            killDeathAirS = elementsS.select("li.list-group-item").eq(7).select("span.badge").text();
            killDeathAirDiffA = elementsA.select("li.list-group-item").eq(7).select("span.diff").text();
            killDeathAirDiffR = elementsR.select("li.list-group-item").eq(7).select("span.diff").text();
            killDeathAirDiffS = elementsS.select("li.list-group-item").eq(7).select("span.diff").text();




            //Убийств всего за бой и разница в сравнении по датам
            killBattleA = elementsA.select("li.list-group-item").eq(5).select("span.badge").text();
            killBattleR = elementsR.select("li.list-group-item").eq(5).select("span.badge").text();
            killBattleS = elementsS.select("li.list-group-item").eq(5).select("span.badge").text();
            killBattleDiffA = elementsA.select("li.list-group-item").eq(5).select("span.diff").text();
            killBattleDiffR = elementsR.select("li.list-group-item").eq(5).select("span.diff").text();
            killBattleDiffS = elementsS.select("li.list-group-item").eq(5).select("span.diff").text();

            //Убийств танков за бой и разница в сравнении по датам
            killBattleGroundA = elementsA.select("li.list-group-item").eq(6).select("span.badge").text();
            killBattleGroundR = elementsR.select("li.list-group-item").eq(6).select("span.badge").text();
            killBattleGroundS = elementsS.select("li.list-group-item").eq(6).select("span.badge").text();
            killBattleGroundDiffA = elementsA.select("li.list-group-item").eq(6).select("span.diff").text();
            killBattleGroundDiffR = elementsR.select("li.list-group-item").eq(6).select("span.diff").text();
            killBattleGroundDiffS = elementsS.select("li.list-group-item").eq(6).select("span.diff").text();

            //Убийств самолетов за бой и разница в сравнении по датам
            killBattleAirA = elementsA.select("li.list-group-item").eq(7).select("span.badge").text();
            killBattleAirR = elementsR.select("li.list-group-item").eq(7).select("span.badge").text();
            killBattleAirS = elementsS.select("li.list-group-item").eq(7).select("span.badge").text();
            killBattleAirDiffA = elementsA.select("li.list-group-item").eq(7).select("span.diff").text();
            killBattleAirDiffR = elementsR.select("li.list-group-item").eq(7).select("span.diff").text();
            killBattleAirDiffS = elementsS.select("li.list-group-item").eq(7).select("span.diff").text();


            // Продолжительность жизни и разница в сравнении по дата
            lifetimeA = elementsA.select("li.list-group-item").eq(11).select("span.badge").text();
            lifetimeR = elementsR.select("li.list-group-item").eq(11).select("span.badge").text();
            lifetimeS = elementsS.select("li.list-group-item").eq(11).select("span.badge").text();
            lifetimeDiffA = elementsA.select("li.list-group-item").eq(11).select("span.diff").text();
            lifetimeDiffR = elementsR.select("li.list-group-item").eq(11).select("span.diff").text();
            lifetimeDiffS = elementsS.select("li.list-group-item").eq(11).select("span.diff").text();

            // всего миссий и разница в сравнении по дата
            missionA = elementsA.select("li.list-group-item").eq(12).select("span.badge").text();
            missionR = elementsR.select("li.list-group-item").eq(12).select("span.badge").text();
            missionS = elementsS.select("li.list-group-item").eq(12).select("span.badge").text();
            missionDiffA = elementsA.select("li.list-group-item").eq(12).select("span.diff").text();
            missionDiffR = elementsR.select("li.list-group-item").eq(12).select("span.diff").text();
            missionDiffS = elementsS.select("li.list-group-item").eq(12).select("span.diff").text();


            testVar = elementsA.select("li.list-group-item").eq(3).select("span.diff").text();
        } catch (IOException e) {
            e.printStackTrace();
            reaply[0] = "Не возможно получить статистику для " + playerName;
            return reaply;
        }
        if (testVar.length() >= 500)

            testVar = testVar.substring(0,500);

        resultA ="\n\n**:small_blue_diamond:" + playerName + ":small_blue_diamond: " + preferMode + "\n" + lastUpdate + "\n" + comparsion + "**" +
                "--\n:regional_indicator_a:**Аркадные бои" +
                "\n" + preferAirOrGroundBattlesA +
                "\n**Ранг: **" + rankA +
                "\n**КПД: **" + kpdA +
                "\n**Воздушных боёв: **" + preferAirBattlesA +
                "\n**Наземных боёв: **" + preferGroundBattlesA +
                "\n**Процент побед: **" + winrateA + "    " + CommonClass.setColorForDiff(winrateDiffA) +
                "\n**Всего боёв: **" + missionA + "    " + CommonClass.setColorForDiff(missionDiffA) +
                "\n**Убийства / Смерти: **" + killDeathA + "    " + CommonClass.setColorForDiff(killDeathDiffA) +
                "\n**Убито танков / смерти: **" + "/" + killDeathGroundA + "    " + CommonClass.setColorForDiff(killDeathGroundDiffA) +
                "\n**Сбито самолет" +
                "в / смерти: **" + killDeathAirA + "    " + CommonClass.setColorForDiff(killDeathAirDiffA) +
                "\n**Убийств за бой: **" + killBattleA + "    " + CommonClass.setColorForDiff(killBattleDiffA) +
                "\n**Убито танков / битв: **" + killBattleGroundA + "    " + CommonClass.setColorForDiff(killBattleGroundDiffA) +
                "\n**Сбито самолетов / битв: **" + killBattleAirA + "    " + CommonClass.setColorForDiff(killBattleAirDiffA) +
                "\n**Продолжительность жизни: **" + lifetimeA + "    " + CommonClass.setColorForDiff(lifetimeDiffA) + "**";

        resultR ="\n\n**:small_blue_diamond:" + playerName + ":small_blue_diamond:" + preferMode + "\n" + lastUpdate + "\n" + comparsion + "**" +
                "--\n:regional_indicator_r:**Реалистичные бои" +
                "\n" + preferAirOrGroundBattlesR +
                "\n**Ранг: **" + rankR +
                "\n**КПД: **" + kpdR + "%" +
                "\n**Воздушных боёв: **" + preferAirBattlesR +
                "\n**Наземных боёв: **" + preferGroundBattlesR +
                "\n**Процент побед: **" + winrateR + "    " + CommonClass.setColorForDiff(winrateDiffR) +
                "\n**Всего боёв: **" + missionR + "    " + CommonClass.setColorForDiff(missionDiffR) +
                "\n**Убийства / Смерти: **" + killDeathR + "    " + CommonClass.setColorForDiff(killDeathDiffR) +
                "\n**Убито танков / смерти: **" + "/" + killDeathGroundR + "    " + CommonClass.setColorForDiff(killDeathGroundDiffR) +
                "\n**Сбито самолетов / смерти: **" + killDeathAirR + "    " + CommonClass.setColorForDiff(killDeathAirDiffR) +
                "\n**Убийств за бой: **" + killBattleR + "    " + CommonClass.setColorForDiff(killBattleDiffR) +
                "\n**Убито танков / битв: **" + killBattleGroundR + "    " + CommonClass.setColorForDiff(killBattleGroundDiffR) +
                "\n**Сбито самолетов / битв: **" + killBattleAirR + "    " + CommonClass.setColorForDiff(killBattleAirDiffR) +
                "\n**Продолжительность жизни: **" + lifetimeR + "    " + CommonClass.setColorForDiff(lifetimeDiffR) + "**";

        resultS ="\n\n**:small_blue_diamond:" + playerName + ":small_blue_diamond:" + preferMode + "\n" + lastUpdate + "\n" + comparsion + "**" +
                "--\n:regional_indicator_s:**Симуляторные бои" +
                "\n" + preferAirOrGroundBattlesS +
                "\n**Ранг: **" + rankS +
                "\n**КПД: **" + kpdS +
                "\n**Воздушных боёв: **" + preferAirBattlesS +
                "\n**Наземных боёв: **" + preferGroundBattlesS +
                "\n**Процент побед: **" + winrateS + "    " + CommonClass.setColorForDiff(winrateDiffS) +
                "\n**Всего боёв: **" + missionS + "    " + CommonClass.setColorForDiff(missionDiffS) +
                "\n**Убийства / Смерти: **" + killDeathS + "    " + CommonClass.setColorForDiff(killDeathDiffS) +
                "\n**Убито танков / смерти: **" + "/" + killDeathGroundS + "    " + CommonClass.setColorForDiff(killDeathGroundDiffS) +
                "\n**Сбито самолетов / смерти: **" + killDeathAirS + "    " + CommonClass.setColorForDiff(killDeathAirDiffS) +
                "\n**Убийств за бой: **" + killBattleS + "    " + CommonClass.setColorForDiff(killBattleDiffS) +
                "\n**Убито танков / битв: **" + killBattleGroundS + "    " + CommonClass.setColorForDiff(killBattleGroundDiffS) +
                "\n**Сбито самолетов / битв: **" + killBattleAirS + "    " + CommonClass.setColorForDiff(killBattleAirDiffS) +
                "\n**Продолжительность жизни: **" + lifetimeS + "    " + CommonClass.setColorForDiff(lifetimeDiffS) + "**\n";

        reaply[0] = resultA;
        reaply[1] = resultR;
        reaply[2] = resultS;
        reaply[3] = testVar;

        // если привысит длину сообщения, то отсеч от аркадной статистики
//        if (reaply.length >= 2000)
//            reaply[0] = reaply[0].substring(0,2000);

//        for(int j = 0; j < reaply.length; j++) {
//            System.out.println("in action" + reaply[j]);
//        }
        return reaply;

    }
}
