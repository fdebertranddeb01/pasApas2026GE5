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
public class PairImpairWait1 {

    public static class Verrou {

    }

    public static class Pair extends Thread {

        private final Verrou bloque;

        public Pair(Verrou bloque) {
            this.bloque = bloque;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i+=2) {
                ThreadUtils.sleepAlea(500);
                System.out.println(Thread.currentThread().getName() + " : " + i);
                synchronized (this.bloque) {
                    this.bloque.notifyAll();
                }
                try {
                    synchronized (this.bloque) {
                        this.bloque.wait();
                    }

                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt attendu", ex);
                }
            }
        }
    }

    public static class Impair extends Thread {

        private final Verrou bloque;

        public Impair(Verrou bloque) {
            this.bloque = bloque;
        }

        @Override
        public void run() {
            for (int i = 1; i < 10; i+=2) {
                try {
                    synchronized (this.bloque) {
                        this.bloque.wait();
                    }
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt attendu", ex);
                }
                ThreadUtils.sleepAlea(500);
                System.out.println(Thread.currentThread().getName() + " : " + i);
                synchronized (this.bloque) {
                    this.bloque.notifyAll();
                }
            }
        }
    }

    public static void test() {
        Verrou vv = new Verrou();
        Pair p = new Pair(vv);
        Impair i = new Impair(vv);
        p.start();
        i.start();
        ThreadUtils.joinSansInterruption(p);
        ThreadUtils.joinSansInterruption(i);
        System.out.println("fini");
    }

    public static void main(String[] args) {
        test();
    }

}
