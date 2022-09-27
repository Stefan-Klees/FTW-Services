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
@Table(name = "Flughafen_Klassen")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "FlughafenKlassen.findAll", query = "SELECT f FROM FlughafenKlassen f")})
public class FlughafenKlassen implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idFlughafen_Klassen")
  private Integer idFlughafenKlassen;
  @Column(name = "klasseNummer")
  private Integer klasseNummer;
  @Column(name = "bezeichnung")
  private String bezeichnung;

  public FlughafenKlassen() {
  }

  public FlughafenKlassen(Integer idFlughafenKlassen) {
    this.idFlughafenKlassen = idFlughafenKlassen;
  }

  public Integer getIdFlughafenKlassen() {
    return idFlughafenKlassen;
  }

  public void setIdFlughafenKlassen(Integer idFlughafenKlassen) {
    this.idFlughafenKlassen = idFlughafenKlassen;
  }

  public Integer getKlasseNummer() {
    return klasseNummer;
  }

  public void setKlasseNummer(Integer klasseNummer) {
    this.klasseNummer = klasseNummer;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public void setBezeichnung(String bezeichnung) {
    this.bezeichnung = bezeichnung;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idFlughafenKlassen != null ? idFlughafenKlassen.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof FlughafenKlassen)) {
      return false;
    }
    FlughafenKlassen other = (FlughafenKlassen) object;
    if ((this.idFlughafenKlassen == null && other.idFlughafenKlassen != null) || (this.idFlughafenKlassen != null && !this.idFlughafenKlassen.equals(other.idFlughafenKlassen))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.FlughafenKlassen[ idFlughafenKlassen=" + idFlughafenKlassen + " ]";
  }
  
}
