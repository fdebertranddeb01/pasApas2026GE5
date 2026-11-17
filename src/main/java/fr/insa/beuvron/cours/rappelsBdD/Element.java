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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * classe "miroir" de la table element.
 *
 * @author fdebertranddeb01
 */
public class Element {

    private int id;
    private String nom;

    public Element(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Element(String nom) {
        this(-1, nom);
    }

    @Override
    public String toString() {
        return "Element{" + "id=" + id + ", nom=" + nom + '}';
    }

    public static Element demande() {
        String nom = ConsoleFdB.entreeString("nom de l'élément");
        return new Element(nom);
    }

    public void saveInDB(Connection con) throws SQLException {
        if (this.id != -1) {
            throw new Error("element existe déjà");
        }
        try (PreparedStatement pst = con.prepareStatement(
                "insert into element (nom) values (?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, this.nom);
            pst.executeUpdate();
            ResultSet keys = pst.getGeneratedKeys();
            if (keys.next()) {
                this.id = keys.getInt(1);
            } else {
                throw new Error("impossible de générer la clé");
            }
        }
    }

    public static List<Element> tous(Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select id,nom from element")) {
            ResultSet rs = pst.executeQuery();
            ArrayList<Element> res = new ArrayList<>();
            while (rs.next()) {
                res.add(new Element(rs.getInt("id"),
                        rs.getString("nom")));
            }
            return res;
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

}
