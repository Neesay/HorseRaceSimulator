import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 *
 * @author McFarewell, Yaseen Alam
 * @version 1.1
 */
public class Race {
    public final int raceLength;
    public final int totalLanes;
    public int terrain = 0;
    public double terrainMultiplier = 0.3;
    public List<Horse> horses = new ArrayList<>();
    public ArrayList<Horse> winningHorses = new ArrayList<>();
    public List<List<Object>> statistics = new ArrayList<>();
    public JTextArea textArea;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, ArrayList<Horse> new_horses, int new_terrain, JTextArea new_textArea) {
        // initialise instance variables
        raceLength = distance;
        this.totalLanes = new_horses.size();
        horses = new_horses;
        this.terrain = new_terrain;
        this.textArea = new_textArea;
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */
    public void startRace() {
        new Thread(() -> {
            setTerrainMultiplier();

            // reset all the lanes (all horses not fallen and back to 0)
            for (Horse horse : horses) {
                horse.goBackToStart();
            }

            long startTime = System.currentTimeMillis();

            ArrayList<Horse> fallenHorses = new ArrayList<>();

            boolean finished = false;
            while (!finished) {
                // print the race positions
                SwingUtilities.invokeLater(() -> {
                    printRace();
                    textArea.revalidate();
                    textArea.repaint();
                });

                // move each horse
                for (Horse horse : horses) {
                    moveHorse(horse);
                }

                // loop to find any winning horses
                for (Horse horse : horses) {
                    if (raceWonBy(horse)) {
                        winningHorses.add(horse);
                    }
                }

                // if all horses fell
                for (Horse horse : horses) {
                    if (horse.hasFallen()) {
                        if (!fallenHorses.contains(horse)) {
                            long timeElapsed = System.currentTimeMillis() - startTime;
                            horse.totalTimeSpent += timeElapsed;
                            horse.totalDistance += horse.distanceTravelled;

                            fallenHorses.add(horse);
                        }
                    }
                }

                if (fallenHorses.size() == horses.size()) {
                    // print terminal message
                    textArea.append("");
                    textArea.append("All horses have fallen. There are no winners.\n");

                    // end loop
                    finished = true;
                }

                // if one, two or three horses win
                if (winningHorses.size() == 1) {
                    // print terminal message with the name of one winning horse
                    textArea.append("");
                    textArea.append("And the winner is " + winningHorses.getFirst().getName() + "\n");

                    // end loop
                    finished = true;
                } else if (winningHorses.size() > 1) {
                    // print terminal message with the name of two winning horses
                    textArea.append("");
                    textArea.append("And the winners are " + winningHorses.getFirst().getName());

                    for (int i = 1; i < winningHorses.size(); i++) {
                        if (i == winningHorses.size() - 1) {
                            textArea.append(" and ");
                        } else {
                            textArea.append(", ");
                        }
                        textArea.append(winningHorses.get(i).getName());
                    }
                    textArea.append("\n");

                    // end loop
                    finished = true;
                }

                // wait for 100 milliseconds
                try {
                    TimeUnit.MILLISECONDS.sleep(350);
                } catch (Exception ignored) {}

                if (!finished) {
                    SwingUtilities.invokeLater(() -> {
                        textArea.setText("");
                    });
                }
            }

            for (Horse horse : winningHorses) {
                horse.addWin();
            }

            for (Horse horse : horses) {
                horse.addRaces();
                horse.setProbability();
                if (!fallenHorses.contains(horse)) {
                    long timeElapsed = System.currentTimeMillis() - startTime;
                    horse.totalTimeSpent += timeElapsed;
                    horse.totalDistance += horse.distanceTravelled;

                    fallenHorses.add(horse);
                }
            }

            statistics.add(Collections.singletonList(winningHorses));
        }).start();
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen()) {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence()) {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence())) {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse) {
        if (theHorse.getDistanceTravelled() == raceLength) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        printLane(lane1Horse);
        System.out.println();
        
        printLane(lane2Horse);
        System.out.println();
        
        printLane(lane3Horse);
        System.out.println();
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse) {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen()) {
            System.out.print('\u2322');
        } else {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times) {
        int i = 0;
        while (i < times) {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}
