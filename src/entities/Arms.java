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
    private int aniTick, aniIndex, aniSpeed = 15;
    private static int ARM_OFFSET_X = 19, ARM_OFFSET_Y = 21;
    private boolean shooting;

    public Arms(float x, float y) {
        this.x = x;
        this.y = y;
        this.pivot = new Point();
        this.pivot.x = (int) x + ARM_OFFSET_X;
        this.pivot.y = (int) y + ARM_OFFSET_Y;
        importImg();
        loadAnimations();
    }

    public void updatePos(float playerX, float playerY) {
        this.x = playerX;
        this.y = playerY;

        // Asignar el nuevo pivote de forma absoluta (no acumular)
        this.pivot.x = (int) playerX + ARM_OFFSET_X;
        this.pivot.y = (int) playerY + ARM_OFFSET_Y;
    }

    private void setAnimation() {
        int startAni = this.currentWeaponAction;
        if (shooting) {
            this.currentWeaponAction = WeaponConstants.SHOOTING;
        } else {
            this.currentWeaponAction = WeaponConstants.IDDLE;
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
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/resources/arms/p1/p1_pistol.png");
        try {
            this.armsImg = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void render(Graphics2D g2d) {
        AffineTransform old = g2d.getTransform();

        g2d.rotate(this.angle, pivot.x, pivot.y);

        // Dibuja el brazo "compensado" desde el pivote hacia atrás
        g2d.drawImage(
                this.animations[currentWeaponAction][aniIndex],
                (int) (pivot.x),
                (int) (pivot.y),
                null
        );

        g2d.setTransform(old);
    }
}
