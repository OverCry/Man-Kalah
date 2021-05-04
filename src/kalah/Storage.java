package kalah;

public abstract class Storage {
    int _amount;

    public int getAmount() {
        return _amount;
    }

    public void addAmount(int amount) {
        _amount+=amount;
    }
}
