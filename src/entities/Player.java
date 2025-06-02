/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import gameStates.GameState;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import main.Game;
import weapons.*;
import utils.LoadSave;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

/**
 *
 * @author Juan Pablo
 */
public class Player extends Entity {

    private BufferedImage playerImg;
    private BufferedImage[][] animations;
    private Arms arms;
    private int currentPlayerAction = IDDLE;
    private int aniTick, aniIndex, aniSpeed = 15;
    private boolean moving = false;
    private boolean left, up, right, down, jump;
    private static final float GRAVITY = 0.04f;
    private float playerSpeed = 2.0f;
    private Weapon currentWeapon;
    private Crosshair crosshair;
    private int[][] levelData;
    // Hitbox
    private float xDrawOffset = 42 * Game.SCALE;
    private float yDrawOffset = 1 * Game.SCALE;
    private float xRightDrawOffset = 22 * Game.SCALE;
    // Gravedad y salto
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.2f * Game.SCALE;
    private boolean inAir = false;
    // Ventana de vida, municion y escudo -> por implementar xd
    private float statusUIScale = 1.5f;
    private BufferedImage statsHud;
    private int statusHudHeight = (int) (72 * Game.SCALE * statusUIScale);
    private int statusHudWidth = (int) (144 * Game.SCALE * 1.5f);
    private int statusHudX = (int) (10 * Game.SCALE);
    private int statusHudY = (int) (10 * Game.SCALE);
    // Variables para la vida del jugador
    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    // Icono de corazón
    private BufferedImage healthIcon;
    private int hearthIconHeight = (int) (15 * Game.SCALE * statusUIScale);
    private int hearthIconWidth = (int) (14 * Game.SCALE * statusUIScale);
    private int hearthIconX = (int) (128 * Game.SCALE);
    private int hearthIconY = (int) (35 * Game.SCALE);
    private int healthTextX = (int) (153 * Game.SCALE);
    private int healthTextY = (int) (52 * Game.SCALE);
    // Icono de munición
    private BufferedImage ammoIcon;
    private int ammoIconHeight = (int) (8 * Game.SCALE * statusUIScale);
    private int ammoIconWidth = (int) (11 * Game.SCALE * statusUIScale);
    private int ammoIconX = (int) (15 * Game.SCALE);
    private int ammoIconY = (int) (9 * Game.SCALE);
    private int ammoTextX = (int) (40 * Game.SCALE);
    private int ammoTextY = (int) (20 * Game.SCALE);
    // Armadura (Este si no lo alcanzo a implementar teacher XD)
    private BufferedImage armorIcon;
    private int armorIconHeight = (int) (16 * Game.SCALE * statusUIScale);
    private int armorIconWidth = (int) (15 * Game.SCALE * statusUIScale);
    private int armorIconX = (int) (128 * Game.SCALE);
    private int armorIconY = (int) (70 * Game.SCALE);
    private int armorTextX = (int) (165 * Game.SCALE);
    private int armorTextY = (int) (87 * Game.SCALE);

