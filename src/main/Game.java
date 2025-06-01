/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entities.Crosshair;
import entities.Player;
import java.awt.Graphics;
import levels.LevelManager;
import utils.SoundManager;

/**
 *
 * @author juanp
 */
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameLoopThread;
    private final int FPS_SET = 90;
    private final int UPS_SET = 100;
    public final static int TILE_DEFAULT_SIZE = 32;
    public static final float SCALE = 0.87f;
    public static final int TILES_IN_WIDTH = 40;
    public static final int TILES_IN_HEIGHT = 20;
    public static final int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGTH = TILES_SIZE * TILES_IN_HEIGHT;
    private Player player;
    private LevelManager levelManager;

    public Game() {
        initClasses();
        this.gamePanel = new GamePanel(this);
        this.gameWindow = new GameWindow(gamePanel);
        this.gamePanel.requestFocus();
        loadSounds();
        startGameLoop();

    }
    
    private void loadSounds() {
        SoundManager.loadSound("pistol_shot", "/resources/sounds/weapons/pistol/pistol_shot.wav");
        SoundManager.loadSound("pistol_ricochet", "/resources/sounds/weapons/pistol/pistol_ricochet.wav");
        SoundManager.loadSound("pistol_empty", "/resources/sounds/weapons/pistol/pistol_empty.wav");
        SoundManager.loadSound("pistol_reload", "/resources/sounds/weapons/pistol/pistol_reload.wav");
    }

    private void startGameLoop() {
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    public void update() {
        player.update();
        levelManager.update();
    }

    public void render(Graphics g) {
        player.render(g);
        levelManager.draw(g);
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();
        int frames = 0;
        int updates = 0;

        double deltaU = 0;
        double deltaF = 0;
        long lastCheck = System.currentTimeMillis();
        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                deltaF--;
                frames++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "| UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    private void initClasses() {
        this.levelManager = new LevelManager(this);
        this.player = new Player(500, 200, 64, 64);
        this.player.loadLvlData(
                levelManager.getLvl1().getLvlData());

    }

    void windowFocusLost() {
        this.player.resetDirBooleans();
    }
}
