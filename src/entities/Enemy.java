/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import main.Game;
import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.EnemyConstants.getSpritAmmount;
import static utils.HelpMethods.*;

/**
 *
 * @author juanp
 */
public abstract class Enemy extends Entity {

    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 25;
    // Gravedad
    protected boolean inAir = false;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 1.0f * Game.SCALE;
    protected int walkDir = LEFT;
    protected boolean firstUpdate = true;

    public Enemy(float x, float y, float width, float height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.enemyState = IDDLE;
        initHitbox(x, y, width, height);
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!isEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
        firstUpdate = false;

    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpritAmmount(enemyType, enemyState)) {
                aniIndex = 0;
            }
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT) {
            walkDir = RIGHT;

        } else {
            walkDir = LEFT;
        }
    }

}
