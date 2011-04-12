 
/**
* @author: Gang Shu
*/
import java.util.ArrayList;
import java.util.Random;

/**
 * @Description:
 * 		The functions of class Game contain the following parts: 
 * 
 * 		1. In function Game ()
 * 			Construct a new map with walls and rewards.
 * 
 * 		2. In function ResetInitialState ()
 * 			Generate the initial location of Agent, Enemy#1 and Enemy#2.
 * 
 * 		3. In function Action ()
 * 			Accept action request from Agent and enemies, and judge if the action is feasible. 
 * 			If the action is feasible, it will execute it and trigger some events.
 * 			If the action is non-feasible, it will refuse the action and return false.
 * 
 * 			The private Action (char direction, String type) can accept requests from Agent, Enemy#1 and Enemy#2.
 * 			The public  Action (char direction) can just accept requests from Agent. This function is public to all.
 * 
 * 		4. In function EnemyAction ()
 * 			Enemy's action policy:
 * 				With  1/4 chance, enemy move in random direction.
 * 				With 3/4 chance, enemy move toward agent.
 * 
 * 		5. In function TakeRandomAction ()
 * 			Enemy will take a random action whose direction is generated randomly.
 * 
 * 		6. ... Some other getter and setter functions.
 * 
 * 		( Note: you can just learn how the game runs from here, however you do not need to modify these codes. )
 */


public class Game 
{
	//information for the environment
	private static int maze [][] = new int [20][20];
	private static ArrayList<Wall> wallList = new ArrayList <Wall>(); 
	private static ArrayList<Reward> rewardList = new ArrayList <Reward>();
	public static int step;
	public static boolean isGameOver;
	private static DrawClass draw;
	public static int indicator = 0;	//1: achieve goal; 2: eaten by enemy #1; 3: eaten by enemy #2
	
	//information for the agent
	public static int agent_x, agent_y;  
	private static int score = 0;
	private static int previousscore = 0;
	
	//information for the enemies #1 and #2.
	private static int e1_x, e1_y, e2_x, e2_y;  
	
	private static boolean visualize = true;
	private static boolean printInfo = true;
	private static Random rand = new Random(12345);//change back to 12345
	
	public Game ()
	{
		/*1. Initialize all cells in the maze with zero.*/
		for (int i=0; i<20; i++)
			for (int j=0; j<20; j++)
				maze[i][j] = 0;
		
		/*2. Build the walls in the maze.*/
			//the first wall
		for (int i=0; i<7; i++) { 
			wallList.add( new Wall (i,2,'E') ); 
			wallList.add( new Wall (i,3,'W') ); 
			} 
			//the second wall
		for (int i=14; i<20; i++) { 
			wallList.add( new Wall (4,i,'S') ); 
			wallList.add( new Wall (5,i,'N') ); 
			}
			//the third wall
		for (int i=11; i<20; i++) { 
			wallList.add( new Wall (i,14,'E') ); 
			wallList.add( new Wall (i,15,'W') ); 
			} 	
		
		/*3. Put the rewards in the maze.*/
		rewardList.add( new Reward(0,3,2) );
		rewardList.add( new Reward(19,14,2) );
		rewardList.add( new Reward(19,19,200) );
	}
	
	public static void setVisualize(boolean flag)
	{
		visualize = flag;
	}
	public static boolean getVisualize()
	{
		return visualize;
	}
	
	public static void setPrintInfo(boolean flag)
	{
		printInfo = flag;
	}
	public static boolean getPrintInfo()
	{
		return printInfo;
	}

