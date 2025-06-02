/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weapons;

import java.util.List;
import entities.Bullet;
import java.util.ArrayList;
import utils.SoundManager;

/**
 *
 * @author juanp
 */
public abstract class Weapon {

    protected int ammo;
    protected int maxAmmo;
    protected long fireRate;
    protected long lastShotTime;
    protected List<Bullet> bullets;
    protected String shootSound;
    protected String ricochetSound;
    protected String emptySound;
    protected String reloadSound;
    public Weapon(String shootSound, String ricochetSound, String emptySound, String reloadSound){
        this.shootSound = shootSound;
        this.ricochetSound = ricochetSound;
        this.emptySound = emptySound;
        this.reloadSound = reloadSound;
    }
    public abstract void shoot(float x, float y, double angle);
    public boolean canShoot() {
        return System.currentTimeMillis() - lastShotTime >= fireRate 
                && this.ammo > 0;
    }
    public boolean isMagEmpty(){
        return this.ammo > 0;
    }
    public void reload(int ammo){
        this.ammo = ammo;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }
    
    public long getFireRate() {
        return fireRate;
    }

    public long getLastShotTime() {
        return lastShotTime;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
    public void playShootSound(){
        SoundManager.playSound(shootSound);
    }
    public void playRicochetSound(){
        SoundManager.playSound(ricochetSound);
    }
    public void playEmptySound(){
        SoundManager.playSound(emptySound);
    }
    public void playReloadSound(){
        SoundManager.playSound(reloadSound);
    }
}
