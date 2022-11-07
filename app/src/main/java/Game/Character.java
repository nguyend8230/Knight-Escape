package Game;

import Inventory.InventoryManager;
import Maze.PowerUps;
import Maze.Punishment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class Character implements Runnable {
    public int x;
    public int y;
    public int points = 0;
    public int rewardPicked = 0;
    int coolDown = 150;
    long lastMove = 0;
    long currentTime = System.currentTimeMillis();



    /* ---- setup character imaging ----- @ Danial
    * Use characterLeft for the right animation
    * Use characterLeft fro the left animation
    * Keep track of the active animation using isDirectionRight
    * Use setImage to swap when needed
    */

    boolean isDirectionRight = false;
    JLabel characterRight = new JLabel(new ImageIcon(new ImageIcon("../src/main/resources/Images/Character/right.png")
                            .getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT)));

    JLabel characterLeft = new JLabel(new ImageIcon(new ImageIcon("../src/main/resources/Images/Character/left.png")
                            .getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT)));


    /* ---- character points animation ----- @ Danial
    * xAnimation - label for writing x animation to the screen
    * use xAnimationActivated to check if animation should be written to screen
    * */
    private volatile boolean threadRun = true;

    // https://stackoverflow.com/questions/14107252/java-multithreading-volatile-boolean-not-working
    protected volatile boolean pointsAnimationActivation, trapAnimationActivation, doublePointsAnimationActivation, powerAnimationActivation = false;

    protected JLabel trapAnimation = new JLabel("-5");
    protected JLabel pointsAnimation = new JLabel("+5");
    protected JLabel doublePointsAnimation = new JLabel("+10");
    protected JLabel powerAnimation = new JLabel(new ImageIcon(new ImageIcon("../src/main/resources/Images/speed.png")
                                        .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));


    public Character(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public synchronized void run() {
        while (threadRun) {
            if (pointsAnimationActivation) {
                pointsAnimationActivation = false;

                // setup the label parameters
                pointsAnimation.setVisible(true);
                pointsAnimation.setBounds(x* 40, (y* 40)-20, 50, 20);

                // STATE: visible
                try { // https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // STATE: disabled
                pointsAnimation.setVisible(false);
            }
            if (trapAnimationActivation) {
                trapAnimationActivation = false;

                // setup the label parameters
                trapAnimation.setVisible(true);
                trapAnimation.setBounds(x* 40, (y* 40)-20, 55, 20);

                // STATE: visible
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // STATE: disabled
                trapAnimation.setVisible(false);
            }
            if (powerAnimationActivation) {
                powerAnimationActivation = false;

                // setup the label parameters
                powerAnimation.setVisible(true);
                powerAnimation.setBounds(x* 40, (y* 40)-20, 35, 30);

                // STATE: visible
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // STATE: disabled
                powerAnimation.setVisible(false);
            }
            if (doublePointsAnimationActivation) {
                doublePointsAnimationActivation = false;

                // setup the label parameters
                doublePointsAnimation.setVisible(true);
                doublePointsAnimation.setBounds(x* 40, (y* 40)-20, 85, 30);

                // STATE: visible
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // STATE: disabled
                doublePointsAnimation.setVisible(false);
            }


        }
    }

    public void stop() {
        threadRun = false;
    }

    void animationSetup(GameScreen gameScreen) {
        // points
        pointsAnimation.setBounds(x* 40, (y* 40)-20, 55, 20);
        pointsAnimation.setVisible(false);
        pointsAnimation.setForeground(Color.WHITE);
        pointsAnimation.setFont(gameScreen.setGameFont(25));

        // double points
        doublePointsAnimation.setBounds(x* 40, (y* 40)-20, 85, 30);
        doublePointsAnimation.setVisible(false);
        doublePointsAnimation.setForeground(Color.GREEN);
        doublePointsAnimation.setFont(gameScreen.setGameFont(25));

        // trap
        trapAnimation.setBounds(x* 40, (y* 40)-20, 35, 30);
        trapAnimation.setVisible(false);
        trapAnimation.setForeground(Color.RED);
        trapAnimation.setFont(gameScreen.setGameFont(25));

        // power
        powerAnimation.setVisible(false);
        powerAnimation.setBounds(x* 40, (y* 40)-20, 32, 32);

        gameScreen.add(pointsAnimation);
        gameScreen.add(trapAnimation);
        gameScreen.add(powerAnimation);
        gameScreen.add(doublePointsAnimation);
    }


    void spawn(GameScreen gameScreen) {
        points = 0;
        rewardPicked = 0;

        characterRight.setBounds(x * 40, y* 40, 40, 40);
        characterRight.setVisible(true);
        characterLeft.setVisible(false);
        animationSetup(gameScreen);
        gameScreen.add(characterLeft);
        gameScreen.add(characterRight);
    }

    void setImage() {
        if (isDirectionRight) {
            characterRight.setBounds(x * 40, y * 40, 40, 40);
            characterRight.setVisible(true);
            characterLeft.setVisible(false);
        } else {
            characterLeft.setBounds(x * 40, y * 40, 40, 40);
            characterLeft.setVisible(true);
            characterRight.setVisible(false);
        }

    }

    public void checkExit(Game game, int[][] map) {
        if(rewardPicked == game.smallRewardNum && map[y][x] == 5) {
            game.gameState = false;
            JOptionPane.showMessageDialog(game.frame,
                    "You win, points: " + points);
        }
    }

    public void checkExit(Game game, int[][] map, LeaderBoard leaderBoard) {
        //System.out.println(rewardPicked);
        //System.out.println(game.smallRewardNum);
        if(rewardPicked == game.smallRewardNum && map[y][x] == 5) {
            leaderBoard.saveNewHighScore(points, game.time, game.mapList[game.mapOption]);
            leaderBoard.writeHighScore("../src/main/resources/high score");
            game.gameState = false;
            JOptionPane.showMessageDialog(game.frame,
                    "You win, points: " + points);
        }
    }
    public void checkEnemy(Game game) {
        if(x == game.enemy.x && y == game.enemy.y) {
            game.gameState = false;
            JOptionPane.showMessageDialog(game.frame,
                    "Game over",
                    "Game over",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void checkPunishment(int[][] map, Game game) {
        Punishment punishment = new Punishment(map);
        if ( punishment.isPunishment(x, y) )  {
            points-=5;
            game.scoreBoard.setText("Points: " + points);
            trapAnimationActivation = true;
        }
        if(points < 0) {
            game.gameState = false;
            JOptionPane.showMessageDialog(game.frame,
                    "Game over",
                    "Game over",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void checkReward(Game game) {
        for(int i =0; i < game.rewardList.size(); i++) {
            if(game.rewardList.get(i).x == x && game.rewardList.get(i).y == y) {
                game.map[game.rewardList.get(i).y][game.rewardList.get(i).x] = 1;
                points += game.rewardList.get(i).point;
                game.rewardList.get(i).despawn();
                if(game.rewardList.get(i).point == 5) {
                    rewardPicked++;
                    pointsAnimationActivation = true;
                } else {
                    doublePointsAnimationActivation = true;
                }
                game.scoreBoard.setText("Points: " + points);

                //System.out.println(pointsAnimationActivation);
            }
        }
    }

    public void move(Game game, int[][] map, int keyCode) {
        new CharacterKeyListener(game, map).move(keyCode);
    }

    void setControl(Game game, int[][] map) {
        game.gameScreen.addKeyListener(new CharacterKeyListener(game, map));
    }


    class CharacterKeyListener extends KeyAdapter {
        Game game;
        int[][] map;

        CharacterKeyListener(Game game, int[][] map) {
            this.game = game;
            this.map = map;
        }

        int movementModifier = 1;
        int lastKey;



        public void keyPressed(KeyEvent e) {
            move(e.getKeyCode());
            checkAll();
        }
        void move(int keyCode) {
            currentTime = System.currentTimeMillis();

            InventoryManager inventoryManager = new InventoryManager();
            PowerUps powerUps = new PowerUps(map);


            if ( currentTime > lastMove + coolDown && game.gameState == true) {


                if (inventoryManager.hasEmptySlot()) {
                    /*
                     * 0 = empty (default)
                     * 1 = speed up
                     * 2 = teleport
                     */
                    if (powerUps.isSpeedUp(x, y)) {
                        powerAnimationActivation = true;
                        inventoryManager.addItem(1);
                    } else if (powerUps.isTeleport(x, y)) {
                        inventoryManager.addItem(2);
                    }


                }

                /*
                 * Inventory key listener
                 *
                 */
                int powerUpType = -1;

                if (keyCode == KeyEvent.VK_1) {
                    powerUpType = inventoryManager.useItem(0);
                } else if (keyCode == KeyEvent.VK_2) {
                    powerUpType = inventoryManager.useItem(1);
                } else if (keyCode == KeyEvent.VK_3) {
                    powerUpType = inventoryManager.useItem(2);
                } else if (keyCode == KeyEvent.VK_4) {
                    powerUpType = inventoryManager.useItem(3);
                } else if (keyCode == KeyEvent.VK_5) {
                    powerUpType = inventoryManager.useItem(4);
                } else if (keyCode == KeyEvent.VK_6) {
                    powerUpType = inventoryManager.useItem(5);
                }

                /*
                 * 0 = empty (default)
                 * 1 = speed up
                 * 2 = random teleport
                 */
                int modifier = 0;
                int tmp = x;
                if (powerUpType == 1) { // speed up
                    if (isDirectionRight) {
                        for (int i = 1; i <= 3; i++) {
                            if (tmp >= 1 && tmp <= 18) {
                                if (map[y][x + i] != 0) {
                                    modifier++;
                                    tmp++;
                                } else {
                                    break;
                                }
                            }
                        }
                        x+=modifier;
                    } else {
                        for (int i = 1; i <= 3; i++) {
                            if (tmp >= 1 && tmp <= 18) {
                                if (map[y][x - i] != 0) {
                                    modifier++;
                                    tmp--;
                                } else {
                                    break;
                                }
                            }


                        }
                        x-=modifier;
                    }

                }

                /*
                 * Movement
                 *
                 */
                if ( (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) && y >= 1 && map[y - 1][x] != 0){
                    y-= movementModifier;
                    lastKey = KeyEvent.VK_UP;
                }
                else if ( (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) && y <= 18 && map[y + 1][x] != 0){
                    y+= movementModifier;
                    lastKey = KeyEvent.VK_DOWN;
                }
                else if ( (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) && x >= 1 && map[y][x - 1] != 0){
                    isDirectionRight = false;
                    x-= movementModifier;
                    lastKey = KeyEvent.VK_LEFT;
                }
                else if ((keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) && x <= 18 && map[y][x + 1] != 0){
                    isDirectionRight = true;
                    x+= movementModifier;
                    lastKey = KeyEvent.VK_RIGHT;
                }
            }
        }

        void checkAll() {
            if (currentTime > lastMove + coolDown && game.gameState == true) {
                setImage();
                checkPunishment(map, game);
                checkEnemy(game);
                checkExit(game, map, game.leaderBoard);
                checkReward(game);
                lastMove = currentTime;

            }
        }
    }
}

