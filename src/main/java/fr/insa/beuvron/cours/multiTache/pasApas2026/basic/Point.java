/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pasApas2026.basic;

/**
 *
 * @author fdebertranddeb01
 */
public class Point extends FigureSimple {
    
    private double px;
    private double py;

    public Point(double px, double py) {
        this.px = px;
        this.py = py;
    }

    @Override
    public String toString() {
        return "(" + this.px + "," + py + ')';
    }
    
    

    /**
     * @return the px
     */
    public double getPx() {
        return px;
    }

    /**
     * @param px the px to set
     */
    public void setPx(double px) {
        this.px = px;
    }

    /**
     * @return the py
     */
    public double getPy() {
        return py;
    }

    /**
     * @param py the py to set
     */
    public void setPy(double py) {
        this.py = py;
    }

    @Override
    public double minX() {
        return this.px;
    }

    @Override
    public double maxX() {
        return this.px;
    }
    
    
    
}
