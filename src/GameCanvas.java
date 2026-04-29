import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameCanvas extends Canvas {
    //az aktuális játéktér
    private Board board;
    //az folyamatban lévő játék
    private Game game;
    //a settingsPanelen lévő label, amit lépésenként frissíteni kell
    private JLabel counterLabel;

    //a konstruktor megkapja és eltárolja az aktuális játékot, és a számlálót, amit frissítenie kell
    public GameCanvas(Game game, JLabel counterLabel) {
        this.board = game.getActualBoard();
        this.game = game;
        this.counterLabel = counterLabel;
    }
    //a game settere
    public void setGame(Game game) {
        this.game = game;
    }

    //Felülírjuk a paint függvényt, és itt valósítjuk meg a csempék kirajzolását
    @Override
    public void paint(Graphics graphics) {
        //ha az utolsó lépés eredményeként véget ért a játék, akkor a gratuláló üzenet jelenik meg
        if (game.isFinished()){
            graphics.drawString("Congratulations, you solved the puzzle!", 200, 300);
            return;
        }

        board = game.getActualBoard();

        //a Canvas méretéből és a Board méretéből számoljuk a megjelenítendő csempeméretet
        int canvas_size = getWidth();
        int board_size = board.getSize();

        //végigmegyünk a játéktábla csempéin és egyesével megjelenítjük őket
        for (int i=1; i<=board_size; i++){
            for (int j=1; j<=board_size; j++){
                Image img = Toolkit.getDefaultToolkit().getImage("img/" + board_size + "/" + board.tileAt(i-1,j-1).getImageFileName());
                graphics.drawImage(img, (j-1)*(canvas_size/board_size)+5, (i-1)*(canvas_size/board_size)+5, (canvas_size/board_size)-10, (canvas_size/board_size)-10, this);
            }
        }
    }
    //Itt adjuk hozzá a nested classban megvalósított Listenert, ami a mouseClicked eseménykezelőt implementálja
    public void initialize (){
        this.addMouseListener(new MouseClickListener());
    }

    //Az eseménykezelő osztály
    private class MouseClickListener extends MouseAdapter {
        //az egér megnyomására a boardon elmozgatja a kijelölt csempét az üres helyére. Siker esetén növeli a számlálót
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int row = y/(getWidth()/board.getSize());
            int col = x/(getWidth()/board.getSize());
            if (board.moveTile(row,col)){
                counterLabel.setText(String.valueOf(game.incrementSteps()));
            }
            repaint();
        }
    }
}
