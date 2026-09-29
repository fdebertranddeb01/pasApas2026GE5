/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.threadBasic;

import fr.insa.beuvron.cours.multiTache.pasApas2026.utils.ThreadUtils;

/**
 *
 * @author fdebertranddeb01
 */
public class Incrementeur {
    
    public static volatile long compteur = 0;

    public static void incremente() {
        for (long i = 0; i < 10_000_000; i++) {
            synchronized (Incrementeur.class) {
                compteur++;
            }
        }
    }

    public static void test() {
        long debut = System.currentTimeMillis();
        Thread t1 = new Thread(() -> incremente());
        Thread t2 = new Thread(() -> incremente());
        t1.start();
        t2.start();
        ThreadUtils.joinSansInterruption(t1);
        ThreadUtils.joinSansInterruption(t2);
        System.out.println("compteur : " + compteur);
        long fin = System.currentTimeMillis();
        System.out.println("fini en " + (fin-debut) + " ms");
    }

    public static void main(String[] args) {
        test();
    }

}
