import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

//JSON Libraries
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// THANK YOU IVAN
// info on buffered reader
//http://www.java67.com/2016/06/5-difference-between-bufferedreader-and-scanner-in-java.html
// Json objects have {  Json array have [
// below is a google doc link that has the jar file and json and java explained 
//https://docs.google.com/a/milton.edu/document/d/1lQdce7mAX2KipHlXhppWbkq4KZ1-ThLw8i_4Q8iFk_0/edit?usp=sharing

public class ReadParseJson{

   private String milton = "http://www.milton.edu";
   private String menuURL= "http://flik.ma1geek.org/getMeals.php?date=2016-12-12";
   private JSONObject menuJO,lunchObject;
   private String tempText;
   private JSONArray sidesJA, entrees, vegan;




   public static void main(String[] args) {
      System.out.println("Read...");
      ReadParseJson m = new ReadParseJson();
   }



   private ReadParseJson(){
      System.out.println("Starting to read String...");

      try{
         tempText=read(menuURL);   //read in the menu from a specified day.
        // System.out.println("Text Read In   ----   "+tempText);
          System.out.println();
          System.out.println(tempText + "asdfasdf");
         menuJO= new JSONObject(tempText);  //create a JSON Object from the text read in.

         lunchObject= menuJO.getJSONObject("Dinner"); // This gets the lunch from the json object menuJSO
          entrees = lunchObject.getJSONArray("Entree");
          vegan = lunchObject.getJSONArray("Vegan");
          sidesJA= lunchObject.getJSONArray("Sides");// this gets the json array with the side dishes the json object lunchObject



         // System.out.println("Lunch Side Dishes in JSON format");
            System.out.println("Dinner entrees and vegan in JSON Format");

          //goes through the lunch side dish JSON Array and prints out the JSON text
          /*
          for(int x=0;x<sidesJA.length();x++)
         {
            System.out.println("ARRAY"+sidesJA.getJSONObject(x).toString());
         
         }
         */
         for (int y = 0; y< vegan.length();y++){
              System.out.println("ARRAY" + vegan.getJSONObject(y).toString());
         }
          for (int z = 0; z< entrees.length();z++){
              System.out.println("ARRAY" + entrees.getJSONObject(z).toString());
          }

         /*
         for( int x=0;x<entrees.length();x++)
         {

             System.out.println("ARRAY"+entrees.getJSONObject(x).toString());

         }
        */
          System.out.println();
          System.out.println();
         // System.out.println("Lunch Side Dishes in Plain Text Format");
          System.out.println("Dinner vegan and entrees in Plain Text Format");
          System.out.println("We get the string with the key mealName");


        /*
          for(int x=0;x<sidesJA.length();x++)
         {
            System.out.println(sidesJA.getJSONObject(x).getString("mealTime")+"  "+sidesJA.getJSONObject(x).getString("mealName"));

         }
         */
          for(int x=0;x<vegan.length();x++)
          {
              System.out.println(vegan.getJSONObject(x).getString("mealTime")+"  "+vegan.getJSONObject(x).getString("mealName"));

          }
          for(int x=0;x<entrees.length();x++)
          {
              System.out.println(entrees.getJSONObject(x).getString("mealTime")+"  "+entrees.getJSONObject(x).getString("mealName"));

          }
          System.out.println(menuJO);

          System.out.println();
          System.out.println();
         /*
         **
            I do not have a food class but if I did then sides[x] would be an array of sides
         for(int x=0;x<breakJA.length();x++)
         {
            sides[x]=new food(breakJA.getJSONObject(x).getString("mealName"));
         
         }
         
         */
      
      
         System.out.println("   ");

      }
      catch(Exception ex)
      {System.out.println(ex.toString());}
      
   }

   /*
   Reads in text from a URL using a Buffered Reader

    */
   private String read(String url){
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
