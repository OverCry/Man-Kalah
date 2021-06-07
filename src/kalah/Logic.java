package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.*;
import kalah.Singleton.Printer;
import kalah.Strategy.MovementStrategy;
import kalah.Strategy.Player1Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * kalah.Board for the game (Man)Kala
 * Contains all objects relevant to the game
 * Written by: Wong Chong
 */
public class Logic implements ILogic {
    private int _turn = 0;
    private IO _io;
    private Printer _printer = Printer.getInstance();
    private Board _board;

    /**
     * Field variables for modularity
     * These are default values
     */
    private int _stalls = 6;
    private int _startingSeeds = 4;
    private int _players = 2;

    MovementStrategy player1Strat;// = new Player1Strategy(Board);

    public Logic(IO io) {
        _board = new Board(_players,_stalls,_startingSeeds);
        _io = io;
    }

    public void play(){
        _printer.printState(_io,  _board.getTeams(),_stalls);
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
            _printer.printState(_io,  _board.getTeams(),_stalls);
        }

        _io.println("Game over");
        _printer.printState(_io,  _board.getTeams(),_stalls);
        // check if the game naturally finished
        if (ifOver()){
            _printer.printResult(_io,  _board.getTeams());
        }
    }

    private void doAction(int storeNum) {
        ITeam team = _board.getTeams().get(_turn);
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


    /**
     * Check if the game does not have a valid move
     * @return true if game is over
     */
    private boolean ifOver(){
        //check if the stores are empty
        for (IStore store : (_board.getTeams().get(_turn).getStores())){
            if (store.getAmount()!=0){
                return false;
            }
        }
        return true;
    }


}