//Dan Adair's serializable demo  changed by chris hales


import com.sun.jdi.IntegerValue;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.FileWriter;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.*;

public class CsvWriterLayout extends JFrame implements ActionListener
{

   public Player highScorers[];
   public int arrayLength=25;

   Panel xtra = new Panel(new FlowLayout());
   JButton save;
   Font font = new Font ("Times New Roman", Font.ITALIC, 12);
   Font font1 = new Font("Helvetica", Font.BOLD, 12);
   public int counter=0;
   //public static CsvWriter thisApp;
   JTextArea  textArea, textArea_2, textArea1, textArea1_1, textArea2, textArea3, textArea3_1, textArea4, textArea4_1, textArea5, textArea5_1, textArea6, textArea6_1, textArea7, textArea7_1;
   String eName, eTeam, eG, eFG, eFT, eP, ePPG;
   JPanel  subPanel = new JPanel();

      
    ArrayList<Player> players;
   
   
   String newline = "";
   
   int arrayIndex=0;
   public static void main(String[] args)
   {
      System.out.println("Main");
      CsvWriterLayout thisApp=new CsvWriterLayout();		// calls the saveload constructer and makes a saveload

        
   }
   public  CsvWriterLayout()
   {

      super("WNBA Layout");


      System.out.println("Constructor");
      highScorers= new Player[arrayLength];

      highScorers[0]= new Player("Katie Smith", "Minnesota Lynx", 32, 204, 246, 739, 23.1);
      highScorers[1]= new Player("Lisa Leslie", "LA Sparks", 31, 221, 142, 606, 19.5);
      highScorers[2]= new Player("Tina Thompson", "Houston Comets", 30, 199, 137, 579, 19.3);
      highScorers[3]= new Player("Janeth Arcain", "Houston Comets",32, 217, 135, 591, 18.5);
      highScorers[4]= new Player("Chamique Holdsclaw", "Washington Mystics", 29, 187, 101, 486, 16.8);
      highScorers[5]= new Player("Yolanda Griffith", "Sacremento Monarchs", 32, 192, 134, 518, 16.2);
      highScorers[6]= new Player("Tari Phillips", "New York Liberty", 32, 208, 73, 489, 15.3);
      highScorers[7]= new Player("Lauren Jackson", "Seattle Storm", 29, 149, 104, 442, 15.2);
      highScorers[8]= new Player("Jackie Stiles", "Portland Fire", 32, 156, 116, 478, 14.9);
      highScorers[9]= new Player("Natalie Williams", "Utah Starzz", 31, 171, 97, 439, 14.2);
      highScorers[10]= new Player("Andrea Stinson", "Charlote Sting", 32, 179, 63, 450, 14.1);
      highScorers[11]= new Player("Sheri Sam", "Miami Sol", 32, 180, 57, 444, 13.9);
      highScorers[12]= new Player("Nykesha Sales", "Orlando Miracle", 32, 166, 58, 433, 13.5);
      highScorers[13]= new Player("Merlakia Jones", "Cleveland Rockers", 30, 165, 65, 404, 13.5);
      highScorers[14]= new Player("Svetlana Abrosimova", "Minnesota Lynx", 26, 114, 96, 343, 13.2);
      highScorers[15]= new Player("Sandy Brondello", "Miami Sol", 29, 142, 57, 367, 12.7);
      highScorers[16]= new Player("Taj McWilliams-Franklin", "Orlando Miracle", 32, 157, 87, 403, 12.6);
      highScorers[17]= new Player("Jennifer Gillom", "Phoenix Mercury", 32, 150, 71, 395, 12.3);
      highScorers[18]= new Player("Adrienne Goodson", "Utah Starzz", 28, 138, 62, 343, 12.3);
      highScorers[19]= new Player("Sophia Witherspoon", "Portland Fire", 31, 113, 90, 373, 12);
      highScorers[20]= new Player("Rita Williams", "Indiana Fever", 32, 115, 109, 380, 11.9);
      highScorers[21]= new Player("Elena Baranova", "Miami Sol", 32, 141, 66, 378, 11.8);
      highScorers[22]= new Player("Astou Ndiaye", "Detroit Shock", 32, 156, 59, 376, 11.8);
      highScorers[23]= new Player("Tamecka Dixon", "LA Sparks", 29, 133, 68, 340, 11.7);
      highScorers[24]= new Player("Shannon Johnson", "Orlando Miracle", 26, 90, 84, 302, 11.6);

      readCSV1();

      setUpWindow();

   }

