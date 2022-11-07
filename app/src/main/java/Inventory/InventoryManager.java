package Inventory;

import javax.swing.*;
import java.awt.*;

public class InventoryManager extends InventoryRender {

    private static final int[] inventory = {0, 0, 0, 0, 0, 0};


    private static final JLabel[] labels = {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()};


    /*
    * Inventory Manager, array element corresponds to ith cell rendered on the game screen
    * 0 = empty (default)
    * 1 = speed up
    * 2 = teleport
     */

    private void setup() {
        for (int i = 0; i < SIZE; i++) {
            inventory[i] = 0;
        }

        int xPos = CELL_POS_X + 6;
        for (int i = 0; i < SIZE; i++) {
            labels[i].setVisible(false);
            labels[i].setBounds(xPos, BACKGROUND_POS_Y + 18, 40, 40);
            xPos = xPos + 59;
        }
    }


    private void updateInventory() {
        for (int i = 0; i < SIZE; i++) {
            if (inventory[i] == 0) {
                labels[i].setVisible(false);
            } else {
                ImageIcon tmpImage = new ImageIcon(new ImageIcon("../src/main/resources/interface/inventory/" + inventory[i] + ".png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
                labels[i].setVisible(true);
                labels[i].setIcon(tmpImage);
            }
        }
    }



    /*
     * Public methods
     */

    public JLabel[] getLabels() {
        return labels;
    }

    public void resetInventory() {
        setup();
    }

    public void addItem(int itemCode) {
        /*
         * 0 = empty (default)
         * 1 = speed up
         * 2 = teleport
         */
        for (int i = 0; i < SIZE; i++) {
            if (inventory[i] == 0) {
                inventory[i] = itemCode;
                break;
            }
        }
        updateInventory();

    }

    public int useItem(int slot) {
        if (inventory[slot] == 0) {
            return -1;
        }


        int powerUp = inventory[slot];
        inventory[slot] = 0;


        updateInventory();
        return powerUp;

    }

    public boolean hasEmptySlot() {
        for (int i = 0; i < SIZE; i++) {
            if (inventory[i] == 0) {
                return true;
            }
        }
        return false;
    }
}
