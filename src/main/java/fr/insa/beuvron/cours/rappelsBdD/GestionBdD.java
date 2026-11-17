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
package fr.insa.beuvron.cours.rappelsBdD;

import fr.insa.beuvron.utils.ConsoleFdB;
import fr.insa.beuvron.utils.database.ConnectionSimpleSGBD;
import fr.insa.beuvron.utils.exceptions.ExceptionsUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author francois
 */
public class GestionBdD {
    
    public static void creeSchema(Connection con) throws SQLException {
        // je commence une transaction
        con.setAutoCommit(false);
        try {
            try (PreparedStatement pst = con.prepareStatement(
                    "create table element (\n"
                    + ConnectionSimpleSGBD.SGBD.declarePrimaryKeyGenerated("id")
                    + "  nom varchar(30) not null unique\n"
                    + ")")) {
                pst.executeUpdate();
            }
            try (PreparedStatement pst = con.prepareStatement(
                    "create table poke (\n"
                    + ConnectionSimpleSGBD.SGBD.declarePrimaryKeyGenerated("id")
                    + "  nom varchar(30) not null unique,\n"
                    + "  element int not null\n"
                    + ")")) {
                pst.executeUpdate();
            }
            try (PreparedStatement pst = con.prepareStatement(
                    "alter table poke add constraint fk_poke_element \n"
                    + "foreign key (element) references element(id)")) {
                pst.executeUpdate();
            }
            // si je suis arrivé jusque là, c'est que tout c'est bien passé
            // je valide la transaction
            con.commit();
        } catch (SQLException ex) {
            // il y a eu un problème
            // j'annule toute la transaction
            con.rollback();
            // je renvoie l'exception pour qu'elle puisse être traitée coté utilisateur
            throw ex;
        } finally {
            // dans tous les cas, je reviens à la gestion des transactions par défaut :
            // chaque ordre SQL (execute d'un Statement) est considéré comme une
            // transaction indépendante
            con.setAutoCommit(true);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void deleteSchema(Connection con) {
        try (PreparedStatement pst = con.prepareStatement(
                "drop constraint fk_poke_element")) {
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("impossible de supprimer le lien element : "+ex);
        }
        try (PreparedStatement pst = con.prepareStatement(
                "drop table if exists poke")) {
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("impossible de supprimer table poke : "+ex);
        }
        try (PreparedStatement pst = con.prepareStatement(
                "drop table if exists element")) {
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("impossible de supprimer table element : "+ex);
        }
    }
    
    public static void raz(Connection con) throws SQLException {
        deleteSchema(con);
        creeSchema(con);
    }
    
    public static void menuPrincipal() {
        try (Connection con = ConnectionSimpleSGBD.defaultCon()) {
            int rep = -1;
            while (rep != 0) {
                int i = 1;
                System.out.println((i++) + ") Raz de la BdD");
                System.out.println((i++) + ") creation élément");
                System.out.println((i++) + ") lister tous les éléments");
                System.out.println((i++) + ") creation poke");
                System.out.println((i++) + ") lister tous les pokes");
                System.out.println("0) sortie");
                try {
                    rep = ConsoleFdB.entreeInt("votre choix : ");
                    int j = 1;
                    if (j++ == rep) {
                        raz(con);
                    } else if (j++ == rep) {
                        Element elem = Element.demande();
                        elem.saveInDB(con);
                    } else if (j++ == rep) {
                        List<Element> tous = Element.tous(con);
                        for(var e : tous) {
                            System.out.println(e);
                        }
                    } else if (j++ == rep) {
                        Poke pok = Poke.demande();
                        pok.saveInDB(con);
                    } else if (j++ == rep) {
                        List<Poke> tous = Poke.tous(con);
                        for (var p : tous) {
                            System.out.println(p);
                        }
                    }
                } catch (SQLException ex) {
                    System.err.println(ExceptionsUtils.messageEtPremiersAppelsDansPackage(
                            ex, "fr.insa", 4));
                }
            }
            
        } catch (SQLException ex) {
            System.err.println("Connection impossible : " + ex.getLocalizedMessage());
        }
    }
    
    public static void main(String[] args) {
        menuPrincipal();
    }
}
