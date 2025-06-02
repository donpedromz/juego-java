/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author juanp
 */
public class Bullet {
    private float x,y;
    private float dx,dy;
    private float speed = 50f;
    private float trailLength = 25f;
    private Rectangle2D hitbox;
    private int width = 4;
    private double angle;
    private boolean active = true;
    public Bullet(float startX, float startY, double angle){
        this.x = startX;
        this.y = startY;
        this.dx = (float) Math.cos(angle) * speed;
        this.dy = (float) Math.sin(angle) * speed;
        this.angle = angle;
        this.hitbox = new Rectangle2D.Float(x, y, 4, 4);
    }
    public void update(){
        x += dx;
        y += dy;
        this.hitbox.setFrame(x,y,4,4);
    }
    public void render(Graphics2D g2d){
        if(!active) return;
        AffineTransform old = g2d.getTransform();
        g2d.translate(x, y);
        g2d.rotate(angle);
        g2d.setColor(Color.YELLOW);g2d.fillRect(0,-width / 2, (int) trailLength, width);
        g2d.setTransform(old);
    }

    public Rectangle2D getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }
    public void deactivate(){
        this.active = false;
    }
}
