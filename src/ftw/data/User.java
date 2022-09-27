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
@Table(name = "User")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User implements Serializable {

  @Column(name = "fluggesellschaftManagerID")
  private Integer fluggesellschaftManagerID;
  @Column(name = "lizenz")
  private String lizenz;
  @Column(name = "funktion")
  private String funktion;
  @Column(name = "iconSize")
  private Integer iconSize;
  @Column(name = "standort")
  private String standort;
  @Column(name = "rangabzeichen")
  private String rangabzeichen;
  @Column(name = "flugzeitenFG")
  private Integer flugzeitenFG;

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idUser")
  private Integer idUser;
  @Column(name = "created")
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;
  @Column(name = "lastlogon")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastlogon;
  @Basic(optional = false)
  @Column(name = "name")
  private String name;
  @Column(name = "email")
  private String email;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "cashmoney")
  private Double cashmoney;
  @Column(name = "cashbank")
  private Double cashbank;
  @Column(name = "comment")
  private String comment;
  @Lob
  @Column(name = "groups")
  private String groups;
  @Column(name = "url")
  private String url;
  @Column(name = "loanlimit")
  private Double loanlimit;
  @Column(name = "exposure")
  private Integer exposure;
  @Column(name = "dateformat")
  private String dateformat;
  @Lob
  @Column(name = "banlists")
  private String banlists;
  @Column(name = "zinsen")
  private Double zinsen;
  @Column(name = "readaccesskey")
  private String readaccesskey;
  @Column(name = "writeaccesskey")
  private String writeaccesskey;
  @Column(name = "password")
  private String password;
  @Column(name = "theme")
  private String theme;
  @Column(name = "gesperrt")
  private Boolean gesperrt;
  @Column(name = "isActive")
  private Boolean isActive;
  @Column(name = "rolle")
  private String rolle;
  @Column(name = "flightmiles")
  private Integer flightmiles;
  @Column(name = "flights")
  private Integer flights;
  @Column(name = "flighttime")
  private Integer flighttime;
  @Column(name = "flightpaxes")
  private Integer flightpaxes;
  @Column(name = "flightcargo")
  private Integer flightcargo;
  @Column(name = "sprache")
  private String sprache;
  @Column(name = "online")
  private Boolean online;
  @Column(name = "banner")
  private String banner;
  @Column(name = "allowBenutzerEdit")
  private Boolean allowBenutzerEdit;
  @Column(name = "allowFlugzeugeEdit")
  private Boolean allowFlugzeugeEdit;
  @Column(name = "allowFlughafenEdit")
  private Boolean allowFlughafenEdit;
  @Column(name = "allowGeschichtenEdit")
  private Boolean allowGeschichtenEdit;
  @Column(name = "allowNewsEdit")
  private Boolean allowNewsEdit;
  @Column(name = "allowToolsOpen")
  private Boolean allowToolsOpen;
  @Column(name = "allowAdminOpen")
  private Boolean allowAdminOpen;
  @Column(name = "bankKonto")
  private String bankKonto;
  @Column(name = "zeitZone")
  private String zeitZone;

  public User() {
  }

  public User(Integer idUser) {
    this.idUser = idUser;
  }

  public User(Integer idUser, String name) {
    this.idUser = idUser;
    this.name = name;
  }

  public Integer getIdUser() {
    return idUser;
  }

  public void setIdUser(Integer idUser) {
    this.idUser = idUser;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getLastlogon() {
    return lastlogon;
  }

  public void setLastlogon(Date lastlogon) {
    this.lastlogon = lastlogon;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Double getCashmoney() {
    return cashmoney;
  }

  public void setCashmoney(Double cashmoney) {
    this.cashmoney = cashmoney;
  }

  public Double getCashbank() {
    return cashbank;
  }

  public void setCashbank(Double cashbank) {
    this.cashbank = cashbank;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getGroups() {
    return groups;
  }

  public void setGroups(String groups) {
    this.groups = groups;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Double getLoanlimit() {
    return loanlimit;
  }

  public void setLoanlimit(Double loanlimit) {
    this.loanlimit = loanlimit;
  }

  public Integer getExposure() {
    return exposure;
  }

  public void setExposure(Integer exposure) {
    this.exposure = exposure;
  }

  public String getDateformat() {
    return dateformat;
  }

  public void setDateformat(String dateformat) {
    this.dateformat = dateformat;
  }

  public String getBanlists() {
    return banlists;
  }

  public void setBanlists(String banlists) {
    this.banlists = banlists;
  }

  public Double getZinsen() {
    return zinsen;
  }

  public void setZinsen(Double zinsen) {
    this.zinsen = zinsen;
  }

  public String getReadaccesskey() {
    return readaccesskey;
  }

  public void setReadaccesskey(String readaccesskey) {
    this.readaccesskey = readaccesskey;
  }

  public String getWriteaccesskey() {
    return writeaccesskey;
  }

  public void setWriteaccesskey(String writeaccesskey) {
    this.writeaccesskey = writeaccesskey;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public Boolean getGesperrt() {
    return gesperrt;
  }

  public void setGesperrt(Boolean gesperrt) {
    this.gesperrt = gesperrt;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public String getRolle() {
    return rolle;
  }

  public void setRolle(String rolle) {
    this.rolle = rolle;
  }

  public Integer getFlightmiles() {
    return flightmiles;
  }

  public void setFlightmiles(Integer flightmiles) {
    this.flightmiles = flightmiles;
  }

  public Integer getFlights() {
    return flights;
  }

  public void setFlights(Integer flights) {
    this.flights = flights;
  }

  public Integer getFlighttime() {
    return flighttime;
  }

  public void setFlighttime(Integer flighttime) {
    this.flighttime = flighttime;
  }

  public Integer getFlightpaxes() {
    return flightpaxes;
  }

  public void setFlightpaxes(Integer flightpaxes) {
    this.flightpaxes = flightpaxes;
  }

  public Integer getFlightcargo() {
    return flightcargo;
  }

  public void setFlightcargo(Integer flightcargo) {
    this.flightcargo = flightcargo;
  }

  public String getSprache() {
    return sprache;
  }

  public void setSprache(String sprache) {
    this.sprache = sprache;
  }

  public Boolean getOnline() {
    return online;
  }

  public void setOnline(Boolean online) {
    this.online = online;
  }

  public String getBanner() {
    return banner;
  }

  public void setBanner(String banner) {
    this.banner = banner;
  }

  public Boolean getAllowBenutzerEdit() {
    return allowBenutzerEdit;
  }

  public void setAllowBenutzerEdit(Boolean allowBenutzerEdit) {
    this.allowBenutzerEdit = allowBenutzerEdit;
  }

  public Boolean getAllowFlugzeugeEdit() {
    return allowFlugzeugeEdit;
  }

  public void setAllowFlugzeugeEdit(Boolean allowFlugzeugeEdit) {
    this.allowFlugzeugeEdit = allowFlugzeugeEdit;
  }

  public Boolean getAllowFlughafenEdit() {
    return allowFlughafenEdit;
  }

  public void setAllowFlughafenEdit(Boolean allowFlughafenEdit) {
    this.allowFlughafenEdit = allowFlughafenEdit;
  }

  public Boolean getAllowGeschichtenEdit() {
    return allowGeschichtenEdit;
  }

  public void setAllowGeschichtenEdit(Boolean allowGeschichtenEdit) {
    this.allowGeschichtenEdit = allowGeschichtenEdit;
  }

  public Boolean getAllowNewsEdit() {
    return allowNewsEdit;
  }

  public void setAllowNewsEdit(Boolean allowNewsEdit) {
    this.allowNewsEdit = allowNewsEdit;
  }

  public Boolean getAllowToolsOpen() {
    return allowToolsOpen;
  }

  public void setAllowToolsOpen(Boolean allowToolsOpen) {
    this.allowToolsOpen = allowToolsOpen;
  }

  public Boolean getAllowAdminOpen() {
    return allowAdminOpen;
  }

  public void setAllowAdminOpen(Boolean allowAdminOpen) {
    this.allowAdminOpen = allowAdminOpen;
  }

  public String getBankKonto() {
    return bankKonto;
  }

  public void setBankKonto(String bankKonto) {
    this.bankKonto = bankKonto;
  }

  public String getZeitZone() {
    return zeitZone;
  }

  public void setZeitZone(String zeitZone) {
    this.zeitZone = zeitZone;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idUser != null ? idUser.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof User)) {
      return false;
    }
    User other = (User) object;
    if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.User[ idUser=" + idUser + " ]";
  }

  public Integer getFluggesellschaftManagerID() {
    return fluggesellschaftManagerID;
  }

  public void setFluggesellschaftManagerID(Integer fluggesellschaftManagerID) {
    this.fluggesellschaftManagerID = fluggesellschaftManagerID;
  }

  public String getLizenz() {
    return lizenz;
  }

  public void setLizenz(String lizenz) {
    this.lizenz = lizenz;
  }

  public String getFunktion() {
    return funktion;
  }

  public void setFunktion(String funktion) {
    this.funktion = funktion;
  }

  public Integer getIconSize() {
    return iconSize;
  }

  public void setIconSize(Integer iconSize) {
    this.iconSize = iconSize;
  }

  public String getStandort() {
    return standort;
  }

  public void setStandort(String standort) {
    this.standort = standort;
  }

  public String getRangabzeichen() {
    return rangabzeichen;
  }

  public void setRangabzeichen(String rangabzeichen) {
    this.rangabzeichen = rangabzeichen;
  }

  public Integer getFlugzeitenFG() {
    return flugzeitenFG;
  }

  public void setFlugzeitenFG(Integer flugzeitenFG) {
    this.flugzeitenFG = flugzeitenFG;
  }
  
}
