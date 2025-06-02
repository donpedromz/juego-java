/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Game;

/**
 *
 * @author juanp
 */
public class State implements StateMethods{
    protected Game game;
    public State(Game game){
        this.game = game;
    }
    @Override
    public void update() {
        return;
    }

    @Override
    public void draw(Graphics g) {
        return;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        return;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        return;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        return;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        return;
    }
    
    
}
