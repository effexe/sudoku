import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {

	private JPanel buttonSelectionPanel;
	private SudokuPanel sPanel;
	
	public SudokuFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800,600));
		
		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout());
		windowPanel.setPreferredSize(new Dimension(800,600));
		
		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(90,500));
		sPanel = new SudokuPanel();
		
		windowPanel.add(sPanel);
		windowPanel.add(buttonSelectionPanel);
		
		this.add(windowPanel);
		buildInterface(new SudokuPuzzle(9, 9, 3, 3, new String[] {"1","2","3","4","5","6","7","8","9"}), 26);
	}
	
	public void buildInterface(SudokuPuzzle sudokuPuzzle,int fontSize) {
		sPanel.newSudokuPuzzle(sudokuPuzzle);
		sPanel.setFontSize(fontSize);
		for(String value : sudokuPuzzle.getValidValues()) {
			JButton b = new JButton(value);
			b.setPreferredSize(new Dimension(40,40));
			b.addActionListener(sPanel.new NumActionListener());
			buttonSelectionPanel.add(b);
		}
		
		sPanel.repaint();
		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();
	}
	
	public SudokuPanel getSPanel() {
		return sPanel;
	}
	
	public JPanel getButtonSelectionPannel() {
		return this.buttonSelectionPanel;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SudokuFrame frame = new SudokuFrame();
				frame.setVisible(true);
			}
		});
	}
}
