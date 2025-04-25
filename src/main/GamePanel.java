/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import inputs.KeyBoardInput;
import inputs.MouseInput;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author juanp
 */
public class GamePanel extends JPanel {

    /**
     * Configuraciones de pantalla
     */
    private MouseInput mouseInput;
    private int xDelta = 0, yDelta = 0;
    private int xDir = 1, yDir = 1;
    private BufferedImage img, imgRun;
    private BufferedImage[] stAnimation, runAnimation;
    private int aniTick, aniIndex, runAniTick, runAniIndex, aniSpeed = 30, runAniSpeed = 10;
    private boolean isRunning = false;

    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    public void changeYDelta(int value) {
        this.yDelta += value;

    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    public GamePanel() {
        importImg();
        loadAnimations();
        setPanelSize();
        this.mouseInput = new MouseInput(this);
        this.addKeyListener(new KeyBoardInput(this));
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/resources/p1_st.png");
        InputStream is2 = getClass().getResourceAsStream("/resources/p1_run.png");
        try {
            img = ImageIO.read(is);
            imgRun = ImageIO.read(is2);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 640);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!this.isRunning) {
            System.out.println("De pie");
            updateAnimationTick();
            g.drawImage(stAnimation[aniIndex], xDelta, yDelta, 128, 128, null);
        }
        else{
            System.out.println("Corriendo");
            updateRunAnimationTick();
            g.drawImage(runAnimation[runAniIndex], xDelta, yDelta, 128,128,null);
        }

    }
    public void updateRunning(){
        this.isRunning = true;
    }
    public void stop(){
        this.isRunning = false;
    }
    private void loadAnimations() {
        this.stAnimation = new BufferedImage[4];
        this.runAnimation = new BufferedImage[8];
        for (int i = 0; i < 4; i++) {
            stAnimation[i] = img.getSubimage(0, i * 64, 64, 64);
        }
        for (int i = 0; i < 8; i++) {
            runAnimation[i] = imgRun.getSubimage(0, i * 64, 64, 64);
        }

    }
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex += 1;
            if (aniIndex >= this.stAnimation.length) {
                aniIndex = 0;
            }
        }
    }

    private void updateRunAnimationTick() {
        runAniTick++;
        if (runAniTick >= runAniSpeed) {
            runAniTick = 0;
            runAniIndex += 1;
            if (this.runAniIndex >= this.runAnimation.length) {
                this.runAniIndex = 0;
            }
        }
    }
}
