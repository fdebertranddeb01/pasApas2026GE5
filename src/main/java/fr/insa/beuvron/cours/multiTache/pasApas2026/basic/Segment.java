/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.basic;

/**
 *
 * @author fdebertranddeb01
 */
public class Segment extends FigureSimple{
    
    private Point debut;
    private Point fin;

    public Segment(Point debut, Point fin) {
        this.debut = debut;
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "[" + this.debut + ";" + fin + ']';
    }
    
    

    /**
     * @return the debut
     */
    public Point getDebut() {
        return debut;
    }

    /**
     * @return the fin
     */
    public Point getFin() {
        return fin;
    }

    @Override
    public double minX() {
        return Math.min(this.debut.minX(), this.fin.minX());
    }

    @Override
    public double maxX() {
        return Math.max(this.debut.maxX(), this.fin.maxX());
    }
    
    
    
}
