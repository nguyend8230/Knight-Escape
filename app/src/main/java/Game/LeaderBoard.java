package Game;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;

public class LeaderBoard extends JFrame {
    JPanel panel = new JPanel();
    JTextArea highScores = new JTextArea("HIGH SCORES:");
    public HighScore[] highScoresList = new HighScore[10];

    void setFrame(Game game) {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        this.add(panel);
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.setLayout(null);

        panel.add(highScores);
        highScores.setFont(game.gameScreen.setGameFont(30));
        highScores.setEditable(false);
        highScores.setBounds(0, 0, 1000, 1000);
        this.getContentPane().add(BorderLayout.EAST, highScores);
        this.pack();
        highScores.setVisible(true);
    }

    public void resetHighScores() {
        for(int i = 0; i < highScoresList.length; i++){
            highScoresList[i] = new HighScore(1000, 1000, "--");
        }
    }

    public void saveNewHighScore (int points, double time, String map) {

        HighScore[] tempList = new HighScore[highScoresList.length + 1];
        HighScore temp;

        for(int i = 0; i < highScoresList.length; i++) {
            tempList[i] = highScoresList[i];
        }
        tempList[highScoresList.length] = new HighScore(points, time, map);

        for(int end = tempList.length; end > 0; end--) {
            for(int i = 0; i < end - 1; i++) {
                if(tempList[i].time > tempList[i+1].time) {
                    temp = tempList[i];
                    tempList[i] = tempList[i+1];
                    tempList[i+1] = temp;
                }
            }
        }

        for(int i = 0; i < highScoresList.length; i++) {
            highScoresList[i] = tempList[i];
        }
    }

    public void writeHighScore(String fileName) {
        //System.out.println(time);
        //saveNewHighScore(points, time, map);

        try (FileWriter file = new FileWriter(fileName)) {
            for(int i = 0; i < highScoresList.length; i++) {
                BigDecimal dc = new BigDecimal(highScoresList[i].time);
                dc = dc .round(new MathContext(4));
                double rounded = dc.doubleValue();
                file.write(highScoresList[i].point + "\n" + rounded + "\n" + highScoresList[i].map + "\n");
            }
        } catch(Exception e){
            System.out.println(e);

        }
    }

    public void readHighScore(String fileName) {
        int i = 0;
        File file = new File(new File(fileName).getAbsolutePath());
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(scan.hasNextLine()) {
            highScoresList[i].point = Integer.parseInt(scan.nextLine());
            highScoresList[i].time = Double.parseDouble(scan.nextLine());
            highScoresList[i].map = scan.nextLine();
            i++;
        }
    }

    void setText() {
        String text = "HIGH SCORES: \n";
        File file = new File("../src/main/resources/high score");
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(scan.hasNextLine()) {
            text = text.concat("Score: " + scan.nextLine() + "   Time: " + scan.nextLine() + "   Map: " + scan.nextLine() + "\n");
        }
        text = text.replaceAll("1000.0", "--");
        text = text.replaceAll("1000", "--");
        highScores.setText(text);
    }

}
