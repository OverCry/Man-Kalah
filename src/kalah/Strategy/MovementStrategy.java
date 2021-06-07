package kalah.Strategy;

public interface MovementStrategy {

    /**
     * moves a seeds from the specified location
     * @param position
     * @param seeds
     * @param turn
     * @return tells if the player will change
     */
    public boolean move(int position,int seeds, int turn);

    /**
     * checks if the move can be made
     * @param storeNum
     * @return amount of seeds in the store selected
     */
    public int getLegibility(int storeNum);
}
