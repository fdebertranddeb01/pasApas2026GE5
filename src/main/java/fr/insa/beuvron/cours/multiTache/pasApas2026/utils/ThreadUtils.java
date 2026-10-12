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
package fr.insa.beuvron.cours.multiTache.pasApas2026.utils;

/**
 *
 * @author fdebertranddeb01
 */
public class ThreadUtils {

    public static void joinSansInterruption(Thread t) {
        try {
            t.join();
        } catch (InterruptedException ex) {
            throw new Error("interruption non attendue", ex);
        }
    }

     public static void sleepAlea(long duree) {
        long de = (long) (duree * (0.5 + Math.random()));
        try {
            Thread.sleep(de);
        } catch (InterruptedException ex) {
            throw new Error("interruption non attendue");
        }
    }

}
