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
@Table(name = "buchungen")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Buchungen.findAll", query = "SELECT b FROM Buchungen b")})
public class Buchungen implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "primanotaID")
  private Integer primanotaID;
  @Column(name = "idUser")
  private Integer idUser;
  @Column(name = "idFlugzeug")
  private Integer idFlugzeug;
  @Column(name = "idFboObjekt")
  private Integer idFboObjekt;
  @Column(name = "idFluggesellschaft")
  private Integer idFluggesellschaft;
  @Column(name = "datum")
  @Temporal(TemporalType.DATE)
  private Date datum;
  @Column(name = "buchungsText")
  private String buchungsText;
  @Column(name = "menge")
  private Integer menge;
  @Column(name = "kostenstelle")
  private Integer kostenstelle;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "betrag")
  private Double betrag;

  public Buchungen() {
  }

  public Buchungen(Integer primanotaID) {
    this.primanotaID = primanotaID;
  }

  public Integer getPrimanotaID() {
    return primanotaID;
  }

  public void setPrimanotaID(Integer primanotaID) {
    this.primanotaID = primanotaID;
  }

  public Integer getIdUser() {
    return idUser;
  }

  public void setIdUser(Integer idUser) {
    this.idUser = idUser;
  }

  public Integer getIdFlugzeug() {
    return idFlugzeug;
  }

  public void setIdFlugzeug(Integer idFlugzeug) {
    this.idFlugzeug = idFlugzeug;
  }

  public Integer getIdFboObjekt() {
    return idFboObjekt;
  }

  public void setIdFboObjekt(Integer idFboObjekt) {
    this.idFboObjekt = idFboObjekt;
  }

  public Integer getIdFluggesellschaft() {
    return idFluggesellschaft;
  }

  public void setIdFluggesellschaft(Integer idFluggesellschaft) {
    this.idFluggesellschaft = idFluggesellschaft;
  }

  public Date getDatum() {
    return datum;
  }

  public void setDatum(Date datum) {
    this.datum = datum;
  }

  public String getBuchungsText() {
    return buchungsText;
  }

  public void setBuchungsText(String buchungsText) {
    this.buchungsText = buchungsText;
  }

  public Integer getMenge() {
    return menge;
  }

  public void setMenge(Integer menge) {
    this.menge = menge;
  }

  public Integer getKostenstelle() {
    return kostenstelle;
  }

  public void setKostenstelle(Integer kostenstelle) {
    this.kostenstelle = kostenstelle;
  }

  public Double getBetrag() {
    return betrag;
  }

  public void setBetrag(Double betrag) {
    this.betrag = betrag;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (primanotaID != null ? primanotaID.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Buchungen)) {
      return false;
    }
    Buchungen other = (Buchungen) object;
    if ((this.primanotaID == null && other.primanotaID != null) || (this.primanotaID != null && !this.primanotaID.equals(other.primanotaID))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.Buchungen[ primanotaID=" + primanotaID + " ]";
  }
  
}
