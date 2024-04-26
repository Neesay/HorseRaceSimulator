/**
 * The class Horse represents a horse in a simulation. It
 * has fields to store the horse's symbol, name, confidence
 * level, whether it has fallen or not, and the distance it
 * has travelled. Including methods that change the horse's
 * attributes in the simulation.
 *
 * @author Yaseen Alam
 * @version 1.0
 */
public class Horse {
    //Fields of class Horse
    char horseSymbol;
    String horseName;
    double horseConfidence;
    boolean horseFallen = false;
    int distanceTravelled = 0;

    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        if (horseConfidence >= 0 && horseConfidence <= 1) {
            this.horseConfidence = horseConfidence;
        } else {
            this.horseConfidence = 0.5;
        }
    }
    
    //Other methods of class Horse
    /**
     * Makes the horse fall
     */
    public void fall() {
        this.horseFallen = true;
    }

    /**
     * Returns the value of the horse's confidence
     * @return horseConfidence
     */
    public double getConfidence() {
        return this.horseConfidence;
    }

    /**
     * Returns the value of distance travelled by the horse
     * @return distanceTravelled
     */
    public int getDistanceTravelled() {
        return this.distanceTravelled;
    }

    /**
     * Returns the name of the horse
     * @return horseName
     */
    public String getName() {
        return this.horseName;
    }

    /**
     * Returns the horse's symbol
     * @return horseSymbol
     */
    public char getSymbol() {
        return this.horseSymbol;
    }

    /**
     * Makes the horse have zero distance travelled
     */
    public void goBackToStart() {
        this.horseFallen = false;
        this.distanceTravelled = 0;
    }

    /**
     * Returns the boolean value if the horse fell or not
     * @return horseFallen
     */
    public boolean hasFallen() {
        return this.horseFallen;
    }

    /**
     * Increments the horse's distance travelled by 1
     */
    public void moveForward() {
        this.distanceTravelled++;
    }

    /**
     * Sets a new value of the horse's confidence
     * @param newConfidence
     */
    public void setConfidence(double newConfidence) {
        if (newConfidence >= 0 && newConfidence <= 1) {
            this.horseConfidence = newConfidence;
        } else {
            this.horseConfidence = 0.5;
        }
    }

    /**
     * Sets a nw value of the horse's symbol
     * @param newSymbol
     */
    public void setSymbol(char newSymbol) {
        this.horseSymbol = newSymbol;
    }
}
