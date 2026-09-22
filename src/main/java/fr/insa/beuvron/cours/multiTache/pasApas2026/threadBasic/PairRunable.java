/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.threadBasic;

/**
 *
 * @author fdebertranddeb01
 */
public class PairRunable implements Runnable {
    
    @Override
    public void run() {
        for (int i = 0; i < 10; i += 2) {
            Thread cur = Thread.currentThread();
            System.out.println("Thead courant : "+cur.getName());
            try {
                Thread.sleep((int) (Math.random()*5000));
            } catch (InterruptedException ex) {
                throw new Error("impossible" , ex);
            }
            System.out.println(i);
        }

    }

    public static void main(String[] args) {
        Thread p1 = new Thread(new PairRunable());
        p1.start();
        Thread p2 = new Thread(new PairRunable());
        p2.start();
        try {
            p1.join();
        } catch (InterruptedException ex) {
            throw new Error("normalement impossible", ex);
        }
        try {
            p2.join();
        } catch (InterruptedException ex) {
            throw new Error("normalement impossible", ex);
        }
        System.out.println("fini");
    }

}
