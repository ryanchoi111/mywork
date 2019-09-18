import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.constant.Region;
import net.rithms.riot.constant.Season;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

/**
 * Created by smchoi18 on 1/7/17.
 */
public class Riot extends JFrame implements ActionListener {
    private static RiotApi api = new RiotApi("RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e");
    private static String tempText, tempText1;
    private static JSONObject summoner, summoner1, recentg, recentg1;
    private static long id, id1;
    private static Panel xtra = new Panel(new FlowLayout());
    private static Panel subPanel = new Panel(new BorderLayout());
    private static Font font = new Font("Times New Roman", Font.ITALIC, 12);
    private Font font1;
    private static JButton search = new JButton("Search");
    private static ArrayList<String> fields = new ArrayList<>();
    private static ArrayList<Float> val1 = new ArrayList<>();
    private static ArrayList<Float> val2 = new ArrayList<>();
    private static JTextArea textArea, textArea_1,  textArea1, textArea2,  textArea3, textArea4,textArea5, textArea_2, textArea_;
    private static JTextArea textArea6,  textArea8,  textArea9, textArea10, textArea_3, textArea__;
    private static JTextArea textArea11,  textArea12, textArea13,  textArea14,  textArea15;
    private static JTextArea textArea16,  textArea17, textArea18,  textArea19,  textArea20;
    private static JTextArea textArea21,  textArea22, textArea23,  textArea25;
    private static JTextArea textArea26, textArea27,  textArea28, textArea29,  textArea30;
    private static JTextArea textArea33, textArea34, textArea35, textArea36, textArea37, textArea38, textArea39, textArea40, textArea41, textArea42, textArea43, textArea44, textArea45, textArea46, textArea47, textArea48;
    static HashMap<String, Double> averages = new HashMap<String, Double>();
    static HashMap<String, Double> averages1 = new HashMap<String, Double>();
    static String p;
    static String l;
    static String input;
    static String input1;
    static Panel b1 = new Panel(new GridLayout(23, 2));
    static Panel b2 = new Panel(new GridLayout(23, 2));


    public static void main(String[] args) throws RiotApiException {

        api.setSeason(Season.CURRENT);
        api.setRegion(Region.NA);
        System.out.println("Type in first user: ");
        Scanner sc = new Scanner(System.in);
        String i = sc.nextLine().toLowerCase();
        l = i;
        System.out.println("Type in second user: ");
        Scanner s = new Scanner(System.in);
        String o = s.nextLine().toLowerCase();
        p = o;


        String parta = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
        String partb = "?api_key=RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e";
        String part2a = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
        String part2b = "?api_key=RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e";
        String url = parta + i + partb;
        String url2 = part2a + o + part2b;


        try {

            tempText = read(url);   //read in the menu from a specified day.
            tempText1 = read(url2);

            summoner = new JSONObject(tempText);
            summoner1 = new JSONObject(tempText1);


            id = (long) (summoner.getJSONObject(i).getInt("id"));
            id1 = (long) (summoner1.getJSONObject(o).getInt("id"));


            System.out.println("Summoner ID for User 1: " + id);
            System.out.println("Summoner ID for User 2: " + id1);





        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        //setUpWindow1();
        Riot r = new Riot();



    }

    public Riot() {



        setUpWindow();


    }

    private static long[] returnGameID(JSONObject recentg) throws Exception {
        JSONArray recentgames = recentg.getJSONArray("games"); //look in field of "games"
        long[] gameIDS = new long[recentgames.length()]; //finds length of array "games"

        for (int i = 0; i < recentgames.length(); i++) {
            JSONObject game = recentgames.getJSONObject(i); //goes through JSONArray "games"
            gameIDS[i] = (long) game.get("gameId"); //pulls out gameId
        }

        return gameIDS;
    }

    private static String read(String url) {
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

            while (line != null) {
                sb.append(line);
                line = rd.readLine();
            }


            jsonText = sb.toString();  //convert the StringBuilder object to a String.
            // System.out.println("JSON Text Read in");
            // System.out.println();
            // System.out.println(""+jsonText);
            is.close();  //close up your input stream
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (jsonText);
    }

    public static HashMap<String, Integer> getStats(JSONObject game) throws Exception {
        HashMap<String, Integer> stat = new HashMap<String, Integer>();

        Iterator<?> keys = game.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (key.equals("win"))
                continue;

            int val = (int) game.get(key);
            stat.put(key, val);
        }

        return stat;
    }


