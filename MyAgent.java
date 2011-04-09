import java.util.Random;
import java.util.HashMap;

public class MyAgent implements Agent
{

	//MAPPING
	//0 = N
	//1 = E
	//2 = S
	//3 = W
	//-------

	final double GAMMA = 0.2;
	final double EPSILON = 0.1;
	final double ALPHA = 0.05;
	private int lastAction;
	private int lastY;
	private int lastX;
	//Section of Markov Decision Process Variables
	HashMap<Integer, Double> qs;
	Random generator;

	public MyAgent(){
		qs = new HashMap<Integer, Double>();
		generator = new Random(12345);
		for (int i = 0; i < 20; i++){
			for (int j = 0; j < 20; j++){
				for (int k = 0; k < 4; k++){
					uQ(i,j,k,generator.nextDouble());
				}
			}
		}
		lastY = getAgentLocation().y;
		lastX = getAgentLocation().x;
		lastAction = 0;
	}

	public void LearntoPlay ()
	{
		int x = getAgentLocation().x;
		int y = getAgentLocation().y;
		double max = Double.NEGATIVE_INFINITY;
		int maxA = 0;
		for (int i = 0; i < 4; i++){
			if (gQ(y,x,i) > max){
				max = gQ(y,x,i);
				maxA = i;
			}
		}
		//System.out.println(gQ(getAgentLocation().y, getAgentLocation().x, lastAction));
		char action;
		switch(maxA){
			case 1: action = 'N';break;
			case 2: action = 'E';break;
			case 3: action = 'S';break;
			case 4: action = 'W';break;
			default:action = 'E';
		}
		Q(y, x, maxA);//update Q
		if (generator.nextDouble() < EPSILON || !Action(action)){
			switch(action){
				case 'N': action = (generator.nextDouble() > 0.5) ? 'W' : 'E';break;
				case 'E': action = (generator.nextDouble() > 0.5) ? 'N' : 'S';break;
				case 'S': action = (generator.nextDouble() > 0.5) ? 'W' : 'E';break;
				case 'W': action = (generator.nextDouble() > 0.5) ? 'N' : 'S';break;
			}
			Action(action);
		}
		switch(action){
			case 'N': maxA = 0;break;
			case 'E': maxA = 1;break;
			case 'S': maxA = 2;break;
			case 'W': maxA = 3;break;
			default:action = 0;
		}
		lastAction = maxA;
		lastX = x;
		lastY = y;
	}

	private void Q(int y, int x, int a){
		double max = gQ(y,x,a);
		uQ(lastY, lastX, lastAction, gQ(lastY, lastX, lastAction) + ALPHA * (getReward() + GAMMA * max - gQ(lastY, lastX, lastAction)));		
	}

	private double gQ(int y, int x, int a){
		int hsh = 0;
		hsh += y * 80;
		hsh += 4 * x;
		hsh += a;
		try{
		return qs.get(hsh);
		}
		catch(Exception e){
			return Double.NEGATIVE_INFINITY;
		}
	}

	private void uQ(int y, int x, int a, double val){
		int hsh = 0;
		hsh += y * 80;
		hsh += 4 * x;
		hsh += a;
		qs.put(hsh, val);
	}

	/**
	 * @Function: evalPolicy ()
	 * @Description:
	 * 		Every several episodes, the Agent should evaluate the goodness of its current policy in the game.
	 * */
	public double evalPolicy(int n)
	{
		//You should implement this function 
			int x = getAgentLocation().x;
			int y = getAgentLocation().y;
			double max = Double.NEGATIVE_INFINITY;
			int maxA = 0;
			for (int i = 0; i < 4; i++){
				if (gQ(y,x,i) > max){
					max = gQ(y,x,i);
					maxA = i;
				}
			}
			//System.out.println(gQ(getAgentLocation().y, getAgentLocation().x, lastAction));
			char action;
			switch(maxA){
				case 1: action = 'N';break;
				case 2: action = 'E';break;
				case 3: action = 'S';break;
				case 4: action = 'W';break;
				default:action = 'E';
			}
			//Q(y, x, action);//update Q
			if (!Action(action)){
				switch(action){
					case 'N': action = (generator.nextDouble() > 0.5) ? 'W' : 'E';break;
					case 'E': action = (generator.nextDouble() > 0.5) ? 'N' : 'S';break;
					case 'S': action = (generator.nextDouble() > 0.5) ? 'W' : 'E';break;
					case 'W': action = (generator.nextDouble() > 0.5) ? 'N' : 'S';break;
				}
				Action(action);
			}
			lastAction = action;
			lastX = x;
			lastY = y;
			return 0.0;
	}


