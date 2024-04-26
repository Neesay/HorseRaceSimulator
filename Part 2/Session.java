import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private static final String racingData = "RacingData.txt";
    private static final String horseData = "HorseData.ser";
    public static int money = 500;
    public static HashMap<Horse, Integer> bets = new HashMap<>();
    public static String username = "Username";
    public static ArrayList<Race> races = new ArrayList<>();
    public static Horse[] currentSelectedHorse = {new Horse("'", new String[]{" ", "_"},"DEFAULT", 0.5)};
    public static int currentTerrain = 0;
    public static int currentTerrainSelection = 0;
    public static int raceLength = 50;

    public static void main(String[] args) {
        ArrayList<Horse> horses = new ArrayList<>();

        Horse pippi = new Horse("*", new String[]{"O", "o"},"PIPPI LONGSTOCKING", 0.75);
        Horse kokomo = new Horse("^", new String[] {"X", "x"}, "KOKOMO", 0.65);
        Horse jefe = new Horse("`", new String[] {"#", "~"}, "EL JEFE", 0.45);
        Horse porty = new Horse("¿", new String[] {"f", "¬"}, "PORTY MC-ALLISTER", 0.45);

        horses.add(pippi);
        horses.add(kokomo);
        horses.add(jefe);
        horses.add(porty);

        /* USERNAME */
        welcome();
        setUsername();

        /* MAIN */
        JFrame mainFrame = new JFrame("Horse Race Simulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 720);

        Color pastelBLUE = new Color(186,225,255);
        Color pastelPINK = new Color(255,179,186);

        JTabbedPane panels = new JTabbedPane();
        panels.setBackground(pastelBLUE);

        /* RACING */
        JPanel racing = new JPanel();
        racing.setLayout(new BorderLayout());
        racing.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JPanel currentSelection = new JPanel();
        currentSelection.setLayout(new BorderLayout());
        currentSelection.add(new JLabel(username), BorderLayout.WEST);
        currentSelection.add(new JLabel(" Wallet: £" + money), BorderLayout.EAST);

        JPanel betPanel = new JPanel();
        betPanel.setLayout(new GridLayout(bets.size(), 2));
        betPanel.add(new JLabel("My Bets: "));
        betPanel.add(new JLabel(""));
        betPanel.add(new JLabel("NO BETS ADDED YET"));

        for (Map.Entry<Horse, Integer> entry : bets.entrySet()) {
            Horse horse = entry.getKey();
            int betAmount = entry.getValue();
            betPanel.add(new JLabel(horse.getName()));
            betPanel.add(new JLabel(Integer.toString(betAmount)));
        }

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear bets?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    currentSelection.removeAll();
                    currentSelection.add(new JLabel(username), BorderLayout.WEST);
                    currentSelection.add(new JLabel(" Wallet: £" + money), BorderLayout.EAST);
                    betPanel.removeAll();
                    betPanel.setLayout(new GridLayout(0, 2));
                    betPanel.add(new JLabel("My Bets:"));
                    betPanel.add(new JLabel(""));
                    betPanel.add(new JLabel("NO BETS ADDED YET"));
                    bets = new HashMap<>();
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            }
        });

        JPanel raceView = new JPanel();

        JButton startButton = new JButton("Start");

        JPanel bottomRacing = new JPanel();
        bottomRacing.setLayout(new BorderLayout());
        bottomRacing.add(betPanel, BorderLayout.CENTER);
        bottomRacing.add(startButton, BorderLayout.SOUTH);
        bottomRacing.add(clearButton, BorderLayout.EAST);

        racing.add(currentSelection, BorderLayout.NORTH);
        racing.add(bottomRacing, BorderLayout.SOUTH);

        /* HORSE */
        JPanel horsePanel = new JPanel();
        horsePanel.setLayout(new BorderLayout());
        horsePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JPanel horseCustomisationPanel = new JPanel();
        horseCustomisationPanel.setLayout(new BorderLayout());

        JPanel horsePictureHolder = new JPanel(new BorderLayout());

        JPanel horsePicture = new JPanel(new BorderLayout());
        horsePicture.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Font monoFont = new Font("Courier New", Font.PLAIN, 55);

        horsePicture.setLayout(new GridLayout(7, 1));
        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
        horseArt1.setFont(monoFont);
        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
        horseArt2.setFont(monoFont);
        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
        horseArt3.setFont(monoFont);
        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
        horseArt4.setFont(monoFont);
        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
        horseArt5.setFont(monoFont);
        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
        horseArt6.setFont(monoFont);

        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
        horsePicture.add(horseArt1);
        horsePicture.add(horseArt2);
        horsePicture.add(horseArt3);
        horsePicture.add(horseArt4);
        horsePicture.add(horseArt5);
        horsePicture.add(horseArt6);
        horsePictureHolder.add(horsePicture, BorderLayout.NORTH);
        horseCustomisationPanel.add(horsePictureHolder, BorderLayout.CENTER);
        horsePanel.add(horseCustomisationPanel, BorderLayout.CENTER);
        JPanel horseToolList = new JPanel();
        horseToolList.setLayout(new BoxLayout(horseToolList, BoxLayout.X_AXIS));


        JPanel horseNameList = new JPanel();
        horseNameList.setLayout(new BoxLayout(horseNameList, BoxLayout.Y_AXIS));
        List<JButton> horseButtons = new ArrayList<>();

        for (Horse horse : horses) {
            JButton horseButton = new JButton(horse.getName());
            horseButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, horseButton.getPreferredSize().height));
            horseButton.setBackground(pastelPINK);
            horseNameList.add(horseButton);
            horseButtons.add(horseButton);

            horseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentSelectedHorse = new Horse[]{horse};
                    horsePicture.removeAll();
                    JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                    horseArt1.setFont(monoFont);
                    JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                    horseArt2.setFont(monoFont);
                    JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                    horseArt3.setFont(monoFont);
                    JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                    horseArt4.setFont(monoFont);
                    JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                    horseArt5.setFont(monoFont);
                    JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                    horseArt6.setFont(monoFont);
                    horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                    horsePicture.add(horseArt1);
                    horsePicture.add(horseArt2);
                    horsePicture.add(horseArt3);
                    horsePicture.add(horseArt4);
                    horsePicture.add(horseArt5);
                    horsePicture.add(horseArt6);
                    horsePictureHolder.revalidate();
                    horsePictureHolder.repaint();
                }
            });
        }
        horsePanel.add(horseNameList, BorderLayout.EAST);

        JButton nameButton = new JButton("Name");
        nameButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameButton.getPreferredSize().height));
        horseToolList.add(nameButton);

        JButton eyeButton = new JButton("Eyes");
        eyeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, eyeButton.getPreferredSize().height));
        eyeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu eyeMenu = new JPopupMenu();
                JMenuItem option1 = new JMenuItem("Normal");
                option1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setSymbol("'");
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option2 = new JMenuItem("Happy");
                option2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setSymbol("^");
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option3 = new JMenuItem("Angry");
                option3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setSymbol("`");
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option4 = new JMenuItem("Dizzy");
                option4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setSymbol("*");
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option5 = new JMenuItem("Pimple");
                option5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setSymbol("¿");
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option6 = new JMenuItem("Hollow");
                option6.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setSymbol("°");
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                eyeMenu.add(option1);
                eyeMenu.add(option2);
                eyeMenu.add(option3);
                eyeMenu.add(option4);
                eyeMenu.add(option5);
                eyeMenu.add(option6);
                eyeMenu.show(eyeButton, 0, eyeButton.getHeight());
            }
        });
        horseToolList.add(eyeButton);
        JButton bodyButton = new JButton("Body");
        bodyButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, bodyButton.getPreferredSize().height));
        bodyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu bodyMenu = new JPopupMenu();
                JMenuItem option1 = new JMenuItem("Normal");
                option1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setBody(new String[]{" ", "_"});
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option2 = new JMenuItem("Diamonds");
                option2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setBody(new String[]{"X", "x"});
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option3 = new JMenuItem("Skeleton");
                option3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setBody(new String[]{"f", "¬"});
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option4 = new JMenuItem("Bubbly");
                option4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setBody(new String[]{"O", "o"});
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option5 = new JMenuItem("Cones");
                option5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setBody(new String[]{"}", ">"});
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                JMenuItem option6 = new JMenuItem("Chainmail");
                option6.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSelectedHorse[0].setBody(new String[]{"#", "~"});
                        horsePicture.removeAll();
                        JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                        horseArt1.setFont(monoFont);
                        JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                        horseArt2.setFont(monoFont);
                        JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                        horseArt3.setFont(monoFont);
                        JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                        horseArt4.setFont(monoFont);
                        JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                        horseArt5.setFont(monoFont);
                        JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                        horseArt6.setFont(monoFont);
                        horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                        horsePicture.add(horseArt1);
                        horsePicture.add(horseArt2);
                        horsePicture.add(horseArt3);
                        horsePicture.add(horseArt4);
                        horsePicture.add(horseArt5);
                        horsePicture.add(horseArt6);
                        horsePictureHolder.revalidate();
                        horsePictureHolder.repaint();
                    }
                });
                bodyMenu.add(option1);
                bodyMenu.add(option2);
                bodyMenu.add(option3);
                bodyMenu.add(option4);
                bodyMenu.add(option5);
                bodyMenu.add(option6);
                bodyMenu.show(eyeButton, 0, eyeButton.getHeight());
            }
        });
        horseToolList.add(bodyButton);

        JButton removeButton = new JButton("Remove Horse");
        removeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, removeButton.getPreferredSize().height));
        horseToolList.add(removeButton);

        JButton defaultButton = new JButton("Change to Default");
        defaultButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, defaultButton.getPreferredSize().height));
        horseToolList.add(defaultButton);

        horseCustomisationPanel.add(horseToolList, BorderLayout.SOUTH);

        JButton addHorseButton = new JButton("Add Horse");
        horsePanel.add(addHorseButton, BorderLayout.SOUTH);

        /* ENVIRONMENT */
        JPanel environment = new JPanel();
        environment.setLayout(new BorderLayout());
        environment.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        environment.setBackground(pastelBLUE);
        JPanel environmentSettings = new JPanel();
        environmentSettings.setLayout(new GridLayout(0,1));

        environmentSettings.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel numTracksLabel = new JLabel("Track Location: ");
        numTracksLabel.setFont(monoFont);
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setBackground(pastelBLUE);
        radioButtonPanel.setLayout(new GridLayout(3, 1));

        JRadioButton option1 = new JRadioButton("London Royal Gardens");
        JRadioButton option2 = new JRadioButton("Amazonian Exotic Jungle");
        JRadioButton option3 = new JRadioButton("Alaskan Filthy Plains");
        JRadioButton option4 = new JRadioButton("Sahara Harsh Deserts");
        JRadioButton option5 = new JRadioButton("Antarctica Frozen Fields");

        option1.setBackground(pastelBLUE);
        option2.setBackground(pastelBLUE);
        option3.setBackground(pastelBLUE);
        option4.setBackground(pastelBLUE);
        option5.setBackground(pastelBLUE);

        option1.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);
        group.add(option5);

        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTerrainSelection = 0;
            }
        });

        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTerrainSelection = 1;
            }
        });

        option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTerrainSelection = 2;
            }
        });

        option4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTerrainSelection = 3;
            }
        });

        option5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTerrainSelection = 4;
            }
        });

        radioButtonPanel.add(option1);
        radioButtonPanel.add(option2);
        radioButtonPanel.add(option3);
        radioButtonPanel.add(option4);
        radioButtonPanel.add(option5);

        environmentSettings.add(radioButtonPanel, BorderLayout.CENTER);

        environmentSettings.add(numTracksLabel);
        environmentSettings.add(radioButtonPanel);

        JSlider trackLengthSlider = new JSlider(JSlider.HORIZONTAL, 50, 100, 50);
        trackLengthSlider.setBackground(pastelBLUE);
        trackLengthSlider.setMajorTickSpacing(5);
        trackLengthSlider.setPaintTicks(true);
        trackLengthSlider.setPaintLabels(true);
        JLabel trackLengthLabel = new JLabel("Track Length (meters): ");
        trackLengthLabel.setFont(monoFont);
        environmentSettings.add(trackLengthLabel);
        environmentSettings.add(trackLengthSlider);

        JButton designButton = new JButton("Set Track Design");
        designButton.addActionListener(e -> {
            currentTerrain = currentTerrainSelection;
            raceLength = trackLengthSlider.getValue();

            JOptionPane.showMessageDialog(null, "New Track Design is Set!");
        });

        environment.add(environmentSettings, BorderLayout.CENTER);
        environment.add(designButton, BorderLayout.SOUTH);

        /* SETTINGS */
        JPanel statistics = new JPanel();
        statistics.setLayout(new BorderLayout());

        racing.setBackground(pastelBLUE);
        racing.setBackground(pastelBLUE);
        racing.setBackground(pastelBLUE);
        racing.setBackground(pastelBLUE);

        panels.addTab("Racing", racing);
        panels.addTab("Horses", horsePanel);
        panels.addTab("Environment", environment);
        panels.addTab("Statistics", statistics);

        mainFrame.add(panels);

        mainFrame.setVisible(true);

        JFrame tableFrame = new JFrame("");
        tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableFrame.setSize(800, 200);


        Object[][] data = new Object[horses.size()][5];
        for (int i = 0; i < horses.size(); i++) {
            addValue(data, i, 0, horses.get(i).getName());
            addValue(data, i, 1, (long) (horses.get(i).getConfidence()*100) + "%");
            addValue(data, i, 2, horses.get(i).getOddsString());
            addValue(data, i, 3, (int) horses.get(i).calculateProfit(100));
            addValue(data, i, 4, "Bet");
        }


        String[] columnNames = {"Name", "Confidence", "Betting Odds", "Potential Profit per £100", ""};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        centerPanel.setBackground(pastelBLUE);
        raceView.setBackground(pastelBLUE);
        tableFrame.setBackground(pastelBLUE);

        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));

        JTextArea textArea = new JTextArea(70, 190);
        Font monoFontTextArea = new Font("Courier New", Font.PLAIN, 9);
        textArea.setFont(monoFontTextArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        table.setGridColor(pastelBLUE);
        table.setBackground(pastelPINK);

        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / table.getRowHeight();

                if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                    if (table.getValueAt(row, column).equals("Bet")) {
                        JFrame betsFrame = new JFrame("Make Bet");
                        int amount = Integer.parseInt(JOptionPane.showInputDialog(betsFrame, "Enter your bet amount on "+ horses.get(row).getName() +":"));
                        if (amount > 0 && amount <= money) {
                            money -= amount;
                            if (bets.get(horses.get(row)) == null) {
                                bets.put(horses.get(row), amount);
                            } else {
                                bets.put(horses.get(row), bets.get(horses.get(row)) + amount);
                            }
                            currentSelection.removeAll();
                            currentSelection.add(new JLabel(username), BorderLayout.WEST);
                            currentSelection.add(new JLabel(" Wallet: £" + money), BorderLayout.EAST);
                            betPanel.removeAll();
                            betPanel.add(new JLabel("My Bets:"));
                            betPanel.add(new JLabel(""));
                            betPanel.setLayout(new GridLayout(0, 2));
                            for (Map.Entry<Horse, Integer> entry : bets.entrySet()) {
                                Horse horse = entry.getKey();
                                int betAmount = entry.getValue();
                                betPanel.add(new JLabel(horse.getName()));
                                betPanel.add(new JLabel("£" + betAmount));
                            }
                            mainFrame.revalidate();
                            mainFrame.repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "You do not have enough £!");
                        }
                    }
                }
            }
        });

        defaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelectedHorse = new Horse[]{new Horse("'", new String[]{" ", "_"}, "DEFAULT", 0.5)};

                horsePicture.removeAll();
                JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                horseArt1.setFont(monoFont);
                JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                horseArt2.setFont(monoFont);
                JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                horseArt3.setFont(monoFont);
                JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                horseArt4.setFont(monoFont);
                JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                horseArt5.setFont(monoFont);
                JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                horseArt6.setFont(monoFont);
                horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                horsePicture.add(horseArt1);
                horsePicture.add(horseArt2);
                horsePicture.add(horseArt3);
                horsePicture.add(horseArt4);
                horsePicture.add(horseArt5);
                horsePicture.add(horseArt6);
                horsePictureHolder.revalidate();
                horsePictureHolder.repaint();
                currentSelection.removeAll();
                currentSelection.add(new JLabel(username), BorderLayout.WEST);
                currentSelection.add(new JLabel(" Wallet: £" + money), BorderLayout.EAST);
                betPanel.removeAll();
                betPanel.setLayout(new GridLayout(0, 2));
                betPanel.add(new JLabel("My Bets:"));
                betPanel.add(new JLabel(""));
                betPanel.add(new JLabel("NO BETS ADDED YET"));
                bets = new HashMap<>();

                tableFrame.removeAll();
                Object[][] data = new Object[horses.size()][5];
                for (int i = 0; i < horses.size(); i++) {
                    addValue(data, i, 0, horses.get(i).getName());
                    addValue(data, i, 1, (long) (horses.get(i).getConfidence()*100) + "%");
                    addValue(data, i, 2, horses.get(i).getOddsString());
                    addValue(data, i, 3, (int) horses.get(i).calculateProfit(100));
                    addValue(data, i, 4, "Bet");
                }


                String[] columnNames = {"Name", "Confidence", "Betting Odds", "Potential Profit per £100", ""};

                DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == 4;
                    }
                };

                table.setModel(model);
                table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
                table.revalidate();

                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });

        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame nameFrame = new JFrame("Name Update");
                String name = JOptionPane.showInputDialog(nameFrame, "Enter a new name:");
                currentSelectedHorse[0].setName(name);

                horsePicture.removeAll();
                JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                horseArt1.setFont(monoFont);
                JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                horseArt2.setFont(monoFont);
                JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                horseArt3.setFont(monoFont);
                JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                horseArt4.setFont(monoFont);
                JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                horseArt5.setFont(monoFont);
                JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                horseArt6.setFont(monoFont);
                horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                horsePicture.add(horseArt1);
                horsePicture.add(horseArt2);
                horsePicture.add(horseArt3);
                horsePicture.add(horseArt4);
                horsePicture.add(horseArt5);
                horsePicture.add(horseArt6);
                horsePictureHolder.revalidate();
                horsePictureHolder.repaint();
                currentSelection.removeAll();
                currentSelection.add(new JLabel(username), BorderLayout.WEST);
                currentSelection.add(new JLabel(" Wallet: £" + money), BorderLayout.EAST);
                betPanel.removeAll();
                betPanel.setLayout(new GridLayout(0, 2));
                betPanel.add(new JLabel("My Bets:"));
                betPanel.add(new JLabel(""));
                betPanel.add(new JLabel("NO BETS ADDED YET"));
                bets = new HashMap<>();

                tableFrame.removeAll();
                Object[][] data = new Object[horses.size()][5];
                for (int i = 0; i < horses.size(); i++) {
                    addValue(data, i, 0, horses.get(i).getName());
                    addValue(data, i, 1, (long) (horses.get(i).getConfidence()*100) + "%");
                    addValue(data, i, 2, horses.get(i).getOddsString());
                    addValue(data, i, 3, (int) horses.get(i).calculateProfit(100));
                    addValue(data, i, 4, "Bet");
                }


                String[] columnNames = {"Name", "Confidence", "Betting Odds", "Potential Profit per £100", ""};

                DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == 4;
                    }
                };

                table.setModel(model);
                table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
                table.revalidate();

                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });

        addHorseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to add the horse", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    horses.add(currentSelectedHorse[0]);
                    horsePicture.removeAll();
                    JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                    horseArt1.setFont(monoFont);
                    JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                    horseArt2.setFont(monoFont);
                    JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                    horseArt3.setFont(monoFont);
                    JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                    horseArt4.setFont(monoFont);
                    JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                    horseArt5.setFont(monoFont);
                    JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                    horseArt6.setFont(monoFont);
                    horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                    horsePicture.add(horseArt1);
                    horsePicture.add(horseArt2);
                    horsePicture.add(horseArt3);
                    horsePicture.add(horseArt4);
                    horsePicture.add(horseArt5);
                    horsePicture.add(horseArt6);
                    horsePictureHolder.revalidate();
                    horsePictureHolder.repaint();
                    currentSelection.removeAll();
                    currentSelection.add(new JLabel(username), BorderLayout.WEST);
                    currentSelection.add(new JLabel(" Wallet: £" + money), BorderLayout.EAST);
                    betPanel.removeAll();
                    betPanel.setLayout(new GridLayout(0, 2));
                    betPanel.add(new JLabel("My Bets:"));
                    betPanel.add(new JLabel(""));
                    betPanel.add(new JLabel("NO BETS ADDED YET"));
                    bets = new HashMap<>();

                    tableFrame.removeAll();
                    Object[][] data = new Object[horses.size()][5];
                    for (int i = 0; i < horses.size(); i++) {
                        addValue(data, i, 0, horses.get(i).getName());
                        addValue(data, i, 1, (long) (horses.get(i).getConfidence()*100) + "%");
                        addValue(data, i, 2, horses.get(i).getOddsString());
                        addValue(data, i, 3, (int) horses.get(i).calculateProfit(100));
                        addValue(data, i, 4, "Bet");
                    }


                    String[] columnNames = {"Name", "Confidence", "Betting Odds", "Potential Profit per £100", ""};

                    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return column == 4;
                        }
                    };

                    table.setModel(model);
                    table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
                    table.revalidate();

                    horseNameList.removeAll();

                    List<JButton> horseButtons = new ArrayList<>();

                    for (Horse horse : horses) {
                        JButton horseButton = new JButton(horse.getName());
                        horseButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, horseButton.getPreferredSize().height));
                        horseNameList.add(horseButton);
                        horseButtons.add(horseButton);

                        horseButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                currentSelectedHorse = new Horse[]{horse};
                                horsePicture.removeAll();
                                JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                                horseArt1.setFont(monoFont);
                                JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                                horseArt2.setFont(monoFont);
                                JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                                horseArt3.setFont(monoFont);
                                JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                                horseArt4.setFont(monoFont);
                                JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                                horseArt5.setFont(monoFont);
                                JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                                horseArt6.setFont(monoFont);
                                horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                                horsePicture.add(horseArt1);
                                horsePicture.add(horseArt2);
                                horsePicture.add(horseArt3);
                                horsePicture.add(horseArt4);
                                horsePicture.add(horseArt5);
                                horsePicture.add(horseArt6);
                                horsePictureHolder.revalidate();
                                horsePictureHolder.repaint();
                            }
                        });
                    }

                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the horse", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    horses.remove(currentSelectedHorse[0]);
                    currentSelectedHorse[0] = horses.getFirst();
                    horsePicture.removeAll();
                    JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                    horseArt1.setFont(monoFont);
                    JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                    horseArt2.setFont(monoFont);
                    JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                    horseArt3.setFont(monoFont);
                    JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                    horseArt4.setFont(monoFont);
                    JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                    horseArt5.setFont(monoFont);
                    JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                    horseArt6.setFont(monoFont);
                    horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                    horsePicture.add(horseArt1);
                    horsePicture.add(horseArt2);
                    horsePicture.add(horseArt3);
                    horsePicture.add(horseArt4);
                    horsePicture.add(horseArt5);
                    horsePicture.add(horseArt6);
                    horsePictureHolder.revalidate();
                    horsePictureHolder.repaint();
                    currentSelection.removeAll();
                    currentSelection.add(new JLabel(username), BorderLayout.WEST);
                    currentSelection.add(new JLabel(" Wallet: £" + money), BorderLayout.EAST);
                    betPanel.removeAll();
                    betPanel.setLayout(new GridLayout(0, 2));
                    betPanel.add(new JLabel("My Bets:"));
                    betPanel.add(new JLabel(""));
                    betPanel.add(new JLabel("NO BETS ADDED YET"));
                    bets = new HashMap<>();

                    tableFrame.removeAll();
                    Object[][] data = new Object[horses.size()][5];
                    for (int i = 0; i < horses.size(); i++) {
                        addValue(data, i, 0, horses.get(i).getName());
                        addValue(data, i, 1, (long) (horses.get(i).getConfidence()*100) + "%");
                        addValue(data, i, 2, horses.get(i).getOddsString());
                        addValue(data, i, 3, (int) horses.get(i).calculateProfit(100));
                        addValue(data, i, 4, "Bet");
                    }


                    String[] columnNames = {"Name", "Confidence", "Betting Odds", "Potential Profit per £100", ""};

                    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return column == 4;
                        }
                    };

                    table.setModel(model);
                    table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
                    table.revalidate();

                    horseNameList.removeAll();

                    List<JButton> horseButtons = new ArrayList<>();

                    for (Horse horse : horses) {
                        JButton horseButton = new JButton(horse.getName());
                        horseButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, horseButton.getPreferredSize().height));
                        horseNameList.add(horseButton);
                        horseButtons.add(horseButton);

                        horseButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                currentSelectedHorse = new Horse[]{horse};
                                horsePicture.removeAll();
                                JLabel horseArt1 = new JLabel("            .''", SwingConstants.LEFT);
                                horseArt1.setFont(monoFont);
                                JLabel horseArt2 = new JLabel("  ._.-." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] + ".' (" + currentSelectedHorse[0].getSymbol() + "\\", SwingConstants.LEFT);
                                horseArt2.setFont(monoFont);
                                JLabel horseArt3 = new JLabel(" //( " + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + currentSelectedHorse[0].getBody()[0] + " ( `'", SwingConstants.LEFT);
                                horseArt3.setFont(monoFont);
                                JLabel horseArt4 = new JLabel("'/ )\\ )." + currentSelectedHorse[0].getBody()[1] + currentSelectedHorse[0].getBody()[1] +". ) ", SwingConstants.LEFT);
                                horseArt4.setFont(monoFont);
                                JLabel horseArt5 = new JLabel("' <' `\\ ._/'\\", SwingConstants.LEFT);
                                horseArt5.setFont(monoFont);
                                JLabel horseArt6 = new JLabel("   `   \\     \\", SwingConstants.LEFT);
                                horseArt6.setFont(monoFont);
                                horsePicture.add(new JLabel(currentSelectedHorse[0].getName()));
                                horsePicture.add(horseArt1);
                                horsePicture.add(horseArt2);
                                horsePicture.add(horseArt3);
                                horsePicture.add(horseArt4);
                                horsePicture.add(horseArt5);
                                horsePicture.add(horseArt6);
                                horsePictureHolder.revalidate();
                                horsePictureHolder.repaint();
                            }
                        });
                    }

                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Horse horse: horses) {
                    horse.goBackToStart();
                }

                Race race = new Race(raceLength, horses, currentTerrain, textArea);
                race.startRace();
                races.add(race);

                new Thread(() -> {
                    if (!bets.isEmpty()) {
                        for (Horse horse : race.winningHorses) {
                            if (bets.containsKey(horse)) {
                                money += (int) horse.calculateProfit(bets.get(horse));
                            }
                        }
                    }

                    currentSelection.removeAll();
                    currentSelection.add(new JLabel(username), BorderLayout.WEST);
                    currentSelection.add(new JLabel(" Wallet: £" + money), BorderLayout.EAST);
                    betPanel.removeAll();
                    betPanel.setLayout(new GridLayout(0, 2));
                    betPanel.add(new JLabel("My Bets:"));
                    betPanel.add(new JLabel(""));
                    betPanel.add(new JLabel("NO BETS ADDED YET"));
                    bets = new HashMap<>();

                    tableFrame.removeAll();
                    Object[][] data = new Object[horses.size()][5];
                    for (int i = 0; i < horses.size(); i++) {
                        addValue(data, i, 0, horses.get(i).getName());
                        addValue(data, i, 1, (long) (horses.get(i).getConfidence()*100) + "%");
                        addValue(data, i, 2, horses.get(i).getOddsString());
                        addValue(data, i, 3, (int) horses.get(i).calculateProfit(100));
                        addValue(data, i, 4, "Bet");
                    }


                    String[] columnNames = {"Name", "Confidence", "Betting Odds", "Potential Profit per £100", ""};

                    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return column == 4;
                        }
                    };

                    table.setModel(model);
                    table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
                    table.revalidate();

                    mainFrame.revalidate();
                    mainFrame.repaint();
                }).start();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(pastelBLUE);
        currentSelection.setBackground(pastelBLUE);
        betPanel.setBackground(pastelBLUE);
        horsePanel.setBackground(pastelBLUE);
        horsePicture.setBackground(pastelBLUE);
        horseToolList.setBackground(pastelBLUE);
        horseCustomisationPanel.setBackground(pastelBLUE);
        horseNameList.setBackground(pastelBLUE);
        horsePictureHolder.setBackground(pastelBLUE);

        raceView.add(textArea);
        centerPanel.add(scrollPane, BorderLayout.NORTH);
        centerPanel.add(raceView, BorderLayout.CENTER);
        startButton.setBackground(pastelPINK);
        clearButton.setBackground(pastelPINK);
        nameButton.setBackground(pastelPINK);
        eyeButton.setBackground(pastelPINK);
        bodyButton.setBackground(pastelPINK);
        removeButton.setBackground(pastelPINK);
        defaultButton.setBackground(pastelPINK);
        addHorseButton.setBackground(pastelPINK);
        designButton.setBackground(pastelPINK);

        environmentSettings.setBackground(pastelBLUE);


        racing.add(centerPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(255,179,186));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    public static void setUsername() {
        JFrame usernameFrame = new JFrame("Username Update");
        username = JOptionPane.showInputDialog(usernameFrame, "Enter your new username:");
    }

    public static void welcome() {
        JPanel welcomePictureHolder = new JPanel(new BorderLayout());

        JPanel welcomePicture = new JPanel(new BorderLayout());
        welcomePicture.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Font monoFont2 = new Font("Courier New", Font.PLAIN, 15);
        welcomePicture.setLayout(new GridLayout(15, 1));

        JLabel label1 = new JLabel(" _____                                                   _____");
        JLabel label2 = new JLabel("( ___ )                                                 ( ___ )");
        JLabel label3 = new JLabel(" |   |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|   |");
        JLabel label4 = new JLabel(" |   |  _   _                       ____                 |   |");
        JLabel label5 = new JLabel(" |   | | | | | ___  _ __ ___  ___  |  _ \\ __ _  ___ ___  |   |");
        JLabel label6 = new JLabel(" |   | | |_| |/ _ \\| '__/ __|/ _ \\ | |_) / _` |/ __/ _ \\ |   |");
        JLabel label7 = new JLabel(" |   | |  _  | (_) | |  \\__ \\  __/ |  _ < (_| | (_|  __/ |   |");
        JLabel label8 = new JLabel(" |   | |_|_|_|\\___/|_|  |___/\\___| |_| \\_\\__,_|\\___\\___| |   |");
        JLabel label9 = new JLabel(" |   | / ___|(_)_ __ ___  _   _| | __ _| |_ ___  _ __    |   |");
        JLabel label10 = new JLabel(" |   | \\___ \\| | '_ ` _ \\| | | | |/ _` | __/ _ \\| '__|   |   |");
        JLabel label11 = new JLabel(" |   |  ___) | | | | | | | |_| | | (_| | || (_) | |      |   |");
        JLabel label12 = new JLabel(" |   | |____/|_|_| |_| |_|\\__,_|_|\\__,_|\\__\\___/|_|      |   |");
        JLabel label13 = new JLabel(" |___|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|___|");
        JLabel label14 = new JLabel("(_____)                                                 (_____)");

        label1.setFont(monoFont2);
        label2.setFont(monoFont2);
        label3.setFont(monoFont2);
        label4.setFont(monoFont2);
        label5.setFont(monoFont2);
        label6.setFont(monoFont2);
        label7.setFont(monoFont2);
        label8.setFont(monoFont2);
        label9.setFont(monoFont2);
        label10.setFont(monoFont2);
        label11.setFont(monoFont2);
        label12.setFont(monoFont2);
        label13.setFont(monoFont2);
        label14.setFont(monoFont2);

        welcomePicture.add(label1);
        welcomePicture.add(label2);
        welcomePicture.add(label3);
        welcomePicture.add(label4);
        welcomePicture.add(label5);
        welcomePicture.add(label6);
        welcomePicture.add(label7);
        welcomePicture.add(label8);
        welcomePicture.add(label9);
        welcomePicture.add(label10);
        welcomePicture.add(label11);
        welcomePicture.add(label12);
        welcomePicture.add(label13);
        welcomePicture.add(label14);
        
        welcomePictureHolder.add(welcomePicture, BorderLayout.NORTH);

        JOptionPane.showMessageDialog(null, welcomePictureHolder, "Welcome", JOptionPane.PLAIN_MESSAGE);
    }

    public static void addValue(Object[][] array, int row, int column, Object value) {
        if (row >= 0 && row < array.length && column >= 0 && column < array[0].length) {
            array[row][column] = value;
        } else {
            System.out.println("Invalid row or column index.");
        }
    }

    public static Object getValue(Object[][] array, int row, int column) {
        if (row >= 0 && row < array.length && column >= 0 && column < array[0].length) {
            return array[row][column];
        } else {
            System.out.println("Invalid row or column index.");
            return null;
        }
    }

    public static List<List<Object>> readData(String FILENAME) {
        List<List<Object>> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                List<Object> row = new ArrayList<>();
                for (String value : values) {
                    row.add(value);
                }
                dataList.add(row);
            }
            System.out.println("Data read from file.");
        } catch (IOException e) {
            System.out.println("No file found.");
        }
        return dataList;
    }
}