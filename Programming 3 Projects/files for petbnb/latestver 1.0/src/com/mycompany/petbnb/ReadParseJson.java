package com.mycompany.petbnb;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//JSON Libraries
//import org.json.JSONArray;
//import org.json.JSONObject;
import com.codename1.io.JSONParser;

//Created by Tanay Srivastava 11/21/17

// THANK YOU IVAN
// info on buffered reader
//http://www.java67.com/2016/06/5-difference-between-bufferedreader-and-scanner-in-java.html
// Json objects have {  Json array have [
// below is a google doc link that has the jar file and json and java explained
//https://docs.google.com/a/milton.edu/document/d/1lQdce7mAX2KipHlXhppWbkq4KZ1-ThLw8i_4Q8iFk_0/edit?usp=sharing

public class ReadParseJson{

    private String milton = "http://www.milton.edu";
    private String userURL = "http://kjc.ma1geek.org/test.php";
    //private JSONArray userJO;
    //private JSONObject nameObject;
    private String tempText;
    //private JSONArray date, first, last;
    String[] myArray = new String[5];



    public static void main(String[] args) {
        System.out.println("Read...");
        ReadParseJson m = new ReadParseJson();
    }

    public String[] getArray() {




        /*try {
            tempText = read(userURL);   //read in the menu from a specified day.
            // System.out.println("Text Read In   ----   "+tempText);
            System.out.println();
            System.out.println();

            userJO = new JSONArray(tempText);
            for (int i = 0; i < userJO.length(); i++) {
                JSONObject user = (JSONObject) userJO.get(i);

                myArray[i] = new String((user.getString("first_name")));


            }

        }
        catch(Exception ex)
        {System.out.println(ex.toString());}*/

        return myArray;

    }

    public ArrayList<String> ReadParseJson(){
        System.out.println("Starting to read String...");

        tempText = read(userURL);

        JSONParser json = new JSONParser();
        ArrayList<String> data = new ArrayList<>();
        try {
            Map<String, Object> obj = json.parseJSON(new StringReader(tempText));
            List<Map<String, Object>> users = (List<Map<String, Object>>) obj.get("root");
            for (Map<String, Object> user : users){
                data.add(user.get("first_name").toString());
            }
            for (Map<String, Object> user : users) {
               //System.out.println(user.get("last_name"));
                //System.out.println("data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }

    /*
    Reads in text from a URL using a Buffered Reader

     */
    public String read(String url){
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


}
