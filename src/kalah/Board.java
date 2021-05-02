package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.IBoard;
import kalah.Interface.IHouse;
import kalah.Interface.IStore;
import kalah.Interface.ITeam;

import java.util.ArrayList;
import java.util.List;

public class Board implements IBoard {
    final private int PLAYER_1 = 0;
    final private int PLAYER_2 = 1;
    private int _turn;
    private List<ITeam> _teams = new ArrayList<>();
//    private List<IStore> _p1Stores = new ArrayList<>();
//    private List<IStore> _p2Stores = new ArrayList<>();
//    private IHouse _p1house = new House();
//    private IHouse _p2house = new House();
    private IO _printer;

    public Board(IO io) {
        setUp();
        _printer = io;
        _turn = PLAYER_1;
    }

    public void play(){
        printState();
        while (!ifOver()) {
            String command = _printer.readFromKeyboard("Player P" + (_turn+1) + "'s turn - Specify house number or 'q' to quit: ");
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
        IStore oriStore = _teams.get(_turn).getStore(storeNum);

        int seeds = oriStore.takeAll();
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
                                    _teams.get(side==PLAYER_1 ? PLAYER_2:PLAYER_1).getHouse().addAmount(opposite.takeAll() + add);
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
                                _teams.get(side==PLAYER_1 ? PLAYER_2:PLAYER_1).getHouse().addAmount(opposite.takeAll() + add);
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
        _teams.add(new Team());
        _teams.add(new Team());
    }

    /**
     * Helper function to printing out the output
     */
    private void printState() {
        int p1Amount = _teams.get(PLAYER_1).getHouse().getAmount();
        int p2Amount = _teams.get(PLAYER_2).getHouse().getAmount();
        List<IStore> p1Stores = _teams.get(PLAYER_1).getStores();
        List<IStore> p2Stores = _teams.get(PLAYER_2).getStores();

        _printer.println("+----+-------+-------+-------+-------+-------+-------+----+");
        _printer.print("| P2 ");
        for (int i = 6; i > 0; i--) {
            int amount = p2Stores.get(i - 1).getAmount();
            _printer.print("| " + i + "["  + (amount<10 ? " "+ amount : amount) + "] ");
        }
        _printer.println("| " + (p1Amount <10 ? " "+p1Amount : p1Amount) + " |");

        _printer.println("|    |-------+-------+-------+-------+-------+-------|    |");

        _printer.print("| " + (p2Amount <10 ? " "+p2Amount : p2Amount) + " ");
        for (int i = 0; i < 6; i++) {
            int amount = p1Stores.get(i).getAmount();
            _printer.print("| " + (i + 1) + "[" +  (amount<10 ? " "+ amount : amount)  + "] ");
        }

        _printer.println("| P1 |");
        _printer.println("+----+-------+-------+-------+-------+-------+-------+----+");
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
