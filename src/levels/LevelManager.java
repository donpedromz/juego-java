/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import utils.LoadSave;
import static main.Game.TILES_IN_HEIGHT;
import static main.Game.TILES_IN_WIDTH;
import static main.Game.TILES_SIZE;
/**
 *
 * @author juanp
 */
public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level lvl1;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        this.lvl1 = new Level(LoadSave.getLevelData());
    }

    public void draw(Graphics g) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < TILES_IN_WIDTH; i++) {
                int index = lvl1.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i*TILES_SIZE, 
                        j*TILES_SIZE,TILES_SIZE,TILES_SIZE, null);
            }
        }

    }

    public void update() {

    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        this.levelSprite = new BufferedImage[48];
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 12; i++){
                int index = j*12 + i;
                levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
            }
        }
    }
}
