/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
