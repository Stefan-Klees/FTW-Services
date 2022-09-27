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
@Table(name = "Airport")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Airport.findAll", query = "SELECT a FROM Airport a")
  , @NamedQuery(name = "Airport.findByIdairport", query = "SELECT a FROM Airport a WHERE a.idairport = :idairport")
  , @NamedQuery(name = "Airport.findByIcao", query = "SELECT a FROM Airport a WHERE a.icao = :icao")
  , @NamedQuery(name = "Airport.findByName", query = "SELECT a FROM Airport a WHERE a.name = :name")
  , @NamedQuery(name = "Airport.findByLand", query = "SELECT a FROM Airport a WHERE a.land = :land")
  , @NamedQuery(name = "Airport.findByBundesland", query = "SELECT a FROM Airport a WHERE a.bundesland = :bundesland")
  , @NamedQuery(name = "Airport.findByStadt", query = "SELECT a FROM Airport a WHERE a.stadt = :stadt")
  , @NamedQuery(name = "Airport.findByLatitude", query = "SELECT a FROM Airport a WHERE a.latitude = :latitude")
  , @NamedQuery(name = "Airport.findByLongitude", query = "SELECT a FROM Airport a WHERE a.longitude = :longitude")
  , @NamedQuery(name = "Airport.findByHoehe", query = "SELECT a FROM Airport a WHERE a.hoehe = :hoehe")
  , @NamedQuery(name = "Airport.findByBelag", query = "SELECT a FROM Airport a WHERE a.belag = :belag")
  , @NamedQuery(name = "Airport.findByLaengsteLandeBahn", query = "SELECT a FROM Airport a WHERE a.laengsteLandeBahn = :laengsteLandeBahn")
  , @NamedQuery(name = "Airport.findByZustand", query = "SELECT a FROM Airport a WHERE a.zustand = :zustand")
  , @NamedQuery(name = "Airport.findByMaxpassagiereprotag", query = "SELECT a FROM Airport a WHERE a.maxpassagiereprotag = :maxpassagiereprotag")
  , @NamedQuery(name = "Airport.findByMaxCargo", query = "SELECT a FROM Airport a WHERE a.maxCargo = :maxCargo")
  , @NamedQuery(name = "Airport.findByKlasse", query = "SELECT a FROM Airport a WHERE a.klasse = :klasse")
  , @NamedQuery(name = "Airport.findByPreisklasse", query = "SELECT a FROM Airport a WHERE a.preisklasse = :preisklasse")
  , @NamedQuery(name = "Airport.findByFreeXPlaneSceneryUrl", query = "SELECT a FROM Airport a WHERE a.freeXPlaneSceneryUrl = :freeXPlaneSceneryUrl")
  , @NamedQuery(name = "Airport.findByFreeFSXSceneryUrl", query = "SELECT a FROM Airport a WHERE a.freeFSXSceneryUrl = :freeFSXSceneryUrl")
  , @NamedQuery(name = "Airport.findByFreeP3DSceneryUrl", query = "SELECT a FROM Airport a WHERE a.freeP3DSceneryUrl = :freeP3DSceneryUrl")
  , @NamedQuery(name = "Airport.findByPaywareXPlaneSceneryUrl", query = "SELECT a FROM Airport a WHERE a.paywareXPlaneSceneryUrl = :paywareXPlaneSceneryUrl")
  , @NamedQuery(name = "Airport.findByPaywareFSXSceneryUrl", query = "SELECT a FROM Airport a WHERE a.paywareFSXSceneryUrl = :paywareFSXSceneryUrl")
  , @NamedQuery(name = "Airport.findByPaywareP3DSceneryUrl", query = "SELECT a FROM Airport a WHERE a.paywareP3DSceneryUrl = :paywareP3DSceneryUrl")
  , @NamedQuery(name = "Airport.findByIsActiv", query = "SELECT a FROM Airport a WHERE a.isActiv = :isActiv")
  , @NamedQuery(name = "Airport.findByFreeFS9SceneryUrl", query = "SELECT a FROM Airport a WHERE a.freeFS9SceneryUrl = :freeFS9SceneryUrl")
  , @NamedQuery(name = "Airport.findByPaywareFS9SceneryUrl", query = "SELECT a FROM Airport a WHERE a.paywareFS9SceneryUrl = :paywareFS9SceneryUrl")
  , @NamedQuery(name = "Airport.findByGepflegt", query = "SELECT a FROM Airport a WHERE a.gepflegt = :gepflegt")
  , @NamedQuery(name = "Airport.findByBgVon", query = "SELECT a FROM Airport a WHERE a.bgVon = :bgVon")
  , @NamedQuery(name = "Airport.findByBgBis", query = "SELECT a FROM Airport a WHERE a.bgBis = :bgBis")
  , @NamedQuery(name = "Airport.findByLgVon", query = "SELECT a FROM Airport a WHERE a.lgVon = :lgVon")
  , @NamedQuery(name = "Airport.findByLgBis", query = "SELECT a FROM Airport a WHERE a.lgBis = :lgBis")
  , @NamedQuery(name = "Airport.findByErsterAnflug", query = "SELECT a FROM Airport a WHERE a.ersterAnflug = :ersterAnflug")
  , @NamedQuery(name = "Airport.findByTransportiertePax", query = "SELECT a FROM Airport a WHERE a.transportiertePax = :transportiertePax")
  , @NamedQuery(name = "Airport.findByTransportierteCargo", query = "SELECT a FROM Airport a WHERE a.transportierteCargo = :transportierteCargo")
  , @NamedQuery(name = "Airport.findByFluege", query = "SELECT a FROM Airport a WHERE a.fluege = :fluege")
  , @NamedQuery(name = "Airport.findByFaktor", query = "SELECT a FROM Airport a WHERE a.faktor = :faktor")})
