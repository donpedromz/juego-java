/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Graphics;
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
    private int currentPlayerAction = PlayerConstants.IDDLE;
    private int aniTick, aniIndex, aniSpeed = 15;
    private boolean moving = false;
    private int playerDir = -1;

    public Player(float x, float y) {
        super(x, y);
        importImg();
        loadAnimations();
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        this.moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePos();
    }

    private void setAnimation() {
        if (moving) {
            this.currentPlayerAction = PlayerConstants.RUNNING;
        } else {
            this.currentPlayerAction = PlayerConstants.IDDLE;
        }
    }

    public void render(Graphics g) {
        g.drawImage(animations[currentPlayerAction][aniIndex],
                (int) this.x, (int) this.y, 128, 128, null);
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
        if (moving) {
            switch (this.playerDir) {
                case Directions.LEFT:
                    this.x -= 5;
                    break;
                case Directions.DOWN:
                    this.y += 10;
                    break;
                case Directions.RIGHT:
                    this.x += 5;
                    break;
                case Directions.UP:
                    this.y -= 10;
                    break;

            }
        }
    }

}
