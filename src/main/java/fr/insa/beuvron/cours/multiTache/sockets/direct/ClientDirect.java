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

import fr.insa.beuvron.utils.ConsoleFdB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 * @author francois
 */
public class ClientDirect {

    public static void direct() {
        try {
            String adrS = ConsoleFdB.entreeString("adresse serveur : ");
            int port = ConsoleFdB.entreeInt("port : ");
            Socket soc = new Socket(adrS, port);
            System.out.println("adr serveur : " + soc.getInetAddress().getHostAddress());
            System.out.println("port serveur : " + soc.getPort());
            System.out.println("port local : " + soc.getLocalPort());
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    soc.getOutputStream(), Charset.forName("UTF8")))) {
                String mess = "";
                while (!mess.equalsIgnoreCase("FIN")) {
                    mess = ConsoleFdB.entreeString("votre message : ");
                    out.println(mess);
                    out.flush();
                }
            }
        } catch (IOException ex) {
            throw new Error(ex);
        }

    }

    public static void main(String[] args) {
        direct();
    }

}
