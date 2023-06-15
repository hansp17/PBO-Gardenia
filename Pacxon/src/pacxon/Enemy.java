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
    protected int status;
    protected boolean hit;
    public Enemy(int x, int y) {
        super(x, y);
        hit=true;
        status=2;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isHit() {
        return hit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
      
    public abstract void move(int[][] grid);
}
