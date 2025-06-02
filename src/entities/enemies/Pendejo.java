/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.enemies;

import entities.Enemy;
import main.Game;
import static utils.Constants.Directions.LEFT;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utils.HelpMethods.canMoveHere;
import static utils.HelpMethods.isEntityOnFloor;
import static utils.HelpMethods.isFloor;

/**
 *
 * @author juanp
 */
public class Pendejo extends Enemy {

    public Pendejo(float x, float y) {
        super(x, y, PENDEJO_ANCHO, PENDEJO_ALTO, PENDEJO);
        initHitbox(x, y + PENDEJO_YDRAW_OFFSET - PENDEJO_ALTO_ESTANDAR,
                PENDEJO_ANCHO - PENDEJO_xDRAW_OFFSET,
                PENDEJO_ALTO - PENDEJO_YDRAW_OFFSET);
    }
      public void update(int[][] lvlData) {
        updateMove(lvlData);
        updateAnimationTick();
    }
    private void updateMove(int[][] lvlData) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + fallSpeed,
                    hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        }
        
        switch (enemyState) {
            case IDDLE:
                this.enemyState = WALK;
                break;
            case WALK:
                float xSpeed = 0;
                if (walkDir == LEFT) {
                    xSpeed += walkSpeed;
                } else {
                    xSpeed -= walkSpeed;
                }
                if (canMoveHere(hitbox.x + xSpeed,
                        hitbox.y, hitbox.width, hitbox.height, lvlData)) {
                    if (isFloor(hitbox, xSpeed, lvlData)) {
                        hitbox.x += xSpeed;
                        return;
                    }

                }
                changeWalkDir();
                break;
        }
    }

}
