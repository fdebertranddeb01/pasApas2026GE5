/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
            throw new Error("interruption non attendue",ex);
        }
    }
    
}
