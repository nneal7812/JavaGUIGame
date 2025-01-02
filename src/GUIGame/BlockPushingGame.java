package GUIGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlockPushingGame extends JPanel {
	private static final long serialVersionUID = 1L;

	private final int JUMP = 50;
    private int currentLevel = 1;
    private int[][][] levels = {
            // Level 1, 2, 3, 4, 5
    		{
    			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    			{1, 4, 0, 0, 0, 0, 1, 0, 1, 1},
    			{1, 1, 1, 1, 1, 0, 1, 0, 1, 1},
    			{1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
    			{1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
    			{1, 0, 0, 0, 1, 3, 0, 0, 0, 1},
    			{1, 0, 2, 0, 1, 0, 0, 0, 0, 1},
    			{1, 0, 0, 0, 1, 1, 0, 0, 1, 1},
    			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    			{1, 1, 1, 1, 1, 1, 0, 0, 1, 1}
    		},
    		{
    			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    			{1, 4, 0, 0, 1, 0, 0, 0, 0, 1},
    			{1, 0, 0, 0, 1, 1, 0, 2, 0, 1},
    			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    			{1, 0, 0, 0, 1, 0, 0, 0, 1, 1},
    			{1, 0, 0, 0, 1, 0, 1, 1, 1, 1},
    			{1, 0, 1, 0, 1, 0, 1, 1, 3, 1},
    			{1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
    			{1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
    			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    		},
    		{
    			{1, 1, 1, 1, 1, 1, 1, 0, 0, 1},
    			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    			{1, 0, 1, 1, 1, 0, 0, 1, 0, 1},
    			{1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
    			{1, 0, 1, 0, 0, 1, 0, 1, 0, 1},
    			{1, 0, 1, 0, 2, 1, 0, 1, 0, 1},
    			{1, 0, 1, 0, 0, 1, 0, 1, 0, 1},
    			{1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
    			{1, 0, 0, 0, 0, 0, 0, 1, 4, 1},
    			{1, 1, 1, 1, 1, 1, 1, 1, 3, 1}
    		},
    		{
    			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    			{0, 0, 0, 0, 0, 0, 1, 4, 0, 1},
    			{1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
    			{1, 0, 1, 1, 0, 0, 0, 1, 0, 1},
    			{1, 2, 1, 0, 0, 0, 0, 1, 0, 1},
    			{0, 0, 1, 1, 0, 1, 0, 1, 0, 1},
    			{0, 1, 0, 0, 0, 1, 0, 1, 1, 1},
    			{0, 3, 0, 0, 0, 0, 0, 0, 0, 1},
    			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    		},
    		{
    		    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    		    {1, 3, 0, 1, 0, 0, 0, 0, 0, 0},
    		    {1, 0, 0, 0, 1, 1, 1, 0, 1, 0},
    		    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    		    {1, 1, 0, 1, 0, 1, 0, 0, 4, 0},
    		    {1, 0, 0, 1, 0, 0, 0, 0, 0, 1},
    		    {1, 0, 1, 1, 0, 0, 1, 1, 0, 0},
    		    {1, 0, 2, 0, 0, 0, 0, 0, 0, 0},
    		    {1, 0, 0, 1, 0, 0, 0, 1, 1, 0},
    		    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    		},
    };

    private ImageIcon playerBlock, pushableBlock, goalBlock;
    private int playerX, playerY;
    private int pushableBlockX, pushableBlockY;
    private int goalBlockX, goalBlockY;
    
  //-----------------------------------------------------------------
  //  Constructor: Sets up this panel and loads the images.
  //-----------------------------------------------------------------
    public BlockPushingGame() {
        initializeGame();
        addKeyListener(new DirectionListener());
        setFocusable(true);
        updateLevelCounter();
    }
    
    private void updateLevelCounter()
    {
    	//https://stackoverflow.com/questions/30122731/how-to-get-the-parent-container-of-a-jwindow-in-swing
    	JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null)
            frame.setTitle("Push Block Game - Level " + currentLevel);
    }
    
    private void initializeGame() {
        int[][] currentLevelMaze = levels[currentLevel - 1];
        playerBlock = new ImageIcon("player_block.png");
        pushableBlock = new ImageIcon("push_block.png");
        goalBlock = new ImageIcon("goal_block.png");

        // Find the initial position of the player (4), pushable block (2), and goal block (3) in the maze
        for (int i = 0; i < currentLevelMaze.length; i++) {
            for (int j = 0; j < currentLevelMaze[i].length; j++) {
                if (currentLevelMaze[i][j] == 4) {
                    playerX = j;
                    playerY = i;
                } else if (currentLevelMaze[i][j] == 2) {
                    pushableBlockX = j;
                    pushableBlockY = i;
                } else if (currentLevelMaze[i][j] == 3) {
                    goalBlockX = j;
                    goalBlockY = i;
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics page) {
        super.paintComponent(page);

        // Draw the player block at the current location
        playerBlock.paintIcon(this, page, playerX * JUMP, playerY * JUMP);

        // Draw the pushable block at its location
        pushableBlock.paintIcon(this, page, pushableBlockX * JUMP, pushableBlockY * JUMP);

        // Draw the goal block at its location
        goalBlock.paintIcon(this, page, goalBlockX * JUMP, goalBlockY * JUMP);

        // Draw the maze for the current level
        paintMaze(page);
    }

    private void paintMaze(Graphics page) {
        int[][] currentLevelMaze = levels[currentLevel - 1];
        for (int i = 0; i < currentLevelMaze.length; i++) {
            for (int j = 0; j < currentLevelMaze[i].length; j++) {
                if (currentLevelMaze[i][j] == 1) {
                    page.setColor(Color.BLACK);
                    page.fillRect(j * JUMP, i * JUMP, JUMP, JUMP);
                }
            }
        }
    }
  //*****************************************************************
  //  Represents the listener for keyboard activity.
  //*****************************************************************
    private class DirectionListener implements KeyListener {
    	  //--------------------------------------------------------------
    	  //  Responds to the user pressing arrow keys by adjusting the
    	  //  image and image location accordingly.
    	  //--------------------------------------------------------------
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();

            if (keyCode == KeyEvent.VK_R) {
                restartLevel();
                return;
            }

            int nextX = playerX;
            int nextY = playerY;

            switch (keyCode) {
                case KeyEvent.VK_UP:
                    nextY--;
                    break;
                case KeyEvent.VK_DOWN:
                    nextY++;
                    break;
                case KeyEvent.VK_LEFT:
                    nextX--;
                    break;
                case KeyEvent.VK_RIGHT:
                    nextX++;
                    break;
            }

            if (isValidMove(nextX, nextY)) {
                if (nextX == pushableBlockX && nextY == pushableBlockY) {
                    int newPushableX = pushableBlockX + (nextX - playerX);
                    int newPushableY = pushableBlockY + (nextY - playerY);
                    if (isValidMove(newPushableX, newPushableY)) {
                        pushableBlockX = newPushableX;
                        pushableBlockY = newPushableY;
                    }
                }

                playerX = nextX;
                playerY = nextY;

                // Check if the pushable block is in the goal
                if (pushableBlockX == goalBlockX && pushableBlockY == goalBlockY) {
                    System.out.println("Congratulations! You pushed the block to the goal.");
                    currentLevel++;
                    
                    if (currentLevel <= levels.length) {
                        initializeGame();
                        updateLevelCounter();
                    } else {
                        System.out.println("All levels completed!");
                        System.exit(0);
                        
                    }
                }

                repaint();
            }
        }

        public void keyTyped(KeyEvent event) {}

        public void keyReleased(KeyEvent event) {}
    }


    private boolean isValidMove(int x, int y) {
    	
    	// Left and right border & top and bottom border & if the levels coordinate the player is moving to is a wall
        if (x >= 0 && x < levels[currentLevel - 1][0].length &&
            y >= 0 && y < levels[currentLevel - 1].length &&
                levels[currentLevel - 1][y][x] != 1) {

            // If the target position is not a wall, check if it's the pushable block
            if (x == pushableBlockX && y == pushableBlockY) {
                // Check the position after pushing the block
                int newPushableX = pushableBlockX + (x - playerX);
                int newPushableY = pushableBlockY + (y - playerY);

                // Return whether they new position of the push block is in bounds and not in a wall.
                return newPushableX >= 0 && newPushableX < levels[currentLevel - 1][0].length &&
                       newPushableY >= 0 && newPushableY < levels[currentLevel - 1].length &&
                        levels[currentLevel - 1][newPushableY][newPushableX] != 1;
            }
            return true;
        }
        return false;
    }

        private void restartLevel() {
            initializeGame(); // Reset the current level
            repaint();
        }
    }
