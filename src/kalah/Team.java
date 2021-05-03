package kalah;

import kalah.Interface.IHouse;
import kalah.Interface.IStore;
import kalah.Interface.ITeam;

import java.util.ArrayList;
import java.util.List;

public class Team implements ITeam {
    private List<IStore> _stores = new ArrayList<>();
    private IHouse _house = new House();

    public Team(int stalls,int startingSeeds){
        for (int i = 1; i <= stalls; i++) {
            _stores.add(new Store(i,startingSeeds));
        }
    }

    @Override
    public IHouse getHouse() {
        return _house;
    }

    @Override
    public IStore getStore(int storeNumber) {
        return _stores.get(storeNumber-1);
    }

    @Override
    public List<IStore> getStores() {
        return _stores;
    }

    @Override
    public int getScore() {
        int res = _house.getAmount();
        for (IStore s: _stores){
            res+=s.getAmount();
        }
        return res;
    }
}
