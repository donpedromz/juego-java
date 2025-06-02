/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import utils.LoadSave;
import static main.Game.*;

/**
 *
 * @author juanp
 */
public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage background;
    private Level lvl1;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        loadBackgroundImage();
        this.lvl1 = new Level(LoadSave.getLevelData());
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,GAME_WIDTH, GAME_HEIGTH);
        g.drawImage(background,0,0, GAME_WIDTH, GAME_HEIGTH, null);
        for (int j = 0; j < TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < TILES_IN_WIDTH; i++) {
                int index = lvl1.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i * TILES_SIZE,
                        j * TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
            }
        }

    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);

        int tileWidth = 16;
        int tileHeight = 16;

        int tilesX = img.getWidth() / tileWidth;
        int tilesY = img.getHeight() / tileHeight;

        BufferedImage[] levelSprites = new BufferedImage[tilesX * tilesY];

        for (int y = 0; y < tilesY; y++) {
            for (int x = 0; x < tilesX; x++) {
                int index = y * tilesX + x;
                levelSprites[index] = img.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
            }
        }
        this.levelSprite = levelSprites;
    }

    public Level getLvl1() {
        return lvl1;
    }

    private void loadBackgroundImage() {
        this.background = LoadSave.
                getSpriteAtlas(LoadSave.BACKGROUND_ATLAS);
    }

}
