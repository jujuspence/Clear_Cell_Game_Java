package model;

import java.util.Random;

/* This class must extend Game */
public class ClearCellGame extends Game{

	Random random;

	public ClearCellGame(int maxRows,int maxCols, Random random, int strategy) {
		super(maxRows,maxCols);
		this.random=random;

	}

	public boolean isGameOver() {
		boolean gameOver=false;

		Outer:
			for(int i=0;i<maxCols;i++) {
				if(board[maxRows-1][i]!=BoardCell.EMPTY) {
					gameOver=true;
					break Outer;
				}
			}
		return gameOver;
	}

	public int getScore() {
		return score;
	}

	public void nextAnimationStep() {

		if(!isGameOver()) {
			for(int i=maxRows-1;i>-1;i--) {
				for(int j=0;j<maxCols;j++) {
					if(i==0) {
						board[0][j]=BoardCell.getNonEmptyRandomBoardCell(random);
					}else {
						board[i][j]=board[i-1][j];
					}	
				}
			}
		}
	}

	private BoardCell[][] processVertical(BoardCell[][] gameBoard, BoardCell targetCell,int column,int row) {

		//search downwards
		lower:
			for(int i=row+1;i<maxRows;i++) {
				if(gameBoard[i][column]==targetCell) {
					gameBoard[i][column]=BoardCell.EMPTY;
					score++;
				}else {
					break lower;
				}
			}
	//search upwards
	upper:
		for(int i=row-1;i>=0;i--) {
			if(gameBoard[i][column]==targetCell) {
				gameBoard[i][column]=BoardCell.EMPTY;
				score++;
			}else {
				break upper;
			}
		}


			return gameBoard;
	}

	private BoardCell[][] processHorizontal(BoardCell[][] gameBoard,BoardCell targetCell, int column,int row){


		//searches left
		left:
			for(int i=column-1;i>=0;i--) {
				if(gameBoard[row][i]==targetCell) {
					gameBoard[row][i]=BoardCell.EMPTY;
					score++;
				}else {
					break left;
				}
			}

	right:
		for(int i=column+1;i<maxCols;i++) {
			if(gameBoard[row][i]==targetCell) {
				gameBoard[row][i]=BoardCell.EMPTY;
				score++;
			}else {
				break right;
			}
		}




			return gameBoard;
	}

	private BoardCell[][] processDiagonal(BoardCell[][] gameBoard,BoardCell target, int column,int row){
		int rowCount=1;

		rightDown:
			for(int i=column+1;i<maxCols;i++) {
				if(row+rowCount==maxRows) {
					break rightDown;
				}
				if(gameBoard[row+rowCount][i]==target) {
					gameBoard[row+rowCount][i]=BoardCell.EMPTY;
					score++;
					rowCount++;
				}else if(gameBoard[row+rowCount][i]!=target){
					break rightDown;
				}
			}

		rowCount=1;
		rightUp:
			for(int i=column+1;i<maxCols;i++) {
				if(row==0 || row-rowCount==-1) {
					break rightUp;
				}
				if(gameBoard[row-rowCount][i]==target) {
					gameBoard[row-rowCount][i]=BoardCell.EMPTY;
					score++;
					rowCount++;
				}else if(gameBoard[row-rowCount][i]!=target){
					break rightUp;
				}
			}
		rowCount=1;
		leftDown:
			for(int i=column-1;i>=0;i--) {
				if(row+rowCount==maxRows) {
					break leftDown;
				}
				if(gameBoard[row+rowCount][i]==target) {
					gameBoard[row+rowCount][i]=BoardCell.EMPTY;
					score++;
					rowCount++;
				}else if(gameBoard[row+rowCount][i]!=target){
					break leftDown;
				}
			}

		rowCount=1;
		leftUp:
			for(int i=column-1;i>=0;i--) {
				if(row==0 || row-rowCount==-1) {
					break leftUp;
				}
				if(gameBoard[row-rowCount][i]==target) {
					gameBoard[row-rowCount][i]=BoardCell.EMPTY;
					score++;
					rowCount++;
				}else if(gameBoard[row-rowCount][i]!=target){
					break leftUp;
				}
			}

		return gameBoard;
	}

	private BoardCell[][] collapseRows(BoardCell[][] gameBoard){
		BoardCell[][] newBoard=new BoardCell[maxRows][maxCols];
		int[] emptyRows=new int[maxRows];
		int emptyRowIndex=0;

		//searches to find empty rows to collapse
		for(int i=0;i<maxRows-1;i++) {
			int emptyColSpace=0;
			nextRow:
				for(int j=0;j<maxCols;j++) {
					if(board[i][j]==BoardCell.EMPTY) {
						emptyColSpace++;
						if(emptyColSpace==maxCols) {
							//save empty row index
							emptyRows[emptyRowIndex]=i+1;
							emptyRowIndex++;
						}
					}else if(board[i][j]!=BoardCell.EMPTY){
						break nextRow;
					}
				}
		}

		//handles when empty rows are found
		if(emptyRows[0]!=0) {
			emptyRowIndex=0;
			int emptyCount=0;

			for(int index:emptyRows) {
				if(index!=0) {
					emptyCount++;
				}
			}
			int rowShift=0;
			//loop to recreate gameBoard
			for(int i=0;i<maxRows;i++) {
				if(emptyRows[emptyRowIndex]==i+1 && board[i+1][0]!=BoardCell.EMPTY) {
					emptyRowIndex++;
					rowShift++;
					for(int j=0;j<maxCols;j++) {
						newBoard[i][j]=board[i+1][j];
					}
				}else if(i<maxRows-emptyCount-1){
					for(int j=0;j<maxCols;j++) {
						newBoard[i][j]=board[i+rowShift][j];
					}
				}else if(i>=maxRows-emptyCount-1) {
					for(int j=0;j<maxCols;j++) {
						newBoard[i][j]=BoardCell.EMPTY;
					}
				}
			}
			board=newBoard;
		}
		return board;
	}

	public void processCell(int rowIndex,int colIndex) {
		BoardCell targetCell = board[rowIndex][colIndex];

		if(!isGameOver() && targetCell!=BoardCell.EMPTY) {
			
			board=processVertical(board,targetCell,colIndex,rowIndex);
			board=processHorizontal(board,targetCell,colIndex,rowIndex);
			board=processDiagonal(board,targetCell,colIndex,rowIndex);
			board[rowIndex][colIndex]=BoardCell.EMPTY;
			score++;
			board=collapseRows(board);

		}
	}


}