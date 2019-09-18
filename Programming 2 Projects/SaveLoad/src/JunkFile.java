import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class JunkFile implements java.io.Serializable
{
	public int rx,ry,DX=3,DY=3;

	public JunkFile(int x, int y)
	{
		rx=x;
		ry=y;
	}
}