    public Player(float x, float y, float width, float height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (width - xDrawOffset), height);
        this.currentWeapon = new Pistol();
        this.arms = new Arms(hitbox.x
                - xDrawOffset + xRightDrawOffset, hitbox.y);
        this.arms.setActualWeapon(currentWeapon);
        this.crosshair = new Crosshair();
    }

    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePos();
        updateCurrentWeapon();
        arms.update();
    }

    public void loadLvlData(int[][] lvlData) {
        this.levelData = lvlData;
        if (!isEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
    }

    private void setAnimation() {
        int startAni = this.currentPlayerAction;
        if (moving) {
            this.currentPlayerAction = RUNNING;
        } else {
            this.currentPlayerAction = IDDLE;
        }
        if (startAni != this.currentPlayerAction) {
            resetAniTick();
        }
    }

    public void render(Graphics g) {
        g.drawImage(animations[currentPlayerAction][aniIndex],
                (int) (hitbox.x - xDrawOffset + xRightDrawOffset), (int) (this.hitbox.y - yDrawOffset), 64, 64, null);
        this.arms.render((Graphics2D) g);
        renderBullets((Graphics2D) g);
        crosshair.render((Graphics2D) g);
        this.drawHitbox(g);
        drawUI(g);
    }

    public void changeHealth(int value) {
        currentHealth += value;
        if (currentHealth <= 0) {
            currentHealth = 0;
            GameState.state = GameState.GAME_OVER;
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    private void loadAnimations() {
        this.playerImg = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
        int cols = this.playerImg.getWidth() / 64;
        int rows = this.playerImg.getHeight() / 64;

        this.animations = new BufferedImage[cols][rows];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[0].length; j++) {
                animations[i][j] = playerImg.getSubimage(i * 64, j * 64, 64, 64);
            }
        }
        this.statsHud = LoadSave.getSpriteAtlas(LoadSave.STATUS_ATLAS);
        this.healthIcon = LoadSave.getSpriteAtlas(LoadSave.HEALTH_ICON_ATLAS);
        this.ammoIcon = LoadSave.getSpriteAtlas(LoadSave.AMMO_ICON_ATLAS);
        this.armorIcon = LoadSave.getSpriteAtlas(LoadSave.ARMOR_ICON_ATLAS);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex += 1;
            if (aniIndex
                    >= GetSpriteAmmount(currentPlayerAction)) {
                aniIndex = 0;
            }
        }
    }

    private void updatePos() {
        moving = false;
        if (jump) {
            jump();
        }
        if (!left && !right && !inAir) {
            return;
        }
        float xSpeed = 0, ySpeed = 0;
        if (left) {
            xSpeed -= this.playerSpeed;
        } else if (right) {
            xSpeed += this.playerSpeed;
        }
        if (!inAir) {
            if (!isEntityOnFloor(hitbox, this.levelData)) {
                inAir = true;
            }
        }
        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed,
                    hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    public void shoot() {
        this.arms.setShooting(true);
        this.currentWeapon.shoot(this.arms.getGunX(), this.arms.getGunY(), this.arms.getAngle());
    }

    public void updateArms(int x, int y) {
        this.arms.setAngle(x, y);
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirBooleans() {
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
    }

    private void resetAniTick() {
        this.aniTick = 0;
        this.aniIndex = 0;
    }

    private void updateCurrentWeapon() {
        for (Bullet b : this.currentWeapon.getBullets()) {
            {
                b.update();
            }
        }

    }

    private void renderBullets(Graphics2D graphics2D) {
   
            for (Bullet b : this.currentWeapon.getBullets()) {
                b.render(graphics2D);
            }
        
    }

    public void reload() {
        this.currentWeapon.reload(currentWeapon.getMaxAmmo());
    }

    public void updateCrosshair(int mouseX, int mouseY) {
        this.crosshair.update(this.hitbox.x, this.hitbox.y, mouseX, mouseY);
    }

    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            this.hitbox.x += xSpeed;
            arms.updatePos(this.hitbox.x - xDrawOffset + xRightDrawOffset, this.hitbox.y);
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
        arms.updatePos(this.hitbox.x - xDrawOffset + xRightDrawOffset, this.hitbox.y);
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0f;
    }

    private void jump() {
        if (inAir) {
            return;
        } else {
            inAir = true;
            airSpeed = jumpSpeed;
        }
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    private void drawUI(Graphics g) {
        Font systemFont = new Font("Courier New", Font.BOLD, (int) (16 * Game.SCALE));
        g.drawImage(statsHud, statusHudX, statusHudY, statusHudWidth, statusHudHeight, null);
        g.drawImage(healthIcon, hearthIconX + statusHudX,
                hearthIconY + statusHudY,
                hearthIconWidth, hearthIconHeight, null);
        g.drawImage(ammoIcon, ammoIconX + statusHudX,
                ammoIconY + statusHudY,
                ammoIconWidth, ammoIconHeight, null);
        g.drawImage(armorIcon, armorIconX + statusHudX,
                armorIconY + statusHudY,
                armorIconWidth, armorIconHeight, null);
        g.setFont(systemFont);
        float percentage = (currentHealth * 100f) / maxHealth;
        String formatted = String.format("%.1f%%", percentage);
        g.setColor(Color.WHITE);
        g.drawString(this.currentWeapon.getAmmo() + "/"
                + this.currentWeapon.getMaxAmmo(),
                ammoTextX + statusHudX, ammoTextY + statusHudY);
        g.drawString("--", armorTextX + statusHudX, armorTextY + statusHudY);
        g.drawString(formatted, healthTextX + statusHudX, healthTextY + statusHudY);

    }
    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        moving = false;
        this.currentWeapon = new Pistol();
        this.currentHealth = maxHealth;
        this.currentPlayerAction = IDDLE;
    }

}
