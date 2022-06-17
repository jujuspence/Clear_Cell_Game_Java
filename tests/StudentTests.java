package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import model.BoardCell;
import model.ClearCellGame;
import model.Game;
import tests.PublicTests;

public class StudentTests {

	@Test
	public void procesCellTest() {
		
		Game game1 = new ClearCellGame(6,5,new Random(1L),1);
		game1.setBoardWithColor(BoardCell.RED);
		game1.setColWithColor(1, BoardCell.GREEN);
		game1.setColWithColor(3, BoardCell.GREEN);
		game1.setBoardCell(3, 2, BoardCell.YELLOW);
		game1.setRowWithColor(0, BoardCell.BLUE);
		game1.setRowWithColor(5, BoardCell.EMPTY);
		
		String ans= "Board(Rows: 6, Columns: 5)\nBBBBB\nRGRGR\nRGRGR\nRGYGR\nRGRGR\n.....\n";
		assertEquals(ans,getBoardStr(game1));
		
		game1.processCell(0,2);
		game1.processCell(5, 2);
		String ans2= "Board(Rows: 6, Columns: 5)\nRGRGR\nRGRGR\nRGYGR\nRGRGR\n.....\n.....\n";
		assertEquals(ans2,getBoardStr(game1));
	}
	
	@Test
	public void processEmptyCellTest() {
		
		Game game2 = new ClearCellGame(4,4,new Random(1L),1);
		game2.setColWithColor(0, BoardCell.RED);
		game2.setColWithColor(1, BoardCell.BLUE);
		game2.setColWithColor(2, BoardCell.YELLOW);
		game2.setColWithColor(3, BoardCell.GREEN);
		game2.setRowWithColor(3, BoardCell.EMPTY);
		game2.setBoardCell(2, 1, BoardCell.EMPTY);
		game2.setBoardCell(2, 2, BoardCell.EMPTY);
		game2.processCell(2, 1);
		game2.processCell(3, 0);
		String ans = "Board(Rows: 4, Columns: 4)\nRBYG\nRBYG\nR..G\n....\n";
		assertEquals(ans,getBoardStr(game2));
		
		game2.setBoardCell(0, 2, BoardCell.EMPTY);
		game2.processCell(0, 2);
		String ans2 = "Board(Rows: 4, Columns: 4)\nRB.G\nRBYG\nR..G\n....\n";
		assertEquals(ans2,getBoardStr(game2));
		
	}
	
	@Test
	public void isGameOverTest() {
		
		Game game3 = new ClearCellGame(3,3,new Random(1L),1);
		game3.setBoardWithColor(BoardCell.BLUE);
		assertEquals(true, game3.isGameOver());
		
		game3.setRowWithColor(2, BoardCell.EMPTY);
		game3.setBoardCell(2, 0, BoardCell.RED);
		assertEquals(true,game3.isGameOver());
		game3.setRowWithColor(2, BoardCell.EMPTY);
		assertEquals(false,game3.isGameOver());
	}
	
	@Test
	public void getScoreTest() {
		
		Game game4 = new ClearCellGame(4,4,new Random(1L),1) ;
		game4.setBoardWithColor(BoardCell.RED);
		game4.setColWithColor(1, BoardCell.YELLOW);
		game4.setBoardCell(1, 3, BoardCell.BLUE);
		game4.setBoardCell(1, 2, BoardCell.BLUE);
		game4.setBoardCell(2, 2, BoardCell.BLUE);
		game4.setRowWithColor(3, BoardCell.EMPTY);
		
		String str = "Board(Rows: 4, Columns: 4)\nRYRR\nRYBB\nRYBR\n....\n";
		assertEquals(str, getBoardStr(game4));
		game4.processCell(1, 2);
		assertEquals(3,game4.getScore());
		
		Game game5 = new ClearCellGame(4,4, new Random(1L),1);
		game5.setBoardWithColor(BoardCell.GREEN);
		game5.setColWithColor(1, BoardCell.YELLOW);
		game5.setRowWithColor(2,  BoardCell.YELLOW);
		game5.setRowWithColor(3, BoardCell.EMPTY);
		game5.processCell(2, 1);
		String ans= "Board(Rows: 4, Columns: 4)\nG.GG\nG.GG\n....\n....\n";
		assertEquals(6,game5.getScore());
		assertEquals(ans,getBoardStr(game5));
	}
	
	
	
	private static String getBoardStr(Game game) {
		int maxRows = game.getMaxRows(), maxCols = game.getMaxCols();

		String answer = "Board(Rows: " + maxRows + ", Columns: " + maxCols + ")\n";
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				answer += game.getBoardCell(row, col).getName();
			}
			answer += "\n";
		}

		return answer;
	}
}
