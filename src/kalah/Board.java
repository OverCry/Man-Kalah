package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.IBoard;
import kalah.Interface.IHouse;
import kalah.Interface.IStorage;
import kalah.Interface.IStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board implements IBoard {
    final private  boolean PLAYER_1 = true;
    final private  boolean PLAYER_2 = false;
    private boolean turn = PLAYER_1;
    private List<IStore> _p1Stores = new ArrayList<>();
    private List<IStore> _p2Stores = new ArrayList<>();
    private List<IHouse> _p1house = new ArrayList<>(); //player 1 is first, player 2 is second
    private List<IHouse> _p2house = new ArrayList<>(); //player 1 is first, player 2 is second

    public Board (IO io){
        setUp();

        while (true){
            printState(io);
        }
    }

    private void setUp(){
        for (int i=0;i<6;i++){
            _p1Stores.add(new Store());
            _p2Stores.add(new Store());
        }
        _p1house.add(new House());
        _p2house.add(new House());
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
