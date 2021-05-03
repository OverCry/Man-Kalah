package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.IBoard;
import kalah.Interface.IHouse;
import kalah.Interface.IStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Board for the game (Man)Kala
 * Contains all objects relevant to the game
 * Written by: Wong Chong
 */
public class Board implements IBoard {
    final private boolean PLAYER_1 = true;
    final private boolean PLAYER_2 = false;
    private boolean _turn = PLAYER_1;
    private List<IStore> _p1Stores = new ArrayList<>();
    private List<IStore> _p2Stores = new ArrayList<>();
    private IHouse _p1house = new House();
    private IHouse _p2house = new House();
    private IO _printer;

    /**
     * Field variables for modularity
     */
    private int _stalls = 6;
    private int _startingSeeds = 4;
    private int _players = 2;

    public Board(IO io) {
        setUp();
        _printer = io;
    }

    public Board(IO io,int stalls,int startSeeds, int players) {
        _stalls = stalls;
        _startingSeeds = startSeeds;
        _players = players;
        setUp();
        _printer = io;
    }

    public void play(){
        printState();
        while (!ifOver()) {
            String command = _printer.readFromKeyboard("Player P" + (_turn ? "1" : "2") + "'s turn - Specify house number or 'q' to quit: ");
            if (command.equals("q")) {
                break;
            } else if (command.matches("[1-9]*")) {
                int number = Integer.parseInt(command);
                if (number<=_stalls) {
                    doAction(number);
                }
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
        // check if empty
        if (seeds == 0){
            _printer.println("House is empty. Move again.");
            return;
        }

        boolean side = _turn;
        boolean midpoint = true;
        while (seeds > 0) {
            if (midpoint) {
                for (IStore store : (side ? _p1Stores : _p2Stores)) {
                    if (store.getNumber() > storeNum) {
                        store.addAmount(1);
                        seeds--;
                        // if it ends on a store, swap player
                        if (seeds == 0) {
                            if (store.getAmount()==1){
                                int pos = store.getNumber()-1;
                                IStore opposite = (side ? _p2Stores : _p1Stores).get(5-pos);
                                if (opposite.getAmount()>0) {
                                    int add = store.takeAll();
                                    // get all from opposite
                                    (_turn ? _p1house : _p2house).addAmount((side ? _p2Stores : _p1Stores).get(5 - pos).takeAll() + add);
                                }
                            }
                            _turn = !_turn;
                            break;
                        }

                    }
                }
                midpoint = false;
            } else {
                // add a seed to our house before you start adding on the 'enemy' side
                if (!side == _turn) {
                    (_turn ? _p1house : _p2house).addAmount(1);
                    seeds--;
                    if (seeds == 0) {
                        //no need to change players
                        return;
                    }
                }
                for (IStore store : (side ? _p1Stores : _p2Stores)) {
                    store.addAmount(1);
                    seeds--;
                    // if it ends on a store, swap player
                    if (seeds == 0) {
                        if (store.getAmount()==1 && side == _turn){
                            int pos = store.getNumber()-1;
                            IStore opposite = (side ? _p2Stores : _p1Stores).get(5-pos);
                            if (opposite.getAmount()>0) {
                                int add = store.takeAll();
                                // get all from opposite
                                (_turn ? _p1house : _p2house).addAmount((side ? _p2Stores : _p1Stores).get(5 - pos).takeAll() + add);
                            }
                        }
                        _turn = !_turn;
                        break;
                    }
                }
            }

            side = !side;
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
        for (int i = 1; i <= _stalls; i++) {
            _p1Stores.add(new Store(i,_startingSeeds));
            _p2Stores.add(new Store(i,_startingSeeds));
        }
    }

    /**
     * Helper function to printing out the output
     * Current formatting expects only two players
     */
    private void printState() {

        _printer.print("+----");
        for (int i=0;i<_stalls;i++){
            _printer.print("+-------");
        }
        _printer.println("+----+");

        // p2 | stalls for p2 | score of p1
        _printer.print("| P2 ");
        Collections.reverse(_p2Stores);
        for (IStore store : _p2Stores) {
            _printer.print("|" + printNumber(store.getNumber()) + "["  + printNumber(store.getAmount()) + "] ");
        }
        Collections.reverse(_p2Stores);
        _printer.println("| " + printNumber(_p1house.getAmount()) + " |");

        _printer.print("|    |");
        for (int i=0;i<_stalls;i++){
            _printer.print("-------|");
        }
        _printer.println("    |");

        // score of p2 | stalls for p1 | p2
        _printer.print("| " + printNumber(_p2house.getAmount()) + " ");
        for (IStore store : _p1Stores){
            _printer.print("|" + printNumber(store.getNumber()) + "[" +  printNumber(store.getAmount())  + "] ");
        }
        _printer.println("| P1 |");

        _printer.print("+----");
        for (int i=0;i<_stalls;i++){
            _printer.print("+-------");
        }
        _printer.println("+----+");
    }

    /**
     * formats a number to the correct string format
     * ONLY intended for double digits
     * @param number
     * @return
     */
    private String printNumber(int number){
        if (number>9){
            return Integer.toString(number);
        }
        return " "+number;
    }

    /**
     * Print the result of the game when it has naturally finished
     */
    private void printResult(){
        int p1sum = _p1house.getAmount();
        int p2sum = _p2house.getAmount();

        // create a list of players

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
