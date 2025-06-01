/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import weapons.*;
import main.GamePanel;
import utils.LoadSave;
import static utils.constants.PlayerConstants;
import static utils.HelpMethods.canMoveHere;

/**
 *
 * @author Juan Pablo
 */
public class Player extends Entity {

    private BufferedImage playerImg;
    private BufferedImage[][] animations;
    private Arms arms;
    private int currentPlayerAction = PlayerConstants.IDDLE;
    private int aniTick, aniIndex, aniSpeed = 15;
    private boolean moving = false;
    private boolean left, up, right, down, jump;
    private float airSpeed = 0f;
    private static final float GRAVITY = 0.04f;
    private float playerSpeed = 2.0f;
    private List<Weapon> ownedWeapons;
    private Weapon currentWeapon;
    private List<Bullet> bullets;
    private Crosshair crosshair;
    private int[][] levelData;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        this.ownedWeapons = new ArrayList<>();
        this.currentWeapon = new Pistol();
        this.ownedWeapons.add(currentWeapon);
        this.arms = new Arms(0, 0);
        this.arms.setActualWeapon(currentWeapon);
        this.crosshair = new Crosshair();
    }

    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePos();
        updateWeapons();
        arms.update();
        this.updateHitbox();
    }

    public void loadLvlData(int[][] lvlData) {
        this.levelData = lvlData;
    }

    public void pickUpWeapon(Weapon w) {
        if (!this.ownedWeapons.contains(w)) {
            ownedWeapons.add(w);
        }
        this.currentWeapon = w;
    }

    public void switchWeapon(int index) {
        if (index >= 0 && index < ownedWeapons.size()) {
            currentWeapon = ownedWeapons.get(index);
        }
    }

    private void setAnimation() {
        int startAni = this.currentPlayerAction;
        if (moving) {
            this.currentPlayerAction = PlayerConstants.RUNNING;
        } else {
            this.currentPlayerAction = PlayerConstants.IDDLE;
        }
        if (startAni != this.currentPlayerAction) {
            resetAniTick();
        }
    }

    public void render(Graphics g) {
        g.drawImage(animations[currentPlayerAction][aniIndex],
                (int) this.x, (int) this.y, 64, 64, null);
        this.arms.render((Graphics2D) g);
        renderBullets((Graphics2D) g);
        crosshair.render((Graphics2D) g);
        this.drawHitbox(g);
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
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex += 1;
            if (aniIndex
                    >= PlayerConstants.GetSpriteAmmount(currentPlayerAction)) {
                aniIndex = 0;
            }
        }
    }

    private void updatePos() {
        moving = false;
        if (!left && !right && !up && !down) {
            return;
        }
        float xSpeed = 0, ySpeed = 0;
        if (left && !right) {
            xSpeed = -this.playerSpeed;

            arms.updatePos(x, y);
        } else if (right && !left) {
            xSpeed = this.playerSpeed;
        }

        if (up && !down) {
            ySpeed = -this.playerSpeed;
        } else if (down && !up) {
            ySpeed = this.playerSpeed;

        }
        if (canMoveHere(x + xSpeed, y + ySpeed, width, height, levelData)) {
            this.x += xSpeed;
            this.y += ySpeed;
            arms.updatePos(x, y);
            moving = true;
        }

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

    private void updateWeapons() {
        for (Weapon w : this.ownedWeapons) {
            for (Bullet b : w.getBullets()) {
                {
                    b.update();
                }
            }
        }

    }

    private void renderBullets(Graphics2D graphics2D) {
        for (Weapon w : this.ownedWeapons) {
            for (Bullet b : w.getBullets()) {
                b.render(graphics2D);
            }
        }
    }

    public void reload() {
        this.currentWeapon.reload(5);
    }

    public void updateCrosshair(int mouseX, int mouseY) {
        this.crosshair.update(this.x, this.y, mouseX, mouseY);
    }
}
