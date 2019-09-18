//Dan Adair's serializable demo  changed by chris hales


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.applet.Applet;
import java.util.*;

public class SaveLoad extends Frame implements ActionListener // this extends frame
{

	public static SaveLoad sl; //class wide variable of itself.
	public JunkFile[] loader; // an array of junkFile objects of "robots"
	public JunkFile[] saver;
	public Button save,load;	// declare two buttons
	public Canvas c;

	public int counter=0;

	// this is the main method which is run when you run an application
	public static void main(String[] args)
	{
		sl=new SaveLoad();		// calls the saveload constructer and makes a saveload
		sl.resize(400,300);		// resize window
		sl.show();				// shows the window
	}

	// this is the constructor for saveload and calls initialize
	public SaveLoad()
	{
		initialize();
	}

	public void initialize()
	{
		saver=new JunkFile[15];

		for(int x=0; x<15; x++)
		{

			saver[x]=new JunkFile((int)(25* Math.random()),(int)(25* Math.random()));
		}

		// create new buttons and add a listener on them.
		Button save=new Button("save array");
		save.addActionListener(this);

		Button load=new Button("load array");
		load.addActionListener(this);

		setLayout(new BorderLayout());
			add ("Center", save);
			add ("North", load);



	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("save array"))
		{
			// call save array and send it saver
			savearray(saver);
			for(int x=0; x<15; x++)
			{
				System.out.println("rx=" +saver[x].rx);
			}
		}
		if(e.getActionCommand().equals("load array"))
		{
			loadarray();

			for(int x=0; x<15; x++)
			{
				System.out.println("rx=" +loader[x].rx);
			}
		}
	}




	public void loadarray()
	{
		String fileName = "";
		String dir = "C:/windows/desktop";
		FileDialog fdL = new FileDialog(this, "load me", FileDialog.LOAD);
		fdL.show();
		fileName = fdL.getFile();
		dir = fdL.getDirectory();

		FileInputStream fis;
		try
		{
			fis = new FileInputStream(dir + fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			JunkFile b[] = (JunkFile[])ois.readObject();
			loader=new JunkFile[b.length];
			loader=b;
			fis.close();
			System.out.println("File "+fileName+"  loaded successfully");

		}
		catch(IOException e)
		{
			System.out.println("IOException");
			System.out.println(e);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Class not found");
		}


	}
	public void savearray(JunkFile s[])
	{
		String fileName = "";
		String dir = "C:/windows/desktop";


		FileDialog fdS = new FileDialog(this, "SAVE", FileDialog.SAVE);
		fdS.show();
		fileName = fdS.getFile();
		dir = fdS.getDirectory();


		try
		{
			FileOutputStream fos=new FileOutputStream(dir + fileName);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(s);
			oos.flush();
			fos.close();





		}
		catch(IOException e)
		{
			System.out.println("did not save");

		}


	}


}