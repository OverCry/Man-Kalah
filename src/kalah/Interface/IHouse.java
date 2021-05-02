package kalah.Interface;

public interface IHouse{
    /**
     * get the amount of seeds already in the house
     * @return
     */
    public int getAmount();

    /**
     * add an amount to the player's house
     * @param amount
     */
    public void addAmount(int amount);
}
