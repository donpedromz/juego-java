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
public class GamePanel extends JPanel{
    /**
     * Configuraciones de pantalla
     */
    private MouseInput mouseInput;
    private int xDelta = 0, yDelta = 0;
    private int xDir = 1, yDir = 1;
    private BufferedImage img;
    private BufferedImage[] stAnimation;
    private int aniTick, aniIndex, aniSpeed = 30;
    public void changeXDelta(int value){
        this.xDelta += value;
    }
    public void changeYDelta(int value){
        this.yDelta += value;
    }
    public void setRectPos(int x, int y){
        this.xDelta = x;
        this.yDelta = y;
    }
    public GamePanel(){
        importImg();
        loadAnimations();
        setPanelSize();
        this.mouseInput = new MouseInput(this);
        this.addKeyListener(new KeyBoardInput(this));
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
    }
    private void importImg(){
        InputStream is = getClass().getResourceAsStream("/resources/p1_st.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setPanelSize(){
        Dimension size = new Dimension(1280, 768);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        updateAnimationTick();
        g.drawImage(stAnimation[aniIndex], 0, 0, 128, 128, null);
    }
    private void loadAnimations() {
        this.stAnimation = new BufferedImage[4];
        for(int i = 0; i < 4; i++){
            stAnimation[i] = img.getSubimage(0, i*64, 64, 64);
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex+= 1;
            if(aniIndex >= this.stAnimation.length){
                aniIndex = 0;
            }
        }        
    }
}