   public void setUpWindow()
   {
   
      JButton bone, btwo, bthree, bfour ,bfive;
      JButton prev, next, create, delete, edit, load;
      JTextArea output;

      
      System.out.println("Setting up the window");
      //set the layout of the main container (JFrame)
      setLayout(new BorderLayout());


      textArea = new JTextArea("Name: ");
      textArea_2 = new JTextArea(players.get(arrayIndex).N);
      textArea.setFont(font1);
      textArea_2.setFont(font1);
      textArea1 = new JTextArea("Team: ");
      textArea1_1 = new JTextArea(players.get(arrayIndex).T);
      textArea1.setFont(font1);
      textArea1_1.setFont(font1);
      textArea2 = new JTextArea(""+arrayIndex);
      textArea2.setFont(font1);
      textArea3 = new JTextArea("Games Played : ");
      textArea3_1 = new JTextArea(players.get(arrayIndex).G);
      textArea3.setFont(font1);
      textArea4 = new JTextArea("Field Goals Made: ");
      textArea4_1 = new JTextArea(players.get(arrayIndex).FG);
      textArea4.setFont(font1);
      textArea5 = new JTextArea("Free Throws Made: ");
      textArea5_1 = new JTextArea(players.get(arrayIndex).FT);
      textArea5.setFont(font1);
      textArea6 = new JTextArea("Points Made: ");
      textArea6_1 = new JTextArea(players.get(arrayIndex).P);
      textArea6.setFont(font1);
      textArea7 = new JTextArea("Points Per Game ");
      textArea7_1 = new JTextArea(players.get(arrayIndex).PPG);
      textArea7.setFont(font1);
      
   
      output = new JTextArea(2, 20);
      output.setEditable(false);
   
   
   /*
   
   	FontRenderContext frc = g2.getFontRenderContext();
   	Font f = new Font("Helvetica",Font.BOLD, 24);
   	String s = new String("24 Pont Helvetica Bold");
   	TextLayout tl = new TextLayout(s, f, frc);
   	Dimension theSize=getSize();
   	g2.setColor(Color.green);
   	tl.draw(g2, theSize.width/30, theSize.height/2);
   
   */
   
      //create the buttons and other elements
      
   
   
      prev = new JButton("Prev");
      prev.addActionListener(this);
      prev.setFont(font);
   
      next = new JButton("Next");
      next.addActionListener(this);
      next.setFont(font);

      create = new JButton("Create");
      create.addActionListener(this);
      create.setFont(font);

      edit = new JButton("Edit");
      edit.addActionListener(this);
      edit.setFont(font);

      load = new JButton("Load");
      load.addActionListener(this);
      load.setFont(font);

      delete = new JButton("Delete");
      delete.addActionListener(this);
      delete.setFont(font);

      //create the panels
      Panel flow = new Panel(new FlowLayout());
      Panel xt1 = new Panel(new FlowLayout());
      Panel new1 = new Panel(new FlowLayout());
      Panel xt3 = new Panel(new FlowLayout(FlowLayout.CENTER, 90, 0));


      subPanel.setLayout(new BorderLayout());

      
      //add elements to the panels
      flow.add(prev);
      flow.add(next);
      flow.add(xt1);
      flow.add(new1);
      xtra.add(load);
      xtra.add(edit);
      xt1.add(create);
      xt1.add(delete);
      new1.add(textArea3);
      new1.add(textArea4);
      new1.add(textArea5);
      new1.add(textArea6);
      new1.add(textArea7);
      xt3.add(textArea3_1);
      xt3.add(textArea4_1);
      xt3.add(textArea5_1);
      xt3.add(textArea6_1);
      xt3.add(textArea7_1);

      subPanel.add(new1, BorderLayout.NORTH);
      subPanel.add(xt3, BorderLayout.CENTER );
      subPanel.add(xtra, BorderLayout.SOUTH);

      //add the panel to the main container layout
      add(flow,BorderLayout.SOUTH);
      add(subPanel, BorderLayout.CENTER);

      
      Panel center = new Panel(new FlowLayout());
      center.add(textArea);
      center.add(textArea_2);
      center.add(textArea1);
      center.add(textArea1_1);
      add(center,BorderLayout.NORTH);

   
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(600,400);
      setVisible(true);
      setBackground(Color.ORANGE);
   
   }

