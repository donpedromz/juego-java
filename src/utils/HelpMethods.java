/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.geom.Rectangle2D;
import main.Game;

/**
 *
 * @author juanp
 */
public class HelpMethods {

    public static boolean canMoveHere(float x, float y,
            float width, float height, int[][] lvlData) {
        if (!isSolid(x, y, lvlData)) {
            if (!isSolid(x + width, y + height, lvlData)) {
                if (!isSolid(x + width, y, lvlData)) {
                    if (!isSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {
        if (x < 0 || x >= Game.GAME_WIDTH) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGTH) {
            return true;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        int value = lvlData[(int) yIndex][(int) xIndex];
        if (value <= 35 && value >= 0 && value != 6) {
            return true;
        }
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        if(xSpeed > 0){
            // Derecha
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffSet = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffSet - 1;
        }else{
            //Izquierda
            return currentTile * Game.TILES_SIZE;
        }
    }
    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, Float airSpeed){
        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        if(airSpeed > 0){
            // Cayendo
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffSet = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffSet - 1;
        }else{
            //Saltando
            return currentTile * Game.TILES_SIZE;
        }
    }
}
