package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.Interface.ILogic;

/**
 * Main class for (Man)Kalah
 * Written by: Wong Chong
 */
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		ILogic game = new Logic(io);

		game.play();
	}
}
