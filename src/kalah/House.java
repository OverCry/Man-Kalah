package kalah;

import kalah.Interface.IHouse;

public class House implements IHouse {
    int _amount = 0;

    public int getAmount() {
        return _amount;
    }

    public void addAmount(int amount) {
        _amount+= amount;
    }

}