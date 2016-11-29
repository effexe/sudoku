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
			try {
				ArrayList<ArrayList<Integer>> l = (ArrayList<ArrayList<Integer>>) ois.readObject();
				for(int i = 0; i < l.size(); i ++){
					Set<Integer> set = new HashSet<Integer>();
					for(int j = 0; j < l.get(i).size(); j++){
						set.add(l.get(i).get(j));
					}
					if(set.size() != 9){
						//error
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			System.out.println("Hello from a thread!");
		}

	}

	public class ColThread extends Thread {

		private ObjectInputStream ois;

		public ColThread(ObjectInputStream ois) {
			this.ois = ois;
		}

		public void run() {
			try {
				ArrayList<ArrayList<Integer>> l = (ArrayList<ArrayList<Integer>>) ois.readObject();
				for(int i = 0; i < l.size(); i ++){
					Set<Integer> set = new HashSet<Integer>(l.get(i));
					if(set.size() != 9){
						//error
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			System.out.println("Hello from a thread!");
		}

	}

	public class BoxThread extends Thread {

		private ObjectInputStream ois;

		public BoxThread(ObjectInputStream ois) {
			this.ois = ois;
		}

		public void run() {
			try {
				ArrayList<ArrayList<Integer>> l = (ArrayList<ArrayList<Integer>>) ois.readObject();
				for(int i = 0; i < l.size(); i += 3){
					Set<Integer> set = new HashSet<Integer>();
					for(int j = 0; j < l.get(i).size(); j += 3){
						checkBox(l, i, j, set);
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			System.out.println("Hello from a thread!");
		}

		private void checkBox(ArrayList<ArrayList<Integer>> l, int i, int j, Set<Integer> set) {
			int counter = 3;
			for(int k = i; k < counter; k++) {
				for(int m = j; m < counter; m++) {
					set.add(l.get(k).get(m));
				}
			}
			if(set.size() != 9){
				//error
			}
		}

	}



	public static void main(String[] args) {
		SudokuServer server = new SudokuServer();
		try {
			ServerSocket sock = new ServerSocket(6013);
			// now listen for connections
			while (true) {
				Socket client = sock.accept();
				// we have a connection
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				server.new BoxThread(ois).run();
				server.new ColThread(ois).run();
				server.new RowThread(ois).run();
				PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
				// write the Date to the socket
				pout.println(new java.util.Date().toString());

				// close the socket and resume listening for more connections
				client.close();
			}
		}
		catch (IOException ioe) {
				System.err.println(ioe);
		}
	}
}

