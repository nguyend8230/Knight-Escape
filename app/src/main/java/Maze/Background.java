package Maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Background extends Maze {
    public Background(int[][] map) {
        super(map);
    }
    ArrayList<JLabel> generate(int mapOption) {
        ImageIcon image = new ImageIcon(new ImageIcon("../src/main/resources/Images/" + mapList[mapOption]+ "/"+ mapList[mapOption] + "_floor.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

        ArrayList<JLabel> backgroundTiles = new ArrayList<JLabel>();
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                JLabel imageLabel = new JLabel(image);
                imageLabel.setBounds(j * 40, i * 40, 40, 40);
                imageLabel.setVisible(true);
                backgroundTiles.add(imageLabel);

            }
        }

        return backgroundTiles;
    }
}
