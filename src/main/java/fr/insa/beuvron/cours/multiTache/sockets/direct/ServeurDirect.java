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
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 * @author francois
 */
public class ServeurDirect {

    public static final int PORT = 50001;
    
    private MyBlockingQueue<String> messages;

    public ServeurDirect() {
        this.messages = new MyBlockingQueue<>(30);
    }
    
    public static String strSocket(Socket soc) {
        return soc.getInetAddress().getHostAddress() + ":" + soc.getPort();
    }

    public static void direct() {
        try {
            ServerSocket serv = new ServerSocket(PORT);
            var adrS = INetAdressUtil.premiereAdresseNonLoopback();
            System.out.println("Serveur attends connection");
            System.out.println("adresse : " + adrS.getHostAddress());
            System.out.println("port : " + PORT);
            while (true) {
                Socket soc = serv.accept();
                System.out.println("connection : ");
                System.out.println("adr client : " + soc.getInetAddress().getHostAddress());
                System.out.println("port client : " + soc.getPort());
                System.out.println("port local : " + soc.getLocalPort());
                EcouteurClient cl = new EcouteurClient(soc);
                cl.start();
            }
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }
    
    public void chat() {
        try {
            ServerSocket serv = new ServerSocket(PORT);
            var adrS = INetAdressUtil.premiereAdresseNonLoopback();
            System.out.println("Serveur attends connection");
            System.out.println("adresse : " + adrS.getHostAddress());
            System.out.println("port : " + PORT);
            while (true) {
                Socket soc = serv.accept();
                System.out.println("connection : ");
                System.out.println("adr client : " + soc.getInetAddress().getHostAddress());
                System.out.println("port client : " + soc.getPort());
                System.out.println("port local : " + soc.getLocalPort());
                EcouteurChat cl = this.new EcouteurChat(soc);
                cl.start();
            }
        } catch (IOException ex) {
            throw new Error(ex);
        }             
    }

    /** cela n'est plus utilisé dans la version chat
     * ne fonctionne qu'avec l'appel "direct"
     */
    public static class EcouteurClient extends Thread {

        private Socket soc;

        public EcouteurClient(Socket soc) {
            this.soc = soc;
        }

        @Override
        public void run() {
            try (var in = new BufferedReader(
                    new InputStreamReader(
                            soc.getInputStream(), Charset.forName("UTF8")))) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("recu : " + line);
                }
            } catch (IOException ex) {
                throw new Error(ex);
            }

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
    
    public class RenvoieChat extends Thread {
        
    }

    public static void main(String[] args) {
        direct();
    }

}
