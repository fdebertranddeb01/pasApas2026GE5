/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.threadBasic;

import fr.insa.beuvron.cours.multiTache.pasApas2026.utils.ThreadUtils;

/**
 * Exemple NON realiste : je synchronise sur l'ensemble de la méthode incrémente
 * j'ai donc une exécution quasi séquentielle des threads.
 *
 * @author fdebertranddeb01
 */
public class Incrementeur3 {

    public long compteur = 0;

//    public void incremente() {
//        synchronized (this) {
//            for (long i = 0; i < 10_000_000; i++) {
//                this.compteur++;
//            }
//        }
//    }
//
    public synchronized void incremente() {
        for (long i = 0; i < 10_000_000; i++) {
            this.compteur++;
        }
    }

    public static void test() {
        long debut = System.currentTimeMillis();
        Incrementeur3 compt = new Incrementeur3();
        Thread t1 = new Thread(() -> compt.incremente());
        Thread t2 = new Thread(() -> compt.incremente());
        t1.start();
        t2.start();
        ThreadUtils.joinSansInterruption(t1);
        ThreadUtils.joinSansInterruption(t2);
        System.out.println("compteur : " + compt.compteur);
        long fin = System.currentTimeMillis();
        System.out.println("fini en " + (fin - debut) + " ms");
    }

    public static void main(String[] args) {
        test();
    }

}
