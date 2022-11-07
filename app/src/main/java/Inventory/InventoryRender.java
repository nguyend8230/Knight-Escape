package Inventory;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class InventoryRender extends JFrame {


    protected final int SIZE = 6;

    /* Inventory background */
    private JLabel backgroundLabel;
    protected final int BACKGROUND_POS_X = 430 ;
    protected final int BACKGROUND_POS_Y = 800;

    /* Starting position of the first cell, increase CELL_POS_X for the other cells */
    private final ArrayList<JLabel> cells = new ArrayList<>();
    private final ArrayList<JLabel> numbers = new ArrayList<>();
    protected int CELL_POS_X = BACKGROUND_POS_X + 11;

    /*
     * Private methods
     */
    private void createCells() {
        int xPos = CELL_POS_X;
        for (int i = 0; i < SIZE; i++) {
            JLabel individualCell = new JLabel( new ImageIcon(new ImageIcon("../src/main/resources/interface/cell.png").getImage().getScaledInstance(53, 53, Image.SCALE_SMOOTH)));

            individualCell.setBounds(xPos, BACKGROUND_POS_Y + 11, 53, 53);
            individualCell.setVisible(true);
            xPos = xPos + 59;
            cells.add(individualCell);
        }

    }

    private void createBackground() {

        ImageIcon tmpImage = new ImageIcon(new ImageIcon("../src/main/resources/interface/bg.png").getImage().getScaledInstance(370, 74, Image.SCALE_SMOOTH));
        backgroundLabel = new JLabel(tmpImage);

        backgroundLabel.setBounds(BACKGROUND_POS_X, BACKGROUND_POS_Y, 370, 74);
        backgroundLabel.setVisible(true);

    }

    private void createNumbers() {
        int xPos = CELL_POS_X;
        for (int i = 0; i < SIZE; i++) {
            JLabel number = new JLabel(String.valueOf(i+1));
            number.setBounds(xPos + 44, BACKGROUND_POS_Y + 47, 20, 20);
            number.setForeground(Color.gray);
            xPos = xPos + 59;
            numbers.add(number);
        }
    }

    /*
    * Public getters
    */

    public JLabel getBackgroundLabel() {
        createBackground();
        return backgroundLabel;
    }

    public ArrayList<JLabel> getCells() {
        createCells();
        return cells;
    }

    public ArrayList<JLabel> getNumbers() {
        createNumbers();
        return numbers;
    }

}
