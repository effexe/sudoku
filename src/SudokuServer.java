import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SudokuServer {

	public class RowThread extends Thread {

		private ArrayList<ArrayList<Integer>> l;

		public RowThread(ArrayList<ArrayList<Integer>> l) {
			this.l = l;
		}

		public void run() {
			int row = 0;
			try {

				for (int i = 0; i < l.size(); i++) {
					row = i;
					Set<Integer> set = new HashSet<Integer>();
					for (int j = 0; j < l.get(i).size(); j++) {
						set.add(l.get(i).get(j));
					}
					if (set.size() != 9) {
						System.out.println(set);
						throw new IllegalArgumentException();
					}
				}
			} catch (Exception e) {
				System.out.println("The " + ordinal(row+1) + " row is not a valid row in a sudoku puzzle");
			}
		}

	}

	public class ColThread extends Thread {

		private ArrayList<ArrayList<Integer>> l;

		public ColThread(ArrayList<ArrayList<Integer>> l) {
			this.l = l;
		}

		public void run() {
			int col = 0;
			try {
				System.out.println("Thread: " + l);
				for (int i = 0; i < l.size(); i++) {
					col = i;
					Set<Integer> set = new HashSet<Integer>(l.get(i));
					if (set.size() != 9) {
						throw new IllegalArgumentException();
					}
				}
			} catch (Exception e) {
				System.out.println("The " + ordinal(col+1) + " column is not a valid column in a sudoku puzzle");
			}
		}

	}

	public class BoxThread extends Thread {

		private ArrayList<ArrayList<Integer>> l;

		public BoxThread(ArrayList<ArrayList<Integer>> l) {
			this.l = l;
		}

		public void run() {
			int box = 0;
			try {
				for (int i = 0; i < l.size(); i += 3) {
					for (int j = 0; j < l.get(i).size(); j += 3) {
						box++;
						checkBox(l, i, j, new HashSet<Integer>());
					}
				}
			} catch (Exception e) {
				System.out.println("The " + ordinal(box+1) + " box is not a valid box in a sudoku puzzle");
			}
		}

		private void checkBox(ArrayList<ArrayList<Integer>> l, int i, int j, Set<Integer> set) throws Exception {
			int counter = 3;
			System.out.print(l);
			for (int k = i; k < counter; k++) {
				for (int m = j; m < counter; m++) {
					set.add(l.get(k).get(m));
				}
			}
			if (set.size() != 9) {
				throw new IllegalArgumentException();
			}
		}

	}

	private String ordinal(int i) {
		String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
		switch (i % 100) {
		case 11:
		case 12:
		case 13:
			return i + "th";
		default:
			return i + sufixes[i % 10];

		}
	}

	public static void main(String[] args) {
		SudokuServer server = new SudokuServer();
		System.out.println("Welcome to the Sudoku validation server!");
		try {
			ServerSocket sock = new ServerSocket(6013);
			// now listen for connections
			while (true) {
				Socket client = sock.accept();
				// we have a connection
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				ArrayList<ArrayList<Integer>> list = (ArrayList<ArrayList<Integer>>) ois.readObject();
				System.out.println(list);
				server.new BoxThread(list).start();
				server.new ColThread(list).start();
				server.new RowThread(list).start();
				// close the socket and resume listening for more connections
				client.close();
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
