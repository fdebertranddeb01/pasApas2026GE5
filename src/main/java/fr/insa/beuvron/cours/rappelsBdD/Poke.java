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
 * classe "miroir" de la table poke. Convention : id == -1 <==> non sauvegardé
 *
 * @author fdebertranddeb01
 */
public class Poke {

    private int id;
    private String nom;
    private int element;

    /**
     * pour poke récupéré dans la BdD
     */
    public Poke(int id, String nom, int element) {
        this.id = id;
        this.nom = nom;
        this.element = element;
    }

    /**
     * nouveau poke en mémoire
     */
    public Poke(String nom, int element) {
        this(-1, nom, element);
    }

    @Override
    public String toString() {
        return "Poke{" + "id=" + id + ", nom=" + nom + ", element=" + element + '}';
    }
    
    public static Poke demande() {
        String nom = ConsoleFdB.entreeString("nom");
        int elem = ConsoleFdB.entreeInt("element (prov : devrait être testé) : ");
        return new Poke(nom, elem);
    }

    public void saveInDB(Connection con) throws SQLException {
        if (this.id != -1) {
            throw new Error("pokemon existe déjà");
        }
        try (PreparedStatement pst = con.prepareStatement(
                "insert into poke (nom,element) values (?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, this.nom);
            pst.setInt(2, this.element);
            pst.executeUpdate();
            ResultSet keys = pst.getGeneratedKeys();
            if (keys.next()) {
                this.id = keys.getInt(1);
            } else {
                throw new Error("impossible de générer la clé");
            }
        }
    }

    public static List<Poke> tous(Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select id,nom,element from poke")) {
            ResultSet rs = pst.executeQuery();
            ArrayList<Poke> res = new ArrayList<>();
            while (rs.next()) {
                res.add(new Poke(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("element")));
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

    /**
     * @return the element
     */
    public int getElement() {
        return element;
    }

    /**
     * @param element the element to set
     */
    public void setElement(int element) {
        this.element = element;
    }

}
