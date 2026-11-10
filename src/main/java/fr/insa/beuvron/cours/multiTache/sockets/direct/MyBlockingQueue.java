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
package fr.insa.beuvron.cours.multiTache.sockets.direct;

import fr.insa.beuvron.cours.multiTache.pasApas2026.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author fdebertranddeb01
 */
public class MyBlockingQueue<E> {
    
    private int maxElem;
    private LinkedList<E> realQueue;
    private ReentrantLock lock;
    private Condition notFull;
    private Condition notEmpty;

    public MyBlockingQueue(int maxElem) {
        this.maxElem = maxElem;
        this.realQueue = new LinkedList<>();
        this.lock = new ReentrantLock(true);
        this.notFull = this.lock.newCondition();
        this.notEmpty = this.lock.newCondition();
    }
    
    
    
    public void offerWait(E elem) {
        this.lock.lock();
        try {
            while (this.realQueue.size() == this.maxElem) {
                try {
                    System.out.println(Thread.currentThread().getName() + " attends");
                    this.notFull.await();
                } catch (InterruptedException ex) {
                    throw new Error("unexpected interrupt",ex);
                }
            }
            this.realQueue.offer(elem);
            this.notEmpty.signal();
            
        } finally {
            this.lock.unlock();
        }
    }
    
    public E pollWait() {
        this.lock.lock();
        try {
            while (this.realQueue.isEmpty()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " attends");
                    this.notEmpty.await();
                } catch (InterruptedException ex) {
                    throw new Error("unexpected interrupt",ex);
                }
            }
            E elem = this.realQueue.poll();
            this.notFull.signal();
            return elem;
            
        } finally {
            this.lock.unlock();
        }
        
    }
    
    public static void test(int nbrConsoProd,int nbrMessage) {
        MyBlockingQueue<String> bq = new MyBlockingQueue<>(2);
        AtomicInteger compteurMess = new AtomicInteger();
        List<Thread> bosseurs = new ArrayList<>();
        for (int i = 0 ; i < nbrConsoProd ; i ++) {
            var prod = new Thread(()-> {
                for (int j = 0 ; j < nbrMessage ; j ++) {
                    String mess = "M" + compteurMess.getAndIncrement();
                    bq.offerWait(mess);
                    System.out.println(Thread.currentThread().getName() +
                            " a cree : " + mess);
                    ThreadUtils.sleepAlea(200);
                }
            },"Prod"+i+ " : ");
            bosseurs.add(prod);
        }
        for (int i = 0 ; i < nbrConsoProd ; i ++) {
            var conso = new Thread(()-> {
                for (int j = 0 ; j < nbrMessage ; j ++) {
                    String mess = bq.pollWait();
                    System.out.println(Thread.currentThread().getName() +
                            " a recup : " + mess);
                    ThreadUtils.sleepAlea(100);
                }
            },"Conso "+i + " : ");
            bosseurs.add(conso);
        }
        for (var b : bosseurs) {
            b.start();
        }
        for (var b : bosseurs) {
            ThreadUtils.joinSansInterruption(b);
        }
    }
    
    public static void main(String[] args) {
        test(2, 3);
    }
    
}
