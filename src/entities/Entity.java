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
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initHitbox(float x, float y, float width, float heigth) {
        this.hitbox = new Rectangle2D.Float(x,y,width,height);
    }
    protected void drawHitbox(Graphics g){
        g.setColor(Color.red);
        g.drawRect((int)hitbox.x,(int)hitbox.y, (int)hitbox.width,(int)hitbox.height);
    }
    protected void updateHitbox(){
        hitbox.x = (int) x;
        hitbox.y = (int) y ;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }


    
}
