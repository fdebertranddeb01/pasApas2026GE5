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
package fr.insa.beuvron.cours.multiTache.pasApas2026.basic;

/**
 *
 * @author fdebertranddeb01
 */
public class PairAvecMin extends Pair2 {
    
    private int nMin;
    
    public PairAvecMin(int nMin,int nMax) {
        super(nMax);
        if (nMin > nMax) {
            throw new Error("nmin < nmax");
        }
        this.nMin = nMin;     
    }
    
    @Override
    public void affPair() {
        for (int i = this.nMin ; i < this.getnMax() ; i ++) {
            System.out.println(i);
        }
    }
    
    public static void main(String[] args) {
        PairAvecMin pm = new PairAvecMin(10, 20);
        pm.affPair();
    }

    /**
     * @return the nMin
     */
    public int getnMin() {
        return nMin;
    }

    /**
     * @param nMin the nMin to set
     */
    public void setnMin(int nMin) {
        this.nMin = nMin;
    }
    
}