	/**
	 * @Function: Update ()
	 * @Description:
	 * 		This function is necessary for the Q function.
	 * */	
	public void Update ()
	{
	}

	/**
	 * @Function: Reset ()
	 * @Description:
	 * 		This function will reset the game to the initial state.
	 * */	
	public void Reset ()
	{

	}


	/*****************************************/
	/*You do not need to change the codes below.*/
	/*****************************************/

	/**
	 * @Function: getAgentLocation ()
	 * @Description:
	 * 		To get the current location of Agent.
	 * @Parameter: 
	 * 		No parameters are needed.
	 * @Return value: 
	 *  	The function will return an Object containing current location to you.
	 *  	The object is 'Location' which consists of two variables: int x, int y.
	 *  		int x: indicates the current row of Agent.
	 *  		int y: indicates the current column of Agent.
	 * @Example:
	 * 		In other function, you can call this function using the below format:
	 * 
	 * 			Location loc = getState ();
	 * 			System.out.println(loc.x);	//Display the current row of Agent
	 * 			System.out.println(loc.y);	//Display the current column of Agent
	 * */
	public Location getAgentLocation ()
	{
		//You can directly use this function and do not make any change.
		return Game.getAgentLocation ();
	}

	/**
	 * @Function: getEnemyLocation ()
	 * @Description:
	 * 		To get the current state of mobile enemy
	 * @Parameter:
	 * 		No parameters are needed
	 * @Return value:
	 * 		The function will return an Object containing current state of mobile enemy to you.
	 *  	The object is 'AgentState' which consists of two variables: int x, int y.
	 *  		int x: indicates the current row of mobile enemy.
	 *  		int y: indicates the current column of mobile enemy.
	 * @Example:
	 * 		In other function, you can call this function using the below format:
	 * 
	 * 			Location loc = getEnemyLocation ();
	 * 			System.out.println(loc.x);	//Display the current row of mobile enemy
	 * 			System.out.println(loc.y);	//Display the current column of mobile enemy
	 */
	public Location getEnemyLocation()
	{
		//You can directly use this function and do not make any change.
		return Game.getEnemyLocation();
	}


	/**
	 * @Function: Action (char direction)
	 * @Description:
	 * 		Let Agent move to a new cell, and its consequence may trigger some events. 
	 * 		The events include getting rewards, achieving goal, eaten by enemies.
	 * 		P.S. Agent's movement has been limited inside the maze as well as could not go through the walls.  
	 * @Parameter: 
	 * 		char direction (which is the direction Agent want to move towards.)
	 * @The input format of Parameter direction: 
	 * 		E, W, N, S
	 * 		These four letters represent the Eastern, the Western, the Northern and the Southern.
	 * @Return value: 
	 * 		If the movement is valid, then the agent moves to the direction and the function return the value 'true';
	 * 		If the movement is invalid, then the agent still stay in the current location and the function return the value 'false'; 
	 * @Example:
	 * 		In ohter function, you can call this function using the below format:
	 * 
	 * 			boolean flag = Action ('E');	//Based on your algorithm, you want Agent to move from current loction to eastern.
	 * 											//If the flag is equal to true, then the Agent has moved to desirable direction.
	 * 											//Else if the flag is equal to false, then the Agent still stay in original location.
	 * */
	public boolean Action (char direction)
	{
		//You can directly use this function and do not make any change.
		return Game.Action(direction);
	}



	/**
	 * @Function: getReward()
	 * @Description:
	 * 		To retrieve the reward score that Agent have received.
	 * @Parameter: 
	 * 		No parameters are needed.
	 * @Return value: 
	 *  	The function will return an Integer number to indicate the reward score.
	 * @Example:
	 * 		In other function, you can call this function using the below format:
	 * 
	 * 			int CumulativeReward = getReward();		//The return value 
	 * 			System.out.println(CumulativeReward);	//Display the cumulative reward the Agent has received
	 * */
	public int getReward()
	{
		//You can directly use this function and do not make any change.
		return Game.getReward();
	}
}
