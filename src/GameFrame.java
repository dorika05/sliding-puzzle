import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameFrame extends JFrame {
    //a gombokat és számlálókat jeleníti meg
    private final JPanel settingsPanel;
    //a játéktér
    private final JPanel gamePanel;
    //a játéktéren lévő Canvas
    private GameCanvas canvas;
    //a játék újraindítása gomb
    private JButton buttonRestart;
    //az aktuális játék
    private Game actualGame;
    //a lépésszámláló
    private JLabel labelCounter;

    //játék mentése a filename nevű fájlba
    public void saveGame (String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(actualGame);
            out.close();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //játék betöltése a filename nevű fájlból
    public void loadGame (String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            actualGame = (Game)in.readObject();
            in.close();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //a Save As (save=true) vagy Open File (save=false) dialógusablak megjelenítése.
    //A kiválasztott fájlnévvel tér vissza
    //Ha nem választottak ki fájlt, akkor üres sztring a visszatérés
    private String displayFileDialog(boolean save) {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection;
        if (save){
            fileChooser.setDialogTitle("Save game as");
            userSelection = fileChooser.showSaveDialog(this);
        }
        else{
            fileChooser.setDialogTitle("Load game");
            userSelection = fileChooser.showOpenDialog(this);
        }
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    //A konstruktorban hozzuk létre az összes vizuális elemet, és építjük fel a frame-et
    public GameFrame(Game game) {
        //A program által kezelt aktuális játék
        actualGame = game;
        //A felső gombokat és számlálót tartalmazó panel
        settingsPanel = new JPanel(new GridLayout(1, 7));
        //Az alsó játékteret megjelenítő panel
        gamePanel = new JPanel(new BorderLayout());
        this.setLayout(new BorderLayout());
        settingsPanel.setPreferredSize(new Dimension(614, 100));
        gamePanel.setPreferredSize(new Dimension(614, 657));
        //nem engedjük az átméretezést, hogy a játéktér mindig négyzetes maradjon
        this.setResizable(false);


        buttonRestart = new JButton("Restart");
        //3x3 gomb
        JButton button3 = new JButton("3X3");
        //4x4 gomb
        JButton button4 = new JButton("4X4");
        //5x5 gomb
        JButton button5 = new JButton("5X5");
        //Save gomb
        JButton buttonSave = new JButton("Save");
        //Load gomb
        JButton buttonLoad = new JButton("Load");
        //számláló
        labelCounter = new JLabel("0");

        //a settingsPanel felépítése
        settingsPanel.add(buttonSave);
        settingsPanel.add(buttonLoad);
        settingsPanel.add(button3);
        settingsPanel.add(button4);
        settingsPanel.add(button5);
        settingsPanel.add(buttonRestart);
        settingsPanel.add(labelCounter);

        //a Canvas létrehozása és hozzáadása az alsó panelhez
        canvas = new GameCanvas(game, labelCounter);
        canvas.initialize();

        gamePanel.add(canvas, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.NORTH);
        this.add(gamePanel, BorderLayout.CENTER);

        //az eseménykezelők hozzáadása
        buttonRestart.addActionListener(new RestartButtonActionListener());
        button3.addActionListener(new ButtonBoardSizeActionListener(3));
        button4.addActionListener(new ButtonBoardSizeActionListener(4));
        button5.addActionListener(new ButtonBoardSizeActionListener(5));
        buttonSave.addActionListener(new ButtonSaveActionListener());
        buttonLoad.addActionListener(new ButtonLoadActionListener());
    }

    //a Restart button eseménykezelő osztálya
    private class RestartButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            actualGame.restart();
            canvas.repaint();
            labelCounter.setText("0");
        }
    }

    //A pályaméretválasztó gombok eseménykezelő osztálya
    private class ButtonBoardSizeActionListener implements ActionListener {
        private int size;
        //A konstruktor eltárolja a pálya méretét, így az összes gombhoz
        //ugyanaz az eseménykezelő osztály tartozhat
        public ButtonBoardSizeActionListener(int size) {
            this.size = size;
        }
        public void actionPerformed(ActionEvent e) {
            actualGame = new Game(size);
            //újrakeverés
            actualGame.shuffle();
            canvas.setGame(actualGame);
            canvas.repaint();
            //számláló nullázása
            labelCounter.setText("0");
        }
    }

    //Save button eseménykezelő osztálya
    private class ButtonSaveActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Save As dialogus megjelnítése
            String filename = displayFileDialog(true);
            //Ha kiválasztott fájlnevet, akkor abba mentünk
            if (!filename.isEmpty()){
                saveGame(filename);
            }
        }
    }

    //Load button eseménykezelő osztálya
    private class ButtonLoadActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //File Open dialogus megjelnítése
            String filename = displayFileDialog(false);
            //Ha kiválasztott fájlt, akkor azt betölti
            if (!filename.isEmpty()){
                loadGame(filename);
            }
            canvas.setGame(actualGame);
            canvas.repaint();
            //megjeleníti az elmentett lépészámot
            labelCounter.setText(String.valueOf(actualGame.getSteps()));
        }
    }
}
