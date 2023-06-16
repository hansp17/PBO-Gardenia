/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacxon;
/**
 *
 * @author hanst
 */
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;

public class App extends PApplet {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public int move=0;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int TOPBAR = 80;
    public int timeInSeconds;
    public static final int FPS = 60;
    public int moveDelay = 8; 
    public int moveCounter = 0;
    public int pathDelay = 3;
    public int pathCounter = 0;
    public int powerTimer;
    public int powerActiveTimer;
    public int spawnTimer;
    public String configPath;
    public boolean gameStarted;
    public int lvlpass;
    public int playerX;
    public int playerY;
    public JSONObject config;
    public JSONArray levels;
    public PowerUps pu;
    public int timer;
    public int currentLevel;
    public int currentScore;
    public List<Concrete> concretes;
    public List<Dirt> dirts;
    public List<String> outlays;
    public List<Double> goals;
    public List<JSONArray> enemies_ls;
    public ArrayList<Enemy> enemies;
    public ArrayList<Path> path;
    public static boolean[][] filled;
    public static int[][] filledYet;
    public static char[][] loadTiles;
    public int lives;
    public boolean win;
    public ArrayList<Integer>pathX;
    public ArrayList<Integer>pathY;

    public App() throws Exception {
        this.configPath = "config.json";
        //this.readyToDraw = false;
        currentLevel=0;
        timer=0;
        powerTimer=0;
        spawnTimer=0;
        gameStarted=false;
        // Creates new App objects for player, enemies, and map
        timeInSeconds=0;
        powerActiveTimer=0;
        dirts = new ArrayList<Dirt>();
        concretes = new ArrayList<Concrete>();
        outlays = new ArrayList<String>();
        goals = new ArrayList<Double>();
        path = new ArrayList();
        this.enemies_ls = new ArrayList<JSONArray>();
        enemies = new ArrayList<>();
        lives=0;
        lvlpass = 0;
        win=false;
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }
    

    /**
     * Load all resources such as images. Initialize the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);
        playerX=0;
        playerY=0;
        this.config = loadJSONObject(configPath);
        pathX=new ArrayList<>();
        pathY=new ArrayList<>();
        this.levels = config.getJSONArray("levels");
        this.lives = config.getInt("lives");
        // for loop to iterate through the contents in levels from the file config.json
        for (int i = 0; i < levels.size(); i++) {
            JSONObject level = levels.getJSONObject(i);
            outlays.add(level.getString("outlay"));
            goals.add(level.getDouble("goal"));
            JSONArray enemies = level.getJSONArray("enemies");
            enemies_ls.add(enemies);
        }
        try {
            readLevel(currentLevel);
            
        } catch (Exception e) {  
            e.printStackTrace();
        }
    }
    
    /** 
     * @param level
     * @throws IOException
     */
    // Method to read level and load map
    public void readLevel(int level) throws IOException{
        FileReader fr = new FileReader(this.outlays.get(level));
        BufferedReader br = new BufferedReader(fr);
        filled = new boolean[32][64];
        filledYet = new int[32][64];
        loadTiles = new char[32][64];
        int index;
        int row = 0;
        int col = 0;

        // While loop to iterate through the txt file, 
        while((index=br.read())!=-1){
            //System.out.print((char)i);
            if (col == 0){
                if (((char)index) == 'X'){
                loadTiles[row][col] = (char)index;
                col++;
                continue;
                } else{
                continue;
                }
            }
            loadTiles[row][col] = (char)index;

            if(col == 63){
                col = -1;
                row++;
            }

            col++;
            if (row == 32){
                break;
            }

        }

        br.close();
        fr.close();

        currentScore=0;
        concretes =  new ArrayList<>();
        for (int i = 0; i < 32; i++){
            for (int j = 0; j < 64; j++){
                if (loadTiles[i][j]== 'X'){
                    Concrete c= new Concrete(j*App.SPRITESIZE,App.TOPBAR+ i*App.SPRITESIZE);
                    c.setSprite(this.loadImage("src/assets/concrete_tile.png"));
                    concretes.add(c);
                    filledYet[i][j]=3;
                    filled[i][j]=true;
                }
                else{
                    Dirt d = new Dirt(j*App.SPRITESIZE,App.TOPBAR+ i*App.SPRITESIZE);
                    d.setSprite(this.loadImage("src/assets/dirt.png"));
                    dirts.add(d);
                    filledYet[i][j]=0;
                    filled[i][j]=false;
                }
            }
        }
        addEnemy();
        // Spawn enemies based on the configuration
        
    }
    
