package Game;

import Maze.Maze;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Game {
    public static Game game = new Game();

    static int mapOption = 0;
    public static int smallRewardNum = 10;
    public static boolean gameState = false;
    static boolean firstGame = true;

    static double time = 0;
    static long currentTime = System.currentTimeMillis();

    static DecimalFormat df = new DecimalFormat();
    static Character character = new Character(0, 0);
    static Enemy enemy = new Enemy(0, 0);

    static JFrame frame = new JFrame();
    static LeaderBoard leaderBoard = new LeaderBoard();

    static GameScreen gameScreen = new GameScreen(game);

    static JLabel scoreBoard = new JLabel("Points: " + 0);
    static JLabel timeBoard = new JLabel("Time: " + 0);

    static Timer enemyMoveTimer = new Timer();
    static Timer gameTime = new Timer();
    static TimerTask startTimerTask;
    static TimerTask endTimerTask;

    public static ArrayList <Reward> rewardList = new ArrayList<>();

    static Settings settings = new Settings();
    static String mapList[] = {"Desert", "Jungle", "Ice"};


    static JLabel playButton = new JLabel("Play", SwingConstants.CENTER);
    static JLabel highScoreButton = new JLabel("Leaderboard", SwingConstants.CENTER);
    static JLabel settingsButton = new JLabel("Map", SwingConstants.CENTER);



    //wall = 0, open spot = 1, rewards = 2, character = 3, enemy = 4, exit = 5, trap = 6, power up = 7
    public static int[][] map = new int[20][20];

    static int[][] desertMap =     {{0,1,1,1,0,1,1,0,0,0,1,0,0,0,1,0,0,0,0,5},
                                    {0,4,0,1,1,1,0,0,1,1,1,1,1,0,1,1,1,1,1,1},
                                    {1,1,0,1,0,1,0,1,1,0,0,0,1,1,1,0,0,0,0,6},
                                    {1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,0,1,1,0,1},
                                    {0,1,0,1,1,0,1,1,0,0,1,0,0,0,1,0,0,1,0,1},
                                    {1,0,0,0,1,1,6,1,1,1,1,1,1,1,1,6,0,1,0,1},
                                    {1,1,1,0,0,1,1,1,0,0,1,0,1,0,0,1,1,1,1,1},
                                    {1,0,1,1,0,0,1,1,1,0,1,0,1,0,0,1,0,0,0,0},
                                    {6,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1},
                                    {1,1,0,0,0,0,1,0,0,0,1,0,0,0,1,1,1,1,0,1},
                                    {0,1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,0,0,0,1},
                                    {1,1,0,1,1,0,1,1,1,0,0,0,1,1,1,1,1,1,0,1},
                                    {1,0,0,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,0,1},
                                    {1,1,0,1,0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0},
                                    {1,1,1,1,0,1,1,0,1,0,6,0,1,1,1,0,1,1,1,1},
                                    {1,1,0,1,0,1,1,0,1,1,1,1,1,1,1,1,1,0,0,0},
                                    {0,0,0,1,0,1,1,0,1,0,1,0,0,1,0,1,1,1,1,0},
                                    {0,1,1,1,1,1,0,0,1,1,1,1,0,1,0,1,0,0,1,0},
                                    {0,1,0,0,0,1,0,1,1,0,0,1,0,1,0,1,0,0,1,0},
                                    {0,3,1,1,1,1,0,1,1,1,0,1,0,1,1,1,1,1,1,0}};

    static int[][] jungleMap =     {{1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,0,1,5},
                                    {1,1,0,1,0,1,0,0,0,1,0,1,1,0,1,0,1,1,1,1},
                                    {1,0,1,1,1,1,1,1,1,1,1,0,1,1,0,1,0,1,0,0},
                                    {1,0,1,0,1,1,0,0,1,6,1,1,0,0,1,1,1,1,0,1},
                                    {1,0,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1},
                                    {1,1,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,1,0,1},
                                    {1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,0,1,0,1},
                                    {1,0,1,0,1,0,1,1,0,1,1,0,1,1,0,1,0,1,1,1},
                                    {1,1,1,1,0,1,1,0,1,1,1,1,0,1,1,1,1,1,0,1},
                                    {0,0,1,0,1,0,1,1,1,0,0,1,6,1,0,1,0,0,1,1},
                                    {1,1,0,1,1,0,1,1,1,0,0,1,1,1,1,0,1,1,0,1},
                                    {1,0,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,0,1},
                                    {1,1,1,0,1,0,1,1,0,1,1,0,1,1,0,1,0,1,0,1},
                                    {0,0,0,0,1,1,0,1,1,1,1,1,1,0,1,1,0,1,1,0},
                                    {1,1,1,1,0,1,1,0,1,0,0,1,0,1,1,0,1,6,1,0},
                                    {1,0,1,1,1,0,1,1,6,1,1,1,1,1,0,1,1,1,1,1},
                                    {1,1,1,0,1,1,0,0,1,1,1,1,0,0,1,1,0,0,1,1},
                                    {1,0,1,1,0,1,1,1,1,0,1,1,0,1,1,1,0,4,0,1},
                                    {1,0,0,1,0,0,0,1,0,1,0,1,1,1,0,0,1,1,0,1,},
                                    {1,3,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,1}};

    static int[][] iceMap =        {{5,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,4},
                                    {1,1,1,0,0,0,1,0,0,0,0,0,0,0,1,0,1,1,0,1},
                                    {1,0,0,0,1,1,1,0,1,1,1,1,1,0,1,0,0,0,0,1},
                                    {1,0,1,1,1,0,0,0,1,6,1,0,1,1,1,0,1,1,0,1},
                                    {1,1,1,0,0,0,1,0,1,1,0,1,0,1,1,1,0,1,1,1},
                                    {1,0,0,0,1,1,1,1,1,0,1,1,0,0,1,0,1,0,1,1},
                                    {0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0},
                                    {0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1,0},
                                    {0,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,0},
                                    {0,1,0,1,1,1,1,1,0,0,1,0,1,1,1,1,1,0,1,0},
                                    {0,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,0},
                                    {0,1,0,1,1,1,1,1,0,1,0,0,1,1,1,1,1,0,1,0},
                                    {0,1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,1,0},
                                    {0,1,1,0,1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,0},
                                    {1,0,1,1,0,1,1,1,1,0,6,1,1,0,1,1,0,1,1,0},
                                    {1,0,0,1,1,0,0,1,1,0,1,0,1,0,0,0,0,0,1,0},
                                    {1,1,0,6,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0},
                                    {1,1,1,1,1,0,1,0,1,0,0,0,1,6,1,0,1,1,0,1},
                                    {1,0,0,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,0,1},
                                    {1,3,1,0,1,1,0,1,1,1,1,1,0,1,1,1,0,1,1,4}};



    static Maze maze = new Maze(map);

    static Thread animationThread = new Thread(character);
    static Thread animationThreadBackup = new Thread(character);

    boolean getGameState() {
        return gameState;
    }
    public static void main (String[] args) {

        animationThread.start();
        animationThreadBackup.start();

        gameScreen.spawnAll(game);
        gameScreen.setFrame();

        leaderBoard.resetHighScores();
        leaderBoard.readHighScore("../src/main/resources/high score");

        enemyMoveTimer.schedule( new TimerTask() {
            public void run() {
                if(gameState == true) {
                    enemy.move(map, character.x, character.y);
                    if(enemy.x == character.x && enemy.y == character.y){
                        game.gameState = false;
                    }
                }
            }
        }, 0, 600);
        gameTime.schedule( new TimerTask() {
            public void run() {
                df.setMaximumFractionDigits(2);
                if(gameState == true) {
                    time+= (double)(System.currentTimeMillis() - currentTime)/1000;
                    currentTime = System.currentTimeMillis();
                    timeBoard.setText("Time: " + df.format(time));
                }
            }
        }, 0, 10);

        try {
            animationThread.join();
            animationThreadBackup.join();
        } catch (InterruptedException e) {
            System.err.println("an error occurred");
        }

    }
}


