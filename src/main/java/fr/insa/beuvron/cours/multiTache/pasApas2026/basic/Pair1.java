/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.basic;

/**
 *
 * @author fdebertranddeb01
 */
public class Pair1 {
    
    public static void affPair(int n) {
        for (int i = 0; i < n ; i += 2) {
            System.out.println(i);
        }
    }
    
    public static void main(String[] args) {
        affPair(10);
    }
    
}
