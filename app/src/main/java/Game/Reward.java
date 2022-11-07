package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Reward {
    int point;
    public int x;
    public int y;
    String imageName;
    ImageIcon image;
    JLabel imageLabel;

    public Reward(int x, int y, int point, String imageName) {
        this.point = point;
        this.x = x;
        this.y = y;
        this.imageName = imageName;
        this.image = new ImageIcon(new ImageIcon(this.imageName).getImage().getScaledInstance(32, 32,Image.SCALE_SMOOTH));
        this.imageLabel = new JLabel(image);
    }

    int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    void spawn(GameScreen gameScreen) {
        imageLabel.setBounds(x * 40, y * 40, 40, 40);
        gameScreen.add(imageLabel);
        imageLabel.setVisible(true);
    }

    void setImage() {
        imageLabel.setBounds(x * 40, y * 40, 40, 40);
        imageLabel.setVisible(true);
    }

    void despawn() {
        x = 20;
        y = 20;
        imageLabel.setVisible(false);
    }

    public void addReward(ArrayList<Reward> rewardList) {
        rewardList.add(this);
    }

    public void createAllRewardRandom(int[][] map, int rewardNum, ArrayList<Reward> rewardList, int point, String imageName) {
        for(int i = 0; i < rewardNum; ){
            int rewardXPos = random(0, 19);
            int rewardYPos = random(0, 19);
            if(map[rewardYPos][rewardXPos] == 1) {
                rewardList.add(new Reward(rewardXPos, rewardYPos, point, imageName));
                map[rewardYPos][rewardXPos] = 2;
                i++;
            }
        }
    }
}