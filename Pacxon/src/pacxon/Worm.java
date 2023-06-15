
package pacxon;

/**
 *
 * @author hanst
 */
public class Worm extends Enemy{
    int directionX, directionY;
    public Worm(int x, int y) {
        super(x, y);
        directionX=1;
        directionY=1;
    }

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
        if (grid[tY][nextX] > 1 || grid[tY][nextX]==-1) {
            directionX *= -1;
        }
        else if (grid[tY][nextX] == 1) {
            directionX *= -1;
            if(hit)
            grid[tY][nextX]=-1;
        }

        if (grid[nextY][tX] > 1 || grid[nextY][tX]==-1) {
            directionY *= -1;
        }
        else if (grid[nextY][tX] == 1) {
            directionY *= -1;
            if(hit)
            grid[nextY][tX]=-1;
        }

        x += directionX * status;
        y += directionY * status;
    }
    
}
