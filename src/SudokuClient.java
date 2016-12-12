import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SudokuClient {

	private SudokuFrame frame;

	public SudokuClient() {

		frame = new SudokuFrame();

		// add a submit button to the button selection panel
		JButton submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(80, 40));
		submit.addActionListener(new SubmitActionListener());
		frame.getButtonSelectionPanel().add(submit);

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");

		// add the menu to the bar
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);

		// add the menu item to the menu
		JMenuItem menuItem = new JMenuItem("New Game", KeyEvent.VK_T);
		menuItem.addActionListener(new NewGameActionListener());
		menu.add(menuItem);

		// add the bar to the frame
		frame.setJMenuBar(menuBar);

		frame.setVisible(true);
	}

	private void parseBoard(ArrayList<ArrayList<Integer>> l) throws NumberFormatException {
		String[][] boardButtons = frame.getSPanel().getPuzzle().getBoard();
		// parses string[][] board into an array of arrays of integers
		for (int i = 0; i < boardButtons.length; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int j = 0; j < boardButtons[i].length; j++) {
				temp.add(Integer.parseInt(boardButtons[i][j]));
			}

			l.add(temp);
		}
	}

	// executed when the submit button is clicked
	public class SubmitActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				validate();
			} catch (NumberFormatException e1) {
				System.out.println("Please make sure all boxes are filled before submitting.");
			}
		}
	}

	// executed when the new game option is clicked
	public class NewGameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SudokuGenerate g = new SudokuGenerate();
			int[][] newVals = g.nextBoard(35);
			String[][] stringVals = frame.getSPanel().getPuzzle().stringValuesFromIntValues(newVals);

			frame.getSPanel().getPuzzle().newGame(stringVals);
			frame.getSPanel().repaint();
		}
	}

	public void validate() throws NumberFormatException {
		System.out.println("Thank you for your submission, please stand by for validation...");
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		parseBoard(list);
		try {
			Socket socket = new Socket("127.0.0.1", 6013);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(list);
		} catch (Exception e1) {
			System.out.println("Could not connect to server");
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		SudokuClient s = new SudokuClient();
	}

}