   public void writeToCSV()
   {
      System.out.println("Writing to CSV File");
   	//generateCsvFile("c:\\test.csv");
   
      try
      {
         FileWriter writer = new FileWriter("zzz.csv");
      
         writer.append("Player");
         writer.append(',');
         writer.append("Team");
         writer.append(',');
         writer.append("Games");
         writer.append(',');
         writer.append("Field Goals");
         writer.append(',');
         writer.append("Free Throws");
         writer.append(',');
         writer.append("Points");
         writer.append(',');
         writer.append("PPG");
         writer.append('\n');
      
      
         for(int g=0;g<highScorers.length;g++)
         {
         
            writer.append(highScorers[g].N);
            writer.append(',');
            writer.append(highScorers[g].T);
            writer.append(',');
            writer.append(highScorers[g].G);
            writer.append(',');
            writer.append(highScorers[g].FG);
            writer.append(',');
            writer.append(highScorers[g].FT);
            writer.append(',');
            writer.append(highScorers[g].P);
            writer.append(',');
            writer.append(highScorers[g].PPG);
            writer.append('\n');
         
         }
      
         writer.flush();
         writer.close();
      }//try
      catch(IOException e)
      {
         e.printStackTrace();
      }//catch
   }
  
  //This example reads in loaded CSV files and parses it out using the commas.
  //It then adds each line to the arrayList
   public void readCSV()
   {
      String fileName= "zzz.csv";
      //String line = "";
      String dir;
      FileDialog fdL = new FileDialog(this, "load me", FileDialog.LOAD);
              fdL.show();
         dir = fdL.getDirectory();
      BufferedReader fileReader = null;
     
      try {
        	
        	 
         players = new ArrayList<Player>();
        	
         String line = "";
            
         //Create the file reader
         fileReader = new BufferedReader(new FileReader(fileName));
            
         //Read the CSV file header to skip it. This line has the column names
         line = fileReader.readLine();
         System.out.println("Header  "+line);
            
         //Read the rest of the file line by line.
         while ((line = fileReader.readLine()) != null) {
            //Get all tokens available in line
            String[] tokens = line.split(",");
           // System.out.println("Tokens  "+tokens.length);
            if (tokens.length > 0) {
                
               //Create a new student object and fill his  data
               Player playerIn = new Player(tokens[0], tokens[1],Integer.parseInt( tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]),Double.parseDouble(tokens[6]));
               players.add(playerIn);
            }
         }
      /*           
         //Prints some of the arraylist out
         for (Player p : players) {
            System.out.println(p.N+"\t   "+p.T);
         }
         */
      } 
      catch (Exception e) {
         System.out.println("Error in CsvFileReader !!!");
         e.printStackTrace();
      } 
      finally {
         try {
            fileReader.close();
         } 
         catch (IOException e) {
            System.out.println("Error while closing fileReader !!!");
            e.printStackTrace();
         }
      }
   
      
   }
   
   //*************************************************************************************
   // Put what to do when the buttons are pressed in actionPerformed( )
   //*************************************************************************************

   public void actionPerformed(ActionEvent e)
   {
   
      System.out.println("Button clicked  "+e.getActionCommand());
   
   
      if(e.getActionCommand().equals("Prev"))
      {
      
         if(arrayIndex>0)
         {
            arrayIndex--;
            textArea.setText("Name: ");
            textArea_2.setText(players.get(+arrayIndex).N);
            textArea1.setText("Team: ");
            textArea1_1.setText(players.get(+arrayIndex).T);
            textArea3.setText("Total Games Played: ");
            textArea3_1.setText(players.get(+arrayIndex).G);
            textArea4.setText("Field Goals Made: ");
            textArea4_1.setText(players.get(+arrayIndex).FG);
            textArea5.setText("Free Throws Made: ");
            textArea5_1.setText(players.get(+arrayIndex).FT);
            textArea6.setText("Total Points: ");
            textArea6_1.setText(players.get(+arrayIndex).P);
            textArea7.setText("Points Per Game: ");
            textArea7_1.setText(players.get(arrayIndex).PPG);
            
         }
         System.out.println(arrayIndex);
         repaint();
      
      }
   
   
   
   
      if(e.getActionCommand().equals("Next"))
      {
         if(arrayIndex<players.size()-1)
         {
            arrayIndex++;
            textArea.setText("Name: ");
            textArea_2.setText(players.get(+arrayIndex).N);
            textArea1.setText("Team: ");
            textArea1_1.setText(players.get(+arrayIndex).T);
            textArea3.setText("Total Games Played: ");
            textArea3_1.setText(players.get(+arrayIndex).G);
            textArea4.setText("Field Goals Made: ");
            textArea4_1.setText(players.get(+arrayIndex).FG);
            textArea5.setText("Free Throws Made: ");
            textArea5_1.setText(players.get(+arrayIndex).FT);
            textArea6.setText("Total Points: ");
            textArea6_1.setText(players.get(+arrayIndex).P);
            textArea7.setText("Points Per Game: ");
            textArea7_1.setText(players.get(arrayIndex).PPG);


         }
         System.out.println(arrayIndex);
         repaint();
      
      
      }

      if(e.getActionCommand().equals("Create")){
      createPlayer();
      repaint();
      }
      if(e.getActionCommand().equals("Edit")){
         editPlayer();
         repaint();
      }
      if(e.getActionCommand().equals("Delete")){
         System.out.println(players.get(arrayIndex).N + " deleted");
         players.remove(arrayIndex);
         players.trimToSize();
         repaint();
      }
      if(e.getActionCommand().equals("Save")){
         savePlayer();

         repaint();
      }
      if(e.getActionCommand().equals("Load")){
         readCSV();
         repaint();
      }
   }
   public void editPlayer(){
      System.out.println("Editing....");

      textArea_2.setEditable(true);
      textArea1_1.setEditable(true);
      textArea2.setEditable(true);
      textArea3_1.setEditable(true);
      textArea4_1.setEditable(true);
      textArea5_1.setEditable(true);
      textArea6_1.setEditable(true);
      textArea7_1.setEditable(true);


      save = new JButton("Save");
      save.addActionListener(this);
      save.setFont(font);
      xtra.add(save);
      repaint();
      revalidate();

   }

