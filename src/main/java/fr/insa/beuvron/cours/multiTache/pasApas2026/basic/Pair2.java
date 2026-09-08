/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.basic;

/**
 *
 * @author fdebertranddeb01
 */
public class Pair2 {
    
    private int nMax;
        
    public Pair2(int nMax) {
        if (nMax <= 0) {
            throw new Error("val impossible");
        }
        this.nMax = nMax;
    }
    
    public void affPair() {
        for (int i = 0 ; i < this.getnMax() ; i +=2) {
            System.out.println(i);
        }
    }
    
    public static void main(String[] args) {
        Pair2 p = new Pair2(9);
        p.affPair();
    }

    /**
     * @return the nMax
     */
    public int getnMax() {
        return nMax;
    }

    /**
     * @param nMax the nMax to set
     */
    public void setnMax(int nMax) {
        if (nMax <= 0) {
            throw new Error("nmum doit être strictement positif") ;
        }
        this.nMax = nMax;
    }
    
}
