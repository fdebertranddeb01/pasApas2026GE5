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

import fr.insa.beuvron.cours.multiTache.sockets.INetAdressUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;

/**
 *
 * @author francois
 */
public class ServeurChatAvecReadWriteList {

    public static final int PORT = 50001;

    private MyBlockingQueue<String> messages;

    // à améliorer avec des read-write lock
    private MyReadWriteList<WriterClient> resenders;

    public ServeurChatAvecReadWriteList() {
        this.messages = new MyBlockingQueue<>(30);
        this.resenders = new MyReadWriteList<>();
    }

    public static String strSocket(Socket soc) {
        return soc.getInetAddress().getHostAddress() + ":" + soc.getPort();
    }

    public void chat() {
        try {
            System.out.println("Serveur chat avec ReadWriteList");
            ServerSocket serv = new ServerSocket(PORT);
            var adrS = INetAdressUtil.premiereAdresseNonLoopback();
            System.out.println("Serveur attends connection");
            System.out.println("adresse : " + adrS.getHostAddress());
            System.out.println("port : " + PORT);
            RenvoieChat renvoie = new RenvoieChat();
            renvoie.start();
            System.out.println("resender running");
            while (true) {
                Socket soc = serv.accept();
                System.out.println("connection : ");
                System.out.println("adr client : " + soc.getInetAddress().getHostAddress());
                System.out.println("port client : " + soc.getPort());
                System.out.println("port local : " + soc.getLocalPort());
                EcouteurChat cl = this.new EcouteurChat(soc);
                cl.start();
                WriterClient wc = new WriterClient(soc);
                this.resenders.getWriteLock();
                try {
                    this.resenders.getVraieList().add(wc);
                } finally {
                    this.resenders.releaseWriteLock();
                }
            }
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    public class EcouteurChat extends Thread {

        private Socket soc;

        public EcouteurChat(Socket soc) {
            this.soc = soc;
        }

        @Override
        public void run() {
            try (var in = new BufferedReader(
                    new InputStreamReader(
                            soc.getInputStream(), Charset.forName("UTF8")))) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("recu de " + strSocket(soc) + " : " + line);
                    messages.offerWait(line);
                }
            } catch (IOException ex) {
                throw new Error(ex);
            }

        }
    }

    public static class WriterClient extends Thread {

        private Socket soc;
        BlockingQueue<String> enAttente;

        public WriterClient(Socket soc) {
            this.soc = soc;
            this.enAttente = new LinkedBlockingQueue<>(100);
        }

        public void addMessage(String mess) {
            try {
                System.out.println("adding mess toSend for socket "+strSocket(getSoc()));
                this.enAttente.put(mess);
            } catch (InterruptedException ex) {
                throw new Error("pas d'interrupt", ex);
            }
        }

        @Override
        public void run() {
            try (var out = new PrintWriter(
                    new OutputStreamWriter(
                            this.getSoc().getOutputStream(), Charset.forName("UTF8")))) {
                while (true) {
                    try {
                        String mess = this.enAttente.take();
                        System.out.println("writer for soc "+ strSocket(getSoc()) + " : "+mess);
                        out.println(mess);
                        out.flush();
                    } catch (InterruptedException ex) {
                        throw new Error("pas d'interrupt", ex);
                    }

                }
            } catch (IOException ex) {
                throw new Error("probleme écriture sur " + strSocket(getSoc()), ex);
            }
        }

        /**
         * @return the soc
         */
        public Socket getSoc() {
            return soc;
        }
    }

    public class RenvoieChat extends Thread {

        @Override
        public void run() {
            while (true) {
                String mess = messages.pollWait();
                resenders.getReadLock();
                try {
                    for (WriterClient wc : resenders.getVraieList()) {
                        System.out.println("Renvoie chat renvoie a "+strSocket(wc.getSoc()));
                        wc.addMessage(mess);
                    }
                } finally {
                    resenders.releaseReadLock();
                }
            }
        }

    }

    public static void main(String[] args) {
        ServeurChatAvecReadWriteList sChat = new ServeurChatAvecReadWriteList();
        sChat.chat();
    }

}
