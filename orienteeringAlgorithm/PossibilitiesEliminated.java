/**
 * Created by danielmacario on 14-10-23.
 */
public class PossibilitiesEliminated {

    private int countOnClear;
    private int countOnObstacle;

    public PossibilitiesEliminated() { }

    public PossibilitiesEliminated(int countOnClear, int countOnObstacle) {
        this.countOnClear = countOnClear;
        this.countOnObstacle = countOnObstacle;
    }

    public int getCountOnClear() {
        return countOnClear;
    }

    public void setCountOnClear(int countOnClear) {
        this.countOnClear = countOnClear;
    }

    public int getCountOnObstacle() {
        return countOnObstacle;
    }

    public void setCountOnObstacle(int countOnObstacle) {
        this.countOnObstacle = countOnObstacle;
    }

    public int getMaxEliminated() {
        return Math.max(countOnClear, countOnClear);
    }

    public int max() {
        return Math.max(countOnClear, countOnObstacle);
    }

    public int min() {
        return Math.min(countOnClear, countOnObstacle);
    }

    public void submitCount(Obstacle obs, int eliminatedPosCount) {

        if (obs == Obstacle.CLEAR) {
            countOnClear = eliminatedPosCount;
        } else {
            countOnObstacle = eliminatedPosCount;
        }

    }
}
