/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.enemies;

import entities.Enemy;
import static utils.Constants.EnemyConstants.*;
/**
 *
 * @author juanp
 */
public class Pendejo extends Enemy{
    
    public Pendejo(float x, float y) {
        super(x, y, PENDEJO_ANCHO, PENDEJO_ALTO, PENDEJO);
        initHitbox(x, y - PENDEJO_ALTO, width, height);
    }
}
