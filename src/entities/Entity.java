/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author juanp
 */
public abstract class Entity {
    protected float x,y;
    protected float width, height;
    protected Rectangle2D.Float hitbox;
    public Entity(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initHitbox(float x, float y, float width, float height) {
        this.hitbox = new Rectangle2D.Float(x,y,width,height);
    }
    protected void drawHitbox(Graphics g){
        g.setColor(Color.red);
        g.drawRect((int)hitbox.x,(int)hitbox.y, (int)hitbox.width,(int)hitbox.height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }


    
}
