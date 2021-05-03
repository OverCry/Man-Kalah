package kalah;

import kalah.Interface.IStore;

public class Store implements IStore {
    int _amount;
    int _number;

    public Store(int number, int startingSeeds){
        _number = number; _amount =startingSeeds;
    }

    @Override
    public int getNumber() {
        return _number;
    }

    public int getAmount() {
        return _amount;
    }

    @Override
    public void addAmount(int amount) {
        _amount+=amount;
    }

    @Override
    public int takeAll() {
        int returning = getAmount();
        _amount=0;
        return returning;
    }
}
