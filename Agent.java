 
/**
* @author: Gang Shu
*/


/**
 * @Description:
 * 		The interface Agent define all functions which may be used in students' agent classes.
 * 
 * 		There are five abstract function interfaces as follows:
 * 			1. getAgentLocation()	This interface have been implemented by us. You do not need to rewrite it.
 * 			2. getEnemyLocation()   This interface have been implemented by us. You do not need to rewrite it.
 * 			3. Action()		This interface have been implemented by us. You do not need to rewrite it.
 * 			4. getReward()	This interface have been implemented by us. You do not need to rewrite it.
 * 			5. LearntoPlay()	This interface should be implemented by students.
 * 			6. evalPolicy()		This interface should be implemented by students.
 * 			7. Update ()
 * 			8. Reset ()
 * 
 * 		If you still have any question about this interface, please read the example of DemoAgent.java. 
 * */

interface Agent 
{	
	abstract Location getAgentLocation ();			//To get the current state of Agent.
	
	abstract Location getEnemyLocation ();		//To get the current state of mobile enemy.
	
	abstract void Update ();					
	
	abstract void Reset ();
	
	abstract boolean Action (char direction);	//Let Agent move to a new cell, and its consequence may trigger some events. 
	
	abstract int getReward();					//To retrieve the reward score that Agent have received.
	
	abstract void LearntoPlay ();				//To conduct Agent to move based on special algorithm.
	
	abstract double evalPolicy(int i);				//Every several episodes, the Agent should evaluate the goodness of its current policy in the game.
}


