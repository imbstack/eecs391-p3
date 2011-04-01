/**
 * @author: Gang Shu
 */
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

/**
 * @Description:
 * 		The whole game will launch from the main function in this class.
 * */


public class RL {
	static int MAX_STEPS=1000;
	public static void main (String [] args) throws IOException
	{
		//1.Set the initial parameters for the number of episodes
		int numtrials = 100;
		if(args.length>0)
			numtrials = Integer.parseInt(args[0]); //specified in command line int 
		
		// run one of the following demos to see results.
		graphicalDemo();
		System.out.println("Demo over.");
		System.exit(1);
		//randomAgentDemo(numtrials);
	}
	
	public static void graphicalDemo()
	{
		new Game();
		DemoAgent a = new DemoAgent();
		Scanner input = new Scanner(System.in); 
		Game.setVisualize(true);
		Game.setPrintInfo(true);
		
		Game.ResetInitialState();
		for (int j=0; j<MAX_STEPS; j++)
		{
			a.LearntoPlay();
			a.Update();  // for q learning agent
			if(Game.getVisualize()){
				//input.nextLine();
				try{
					Thread.sleep(500);
				}
				catch(Exception e){
				}
			}
			if (Game.isGameOver == true)
			{
				Game.setVisualize(false);
				Game.Final ();
				break;
			}
		}
	}
	
	public static void randomAgentDemo(int numtrials)
	{
		int r = (int)numtrials / 50; 

		//2. Construct a new game environment. (You do not need to modify this sentence)
		new Game();

		//3. Create a new Agent instance. (You can change this sentence using your own Agent name) 
		DemoAgent a = new DemoAgent();

		//4. The below code will implement a main loop. (You do not need to modify these sentences)
		Scanner input = new Scanner(System.in); 
		
		//5. You can choose to visualize the game or not 
		Game.setVisualize(false);
		
		//6. You can choose to print out the game information or not 
		Game.setPrintInfo(false);

		for (int i=0; i<r; i++) 
		{
			System.out.println("==================================");
			System.out.println("The episode: "+(i+1));
			System.out.println("==================================");
			Game.setPrintInfo(true);
			Game.setPrintInfo(true);

			for(int k=0;k<50;k++){//episodes	
				Game.ResetInitialState();

				for (int j=0; j<MAX_STEPS; j++)
				{
					a.LearntoPlay();
					if(Game.getVisualize())
						input.nextLine();

					if (Game.isGameOver == true)
					{
						Game.Final ();
						break;
					}
				}
				if(Game.getVisualize())
					input.nextLine();
			}
		}
	}
	
	
}
