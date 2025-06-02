/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import main.Game;

/**
 *
 * @author juanp
 */
public class Constants {

    public static class EnemyConstants {

        public static final int PENDEJO = 0;
        public static final int IDDLE = 3;
        public static final int WALK = 4;
        public static final int ATTACK = 0;
        public static final int HURT = 2;
        public static final int DEATH = 1;
        public static final int PENDEJO_ANCHO_ESTANDAR = 48;
        public static final int PENDEJO_ALTO_ESTANDAR = 48;
        public static final int PENDEJO_ANCHO = (int) (Game.SCALE * PENDEJO_ANCHO_ESTANDAR*2);
        public static final int PENDEJO_ALTO = (int) (Game.SCALE * PENDEJO_ALTO_ESTANDAR*2);
        public static final int PENDEJO_xDRAW_OFFSET = (int) (Game.SCALE * 50);
        public static final int PENDEJO_YDRAW_OFFSET = (int) (Game.SCALE * 20);
        public static int getSpritAmmount(int enemy_type, int enemy_state){
            switch(enemy_type){
                // Cantidad de animaciones del Psendejo
                case PENDEJO:
                    switch(enemy_state){
                        case IDDLE:
                            return 4;
                        case WALK:
                            return 6;
                        case ATTACK: 
                            return 6;
                        case HURT:
                            return 2;
                        case DEATH:
                            return 6;
                    }
                            
            }
            return 0;
        }

    }

    public static class Directions {

        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {

        public static final int RUNNING = 0;
        public static final int IDDLE = 1;

        public static int GetSpriteAmmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 7;
                case IDDLE:
                    return 3;
                default:
                    return 1;
            }
        }
    }

    public static class WeaponConstants {

        public static final int IDDLE = 0;
        public static final int SHOOTING = 1;

        public static int GetSpriteAmmount(int weapon_action) {
            switch (weapon_action) {
                case IDDLE:
                    return 1;
                case SHOOTING:
                    return 11;
                default:
                    return 1;
            }
        }
    }
}
