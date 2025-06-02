/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import entities.enemies.Pendejo;
import gameStates.states.Playing;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import utils.LoadSave;
import static utils.Constants.EnemyConstants.*;
/**
 *
 * @author juanp
 */
public class EnemyManager {
    private Playing playing;
    private List<Pendejo> pendejos;
    private BufferedImage[][] pendejoArr;
    public EnemyManager(Playing playing){
        this.playing = playing;
        this.pendejos = new ArrayList<Pendejo>();
        loadEnemyImgs();
        addEnemies();
    }
    public void update(int[][] lvlData){
        for(Pendejo p : pendejos){
            p.update(lvlData);
        }
    }
    public void draw(Graphics g){
        drawEnemies(g);
    }
    private void loadEnemyImgs() {
        pendejoArr = new BufferedImage[5][6];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.ENEMY1_ATLAS);
        for(int j = 0; j < pendejoArr.length; j++){
            for(int i = 0; i < pendejoArr[0].length; i++){
                pendejoArr[j][i] = temp.getSubimage(i * PENDEJO_ANCHO_ESTANDAR , 
                        j * PENDEJO_ALTO_ESTANDAR, 
                        PENDEJO_ANCHO_ESTANDAR, PENDEJO_ALTO_ESTANDAR);
            }
        }
    }

    private void drawEnemies(Graphics g) {
        for(Pendejo p : pendejos){
            p.drawHitbox(g);
            g.drawImage(pendejoArr[p.getEnemyState()][p.getAniIndex()],(int) 
                    p.getHitbox().x, (int)p.getHitbox().y - PENDEJO_YDRAW_OFFSET, 
                    PENDEJO_ANCHO, PENDEJO_ALTO,null);
        }
    }
    private void addEnemies() {
        this.pendejos = LoadSave.getPendejos();
    }
    
}
