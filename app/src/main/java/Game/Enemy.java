package Game;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Enemy {
    int x;
    int y;
    String imageName = "../src/main/resources/Images/ice_fiend.png";
    ImageIcon image = new ImageIcon(new ImageIcon(this.imageName).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
    JLabel imageLabel = new JLabel(image);

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void spawn(GameScreen gameScreen) {
        setImage();
        gameScreen.add(imageLabel);
    }

    void setImage() {
        imageLabel.setBounds(x * 40, y * 40, 40, 40);
        imageLabel.setVisible(true);
    }

    void move(int[][] map, int charXPos, int charYPos) {
        Point p = nextMove(map, charXPos, charYPos);
        x = p.x;
        y = p.y;

        setImage();

    }

    boolean inBound(int x, int y) {
        if(x >= 0 && x <= 19 && y >= 0 && y <= 19) {
            return true;
        }
        return false;
    }

    Point nextMove(int[][] map, int charX, int charY) {
        LinkedList<Point> l = new LinkedList<>();
        Point path[][] = bfs(map);
        Point p = path[charY][charX];
        l.add(new Point(charX, charY));
        while(path[p.y][p.x] != null) {
            l.add(new Point(p.x, p.y));
            p = path[p.y][p.x];
        }
        return l.getLast();
    }

    public Point[][] bfs(int[][] map) {
        int originX;
        int originY;

        boolean[][] seen = new boolean[20][20];
        Point[][] path = new Point[20][20];
        Queue<Point> queue = new LinkedList<>();

        Point p = new Point(x, y);

        queue.add(p);
        path[p.y][p.x] = null;
        seen[p.y][p.x] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, 1, -1};


        //enqueue the the point of the current coordinate of the enemy
        //put that point in the path 2d array
        //mark the point as seen
        //loop until the point is the main character
        while(!queue.isEmpty()) {
        //while(p.x != charX && p.y != charY) {
            //dequeue the latest point in queue
            //set the origin positions to be of the dequeued point
            p = queue.remove();
            originX = p.x;
            originY = p.y;

            for(int i = 0; i < 4; i++) {
                //go through the points around p by setting the coordinate of the point to be the origin
                //if at that position, seen is false, there is nothing blocking, enqueue that point
                //the parent of that point is added to the path 2d array
                if(inBound(originX + dr[i], originY + dc[i]) && !seen[originY + dc[i]][originX + dr[i]] && map[originY + dc[i]][originX + dr[i]] != 0){
                    queue.add(new Point(originX + dr[i], originY + dc[i]));
                    //System.out.print("("+ originX + dr[i] +","+ originY + dc[i] +") ");
                    seen[originY + dc[i]][originX + dr[i]] = true;
                    path[originY + dc[i]][originX + dr[i]] = p;
                }
            }
        }
        return path;
    }
}
