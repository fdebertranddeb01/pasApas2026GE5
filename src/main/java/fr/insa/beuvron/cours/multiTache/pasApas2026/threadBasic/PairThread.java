/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.threadBasic;

/**
 *
 * @author fdebertranddeb01
 */
public class PairThread extends Thread {
    
    @Override
    public void run() {
        for (int i = 0 ; i < 10 ; i += 2) {
            System.out.println(i);
        }
        
    }
    
    public static void main(String[] args) {
        PairThread p1 = new PairThread();
        p1.start();
        PairThread p2 = new PairThread();
        p2.start();
        System.out.println("fini");
    }
    
}