public class Airport implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idairport")
  private Integer idairport;
  @Basic(optional = false)
  @Column(name = "icao")
  private String icao;
  @Column(name = "name")
  private String name;
  @Column(name = "land")
  private String land;
  @Column(name = "bundesland")
  private String bundesland;
  @Column(name = "stadt")
  private String stadt;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "latitude")
  private Double latitude;
  @Column(name = "longitude")
  private Double longitude;
  @Column(name = "hoehe")
  private Integer hoehe;
  @Column(name = "belag")
  private Integer belag;
  @Column(name = "laengsteLandeBahn")
  private Integer laengsteLandeBahn;
  @Column(name = "zustand")
  private Integer zustand;
  @Column(name = "maxpassagiereprotag")
  private Integer maxpassagiereprotag;
  @Column(name = "maxCargo")
  private Integer maxCargo;
  @Column(name = "klasse")
  private Integer klasse;
  @Column(name = "preisklasse")
  private Integer preisklasse;
  @Lob
  @Column(name = "bilderUrls")
  private String bilderUrls;
  @Column(name = "FreeXPlaneSceneryUrl")
  private String freeXPlaneSceneryUrl;
  @Column(name = "FreeFSXSceneryUrl")
  private String freeFSXSceneryUrl;
  @Column(name = "FreeP3DSceneryUrl")
  private String freeP3DSceneryUrl;
  @Column(name = "PaywareXPlaneSceneryUrl")
  private String paywareXPlaneSceneryUrl;
  @Column(name = "PaywareFSXSceneryUrl")
  private String paywareFSXSceneryUrl;
  @Column(name = "PaywareP3DSceneryUrl")
  private String paywareP3DSceneryUrl;
  @Column(name = "isActiv")
  private Boolean isActiv;
  @Column(name = "FreeFS9SceneryUrl")
  private String freeFS9SceneryUrl;
  @Column(name = "PaywareFS9SceneryUrl")
  private String paywareFS9SceneryUrl;
  @Lob
  @Column(name = "notem")
  private String notem;
  @Column(name = "gepflegt")
  private Boolean gepflegt;
  @Column(name = "bgVon")
  private Double bgVon;
  @Column(name = "bgBis")
  private Double bgBis;
  @Column(name = "lgVon")
  private Double lgVon;
  @Column(name = "lgBis")
  private Double lgBis;
  @Column(name = "ersterAnflug")
  @Temporal(TemporalType.DATE)
  private Date ersterAnflug;
  @Column(name = "transportiertePax")
  private Integer transportiertePax;
  @Column(name = "transportierteCargo")
  private Integer transportierteCargo;
  @Column(name = "fluege")
  private Integer fluege;
  @Column(name = "faktor")
  private Double faktor;

  public Airport() {
  }

  public Airport(Integer idairport) {
    this.idairport = idairport;
  }

  public Airport(Integer idairport, String icao) {
    this.idairport = idairport;
    this.icao = icao;
  }

  public Integer getIdairport() {
    return idairport;
  }

  public void setIdairport(Integer idairport) {
    this.idairport = idairport;
  }

  public String getIcao() {
    return icao;
  }

  public void setIcao(String icao) {
    this.icao = icao;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLand() {
    return land;
  }

  public void setLand(String land) {
    this.land = land;
  }

  public String getBundesland() {
    return bundesland;
  }

  public void setBundesland(String bundesland) {
    this.bundesland = bundesland;
  }

  public String getStadt() {
    return stadt;
  }

  public void setStadt(String stadt) {
    this.stadt = stadt;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Integer getHoehe() {
    return hoehe;
  }

  public void setHoehe(Integer hoehe) {
    this.hoehe = hoehe;
  }

  public Integer getBelag() {
    return belag;
  }

  public void setBelag(Integer belag) {
    this.belag = belag;
  }

  public Integer getLaengsteLandeBahn() {
    return laengsteLandeBahn;
  }

  public void setLaengsteLandeBahn(Integer laengsteLandeBahn) {
    this.laengsteLandeBahn = laengsteLandeBahn;
  }

  public Integer getZustand() {
    return zustand;
  }

  public void setZustand(Integer zustand) {
    this.zustand = zustand;
  }

  public Integer getMaxpassagiereprotag() {
    return maxpassagiereprotag;
  }

  public void setMaxpassagiereprotag(Integer maxpassagiereprotag) {
    this.maxpassagiereprotag = maxpassagiereprotag;
  }

  public Integer getMaxCargo() {
    return maxCargo;
  }

  public void setMaxCargo(Integer maxCargo) {
    this.maxCargo = maxCargo;
  }

  public Integer getKlasse() {
    return klasse;
  }

  public void setKlasse(Integer klasse) {
    this.klasse = klasse;
  }

  public Integer getPreisklasse() {
    return preisklasse;
  }

  public void setPreisklasse(Integer preisklasse) {
    this.preisklasse = preisklasse;
  }

  public String getBilderUrls() {
    return bilderUrls;
  }

  public void setBilderUrls(String bilderUrls) {
    this.bilderUrls = bilderUrls;
  }

  public String getFreeXPlaneSceneryUrl() {
    return freeXPlaneSceneryUrl;
  }

  public void setFreeXPlaneSceneryUrl(String freeXPlaneSceneryUrl) {
    this.freeXPlaneSceneryUrl = freeXPlaneSceneryUrl;
  }

  public String getFreeFSXSceneryUrl() {
    return freeFSXSceneryUrl;
  }

  public void setFreeFSXSceneryUrl(String freeFSXSceneryUrl) {
    this.freeFSXSceneryUrl = freeFSXSceneryUrl;
  }

  public String getFreeP3DSceneryUrl() {
    return freeP3DSceneryUrl;
  }

  public void setFreeP3DSceneryUrl(String freeP3DSceneryUrl) {
    this.freeP3DSceneryUrl = freeP3DSceneryUrl;
  }

  public String getPaywareXPlaneSceneryUrl() {
    return paywareXPlaneSceneryUrl;
  }

  public void setPaywareXPlaneSceneryUrl(String paywareXPlaneSceneryUrl) {
    this.paywareXPlaneSceneryUrl = paywareXPlaneSceneryUrl;
  }

  public String getPaywareFSXSceneryUrl() {
    return paywareFSXSceneryUrl;
  }

  public void setPaywareFSXSceneryUrl(String paywareFSXSceneryUrl) {
    this.paywareFSXSceneryUrl = paywareFSXSceneryUrl;
  }

  public String getPaywareP3DSceneryUrl() {
    return paywareP3DSceneryUrl;
  }

  public void setPaywareP3DSceneryUrl(String paywareP3DSceneryUrl) {
    this.paywareP3DSceneryUrl = paywareP3DSceneryUrl;
  }

  public Boolean getIsActiv() {
    return isActiv;
  }

  public void setIsActiv(Boolean isActiv) {
    this.isActiv = isActiv;
  }

  public String getFreeFS9SceneryUrl() {
    return freeFS9SceneryUrl;
  }

  public void setFreeFS9SceneryUrl(String freeFS9SceneryUrl) {
    this.freeFS9SceneryUrl = freeFS9SceneryUrl;
  }

  public String getPaywareFS9SceneryUrl() {
    return paywareFS9SceneryUrl;
  }

  public void setPaywareFS9SceneryUrl(String paywareFS9SceneryUrl) {
    this.paywareFS9SceneryUrl = paywareFS9SceneryUrl;
  }

  public String getNotem() {
    return notem;
  }

  public void setNotem(String notem) {
    this.notem = notem;
  }

  public Boolean getGepflegt() {
    return gepflegt;
  }

  public void setGepflegt(Boolean gepflegt) {
    this.gepflegt = gepflegt;
  }

  public Double getBgVon() {
    return bgVon;
  }

  public void setBgVon(Double bgVon) {
    this.bgVon = bgVon;
  }

  public Double getBgBis() {
    return bgBis;
  }

  public void setBgBis(Double bgBis) {
    this.bgBis = bgBis;
  }

  public Double getLgVon() {
    return lgVon;
  }

  public void setLgVon(Double lgVon) {
    this.lgVon = lgVon;
  }

  public Double getLgBis() {
    return lgBis;
  }

  public void setLgBis(Double lgBis) {
    this.lgBis = lgBis;
  }

  public Date getErsterAnflug() {
    return ersterAnflug;
  }

  public void setErsterAnflug(Date ersterAnflug) {
    this.ersterAnflug = ersterAnflug;
  }

  public Integer getTransportiertePax() {
    return transportiertePax;
  }

  public void setTransportiertePax(Integer transportiertePax) {
    this.transportiertePax = transportiertePax;
  }

  public Integer getTransportierteCargo() {
    return transportierteCargo;
  }

  public void setTransportierteCargo(Integer transportierteCargo) {
    this.transportierteCargo = transportierteCargo;
  }

  public Integer getFluege() {
    return fluege;
  }

  public void setFluege(Integer fluege) {
    this.fluege = fluege;
  }

  public Double getFaktor() {
    return faktor;
  }

  public void setFaktor(Double faktor) {
    this.faktor = faktor;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idairport != null ? idairport.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Airport)) {
      return false;
    }
    Airport other = (Airport) object;
    if ((this.idairport == null && other.idairport != null) || (this.idairport != null && !this.idairport.equals(other.idairport))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.Airport[ idairport=" + idairport + " ]";
  }
  
}
