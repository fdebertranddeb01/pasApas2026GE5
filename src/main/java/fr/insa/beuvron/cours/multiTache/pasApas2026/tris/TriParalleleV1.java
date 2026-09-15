/*
 Copyright 2000-2014 Francois de Bertrand de Beuvron

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
package fr.insa.beuvron.cours.multiTache.pasApas2026.tris;

import fr.insa.beuvron.cours.multiTache.pasApas2026.utils.ThreadUtils;

public class TriParalleleV1 extends Thread {
    
    private int[] tab;
    private int min;
    private int max;
    private int minParallele;

    public TriParalleleV1(int[] tab, int min, int max,int minParallele) {
        this.tab = tab;
        this.min = min;
        this.max = max;
        this.minParallele = minParallele;
    }
    
    

    /**
     * todoDoc.
     */
    public static int BMAX = 5000;


    @Override
    public void run() {
//        System.out.println("sorting " + Arrays.toString(tab) + " between " + min + " and " + max);
        if (max - min <= minParallele) {
            TriSequentielCreationBuffersInutiles.triBorne(tab, min, max);
        } else {
            int milieu = (max + min) / 2;
            TriParalleleV1 tmin = new TriParalleleV1(tab, min, milieu,minParallele);
            TriParalleleV1 tmax = new TriParalleleV1(tab, milieu+1, max,minParallele);
            tmin.start();
            tmax.start();
            ThreadUtils.joinSansInterruption(tmin);
            ThreadUtils.joinSansInterruption(tmax);
            fusion(tab, min, max);
        }
//        System.out.println("sorted " + Arrays.toString(tab) + " between " + min + " and " + max);
    }

    private static void fusion(int[] tab, int min, int max) {
        int[] fu = new int[max - min + 1];
        int milieu = (max + min) / 2;
        int cur1 = min;
        int cur2 = milieu + 1;
        for (int i = 0; i < fu.length; i++) {
            if (cur1 > milieu) {
                // plus rien dans la première partie
                fu[i] = tab[cur2];
                cur2++;
            } else if (cur2 > max) {
                // plus rien dans la seconde partie
                fu[i] = tab[cur1];
                cur1++;
            } else if (tab[cur1] <= tab[cur2]) {
                fu[i] = tab[cur1];
                cur1++;
            } else {
                fu[i] = tab[cur2];
                cur2++;
            }
        }
        for (int i = 0; i < fu.length; i++) {
            tab[min + i] = fu[i];
        }
    }

    /**
     *
     * @param tab
     * @return
     */
    public static boolean testTrie(int[] tab) {
        boolean res = true;
        int i = 0;
        while (res && i < tab.length - 1) {
            res = tab[i] <= tab[i + 1];
            i++;
        }
        return res;
    }

    /**
     *
     * @param size
     * @param borneMax
     * @return
     */
    public static int[] tabAlea(int size, int borneMax) {
        int[] res = new int[size];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) (Math.random() * borneMax);
        }
        return res;
    }

    /**
     *
     * @param size
     * @param bmax
     */
    public static void test(int size, int bmax) {
        int[] t = tabAlea(size, bmax);
        System.out.println("trie tableau taille : " + size
                + " (0 <= e < " + bmax + ")");
        long deb = System.currentTimeMillis();
        int nbrProc = Runtime.getRuntime().availableProcessors();
        System.out.println("nbrProc = "+nbrProc);
        int minPara = Math.max(1, t.length / nbrProc);
        TriParalleleV1 debut = new TriParalleleV1(t, 0, t.length - 1,minPara);
        debut.start();
        ThreadUtils.joinSansInterruption(debut);
        long duree = System.currentTimeMillis() - deb;
        System.out.println("test : " + testTrie(t));
        System.out.println("in " + duree + " ms");

    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        test(10000000, BMAX);
    }

}
