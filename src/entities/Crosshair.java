/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author juanp
 */
public class Crosshair {

    private int separation;
    private int minSeparation = 5;
    private int maxSeparation = 50;
    private int lineLength = 5;
    private int mouseX;
    private int mouseY;
    private Color color = Color.BLACK;

    public Crosshair() {

    }

    public void update(float playerX, float playerY, int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        double dx = this.mouseX - playerX;
        double dy = this.mouseY - playerY;
        double distance = Math.sqrt((dx * dx) + (dy * dy));
        this.separation = (int) Math.min(maxSeparation, Math.max(minSeparation, distance / 10));
    }

    public void render(Graphics2D g2d) {
        g2d.setColor(color);
        // Líneas: arriba, abajo, izquierda, derecha
        g2d.drawLine(mouseX, mouseY - separation - lineLength, mouseX, mouseY - separation);
        g2d.drawLine(mouseX, mouseY + separation, mouseX, mouseY + separation + lineLength);
        g2d.drawLine(mouseX - separation - lineLength, mouseY, mouseX - separation, mouseY);
        g2d.drawLine(mouseX + separation, mouseY, mouseX + separation + lineLength, mouseY);
    }
     public void setColor(Color color) {
        this.color = color;
    }

    public void setLineLength(int length) {
        this.lineLength = length;
    }

    public void setLimits(int min, int max) {
        this.minSeparation = min;
        this.maxSeparation = max;
    }
}
