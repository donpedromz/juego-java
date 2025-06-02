/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weapons;

import entities.Bullet;
import java.util.ArrayList;

/**
 *
 * @author juanp
 */
public class Pistol extends Weapon {

    public Pistol() {
        super("pistol_shot","pistol_ricochet","pistol_empty","pistol_reload");
        this.ammo = this.maxAmmo = 12;
        this.fireRate = 1000;
        this.bullets = new ArrayList<>();
    }
    @Override
    public void reload(int ammo){
        this.ammo = ammo;
        playReloadSound();
    }
    @Override
    public void shoot(float x, float y, double angle) {
        if (!canShoot()) {
            if(!isMagEmpty()){
                playEmptySound();
            }
            return;
        }
        this.bullets.add(new Bullet(x, y, angle));
        this.lastShotTime = System.currentTimeMillis();
        this.ammo--;
        playShootSound();
    }

}
