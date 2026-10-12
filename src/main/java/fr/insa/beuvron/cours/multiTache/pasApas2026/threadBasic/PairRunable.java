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
