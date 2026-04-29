import java.io.Serializable;

//ez az osztály valósít meg egy játékot
public class Game implements Serializable {
    //eltárolja a kezdeti táblát, hogy ez bármikor visszaállítható legyen
    private Board startBoard;
    //tárolja a az aktuális táblát, amit éppen a felhasználó lát
    private Board actualBoard;
    //tárolja a végső táblát, aminek a játák végén ki kell jönnie
    private Board finalBoard;
    //tárolja, hogy a felhasználó eddig hány mozgatást csinált
    private int steps;

    //a konstruktor beállít minden táblának egy paraméterként megkapott méretű táblát
    //és beállítja a mozgatások számát 0-ra
    public Game(int size){
        startBoard = new Board(size);
        actualBoard = new Board(size);
        finalBoard = new Board(size);
        steps = 0;
    }

    //visszaadja a kezdeti táblát
    public Board getStartBoard(){
        return startBoard;
    }

    //visszaadja az aktuális táblát
    public Board getActualBoard(){
        return actualBoard;
    }

    //visszaadja, hogy hány mozgatásnál jár a felhasználó
    public int getSteps(){
        return steps;
    }

    //növeli a mozgatások számát 1-el
    public int incrementSteps(){
        return ++steps;
    }

    //visszaállítja a kezdeti táblát az aktuális táblának
    //és a mozgatások számát 0-ra állítja, ez valósítja meg a játék újrakezdését
    public void restart(){
        actualBoard = startBoard.clone();
        steps = 0;
    }

    //a tábla összekeverését valósítja meg, és a kezdeti táblát beállítja az összekevert táblára
    public void shuffle(){
        Tile lastMoved = new Tile("", true);
        //5 mozgatást hajt végre
        for(int i = 0; i < 5; i++){
            while (true){
                //kiválaszt egy random csempét
                int rowToMove = (int) (Math.random() * actualBoard.getSize());
                int colToMove = (int) (Math.random() * actualBoard.getSize());
                //ha ezt tudja mozgatni és előzőleg nem ezt mozgatta, akkor végrehajtja a mozgatást
                //ha nem tudja mozgatni, kiválaszt egy új random csempét
                if (actualBoard.moveTile(rowToMove, colToMove) && lastMoved.getImageFileName()!="row-" + rowToMove + "-column-" + colToMove + ".jpg"){
                    //a most mozgatott csempét állítja be utoljára mozgatottnak
                    lastMoved = new Tile("row-" + rowToMove + "-column-" + colToMove + ".jpg", false);
                    break;
                }
            }
        }
        //beállítja kezdeti táblának a most összekevert táblát
        startBoard = actualBoard.clone();
    }

    //visszaadja, hogy végetért-e a játék
    public boolean isFinished(){
        //ha az aktuális tábla megegyezik a végső táblával, akkor végetért a játék
        return actualBoard.equals(finalBoard);
    }
}