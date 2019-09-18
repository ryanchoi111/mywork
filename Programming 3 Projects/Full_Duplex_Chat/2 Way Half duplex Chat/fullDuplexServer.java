
// KJC @ Milton
// Two way chat server


import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;



public class fullDuplexServer extends Frame
{

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket connection;
	private ServerSocket server;
	private String ip, clientIP, textin, exitword ="exit";
	private networkmessage nmessage;
	private boolean connected=false;
	private boolean done=false;
	private String myName="";
	private int counter = 0;
   receiveThread myInThread;
   sendThread myOutThread;


/*  main method
	required method for all applications.
	It's the first method run when the program starts.
	This main method calls the serverProg constructor
*/

	public static void main(String[] args)
	{
		new fullDuplexServer();


	}

/*  serverProg()
	This is the constructor for the class.
	This constructor calls runServer();

*/

	public  fullDuplexServer()
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



		runServer();


	}


/*	runServer()
	This is where all the actual work gets done.
*/

	public void runServer()
	{
		BufferedReader inn = new BufferedReader(new InputStreamReader(System.in));

		//All networking stuff has to be put into try - catches
			try //catch(SocketException sx)
			{


					try //SocketException sx
					{
						if (!connected)  //if you are not connected create sockets and setup streams
						{
							setupConnection();  //setup sockets, wait for a connection
							setupStreams();		//after connected, setup streams

						} //if

						connected=true;			//since you ran the stuff above you're connected!


						//input data to send
						System.out.print("Send>>"); //send prompt to DOS window
						textin =inn.readLine();		//read in user input
                  myInThread = new receiveThread();
                  myOutThread = new sendThread();
						//sendData();					//send data to client
						//getData();					//read data from client
                  myInThread.start();
                  myOutThread.start();


						/*if (textin.equals(exitword))  // if "exit" is typed in shutdown the server.
						{
							serverShutdown();
						}

						else*/
						runServer();			// otherwise call runserver again to get more input

					}//try

					catch(SocketException sx)		// if the above fails close up things and then try again
					{
						if(sx.getMessage().equalsIgnoreCase("Connection reset"))
						{
							System.out.println("Connection to "+clientIP+" closed");
							connected=false;
							try //Exception ex2
							{
								oos.close();
								ois.close();
								server.close();
								connection.close();
								runServer();
							} //try
							catch(Exception ex2)
							{
								System.out.println(ex2);
							} //catch
						} // if
					}//SocketException sx
		


			}//try

			catch(SocketException sx)
			{
				if(sx.getMessage().equalsIgnoreCase("Connection reset"))
				{
					System.out.println("Connection to "+clientIP+" closed");
					try //catch(Exception ex2)
					{
						ois.close();
						server.close();
						connection.close();
						runServer();
					} //try
					catch(Exception ex2)
					{
						System.out.println(ex2);
					} // catch(Exception ex2)
				} //if
			} //catch(SocketException sx)


			catch(Exception sd)
			{
				System.out.println(sd);
			}



	} //runServer

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

	//setup connection
	public void setupConnection() throws IOException
	{
		System.out.println("SERVER MODE ACTIVATED");
		server = new ServerSocket (8000);  						//create the socket at port 8000
		System.out.println("Waiting For Connection...");
		connection = server.accept();							//wait for a connection
		System.out.println("Received connection: "+connection.getInetAddress());
		clientIP=""+connection.getInetAddress();				//print out the client IP address


	}//setupconnection()

	//Setup streams connection
	public void setupStreams() throws IOException
	{
		//Open up Streams
		System.out.println("Streams Setup");
		oos=new ObjectOutputStream(connection.getOutputStream()); //construct object output stream
		ois=new ObjectInputStream(connection.getInputStream());
		oos.flush();


	}//setupStreams()


	//method to write/send network data
	public void sendData() throws IOException
	{
		try
		{
			nmessage = new networkmessage(myName, textin);
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



/*  ****************************************************************
serverShutdown()
set done to true
closes streams
******************************************************************** */

	public void serverShutdown()
	{
		System.out.println("exiting initiated");

		try
		{
			done = true;
			textin = "Chat is terminated.  Have a nice day.";
			sendData();
			oos.close();
			server.close();
			connection.close();
		}

		catch (Exception One)
		{
			System.out.println("bad termination");
		}
	}






}  // class serverProg
