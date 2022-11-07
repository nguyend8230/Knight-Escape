package Game;

import Inventory.InventoryManager;
import Inventory.InventoryRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends JPanel {
    Game game;
    public GameScreen(Game game) {
        this.game = game;
    }

    int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    void spawnCharacter() {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                if(game.map[i][j] == 3) {
                    game.character.x = j;
                    game.character.y = i;
                    game.character.spawn(this);
                }
            }
        }
        game.character.setControl(game, game.map);
    }

    public void spawnEnemy(int[][] map) {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                if(map[i][j] == 4) {
                    game.enemy.x = j;
                    game.enemy.y = i;
                    game.enemy.spawn(this);
                }
            }
        }
    }

    //Add the game screen Panel to the main Frame
    void setGameScreen() {
        game.frame.add(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setLayout(null);
    }

    /*public void createReward(int x, int y, int point, String imageName) {
        game.rewardList.add(new Reward(x, y, point, imageName));
    }

    public void createAllSmallReward(int[][] map) {
        for(int i = 0; i < game.smallRewardNum; ){
            int rewardXPos = random(0, 19);
            int rewardYPos = random(0, 19);
            if(map[rewardYPos][rewardXPos] == 1) {
                createReward(rewardXPos, rewardYPos, 5, "../src/main/resources/Images/gold_pile_5.png");
                map[rewardYPos][rewardXPos] = 2;
                i++;
            }
        }
    }
    public void createAllReward(int[][] map) {
        createAllSmallReward(map);
        game.rewardList.add(new Reward(20, 20, 10, "../src/main/resources/Images/gold_pile_25.png"));
    }*/

    void spawnReward(Reward reward) {
        reward.spawn(this);
    }

    //Show the main frame
    void setFrame() {
        game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game.frame.setVisible(true);
    }

    public void setLabel(JLabel label) {
        this.add(label);
    }

    void spawnBigReward() {
        int bigRewardSpawnTime = random(10000, 20000);
        game.startTimerTask = new TimerTask() {
            public void run() {
                if(game.gameState == true) {
                    int x = random(0, 19);
                    int y = random(0, 19);
                    while (game.map[y][x] != 1) {
                        x = random(0, 19);
                        y = random(0, 19);
                    }
                    game.rewardList.get(game.smallRewardNum).x = x;
                    game.rewardList.get(game.smallRewardNum).y = y;
                    game.rewardList.get(game.smallRewardNum).setImage();
                    cancel();
                }
            }
        };
        java.util.Timer startTimer = new java.util.Timer();
        startTimer.schedule(game.startTimerTask, bigRewardSpawnTime);

        game.endTimerTask = new TimerTask() {
            public void run() {
                if(game.gameState == true) {
                    game.rewardList.get(game.smallRewardNum).despawn();
                }
                cancel();
            }
        };
        java.util.Timer endTimer = new Timer();
        endTimer.schedule(game.endTimerTask, bigRewardSpawnTime + 10000);
    }

    void spawnSmallRewards() {
        for(int i = 0; i < game.smallRewardNum; i++){
            this.spawnReward(game.rewardList.get(i));
        }
    }

    void spawnExit() {
        ImageIcon image = new ImageIcon(new ImageIcon("../src/main/resources/Images/exit_abyss_new.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(image);
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                if(game.map[j][i] == 5) {
                    imageLabel.setBounds(i * 40, j * 40, 40, 40);
                    game.map[j][i] = 5;
                }
            }
        }
        this.add(imageLabel);
        imageLabel.setVisible(true);
    }

    void setScoreBoard() {
        game.scoreBoard.setFont(setGameFont(30));
        game.scoreBoard.setBounds(900, 650, 400, 90);
        this.add(game.scoreBoard);
        game.scoreBoard.setVisible(true);

        game.scoreBoard.setText("Points: 0");
    }

    void setTimeBoard() {
        game.timeBoard.setFont(setGameFont(30));
        game.timeBoard.setBounds(900, 700, 400, 90);
        this.add(game.timeBoard);
        game.timeBoard.setVisible(true);
    }

    Font setGameFont(float f) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("../src/main/resources/PixelMplus12-Bold.ttf")).deriveFont(f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("../src/main/resources/PixelMplus12-Bold.ttf")));
            return font;
        } catch (IOException |FontFormatException e) {
            System.out.println("font creating error");
            return null;
        }
    }

    void copyMap(int[][] from, int[][] to) {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                to[i][j] = from[i][j];
            }
        }
    }

    //Change the main map based on the game option
    void setMap() {
        if(game.mapOption == 0) {
            copyMap(game.desertMap, game.map);
        }
        else if(game.mapOption == 1) {
            copyMap(game.jungleMap, game.map);
        }
        else if(game.mapOption == 2) {
            copyMap(game.iceMap, game.map);
        }
    }

    void setHighScoreButton() {
        game.highScoreButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        game.highScoreButton.setFont(setGameFont(30));
        game.highScoreButton.setBounds(900, 150, 400, 90);
        game.highScoreButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        game.gameScreen.add(game.highScoreButton);
        game.highScoreButton.setVisible(true);
        game.highScoreButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(!game.gameState) {
                    game.leaderBoard.readHighScore("../src/main/resources/high score");
                    game.leaderBoard.setText();
                    game.leaderBoard.setFrame(game);
                }
            }
        });
    }

    void setPlayButton() {
        /*

        Play button parameters setup

        */
        game.playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        // https://stackoverflow.com/questions/11686938/how-to-change-the-mouse-pointer-to-finger-pointer-in-swing
        game.playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        game.playButton.setFont(setGameFont(30));
        game.playButton.setBounds(900, 50, 400, 90);
        game.playButton.setVisible(true);

        game.gameScreen.add(game.playButton);

        game.playButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(!game.gameState) {

                    if(game.startTimerTask != null && game.endTimerTask != null) {
                        game.startTimerTask.cancel();
                        game.endTimerTask.cancel();
                    }
                    game.gameScreen.spawnBigReward();
                    game.currentTime = System.currentTimeMillis();
                    game.gameState = !game.gameState;
                    if(!game.firstGame || game.mapOption != 0) {
                        game.gameScreen.removeAll();
                        game.gameScreen.revalidate();
                        game.gameScreen.repaint();
                        spawnAll(game);


                        System.out.println("THIS");
                    }
                    game.firstGame = false;
                }
            }
        });
    }

    void renderMaze() {
        game.maze.createMaze(game.gameScreen, game.mapOption);
    }

    void renderInventory() {
        InventoryRender inventory = new InventoryRender();
        InventoryManager inventoryManager = new InventoryManager();

        for (JLabel i : inventoryManager.getNumbers()) {
            this.add(i);
        }

        inventoryManager.resetInventory();
        for (JLabel i : inventoryManager.getLabels()) {
            this.add(i);
        }

        for (JLabel i : inventory.getCells()) {
            this.add(i);
        }
        this.add(inventory.getBackgroundLabel());



    }


    void spawnAll(Game game) {
        game.time = 0;
        game.rewardList.clear();
        game.maze.clearAllPunishments();
        game.settings.showSettings(game);
        game.gameScreen.setMap();
        new Reward(20, 20, 5, "../src/main/resources/Images/gold_pile_5.png").createAllRewardRandom(game.map, game.smallRewardNum, game.rewardList, 5, "../src/main/resources/Images/gold_pile_5.png");
        new Reward(20, 20, 10, "../src/main/resources/Images/gold_pile_25.png").addReward(game.rewardList);
        spawnEnemy(game.map);
        spawnCharacter();
        spawnExit();
        spawnSmallRewards();
        game.rewardList.get(game.smallRewardNum).spawn(game.gameScreen);
        game.rewardList.get(game.smallRewardNum).imageLabel.setVisible(false);
        renderInventory();
        renderMaze();
        setGameScreen();
        setScoreBoard();
        setTimeBoard();
        setPlayButton();
        setHighScoreButton();
    }



}