    public static void RecentGames() {
        String u1 = "https://na.api.pvp.net/api/lol/na/v1.3/game/by-summoner/";
        String u2 = "/recent?api_key=RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e";
        String c1 = u1 + id + u2;
        String c2 = u1 + id1 + u2;


        try {
            // System.out.println("Data for User 1");
            tempText = read(c1);
            // System.out.println("Data for User 2");
            tempText1 = read(c2);
            recentg = new JSONObject(tempText);
            recentg1 = new JSONObject(tempText1);

            System.out.println("recentg: " + recentg); //prints JSON of recent games of User 1
            System.out.println("");
            System.out.println("recentg1: " + recentg1); //prints JSON of recent games of User 2


            long[] gameid1 = returnGameID(recentg); //gets gameId in JSONArray
            long[] gameid2 = returnGameID(recentg1);

            JSONArray recentgames = recentg.getJSONArray("games");//array of "games" in JSON file
            JSONArray recentgames1 = recentg1.getJSONArray("games");

            ArrayList<HashMap<String, Integer>> allStats = new ArrayList<HashMap<String, Integer>>();
            ArrayList<HashMap<String, Integer>> allStats1 = new ArrayList<HashMap<String, Integer>>();


            System.out.println("Games for USER 1: ");
            for (int i = 0; i < recentgames.length(); i++) {
                JSONObject game = recentgames.getJSONObject(i); //goes through "games"
                HashMap<String, Integer> stats = getStats((JSONObject) game.get("stats")); //gets stats of those different games
                System.out.println("GAME NUMBER: " + (i + 1));
                System.out.println("GAME STATS: " + stats);
                System.out.println("");
                allStats.add(stats);
            }

            System.out.println("");
            System.out.println("");
            System.out.println("Games for USER 2: ");
            for (int o = 0; o < recentgames1.length(); o++) {
                JSONObject game = recentgames1.getJSONObject(o);
                HashMap<String, Integer> stats1 = getStats((JSONObject) game.get("stats")); //
                System.out.println("GAME NUMBER: " + (o + 1));
                System.out.println("GAME STATS: " + stats1);
                System.out.println("");
                allStats1.add(stats1);
            }


            System.out.println("");
            System.out.println("");
            System.out.println("Total Stats for User 1: ");
            for (String key : allStats.get(0).keySet()) {
                System.out.println("key: " + key);
                double total = 0;
                int amt = 0; //how many games has this stat
                for (int i = 0; i < allStats.size(); i++) {
                    if (allStats.get(i).containsKey(key)) {
                        total += allStats.get(i).get(key);
                        amt++;
                    }

                }
                System.out.println("amt: " + amt);
                System.out.println("total: " + total);
                total /= amt;
                averages.put(key, total);
                if (key.contains("item")) {
                    continue;
                }
                if (key.contains("player")) {
                    continue;
                }
                if (key.equals("wardPlaced")) {
                    continue;
                }
                if (key.equals("level")) {
                    continue;
                }
                if (key.equals("team")) {
                    continue;
                }
                if (key.equals("bountyLevel")) {
                    continue;
                }
                if( key.equals("visionWardsBought")){
                    continue;
                }else {
                    fields.add(key);
                }

                val2.add((float) total);

                for (int o = 0; o <= fields.size(); o++) {
                    if (key.contains("item")) {
                        val2.remove(val2.get(1));
                        val2.remove(val2.get(3));
                        val2.remove(val2.get(4));
                        val2.remove(val2.get(28));
                        val2.remove(val2.get(31));
                        val2.remove(val2.get(35));
                        val2.remove(val2.get(36));
                    }
                    if (key.contains("player")) {
                        val2.remove(val2.get(14));
                        val2.remove(val2.get(31));
                    }
                    if (key.equals("wardPlaced")) {
                        val2.remove(val2.get(21));
                    }

                    if (key.equals("level")) {
                        val2.remove(val2.get(19));
                    }
                    if (key.equals("team")) {
                        val2.remove(val2.get(17));
                    }

                }

            }

            System.out.println("");
            System.out.println("");
            System.out.println("Total stats for User 2: ");
            for (String key : allStats.get(0).keySet()) {
                System.out.println("key: " + key);
                double total = 0;
                int amt = 0; //how many games has this stat
                for (int i = 0; i < allStats1.size(); i++) {
                    if (allStats1.get(i).containsKey(key)) {
                        total += allStats1.get(i).get(key);
                        amt++;
                    }

                }
                System.out.println("amt: " + amt);
                System.out.println("total: " + total);
                total /= amt;
                averages1.put(key, total);
                if (key.contains("item")) {
                    continue;
                }
                if (key.contains("player")) {
                    continue;
                }
                if (key.equals("wardPlaced")) {
                    continue;
                }
                if (key.equals("level")) {
                    continue;
                }
                if (key.equals("team")) {
                    continue;
                }
                if (key.equals("bountyLevel")) {
                    continue;
                }
                if( key.equals("visionWardsBought")){
                    continue;
                } else {
                    fields.add(key);
                }

                val1.add((float) total);


                for (int o = 0; o <= fields.size(); o++) {
                    if (key.contains("item")) {
                        val1.remove(val1.get(1));
                        val1.remove(val1.get(4));
                        val1.remove(val1.get(5));
                        val1.remove(val1.get(28));
                        val1.remove(val1.get(32));
                        val1.remove(val1.get(35));
                        val1.remove(val1.get(39));
                    }
                    if (key.contains("player")) {
                        val1.remove(val1.get(16));
                        val1.remove(val1.get(37));
                    }
                    if (key.equals("wardPlaced") || key.contains("Wards")) {
                        val1.remove(val1.get(21));
                        val1.remove(val1.get(9));
                        val1.remove(val1.get(26));
                    }

                    if (key.equals("level")) {
                        val1.remove(val1.get(19));
                    }
                    if (key.equals("team")) {
                        val1.remove(val1.get(23));
                    }

                }

            }


            System.out.println(fields);
            System.out.println("AVERAGE STATS for USER 1: " + averages); //gets average stats
            System.out.println("AVERAGE STATS for USER 2: " + averages1);

            System.out.println("USER 1: Game ID's" + Arrays.toString(gameid1));//prints gameIDs of the 10 games
            System.out.println("USER 2: Game ID's" + Arrays.toString(gameid2));
            System.out.println(val2);
            System.out.println(val1);


        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        System.out.println(c1);
        System.out.println(c2);
        return;
    }


    public void setUpWindow() {


    //    font1 = new Font("Helvetica", Font.BOLD, 12);

        System.out.println("Setting up the window");
        //set the layout of the main container (JFrame)


      //  textArea = new JTextArea("User 1: " + input);


     //   textArea_1 = new JTextArea("User 2: " + input1);
        Panel panel = new Panel(new GridLayout(3, 1));
        Panel panel1 = new Panel(new GridLayout(3,1));
        Panel pnl2 = new Panel(new FlowLayout());
        Panel pnl1 = new Panel(new FlowLayout());
        Panel pnl3 = new Panel(new GridBagLayout());

        textArea_2 = new JTextArea("Type in User 1:");
        textArea_ = new JTextArea(" ");
        textArea_2.setEditable(false);
        pnl2.add(textArea_2);
        pnl2.add(textArea_);



        textArea_3 = new JTextArea("Type in User 2:");
        textArea__ = new JTextArea(" ");
        textArea_3.setEditable(false);
        pnl1.add(textArea_3);
        pnl1.add(textArea__);



        pnl3.add(search);
        panel1.add(pnl2);
        panel1.add(pnl1);
        panel1.add(pnl3);


        search.addActionListener(this);






        //create the panel






        ImageIcon image = new ImageIcon(getClass().getResource("leaguel.png"));
        Image img = image.getImage();
        Image newi = img.getScaledInstance(502, 198, Image.SCALE_SMOOTH);
        ImageIcon imge1 = new ImageIcon(newi);

     //   ImageIcon image1 = new ImageIcon(getClass().getResource("luc1.png"));
      //  Image img1 = image1.getImage();
       // Image newi1 = img1.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
       // ImageIcon imge2 = new ImageIcon(newi1);

       // ImageIcon image2 = new ImageIcon(getClass().getResource("zig.jpg"));
       // Image img2 = image2.getImage();
       // Image newi2 = img2.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
       // ImageIcon imge3 = new ImageIcon(newi2);


        ImageIcon image3 = new ImageIcon(getClass().getResource("riot.png"));
        Image img3 = image3.getImage();
        Image newi3 = img3.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
        ImageIcon imge4 = new ImageIcon(newi3);

        JLabel label = new JLabel("", imge1, JLabel.CENTER);

      //  JLabel l2 = new JLabel("", imge3, JLabel.CENTER);
        JLabel l1 = new JLabel("", imge4, JLabel.CENTER);

        Panel b = new Panel(new GridLayout(2, 1));
        Panel c = new Panel(new FlowLayout());

        panel.add(label);

        panel.add(b);
        panel.add(panel1);

        panel.add(l1);



    //    panel.add(l2);
        //Color myColor = Color.decode("#3468C3");




        //     b2.setBackground(myColor);
        //   b1.setBackground(myColor);
        // b.setBackground(myColor);


        add(b1, BorderLayout.EAST);
        add(b2, BorderLayout.WEST);

        add(panel, BorderLayout.CENTER);






        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1980, 1080);

        setVisible(true);


       // setBackground(myColor);




}




