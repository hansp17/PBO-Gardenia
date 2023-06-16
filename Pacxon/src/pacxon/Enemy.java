/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacxon;

import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author hanst
 */
public abstract class Enemy extends GameObjects{

    /**
     * Status controls the movement speed of the enemy
     */
    protected int status;

    /**
     * Control wether the player is in invincible mode or not
     */
    protected boolean hit;

    /**
     * Constructor for adding the Enemy
     * @param x
     * @param y
     */
    public Enemy(int x, int y) {
        super(x, y);
        hit=true;
        status=2;
    }

    /**
     * Setter for the hit
     * @param hit
     */
    public void setHit(boolean hit) {
        this.hit = hit;
    }

    /**
     * Getter for the hit
     * @return
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * Getter for the status
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter for the status
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }
    /**
     * Make enemy move
     * @param grid 
     */
    public abstract void move(int[][] grid);
}
