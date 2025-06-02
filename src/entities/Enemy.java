/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.geom.Rectangle2D;
import main.Game;
import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.EnemyConstants.getSpritAmmount;
import static utils.HelpMethods.*;
import utils.SoundManager;

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
    // Movimiento del enemigo
    protected float walkSpeed = 1.0f * Game.SCALE;
    protected int walkDir = LEFT;
    protected boolean firstUpdate = true;

    protected int tileY;

    // Rango de vision y ataque
    protected float attackDistance = Game.TILES_SIZE;
    // Vida
    protected int maxHealth;
    protected int currentHealth;
    // Daño que hacen los bichos
    protected int damage;
    protected boolean active = true;
    protected boolean attackChecked = false;

    public Enemy(float x, float y, float width, float height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.enemyState = IDDLE;
        this.maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        this.damage = GetEnemyDamage(enemyType);
        initHitbox(x, y, width, height);
    }

    protected void move(int[][] lvlData) {
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
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!isEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
        firstUpdate = false;

    }

    protected void updateInAir(int[][] lvlData) {
        if (canMoveHere(hitbox.x, hitbox.y + fallSpeed,
                hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            this.tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void checkEnemyHit(Rectangle2D.Float hitbox,Player player) {
        if(hitbox.intersects(player.hitbox)){
            player.changeHealth(-damage);
            SoundManager.playSound("hurt2");
            attackChecked = true;
            
        }
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpritAmmount(enemyType, enemyState)) {
                aniIndex = 0;
                if (enemyState == ATTACK) {
                    enemyState = IDDLE;
                } else if (enemyState == HURT) {
                    enemyState = IDDLE;
                } else if (enemyState == DEATH) {
                    active = false;
                }
            }
        }
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) {
                if (isSightClear(lvlData, hitbox, player.hitbox, this.tileY)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void turnTowarsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x) {
            walkDir = LEFT;
        } else {
            walkDir = RIGHT;
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

    protected boolean isPlayerInRange(Player player) {
        int absValue = Math.abs((int) (player.hitbox.x - this.hitbox.x));
        if (absValue <= attackDistance * 6) {
            return true;
        }
        return false;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = Math.abs((int) (player.hitbox.x - this.hitbox.x));
        if (absValue <= attackDistance) {
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return active;
    }

}
