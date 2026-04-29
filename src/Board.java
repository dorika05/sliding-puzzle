import java.io.Serializable;
import java.util.ArrayList;

//Ez az osztály reprzentálja a játék tábláját, mely mindig egy adott állást tárol
public class Board implements Cloneable, Serializable {
    //egy ArrayListben, ami csempékből álló ArrayListeket tartalmaz
    // eztárolja a később megjelenő, csempékből álló mátrixot
    private ArrayList<ArrayList<Tile>> tileMatrix;
    //eltárolja a méretet, azaz a mátrix majd sizeXsize méretű lesz
    private int size;
    //eltárolja az üres csempe pozícióját (sor és oszlop)
    private int emptyTileRow;
    private int emptyTileCol;

    //a konstruktorban paraméterként megadott méretű táblát feltölt csempékkel
    public Board(int size) {
        this.size = size;
        tileMatrix = new ArrayList<>();
        for (int i=1; i<=size; i++){
            //minden sort egy ArrayList tárol
            tileMatrix.add(new ArrayList<>());
            for (int j=1; j<=size; j++) {
                //minden fájlnak a neve ilyen formátumban van megadva
                String filename = "row-" + i + "-column-" + j + ".jpg";
                //az üres csempe kezdetben mindig a jobb alsó sarokban van
                if (i==size && j==size){
                    //ha éppen az utolsó sor utolsó csempéje adódik hozzá
                    //akkor egy olyan csempe adódik a sorhoz, aminek az empty változója igaz
                    tileMatrix.get(i-1).add(new Tile(filename, true));
                    //beállítja a tábla üres csempéjének pozícióját
                    emptyTileRow = i-1;
                    emptyTileCol = j-1;
                }
                //ha nem az üres csempe jön, akkor az adott sorhoz
                //egy olyan csempét ad, aminek az empty változója hamis
                else {
                    tileMatrix.get(i-1).add(new Tile(filename, false));
                }
            }
        }
    }

    //visszaadja a tábla méretét
    public int getSize(){
        return size;
    }

    //visszaadja azt a csempét, ami a paraméterként megadott pozícióban van (sor, oszlop)
    public Tile tileAt(int row, int col) {
        return tileMatrix.get(row).get(col);
    }

    //a paraméterkét megkapott helyre (sor, oszlop) beállítja a paraméterként kapott csempét
    public void setTileAt(int row, int col, Tile tile) {
        tileMatrix.get(row).set(col, tile);
    }

    //visszaadja, hogy a paraméterként megadott helyen lévő csempe mozgatását végre lehet-e hajtani
    public boolean isValidMove(int row, int col) {
        //ha az üres csempével azonos sorban van és valamelyik szomszédja
        //vagy ha az üres csempével egy oszlopban van és valamelyik szomszédja
        //akkor lehetséges a mozgatás
        if ((emptyTileRow == row && Math.abs(emptyTileCol-col)==1)||
                (emptyTileCol == col && Math.abs(emptyTileRow-row)==1)){
            return true;
        }
        //minden más esetben nem lehetséges
        return false;
    }

    //megvalósítja egy csempe mozgatását és visszaadja, hogy sikeres volt-e
    public boolean moveTile(int row, int col) {
        //ha lehetséges a mozatás, akkor végrehajtja a mozgatást
        if (isValidMove(row, col)) {
            //ez a mozgatni kívánt csempe, ami a paraméterként megadott pozícióban van
            Tile tileToMove = this.tileAt(row,col);
            //a helyére beállítja az üres csempét
            this.setTileAt(row,col, this.tileAt(emptyTileRow,emptyTileCol));
            //az üres csempe helyére pedig beállítja a mozgatni kívánt csempét
            this.setTileAt(emptyTileRow,emptyTileCol, tileToMove);
            //frissíti az üres csempe pozícióját
            emptyTileRow = row;
            emptyTileCol = col;
            return true;
        }
        //ha nem lehetséges a mozgatás, akkor visszaadja, hogy sikertelen volt
        else{
            return false;
        }
    }

    //egy tábla másolását valósítja meg és visszaadja a tábla másolatát
    @Override
    public Board clone() {
        //a másolat minden változóját beállítja az eredetivel megegyezőre
        Board clone = new Board(size);
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                clone.setTileAt(i,j,tileAt(i,j).clone());
            }
        }
        clone.emptyTileRow = emptyTileRow;
        clone.emptyTileCol = emptyTileCol;
        return clone;
    }

    //visszaadja, hogy a tábla megegyezik-e a paraméterként kapott táblával
    public boolean equals(Board board){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                //ha valamelyik csempe nem egyezik, akkor biztosan nem azonosak
                if (!tileAt(i,j).equals(board.tileAt(i,j))){
                    return false;
                }
            }
        }
        //ha minden csempe megegyezik, akkor azonosak
        return true;
    }
}
