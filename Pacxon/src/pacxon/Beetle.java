/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacxon;

/**
 *
 * @author hanst
 */
public class Beetle extends Enemy{
    int directionX, directionY;
    /**
     * Constructor for adding the beetle
     * @param x
     * @param y 
     */
    public Beetle(int x, int y) {
        super(x, y);
        directionX = 1;
        directionY = 1;
    }

    /**
     * Make the beetle move and change the tiles. If the tile is already a grass then change it back to dirt, and green path to red path
     * @param grid 
     */
    @Override
    public void move(int[][] grid) {
        double tempX, tempY;
        tempX = x / 20.0;
        tempY = (y - 80) / 20.0;
        int nextX, tX;
        int nextY, tY;
        tX= (int) Math.ceil(x/20.0);
        tY= (int) Math.ceil((y-80)/20.0);
        
        if(directionX==1){
            nextX= (int) Math.floor(x/20.0+directionX);        
        }
        else{
            nextX= (int) Math.ceil(x/20.0+directionX);
        }
        if(directionY==1){
            nextY= (int) Math.floor((y-80)/20.0+directionY);
        }
        else{
            nextY= (int) Math.ceil((y-80)/20.0+directionY);
        }
        if (grid[tY][nextX] > 0 || grid[tY][nextX]==-1) {
            directionX *= -1;
            if(status<3 && grid[tY][nextX]==2 && hit){
                grid[tY][nextX]=0;
            }
            if(status<3 && grid[tY][nextX]==1 && hit){
                grid[tY][nextX]=-1;
            }
        }

        if (grid[nextY][tX] > 0 || grid[nextY][tX]==-1) {
            directionY *= -1;
            if(status<3 && grid[nextY][tX]==2 && hit){
                grid[nextY][tX]=0;
            }
            if(status<3 && grid[nextY][tX]==1 && hit){
                grid[nextY][tX]=-1;
            }
        }

        x += directionX * status;
        y += directionY * status;
    }
}
