import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SudokuServer {

	public class RowThread extends Thread {

		private ObjectInputStream ois;

		public RowThread(ObjectInputStream ois) {
			this.ois = ois;
		}

		public void run() {
			int row = 0;
			try {
				ArrayList<ArrayList<Integer>> l = (ArrayList<ArrayList<Integer>>) ois.readObject();
				for(int i = 0; i < l.size(); i ++){
					row = i;
					Set<Integer> set = new HashSet<Integer>();
					for(int j = 0; j < l.get(i).size(); j++){
						set.add(l.get(i).get(j));
					}
					if(set.size() != 9){
						throw new IllegalArgumentException();
					}
				}
			} catch (Exception e){
				System.out.println("The " + row + "th row is not a valid row in a sudoku puzzle");
			}
		}

	}

	public class ColThread extends Thread {

		private ObjectInputStream ois;

		public ColThread(ObjectInputStream ois) {
			this.ois = ois;
		}

		public void run() {
			int col = 0;
			try {
				ArrayList<ArrayList<Integer>> l = (ArrayList<ArrayList<Integer>>) ois.readObject();
				for(int i = 0; i < l.size(); i ++){
					col = i;
					Set<Integer> set = new HashSet<Integer>(l.get(i));
					if(set.size() != 9){
						throw new IllegalArgumentException();
					}
				}
			} catch (Exception e){ 
				System.out.println("The " + col + "th column is not a valid column in a sudoku puzzle");
			}
		}

	}

	public class BoxThread extends Thread {

		private ObjectInputStream ois;

		public BoxThread(ObjectInputStream ois) {
			this.ois = ois;
		}

		public void run() {
			int box = 0;
			try {
				ArrayList<ArrayList<Integer>> l = (ArrayList<ArrayList<Integer>>) ois.readObject();
				for(int i = 0; i < l.size(); i += 3){
					box++;
					Set<Integer> set = new HashSet<Integer>();
					for(int j = 0; j < l.get(i).size(); j += 3){
						checkBox(l, i, j, set);
					}
				}
			} catch (Exception e){
				System.out.println("The " + box + "th box is not a valid box in a sudoku puzzle");
			}
		}

		private void checkBox(ArrayList<ArrayList<Integer>> l, int i, int j, Set<Integer> set) throws Exception {
			int counter = 3;
			for(int k = i; k < counter; k++) {
				for(int m = j; m < counter; m++) {
					set.add(l.get(k).get(m));
				}
			}
			if(set.size() != 9){
				throw new IllegalArgumentException();
			}
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
				server.new BoxThread(ois).start();
				server.new ColThread(ois).start();
				server.new RowThread(ois).start();
				// close the socket and resume listening for more connections
				client.close();
			}
		}
		catch (IOException ioe) {
				System.err.println(ioe);
		}
	}
}

