import java.awt.*;

public class networkmessage extends Thread implements java.io.Serializable
{
	public int serialnum;
	public String ipnum;
	public String text;
	public boolean isalive=true;

	public networkmessage(String ipString, String txt)
	{
		ipnum=ipString;
		text = txt;
	}
}