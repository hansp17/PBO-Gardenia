/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacxon;

/**
 *
 * @author hanst
 */
public class Path{
    private int x,y;
    boolean status;
    
    /**
     * constructor to add the path coordinate
     * @param x
     * @param y 
     */
    public Path(int x, int y) {
        this.x = x;
        this.y = y;
        status=true;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
