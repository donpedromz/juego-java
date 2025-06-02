package gameStates.states;

import entities.Particle;
import gameStates.GameState;
import gameStates.State;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Pause extends State {

    private ArrayList<Particle> particles;
    private float hue = 0f;
    private Color bgColor = new Color(0, 0, 0, 180);
    private final Random random = new Random();

    public Pause(Game game) {
        super(game);
        this.particles = new ArrayList<>();
        generateFireParticles();
    }

    private void generateFireParticles() {
        // Genera partículas simulando fuego caótico
        for (int i = 0; i < 120; i++) {
            int x = random.nextInt(Game.GAME_WIDTH);
            int y = random.nextInt(Game.GAME_HEIGTH);
            particles.add(new Particle(x, y, 1 + random.nextInt(3), 1 + random.nextInt(3), Color.ORANGE));
        }
    }

    public void tick() {
        for (Particle p : particles) {
            p.tick();
        }

        // Generar color rojo-naranja intenso caótico
        hue += 0.005f;
        if (hue > 1f) hue = 0f;
        bgColor = new Color(Color.HSBtoRGB(hue, 1f, 0.4f));
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Fondo dinámico con color intenso
        g2.setColor(bgColor);
        g2.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGTH);

        // Fuego visual: círculos caóticos (además de partículas)
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(Game.GAME_WIDTH);
            int y = random.nextInt(Game.GAME_HEIGTH);
            int size = 20 + random.nextInt(40);
            Color fireColor = new Color(
                255,
                100 + random.nextInt(100),
                random.nextInt(50),
                180
            );
            g2.setColor(fireColor);
            g2.fillOval(x, y, size, size);
        }

        // Partículas
        for (Particle p : particles) {
            p.render(g);
        }

        // Texto principal
        g2.setFont(new Font("Arial", Font.BOLD, 64));
        String title = "PAUSA";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(title, (Game.GAME_WIDTH - titleWidth) / 2 + 4, 140 + 4);
        g2.setColor(Color.RED);
        g2.drawString(title, (Game.GAME_WIDTH - titleWidth) / 2, 140);

        // Subtexto
        g2.setFont(new Font("Arial", Font.PLAIN, 28));
        g2.setColor(Color.ORANGE);
        String resume = "Presiona P para reanudar";
        String exit = "Presiona ESC para salir al menú";
        int resumeWidth = g2.getFontMetrics().stringWidth(resume);
        int exitWidth = g2.getFontMetrics().stringWidth(exit);
        g2.drawString(resume, (Game.GAME_WIDTH - resumeWidth) / 2, 250);
        g2.drawString(exit, (Game.GAME_WIDTH - exitWidth) / 2, 300);
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_P) {
            GameState.state = GameState.PLAYING;
        } else if (key == KeyEvent.VK_ESCAPE) {
            GameState.state = GameState.MENU;
        }
    }
}
