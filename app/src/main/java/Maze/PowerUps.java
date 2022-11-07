package Maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PowerUps extends Maze {

    public PowerUps(int[][] map) {
        super(map);
    }

    static ArrayList <JLabel> speedItem = new ArrayList<JLabel>();
    static ArrayList <JLabel> teleportItem = new ArrayList<JLabel>();

    private boolean checkAnyPower(int x, int y) {
        return false;
    }

    public boolean isSpeedUp(int x, int y) {

        for (JLabel i : speedItem) {
            double labelX = (i.getBounds().getX()) / 40;
            double labelY = i.getBounds().getY() / 40;

            if (x == labelX && y == labelY) {
                i.setVisible(false);
                speedItem.remove(i);
                map[y][x] = 1;
                return true;
            }
        }
        return false;
    }

    public boolean isTeleport(int x, int y) {

        for (JLabel i : teleportItem) {
            double labelX = (i.getBounds().getX()) / 40;
            double labelY = i.getBounds().getY() / 40;

            if (x == labelX && y == labelY) {
                i.setVisible(false);
                teleportItem.remove(i);
                map[y][x] = 1;
                return true;
            }
        }
        return false;
    }

    int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public void clearAll() {
        speedItem.clear();
        teleportItem.clear();
    }


    public void generate(int mapOption) {

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (map[j][i] == 1) {
                    if (random(1,200) == 1){

                        ImageIcon image = new ImageIcon(new ImageIcon("../src/main/resources/interface/inventory/1.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                        JLabel imageLabel = new JLabel(image);
                        imageLabel.setBounds(i * 40, j * 40, 40, 40);
                        imageLabel.setVisible(true);
                        speedItem.add(imageLabel);
                     /*else if (type == 2) { // teleport
                        ImageIcon image = new ImageIcon(new ImageIcon("../src/main/resources/interface/inventory/2.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
                        JLabel imageLabel = new JLabel(image);
                        imageLabel.setBounds(i * 40, j * 40, 40, 40);
                        imageLabel.setVisible(true);
                        teleportItem.add(imageLabel);
                    }*/

                    }
                }
            }
        }

    }

    public ArrayList<JLabel> getSpeedItems() {
        return speedItem;
    }

    public ArrayList<JLabel> getTeleportItems() {
        return teleportItem;
    }



}
