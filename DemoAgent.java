 
/**
* @author: Gang Shu
*/
import java.util.Random;

/**
 * @Description:
 * 		Here we give students a small demo program so that you can get used to the code quickly.
 * 		This class 'DemoAgent' is implemented from the interface 'Agent'.
 * */


public class DemoAgent implements Agent
{
	
	
	/***********************************************************/
	/*You read the following code and implement your algorithm.*/
	/***********************************************************/
	
	/**
	 * @Function: LearntoPlay ()
	 * @Description:
	 * 		In this demo, the agent carries out some random movements as action strategy until the game ends.
	 * 		Since the Agent move randomly, so in fact it could not learn knowledge from previous states. Here is just an example.
	 * 		In your implementation, you are supposed to implement totally new functions of LearntoPlay() and  evalCurrentPolicy();
	 * 		In addition, you can use existing functions getState(), Action() and getReward() to get all kinds of state information.
	 * */
	public void LearntoPlay ()
	{
		//You should implement this function using your own algorithm.
		
		boolean flag = false;
		char direction;
		int ran;

		ran = (new Random()).nextInt(4);
		switch (ran)
		{
			case 0:  direction = 'W';  break;
			case 1:  direction = 'E';  break;
			case 2:  direction = 'N';  break;
			default: direction = 'S';  break;
		}
		flag = Action (direction);
		//System.out.println(getReward());
	}
	
	
	/**
	 * @Function: evalPolicy ()
	 * @Description:
	 * 		Every several episodes, the Agent should evaluate the goodness of its current policy in the game.
	 * */
	public double evalPolicy(int i)
	{
		//You should implement this function 
		return 0;
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
