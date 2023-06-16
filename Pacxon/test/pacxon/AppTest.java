/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package pacxon;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hanst
 */
public class AppTest {
    App app;
    public AppTest() throws Exception {
        app = new App();
    }

    @Test
    public void testSettings() {
        assertEquals(1280, App.WIDTH);
        assertEquals(720, App.HEIGHT);
    }

    @Test
    public void testAddEnemy() {
        app.enemies.add(new Worm(5*20,9*20+80));
        app.enemies.add(new Beetle(7*20,9*20+80));
        app.enemies.add(new Worm(9*20,9*20+80));
        app.enemies.add(new Beetle(31*20,9*20+80));
        boolean a=false,b=false;
        if(app.enemies.get(0) instanceof Worm) a=true;
        if(app.enemies.get(1) instanceof Worm) b=true;
        assertEquals(4, app.enemies.size());
        assertEquals(true, a);
        assertEquals(false, b);
    }

    @Test
    public void testPowerUp1() {
        app.enemies.add(new Worm(5*20,9*20+80));
        app.enemies.add(new Beetle(7*20,9*20+80));
        app.enemies.add(new Worm(9*20,9*20+80));
        app.enemies.add(new Beetle(31*20,9*20+80));
        app.playerX=5;
        app.playerY=5;
        app.pu= new Watermelon(5*20, 5*20+80);
        assertEquals(2, app.enemies.get(0).getStatus());
        assertEquals(2, app.enemies.get(1).getStatus());
        assertEquals(2, app.enemies.get(2).getStatus());
        assertEquals(2, app.enemies.get(3).getStatus());
        assertEquals(true, app.pu.isStatus());
        app.collectPower();
        assertEquals(false, app.pu.isStatus());
        app.spawnPowerUp();
        assertEquals(false, app.pu.isStatus());
        assertEquals(1, app.enemies.get(0).getStatus());
        assertEquals(1, app.enemies.get(1).getStatus());
        assertEquals(1, app.enemies.get(2).getStatus());
        assertEquals(1, app.enemies.get(3).getStatus());
    }
    
    @Test
    public void testPowerUp2() {
        app.enemies.add(new Worm(5*20,9*20+80));
        app.enemies.add(new Beetle(7*20,9*20+80));
        app.enemies.add(new Worm(9*20,9*20+80));
        app.enemies.add(new Beetle(31*20,9*20+80));
        app.playerX=5;
        app.playerY=5;
        app.pu= new Apple(5*20, 5*20+80);
        assertEquals(8, app.moveDelay);
        assertEquals(true, app.pu.isStatus());
        app.collectPower();
        assertEquals(false, app.pu.isStatus());
        app.spawnPowerUp();
        assertEquals(false, app.pu.isStatus());
        assertEquals(4, app.moveDelay);
    }
    
    @Test
    public void testPowerUp3() {
        app.enemies.add(new Worm(5*20,9*20+80));
        app.enemies.add(new Beetle(7*20,9*20+80));
        app.enemies.add(new Worm(9*20,9*20+80));
        app.enemies.add(new Beetle(31*20,9*20+80));
        app.playerX=5;
        app.playerY=5;
        app.pu= new Carrot(5*20, 5*20+80);
        assertEquals(2, app.enemies.get(0).getStatus());
        assertEquals(2, app.enemies.get(1).getStatus());
        assertEquals(2, app.enemies.get(2).getStatus());
        assertEquals(2, app.enemies.get(3).getStatus());
        assertEquals(true, app.pu.isStatus());
        app.collectPower();
        assertEquals(false, app.pu.isStatus());
        app.spawnPowerUp();
        assertEquals(false, app.pu.isStatus());
        assertEquals(0, app.enemies.get(0).getStatus());
        assertEquals(0, app.enemies.get(1).getStatus());
        assertEquals(0, app.enemies.get(2).getStatus());
        assertEquals(0, app.enemies.get(3).getStatus());
    }
    
    @Test
    public void testPowerUp4() {
        app.enemies.add(new Worm(5*20,9*20+80));
        app.enemies.add(new Beetle(7*20,9*20+80));
        app.enemies.add(new Worm(9*20,9*20+80));
        app.enemies.add(new Beetle(31*20,9*20+80));
        app.playerX=5;
        app.playerY=5;
        app.pu= new Chocolate(5*20, 5*20+80);
        assertEquals(true, app.enemies.get(0).isHit());
        assertEquals(true, app.enemies.get(1).isHit());
        assertEquals(true, app.enemies.get(2).isHit());
        assertEquals(true, app.enemies.get(3).isHit());
        assertEquals(true, app.pu.isStatus());
        app.collectPower();
        assertEquals(false, app.pu.isStatus());
        app.spawnPowerUp();
        assertEquals(false, app.pu.isStatus());
        assertEquals(false, app.enemies.get(0).isHit());
        assertEquals(false, app.enemies.get(1).isHit());
        assertEquals(false, app.enemies.get(2).isHit());
        assertEquals(false, app.enemies.get(3).isHit());
    }
    
