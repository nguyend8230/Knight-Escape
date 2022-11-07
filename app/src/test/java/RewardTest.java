import Game.Game;
import Game.GameScreen;
import Game.Reward;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class RewardTest {

    int [][] map = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

    void resetMap() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 20; j++) {
                map[i][j] = 0;
            }
        }
        for(int i = 10; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                map[i][j] = 1;
            }
        }
    }

    void printMap() {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    //checks that all rewards spawn on empty squares only
    @Test
    void createAllRewardRandomTest() {
        int count = 0;
        Game game = new Game();
        Reward reward = new Reward(20, 20, 5, "");

        for(int i = 0; i < 100; i++) {
            reward.createAllRewardRandom(map, 10, game.rewardList, 5, "");
            for(int j = 10; j < 20; j++) {
                for(int k = 0; k < 20; k++) {
                    if(map[j][k] == 2) {
                        count++;
                    }
                }
            }

            assertTrue(10 == count);
            resetMap();
            count = 0;
        }
    }

}
