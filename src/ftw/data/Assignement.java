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
@Table(name = "assignement")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Assignement.findAll", query = "SELECT a FROM Assignement a")})
public class Assignement implements Serializable {

  @Column(name = "lizenz")
  private String lizenz;
  @Column(name = "originalKonvertiertDurchAirline")
  private Integer originalKonvertiertDurchAirline;

  @Column(name = "langstrecke")
  private Boolean langstrecke;

  @Column(name = "konvertiert")
  private Boolean konvertiert;

  @Column(name = "idjob")
  private Integer idjob;

  @Column(name = "gesplittet")
  private Boolean gesplittet;

  @Column(name = "verlaengert")
  private Boolean verlaengert;

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idassignement")
  private Integer idassignement;
  @Column(name = "creation")
  @Temporal(TemporalType.TIMESTAMP)
  private Date creation;
  @Column(name = "expires")
  @Temporal(TemporalType.TIMESTAMP)
  private Date expires;
  @Column(name = "commodity")
  private String commodity;
  @Column(name = "idaircraft")
  private Integer idaircraft;
  @Column(name = "from_icao")
  private String fromIcao;
  @Column(name = "to_icao")
  private String toIcao;
  @Column(name = "location_icao")
  private String locationIcao;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "pay")
  private Double pay;
  @Column(name = "type")
  private Integer type;
  @Column(name = "ammount")
  private Integer ammount;
  @Column(name = "distance")
  private Integer distance;
  @Column(name = "active")
  private Integer active;
  @Column(name = "bearing")
  private Integer bearing;
  @Column(name = "gruppe")
  private String gruppe;
  @Column(name = "idgroup")
  private Integer idgroup;
  @Column(name = "pilotfee")
  private Integer pilotfee;
  @Column(name = "units")
  private String units;
  @Column(name = "direct")
  private Integer direct;
  @Column(name = "noext")
  private Integer noext;
  @Lob
  @Column(name = "comment")
  private String comment;
  @Column(name = "idcommodity")
  private Integer idcommodity;
  @Column(name = "idowner")
  private Integer idowner;
  @Column(name = "userlock")
  private Integer userlock;
  @Column(name = "createdbyuser")
  private String createdbyuser;
  @Column(name = "ptassigment")
  private String ptassigment;
  @Column(name = "mpttax")
  private Integer mpttax;
  @Column(name = "daysclaimedactive")
  private Integer daysclaimedactive;
  @Column(name = "idAirline")
  private Integer idAirline;
  @Column(name = "idRoute")
  private Integer idRoute;
  @Column(name = "oeffentlich")
  private Integer oeffentlich;
  @Column(name = "bonusoeffentlich")
  private Double bonusoeffentlich;
  @Column(name = "bonusclosed")
  private Double bonusclosed;
  @Column(name = "nameairline")
  private String nameairline;
  @Column(name = "fromName")
  private String fromName;
  @Column(name = "toName")
  private String toName;
  @Column(name = "flugrouteName")
  private String flugrouteName;
  @Column(name = "fromAirportLandCity")
  private String fromAirportLandCity;
  @Column(name = "toAirportLandCity")
  private String toAirportLandCity;
  @Column(name = "airlineLogo")
  private String airlineLogo;
  @Column(name = "ceoAirline")
  private String ceoAirline;
  @Column(name = "routenArt")
  private Integer routenArt;
  @Column(name = "isBusinessClass")
  private Integer isBusinessClass;
  @Column(name = "gepaeck")
  private Integer gepaeck;
  @Column(name = "provision")
  private Double provision;
  @Column(name = "idTerminal")
  private Integer idTerminal;
  @Column(name = "idFBO")
  private Integer idFBO;
  @Column(name = "icaoCodeFluggesellschaft")
  private String icaoCodeFluggesellschaft;
  @Column(name = "gewichtPax")
  private Integer gewichtPax;

  public Assignement() {
  }

  public Assignement(Integer idassignement) {
    this.idassignement = idassignement;
  }

  public Integer getIdassignement() {
    return idassignement;
  }

  public void setIdassignement(Integer idassignement) {
    this.idassignement = idassignement;
  }

  public Date getCreation() {
    return creation;
  }

  public void setCreation(Date creation) {
    this.creation = creation;
  }

  public Date getExpires() {
    return expires;
  }

  public void setExpires(Date expires) {
    this.expires = expires;
  }

  public String getCommodity() {
    return commodity;
  }

  public void setCommodity(String commodity) {
    this.commodity = commodity;
  }

  public Integer getIdaircraft() {
    return idaircraft;
  }

  public void setIdaircraft(Integer idaircraft) {
    this.idaircraft = idaircraft;
  }

  public String getFromIcao() {
    return fromIcao;
  }

  public void setFromIcao(String fromIcao) {
    this.fromIcao = fromIcao;
  }

  public String getToIcao() {
    return toIcao;
  }

  public void setToIcao(String toIcao) {
    this.toIcao = toIcao;
  }

  public String getLocationIcao() {
    return locationIcao;
  }

  public void setLocationIcao(String locationIcao) {
    this.locationIcao = locationIcao;
  }

  public Double getPay() {
    return pay;
  }

  public void setPay(Double pay) {
    this.pay = pay;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getAmmount() {
    return ammount;
  }

  public void setAmmount(Integer ammount) {
    this.ammount = ammount;
  }

  public Integer getDistance() {
    return distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  public Integer getBearing() {
    return bearing;
  }

  public void setBearing(Integer bearing) {
    this.bearing = bearing;
  }

  public String getGruppe() {
    return gruppe;
  }

  public void setGruppe(String gruppe) {
    this.gruppe = gruppe;
  }

  public Integer getIdgroup() {
    return idgroup;
  }

  public void setIdgroup(Integer idgroup) {
    this.idgroup = idgroup;
  }

  public Integer getPilotfee() {
    return pilotfee;
  }

  public void setPilotfee(Integer pilotfee) {
    this.pilotfee = pilotfee;
  }

  public String getUnits() {
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

  public Integer getDirect() {
    return direct;
  }

  public void setDirect(Integer direct) {
    this.direct = direct;
  }

  public Integer getNoext() {
    return noext;
  }

  public void setNoext(Integer noext) {
    this.noext = noext;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Integer getIdcommodity() {
    return idcommodity;
  }

  public void setIdcommodity(Integer idcommodity) {
    this.idcommodity = idcommodity;
  }

  public Integer getIdowner() {
    return idowner;
  }

  public void setIdowner(Integer idowner) {
    this.idowner = idowner;
  }

  public Integer getUserlock() {
    return userlock;
  }

  public void setUserlock(Integer userlock) {
    this.userlock = userlock;
  }

  public String getCreatedbyuser() {
    return createdbyuser;
  }

  public void setCreatedbyuser(String createdbyuser) {
    this.createdbyuser = createdbyuser;
  }

  public String getPtassigment() {
    return ptassigment;
  }

  public void setPtassigment(String ptassigment) {
    this.ptassigment = ptassigment;
  }

  public Integer getMpttax() {
    return mpttax;
  }

  public void setMpttax(Integer mpttax) {
    this.mpttax = mpttax;
  }

  public Integer getDaysclaimedactive() {
    return daysclaimedactive;
  }

  public void setDaysclaimedactive(Integer daysclaimedactive) {
    this.daysclaimedactive = daysclaimedactive;
  }

  public Integer getIdAirline() {
    return idAirline;
  }

  public void setIdAirline(Integer idAirline) {
    this.idAirline = idAirline;
  }

  public Integer getIdRoute() {
    return idRoute;
  }

  public void setIdRoute(Integer idRoute) {
    this.idRoute = idRoute;
  }

  public Integer getOeffentlich() {
    return oeffentlich;
  }

  public void setOeffentlich(Integer oeffentlich) {
    this.oeffentlich = oeffentlich;
  }

  public Double getBonusoeffentlich() {
    return bonusoeffentlich;
  }

  public void setBonusoeffentlich(Double bonusoeffentlich) {
    this.bonusoeffentlich = bonusoeffentlich;
  }

  public Double getBonusclosed() {
    return bonusclosed;
  }

  public void setBonusclosed(Double bonusclosed) {
    this.bonusclosed = bonusclosed;
  }

  public String getNameairline() {
    return nameairline;
  }

  public void setNameairline(String nameairline) {
    this.nameairline = nameairline;
  }


  public String getFromName() {
    return fromName;
  }

  public void setFromName(String fromName) {
    this.fromName = fromName;
  }

  public String getToName() {
    return toName;
  }

  public void setToName(String toName) {
    this.toName = toName;
  }

  public String getFlugrouteName() {
    return flugrouteName;
  }

  public void setFlugrouteName(String flugrouteName) {
    this.flugrouteName = flugrouteName;
  }

  public String getFromAirportLandCity() {
    return fromAirportLandCity;
  }

  public void setFromAirportLandCity(String fromAirportLandCity) {
    this.fromAirportLandCity = fromAirportLandCity;
  }

  public String getToAirportLandCity() {
    return toAirportLandCity;
  }

  public void setToAirportLandCity(String toAirportLandCity) {
    this.toAirportLandCity = toAirportLandCity;
  }

  public String getAirlineLogo() {
    return airlineLogo;
  }

  public void setAirlineLogo(String airlineLogo) {
    this.airlineLogo = airlineLogo;
  }

  public String getCeoAirline() {
    return ceoAirline;
  }

  public void setCeoAirline(String ceoAirline) {
    this.ceoAirline = ceoAirline;
  }

  public Integer getRoutenArt() {
    return routenArt;
  }

  public void setRoutenArt(Integer routenArt) {
    this.routenArt = routenArt;
  }

  public Integer getIsBusinessClass() {
    return isBusinessClass;
  }

  public void setIsBusinessClass(Integer isBusinessClass) {
    this.isBusinessClass = isBusinessClass;
  }

  public Integer getGepaeck() {
    return gepaeck;
  }

  public void setGepaeck(Integer gepaeck) {
    this.gepaeck = gepaeck;
  }

  public Double getProvision() {
    return provision;
  }

  public void setProvision(Double provision) {
    this.provision = provision;
  }

  public Integer getIdTerminal() {
    return idTerminal;
  }

  public void setIdTerminal(Integer idTerminal) {
    this.idTerminal = idTerminal;
  }

  public Integer getIdFBO() {
    return idFBO;
  }

  public void setIdFBO(Integer idFBO) {
    this.idFBO = idFBO;
  }

  public String getIcaoCodeFluggesellschaft() {
    return icaoCodeFluggesellschaft;
  }

  public void setIcaoCodeFluggesellschaft(String icaoCodeFluggesellschaft) {
    this.icaoCodeFluggesellschaft = icaoCodeFluggesellschaft;
  }

  public Integer getGewichtPax() {
    return gewichtPax;
  }

  public void setGewichtPax(Integer gewichtPax) {
    this.gewichtPax = gewichtPax;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idassignement != null ? idassignement.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Assignement)) {
      return false;
    }
    Assignement other = (Assignement) object;
    if ((this.idassignement == null && other.idassignement != null) || (this.idassignement != null && !this.idassignement.equals(other.idassignement))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.Assignement[ idassignement=" + idassignement + " ]";
  }

  public Boolean getVerlaengert() {
    return verlaengert;
  }

  public void setVerlaengert(Boolean verlaengert) {
    this.verlaengert = verlaengert;
  }

  public Boolean getGesplittet() {
    return gesplittet;
  }

  public void setGesplittet(Boolean gesplittet) {
    this.gesplittet = gesplittet;
  }

  public Integer getIdjob() {
    return idjob;
  }

  public void setIdjob(Integer idjob) {
    this.idjob = idjob;
  }

  public Boolean getKonvertiert() {
    return konvertiert;
  }

  public void setKonvertiert(Boolean konvertiert) {
    this.konvertiert = konvertiert;
  }

  public Boolean getLangstrecke() {
    return langstrecke;
  }

  public void setLangstrecke(Boolean langstrecke) {
    this.langstrecke = langstrecke;
  }

  public String getLizenz() {
    return lizenz;
  }

  public void setLizenz(String lizenz) {
    this.lizenz = lizenz;
  }

  public Integer getOriginalKonvertiertDurchAirline() {
    return originalKonvertiertDurchAirline;
  }

  public void setOriginalKonvertiertDurchAirline(Integer originalKonvertiertDurchAirline) {
    this.originalKonvertiertDurchAirline = originalKonvertiertDurchAirline;
  }
  
}
