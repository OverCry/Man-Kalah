package kalah;

import kalah.Interface.IStore;

public class Store implements IStore {
    int _amount = 4;
    int _number;

    public Store(int number){
        _number = number;
    }

    @Override
    public int getNumber() {
        return _number;
    }

    public int getAmount() {
        return _amount;
    }

    @Override
    public void add(int amount) {
        _amount+=amount;
    }


    @Override
    public int takeAll() {
        int returning = getAmount();
        _amount=0;
        return returning;
    }

}