public void createPlayer(){
      System.out.println("Creating Player");

      textArea_2.setEditable(true);
      textArea1_1.setEditable(true);
      textArea2.setEditable(true);
      textArea3_1.setEditable(true);
      textArea4_1.setEditable(true);
      textArea5_1.setEditable(true);
      textArea6_1.setEditable(true);
      textArea7_1.setEditable(true);


}
//loads the zzz csv file before going into window
   public void readCSV1()
   {
      String fileName= "zzz.csv";
      //String line = "";

      BufferedReader fileReader = null;

      try {


         players = new ArrayList<Player>();

         String line = "";

         //Create the file reader
         fileReader = new BufferedReader(new FileReader(fileName));

         //Read the CSV file header to skip it. This line has the column names
         line = fileReader.readLine();
         System.out.println("Header  "+line);

         //Read the rest of the file line by line.
         while ((line = fileReader.readLine()) != null) {
            //Get all tokens available in line
            String[] tokens = line.split(",");
            // System.out.println("Tokens  "+tokens.length);
            if (tokens.length > 0) {

               //Create a new student object and fill his  data
               Player playerIn = new Player(tokens[0], tokens[1],Integer.parseInt( tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]),Double.parseDouble(tokens[6]));
               players.add(playerIn);
            }
         }
      /*
         //Prints some of the arraylist out
         for (Player p : players) {
            System.out.println(p.N+"\t   "+p.T);
         }
         */
      }
      catch (Exception e) {
         System.out.println("Error in CsvFileReader !!!");
         e.printStackTrace();
      }
      finally {
         try {
            fileReader.close();
         }
         catch (IOException e) {
            System.out.println("Error while closing fileReader !!!");
            e.printStackTrace();
         }
      }


   }
   public void savePlayer(){
      System.out.println("Saving...");
      eName = textArea_2.getText();
      eTeam = textArea1_1.getText();
      eG = textArea3_1.getText();
      eFG = textArea4_1.getText();
      eFT = textArea5_1.getText();
      eP = textArea6_1.getText();
      ePPG = textArea7_1.getText();

      Player editplayer = new Player(eName, eTeam, Integer.valueOf(eG), Integer.valueOf(eFG), Integer.valueOf(eFT), Integer.valueOf(eP), Double.valueOf(ePPG));
      players.set(arrayIndex, editplayer);
      Player newplayer = editplayer;


      textArea_2.setText(newplayer.N);
      textArea1_1.setText(newplayer.T);
      textArea3_1.setText(newplayer.G);
      textArea4_1.setText(newplayer.FG);
      textArea5_1.setText(newplayer.FT);
      textArea6_1.setText(newplayer.P);
      textArea7_1.setText(newplayer.PPG);


      textArea.setEditable(false);
      textArea1.setEditable(false);


      xtra.remove(save);



   }


}