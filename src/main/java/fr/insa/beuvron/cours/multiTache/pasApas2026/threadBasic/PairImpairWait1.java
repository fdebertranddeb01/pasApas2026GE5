/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
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
