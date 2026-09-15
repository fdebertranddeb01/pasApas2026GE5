/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.basic;

/**
 *
 * @author fdebertranddeb01
 */
public abstract class FigureSimple {
    
    public abstract double minX() ;
    public abstract double maxX();
    
    public double largeur(){
        return this.maxX() - this.minX();
    }
    
}
