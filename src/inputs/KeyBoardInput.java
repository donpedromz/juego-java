/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;
import gameStates.GameState;
import static gameStates.GameState.GAME_OVER;
import static gameStates.GameState.MENU;
import static gameStates.GameState.PAUSED;
import static gameStates.GameState.PLAYING;

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
        switch (GameState.state) {
            case PLAYING:
                this.gamePanel.getGame().getPlaying().keyPressed(e);
            case MENU:
                this.gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case GAME_OVER:
                this.gamePanel.getGame().getGameOver().keyPressed(e);
                break;
            case PAUSED:
                this.gamePanel.getGame().getPaused().keyPressed(e);
                break;
            case GAME_WON:
                this.gamePanel.getGame().getWon().keyReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case PLAYING:
                this.gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            case MENU:
                this.gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case GAME_OVER:
                this.gamePanel.getGame().getGameOver().keyReleased(e);
                break;
            case PAUSED:
                this.gamePanel.getGame().getPaused().keyReleased(e);
               break;
            case GAME_WON:
             this.gamePanel.getGame().getWon().keyReleased(e);
             break;
            default:
                break;
        }
    }

}
