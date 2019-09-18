import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.constant.Region;
import net.rithms.riot.constant.Season;
import net.rithms.riot.dto.Game.RecentGames;
import net.rithms.riot.dto.Stats.AggregatedStats;
import net.rithms.riot.dto.Stats.RankedStats;
import net.rithms.riot.dto.Summoner.Summoner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by smchoi18 on 1/7/17.
 */
public class Riot {
    private static RiotApi api = new RiotApi("RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e");
    private static String tempText, tempText1;
    private static JSONObject summoner, summoner1, recentg, recentg1;
    private static JSONArray gameid, gameid1,  matchid;
    private static long id, id1;



    public static void main(String[] args) throws RiotApiException {
        RiotApi api = new RiotApi("RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e");
        api.setSeason(Season.CURRENT);
        api.setRegion(Region.NA);
        System.out.println("Type in first user: ");
        Scanner sc = new Scanner(System.in);
        String i = sc.nextLine().toLowerCase();
        System.out.println("Type in second user: ");
        Scanner s = new Scanner(System.in);
        String o = s.nextLine().toLowerCase();

        String parta = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
        String partb = "?api_key=RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e";
        String part2a = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
        String part2b = "?api_key=RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e";
        String url = parta+i+partb;
        String url2 = part2a+o+part2b;




        try {

            tempText = read(url);   //read in the menu from a specified day.
            tempText1 = read(url2);

            summoner= new JSONObject(tempText);
            summoner1= new JSONObject(tempText1);
            //name = summoner.getJSONObject(i);

            id = (long)(summoner.getJSONObject(i).getInt("id"));
            id1 = (long)(summoner1.getJSONObject(o).getInt("id"));


            System.out.println("Summoner ID for User 1: " +id);
            System.out.println("Summoner ID for User 2: " +id1);
            //System.out.println(url);
            //System.out.println(url2);

            RecentGames();


        }catch(Exception ex)
            {System.out.println(ex.toString());}





        //Rankedstats(id);
       // Rankedstats1(id1);

    }



/*
        public static void Rankedstats(long id) throws RiotApiException{

                RankedStats rankedStats = api.getRankedStats(Region.NA, id);

                AggregatedStats allChampionsStats = rankedStats.getChampions().get(0).getStats();

                int wins = allChampionsStats.getTotalSessionsWon();
                int losses = allChampionsStats.getTotalSessionsLost();
                int dmgD = allChampionsStats.getTotalDamageDealt();
                int dmgT = allChampionsStats.getTotalDamageTaken();
                int assists = allChampionsStats.getTotalAssists();
                int kills = allChampionsStats.getTotalChampionKills();
                int heal = allChampionsStats.getTotalHeal();
                int g = allChampionsStats.getTotalGoldEarned();
                int minionKills = allChampionsStats.getTotalMinionKills();



            System.out.println("");
            System.out.println("Data of User 1: ");
                System.out.println("Wins: " + wins);
                System.out.println("Losses: " + losses);
                System.out.println("Kills: " + kills);
                System.out.println("Assists: " + assists);
                System.out.println("Damage Dealt: " + dmgD);
                System.out.println("Damage Taken: " + dmgT);
                System.out.println("Amount Healed: " + heal);
                System.out.println("Gold Earned: " + g);
                System.out.println("CS: " + minionKills);








            }
    public static void Rankedstats1(long id1) throws RiotApiException {

        RankedStats rankedStats1 = api.getRankedStats(Region.NA, id1);
        AggregatedStats allChampionsStats1 = rankedStats1.getChampions().get(0).getStats();
        int wins1 = allChampionsSftats1.getTotalSessionsWon();
        int losses1 = allChampionsStats1.getTotalSessionsLost();
        int dmgD1 = allChampionsStats1.getTotalDamageDealt();
        int dmgT1 = allChampionsStats1.getTotalDamageTaken();
        int assists1 = allChampionsStats1.getTotalAssists();
        int kills1 = allChampionsStats1.getTotalChampionKills();
        int heal1 = allChampionsStats1.getTotalHeal();
        int g1 = allChampionsStats1.getTotalGoldEarned();
        int minionKills1 = allChampionsStats1.getTotalMinionKills();

        System.out.println("");
        System.out.println("Data of User 2: ");
        System.out.println("Wins: " + wins1);
        System.out.println("Losses: " + losses1);
        System.out.println("Kills: " + kills1);
        System.out.println("Assists: " + assists1);
        System.out.println("Damage Dealt: " + dmgD1);
        System.out.println("Damage Taken: " + dmgT1);
        System.out.println("Amount Healed: " + heal1);
        System.out.println("Gold Earned: " + g1);
        System.out.println("CS: " + minionKills1);
    }
    */

    private static String read(String url){
        InputStream is;
        String jsonText = "";
        try {

            System.out.println(" Connecting to URL");
            is = new URL(url).openStream();  //construct new input stream
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));  //put into Buffered Reader
            StringBuilder sb = new StringBuilder(); //declare and construct a new StringBuilder to put the data read from the URL into

          /*
          //Alternate Method
          int characterNumber;
          //read in each character from the
         while ((characterNumber = rd.read()) != -1) {
            sb.append((char) characterNumber);
         }
           */


            String line;
            line = rd.readLine();  //read in one line of the Buffered Reader

            while (line != null)
            {
                sb.append(line);
                line = rd.readLine();
            }


            jsonText=sb.toString();  //convert the StringBuilder object to a String.
            System.out.println("JSON Text Read in");
            System.out.println();
            System.out.println(""+jsonText);
            is.close();  //close up your input stream
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return(jsonText);
        }



    public static void RecentGames(){
            String u1 = "https://na.api.pvp.net/api/lol/na/v1.3/game/by-summoner/";
            String u2 = "/recent?api_key=RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e";
            String c1 = u1+id+u2;
            String c2 = u1+id1+u2;



        try {
            System.out.println("Data for User 1");
            tempText = read(c1);
            System.out.println("Data for User 2");
            tempText1 = read(c2);
            recentg= new JSONObject(tempText);
            recentg1= new JSONObject(tempText1);

            long[] gameid1 = returnGameID(recentg);
            long[] gameid2 = returnGameID(recentg1);
          //  gameid = recentg.getJSONObject("summonerId").getJSONArray("games");

           // gameid1 = recentg1.getJSONArray("games");



            System.out.println(recentgames);


           // System.out.println(gameid1);




        }catch(Exception ex)
        {System.out.println(ex.toString());}

        System.out.println(c1);
        System.out.println(c2);

    }
}

        







