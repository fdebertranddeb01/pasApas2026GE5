/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.basic;

/**
 * test commit
 * @author fdebertranddeb01
 */
public abstract class FigureSimple implements Figure{
    
    private int couleur;
    
    public abstract double maxX();
    
    @Override
    public double largeur(){
        return this.maxX() - this.minX();
    }
    
}
