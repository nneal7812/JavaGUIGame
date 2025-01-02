package GUIGame;

import javax.swing.JFrame;
public class main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Push Block Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BlockPushingGame pushBlockGame = new BlockPushingGame();
        frame.getContentPane().add(pushBlockGame);
        frame.setSize(513, 535);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    };
}
