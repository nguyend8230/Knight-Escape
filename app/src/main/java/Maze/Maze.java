package Maze;

import Game.GameScreen;

import javax.swing.*;
import java.util.ArrayList;

public class Maze {
    //int mapOption;
    public String mapList[] = {"Desert", "Jungle", "Ice"};

    protected int[][] map;

    protected int length = 20;
    protected int width = 20;

    ArrayList<JLabel> wallsTiles;
    ArrayList<JLabel> backgroundTiles;
    ArrayList<JLabel> punishmentObjects;
    ArrayList<JLabel> powerObjects;

    public Maze(int[][] map) {
        this.map = map;
    }

    public void createMaze(GameScreen screen, int mapOption) {

        Wall wallGenerator = new Wall(map);
        Background backgroundGenerator = new Background(map);
        Punishment punishment = new Punishment(map);
        PowerUps power = new PowerUps(map);
        power.clearAll();
        power.generate(mapOption);
        // power up
        powerObjects = power.getSpeedItems();
        for (JLabel i : powerObjects) {
            screen.setLabel(i);
        }
        powerObjects = power.getTeleportItems();
        for (JLabel i : powerObjects) {
            screen.setLabel(i);
        }

        // punishment
        punishmentObjects = punishment.generate();
        for (JLabel i : punishmentObjects) {
            screen.setLabel(i);
        }

        // get walls
        wallsTiles = wallGenerator.generate(mapOption);
        for (JLabel i : wallsTiles) {
            screen.setLabel(i);
        }

        // get background
        backgroundTiles = backgroundGenerator.generate(mapOption);
        for (JLabel i : backgroundTiles) {
            screen.setLabel(i);
        }
    }

    public void clearAllPunishments() {
        if(punishmentObjects != null) {
            for(JLabel i : punishmentObjects) {
                i.setVisible(false);
            }
            punishmentObjects.clear();
        }
    }

    public int[][] getMap() {
        return map;
    }
}
