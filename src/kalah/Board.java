package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.IBoard;
import kalah.Interface.IHouse;
import kalah.Interface.IStore;

import java.util.ArrayList;
import java.util.List;

public class Board implements IBoard {
    final private boolean PLAYER_1 = true;
    final private boolean PLAYER_2 = false;
    private boolean _turn = PLAYER_1;
    private List<IStore> _p1Stores = new ArrayList<>();
    private List<IStore> _p2Stores = new ArrayList<>();
    private IHouse _p1house = new House();
    private IHouse _p2house = new House();
    private boolean _stop = false; //TODO might not need
    private Boolean _win = null;

    public Board(IO io) {
        setUp();
        while (!ifOver()) {
            printState(io);
            String command = io.readFromKeyboard("Player P" + (_turn ? "1" : "2") + "'s turn - Specify house number or 'q' to quit: ");
            if (command.equals("q")) {
                break;
            } else if (command.matches("[1-6]")) {
                int number = Integer.valueOf(command);
                doAction(number, io);
            }

            if (_stop) {
                break;
            }
        }
        io.println("Game over");
        printState(io);
        if (_win!=null){
//            io.println("STOP");
        }

    }

    private void doAction(int storeNum, IO io) {
        // check if the 'position' has values
        IStore oriStore = (_turn ? _p1Stores.get(storeNum - 1) : _p2Stores.get(storeNum - 1));
        int seeds = oriStore.takeAll();
        if (seeds == 0){
            io.println("House is empty. Move again.");
            return;
        }

        boolean turn = _turn;
        boolean midpoint = true;
        while (seeds > 0) {
            if (midpoint) {
                for (IStore store : (turn ? _p1Stores : _p2Stores)) {
                    if (store.getNumber() > storeNum) {
                        store.add(1);
                        seeds--;
                        // if it ends on a store, swap player
                        if (seeds == 0) {
                            if (store.getAmount()==1){
                                int pos = store.getNumber()-1;
                                int add = store.takeAll();
                                // get all from opposite
                                (_turn ? _p1house : _p2house).addAmount((turn ? _p2Stores : _p1Stores).get(5-pos).takeAll()+add);
                            }
                            _turn = !_turn;
                            break;
                        }

                    }
                }
                midpoint = false;
            } else {
                if (!turn == _turn) {
                    (_turn ? _p1house : _p2house).addAmount(1);
                    seeds--;
                    if (seeds == 0) {
                        //no need to change players
                        break; //TODO return?
                    }
                }
                for (IStore store : (turn ? _p1Stores : _p2Stores)) {
                    store.add(1);
                    seeds--;
                    // if it ends on a store, swap player
                    if (seeds == 0) {
                        if (store.getAmount()==1 && turn == _turn){
                            int pos =  store.getNumber()-1;
                            int add = store.takeAll();
                            // get all from opposite
                            (_turn ? _p1house : _p2house).addAmount((turn ? _p2Stores : _p1Stores).get(5-pos).takeAll()+add);
                        }
                        _turn = !_turn;
                        break;
                        //TODO add logic for checking if last spot was empty
                    }
                }
            }

            turn = !turn;
        }
    }

    private boolean ifOver(){
        //check if the stores are empty
        for (IStore store : (_turn? _p1Stores : _p2Stores)){
            if (store.getNumber()!=0){
                return false;
            }
        }
        // set winner
        _win = _turn;
        return true;
    }

    private void setUp() {
        for (int i = 1; i <= 6; i++) {
            _p1Stores.add(new Store(i));
            _p2Stores.add(new Store(i));
        }
//        _p1house.add(new House());
//        _p2house.add(new House());
    }

    private void printState(IO io) {
        int p1Amount = _p1house.getAmount();
        int p2Amount = _p2house.getAmount();

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");

        io.print("| P2 ");
        for (int i = 6; i > 0; i--) {
            int amount = _p2Stores.get(i - 1).getAmount();
            io.print("| " + i + "["  + (amount<10 ? " "+ amount : amount) + "] ");
        }
        io.println("| " + (p1Amount > 9 ? p1Amount / 10 : " ") + _p1house.getAmount() % 10 + " |");

        io.println("|    |-------+-------+-------+-------+-------+-------|    |");

        io.print("| " + (p2Amount > 9 ? p2Amount / 10 : " ") + _p2house.getAmount() % 10 + " ");
        for (int i = 0; i < 6; i++) {
            int amount = _p1Stores.get(i).getAmount();
            io.print("| " + (i + 1) + "[" +  (amount<10 ? " "+ amount : amount)  + "] ");
        }
        io.println("| P1 |");

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");

    }
}
