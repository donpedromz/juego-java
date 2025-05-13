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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.GamePanel;
import static utils.constants.PlayerConstants;
import static utils.constants.Directions;

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
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        importImg();
        loadAnimations();
        this.arms = new Arms(0,0);
    }

    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePos();
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
    }

    private void loadAnimations() {
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

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/resources/p1.png");
        try {
            this.playerImg = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updatePos() {
        moving = false;
        if (left && !right) {
            this.x -= this.playerSpeed;
            arms.updatePos(x, y);
            this.moving = true;
        } else if (right && !left) {
            this.x += this.playerSpeed;
            arms.updatePos(x, y);
            this.moving = true;
        }

        if (up && !down) {
            this.y -= this.playerSpeed;
            arms.updatePos(x, y);
            this.moving = true;
        } else if (down && !up) {
            this.y += this.playerSpeed;
            arms.updatePos(x, y);
            this.moving = true;
        }

    }

    public void setArmsAngle(int x, int y) {
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

}