    /**
     * Read the enemies list and spawn it inside the map
     */
    public void addEnemy(){
        for (int i = 0; i < enemies_ls.get(currentLevel).size(); i++) {
            Random rand = new Random();
            JSONObject enemyObj = enemies_ls.get(currentLevel).getJSONObject(i);
            int type = enemyObj.getInt("type");
            String spawn = enemyObj.getString("spawn");
            int x,y;
            if(!spawn.equals("random")){ 
                String[] spawnCoords = spawn.split(",");
                y = Integer.parseInt(spawnCoords[0]);
                x = Integer.parseInt(spawnCoords[1]);
            }
            else{
                {
                    x=rand.nextInt(63)+1;
                    y=rand.nextInt(31)+1;
                }while(loadTiles[y][x]!=' ');
            }

            Enemy enemy;

            if (type == 0) {
                enemy = new Worm(x * App.SPRITESIZE, App.TOPBAR + y * App.SPRITESIZE);
                PImage wm = loadImage("src/assets/worm.png");
                enemy.setSprite(wm);
            } else if (type == 1) {
                enemy = new Beetle(x * App.SPRITESIZE, App.TOPBAR + y * App.SPRITESIZE);
                enemy.setSprite(resizeImage(loadImage("src/assets/beetle.png"),20,20));
            } else {
                // Handle other enemy types if needed
                continue;
            }

            // Add the enemy to the list
            enemies.add(enemy);
        }
    }
    
    /**
     * Spawn power up at the map and change enemy or player behavior based on the collected power up
     */
    public void spawnPowerUp(){
        spawnTimer++;
        collectPower();
        if(spawnTimer/FPS==10){
            Random rnd = new Random();
            int powerUpType = rnd.nextInt(5);
            while (true){
                int posx= rnd.nextInt(62)+1;
                int posy= rnd.nextInt(30)+1;

                if (filledYet[posy][posx]==0){
                    if(powerUpType==0){
                        pu=new Watermelon(posx*20, posy*20+80);
                        pu.setSprite(loadImage("src/assets/watermelon.png"));
                    }
                    else if(powerUpType==1){
                        pu=new Apple(posx*20,posy*20+80);         
                        pu.setSprite(loadImage("src/assets/apple.png"));
                    }
                    else if(powerUpType==2){
                        pu=new Chocolate(posx*20,posy*20+80);         
                        pu.setSprite(loadImage("src/assets/chocolate.png"));
                    }
                    else if(powerUpType==3){
                        pu=new Carrot(posx*20,posy*20+80);         
                        pu.setSprite(loadImage("src/assets/carrot.png"));
                    }
                    else {
                        pu=new Potato(posx*20,posy*20+80);         
                        pu.setSprite(loadImage("src/assets/potato.png"));
                    }
                    break;
                }
            }          
        }
        else if(spawnTimer/FPS>10 && pu.isStatus() && pu!=null){
            pu.draw(this);
        }
        if(pu!=null && !pu.isStatus()){
            if(!pu.isStatus()){
                powerActiveTimer++;
            }
            if(powerActiveTimer/FPS<=powerTimer){
                if(pu instanceof Watermelon){
                    for (Enemy e : enemies) {
                        e.setStatus(1);
                    }
                }
                else if(pu instanceof Apple){
                    moveDelay=4;
                }
                else if(pu instanceof Chocolate){
                    for (Enemy e : enemies) {
                        e.setHit(false);
                    }
                }
                else if(pu instanceof Carrot){
                    for (Enemy e : enemies) {
                        e.setStatus(0);
                    }
                }
                else if(pu instanceof Potato){
                    pathDelay=6;
                }
            }
            else if(powerActiveTimer!=0){
                for (Enemy e : enemies) {
                    e.setStatus(2);
                    e.setHit(true);
                }
                pathDelay=3;
                moveDelay=8;
                spawnTimer=0;
                powerActiveTimer=0;
                pu=null;
            }
        }
    }
    
