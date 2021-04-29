package kalah;

import kalah.Interface.IStorage;

public class Storage implements IStorage {
    int _amount = 0;

    @Override
    public int getAmount() {
        return _amount;
    }

    @Override
    public void addOne() {
        _amount++;

    }
}
