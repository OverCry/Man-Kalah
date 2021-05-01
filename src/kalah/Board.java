package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.IBoard;
import kalah.Interface.IHouse;
import kalah.Interface.IStore;

import java.util.ArrayList;
import java.util.List;

public class Board implements IBoard {
    final private  boolean PLAYER_1 = true;
    final private  boolean PLAYER_2 = false;
    private boolean turn = PLAYER_1;
    private List<IStore> _p1Stores = new ArrayList<>();
    private List<IStore> _p2Stores = new ArrayList<>();
//    private List<IHouse> _p1house = new ArrayList<>(); //player 1 is first, player 2 is second
//    private List<IHouse> _p2house = new ArrayList<>();
    private IHouse _p1house = new House();
    private IHouse _p2house = new House();

    public Board (IO io){
        setUp();

//        while (true){
            printState(io);
//        }
    }

    private void setUp(){
        for (int i=0;i<6;i++){
            _p1Stores.add(new Store());
            _p2Stores.add(new Store());
        }
//        _p1house.add(new House());
//        _p2house.add(new House());
    }

    private void printState(IO io){
        int p1Amount = _p1house.getAmount();
        int p2Amount = _p2house.getAmount();

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");

        io.print("| P2 ");
        for (int i=6; i>0;i--){
            io.print("| "+ i+"[ "+ _p2Stores.get(i-1).getAmount()+"] ");
        }
        io.println("| "+ (p1Amount>9 ? p1Amount/10 : " ") + _p1house.getAmount()%10+" |");

        io.println("|    |-------+-------+-------+-------+-------+-------|    |");

        io.print("| "+ (p2Amount>9 ? p2Amount/10 : " ") + _p2house.getAmount()%10+" ");
        for (int i=0; i<6;i++){
            io.print("| "+ (i+1)+"[ "+ _p1Stores.get(i).getAmount()+"] ");
        }
        io.println("| P2 |");

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("Player P"+(turn ? "1":"2")+"'s turn - Specify house number or 'q' to quit: ");
    }
}
