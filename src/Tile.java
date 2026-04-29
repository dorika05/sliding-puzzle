import java.io.Serializable;

//Ez az osztály reprezentál egy csempét
public class Tile implements Cloneable, Serializable {
    //eltárolja egy String-ben a fájl nevét, ahol a csempe képe van
    private String imageFileName;
    //eltárolja, hogy az adott csempe az üres csempe-e
    private boolean empty;
    //eltárolja az eredeti pozícióját, hanyadik sorban és hanyadik oszlopban volt eredetileg
    private int originalRow;
    private int originalCol;

    //A konstruktor beállítja a paraméterként megadott fájlnevet imageFileName-nek
    // és beállítja, hogy üres-e, melyet szintén megkap paraméterként
    public Tile(String fileName, boolean empty) {
        this.imageFileName = fileName;
        this.empty = empty;
    }

    //visszaadja a csempéhez tartozó fájlnevet egy Stringben
    public String getImageFileName() {
        return imageFileName;
    }

    //megvalósítja egy csempe lemásolását és a másolatot visszaadja
    @Override
    public Tile clone() {
        try {
            //beállítja a másolat összes tagváltozóját az eredeti csempével azonosra
            Tile clone = (Tile) super.clone();
            clone.imageFileName = this.imageFileName;
            clone.empty = this.empty;
            clone.originalRow = this.originalRow;
            clone.originalCol = this.originalCol;
            return clone;
        //elkapja azt a hibát, ha olyan objektumot próbálna klónozni, ami nem Cloneable
        }catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    //visszaadja, hogy 2 csempe megegyezik-e
    public boolean equals(Tile tile) {
        //ha a fájljuk neve megegyezik és az is, hogy üresek, vagy sem, akkor igazadt ad vissza
        if (this.imageFileName.equals(tile.imageFileName) && this.empty == tile.empty) {
            return true;
        }
        //ha a fenti kettő közül bármelyik eltér, akkor hamisat ad vissza
        return false;
    }
}
