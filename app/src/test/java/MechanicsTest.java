import Inventory.InventoryManager;
import Maze.PowerUps;
import org.graalvm.compiler.nodes.InvokeWithExceptionNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;

public class MechanicsTest {
        int[][] map =  {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,4,1,1,1,0,1,1,1,1,1,1,1,1,1,3,1,1},
                        {1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};


    InventoryManager inventoryManager = new InventoryManager();
    PowerUps power = new PowerUps(map);



    // integration tests
    @Test
    void checkInventory() {
        inventoryManager.resetInventory();

        for (int i = 0; i < 6; i++) {
            inventoryManager.addItem(1);

        }

        assertFalse(inventoryManager.hasEmptySlot());

        inventoryManager.useItem(2);

        assertTrue(inventoryManager.hasEmptySlot());

        for (int i = 0; i < 6; i++) {
            if (i == 2) {
                assertEquals(inventoryManager.useItem(i), -1);
            } else {

                inventoryManager.useItem(i);
            }

        }

        inventoryManager.resetInventory();

    }

    @Test
    void checkPowerUps() {

        inventoryManager.resetInventory();



        power.generate(1);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (map[j][i] == 1) {
                    inventoryManager.addItem(1);

                }
            }
        }

        assertNotNull(power.getSpeedItems());

        assertNotNull(power.getTeleportItems());

        inventoryManager.resetInventory();

        for (int i = 0; i < 6; i++) {
            inventoryManager.addItem(1);
        }








    }


}
