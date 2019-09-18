public class Player
{
	public int games;
	public int fieldGoals;
	public int freeThrows;
	public int points;
	public double PointsPerGame;
	public String N;
	public String T;
	public String G;
	public String FG;
	public String FT;
	public String P;
	public String PPG;


	public Player( String n, String t, int g, int Fg, int Ft, int p, double ppg)
	{
		N=n;
		T=t;
		games=g;
		fieldGoals=Fg;
		freeThrows=Ft;
		points=p;
		PointsPerGame=ppg;

		G= String.valueOf(games);
		FG= String.valueOf(fieldGoals);
		FT= String.valueOf(freeThrows);
		P= String.valueOf(points);
		PPG= String.valueOf(PointsPerGame);
	}

	public void print()
	{
		System.out.println("");
		System.out.println("");
		System.out.println(N+" of the "+T);
		System.out.println("    Total Games Played:  "+G);
		System.out.println("    Field Goals:  "+FG);
		System.out.println("    Free Throws:  "+FT);
		System.out.println("    Total Points:  "+P);
		System.out.println("    Points Per Game:  "+PPG);
	}
}