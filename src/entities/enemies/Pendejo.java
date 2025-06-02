/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.enemies;

import entities.Enemy;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.Game;
import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;

/**
 *
 * @author juanp
 */
public class Pendejo extends Enemy {

    private Rectangle2D.Float attackBox;
    private int attackBoxOffSetX = (int) (45 * Game.SCALE);
    private int attackBoxOffsetY = (int) (12 * Game.SCALE);

    public Pendejo(float x, float y) {
        super(x, y, PENDEJO_ANCHO, PENDEJO_ALTO, PENDEJO);
        initHitbox(x, y + PENDEJO_YDRAW_OFFSET - PENDEJO_ALTO_ESTANDAR,
                PENDEJO_ANCHO - PENDEJO_xDRAW_OFFSET,
                PENDEJO_ALTO - PENDEJO_YDRAW_OFFSET);
        initAttackBox();
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            updateInAir(lvlData);
        }

        switch (enemyState) {
            case IDDLE:
                newState(WALK);
                break;
            case WALK:
                if (canSeePlayer(lvlData, player)) {
                    turnTowarsPlayer(player);
                }
                if (isPlayerCloseForAttack(player)) {
                    newState(ATTACK);
                }
                move(lvlData);
                break;
            case ATTACK:
                if (aniIndex == 0) {
                    attackChecked = false;
                }
                if (aniIndex == 4 && !attackChecked) {
                    checkEnemyHit(this.attackBox, player);
                }
            case HURT:

        }
    }

    public int flipX() {
        if (walkDir == RIGHT) {
            return (int) hitbox.width;
        } else {
            return 0;
        }
    }

    public int flipW() {
        if (walkDir == RIGHT) {
            return -1;
        } else {
            return 1;
        }
    }

    public Rectangle2D.Float getAttackBox() {
        return attackBox;
    }

    private void initAttackBox() {
        this.attackBox = new Rectangle2D.Float((this.hitbox.x + this.attackBoxOffSetX), this.hitbox.y + attackBoxOffsetY,
                (int) (40 * Game.SCALE), (int) (28 * Game.SCALE));
    }

    private void updateAttackBox() {
        if (walkDir == LEFT) {
            attackBox.x = hitbox.x + this.attackBoxOffSetX;
        } else {
            attackBox.x = hitbox.x - attackBox.width;
        }
        attackBox.y = hitbox.y + this.attackBoxOffsetY;
    }

    public void drawAttackBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public void hurt(int i) {
        this.currentHealth -= i;
        if (currentHealth <= 0) {
            newState(DEATH);
        } else {
            newState(HURT);
        }
    }

    public void reset() {
        initHitbox(x, y + PENDEJO_YDRAW_OFFSET - PENDEJO_ALTO_ESTANDAR,
                PENDEJO_ANCHO - PENDEJO_xDRAW_OFFSET,
                PENDEJO_ALTO - PENDEJO_YDRAW_OFFSET);
        initAttackBox();
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDDLE);
        this.active = true;
        fallSpeed = 0;
    }

}
