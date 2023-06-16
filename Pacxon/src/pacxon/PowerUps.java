/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacxon;

/**
 *
 * @author hanst
 */
public class PowerUps extends GameObjects implements Collectable{
    private boolean status;
    private String nama;

    /**
     * Constructor for adding the powerups
     * @param x
     * @param y
     */
    public PowerUps(int x, int y) {
        super(x, y);
        status=true;
    }

    /**
     * Getter for the nama
     * @return
     */
    public String getNama() {
        return nama;
    }

    /**
     * Setter for the nama
     * @param nama
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * Getter for the X coordinate
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for the X coordinate
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for the Y coordinate
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for the Y coordinate
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Returning the powerups time
     * @return
     */
    @Override
    public int timeRemaining() {
        return 10;
    }

    /**
     * Setter for the powerups status
     * @return
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Setter for the status
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
