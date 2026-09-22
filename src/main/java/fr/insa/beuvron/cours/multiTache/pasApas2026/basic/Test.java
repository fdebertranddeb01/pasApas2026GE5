/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.basic;

/**
 *
 * @author fdebertranddeb01
 */
public class Test {
    
    public static void main(String[] args) {
        // Pair1.affPair(5);
        
        int truc = 3;
        Figure f1 = new Figure(){
            @Override
            public double minX() {
                System.out.println("truc = " + truc);
                return 0;
            }

            @Override
            public double largeur() {
                return 0;
            }
            
        };
        f1.minX();
    }
    
}
