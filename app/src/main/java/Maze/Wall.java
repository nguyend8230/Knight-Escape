package Maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Wall extends Maze {
    public Wall(int[][] map) {
        super(map);
    }

    ArrayList<JLabel> generate(int mapOption) {
        ImageIcon image = new ImageIcon(new ImageIcon("../src/main/resources/Images/" + mapList[mapOption]+ "/"+ mapList[mapOption] + "_wall.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

        ArrayList<JLabel> wallTiles = new ArrayList<JLabel>();
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                if(map[j][i] == 0) {
                    JLabel imageLabel = new JLabel(image);
                    imageLabel.setBounds(i * 40, j * 40, 40, 40);
                    imageLabel.setVisible(true);
                    wallTiles.add(imageLabel);
                }
            }
        }

        return wallTiles;
    }
}
