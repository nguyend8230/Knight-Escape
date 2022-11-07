package Maze;

import Game.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Punishment extends Maze {
    public static ArrayList <JLabel> punishmentList = new ArrayList<JLabel>();

    public Punishment(int[][] map) {
        super(map);
    }


    public boolean isPunishment(int x, int y) {

        for (JLabel i : punishmentList) {
            double labelX = (i.getBounds().getX()) / 40;
            double labelY = i.getBounds().getY() / 40;
            

            if (x == labelX && y == labelY) {
                i.setVisible(false);
                punishmentList.remove(i);
                map[y][x] = 1;
                return true;
            }
        }
        return false;
    }

    public ArrayList <JLabel> generate() {

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (map[i][j] == 6) {
                        String imageName = "../src/main/resources/Images/shadow.png";
                        ImageIcon image = new ImageIcon(new ImageIcon(imageName).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                        JLabel imageLabel = new JLabel(image);
                        imageLabel.setBounds(j * 40, i * 40, 40, 40);
                        imageLabel.setVisible(true);
                        punishmentList.add(imageLabel);

                }
            }
        }
        return punishmentList;
    }

}
