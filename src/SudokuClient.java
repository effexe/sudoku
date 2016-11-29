import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SudokuClient implements ActionListener{
  
  private JPanel boardPanel;
  private JPanel selectionPanel;
  private JPanel submitPanel;
  private JFrame boardFrame;
  private JFrame selectionFrame;
  private JFrame submitFrame;
  private JButton[][] boardButtons;
  private JButton[] selectionButtons;
  private JButton submitButton;
  private JButton selectedButton;
  private int empty = -1;
  
  
  public SudokuClient() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	
	boardPanel = new JPanel(new GridLayout(9, 9));
    boardFrame = new JFrame();
    boardFrame.setSize(900,900);
    boardButtons = new JButton[9][9];
    
    selectionFrame = new JFrame();
    selectionFrame.setSize(500,100);
    selectionPanel = new JPanel(new GridLayout(0,9));
    selectionButtons = new JButton[9];
    
    submitFrame = new JFrame();
    submitFrame.setSize(400,300);
    submitPanel = new JPanel(new GridLayout(1,1));
    submitButton = new JButton();
    submitButton.addActionListener(this);
    submitButton.setText("Submit!");
    submitPanel.add(submitButton);
   
    for(int i = 0; i < boardButtons.length; i++) {
      for(int j = 0; j < boardButtons[i].length; j ++) {
    	  boardButtons[i][j] = new JButton();
    	  boardButtons[i][j].addActionListener(this);
    	  boardPanel.add(boardButtons[i][j]);
      }
    }
    
    setConstants(boardButtons);
    
    for(int i = 0; i < selectionButtons.length; i++) {
    	selectionButtons[i] = new JButton();
    	selectionButtons[i].setText(i + 1 + "");
    	selectionButtons[i].addActionListener(this);
    	selectionPanel.add(selectionButtons[i]);
    }
    
    selectionFrame.getContentPane().add(selectionPanel, "Center");
    selectionFrame.setTitle("Choose a number");
    selectionFrame.setVisible(true);
    
    boardFrame.getContentPane().add(boardPanel, "Center");
    boardFrame.setTitle("Sudoku Board");
    boardFrame.setVisible(true);
    
    submitFrame.getContentPane().add(submitPanel, "Center");
    submitFrame.setTitle("Click to submit");
    submitFrame.setVisible(true);

    
  }
  
  public boolean findClickLocation(JButton clickedButton, String loopToRun){
	  if(loopToRun.equals("selection")){
	  for(int i = 0; i < selectionButtons.length; i++) {
	      if(clickedButton.equals(selectionButtons[i])) {
	        return true;
	      }
	    }
	  }
	  else if(loopToRun.equals("board")){
	  for(int i = 0; i < boardButtons.length; i++) {
	      for(int j = 0; j < boardButtons[i].length; j ++) {
	        if(clickedButton.equals(boardButtons[i][j])) {
	          return true;
	        }
	      }
	    }
	  }
	  return false;
  }
  
  	private void parseBoard(ArrayList<ArrayList<Integer>> l){
  		for(int i = 0; i < boardButtons.length; i++) {
  			ArrayList<Integer> temp = new ArrayList<Integer>();
  	      for(int j = 0; j < boardButtons[i].length; j ++) {
  	    	  try{
  	    	  temp.add(Integer.parseInt(boardButtons[i][j].getText()));
  	    	  }
  	    	  catch(NumberFormatException e){
  	    		  System.out.println("Please make sure boxes are filled!");
  	    	  }
  	      }
  	      
  	      l.add(temp);
  	    }
  	}
  
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		boolean isSelectionClick = findClickLocation(clickedButton, "selection");
		boolean isBoardClick = findClickLocation(clickedButton, "board");
		if (isSelectionClick) {
			selectedButton = clickedButton;
		} else if (isBoardClick) {
			if (selectedButton != null) {
				clickedButton.setText(selectedButton.getText());
			}
			// after the text has been copied to the board, we can clear our selection
			selectedButton = null;
			// this makes sure that if a constant button was clicked, it is reset back to the initial value
			setConstants(boardButtons);
		}

	}

	private void validate() {
		System.out.println("Thank you for your submission, please stand by for validation...");
		ArrayList<ArrayList<Integer>> mainList = new ArrayList<ArrayList<Integer>>();
		
		try{
		Socket echoSocket = new Socket("127.0.0.1",6013);
		ObjectOutputStream out = new ObjectOutputStream(echoSocket.getOutputStream());
		out.writeObject(mainList);
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
	}
  
  public void setConstants(JButton[][] boardButtons){
	  boardButtons[0][0].setText("5");
	  boardButtons[0][1].setText("3");
	  boardButtons[0][4].setText("7");
	  boardButtons[1][0].setText("6");
	  boardButtons[1][3].setText("1");
	  boardButtons[1][4].setText("9");
	  boardButtons[1][5].setText("5");
	  boardButtons[2][1].setText("9");
	  boardButtons[2][2].setText("8");
	  boardButtons[2][7].setText("6");
	  boardButtons[3][0].setText("8");
	  boardButtons[3][4].setText("6");
	  boardButtons[3][8].setText("3");
	  boardButtons[4][0].setText("4");
	  boardButtons[4][3].setText("8");
	  boardButtons[4][5].setText("3");
	  boardButtons[4][8].setText("1");
	  boardButtons[5][0].setText("7");
	  boardButtons[5][4].setText("2");
	  boardButtons[5][8].setText("6");
	  boardButtons[6][1].setText("6");
	  boardButtons[6][6].setText("2");
	  boardButtons[6][7].setText("8");
	  boardButtons[7][3].setText("4");
	  boardButtons[7][4].setText("1");
	  boardButtons[7][5].setText("9");
	  boardButtons[7][8].setText("5");
	  boardButtons[8][4].setText("8");
	  boardButtons[8][7].setText("7");
	  boardButtons[8][8].setText("9");
  }
  
  public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
	  SudokuClient s = new SudokuClient();
	  
  }
  
  
}