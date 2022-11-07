package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Settings {
    JLabel leftArrow = new JLabel("<",SwingConstants.CENTER);
    JLabel rightArrow = new JLabel(">",SwingConstants.CENTER);
    JLabel mapOptions = new JLabel("",SwingConstants.CENTER);

    void showSettings(Game game) {
        setLeftArrow(game);
        setMapOptions(game);
        setRightArrow(game);
        setSettings(game);
    }

    void setSettings(Game game) {
        game.settingsButton.setFont(game.gameScreen.setGameFont(30));
        game.settingsButton.setBounds(900, 350, 400, 90);
        game.settingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        game.gameScreen.add(game.settingsButton);
        game.settingsButton.setVisible(true);
    }


    void setLeftArrow(final Game game) {
        leftArrow.setFont(game.gameScreen.setGameFont(60));
        leftArrow.setBounds(810, 425, 90, 90);
        game.gameScreen.add(leftArrow);
        leftArrow.setVisible(true);
        leftArrow.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            if(game.gameState == false) {
                if(game.mapOption > 0) {
                    game.mapOption--;
                }
                else {
                    game.mapOption = game.mapList.length - 1;
                }
                setMapOptions(game);
            }
            }
        });
    }

    void setRightArrow(final Game game) {
        rightArrow.setFont(game.gameScreen.setGameFont(60));
        rightArrow.setBounds(1300, 425, 90, 90);
        game.gameScreen.add(rightArrow);
        rightArrow.setVisible(true);
        rightArrow.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            if(game.gameState == false) {
                if(game.mapOption < game.mapList.length - 1) {
                    game.mapOption++;
                }
                else {
                    game.mapOption = 0;
                }
                setMapOptions(game);
            }
            }
        });
    }

    void setMapOptions(Game game) {
        mapOptions.setFont(game.gameScreen.setGameFont(30));
        mapOptions.setBounds(900, 425, 400, 90);
        game.gameScreen.add(mapOptions);
        mapOptions.setText(game.mapList[game.mapOption]);
        mapOptions.setVisible(true);
    }

}
