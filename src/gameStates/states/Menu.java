package gameStates.states;

import gameStates.State;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import main.Game;
import entities.Particle;
import gameStates.GameState;

public class Menu extends State {

    private ArrayList<Particle> particles;
    private long lastBlink = 0;
    private boolean showText = true;
    private Color bgColor = new Color(30, 30, 30);
    private float hue = 0f;

    public Menu(Game game) {
        super(game);
        particles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            particles.add(new Particle());
        }
    }

    public void update() {
        for (Particle p : particles) {
            p.tick();
        }

        // Parpadeo cada 500ms
        if (System.currentTimeMillis() - lastBlink > 500) {
            showText = !showText;
            lastBlink = System.currentTimeMillis();
        }

        // Color de fondo dinámico
        hue += 0.001f;
        if (hue > 1) hue = 0;
        bgColor = Color.getHSBColor(hue, 0.5f, 0.2f);
    }

    @Override
    public void draw(Graphics g) {
        // Fondo
        g.setColor(bgColor);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGTH);

        // Dibujar partículas
        for (Particle p : particles) {
            p.render(g);
        }

        // Título
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        drawCenteredString(g, "MENÚ PRINCIPAL", Game.GAME_WIDTH, 150);

        // Instrucciones
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        if (showText) {
            drawCenteredString(g, "Presiona ENTER para comenzar", Game.GAME_WIDTH, 250);
        }
        drawCenteredString(g, "Presiona ESC para salir", Game.GAME_WIDTH, 300);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            GameState.state = GameState.PLAYING;
        } else if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    private void drawCenteredString(Graphics g, String text, int width, int y) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (width - metrics.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }
}