	public static void ResetInitialState ()
	{
		//System.out.println("Reset function has been called!");
		score = 0;
		step = 1;
		isGameOver = false;
		
		//Generate the random location for the agent
		double temp;
		do
		{	
			temp = 1;
			agent_x = rand.nextInt(20);
			agent_y = rand.nextInt(20);
			for (Reward r : rewardList)	//the location of agent should not located on the rewards.
				if (agent_x==r.x && agent_y==r.y)
					temp = -1;
		}while( temp < 0 );
		//Generate the random location for the enemy #1
		do
		{
			e1_x = rand.nextInt(17);
			e1_y = rand.nextInt(17);
			//the distance between agent and enemy should larger than squrt(50)
			temp = Math.sqrt(  Math.pow( (e1_x-agent_x) ,2) + Math.pow( (e1_y-agent_y) ,2) );   
		}while( temp < Math.sqrt(50) );
		//Generate the location for the enemy #2
		e2_x=10;
		e2_y=10;
		
		// release the resource of last window.
		try {
			if(draw!=null)
				draw.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if(visualize)
		{
			draw = new DrawClass(wallList,rewardList,agent_x, agent_y, e1_x, e1_y, e2_x, e2_y);
			draw.Draw(rewardList,agent_x, agent_y, e1_x, e1_y, e2_x, e2_y);
		}
	}
	public static void Final ()
	{
		if(printInfo)
			System.out.println("Cumulative Reward:"+score+"\n" );
		if(visualize)
			draw.Draw(rewardList,agent_x, agent_y, e1_x, e1_y, e2_x, e2_y);	
	}
	
	public static int getStep()
	{
		return step;
	}
	public static double getCumulativeReward()
	{
		return score;
	}
	
	
	public static Location getAgentLocation ()
	{
		return new Location(agent_x, agent_y);	//return a object of 'AgentState' with current state information of agent.
	}
	
	public static Location getEnemyLocation ()
	{
		return new Location(e1_x, e1_y);	//return a object of 'AgentState' with current state information of enemy#1.
	}
	
	public static int getReward()
	{
		return previousscore;
	}
	
	public static boolean Action (char direction)
	{
		return Game.Action(direction, "Agent");
	}
	
	public static boolean isValidMove(int y, int x, int direction){
		char action;
		switch(direction){
			case 0: action = 'N';break;
			case 1: action = 'E';break;
			case 2: action = 'S';break;
			case 3: action = 'W';break;
			default:action = 'E';
		}
		if ( x==0 && direction=='N' ) 		
			return false;
		else if ( x==19 && direction=='S' ) 
			return false;
		else if ( y==0 && direction=='W' )  
			return false;
		else if ( y==19 && direction=='E' ) 
			return false;
		for (Wall w : wallList){
			if (x==w.x && y==w.y && action==w.direction){
				return false;
			}
		}
		return true;	
	}
		
	private static boolean Action (char direction, String type)
	{	
		
		int x=0, y=0;
		if (type.equals("Agent")==true) {
			x = agent_x; 
			y = agent_y; 
			}
		else if  (type.equals("enemy #1")==true) { 	
			x = e1_x; 
			y = e1_y; 
			}
		else if  (type.equals("enemy #2")==true) { 	
			x = e2_x; 
			y = e2_y; 
			}
		
		//Agent or enemies could not move outside of maze.
		if ( x==0 && direction=='N' ) 		
			return false;
		else if ( x==19 && direction=='S' ) 
			return false;
		else if ( y==0 && direction=='W' )  
			return false;
		else if ( y==19 && direction=='E' ) 
			return false;
		
		//Agent or enemies could not move through the walls.
		for (Wall w : wallList)
			if (x==w.x && y==w.y && direction==w.direction)
				return false;
		
		//move is allowed, for each agent's movement, score will be subtracted 1.
		if (type.equals("Agent")==true)
		{
			score--;
			previousscore = -1;
		}

		//Otherwise we should allow the agent or enemies to move to that direction.
		if (type.equals("Agent")==true)	//for the agent
		{
			if (direction=='S') 
				agent_x++;
			else if (direction=='N') 
				agent_x--;
			else if (direction=='E') 
				agent_y++;
			else if (direction=='W') 
				agent_y--;
		}
		else if (type.equals("enemy #1")==true)
		{
			if (direction=='S') 
				e1_x++;
			else if (direction=='N') 
				e1_x--;
			else if (direction=='E') 
				e1_y++;
			else if (direction=='W') 
				e1_y--;
		}
		else if (type.equals("enemy #2")==true)
		{
			if (direction=='S') 
				e2_x++;
			else if (direction=='N') 
				e2_x--;
			else if (direction=='E') 
				e2_y++;
			else if (direction=='W') 
				e2_y--;
		}
		
		//Enemies could not move into the square (17,17) to (19,19).
		if (type.startsWith("enemy")==true)
		{
			if (type.equals("enemy #1")==true && e1_x >=17 && e1_y >=17) {
				e1_x = x; 
				e1_y = y; 
				return false; 
			}
			if (type.equals("enemy #2")==true && e2_x >=17 && e2_y >=17) {
				e2_x = x; 
				e2_y = y; 
				return false; 
			}
		}
		
		//Check if the agent can get rewards
		if (type.equals("Agent")==true)	//for the agent
		{
			//Agent will get rewards if it moves to a chance block.
			for (Reward r : rewardList)
				if (agent_x==r.x && agent_y==r.y)
				{
					score += r.value;
					previousscore += r.value;
					if(printInfo)
						System.out.println("Agent gets a reward worth "+r.value);
					if (agent_x==19 && agent_y==19)
					{
						//Agent reachs the goal and gets the final reward.
						if(printInfo)
						{
							System.out.println("*******************************");
							System.out.println("Agent reached the final goal!");
							System.out.println("*******************************");
						}
						indicator = 1;
						isGameOver = true;
					}
					break;
				}
		}
		
		if(visualize)
			draw.Draw(rewardList,agent_x, agent_y, e1_x, e1_y, e2_x, e2_y);
		
//		check if the Agent encountered the enemyies.;

		if (type.equals("Agent")==true && agent_x==e1_x && agent_y==e1_y)
		{
			if(printInfo)
				System.out.println("Agent encountered the Enemy #1, and it has been eaten.");
			indicator = 2;
			score -= 100;
			previousscore -= 100;
			isGameOver = true;
			return false;
		}
		else if (type.equals("Agent")==true && agent_x==e2_x && agent_y==e2_y)
		{
			if(printInfo)
				System.out.println("Agent encountered the Enemy #2, and it has been eaten.");
			indicator = 3;
			score -= 100;
			previousscore -= 100;
			isGameOver = true;
			return false;
		}
		
		
	    //Enemy #1 and #2 take actions (like chase and random move actions) based on Agent's movement. 
		if (type.equals("Agent")==true && Game.isGameOver == false)
		{
			Game.EnemyAction();
		}
		if(visualize)
			draw.Draw(rewardList,agent_x, agent_y, e1_x, e1_y, e2_x, e2_y);
		
		return true;
	}
	
	
	/*
	 * Enemy's action policy:
	 * 		With 1/4 chance, enemy move in random direction.
	 * 		With 3/4 chance, enemy move toward agent.
	 */
	public static boolean EnemyAction ()
	{
		boolean flag = false;

		
		/*For enemy #1*/
		int ran = rand.nextInt(4);
		if (ran==0)	//with 1/4 chance, the enemy move in random direction.
		{
			TakeRandomAction ("enemy #1");
		}
		else	//with 3/4 chance, the enemy move towards agent.
		{
			flag=false;

			if (e1_x > agent_x)
				flag = Action ('N',"enemy #1");
			if (flag==false && e1_x < agent_x) 
				flag = Action ('S',"enemy #1");
			if (flag==false && e1_y > agent_y) 
				flag = Action ('W',"enemy #1");
			if (flag==false && e1_y < agent_y) 
				flag = Action ('E',"enemy #1");
			
			if (flag==false)//no way to move towards agent, move randomly for now
				TakeRandomAction ("enemy #1");
		}		
		
		if (agent_x==e1_x && agent_y==e1_y) {
			score -= 100; 
			previousscore -= 100;
			if(printInfo)
				System.out.println("Enemy #1 captured the Agent, and Agent has been eaten.");
			indicator = 2;
			isGameOver = true;
			return false;
		}

		
		/*For enemy #2--it does not move*/
		
		if (agent_x==e2_x && agent_y==e2_y) {
			score -= 100; 
			previousscore -= 100;
			if(printInfo)
				System.out.println("Enemy #2 captured the Agent, and Agent has been eaten.");
			indicator = 3;
			isGameOver = true;
			return false;
		} 
		step++;

		return true;
	}
	
	/*enemy will take a random action whose direction is generated randomly.*/
	private static boolean TakeRandomAction (String type)
	{
		boolean flag = false;
		char direction;
		int ran = rand.nextInt(4);
		
		do
		{
			switch (ran)
			{
				case 0:  
					direction = 'W'; 
					break;
				case 1:  
					direction = 'E'; 
					break;
				case 2:  
					direction = 'N'; 
					break;
				default: 
					direction = 'S'; 
				break;
			}
			flag = Action (direction,type);
			ran = rand.nextInt(4);
		}
		while (flag==false);
			

		return true;
	}
}



/**
 * The following three classes defined some data structures.
 * 	1. class Wall: this class will just be used in program.
 * 	2. class Reward: this class will just be used in program.
 * 	3. class AgentState: you may need to learn something about this class, since you may need to use it in your code. 
*/

class Wall
{
	int x, y;
	char direction; //E,W,S,N 
	public Wall (int x, int y, char direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
}

class Reward
{
	int x, y;
	int value;
	public Reward (int x, int y, int value)
	{
		this.x = x;
		this.y = y;
		this.value = value;
	}
}



/**
 *  The class Location is used as data structure for storing the location information of Agent or enemy.
 *  int x: indicates the current row of Agent or enemy.
 *  int y: indicates the current column of Agent or enemy.
 * */
class Location
{	
	int x,y;
	public Location (int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
