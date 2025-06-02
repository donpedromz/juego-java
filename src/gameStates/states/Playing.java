/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameStates.states;

import entities.EnemyManager;
import entities.Player;
import gameStates.State;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import levels.LevelManager;
import main.Game;

/**
 *
 * @author juanp
 */
public class Playing extends State {

    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        this.levelManager = new LevelManager(this.game);
        this.enemyManager = new EnemyManager(this);
        this.player = new Player(500, 200, 64, 64);
        this.player.loadLvlData(
                levelManager.getLvl1().getLvlData());

    }
    @Override
    public void update(){
        this.player.update();
        this.levelManager.update();
        this.enemyManager.update(this.levelManager.
                getLvl1().getLvlData());
    }
    @Override
    public void draw(Graphics g){
        this.player.render(g);
        this.levelManager.draw(g);
        this.enemyManager.draw(g);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.player.reload();
                break;
            case KeyEvent.VK_A:
                this.player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                this.player.setRight(true);
                break;
            case KeyEvent.VK_S:
                this.player.setDown(true);
                break;
            case KeyEvent.VK_W:
                this.player.setUp(true);
                break;
            case KeyEvent.VK_SPACE:
                this.player.setJump(true);
            default:
                return;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                this.player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                this.player.setRight(false);
                break;
            case KeyEvent.VK_S:
                this.player.setDown(false);
                break;
            case KeyEvent.VK_W:
                this.player.setUp(false);
                break;
            case KeyEvent.VK_SPACE:
                this.player.setJump(false);
            default:
                return;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.player.shoot();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.player.updateArms(e.getX(), e.getY());
        this.player.updateCrosshair(e.getX(), e.getY());
    }

    public Player getPlayer() {
        return player;
    }
    
}
