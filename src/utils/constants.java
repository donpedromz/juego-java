/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author juanp
 */
public class constants {
    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants{
        public static final int RUNNING = 0;
        public static final int IDDLE = 1;
        public static int GetSpriteAmmount(int player_action){
            switch(player_action){
                case RUNNING:
                    return 7;
                case IDDLE:
                    return 3;
                default:
                    return 1;
            }
        }
    }
    public static class WeaponConstants{
        public static final int IDDLE = 0;
        public static final int SHOOTING = 1;
           public static int GetSpriteAmmount(int weapon_action){
            switch(weapon_action){
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
