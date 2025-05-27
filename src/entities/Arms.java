/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import static utils.constants.WeaponConstants;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.GamePanel;
import weapons.Weapon;
import utils.LoadSave;

/**
 *
 * @author juanp
 */
public class Arms {

    private BufferedImage[][] animations;
    private BufferedImage armsImg;
    private float x, y;
    private Point pivot;
    private double angle;
    private int currentWeaponAction;
    int armLength;    // Largo horizontal del sprite
    int armVerticalOffset;
    private int aniTick, aniIndex, aniSpeed = 6;
    float gunX;
    float gunY;
    private static int ARM_OFFSET_X = 24, ARM_OFFSET_Y = 23;
    private boolean shooting;
    private long lastShot;
    private Weapon actualWeapon;

    public double getAngle() {
        return angle;
    }

    public Arms(float x, float y) {
        this.x = x;
        this.y = y;
        this.pivot = new Point();
        this.pivot.x = (int) x + ARM_OFFSET_X;
        this.pivot.y = (int) y + ARM_OFFSET_Y;
        importImg();
        loadAnimations();
        this.gunX = (float) (pivot.x + Math.cos(angle) * armLength);
        this.gunY = (float) (float) (pivot.y + Math.sin(angle) * armVerticalOffset);

    }

    public void update() {
        setAnimation();
        updateAnimationTick();
    }

    public void updatePos(float playerX, float playerY) {
        this.x = playerX;
        this.y = playerY;
        // Asignar el nuevo pivote de forma absoluta (no acumular)
        this.pivot.x = (int) playerX + ARM_OFFSET_X;
        this.pivot.y = (int) playerY + ARM_OFFSET_Y;
        this.gunX = (float) (pivot.x + Math.cos(angle) * armLength);
        this.gunY = (float) (pivot.y + Math.sin(angle) * armVerticalOffset);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex += 1;
            if (aniIndex
                    >= WeaponConstants.GetSpriteAmmount(currentWeaponAction)) {
                aniIndex = 0;
            }
        }
    }

    public void setAnimation() {
        int startAni = this.currentWeaponAction;
        if (shooting && System.currentTimeMillis()
                - actualWeapon.getLastShotTime() < 300) {
            this.currentWeaponAction = WeaponConstants.SHOOTING;
        } else {
            this.currentWeaponAction = WeaponConstants.IDDLE;
            this.shooting = false;
        }
        if (startAni != this.currentWeaponAction) {
            this.aniTick = 0;
            this.aniIndex = 0;
        }
    }

    private void loadAnimations() {
        int cols = this.armsImg.getWidth() / 65;
        int rows = this.armsImg.getHeight() / 25;
        this.animations = new BufferedImage[cols][rows];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[0].length; j++) {
                animations[i][j] = armsImg.getSubimage(i * 65, j * 25, 65, 25);
            }
        }
        this.armLength = animations[0][0].getWidth();
        this.armVerticalOffset = animations[0][0].getHeight();
    }

    private void importImg() {
        this.armsImg = LoadSave.getSpriteAtlas(LoadSave.ARMS_ATLAS);
    }

    public void setActualWeapon(Weapon actualWeapon) {
        this.actualWeapon = actualWeapon;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public void setAngle(int x, int y) {
        double dx = x - this.x;
        double dy = y - this.y;
        if (dx == 0 && dy == 0) {
            return;
        } else {
            double newAngle = Math.atan2(dy, dx);
            this.angle = newAngle;
        }
    }

    public float getGunX() {
        return gunX;
    }

    public float getGunY() {
        return gunY;
    }

    public void render(Graphics2D g2d) {
        AffineTransform old = g2d.getTransform();
        g2d.rotate(this.angle, pivot.x, pivot.y);
        // Dibuja el brazo "compensado" desde el pivote hacia atrás
        g2d.drawImage(
                this.animations[currentWeaponAction][aniIndex],
                (int) (pivot.x) - 5,
                (int) (pivot.y) - 5,
                null
        );

        g2d.setTransform(old);
    }
}
