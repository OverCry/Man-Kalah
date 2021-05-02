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
    private IO _printer;

    public Board(IO io) {
        setUp();
        _printer = io;
    }

    public void play(){
        printState();
        while (!ifOver()) {
            String command = _printer.readFromKeyboard("Player P" + (_turn ? "1" : "2") + "'s turn - Specify house number or 'q' to quit: ");
            if (command.equals("q")) {
                break;
            } else if (command.matches("[1-6]")) {
                int number = Integer.parseInt(command);
                doAction(number);
            }
            printState();
        }

        _printer.println("Game over");
        printState();
        // check if the game naturally finished
        if (ifOver()){
            printResult();
        }
    }

    private void doAction(int storeNum) {
        // check if the 'position' has values
        IStore oriStore = (_turn == PLAYER_1 ? _p1Stores.get(storeNum - 1) : _p2Stores.get(storeNum - 1));
        int seeds = oriStore.takeAll();
        if (seeds == 0){
            _printer.println("House is empty. Move again.");
            return;
        }

        boolean turn = _turn;
        boolean midpoint = true;
        while (seeds > 0) {
            if (midpoint) {
                for (IStore store : (turn ? _p1Stores : _p2Stores)) {
                    if (store.getNumber() > storeNum) {
                        store.addAmount(1);
                        seeds--;
                        // if it ends on a store, swap player
                        if (seeds == 0) {
                            if (store.getAmount()==1){
                                int pos = store.getNumber()-1;
                                IStore opposite = (turn ? _p2Stores : _p1Stores).get(5-pos);
                                if (opposite.getAmount()>0) {
                                    int add = store.takeAll();
                                    // get all from opposite
                                    (_turn ? _p1house : _p2house).addAmount((turn ? _p2Stores : _p1Stores).get(5 - pos).takeAll() + add);
                                }
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
                    store.addAmount(1);
                    seeds--;
                    // if it ends on a store, swap player
                    if (seeds == 0) {
                        if (store.getAmount()==1 && turn == _turn){
                            int pos = store.getNumber()-1;
                            IStore opposite = (turn ? _p2Stores : _p1Stores).get(5-pos);
                            if (opposite.getAmount()>0) {
                                int add = store.takeAll();
                                // get all from opposite
                                (_turn ? _p1house : _p2house).addAmount((turn ? _p2Stores : _p1Stores).get(5 - pos).takeAll() + add);
                            }
                        }
                        _turn = !_turn;
                        break;
                    }
                }
            }

            turn = !turn;
        }
    }

    /**
     * Check if the game does not have a valid move
     * @return true if game is over
     */
    private boolean ifOver(){
        //check if the stores are empty
        for (IStore store : (_turn? _p1Stores : _p2Stores)){
            if (store.getAmount()!=0){
                return false;
            }
        }
        return true;
    }

    /**
     * Set up stores
     */
    private void setUp() {
        for (int i = 1; i <= 6; i++) {
            _p1Stores.add(new Store(i));
            _p2Stores.add(new Store(i));
        }
    }

    /**
     * Helper function to printing out the output
     */
    private void printState() {
        int p1Amount = _p1house.getAmount();
        int p2Amount = _p2house.getAmount();

        _printer.println("+----+-------+-------+-------+-------+-------+-------+----+");

        _printer.print("| P2 ");
        for (int i = 6; i > 0; i--) {
            int amount = _p2Stores.get(i - 1).getAmount();
            _printer.print("| " + i + "["  + (amount<10 ? " "+ amount : amount) + "] ");
        }
        _printer.println("| " + (p1Amount <10 ? " "+p1Amount : p1Amount) + " |");

        _printer.println("|    |-------+-------+-------+-------+-------+-------|    |");

        _printer.print("| " + (p2Amount <10 ? " "+p2Amount : p2Amount) + " ");
        for (int i = 0; i < 6; i++) {
            int amount = _p1Stores.get(i).getAmount();
            _printer.print("| " + (i + 1) + "[" +  (amount<10 ? " "+ amount : amount)  + "] ");
        }
        _printer.println("| P1 |");

        _printer.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    /**
     * Print the result of the game when it has naturally finished
     */
    private void printResult(){
        int p1sum = _p1house.getAmount();
        int p2sum = _p2house.getAmount();

        for (IStore s: _p1Stores){
            p1sum+=s.getAmount();
        }
        for (IStore s: _p2Stores){
            p2sum+=s.getAmount();
        }

        _printer.println("\tplayer 1:"+p1sum);
        _printer.println("\tplayer 2:"+p2sum);
        if (p1sum!=p2sum){
            _printer.println("Player " + (p1sum>p2sum ? 1 : 2) + " wins!");
        } else {

            _printer.println("A tie!");
        }
    }
}