    @Override
    public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand().equals("search"));


        String parta = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
        String partb = "?api_key=RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e";
        String part2a = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
        String part2b = "?api_key=RGAPI-19cc41b4-6aba-46ff-955d-98a86adc611e";
        input.replaceAll(" ","");
        input = textArea_.getText().toLowerCase();


        input1.replaceAll(" ","");
        input1 = textArea__.getText().toLowerCase();

        String url = parta + input + partb;
        String url2 = part2a + input1 + part2b;

        try{

            tempText = read(url);   //read in the menu from a specified day.
            tempText1 = read(url2);

            summoner = new JSONObject(tempText);
            summoner1 = new JSONObject(tempText1);


            id = (long) (summoner.getJSONObject(input).getInt("id"));
            id1 = (long) (summoner1.getJSONObject(input1).getInt("id"));


            System.out.println("Summoner ID for User 1: " + id);
            System.out.println("Summoner ID for User 2: " + id1);




        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
        RecentGames();
        values();


    }
    public static void values(){

        textArea1 = new JTextArea(fields.get(0) + ":" + val2.get(0));


        textArea2 = new JTextArea(fields.get(1) + ":" + String.valueOf(val2.get(1)));
      //  textArea2.setFont(font1);


        textArea3 = new JTextArea(fields.get(2) + ":" + String.valueOf(val2.get(2)));

     //   textArea3.setFont(font1);

        textArea4 = new JTextArea(fields.get(3) + ":" + String.valueOf(val2.get(3)));


      //  textArea4.setFont(font1);

        textArea5 = new JTextArea(fields.get(4) + ":" + String.valueOf(val2.get(4)));

     //   textArea5.setFont(font1);




       textArea6 = new JTextArea(fields.get(5) + ":" + String.valueOf(val2.get(5)));




        textArea8 = new JTextArea(fields.get(7) + ":" + String.valueOf(val2.get(7)));


        textArea9 = new JTextArea(fields.get(8) + ":" + String.valueOf(val2.get(8)));


        textArea10 = new JTextArea(fields.get(9) + ":" + String.valueOf(val2.get(9)));

        textArea11 = new JTextArea(fields.get(10) + ":" + String.valueOf(val2.get(10)));


        textArea12 = new JTextArea(fields.get(11) + ":" + String.valueOf(val2.get(11)));


        textArea13 = new JTextArea(fields.get(12) + ":" + String.valueOf(val2.get(12)));


        textArea14 = new JTextArea(fields.get(13) + ":" + String.valueOf(val2.get(13)));


        textArea15 = new JTextArea(fields.get(14) + ":" + String.valueOf(val2.get(14)));


        textArea16 = new JTextArea(fields.get(15) + ":" + String.valueOf(val2.get(15)));


        textArea17 = new JTextArea(fields.get(16) + ":" + String.valueOf(val2.get(16)));


        textArea18 = new JTextArea(fields.get(17) + ":" + String.valueOf(val2.get(17)));


        textArea19 = new JTextArea(fields.get(18) + ":" + String.valueOf(val2.get(18)));


        textArea20 = new JTextArea(fields.get(19) + ":" + String.valueOf(val2.get(19)));


        textArea21 = new JTextArea(fields.get(20) + ":" + String.valueOf(val2.get(20)));


        textArea22 = new JTextArea(fields.get(21) + ":" + String.valueOf(val2.get(21)));


        textArea23 = new JTextArea(fields.get(22) + ":" + String.valueOf(val2.get(22)));






         ///////////   USER 2 STUFF    //////////
            /////////////////////////////////



        textArea25 = new JTextArea(fields.get(0) + ":" + val1.get(0));



        textArea26 = new JTextArea(fields.get(1) + ":" + val1.get(1));
        //textArea26.setFont(font1);


        textArea27 = new JTextArea(fields.get(2) + ":" + val1.get(2));

        //  textArea27.setFont(font1);

        textArea28 = new JTextArea(fields.get(3) + ":" + val1.get(3));

        //  textArea28.setFont(font1);

        textArea29 = new JTextArea(fields.get(4) + ":" + val1.get(4));
        //textArea29.setFont(font1);



       textArea30 = new JTextArea(fields.get(5) + ":" + val1.get(5));
        // textArea30.setFont(font1);


        textArea33 = new JTextArea(fields.get(7) + ":" + val1.get(7));


        textArea34 = new JTextArea(fields.get(8) + ":" + val1.get(8));


        textArea35 = new JTextArea(fields.get(9) + ":" + val1.get(9));


        textArea36 = new JTextArea(fields.get(10) + ":" + val1.get(10));


        textArea37 = new JTextArea(fields.get(11) + ":" + val1.get(11));


        textArea38 = new JTextArea(fields.get(12) + ":" + val1.get(12));


        textArea39 = new JTextArea(fields.get(13) + ":" + val1.get(13));


        textArea40 = new JTextArea(fields.get(14) + ":" + val1.get(14));


        textArea41 = new JTextArea(fields.get(15) + ":" + val1.get(15));

        textArea42 = new JTextArea(fields.get(16) + ":" + val1.get(16));


        textArea43 = new JTextArea(fields.get(17) + ":" + val1.get(17));


        textArea44 = new JTextArea(fields.get(18) + ":" + val1.get(18));


        textArea45 = new JTextArea(fields.get(19) + ":" + val1.get(19));


        textArea46 = new JTextArea(fields.get(20) + ":" + val1.get(20));


        textArea47 = new JTextArea(fields.get(21) + ":" + val1.get(21));



       textArea48 = new JTextArea(fields.get(22) + ":" + val1.get(22));




      b2.add(textArea);
      b2.add(textArea1);
        b2.add(textArea2);
        b2.add(textArea3);
        b2.add(textArea4);
        b2.add(textArea5);
        b2.add(textArea6);

        b2.add(textArea8);
        b2.add(textArea9);
        b2.add(textArea10);
        b2.add(textArea11);
        b2.add(textArea12);
        b2.add(textArea13);
        b2.add(textArea14);
        b2.add(textArea15);
        b2.add(textArea16);
        b2.add(textArea17);
        b2.add(textArea18);
        b2.add(textArea19);
        b2.add(textArea20);
        b2.add(textArea21);
        b2.add(textArea22);
        b2.add(textArea23);
      b1.add(textArea_1);
        b1.add(textArea25);
        b1.add(textArea26);
        b1.add(textArea27);
        b1.add(textArea28);
        b1.add(textArea29);
        b1.add(textArea30);

        b1.add(textArea33);
        b1.add(textArea34);
        b1.add(textArea35);
        b1.add(textArea36);
        b1.add(textArea37);
        b1.add(textArea38);
        b1.add(textArea39);
        b1.add(textArea40);
        b1.add(textArea41);
        b1.add(textArea42);
        b1.add(textArea43);
        b1.add(textArea44);
        b1.add(textArea45);
        b1.add(textArea46);
        b1.add(textArea47);
        b1.add(textArea48);

    }



}