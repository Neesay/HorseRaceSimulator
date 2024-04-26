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
    String horseSymbol;
    String[] horseBody;
    int wins = 0;
    int numRaces = 0;
    double speed = 0;
    int totalDistance = 0;
    long totalTimeSpent = 0;
    double winningProbability = 0.5;
    String horseName;
    double horseConfidence;
    boolean horseFallen = false;
    int distanceTravelled = 0;

    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(String horseSymbol, String[] horseBody, String horseName, double horseConfidence) {
        this.horseSymbol = horseSymbol;
        this.horseBody = horseBody;
        this.horseName = horseName;
        if (horseConfidence >= 0 && horseConfidence <= 1) {
            this.horseConfidence = horseConfidence;
        } else {
            this.horseConfidence = 0.5;
        }
    }

    //Other methods of class Horse
    public void fall() {
        this.horseFallen = true;
    }

    public double getConfidence() {
        return this.horseConfidence;
    }

    public int getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public String getName() {
        return this.horseName;
    }

    public void setName(String new_name) {
        this.horseName = new_name;
    }

    public String getSymbol() {
        return this.horseSymbol;
    }

    public String[] getBody() {
        return this.horseBody;
    }

    public void goBackToStart() {
        this.horseFallen = false;
        this.distanceTravelled = 0;
    }

    public boolean hasFallen() {
        return this.horseFallen;
    }

    public void moveForward() {
        this.distanceTravelled++;
    }

    public void setConfidence(double newConfidence) {
        if (newConfidence >= 0 && newConfidence <= 1) {
            this.horseConfidence = newConfidence;
        } else {
            this.horseConfidence = 0.5;
        }
    }

    public void setSymbol(String newSymbol) {
        this.horseSymbol = newSymbol;
    }
}
