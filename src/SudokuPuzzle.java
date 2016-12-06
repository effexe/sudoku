public class SudokuPuzzle {

	protected String [][] board;		// table that holds the values
	protected boolean [][] mutable;		// Table to determine if a slot is mutable
	private final int ROWS;
	private final int COLUMNS;
	private final int BOXWIDTH;
	private final int BOXHEIGHT;
	private final String [] VALIDVALUES; //array that holds valid values for the puzzle
	
	public SudokuPuzzle(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {
		this.ROWS = rows;
		this.COLUMNS = columns;
		this.BOXWIDTH = boxWidth;
		this.BOXHEIGHT = boxHeight;
		this.VALIDVALUES = validValues;
		this.board = new String[ROWS][COLUMNS];
		this.mutable = new boolean[ROWS][COLUMNS];
		initializeBoard();
		setConstants(board);
		initializeMutableSlots();
	}
	
	public SudokuPuzzle(SudokuPuzzle puzzle) {
		this.ROWS = puzzle.ROWS;
		this.COLUMNS = puzzle.COLUMNS;
		this.BOXWIDTH = puzzle.BOXWIDTH;
		this.BOXHEIGHT = puzzle.BOXHEIGHT;
		this.VALIDVALUES = puzzle.VALIDVALUES;
		this.board = new String[ROWS][COLUMNS];
		for(int r = 0;r < ROWS;r++) {
			for(int c = 0;c < COLUMNS;c++) {
				board[r][c] = puzzle.board[r][c];
			}
		}
		this.mutable = new boolean[ROWS][COLUMNS];
		for(int r = 0;r < ROWS;r++) {
			for(int c = 0;c < COLUMNS;c++) {
				this.mutable[r][c] = puzzle.mutable[r][c];
			}
		}
	}
	
	
	public int getNumRows() {
		return this.ROWS;
	}
	
	public int getNumColumns() {
		return this.COLUMNS;
	}
	
	public int getBoxWidth() {
		return this.BOXWIDTH;
	}
	
	public int getBoxHeight() {
		return this.BOXHEIGHT;
	}
	
	public String [] getValidValues() {
		return this.VALIDVALUES;
	}
	
	public String [][] getBoard() {
		return this.board;
	}
	
	public void makeMove(int row, int col, String value, boolean isMutable) {
		if(this.isValidValue(value) && this.isSlotMutable(row, col)) {
			this.board[row][col] = value;
			this.mutable[row][col] = isMutable;
		}
	}
	
	public boolean isSlotAvailable(int row,int col) {
		 return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
	}
	
	public boolean isSlotMutable(int row,int col) {
		return this.mutable[row][col];
	}
	
	public String getValue(int row,int col) {
		if(this.inRange(row,col)) {
			return this.board[row][col];
		}
		return "";
	}
	
	private boolean isValidValue(String value) {
		for(String str : this.VALIDVALUES) {
			if(str.equals(value)) return true;
		}
		return false;
	}
	
	public boolean inRange(int row,int col) {
		return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
	}
	
	@Override
	public String toString() {
		String str = "Game Board:\n";
		for(int row=0;row < this.ROWS;row++) {
			for(int col=0;col < this.COLUMNS;col++) {
				str += this.board[row][col] + " ";
			}
			str += "\n";
		}
		return str+"\n";
	}
	
	//sets the entire board to be an empty string
	private void initializeBoard() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.board[row][col] = "";
			}
		}
	}
	
	private void setConstants(String[][] boardButtons){
		  boardButtons[0][0] = "5"; 
		  boardButtons[0][1] = "3";
		  boardButtons[0][4] = "7";
		  boardButtons[1][0] = "6"; 
		  boardButtons[1][3] = "1"; 
		  boardButtons[1][4] = "9"; 
		  boardButtons[1][5] = "5"; 
		  boardButtons[2][1] = "9"; 
		  boardButtons[2][2] = "8"; 
		  boardButtons[2][7] = "6"; 
		  boardButtons[3][0] = "8"; 
		  boardButtons[3][4] = "6"; 
		  boardButtons[3][8] = "3"; 
		  boardButtons[4][0] = "4"; 
		  boardButtons[4][3] = "8"; 
		  boardButtons[4][5] = "3"; 
		  boardButtons[4][8] = "1"; 
		  boardButtons[5][0] = "7"; 
		  boardButtons[5][4] = "2"; 
		  boardButtons[5][8] = "6"; 
		  boardButtons[6][1] = "6"; 
		  boardButtons[6][6] = "2"; 
		  boardButtons[6][7] = "8"; 
		  boardButtons[7][3] = "4"; 
		  boardButtons[7][4] = "1"; 
		  boardButtons[7][5] = "9"; 
		  boardButtons[7][8] = "5"; 
		  boardButtons[8][4] = "8"; 
		  boardButtons[8][7] = "7"; 
		  boardButtons[8][8] = "9"; 
	  }
	
	//only allows slots that aren't in the constants to be initialized 
	private void initializeMutableSlots() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				if(this.board[row][col] == "") {
					this.mutable[row][col] = true;
				}
				else {
					this.mutable[row][col] = false;
				}
			}
		}
	}
}