package kalah.Strategy;

import kalah.Board;
import kalah.Interface.IStore;
import kalah.Interface.ITeam;

public class Player2Strategy implements MovementStrategy{

    private Board _board;
    private ITeam _team;

    public Player2Strategy(Board board){
        _board=board;
    }

    public Player2Strategy(ITeam team){
        _team=team;
    }

    @Override
    public boolean move(int position,int seeds, int turn) {
        return _team.moveAtMid(position, seeds,  turn);
    }

    @Override
    public int getLegability(int storeNum) {
        IStore oriStore = _team.getStore(storeNum);
        int seeds = oriStore.takeAll();
        return seeds;
    }
}
