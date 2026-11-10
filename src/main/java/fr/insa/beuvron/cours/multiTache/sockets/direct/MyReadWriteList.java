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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author fdebertranddeb01
 */
public class MyReadWriteList<E> {
    
    private ReadWriteLock lock;
    private ArrayList<E> vraieList;
    
    public MyReadWriteList() {
        this.lock = new ReentrantReadWriteLock(true);
        this.vraieList = new ArrayList<>();
    }
    
    public void getReadLock() {
        this.lock.readLock().lock();
        System.out.println("lock read acquis par "+Thread.currentThread().getName());
    }
    
    public void releaseReadLock() {
        this.lock.readLock().unlock();
        System.out.println("lock read relache par "+Thread.currentThread().getName());
    }
    
    public void getWriteLock() {
        this.lock.writeLock().lock();
        System.out.println("lock write acquis par "+Thread.currentThread().getName());
    }
    
    public void releaseWriteLock() {
        this.lock.writeLock().unlock();
        System.out.println("lock write relache par "+Thread.currentThread().getName());
    }
    
    public List<E> getVraieList() {
        return this.vraieList;
    }
    
    
    
}
