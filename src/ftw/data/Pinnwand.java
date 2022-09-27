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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stefan Klees
 */
@Entity
@Table(name = "pinnwand")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Pinnwand.findAll", query = "SELECT p FROM Pinnwand p")})
public class Pinnwand implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idpinnwand")
  private Integer idpinnwand;
  @Column(name = "datumZeit")
  @Temporal(TemporalType.TIMESTAMP)
  private Date datumZeit;
  @Column(name = "vonID")
  private Integer vonID;
  @Column(name = "anID")
  private Integer anID;
  @Column(name = "vonUser")
  private String vonUser;
  @Column(name = "anUser")
  private String anUser;
  @Column(name = "airlineID")
  private Integer airlineID;
  @Column(name = "airportID")
  private Integer airportID;
  @Lob
  @Column(name = "nachrichtText")
  private String nachrichtText;
  @Column(name = "betreff")
  private String betreff;
  @Column(name = "gelesen")
  private Boolean gelesen;

  public Pinnwand() {
  }

  public Pinnwand(Integer idpinnwand) {
    this.idpinnwand = idpinnwand;
  }

  public Integer getIdpinnwand() {
    return idpinnwand;
  }

  public void setIdpinnwand(Integer idpinnwand) {
    this.idpinnwand = idpinnwand;
  }

  public Date getDatumZeit() {
    return datumZeit;
  }

  public void setDatumZeit(Date datumZeit) {
    this.datumZeit = datumZeit;
  }

  public Integer getVonID() {
    return vonID;
  }

  public void setVonID(Integer vonID) {
    this.vonID = vonID;
  }

  public Integer getAnID() {
    return anID;
  }

  public void setAnID(Integer anID) {
    this.anID = anID;
  }

  public String getVonUser() {
    return vonUser;
  }

  public void setVonUser(String vonUser) {
    this.vonUser = vonUser;
  }

  public String getAnUser() {
    return anUser;
  }

  public void setAnUser(String anUser) {
    this.anUser = anUser;
  }

  public Integer getAirlineID() {
    return airlineID;
  }

  public void setAirlineID(Integer airlineID) {
    this.airlineID = airlineID;
  }

  public Integer getAirportID() {
    return airportID;
  }

  public void setAirportID(Integer airportID) {
    this.airportID = airportID;
  }

  public String getNachrichtText() {
    return nachrichtText;
  }

  public void setNachrichtText(String nachrichtText) {
    this.nachrichtText = nachrichtText;
  }

  public String getBetreff() {
    return betreff;
  }

  public void setBetreff(String betreff) {
    this.betreff = betreff;
  }

  public Boolean getGelesen() {
    return gelesen;
  }

  public void setGelesen(Boolean gelesen) {
    this.gelesen = gelesen;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idpinnwand != null ? idpinnwand.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Pinnwand)) {
      return false;
    }
    Pinnwand other = (Pinnwand) object;
    if ((this.idpinnwand == null && other.idpinnwand != null) || (this.idpinnwand != null && !this.idpinnwand.equals(other.idpinnwand))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.Pinnwand[ idpinnwand=" + idpinnwand + " ]";
  }
  
}
