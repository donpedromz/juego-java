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

    public static boolean isAllTileWalkable(int xStart,
            int xEnd, int yTile, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (isTileSolid(xStart + i, yTile, lvlData)) {
                return false;
            }
            if(!isTileSolid(xStart + i, yTile + 1, lvlData)){
                return false;
            }
        }
        return true;
    }

    public static boolean isSightClear(int[][] lvlData,
            Rectangle2D.Float hitbox1,
            Rectangle2D.Float hitbox2, int yTile) {
        int firstXTile = (int) (hitbox1.x / Game.TILES_SIZE);
        int secondXTile = (int) (hitbox2.x / Game.TILES_SIZE);
        if (firstXTile > secondXTile) {
            if(!isAllTileWalkable(secondXTile, firstXTile, yTile, lvlData)){
                return false;
            }
        } else {
            if(!isAllTileWalkable(firstXTile, secondXTile, yTile, lvlData)){
                return false;
            }
        }
        return true;
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
        return isTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];
        if (value <= 35 && value >= 0 && value != 6) {
            return true;
        }
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {

        if (xSpeed > 0) {
            // Derecha
            int currentTile = (int) ((hitbox.x + hitbox.width) / Game.TILES_SIZE);
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffSet = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffSet - 1;
        } else {
            //Izquierda
            int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
            if (!isSolid((hitbox.x + hitbox.width), hitbox.y + hitbox.height + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, Float airSpeed) {
        int currentTile = (int) ((hitbox.y + hitbox.height) / Game.TILES_SIZE);
        System.out.println(currentTile);
        if (airSpeed > 0) {
            // Cayendo
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffSet = (int) (Game.TILES_SIZE - hitbox.height);
            System.out.println(tileYPos + yOffSet - 1);
            return tileYPos + yOffSet - 1;
        } else {
            //Saltando
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        float xLeft = hitbox.x + xSpeed;
        float xRight = hitbox.x + xSpeed + hitbox.width;

        float y = hitbox.y + hitbox.height + 1;

        return isSolid(xLeft, y, lvlData) || isSolid(xRight, y, lvlData);
    }

}
