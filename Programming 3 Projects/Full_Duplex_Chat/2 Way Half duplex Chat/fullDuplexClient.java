import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;


public class fullDuplexClient extends Frame
{

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket connection;
	private ServerSocket server;
	private String ip, clientIP, messageInName, textin;
	private networkmessage messageIn;
	private boolean connected=false;
	//private boolean done;
	private networkmessage nmessage;
	private String myName;
   receiveThread myInThread;
   sendThread myOutThread;


	public static void main(String[] args)
	{
		new fullDuplexClient();
	}


	public fullDuplexClient()
	{

		try
		{
		BufferedReader brin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter your name:>"); //send prompt to DOS window
		myName =brin.readLine();		//read in user input
		}

		catch (Exception inread)
		{
		}

		runClient();

	}


//this should be threaded.
	public void runClient()
	{
			boolean done = false;

			BufferedReader inn = new BufferedReader(new InputStreamReader(System.in));
			try
			{
	
         while(!connected)
         {
					//if you don't have a connection set one up
					if(!connected)
					{
						System.out.println("Enter server IP or 127.0.0.1 for localhost>>");

						//read in the IP address from the console window and store it
						ip=inn.readLine();

						//construct a new socket using the IP address, use port 8000

						connection=new Socket(ip, 8000);  //this will wait for a connection
						System.out.println("Connected to "+ip);
						System.out.println("Waiting for Data");

						//construct the Object Input Stream for incoming data
						ois=new ObjectInputStream(connection.getInputStream());

						//construct the Object Output Stream for outgoing data
						oos=new ObjectOutputStream(connection.getOutputStream());

					}

					//construct a new message
               myInThread.start();

					System.out.print("Send>>"); //send prompt to DOS window

					//read in the message to send
					textin =inn.readLine();

					//send data to client
					myOutThread.start();


					connected=true;


					if (messageIn.text == "exit")
						done= true;
					//runClient();
			}//while
			} //try


			catch(Exception e)
			{
				System.out.println(e.getMessage());
				connected=false;
				runClient();
			}//(Exception e)

	//		connection.close();


	}//runClient


		public void sendData() throws IOException
		{
			try
			{
				//construct a new network message with the client name and test message
				nmessage = new networkmessage(myName, textin);

				//write the message to the output stream and to the "server""
				oos.writeObject(nmessage);
			}//try

			catch (IOException ioException)
			{
				System.out.println("IO exception in sendData");
			}

		}//sendData()

      	//method to read in network data
   	public void getData() throws IOException
   	{
   		try
   		{
   			networkmessage serverMessage =(networkmessage)(ois.readObject());
   			System.out.println(serverMessage.ipnum +" << "+serverMessage.text);
   
   		}//try
   
   		catch (Exception exp1)
   		{
   			System.out.println("IO exception in sendData");
   		}
   
   	}//getData()

      class receiveThread extends Thread
      {
         public void run()
         {
            try
            {
               networkmessage serverMessage = new networkmessage("  ", "  ");
               getData();
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      }      

      class sendThread extends Thread
      {
         public void run()
         {
            try
            {
               sendData();
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      }
   

		public String getMyIP()
		{

			InetAddress myHost;
			String LocalIP=" ";


			try
				{
					myHost = InetAddress.getLocalHost();
					LocalIP = ""+myHost.getHostAddress();
				}

				catch (Exception k)
				{
				}

			return(LocalIP);
		}// getMyIP()


}  // class serverProg
