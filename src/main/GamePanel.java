 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import inputs.KeyBoardInput;
import inputs.MouseInput;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static main.Game.GAME_HEIGTH;
import static main.Game.GAME_WIDTH;

/**
 *
 * @author juanp
 */
public class GamePanel extends JPanel {

    /**
     * Configuraciones de pantalla
     */
    private MouseInput mouseInput;
    private Game game;
    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();
        this.mouseInput = new MouseInput(this);
        this.addKeyListener(new KeyBoardInput(this));
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGTH);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    void updateGame() {

    }
    public Game getGame() {
        return game;
    }
    
}
