package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.IBoard;
import kalah.Interface.IHouse;
import kalah.Interface.IStore;
import kalah.Interface.ITeam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Board for the game (Man)Kala
 * Contains all objects relevant to the game
 * Written by: Wong Chong
 */
public class Board implements IBoard {
    final private int PLAYER_1 = 0;
    final private int PLAYER_2 = 1;
    private int _turn;
    private List<ITeam> _teams = new ArrayList<>();
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
        _turn = PLAYER_1;
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
            String command = _printer.readFromKeyboard("Player P" + (_turn+1) + "'s turn - Specify house number or 'q' to quit: ");
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
        IStore oriStore = _teams.get(_turn).getStore(storeNum);

        int seeds = oriStore.takeAll();
        // check if empty
        if (seeds == 0){
            _printer.println("House is empty. Move again.");
            return;
        }

        int side = _turn;
        boolean midpoint = true;
        while (seeds > 0) {
            if (midpoint) {
                for (IStore store : _teams.get(side).getStores()) {
                    if (store.getNumber() > storeNum) {
                        store.addAmount(1);
                        seeds--;
                        // if it ends on a store, swap player
                        if (seeds == 0) {
                            if (store.getAmount()==1){
                                int pos = store.getNumber();
                                IStore opposite = _teams.get(side==PLAYER_1 ? PLAYER_2:PLAYER_1).getStore(7-pos);

                                if (opposite.getAmount()>0) {
                                    int add = store.takeAll();
                                    // get all from opposite
                                    _teams.get(side==PLAYER_1 ? PLAYER_1:PLAYER_2).getHouse().addAmount(opposite.takeAll() + add);
                                }
                            }
                            _turn = (_turn==PLAYER_1 ? PLAYER_2 : PLAYER_1);
                            break;
                        }

                    }
                }
                midpoint = false;
            } else {
                if (side != _turn) {
                    (_teams.get(_turn)).getHouse().addAmount(1);
                    seeds--;
                    if (seeds == 0) {
                        break; //TODO return?
                    }
                }
                for (IStore store : _teams.get(side).getStores()) {
                    store.addAmount(1);
                    seeds--;
                    // if it ends on a store, swap player
                    if (seeds == 0) {
                        if (store.getAmount()==1 && side == _turn){
                            int pos = store.getNumber();
                            IStore opposite = _teams.get(side==PLAYER_1 ? PLAYER_2:PLAYER_1).getStore(7-pos);
                            if (opposite.getAmount()>0) {
                                int add = store.takeAll();
                                // get all from opposite
                                _teams.get(side==PLAYER_1 ? PLAYER_1:PLAYER_2).getHouse().addAmount(opposite.takeAll() + add);
                            }
                        }
                        _turn = (_turn==PLAYER_1 ? PLAYER_2 : PLAYER_1);
                        break;
                    }
                }
            }

            side = (side==PLAYER_1 ? PLAYER_2 : PLAYER_1);
        }
    }

    /**
     * Check if the game does not have a valid move
     * @return true if game is over
     */
    private boolean ifOver(){
        //check if the stores are empty
        for (IStore store : (_teams.get(_turn).getStores())){
            if (store.getAmount()!=0){
                return false;
            }
        }
        return true;
    }

    /**
     * Set up teams
     */
    private void setUp() {
        _teams.add(new Team(_stalls,_startingSeeds));
        _teams.add(new Team(_stalls,_startingSeeds));
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
        List<IStore> stores2 = _teams.get(PLAYER_2).getStores();
        Collections.reverse(stores2);
        for (IStore store : stores2) {
            _printer.print("|" + printNumber(store.getNumber()) + "["  + printNumber(store.getAmount()) + "] ");
        }
        Collections.reverse(stores2);
        _printer.println("| " + printNumber(_teams.get(PLAYER_1).getHouse().getAmount()) + " |");

        _printer.print("|    |");
        for (int i=0;i<_stalls-1;i++){
            _printer.print("-------+");
        }
        _printer.println("-------|    |");

        // score of p2 | stalls for p1 | p2
        List<IStore> stores1 = _teams.get(PLAYER_1).getStores();
        _printer.print("| " + printNumber(_teams.get(PLAYER_2).getHouse().getAmount()) + " ");
        for (IStore store : stores1){
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
        int p1sum = _teams.get(PLAYER_1).getScore();
        int p2sum = _teams.get(PLAYER_2).getScore();

        _printer.println("\tplayer 1:"+p1sum);
        _printer.println("\tplayer 2:"+p2sum);
        if (p1sum!=p2sum){
            _printer.println("Player " + (p1sum>p2sum ? 1 : 2) + " wins!");
        } else {
            _printer.println("A tie!");
        }
    }
}
