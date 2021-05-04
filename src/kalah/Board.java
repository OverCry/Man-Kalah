package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.*;
import kalah.Singleton.Printer;

import java.util.ArrayList;
import java.util.List;

/**
 * Board for the game (Man)Kala
 * Contains all objects relevant to the game
 * Written by: Wong Chong
 */
public class Board implements IBoard {
    private int _turn = 0;
    private List<ITeam> _teams = new ArrayList<>();
    private IO _io;
    private Printer _printer;

    /**
     * Field variables for modularity
     * These are default values
     */
    private int _stalls = 6;
    private int _startingSeeds = 4;
    private int _players = 2;

    public Board(IO io) {
        setUp();
        _io = io;
        _printer = Printer.getInstance();
    }

    public Board(IO io,int stalls,int startSeeds, int players) {
        _stalls = stalls;
        _startingSeeds = startSeeds;
        _players = players;
        setUp();
        _io = io;
        _printer = Printer.getInstance();
    }

    public void play(){
        _printer.printState(_io, _teams,_stalls);
        while (!ifOver()) {
            String command = _io.readFromKeyboard("Player P" + (_turn+1) + "'s turn - Specify house number or 'q' to quit: ");
            if (command.equals("q")) {
                break;
            } else if (command.matches("[1-9]*")) {
                int number = Integer.parseInt(command);
                if (number<=_stalls) {
                    doAction(number);
                }
            }
            _printer.printState(_io, _teams,_stalls);
        }

        _io.println("Game over");
        _printer.printState(_io, _teams,_stalls);
        // check if the game naturally finished
        if (ifOver()){
            _printer.printResult(_io, _teams);
        }
    }

    private void doAction(int storeNum) {
        ITeam team = _teams.get(_turn);
        // check if the number is a valid move
        IStore oriStore = team.getStore(storeNum);
        int seeds = oriStore.takeAll();
        // check if empty
        if (seeds == 0){
            _io.println("House is empty. Move again.");
            return;
        }

        if (!team.moveAtMid(storeNum,seeds,_turn+1)){
            _turn=(_turn+1)%_players;
        }
    }

    @Override
    public void reset() {
        _teams = new ArrayList<>();
        setUp();
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
        // initilize all teams
        for (int i =1;i<=_players;i++){
            _teams.add(new Team(_stalls,_startingSeeds,i,_players));
        }
        //link each team to the next person
        for (ITeam team: _teams){
            team.addNext(_teams.get((team.getTeamNumber()%_players)));
        }

    }


}