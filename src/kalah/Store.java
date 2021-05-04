package kalah;

import kalah.Interface.IStore;

public class Store extends Storage implements IStore {

    private int _number;

    public Store(int number, int startingSeeds){
        _number = number; _amount =startingSeeds;
    }

    @Override
    public int getNumber() {
        return _number;
    }


    @Override
    public int takeAll() {
        int returning = getAmount();
        _amount=0;
        return returning;
    }
}
