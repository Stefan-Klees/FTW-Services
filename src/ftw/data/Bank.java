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
@Table(name = "bank")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Bank.findAll", query = "SELECT b FROM Bank b")})
public class Bank implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "primanota")
  private Integer primanota;
  @Basic(optional = false)
  @Column(name = "bankKonto")
  private String bankKonto;
  @Basic(optional = false)
  @Column(name = "kontoName")
  private String kontoName;
  @Column(name = "userID")
  private Integer userID;
  @Column(name = "fluggesellschaftID")
  private Integer fluggesellschaftID;
  @Column(name = "flugzeugBesitzerID")
  private Integer flugzeugBesitzerID;
  @Column(name = "LeasinggesellschaftID")
  private Integer leasinggesellschaftID;
  @Column(name = "airportID")
  private Integer airportID;
  @Column(name = "transportID")
  private Integer transportID;
  @Column(name = "industrieID")
  private Integer industrieID;
  @Column(name = "absenderKonto")
  private String absenderKonto;
  @Column(name = "absenderName")
  private String absenderName;
  @Column(name = "empfaengerKonto")
  private String empfaengerKonto;
  @Column(name = "empfaengerName")
  private String empfaengerName;
  @Column(name = "ueberweisungsDatum")
  @Temporal(TemporalType.TIMESTAMP)
  private Date ueberweisungsDatum;
  @Column(name = "ausfuehrungsDatum")
  @Temporal(TemporalType.TIMESTAMP)
  private Date ausfuehrungsDatum;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "betrag")
  private Double betrag;
  @Lob
  @Column(name = "verwendungsZweck")
  private String verwendungsZweck;
  @Column(name = "icao")
  private String icao;
  @Column(name = "objektID")
  private Integer objektID;
  @Column(name = "flugzeugID")
  private Integer flugzeugID;
  @Column(name = "kostenstelle")
  private Integer kostenstelle;
  @Column(name = "pilotID")
  private Integer pilotID;

  public Bank() {
  }

  public Bank(Integer primanota) {
    this.primanota = primanota;
  }

  public Bank(Integer primanota, String bankKonto, String kontoName) {
    this.primanota = primanota;
    this.bankKonto = bankKonto;
    this.kontoName = kontoName;
  }

  public Integer getPrimanota() {
    return primanota;
  }

  public void setPrimanota(Integer primanota) {
    this.primanota = primanota;
  }

  public String getBankKonto() {
    return bankKonto;
  }

  public void setBankKonto(String bankKonto) {
    this.bankKonto = bankKonto;
  }

  public String getKontoName() {
    return kontoName;
  }

  public void setKontoName(String kontoName) {
    this.kontoName = kontoName;
  }

  public Integer getUserID() {
    return userID;
  }

  public void setUserID(Integer userID) {
    this.userID = userID;
  }

  public Integer getFluggesellschaftID() {
    return fluggesellschaftID;
  }

  public void setFluggesellschaftID(Integer fluggesellschaftID) {
    this.fluggesellschaftID = fluggesellschaftID;
  }

  public Integer getFlugzeugBesitzerID() {
    return flugzeugBesitzerID;
  }

  public void setFlugzeugBesitzerID(Integer flugzeugBesitzerID) {
    this.flugzeugBesitzerID = flugzeugBesitzerID;
  }

  public Integer getLeasinggesellschaftID() {
    return leasinggesellschaftID;
  }

  public void setLeasinggesellschaftID(Integer leasinggesellschaftID) {
    this.leasinggesellschaftID = leasinggesellschaftID;
  }

  public Integer getAirportID() {
    return airportID;
  }

  public void setAirportID(Integer airportID) {
    this.airportID = airportID;
  }

  public Integer getTransportID() {
    return transportID;
  }

  public void setTransportID(Integer transportID) {
    this.transportID = transportID;
  }

  public Integer getIndustrieID() {
    return industrieID;
  }

  public void setIndustrieID(Integer industrieID) {
    this.industrieID = industrieID;
  }

  public String getAbsenderKonto() {
    return absenderKonto;
  }

  public void setAbsenderKonto(String absenderKonto) {
    this.absenderKonto = absenderKonto;
  }

  public String getAbsenderName() {
    return absenderName;
  }

  public void setAbsenderName(String absenderName) {
    this.absenderName = absenderName;
  }

  public String getEmpfaengerKonto() {
    return empfaengerKonto;
  }

  public void setEmpfaengerKonto(String empfaengerKonto) {
    this.empfaengerKonto = empfaengerKonto;
  }

  public String getEmpfaengerName() {
    return empfaengerName;
  }

  public void setEmpfaengerName(String empfaengerName) {
    this.empfaengerName = empfaengerName;
  }

  public Date getUeberweisungsDatum() {
    return ueberweisungsDatum;
  }

  public void setUeberweisungsDatum(Date ueberweisungsDatum) {
    this.ueberweisungsDatum = ueberweisungsDatum;
  }

  public Date getAusfuehrungsDatum() {
    return ausfuehrungsDatum;
  }

  public void setAusfuehrungsDatum(Date ausfuehrungsDatum) {
    this.ausfuehrungsDatum = ausfuehrungsDatum;
  }

  public Double getBetrag() {
    return betrag;
  }

  public void setBetrag(Double betrag) {
    this.betrag = betrag;
  }

  public String getVerwendungsZweck() {
    return verwendungsZweck;
  }

  public void setVerwendungsZweck(String verwendungsZweck) {
    this.verwendungsZweck = verwendungsZweck;
  }

  public String getIcao() {
    return icao;
  }

  public void setIcao(String icao) {
    this.icao = icao;
  }

  public Integer getObjektID() {
    return objektID;
  }

  public void setObjektID(Integer objektID) {
    this.objektID = objektID;
  }

  public Integer getFlugzeugID() {
    return flugzeugID;
  }

  public void setFlugzeugID(Integer flugzeugID) {
    this.flugzeugID = flugzeugID;
  }

  public Integer getKostenstelle() {
    return kostenstelle;
  }

  public void setKostenstelle(Integer kostenstelle) {
    this.kostenstelle = kostenstelle;
  }

  public Integer getPilotID() {
    return pilotID;
  }

  public void setPilotID(Integer pilotID) {
    this.pilotID = pilotID;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (primanota != null ? primanota.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Bank)) {
      return false;
    }
    Bank other = (Bank) object;
    if ((this.primanota == null && other.primanota != null) || (this.primanota != null && !this.primanota.equals(other.primanota))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.Bank[ primanota=" + primanota + " ]";
  }
  
}
