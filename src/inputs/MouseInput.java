/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import gameStates.GameState;
import static gameStates.GameState.MENU;
import static gameStates.GameState.PLAYING;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import main.GamePanel;

/**
 *
 * @author juanp
 */
public class MouseInput implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    public MouseInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING:
                this.gamePanel.getGame().getPlaying().mouseClicked(e);
            case MENU:
                this.gamePanel.getGame().getMenu().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING:
                this.gamePanel.getGame().getPlaying().mouseMoved(e);
            case MENU:
                this.gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            default:
                break;
        }
    }

}