    /**
     * Update all the area inside the green path
     */
    public void updateFilled(){
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 64; j++) {
                if (filledYet[i][j] == 1) {
                    filled[i][j] = true;
                    filledYet[i][j] = 2;
                }
            }
        }
    }
    
    /**
     * Draw the dirt
     */
    public void drawDirt(){
        for (Dirt d : dirts) {
            d.draw(this);
        }
    }
    
    /**
     * Draw the concrete
     */
    public void drawConcrete(){
        for (Concrete c : concretes) {
            c.draw(this);
        }
    }
    
    /**
     * Draw all the things inside the top bar
     */
    public void drawTopBar(){
        timer++;
        background(112,84,62);
        textSize(25);
        currentScore=0;
        text("Level " + (currentLevel+1), 1160,60);

        // First update maps and all game objects
        updateScore();
        text("Lives: " + lives, 100,60);
        text("Timer: " + (timer/FPS), 100,30);
        if(pu!=null){
            if(!pu.isStatus()){
                text("Player's Power: " + pu.getNama(), 400,30);
                text("Power's Timer : " + (powerActiveTimer/FPS)+" / "+pu.timeRemaining(), 400,60);
            }
            else{
                text("Player's Power: -" , 400,30);
            text("Power's Timer : - / -", 400,60);
            }
        }
        else{
            text("Player's Power: -" , 400,30);
            text("Power's Timer : - / -", 400,60);
        }
        int displayscore=(int)((currentScore) / (64*32.0-concretes.size()) * 100);
//        System.out.println(displayscore);
        text( displayscore + "%/" + (int)(goals.get(currentLevel)*100)+"%", 800, 60); 
        
        if( displayscore >= goals.get(currentLevel)*100){
            currentLevel++;
            if (currentLevel< levels.size() ){
                try {
                    //Thread.sleep(2000);
                    enemies.clear();
                    playerX=0;
                    playerY=0;
                    upPressed=false;
                    downPressed=false;
                    rightPressed=false;
                    leftPressed=false;
                    pu=null;
                    readLevel(currentLevel);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else{
                win=true;
                gameStarted=false;
            }
        }
    }
    /**
     * Draw the player
    */
    public void drawPlayer(){
        image(loadImage("src/assets/ball.png"), playerX*App.SPRITESIZE,App.TOPBAR+ playerY*App.SPRITESIZE);
    }

    /**
     * Check the movement of the player and move the player. It controls the speed of the player
     */
    public void updatePlayer(){
        if(moveCounter >= moveDelay){
            if (move == 1 && playerY > 0 && upPressed) {
                if (filledYet[playerY-1][playerX]==0 && filledYet[playerY][playerX]==0) {
                    path.add(new Path(playerX,playerY));
                    filledYet[playerY][playerX] = 1;
                    playerY--;
                } else {
                    if (filledYet[playerY-1][playerX]>0 && filledYet[playerY][playerX]==0){
                        path.add(new Path(playerX,playerY));
                        filledYet[playerY][playerX]=1;
                    }
                    playerY--;
                }
            } else if (move == 2 && playerY < 32 - 1 && downPressed) {
                if (filledYet[playerY+1][playerX]==0 && filledYet[playerY][playerX]==0) {
                    path.add(new Path(playerX,playerY));
                    filledYet[playerY][playerX] = 1;
                    playerY++;
                } else {
                    if (filledYet[playerY+1][playerX]>0 && filledYet[playerY][playerX]==0){
                        path.add(new Path(playerX,playerY));
                        filledYet[playerY][playerX]=1;
                    }
                    playerY++;
                }
            } else if (move == 3 && playerX > 0 && leftPressed) {
                if (filledYet[playerY][playerX-1]==0 && filledYet[playerY][playerX]==0) {
                    path.add(new Path(playerX,playerY));
                    filledYet[playerY][playerX] = 1;
                    playerX--;
                } else {
                    if (filledYet[playerY][playerX-1]>0 && filledYet[playerY][playerX]==0){
                        path.add(new Path(playerX,playerY));
                        filledYet[playerY][playerX]=1;
                    }
                    playerX--;
                }
            } else if (move == 4 && playerX < 64 - 1 && rightPressed) {
                if (filledYet[playerY][playerX+1]==0 && filledYet[playerY][playerX]==0) {
                    path.add(new Path(playerX,playerY));
                    filledYet[playerY][playerX] = 1;
                    playerX++;
                } else {
                    if (filledYet[playerY][playerX+1]>0 && filledYet[playerY][playerX]==0){
                        path.add(new Path(playerX,playerY));
                        filledYet[playerY][playerX]=1;
                    }
                    playerX++;
                }
            } else {
                move = 0;
                upPressed = false;
                leftPressed = false;
                downPressed = false;
                rightPressed = false;
            }
            moveCounter = 0;
        }
        else{
            moveCounter++;
        }
        if(filledYet[playerY][playerX]>1){        
            int dx = 0, dy = 0;
            for (Enemy e : enemies) {
                drop(e.getX()/20,(e.getY()-80)/20);
            }
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 64; j++) {
                    if(filledYet[i][j]==-2){
                        filledYet[i][j]=0;
                    }
                    else if(filledYet[i][j]==0 || filledYet[i][j]==-1){
                        filledYet[i][j]=1;
                    }
                }
            }
            path.clear();
            updateFilled();
        }
        else if(filledYet[playerY][playerX]==1){
            lives--;
            playerX=0;
            playerY=0;
            clearPath();
        }
    }
    
    /**
     * Count the score
     */
    public void updateScore(){
        currentScore=0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 64; j++) {
                if(filledYet[i][j]==2){
                    currentScore++;
                }
            }
        }
    }
    
    /**
     * Check the area inside the green path that don't have any enemy
     * @param x
     * @param y
     */
    public void drop(int x, int y){
       if(filledYet[y][x]==0){
           filledYet[y][x]=-2;
       }
       if(x-1>=0){
           if(filledYet[y][x-1]==0){
               drop(x-1,y);
           }
       }
       if(x+1<=63){
           if(filledYet[y][x+1]==0){
               drop(x+1,y);
           }
       }
       if(y+1<=31){
           if(filledYet[y+1][x]==0){
               drop(x,y+1);
           }
       }
       if(y-1>=0){
           if(filledYet[y-1][x]==0){
               drop(x,y-1);
           }
       }
   }
    
    /**
     * Event that happen after press the valid key
     */
    public void keyPressed(){
        if ((key == 'w' || keyCode==UP) && playerY > 0) {
            if(filledYet[playerY-1][playerX]>=2){
                move = 1;
                upPressed = true;    
            }
            else if(move != 2){
                move = 1;
                upPressed = true;  
            }
        } else if ((key == 's' || keyCode==DOWN) && playerY < 32 - 1) {
            
            if(filledYet[playerY+1][playerX]>=2){
                move = 2;
                downPressed = true;    
            }
            else if(move != 1){
                move = 2;
                downPressed = true;  
            }
        } else if ((key == 'a' || keyCode==LEFT) && playerX > 0) {
            if(filledYet[playerY][playerX-1]>=2){
                move = 3;
                leftPressed = true;    
            }
            else if(move != 4){
                move = 3;
                leftPressed = true;  
            }
        } else if ((key == 'd' || keyCode==RIGHT) && playerX < 64 - 1) {
            if(filledYet[playerY][playerX+1]>=2){
                move = 4;
                rightPressed = true;    
            }
            else if(move != 3){
                move = 4;
                rightPressed = true;  
            }
        }
    }

    /**
     * Clear the path (red or green)
     */
    public void clearPath(){
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 64; j++) {
                if(filledYet[i][j]==1 || filledYet[i][j]==-1)
                    filledYet[i][j]=0;
            }
        }
    }

    /**
     * Draw the enemy
     */
    public void drawEnemy(){
        for (Enemy e : enemies) {
            e.draw(this);
        }
    }
    
    /**
     * Move the enemy
     */
    public void updateEnemy()
    {
        for (Enemy e : enemies) {
            e.move(filledYet);
        }
    }
    
    /**
     * Check if the power up being collected by the player
     */
    public void collectPower(){
        if(pu != null){
            if(playerX==pu.getX()/20 && playerY==(pu.getY()-80)/20 && pu.isStatus()){
                pu.setStatus(false);
                powerTimer=pu.timeRemaining();
            }         
        }
    }
    
    /**
     * draw the path and the grass
     */
    public void drawGrid(){
        PImage green = loadImage("src/assets/gpath.png");
        PImage red = loadImage("src/assets/rpath.png");
        PImage im = loadImage("src/assets/grass.png");
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 64; j++) {
                if (filledYet[i][j] == 1) {
                    image(green, j*20, i*20+80);
                }
                else if (filledYet[i][j] == -1) {
                    image(red, j*20, i*20+80);
                }
                else if(filledYet[i][j]==2){
                    image(im, j*20, i*20+80);
                }
                else{
                    continue;
                }
            }
        }
    }
    
    /**
     * Check if player hit the enemy and place the player in the top left corner like at the start(with or without losing live)
     */
    public void playerDead(){
        for (Enemy e : enemies) {
            if(e.getX()/20==playerX && (e.getY()-80)/20==playerY && e.isHit()){
                for (int i = 0; i < 32; i++) {
                    for (int j = 0; j < 64; j++) {
                        if(filledYet[i][j]==1){
                            filledYet[i][j]=0;
                        }
                    }
                }
                lives--;
                playerX=0;
                playerY=0;
                clearPath();
                break;
            }
            else if(e.getX()/20==playerX && (e.getY()-80)/20==playerY && !e.isHit()){
                for (int i = 0; i < 32; i++) {
                    for (int j = 0; j < 64; j++) {
                        if(filledYet[i][j]==1){
                            filledYet[i][j]=0;
                        }
                    }
                }
                playerX=0;
                playerY=0;
                clearPath();
                break;
            }
        }
    }
    
    /**
     * Search for red path and spreading it to other green path
     */
    public void change(){
        for (int i=path.size()-1; i>=0; i--) {
            if(filledYet[path.get(i).getY()][path.get(i).getX()]==-1){
                pathCounter++;
                if(pathCounter!=pathDelay){
                    break;
                }
            }
            if(filledYet[path.get(i).getY()][path.get(i).getX()]==-1 && pathCounter==pathDelay){
                if(i+1<path.size()){
                    filledYet[path.get(i+1).getY()][path.get(i+1).getX()]=-1;
                    break;    
                }
                else{
                    playerX=0;
                    playerY=0;
                    lives--;
                    clearPath();
                    break;
                }
            }
        }
        if(pathCounter==pathDelay) pathCounter=0;
    }
    
    private PImage resizeImage(PImage image, int targetWidth, int targetHeight) {
        PImage resized = image.get();
        resized.resize(targetWidth, targetHeight);
        return resized;
    }
    
    /**
     * Event that happen after clicking the mouse
     */
    public void mouseClicked() {
        if (win) {
            resetGame();
        } 
        else if(lives<=0){
            resetGame();
            gameStarted=false;
        }
        else if (!gameStarted) {
            gameStarted = true;
        }
    }

    /**
     * Reset the game
     */
    public void resetGame() {
        currentLevel = 0;
        timer = 0;
        powerTimer = 0;
        spawnTimer = 0;
        lives = config.getInt("lives");
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        playerX = 0;
        playerY = 0;
        enemies.clear();
        pu=null;
        pathDelay=3;
        moveDelay=8;
        spawnTimer=0;
        powerActiveTimer=0;
        pu=null;
        try {
            readLevel(currentLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        win = false;
    }
    
    /**
     * Draw the losing screen
     */
    public void drawLose(){
        image(resizeImage(loadImage("src/assets/lose.png"),800,400), 240, 160);
    }
    
    /**
     * Draw the winning screen
     */
    public void drawWin(){
        image(resizeImage(loadImage("src/assets/win.png"),800,400), 240, 160);
    }
    
    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        if(lives<=0){
            drawLose();
        }
        else if(win){
            drawWin();
        }
        else if(!gameStarted){
            PImage b = loadImage("src/assets/title.png");
            image(resizeImage(b, 1280, 720),0,0);
        }
        else{
            drawTopBar();
            drawDirt();
            drawConcrete();
            change();
            drawGrid();
            spawnPowerUp();
            drawPlayer();
            updatePlayer();
            drawEnemy();
            updateEnemy();
            playerDead();
            
        }
    }
    


    
    
    /**
     * Run App
     * @param args
     */
    public static void main(String[] args) {
        PApplet.main("pacxon.App");
    }
}
