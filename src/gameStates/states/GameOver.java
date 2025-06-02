/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameStates.states;

import gameStates.GameState;
import gameStates.State;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;
import main.Game;

/**
 *
 * @author juanp
 */

public class GameOver extends State {

    private final Random random;

    public GameOver(Game game) {
        super(game);
        random = new Random();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Fondo base negro
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGTH);

        // "Fuego caótico" - círculos brillantes aleatorios
        for (int i = 0; i < 80; i++) {
            int x = random.nextInt(Game.GAME_WIDTH);
            int y = random.nextInt(Game.GAME_HEIGTH);
            int size = 10 + random.nextInt(30);
            Color fireColor = new Color(
                    255,
                    random.nextInt(100),
                    0,
                    150 + random.nextInt(100)
            );
            g2.setColor(fireColor);
            g2.fillOval(x, y, size, size);
        }

        // Sombra del texto
        String title = "¡HAS MUERTO!";
        g2.setFont(new Font("Arial", Font.BOLD, 64));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(title);
        int x = (Game.GAME_WIDTH - textWidth) / 2;
        int y = Game.GAME_HEIGTH / 2;

        g2.setColor(Color.DARK_GRAY);
        g2.drawString(title, x + 3, y + 3); // sombra
        g2.setColor(Color.RED);
        g2.drawString(title, x, y);         // texto principal

        // Subtexto
        g2.setFont(new Font("Arial", Font.PLAIN, 28));
        String retry = "Presiona 'R' para reintentar";
        int retryWidth = g2.getFontMetrics().stringWidth(retry);
        g2.setColor(Color.ORANGE);
        g2.drawString(retry, (Game.GAME_WIDTH - retryWidth) / 2, y + 60);
    }

    @Override
    public void update() {
        // Aquí puedes agregar más animaciones si deseas (como partículas)
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            resetAll();
        }
    }

    private void resetAll() {
        game.getPlaying().getPlayer().resetAll();
        game.getPlaying().getEnemyManager().resetAllEnemies();
        GameState.state = GameState.PLAYING;
    }

}
