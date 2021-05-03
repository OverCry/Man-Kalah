package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.Interface.IBoard;

/**
 * Main class for (Man)Kalah
 * Written by: Wong Chong
 */
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		IBoard game = new Board(io);
//		IBoard game = new Board(io,10,8,5);

		game.play();
	}
}
