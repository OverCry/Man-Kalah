package kalah.Strategy;

import kalah.Interface.IStore;
import kalah.Interface.ITeam;

public interface MovementStrategy {

    public boolean move(int position,int seeds, int turn);
}
