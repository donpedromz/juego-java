package gameStates.states;

import gameStates.State;
import gameStates.GameState;
import main.Game;
import entities.Particle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameWon extends State {

    private String winMessage = "¡MISIÓN CUMPLIDA!";
    private Font titleFont = new Font("Arial", Font.BOLD, 60);
    private Color textColor = Color.YELLOW;

    private ArrayList<Particle> particles;
    private Random random;

    public GameWon(Game game) {
        super(game);
        particles = new ArrayList<>();
        random = new Random();

        // Generar partículas iniciales
        for (int i = 0; i < 150; i++) {
            particles.add(new Particle(
                    random.nextInt(Game.GAME_WIDTH),
                    random.nextInt(Game.GAME_HEIGTH),
                    -1 + random.nextFloat() * 2,
                    -1 + random.nextFloat() * 2,
                    new Color(255, random.nextInt(180), 0, 150)
            ));
        }
    }

    @Override
    public void update() {
        for (Particle p : particles) {
            p.tick();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGTH);

        for (Particle p : particles) {
            p.render(g);
        }

        g.setFont(titleFont);
        g.setColor(textColor);
        drawCenteredString(g, winMessage, Game.GAME_WIDTH, Game.GAME_HEIGTH / 2);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Presiona R para reiniciar o ESC para salir", 20, Game.GAME_HEIGTH - 30);
    }

    private void drawCenteredString(Graphics g, String text, int width, int y) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (width - metrics.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_R) {
            resetAll();
            GameState.state = GameState.PLAYING;
        } else if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    private void resetAll() {
        game.getPlaying().getPlayer().resetAll();
        game.getPlaying().getEnemyManager().resetAllEnemies();
        GameState.state = GameState.PLAYING;
    }
}