    @Test
    public void testPowerUp5() {
        app.enemies.add(new Worm(5*20,9*20+80));
        app.enemies.add(new Beetle(7*20,9*20+80));
        app.enemies.add(new Worm(9*20,9*20+80));
        app.enemies.add(new Beetle(31*20,9*20+80));
        app.playerX=5;
        app.playerY=5;
        app.pu= new Potato(5*20, 5*20+80);
        assertEquals(3, app.pathDelay);
        assertEquals(true, app.pu.isStatus());
        app.collectPower();
        assertEquals(false, app.pu.isStatus());
        app.spawnPowerUp();
        assertEquals(false, app.pu.isStatus());
        assertEquals(6, app.pathDelay);
    }

    @Test
    public void testUpdateFilled() {
        app.enemies.add(new Worm(8*20,8*20+80));
        app.filledYet=new int [32][64];
        app.filled=new boolean [32][64];
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 64; j++) {
                if(i==0 || i==31 || j==0 || j==63){
                    app.filledYet[i][j]=3;
                }
                else if(j==3){
                    app.filledYet[i][j]=1;
                }
                else app.filledYet[i][j]=0;
            }
        }
        assertEquals(0, app.filledYet[5][2]);
        assertEquals(1, app.filledYet[5][3]);
        app.playerX=0;
        app.playerY=0;
        app.updateFilled();
        assertEquals(2, app.filledYet[5][3]);
    }
    
    @Test
    public void testDrop() {
        app.enemies.add(new Worm(8*20,8*20+80));
        app.filledYet=new int [32][64];
        app.filled=new boolean [32][64];
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 64; j++) {
                if(i==0 || i==31 || j==0 || j==63){
                    app.filledYet[i][j]=3;
                }
                else if(j==3){
                    app.filledYet[i][j]=1;
                }
                else app.filledYet[i][j]=0;
            }
        }
        assertEquals(0, app.filledYet[5][2]);
        assertEquals(1, app.filledYet[5][3]);
        app.playerX=0;
        app.playerY=0;
        app.drop(app.enemies.get(0).x/20, (app.enemies.get(0).y-80)/20);
        app.updateFilled();
        assertEquals(-2, app.filledYet[5][6]);
    }
    @Test
    public void testUpdatePlayer() {
        app.path=new ArrayList<>();
        app.enemies.add(new Worm(8*20,8*20+80));
        app.filledYet=new int [32][64];
        app.filled=new boolean [32][64];
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 64; j++) {
                if(i==0 || i==31 || j==0 || j==63){
                    app.filledYet[i][j]=3;
                }
                else if(j==3){
                    app.filledYet[i][j]=1;
                }
                else app.filledYet[i][j]=0;
            }
        }
        assertEquals(0, app.filledYet[5][2]);
        assertEquals(1, app.filledYet[5][3]);
        app.playerX=0;
        app.playerY=0;
        app.updatePlayer();
        assertEquals(2, app.filledYet[5][2]);
        
    }

    @Test
    public void testUpdateScore() {
        app.path=new ArrayList<>();
        app.enemies.add(new Worm(8*20,8*20+80));
        app.filledYet=new int [32][64];
        app.filled=new boolean [32][64];
        assertEquals(0, app.currentScore);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 64; j++) {
                if(i==0 || i==31 || j==0 || j==63){
                    app.filledYet[i][j]=3;
                }
                else if(j==3){
                    app.filledYet[i][j]=1;
                }
                else app.filledYet[i][j]=0;
            }
        }
        app.playerX=0;
        app.playerY=0;
        app.updatePlayer();
        app.updateScore();
        assertEquals(90, app.currentScore);
    }

    @Test
    public void testUpdateEnemy() {
        app.filledYet = new int[32][64];
        app.enemies.add(new Worm(5*20,9*20+80));
        app.enemies.add(new Beetle(7*20,9*20+80));
        assertEquals(100, app.enemies.get(0).getX());
        assertEquals(140, app.enemies.get(1).getX());
        assertEquals(260, app.enemies.get(0).getY());
        assertEquals(260, app.enemies.get(1).getY());
        app.updateEnemy();
        app.updateEnemy();
        app.updateEnemy();
        app.updateEnemy();
        app.updateEnemy();
        assertEquals(110, app.enemies.get(0).getX());
        assertEquals(150, app.enemies.get(1).getX());
        assertEquals(270, app.enemies.get(0).getY());
        assertEquals(270, app.enemies.get(1).getY());
    }

    @Test
    public void testPlayerDead() {
        app.filledYet = new int[32][64];
        app.enemies.add(new Worm(5*20,9*20+80));
        app.enemies.add(new Beetle(7*20,9*20+80));
        app.playerX=5;
        app.playerY=9;
        app.lives=3;
        app.playerDead();
        assertEquals(2, app.lives);
        assertEquals(0, app.playerX);
        assertEquals(0, app.playerY);
        app.enemies.get(1).setHit(false);
        app.playerX=7;
        app.playerY=9;
        app.playerDead();
        assertEquals(2, app.lives);
        assertEquals(0, app.playerX);
        assertEquals(0, app.playerY);
    }
    
}
