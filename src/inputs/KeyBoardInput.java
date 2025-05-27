/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;
import static utils.constants.Directions;

/**
 *
 * @author juanp
 */
public class KeyBoardInput implements KeyListener {

    private GamePanel gamePanel;

    public KeyBoardInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.gamePanel.getGame().getPlayer().reload();
                break;
            case KeyEvent.VK_A:
                this.gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_D:
                this.gamePanel.
                        getGame().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_S:
                this.gamePanel.
                        getGame().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_W:
                this.gamePanel.
                        getGame().getPlayer().setUp(true);
                break;

            default:
                return;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                this.gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_D:
                this.gamePanel.
                        getGame().getPlayer().setRight(false);
                break;
            case KeyEvent.VK_S:
                this.gamePanel.
                        getGame().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_W:
                this.gamePanel.
                        getGame().getPlayer().setUp(false);
                break;

            default:
                return;

        }
    }

}
