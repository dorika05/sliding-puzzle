import org.junit.*;

import static org.junit.Assert.*;

public class UnitTests {
    @Test
    public void testValidMoveBottomRightCorner() {
        //Arrange
        Board board = new Board(3);
        //Assert
        assertTrue(board.isValidMove(2,1));
        assertTrue(board.isValidMove(1,2));
    }

    @Test
    public void testValidMoveCenter() {
        //Arrange
        Board board = new Board(3);
        board.moveTile(2,1);
        board.moveTile(1,1);
        //Assert
        assertTrue(board.isValidMove(2,1));
        assertTrue(board.isValidMove(0,1));
        assertTrue(board.isValidMove(1,0));
        assertTrue(board.isValidMove(1,2));
    }

    @Test
    public void testInvalidMoveCenter() {
        //Arrange
        Board board = new Board(3);
        board.moveTile(2,1);
        board.moveTile(1,1);
        //Assert
        assertFalse(board.isValidMove(0,0));
        assertFalse(board.isValidMove(0,2));
        assertFalse(board.isValidMove(2,0));
        assertFalse(board.isValidMove(2,2));
    }

    @Test
    public void testMoveTileBottomLeftCorner() {
        //Arrange
        Board board = new Board(3);
        Tile tileToMove = board.tileAt(2,0).clone();
        //Act
        board.moveTile(2,1);
        board.moveTile(2,0);
        //Assert
        assertTrue(tileToMove.equals(board.tileAt(2,1)));
    }

    @Test
    public void testTileAtBottomLeftCorner() {
        //Arrange
        Board board = new Board(3);
        Tile tile = new Tile("row-3-column-1.jpg", false);
        //Assert
        assertTrue(tile.equals(board.tileAt(2,0)));
    }

    @Test
    public void testSetTileAtBottomLeftCorner() {
        //Arrange
        Board board = new Board(3);
        Tile tile = new Tile("row-3-column-1.jpg", false);
        //Act
        board.setTileAt(2,1,tile);
        //Assert
        assertTrue(tile.equals(board.tileAt(2,0)));

    }

    @Test
    public void testTileEquals() {
        //Arrange
        Tile tile1 = new Tile("row-3-column-1.jpg", false);
        Tile tile2 = new Tile("row-3-column-1.jpg", false);
        //Assert
        assertTrue(tile1.equals(tile2));
    }

    @Test
    public void testTileFilenameNotEquals() {
        //Arrange
        Tile tile1 = new Tile("row-3-column-1.jpg", false);
        Tile tile2 = new Tile("row-2-column-1.jpg", false);
        //Assert
        assertFalse(tile1.equals(tile2));
    }

    @Test
    public void testTileEmptyNotEquals() {
        //Arrange
        Tile tile1 = new Tile("row-3-column-1.jpg", false);
        Tile tile2 = new Tile("row-3-column-1.jpg", true);
        //Assert
        assertFalse(tile1.equals(tile2));
    }

    @Test
    public void testBoardNotEquals() {
        //Arrange
        Board board1 = new Board(3);
        Tile tile1 = new Tile("row-3-column-1.jpg", false);
        board1.setTileAt(2,1,tile1);
        Board board2 = new Board(3);
        //Assert
        assertFalse(board1.equals(board2));
    }

    @Test
    public void testBoardEquals() {
        //Arrange
        Board board1 = new Board(3);
        Board board2 = new Board(3);
        //Act
        board1.moveTile(2,1);
        board2.moveTile(2,1);
        //Assert
        assertTrue(board1.equals(board2));
    }

    @Test
    public void testShuffleNotFinished() {
        //Arrange
        Game game = new Game(3);
        // Act
        game.shuffle();
        // Assert
        assertFalse(game.isFinished());
    }

    @Test
    public void testIncrementSteps() {
        //Arrange
        Game game = new Game(3);
        //Act
        game.incrementSteps();
        game.restart();
        game.incrementSteps();
        //Assert
        assertEquals(1,game.getSteps());
    }

    @Test
    public void testRestart() {
        //Arrange
        Game game = new Game(3);
        //Act
        game.getActualBoard().moveTile(2,1);
        game.incrementSteps();
        game.getActualBoard().moveTile(2,0);
        game.incrementSteps();
        game.restart();
        //Assert
        assertTrue(game.getStartBoard().equals(game.getActualBoard()));
        assertEquals(0,game.getSteps());
    }

    @Test
    public void testIsNotFinished() {
        //Arrange
        Game game = new Game(3);
        //Act
        game.getActualBoard().moveTile(2,1);
        //Assert
        assertFalse(game.isFinished());
    }

    @Test
    public void testIsFinished() {
        //Arrange
        Game game = new Game(3);
        //Act
        game.getActualBoard().moveTile(2,1);
        game.getActualBoard().moveTile(1,1);
        game.getActualBoard().moveTile(2,1);
        game.getActualBoard().moveTile(2,2);
        //Assert
        assertTrue(game.isFinished());
    }
}
