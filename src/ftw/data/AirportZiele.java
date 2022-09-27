/* Fly the World
 * Wirtschaftsymulation für Flugsimulatoren
 * Copyright (C) 2016 Stefan Klees
 * stefan.klees@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ftw.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stefan Klees
 */
@Entity
@Table(name = "Airport_Ziele")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "AirportZiele.findAll", query = "SELECT a FROM AirportZiele a")
  , @NamedQuery(name = "AirportZiele.findByIdListe", query = "SELECT a FROM AirportZiele a WHERE a.idListe = :idListe")
  , @NamedQuery(name = "AirportZiele.findByIdAirport", query = "SELECT a FROM AirportZiele a WHERE a.idAirport = :idAirport")
  , @NamedQuery(name = "AirportZiele.findByIdZielAirport", query = "SELECT a FROM AirportZiele a WHERE a.idZielAirport = :idZielAirport")
  , @NamedQuery(name = "AirportZiele.findByICAOZiel", query = "SELECT a FROM AirportZiele a WHERE a.iCAOZiel = :iCAOZiel")})
public class AirportZiele implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idListe")
  private Integer idListe;
  @Column(name = "idAirport")
  private Integer idAirport;
  @Column(name = "idZielAirport")
  private Integer idZielAirport;
  @Column(name = "ICAOZiel")
  private String iCAOZiel;

  public AirportZiele() {
  }

  public AirportZiele(Integer idListe) {
    this.idListe = idListe;
  }

  public Integer getIdListe() {
    return idListe;
  }

  public void setIdListe(Integer idListe) {
    this.idListe = idListe;
  }

  public Integer getIdAirport() {
    return idAirport;
  }

  public void setIdAirport(Integer idAirport) {
    this.idAirport = idAirport;
  }

  public Integer getIdZielAirport() {
    return idZielAirport;
  }

  public void setIdZielAirport(Integer idZielAirport) {
    this.idZielAirport = idZielAirport;
  }

  public String getICAOZiel() {
    return iCAOZiel;
  }

  public void setICAOZiel(String iCAOZiel) {
    this.iCAOZiel = iCAOZiel;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idListe != null ? idListe.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof AirportZiele)) {
      return false;
    }
    AirportZiele other = (AirportZiele) object;
    if ((this.idListe == null && other.idListe != null) || (this.idListe != null && !this.idListe.equals(other.idListe))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.AirportZiele[ idListe=" + idListe + " ]";
  }
  
}
