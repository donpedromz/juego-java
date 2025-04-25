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
        System.out.println("Tecla presionada");
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setDirection(Directions.UP);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setDirection(Directions.LEFT);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDirection(Directions.DOWN);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setDirection(Directions.RIGHT);
                break;
            default:
                return;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                this.gamePanel.getGame().getPlayer().setMoving(false);
                break;
            case KeyEvent.VK_D:
                this.gamePanel.
                        getGame().getPlayer().setMoving(false);
                break;
            case KeyEvent.VK_S:
                this.gamePanel.
                        getGame().getPlayer().setMoving(false);
                break;
            case KeyEvent.VK_W:
                this.gamePanel.
                        getGame().getPlayer().setMoving(false);
                break;

            default:
                return;

        }
    }

}
