package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Particle {
    private float x, y;       // Posición
    private float dx, dy;     // Velocidad
    private Color color;      // Color
    private static final int SIZE = 3;
    private static final Random rand = new Random();

    // Constructor personalizado
    public Particle(float x, float y, float dx, float dy, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    // Constructor aleatorio (por compatibilidad o pruebas)
    public Particle() {
        reset();
    }

    public void tick() {
        x += dx;
        y += dy;

        // Opcional: fuera de límites, puede reciclarse o morir
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            reset(); // O simplemente: marcar como inactiva
        }
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, SIZE, SIZE);
    }

    // Reset aleatorio
    private void reset() {
        x = rand.nextInt(800);
        y = rand.nextInt(600);
        dx = -1 + rand.nextFloat() * 2;
        dy = -1 + rand.nextFloat() * 2;
        color = new Color(
            200 + rand.nextInt(55), // Rojizo intenso
            rand.nextInt(100),      // Verde bajo
            0,                      // Sin azul para fuego
            150                     // Transparencia
        );
    }
}
