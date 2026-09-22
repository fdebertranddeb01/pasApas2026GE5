/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.threadBasic;

/**
 *
 * @author fdebertranddeb01
 */
public class PairRunableAnonyme {

    public static void main(String[] args) {
        int n = 20;
        Thread p1 = new Thread(() -> {
            for (int i = 0; i < n; i += 2) {
                Thread cur = Thread.currentThread();
                System.out.println("Thead courant : " + cur.getName());
                try {
                    Thread.sleep((int) (Math.random() * 500));
                } catch (InterruptedException ex) {
                    throw new Error("impossible", ex);
                }
                System.out.println(i);
            }
        }
        );
        p1.start();
        try {
            p1.join();
        } catch (InterruptedException ex) {
            throw new Error("normalement impossible", ex);
        }
        System.out.println(
                "fini");
    }

}
