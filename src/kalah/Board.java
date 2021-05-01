package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.IBoard;

public class Board implements IBoard {
    final private  boolean PLAYER_1 = true;
    final private  boolean PLAYER_2 = false;
    private boolean turn = PLAYER_1;
    public Board (IO io){
        printState(io);

    }

    private void printState(IO io){
        // Replace what's below with your implementation
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("Player P"+(turn ? "1":"2")+"'s turn - Specify house number or 'q' to quit: ");
    }
}
