import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(3);
        game.shuffle();
        GameFrame gameFrame = new GameFrame(game);
        gameFrame.setSize(614, 737);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }
}