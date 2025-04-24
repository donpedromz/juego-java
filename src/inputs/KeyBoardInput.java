/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

/**
 *
 * @author juanp
 */
public class KeyBoardInput implements KeyListener {
    private GamePanel gamePanel;
    public KeyBoardInput(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                System.out.println("SALTO");
                this.gamePanel.changeYDelta(-10);
                break;
            case KeyEvent.VK_A:
                System.out.println("IZQUIERDA");
                this.gamePanel.updateRunning();
                this.gamePanel.changeXDelta(-10);
                break;
            case KeyEvent.VK_S:
                System.out.println("AGACHA");
                 this.gamePanel.changeYDelta(10);
                break;
            case KeyEvent.VK_D:
                System.out.println("DERECHA");
                this.gamePanel.updateRunning();
                this.gamePanel.changeXDelta(10);
                break;
            default:
                return;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                this.gamePanel.stop();
                break;
            case KeyEvent.VK_D:
                this.gamePanel.stop();
                break;
            default:
                return;

        }
    }

}
