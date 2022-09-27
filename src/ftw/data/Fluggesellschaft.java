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
@Table(name = "Fluggesellschaft")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Fluggesellschaft.findAll", query = "SELECT f FROM Fluggesellschaft f")})
public class Fluggesellschaft implements Serializable {

  @Column(name = "erzeugtesCargo")
  private Integer erzeugtesCargo;
  @Column(name = "geflogenesCargo")
  private Integer geflogenesCargo;

  @Column(name = "erzeugteJobs")
  private Integer erzeugteJobs;
  @Column(name = "geflogeneJobs")
  private Integer geflogeneJobs;

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idFluggesellschaft")
  private Integer idFluggesellschaft;
  @Column(name = "userid")
  private Integer userid;
  @Column(name = "name")
  private String name;
  @Column(name = "icao")
  private String icao;
  @Column(name = "icaoCode")
  private String icaoCode;
  @Column(name = "stadt")
  private String stadt;
  @Column(name = "land")
  private String land;
  @Column(name = "bundesstaat")
  private String bundesstaat;
  @Column(name = "logoURL")
  private String logoURL;
  @Column(name = "besitzerName")
  private String besitzerName;
  @Column(name = "bankKontoName")
  private String bankKontoName;
  @Column(name = "bankKonto")
  private String bankKonto;
  @Column(name = "kostenstelle")
  private Integer kostenstelle;

  public Fluggesellschaft() {
  }

  public Fluggesellschaft(Integer idFluggesellschaft) {
    this.idFluggesellschaft = idFluggesellschaft;
  }

  public Integer getIdFluggesellschaft() {
    return idFluggesellschaft;
  }

  public void setIdFluggesellschaft(Integer idFluggesellschaft) {
    this.idFluggesellschaft = idFluggesellschaft;
  }

  public Integer getUserid() {
    return userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIcao() {
    return icao;
  }

  public void setIcao(String icao) {
    this.icao = icao;
  }

  public String getIcaoCode() {
    return icaoCode;
  }

  public void setIcaoCode(String icaoCode) {
    this.icaoCode = icaoCode;
  }

  public String getStadt() {
    return stadt;
  }

  public void setStadt(String stadt) {
    this.stadt = stadt;
  }

  public String getLand() {
    return land;
  }

  public void setLand(String land) {
    this.land = land;
  }

  public String getBundesstaat() {
    return bundesstaat;
  }

  public void setBundesstaat(String bundesstaat) {
    this.bundesstaat = bundesstaat;
  }

  public String getLogoURL() {
    return logoURL;
  }

  public void setLogoURL(String logoURL) {
    this.logoURL = logoURL;
  }

  public String getBesitzerName() {
    return besitzerName;
  }

  public void setBesitzerName(String besitzerName) {
    this.besitzerName = besitzerName;
  }

  public String getBankKontoName() {
    return bankKontoName;
  }

  public void setBankKontoName(String bankKontoName) {
    this.bankKontoName = bankKontoName;
  }

  public String getBankKonto() {
    return bankKonto;
  }

  public void setBankKonto(String bankKonto) {
    this.bankKonto = bankKonto;
  }

  public Integer getKostenstelle() {
    return kostenstelle;
  }

  public void setKostenstelle(Integer kostenstelle) {
    this.kostenstelle = kostenstelle;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idFluggesellschaft != null ? idFluggesellschaft.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Fluggesellschaft)) {
      return false;
    }
    Fluggesellschaft other = (Fluggesellschaft) object;
    if ((this.idFluggesellschaft == null && other.idFluggesellschaft != null) || (this.idFluggesellschaft != null && !this.idFluggesellschaft.equals(other.idFluggesellschaft))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.Fluggesellschaft[ idFluggesellschaft=" + idFluggesellschaft + " ]";
  }

  public Integer getErzeugteJobs() {
    return erzeugteJobs;
  }

  public void setErzeugteJobs(Integer erzeugteJobs) {
    this.erzeugteJobs = erzeugteJobs;
  }

  public Integer getGeflogeneJobs() {
    return geflogeneJobs;
  }

  public void setGeflogeneJobs(Integer geflogeneJobs) {
    this.geflogeneJobs = geflogeneJobs;
  }

  public Integer getErzeugtesCargo() {
    return erzeugtesCargo;
  }

  public void setErzeugtesCargo(Integer erzeugtesCargo) {
    this.erzeugtesCargo = erzeugtesCargo;
  }

  public Integer getGeflogenesCargo() {
    return geflogenesCargo;
  }

  public void setGeflogenesCargo(Integer geflogenesCargo) {
    this.geflogenesCargo = geflogenesCargo;
  }
  
}
