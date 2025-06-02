/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static main.Game.TILES_IN_HEIGHT;
import static main.Game.TILES_IN_WIDTH;

/**
 *
 * @author juanp
 */
public class LoadSave {

    public static final String PLAYER_ATLAS = "resources/p1.png";
    public static final String LEVEL_ATLAS = "resources/TILE_SET.png";
    public static final String LVL_1_DATA = "resources/level_one_data.png";
    public static final String ARMS_ATLAS = "resources/arms/p1/pistol.png";
    public static final String ENEMY1_ATLAS = "resources/enemies/e1.png";

    public static BufferedImage getSpriteAtlas(String filename) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
        try {
            img = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return img;
    }

    public static int[][] getLevelData() {
        int[][] lvlData = new int[TILES_IN_HEIGHT][TILES_IN_WIDTH];
        BufferedImage img = getSpriteAtlas(LVL_1_DATA);
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48) {
                    value = 6;
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }
}
