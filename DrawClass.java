/**
 *
 * @author salim akhter chowdhury
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawClass{
    
    Grids grids;
    int agentX,agentY,enemyX,enemyY,enemy2X,enemy2Y;   /// agentX is the row number and agentY is the column number of the agent..same for enemy too
    static ArrayList<Wall> wallList = new ArrayList <Wall>();
    static ArrayList<Reward> rewardList = new ArrayList <Reward>();
    DrawClass(ArrayList<Wall> wallList,ArrayList<Reward> rewardList,int agentX,int agentY,int enemyX, int enemyY, int enemy2X, int enemy2Y  ){
       this.agentX = agentX;
       this.agentY = agentY;
       this.enemyX = enemyX;
       this.enemyY = enemyY;
       this.enemy2X = enemy2X;
       this.enemy2Y = enemy2Y;
       DrawClass.wallList = wallList;
       DrawClass.rewardList = rewardList;
       grids = new Grids("MAZE", 600, 600, 20, 20 ,wallList,rewardList,agentX,agentY,enemyX,enemyY,enemy2X,enemy2Y);
       grids.setVisible(true);
       
    }
    
    // This function dispatches environment variables to the grid
    
    public void Draw(ArrayList<Reward> newRewardList,int agentX,int agentY,int enemyX, int enemyY,int enemy2X, int enemy2Y){
        
        grids.setVariables(newRewardList, agentX, agentY, enemyX, enemyY, enemy2X, enemy2Y);
       
    }
    
    protected void finalize() throws Throwable {
        try {
        	//grids.setVisible(false);
            grids.dispose();
        } finally {
            super.finalize();
        }
    }
    
    
}   

// Drawing is attached to a Grid 

class Grids extends Frame {
   
    ArrayList<Wall> wallList = new ArrayList <Wall>();
    ArrayList<Reward> rewardList = new ArrayList <Reward>();
    GridsCanvas xyz;
    int agentX,agentY,enemyX,enemyY,enemy2X,enemy2Y;
    
    public void setVariables(ArrayList<Reward> newRewardList,int agentX,int agentY,int enemyX, int enemyY,int enemy2X, int enemy2Y){
       xyz.setVariables(newRewardList, agentX, agentY, enemyX, enemyY, enemy2X, enemy2Y);
       xyz.repaint();
    }
    
    
    Grids(String title, int w, int h, int rows, int cols,ArrayList<Wall> walls, ArrayList<Reward> rewards,int agentX, int agentY, int enemyX,int enemyY,int enemy2X, int enemy2Y) {
        setTitle(title);
        this.agentX = agentX;
        this.agentY = agentY;
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.enemy2X = enemy2X;
        this.enemy2Y = enemy2Y;
        this.wallList = walls;
        this.rewardList = rewards;
        setResizable(false);
        
        // Now create a Canvas and add it to the Frame.
        xyz = new GridsCanvas(w, h, rows, cols, walls, rewards, agentX, agentY, enemyX, enemyY, enemy2X, enemy2Y );
        add(xyz);


            addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
                System.exit(0);
             }
          });


          // Normal end ... pack it up!
          pack();

   }
    public GridsCanvas returnCanvas(){
        return xyz;
    }
   
    }

// Actual Drawing is done here

class GridsCanvas extends Canvas {
   int width, height;
   int rows;
   int cols;
   int agentX,agentY,enemyX,enemyY,enemy2X,enemy2Y;
   char[] ch = {'A','R','E','G'};
   int[] rowY =  new int[20];
   int[] colX = new int[20];
   ArrayList<Wall> wallList = new ArrayList <Wall>();
   ArrayList<Reward> rewardList = new ArrayList <Reward>();
   
   GridsCanvas(int w, int h, int r, int c,ArrayList<Wall> wallList, ArrayList<Reward> rewardList,int agentX, int agentY, int enemyX,int enemyY, int enemy2X, int enemy2Y) {
      
      setSize(width=w, height=h);
      rows = r;
      cols = c;
      this.agentX = agentX;
      this.agentY = agentY;
      this.enemyX = enemyX;
      this.enemyY = enemyY;
      this.enemy2X = enemy2X;
      this.enemy2Y = enemy2Y;
      this.wallList = wallList;
      this.rewardList = rewardList;
   }
  
   // Accepts values from outside world to set the coordinates of the variables
   public void setVariables(ArrayList<Reward> newRewardList,int agentX,int agentY,int enemyX, int enemyY,int enemy2X, int enemy2Y){
      this.agentX = agentX;
      this.agentY = agentY;
      this.enemyX = enemyX;
      this.enemyY = enemyY;
      this.enemy2X = enemy2X;
      this.enemy2Y = enemy2Y;
      this.rewardList = newRewardList;
       
    
    }
    
   
   
   // Draws everthing on the canvas
   
   public void paint(Graphics g) {
      int xPoints[] = new int[4];
      int yPoints[] = new int[4];
      int i;
      width = getSize().width;
      height = getSize().height;
      Font font = new Font("sansserif",Font.BOLD,25);
      g.setFont(font);
      
      // draw the rows
      int rowHt = height/(rows);
      for (i = 0; i < rows; i++){
         g.drawLine(0, i*rowHt, width, i*rowHt);
         rowY[i] = (i*rowHt);
      }

      // draw the columns
      int rowWid = width/(cols);
      for (i = 0; i < cols; i++){
         g.drawLine(i*rowWid, 0, i*rowWid, height);
         colX[i] = (i*rowWid);
      }
      
      // draw the walls
      Iterator iterator = wallList.iterator();
      while(iterator.hasNext()){
        Wall wall = (Wall) iterator.next();
        int row = wall.x;
        int col = wall.y;
        char dir = wall.direction;
        if (dir == 'E'){        
            xPoints[0] = colX[col]+rowWid+5;               yPoints[0] = rowY[row] + 5;
            xPoints[1] = colX[col]+rowWid-5;               yPoints[1] = rowY[row] - 5;
            xPoints[2] = colX[col]+rowWid-5;               yPoints[2] = rowY[row]+rowWid - 5;
            xPoints[3] = colX[col]+rowWid+5;               yPoints[3] = rowY[row]+rowWid +5;
            g.setColor(Color.BLACK);
            g.fillPolygon(xPoints, yPoints, 4);

       
        }
        
        else if (dir == 'W'){
            xPoints[0] = colX[col]+5;               yPoints[0] = rowY[row] + 5;
            xPoints[1] = colX[col]-5;               yPoints[1] = rowY[row] - 5;
            xPoints[2] = colX[col]-5;               yPoints[2] = rowY[row]+rowWid - 5;
            xPoints[3] = colX[col]+5;               yPoints[3] = rowY[row]+rowWid +5;
            g.setColor(Color.BLACK);
            g.fillPolygon(xPoints, yPoints, 4);       
        } 
        else if (dir == 'N'){
            xPoints[0] = colX[col]+5;               yPoints[0] = rowY[row] + 5;
            xPoints[1] = colX[col]-5;               yPoints[1] = rowY[row] - 5;
            xPoints[2] = colX[col]+rowWid-5;        yPoints[2] = rowY[row] - 5;
            xPoints[3] = colX[col]+rowWid+5;        yPoints[3] = rowY[row] + 5;
            g.setColor(Color.BLACK);
            g.fillPolygon(xPoints, yPoints, 4);       
        } 
        else {
            xPoints[0] = colX[col]+5;               yPoints[0] = rowY[row] + rowHt + 5;
            xPoints[1] = colX[col]-5;               yPoints[1] = rowY[row] + rowHt - 5;
            xPoints[2] = colX[col]+rowWid-5;        yPoints[2] = rowY[row] + rowHt - 5;
            xPoints[3] = colX[col]+rowWid+5;        yPoints[3] = rowY[row] + rowHt + 5;
            g.setColor(Color.BLACK);
            g.fillPolygon(xPoints, yPoints, 4);       
        } 
        
        
        
        
        
        
      }
      
      
      // draw the rewards.
      
      iterator = rewardList.iterator();
      while(iterator.hasNext()){
        Reward reward = (Reward) iterator.next();
        int row = reward.x;
        int col = reward.y;
        g.setColor(Color.GREEN);
        g.drawChars(ch, 1, 1,colX[col]+5,rowY[row]+rowWid-5);        /// last rowY === colX
                                            /// 5 has been added and subtracted just to position the character in the middle of the grid
      }
      
      // draw enemy1!!
      g.setColor(Color.BLUE);      
      g.drawChars(ch, 2, 1,colX[enemyY]+5,rowY[enemyX]+rowWid-5);
      
       // draw enemy2!!
      g.setColor(Color.MAGENTA);      
      g.drawChars(ch, 2, 1,colX[enemy2Y]+5,rowY[enemy2X]+rowWid-5);
      
      // draw the agent
      g.setColor(Color.RED);      
      g.drawChars(ch, 0, 1,colX[agentY]+5,rowY[agentX]+rowWid-5);
      
      // draw the goal
      g.setColor(Color.YELLOW);      
      g.drawChars(ch, 3, 1,colX[cols-1]+5,rowY[rows-1]+rowWid-5);
      
   }
}





 
 
	
	
 
