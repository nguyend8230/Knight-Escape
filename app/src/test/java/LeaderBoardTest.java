import Game.Game;
import Game.HighScore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Game.LeaderBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeaderBoardTest {
    LeaderBoard leaderBoard = new LeaderBoard();

    int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    //checks if new highscores are added to the list
    @Test
    void addNewHighScoreTest() {
        leaderBoard.resetHighScores();
        leaderBoard.writeHighScore("src/test/resources/high score test");

        leaderBoard.saveNewHighScore(50, 50, "Desert");
        assertEquals(50 ,leaderBoard.highScoresList[0].time);

        //test new highscore with no shift
        leaderBoard.saveNewHighScore(50, 60, "Desert");
        assertEquals(50 ,leaderBoard.highScoresList[0].time);
        assertEquals(60 ,leaderBoard.highScoresList[1].time);

        //test new highscore with shift but the highscore stays
        leaderBoard.saveNewHighScore(50, 55, "Desert");
        assertEquals(50 ,leaderBoard.highScoresList[0].time);
        assertEquals(55 ,leaderBoard.highScoresList[1].time);
        assertEquals(60 ,leaderBoard.highScoresList[2].time);

        //test new highscore with shift but the highscore gets removed
        for(int i = 0; i < 10; i++) {
            leaderBoard.saveNewHighScore(50, i, "Desert");
        }
        for(int i = 0; i < 10; i++) {
            assertEquals(i ,leaderBoard.highScoresList[i].time);
        }

        //test no new highscore
        leaderBoard.saveNewHighScore(50, 11, "Desert");

        for(int i = 0; i < 10; i++) {
            assertEquals(i ,leaderBoard.highScoresList[i].time);
        }
    }

    //checks if highscores are in the right order
    @Test
    void saveNewHighScoreTest() {
        leaderBoard.resetHighScores();
        for(int i = 0; i < 100; i++) {
            leaderBoard.saveNewHighScore(50, random(0, 900), "Desert");
            for(int j = 0; j < leaderBoard.highScoresList.length - 1; j++) {
                assertTrue(leaderBoard.highScoresList[j].time <= leaderBoard.highScoresList[j+1].time);
            }
        }
    }

    //checks if the highscores are written into the file correctly
    @Test
    void writeHighScoreTest() {
        int point;
        double time;
        String map;

        for(int i = 0; i < leaderBoard.highScoresList.length; i++) {
            leaderBoard.highScoresList[i] = new HighScore(i*5, i*10, "Desert");
        }

        leaderBoard.writeHighScore("src/test/resources/high score test");

        File file = new File(new File("src/test/resources/high score test").getAbsolutePath());
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 10; i++) {
            point = Integer.parseInt(scan.nextLine());
            //System.out.println(point);
            assertEquals(i*5, point);
            time = Double.parseDouble(scan.nextLine());
            //System.out.println(time);
            assertEquals(i*10, time);
            map = scan.nextLine();
            //System.out.println(map);
            assertEquals("Desert", map);
        }

        leaderBoard.resetHighScores();

        leaderBoard.writeHighScore("src/test/resources/high score test");
    }

    //checks if the highscores read from files are correct
    @Test
    void readHighScoreTest() {
        for(int i = 0; i < leaderBoard.highScoresList.length; i++) {
            leaderBoard.highScoresList[i] = new HighScore(i*5, i*10, "Desert");
        }

        leaderBoard.writeHighScore("src/test/resources/high score test");
        leaderBoard.resetHighScores();

        leaderBoard.readHighScore("src/test/resources/high score test");

        for(int i = 0; i < 10; i++) {
            assertEquals(i*5, leaderBoard.highScoresList[i].point);
            assertEquals(i*10, leaderBoard.highScoresList[i].time);
            assertEquals("Desert", leaderBoard.highScoresList[i].map);
        }
    }
}
