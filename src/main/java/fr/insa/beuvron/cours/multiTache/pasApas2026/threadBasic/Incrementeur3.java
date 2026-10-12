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
