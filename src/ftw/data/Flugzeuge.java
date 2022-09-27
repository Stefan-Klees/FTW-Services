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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stefan Klees
 */
@Entity
@Table(name = "Flugzeuge")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Flugzeuge.findAll", query = "SELECT f FROM Flugzeuge f")})
public class Flugzeuge implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idFlugzeug")
  private Integer idFlugzeug;
  @Column(name = "erstflug")
  private Integer erstflug;
  @Column(name = "produziertBis")
  private Integer produziertBis;
  @Column(name = "hersteller")
  private String hersteller;
  @Column(name = "herstellerICAO")
  private String herstellerICAO;
  @Column(name = "type")
  private String type;
  @Column(name = "icaoFlugzeugcode")
  private String icaoFlugzeugcode;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "verkaufspreis")
  private Double verkaufspreis;
  @Column(name = "herstellerpreis")
  private Double herstellerpreis;
  @Column(name = "sitzeEconomy")
  private Integer sitzeEconomy;
  @Column(name = "sitzeBusinessClass")
  private Integer sitzeBusinessClass;
  @Column(name = "besatzung")
  private Integer besatzung;
  @Column(name = "flugBegleiter")
  private Integer flugBegleiter;
  @Column(name = "payload")
  private Integer payload;
  @Column(name = "cargo")
  private Integer cargo;
  @Column(name = "hoechstAbfluggewicht")
  private Integer hoechstAbfluggewicht;
  @Column(name = "leergewicht")
  private Integer leergewicht;
  @Column(name = "antriebsart")
  private Integer antriebsart;
  @Column(name = "triebwerkstype")
  private String triebwerkstype;
  @Column(name = "triebwerkspreis")
  private Double triebwerkspreis;
  @Column(name = "anzahltriebwerke")
  private Integer anzahltriebwerke;
  @Column(name = "treibstoffkapazitaet")
  private Integer treibstoffkapazitaet;
  @Column(name = "treibstoffArt")
  private Integer treibstoffArt;
  @Column(name = "verbrauchStunde")
  private Integer verbrauchStunde;
  @Column(name = "reisegeschwindigkeitTAS")
  private Integer reisegeschwindigkeitTAS;
  @Column(name = "hoechstgeschwindigkeitTAS")
  private Integer hoechstgeschwindigkeitTAS;
  @Column(name = "steigleistung")
  private Integer steigleistung;
  @Column(name = "startstreckeBeiMTOW")
  private Integer startstreckeBeiMTOW;
  @Column(name = "maxLandegewicht")
  private Integer maxLandegewicht;
  @Column(name = "maxReichweite")
  private Integer maxReichweite;
  @Column(name = "maxFlughoehe")
  private Integer maxFlughoehe;
  @Column(name = "mindestLandebahnLaenge")
  private Integer mindestLandebahnLaenge;
  @Column(name = "vApp")
  private Integer vApp;
  @Column(name = "vlSpeed")
  private Integer vlSpeed;
  @Column(name = "laenge")
  private Integer laenge;
  @Column(name = "spannweite")
  private Integer spannweite;
  @Column(name = "maxAnzahlZuBelgenderSitze")
  private Integer maxAnzahlZuBelgenderSitze;
  @Column(name = "lizenz")
  private String lizenz;
  @Column(name = "typeRating")
  private String typeRating;
  @Column(name = "typeRatingKostenStd")
  private Double typeRatingKostenStd;
  @Column(name = "typeRatingMinStd")
  private Integer typeRatingMinStd;
  @Column(name = "symbolUrl")
  private String symbolUrl;
  @Column(name = "fsxFreeDownloadUrl")
  private String fsxFreeDownloadUrl;
  @Column(name = "fs9FreeDownloadUrl")
  private String fs9FreeDownloadUrl;
  @Column(name = "p3dFreeDownloadUrl")
  private String p3dFreeDownloadUrl;
  @Column(name = "xplaneFreeDownloadUrl")
  private String xplaneFreeDownloadUrl;
  @Column(name = "fsxPaywareDownloadUrl")
  private String fsxPaywareDownloadUrl;
  @Column(name = "fs9PaywareDownloadUrl")
  private String fs9PaywareDownloadUrl;
  @Column(name = "p3dPayware3DownloadUrl")
  private String p3dPayware3DownloadUrl;
  @Column(name = "xplanePaywareDownloadUrl")
  private String xplanePaywareDownloadUrl;
  @Lob
  @Column(name = "bilderUrl")
  private String bilderUrl;
  @Column(name = "isUserEdit")
  private Boolean isUserEdit;
  @Column(name = "inProduktion")
  private Boolean inProduktion;
  @Column(name = "airframe")
  private Integer airframe;
  @Lob
  @Column(name = "bemerkungen")
  private String bemerkungen;
  @Column(name = "standardMietpreis")
  private Double standardMietpreis;
  @Column(name = "maximumZeroFullWeight")
  private Integer maximumZeroFullWeight;
  @Column(name = "auslieferung")
  private Boolean auslieferung;
  @Column(name = "flugzeugArt")
  private String flugzeugArt;
  @Column(name = "fixkosten")
  private Double fixkosten;
  @Column(name = "langstreckenflugzeug")
  private Boolean langstreckenflugzeug;
  @Column(name = "kalkulatorischerStundensatz")
  private Double kalkulatorischerStundensatz;

  public Flugzeuge() {
  }

  public Flugzeuge(Integer idFlugzeug) {
    this.idFlugzeug = idFlugzeug;
  }

  public Integer getIdFlugzeug() {
    return idFlugzeug;
  }

  public void setIdFlugzeug(Integer idFlugzeug) {
    this.idFlugzeug = idFlugzeug;
  }

  public Integer getErstflug() {
    return erstflug;
  }

  public void setErstflug(Integer erstflug) {
    this.erstflug = erstflug;
  }

  public Integer getProduziertBis() {
    return produziertBis;
  }

  public void setProduziertBis(Integer produziertBis) {
    this.produziertBis = produziertBis;
  }

  public String getHersteller() {
    return hersteller;
  }

  public void setHersteller(String hersteller) {
    this.hersteller = hersteller;
  }

  public String getHerstellerICAO() {
    return herstellerICAO;
  }

  public void setHerstellerICAO(String herstellerICAO) {
    this.herstellerICAO = herstellerICAO;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getIcaoFlugzeugcode() {
    return icaoFlugzeugcode;
  }

  public void setIcaoFlugzeugcode(String icaoFlugzeugcode) {
    this.icaoFlugzeugcode = icaoFlugzeugcode;
  }

  public Double getVerkaufspreis() {
    return verkaufspreis;
  }

  public void setVerkaufspreis(Double verkaufspreis) {
    this.verkaufspreis = verkaufspreis;
  }

  public Double getHerstellerpreis() {
    return herstellerpreis;
  }

  public void setHerstellerpreis(Double herstellerpreis) {
    this.herstellerpreis = herstellerpreis;
  }

  public Integer getSitzeEconomy() {
    return sitzeEconomy;
  }

  public void setSitzeEconomy(Integer sitzeEconomy) {
    this.sitzeEconomy = sitzeEconomy;
  }

  public Integer getSitzeBusinessClass() {
    return sitzeBusinessClass;
  }

  public void setSitzeBusinessClass(Integer sitzeBusinessClass) {
    this.sitzeBusinessClass = sitzeBusinessClass;
  }

  public Integer getBesatzung() {
    return besatzung;
  }

  public void setBesatzung(Integer besatzung) {
    this.besatzung = besatzung;
  }

  public Integer getFlugBegleiter() {
    return flugBegleiter;
  }

  public void setFlugBegleiter(Integer flugBegleiter) {
    this.flugBegleiter = flugBegleiter;
  }

  public Integer getPayload() {
    return payload;
  }

  public void setPayload(Integer payload) {
    this.payload = payload;
  }

  public Integer getCargo() {
    return cargo;
  }

  public void setCargo(Integer cargo) {
    this.cargo = cargo;
  }

  public Integer getHoechstAbfluggewicht() {
    return hoechstAbfluggewicht;
  }

  public void setHoechstAbfluggewicht(Integer hoechstAbfluggewicht) {
    this.hoechstAbfluggewicht = hoechstAbfluggewicht;
  }

  public Integer getLeergewicht() {
    return leergewicht;
  }

  public void setLeergewicht(Integer leergewicht) {
    this.leergewicht = leergewicht;
  }

  public Integer getAntriebsart() {
    return antriebsart;
  }

  public void setAntriebsart(Integer antriebsart) {
    this.antriebsart = antriebsart;
  }

  public String getTriebwerkstype() {
    return triebwerkstype;
  }

  public void setTriebwerkstype(String triebwerkstype) {
    this.triebwerkstype = triebwerkstype;
  }

  public Double getTriebwerkspreis() {
    return triebwerkspreis;
  }

  public void setTriebwerkspreis(Double triebwerkspreis) {
    this.triebwerkspreis = triebwerkspreis;
  }

  public Integer getAnzahltriebwerke() {
    return anzahltriebwerke;
  }

  public void setAnzahltriebwerke(Integer anzahltriebwerke) {
    this.anzahltriebwerke = anzahltriebwerke;
  }

  public Integer getTreibstoffkapazitaet() {
    return treibstoffkapazitaet;
  }

  public void setTreibstoffkapazitaet(Integer treibstoffkapazitaet) {
    this.treibstoffkapazitaet = treibstoffkapazitaet;
  }

  public Integer getTreibstoffArt() {
    return treibstoffArt;
  }

  public void setTreibstoffArt(Integer treibstoffArt) {
    this.treibstoffArt = treibstoffArt;
  }

  public Integer getVerbrauchStunde() {
    return verbrauchStunde;
  }

  public void setVerbrauchStunde(Integer verbrauchStunde) {
    this.verbrauchStunde = verbrauchStunde;
  }

  public Integer getReisegeschwindigkeitTAS() {
    return reisegeschwindigkeitTAS;
  }

  public void setReisegeschwindigkeitTAS(Integer reisegeschwindigkeitTAS) {
    this.reisegeschwindigkeitTAS = reisegeschwindigkeitTAS;
  }

  public Integer getHoechstgeschwindigkeitTAS() {
    return hoechstgeschwindigkeitTAS;
  }

  public void setHoechstgeschwindigkeitTAS(Integer hoechstgeschwindigkeitTAS) {
    this.hoechstgeschwindigkeitTAS = hoechstgeschwindigkeitTAS;
  }

  public Integer getSteigleistung() {
    return steigleistung;
  }

  public void setSteigleistung(Integer steigleistung) {
    this.steigleistung = steigleistung;
  }

  public Integer getStartstreckeBeiMTOW() {
    return startstreckeBeiMTOW;
  }

  public void setStartstreckeBeiMTOW(Integer startstreckeBeiMTOW) {
    this.startstreckeBeiMTOW = startstreckeBeiMTOW;
  }

  public Integer getMaxLandegewicht() {
    return maxLandegewicht;
  }

  public void setMaxLandegewicht(Integer maxLandegewicht) {
    this.maxLandegewicht = maxLandegewicht;
  }

  public Integer getMaxReichweite() {
    return maxReichweite;
  }

  public void setMaxReichweite(Integer maxReichweite) {
    this.maxReichweite = maxReichweite;
  }

  public Integer getMaxFlughoehe() {
    return maxFlughoehe;
  }

  public void setMaxFlughoehe(Integer maxFlughoehe) {
    this.maxFlughoehe = maxFlughoehe;
  }

  public Integer getMindestLandebahnLaenge() {
    return mindestLandebahnLaenge;
  }

  public void setMindestLandebahnLaenge(Integer mindestLandebahnLaenge) {
    this.mindestLandebahnLaenge = mindestLandebahnLaenge;
  }

  public Integer getVApp() {
    return vApp;
  }

  public void setVApp(Integer vApp) {
    this.vApp = vApp;
  }

  public Integer getVlSpeed() {
    return vlSpeed;
  }

  public void setVlSpeed(Integer vlSpeed) {
    this.vlSpeed = vlSpeed;
  }

  public Integer getLaenge() {
    return laenge;
  }

  public void setLaenge(Integer laenge) {
    this.laenge = laenge;
  }

  public Integer getSpannweite() {
    return spannweite;
  }

  public void setSpannweite(Integer spannweite) {
    this.spannweite = spannweite;
  }

  public Integer getMaxAnzahlZuBelgenderSitze() {
    return maxAnzahlZuBelgenderSitze;
  }

  public void setMaxAnzahlZuBelgenderSitze(Integer maxAnzahlZuBelgenderSitze) {
    this.maxAnzahlZuBelgenderSitze = maxAnzahlZuBelgenderSitze;
  }

  public String getLizenz() {
    return lizenz;
  }

  public void setLizenz(String lizenz) {
    this.lizenz = lizenz;
  }

  public String getTypeRating() {
    return typeRating;
  }

  public void setTypeRating(String typeRating) {
    this.typeRating = typeRating;
  }

  public Double getTypeRatingKostenStd() {
    return typeRatingKostenStd;
  }

  public void setTypeRatingKostenStd(Double typeRatingKostenStd) {
    this.typeRatingKostenStd = typeRatingKostenStd;
  }

  public Integer getTypeRatingMinStd() {
    return typeRatingMinStd;
  }

  public void setTypeRatingMinStd(Integer typeRatingMinStd) {
    this.typeRatingMinStd = typeRatingMinStd;
  }

  public String getSymbolUrl() {
    return symbolUrl;
  }

  public void setSymbolUrl(String symbolUrl) {
    this.symbolUrl = symbolUrl;
  }

  public String getFsxFreeDownloadUrl() {
    return fsxFreeDownloadUrl;
  }

  public void setFsxFreeDownloadUrl(String fsxFreeDownloadUrl) {
    this.fsxFreeDownloadUrl = fsxFreeDownloadUrl;
  }

  public String getFs9FreeDownloadUrl() {
    return fs9FreeDownloadUrl;
  }

  public void setFs9FreeDownloadUrl(String fs9FreeDownloadUrl) {
    this.fs9FreeDownloadUrl = fs9FreeDownloadUrl;
  }

  public String getP3dFreeDownloadUrl() {
    return p3dFreeDownloadUrl;
  }

  public void setP3dFreeDownloadUrl(String p3dFreeDownloadUrl) {
    this.p3dFreeDownloadUrl = p3dFreeDownloadUrl;
  }

  public String getXplaneFreeDownloadUrl() {
    return xplaneFreeDownloadUrl;
  }

  public void setXplaneFreeDownloadUrl(String xplaneFreeDownloadUrl) {
    this.xplaneFreeDownloadUrl = xplaneFreeDownloadUrl;
  }

  public String getFsxPaywareDownloadUrl() {
    return fsxPaywareDownloadUrl;
  }

  public void setFsxPaywareDownloadUrl(String fsxPaywareDownloadUrl) {
    this.fsxPaywareDownloadUrl = fsxPaywareDownloadUrl;
  }

  public String getFs9PaywareDownloadUrl() {
    return fs9PaywareDownloadUrl;
  }

  public void setFs9PaywareDownloadUrl(String fs9PaywareDownloadUrl) {
    this.fs9PaywareDownloadUrl = fs9PaywareDownloadUrl;
  }

  public String getP3dPayware3DownloadUrl() {
    return p3dPayware3DownloadUrl;
  }

  public void setP3dPayware3DownloadUrl(String p3dPayware3DownloadUrl) {
    this.p3dPayware3DownloadUrl = p3dPayware3DownloadUrl;
  }

  public String getXplanePaywareDownloadUrl() {
    return xplanePaywareDownloadUrl;
  }

  public void setXplanePaywareDownloadUrl(String xplanePaywareDownloadUrl) {
    this.xplanePaywareDownloadUrl = xplanePaywareDownloadUrl;
  }

  public String getBilderUrl() {
    return bilderUrl;
  }

  public void setBilderUrl(String bilderUrl) {
    this.bilderUrl = bilderUrl;
  }

  public Boolean getIsUserEdit() {
    return isUserEdit;
  }

  public void setIsUserEdit(Boolean isUserEdit) {
    this.isUserEdit = isUserEdit;
  }

  public Boolean getInProduktion() {
    return inProduktion;
  }

  public void setInProduktion(Boolean inProduktion) {
    this.inProduktion = inProduktion;
  }

  public Integer getAirframe() {
    return airframe;
  }

  public void setAirframe(Integer airframe) {
    this.airframe = airframe;
  }

  public String getBemerkungen() {
    return bemerkungen;
  }

  public void setBemerkungen(String bemerkungen) {
    this.bemerkungen = bemerkungen;
  }

  public Double getStandardMietpreis() {
    return standardMietpreis;
  }

  public void setStandardMietpreis(Double standardMietpreis) {
    this.standardMietpreis = standardMietpreis;
  }

  public Integer getMaximumZeroFullWeight() {
    return maximumZeroFullWeight;
  }

  public void setMaximumZeroFullWeight(Integer maximumZeroFullWeight) {
    this.maximumZeroFullWeight = maximumZeroFullWeight;
  }

  public Boolean getAuslieferung() {
    return auslieferung;
  }

  public void setAuslieferung(Boolean auslieferung) {
    this.auslieferung = auslieferung;
  }

  public String getFlugzeugArt() {
    return flugzeugArt;
  }

  public void setFlugzeugArt(String flugzeugArt) {
    this.flugzeugArt = flugzeugArt;
  }

  public Double getFixkosten() {
    return fixkosten;
  }

  public void setFixkosten(Double fixkosten) {
    this.fixkosten = fixkosten;
  }

  public Boolean getLangstreckenflugzeug() {
    return langstreckenflugzeug;
  }

  public void setLangstreckenflugzeug(Boolean langstreckenflugzeug) {
    this.langstreckenflugzeug = langstreckenflugzeug;
  }

  public Double getKalkulatorischerStundensatz() {
    return kalkulatorischerStundensatz;
  }

  public void setKalkulatorischerStundensatz(Double kalkulatorischerStundensatz) {
    this.kalkulatorischerStundensatz = kalkulatorischerStundensatz;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idFlugzeug != null ? idFlugzeug.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Flugzeuge)) {
      return false;
    }
    Flugzeuge other = (Flugzeuge) object;
    if ((this.idFlugzeug == null && other.idFlugzeug != null) || (this.idFlugzeug != null && !this.idFlugzeug.equals(other.idFlugzeug))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.Flugzeuge[ idFlugzeug=" + idFlugzeug + " ]";
  }
  
}
