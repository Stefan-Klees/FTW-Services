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
@Table(name = "ViewFlugzeugeMietKauf")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "ViewFlugzeugeMietKauf.findAll", query = "SELECT v FROM ViewFlugzeugeMietKauf v")})
public class ViewFlugzeugeMietKauf implements Serializable {

  @Column(name = "erstflug")
  private Integer erstflug;
  @Column(name = "lizenz")
  private String lizenz;
  @Column(name = "typeRating")
  private String typeRating;
  @Column(name = "typeRatingKostenStd")
  private Double typeRatingKostenStd;
  @Column(name = "typeRatingMinStd")
  private Integer typeRatingMinStd;
  @Column(name = "zustand")
  private Double zustand;

  private static final long serialVersionUID = 1L;
  @Column(name = "idFlugzeug")
  private Integer idFlugzeug;
  @Basic(optional = false)
  @Column(name = "idMietKauf")
  @Id
  private int idMietKauf;
  @Column(name = "baujahr")
  private Integer baujahr;
  @Column(name = "hersteller")
  private String hersteller;
  @Column(name = "herstellerICAO")
  private String herstellerICAO;
  @Column(name = "type")
  private String type;
  @Column(name = "antriebsart")
  private Integer antriebsart;
  @Column(name = "triebwerkstype")
  private String triebwerkstype;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "triebwerkspreis")
  private Double triebwerkspreis;
  @Column(name = "anzahltriebwerke")
  private Integer anzahltriebwerke;
  @Column(name = "inProduktion")
  private Boolean inProduktion;
  @Column(name = "sitzeEconomy")
  private Integer sitzeEconomy;
  @Column(name = "sitzeBusinessClass")
  private Integer sitzeBusinessClass;
  @Column(name = "besatzung")
  private Integer besatzung;
  @Column(name = "flugbegleiter")
  private Integer flugbegleiter;
  @Column(name = "payload")
  private Integer payload;
  @Column(name = "cargo")
  private Integer cargo;
  @Column(name = "hoechstAbfluggewicht")
  private Integer hoechstAbfluggewicht;
  @Column(name = "reisegeschwindigkeitTAS")
  private Integer reisegeschwindigkeitTAS;
  @Column(name = "startstreckeBeiMTOW")
  private Integer startstreckeBeiMTOW;
  @Column(name = "maxLandegewicht")
  private Integer maxLandegewicht;
  @Column(name = "mindestLandebahnLaenge")
  private Integer mindestLandebahnLaenge;
  @Column(name = "maxAnzahlZuBelgenderSitze")
  private Integer maxAnzahlZuBelgenderSitze;
  @Column(name = "treibstoffArt")
  private Integer treibstoffArt;
  @Column(name = "treibstoffkapazitaet")
  private Integer treibstoffkapazitaet;
  @Column(name = "leergewicht")
  private Integer leergewicht;
  @Column(name = "verbrauchStunde")
  private Integer verbrauchStunde;
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
  @Lob
  @Column(name = "bemerkungen")
  private String bemerkungen;
  @Column(name = "airframe")
  private Integer airframe;
  @Column(name = "registrierung")
  private String registrierung;
  @Column(name = "idflugzeugBesitzer")
  private Integer idflugzeugBesitzer;
  @Column(name = "heimatICAO")
  private String heimatICAO;
  @Column(name = "aktuellePositionICAO")
  private String aktuellePositionICAO;
  @Column(name = "istGesperrt")
  private Boolean istGesperrt;
  @Column(name = "istGesperrtSeit")
  @Temporal(TemporalType.TIMESTAMP)
  private Date istGesperrtSeit;
  @Column(name = "istGesperrtBis")
  @Temporal(TemporalType.TIMESTAMP)
  private Date istGesperrtBis;
  @Column(name = "mietpreisTrocken")
  private Double mietpreisTrocken;
  @Column(name = "mietpreisNass")
  private Double mietpreisNass;
  @Column(name = "aktuelleTankfuellung")
  private Integer aktuelleTankfuellung;
  @Column(name = "verkaufsPreis")
  private Double verkaufsPreis;
  @Column(name = "maxMietzeitMinuten")
  private Integer maxMietzeitMinuten;
  @Column(name = "abgeflogenVonICAO")
  private String abgeflogenVonICAO;
  @Column(name = "bonusFuerRueckfuehrung")
  private Double bonusFuerRueckfuehrung;
  @Column(name = "letzterCheckMinuten")
  private Integer letzterCheckMinuten;
  @Column(name = "gesamtMaschinenLaufzeitMinuten")
  private Integer gesamtMaschinenLaufzeitMinuten;
  @Column(name = "gesamtAlterDesFlugzeugsMinuten")
  private Integer gesamtAlterDesFlugzeugsMinuten;
  @Column(name = "maschinenLaufzeitMinuten")
  private Integer maschinenLaufzeitMinuten;
  @Column(name = "istCheckDurchUserErlaubt")
  private Boolean istCheckDurchUserErlaubt;
  @Column(name = "istMietbar")
  private Boolean istMietbar;
  @Column(name = "istZuVerkaufen")
  private Boolean istZuVerkaufen;
  @Column(name = "idUserDerFlugzeugGesperrtHat")
  private Integer idUserDerFlugzeugGesperrtHat;
  @Column(name = "gesamtFlugzeit")
  private Integer gesamtFlugzeit;
  @Column(name = "gesamtEntfernung")
  private Integer gesamtEntfernung;
  @Column(name = "gesamtFluege")
  private Integer gesamtFluege;
  @Column(name = "gesamtTransportiertePassagiere")
  private Integer gesamtTransportiertePassagiere;
  @Column(name = "gesamtTransportiertesCargo")
  private Integer gesamtTransportiertesCargo;
  @Column(name = "bankkontoBesitzer")
  private String bankkontoBesitzer;
  @Column(name = "letzterSpritPreis")
  private Double letzterSpritPreis;
  @Column(name = "isAktive")
  private Boolean isAktive;
  @Column(name = "idSitzKonfiguration")
  private Integer idSitzKonfiguration;
  @Column(name = "istInDerLuft")
  private Boolean istInDerLuft;
  @Column(name = "pfand")
  private Integer pfand;
  @Column(name = "kostenstelle")
  private Integer kostenstelle;
  @Column(name = "airportName")
  private String airportName;
  @Column(name = "airportCountry")
  private String airportCountry;
  @Column(name = "icaoFlugzeugcode")
  private String icaoFlugzeugcode;
  @Column(name = "fluggesellschaftName")
  private String fluggesellschaftName;
  @Column(name = "icaoHomeFluggesellschaft")
  private String icaoHomeFluggesellschaft;
  @Column(name = "fluggesellschaftID")
  private Integer fluggesellschaftID;
  @Column(name = "userID")
  private Integer userID;
  @Column(name = "userName")
  private String userName;

  public ViewFlugzeugeMietKauf() {
  }

  public Integer getIdFlugzeug() {
    return idFlugzeug;
  }

  public void setIdFlugzeug(Integer idFlugzeug) {
    this.idFlugzeug = idFlugzeug;
  }

  public int getIdMietKauf() {
    return idMietKauf;
  }

  public void setIdMietKauf(int idMietKauf) {
    this.idMietKauf = idMietKauf;
  }

  public Integer getBaujahr() {
    return baujahr;
  }

  public void setBaujahr(Integer baujahr) {
    this.baujahr = baujahr;
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

  public Boolean getInProduktion() {
    return inProduktion;
  }

  public void setInProduktion(Boolean inProduktion) {
    this.inProduktion = inProduktion;
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

  public Integer getFlugbegleiter() {
    return flugbegleiter;
  }

  public void setFlugbegleiter(Integer flugbegleiter) {
    this.flugbegleiter = flugbegleiter;
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

  public Integer getReisegeschwindigkeitTAS() {
    return reisegeschwindigkeitTAS;
  }

  public void setReisegeschwindigkeitTAS(Integer reisegeschwindigkeitTAS) {
    this.reisegeschwindigkeitTAS = reisegeschwindigkeitTAS;
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

  public Integer getMindestLandebahnLaenge() {
    return mindestLandebahnLaenge;
  }

  public void setMindestLandebahnLaenge(Integer mindestLandebahnLaenge) {
    this.mindestLandebahnLaenge = mindestLandebahnLaenge;
  }

  public Integer getMaxAnzahlZuBelgenderSitze() {
    return maxAnzahlZuBelgenderSitze;
  }

  public void setMaxAnzahlZuBelgenderSitze(Integer maxAnzahlZuBelgenderSitze) {
    this.maxAnzahlZuBelgenderSitze = maxAnzahlZuBelgenderSitze;
  }


  public Integer getTreibstoffArt() {
    return treibstoffArt;
  }

  public void setTreibstoffArt(Integer treibstoffArt) {
    this.treibstoffArt = treibstoffArt;
  }

  public Integer getTreibstoffkapazitaet() {
    return treibstoffkapazitaet;
  }

  public void setTreibstoffkapazitaet(Integer treibstoffkapazitaet) {
    this.treibstoffkapazitaet = treibstoffkapazitaet;
  }

  public Integer getLeergewicht() {
    return leergewicht;
  }

  public void setLeergewicht(Integer leergewicht) {
    this.leergewicht = leergewicht;
  }

  public Integer getVerbrauchStunde() {
    return verbrauchStunde;
  }

  public void setVerbrauchStunde(Integer verbrauchStunde) {
    this.verbrauchStunde = verbrauchStunde;
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

  public String getBemerkungen() {
    return bemerkungen;
  }

  public void setBemerkungen(String bemerkungen) {
    this.bemerkungen = bemerkungen;
  }

  public Integer getAirframe() {
    return airframe;
  }

  public void setAirframe(Integer airframe) {
    this.airframe = airframe;
  }

  public String getRegistrierung() {
    return registrierung;
  }

  public void setRegistrierung(String registrierung) {
    this.registrierung = registrierung;
  }

  public Integer getIdflugzeugBesitzer() {
    return idflugzeugBesitzer;
  }

  public void setIdflugzeugBesitzer(Integer idflugzeugBesitzer) {
    this.idflugzeugBesitzer = idflugzeugBesitzer;
  }

  public String getHeimatICAO() {
    return heimatICAO;
  }

  public void setHeimatICAO(String heimatICAO) {
    this.heimatICAO = heimatICAO;
  }

  public String getAktuellePositionICAO() {
    return aktuellePositionICAO;
  }

  public void setAktuellePositionICAO(String aktuellePositionICAO) {
    this.aktuellePositionICAO = aktuellePositionICAO;
  }

  public Boolean getIstGesperrt() {
    return istGesperrt;
  }

  public void setIstGesperrt(Boolean istGesperrt) {
    this.istGesperrt = istGesperrt;
  }

  public Date getIstGesperrtSeit() {
    return istGesperrtSeit;
  }

  public void setIstGesperrtSeit(Date istGesperrtSeit) {
    this.istGesperrtSeit = istGesperrtSeit;
  }

  public Date getIstGesperrtBis() {
    return istGesperrtBis;
  }

  public void setIstGesperrtBis(Date istGesperrtBis) {
    this.istGesperrtBis = istGesperrtBis;
  }

  public Double getMietpreisTrocken() {
    return mietpreisTrocken;
  }

  public void setMietpreisTrocken(Double mietpreisTrocken) {
    this.mietpreisTrocken = mietpreisTrocken;
  }

  public Double getMietpreisNass() {
    return mietpreisNass;
  }

  public void setMietpreisNass(Double mietpreisNass) {
    this.mietpreisNass = mietpreisNass;
  }

  public Integer getAktuelleTankfuellung() {
    return aktuelleTankfuellung;
  }

  public void setAktuelleTankfuellung(Integer aktuelleTankfuellung) {
    this.aktuelleTankfuellung = aktuelleTankfuellung;
  }

  public Double getVerkaufsPreis() {
    return verkaufsPreis;
  }

  public void setVerkaufsPreis(Double verkaufsPreis) {
    this.verkaufsPreis = verkaufsPreis;
  }

  public Integer getMaxMietzeitMinuten() {
    return maxMietzeitMinuten;
  }

  public void setMaxMietzeitMinuten(Integer maxMietzeitMinuten) {
    this.maxMietzeitMinuten = maxMietzeitMinuten;
  }

  public String getAbgeflogenVonICAO() {
    return abgeflogenVonICAO;
  }

  public void setAbgeflogenVonICAO(String abgeflogenVonICAO) {
    this.abgeflogenVonICAO = abgeflogenVonICAO;
  }

  public Double getBonusFuerRueckfuehrung() {
    return bonusFuerRueckfuehrung;
  }

  public void setBonusFuerRueckfuehrung(Double bonusFuerRueckfuehrung) {
    this.bonusFuerRueckfuehrung = bonusFuerRueckfuehrung;
  }

  public Integer getLetzterCheckMinuten() {
    return letzterCheckMinuten;
  }

  public void setLetzterCheckMinuten(Integer letzterCheckMinuten) {
    this.letzterCheckMinuten = letzterCheckMinuten;
  }

  public Integer getGesamtMaschinenLaufzeitMinuten() {
    return gesamtMaschinenLaufzeitMinuten;
  }

  public void setGesamtMaschinenLaufzeitMinuten(Integer gesamtMaschinenLaufzeitMinuten) {
    this.gesamtMaschinenLaufzeitMinuten = gesamtMaschinenLaufzeitMinuten;
  }

  public Integer getGesamtAlterDesFlugzeugsMinuten() {
    return gesamtAlterDesFlugzeugsMinuten;
  }

  public void setGesamtAlterDesFlugzeugsMinuten(Integer gesamtAlterDesFlugzeugsMinuten) {
    this.gesamtAlterDesFlugzeugsMinuten = gesamtAlterDesFlugzeugsMinuten;
  }

  public Integer getMaschinenLaufzeitMinuten() {
    return maschinenLaufzeitMinuten;
  }

  public void setMaschinenLaufzeitMinuten(Integer maschinenLaufzeitMinuten) {
    this.maschinenLaufzeitMinuten = maschinenLaufzeitMinuten;
  }

  public Boolean getIstCheckDurchUserErlaubt() {
    return istCheckDurchUserErlaubt;
  }

  public void setIstCheckDurchUserErlaubt(Boolean istCheckDurchUserErlaubt) {
    this.istCheckDurchUserErlaubt = istCheckDurchUserErlaubt;
  }

  public Boolean getIstMietbar() {
    return istMietbar;
  }

  public void setIstMietbar(Boolean istMietbar) {
    this.istMietbar = istMietbar;
  }

  public Boolean getIstZuVerkaufen() {
    return istZuVerkaufen;
  }

  public void setIstZuVerkaufen(Boolean istZuVerkaufen) {
    this.istZuVerkaufen = istZuVerkaufen;
  }

  public Integer getIdUserDerFlugzeugGesperrtHat() {
    return idUserDerFlugzeugGesperrtHat;
  }

  public void setIdUserDerFlugzeugGesperrtHat(Integer idUserDerFlugzeugGesperrtHat) {
    this.idUserDerFlugzeugGesperrtHat = idUserDerFlugzeugGesperrtHat;
  }

  public Integer getGesamtFlugzeit() {
    return gesamtFlugzeit;
  }

  public void setGesamtFlugzeit(Integer gesamtFlugzeit) {
    this.gesamtFlugzeit = gesamtFlugzeit;
  }

  public Integer getGesamtEntfernung() {
    return gesamtEntfernung;
  }

  public void setGesamtEntfernung(Integer gesamtEntfernung) {
    this.gesamtEntfernung = gesamtEntfernung;
  }

  public Integer getGesamtFluege() {
    return gesamtFluege;
  }

  public void setGesamtFluege(Integer gesamtFluege) {
    this.gesamtFluege = gesamtFluege;
  }

  public Integer getGesamtTransportiertePassagiere() {
    return gesamtTransportiertePassagiere;
  }

  public void setGesamtTransportiertePassagiere(Integer gesamtTransportiertePassagiere) {
    this.gesamtTransportiertePassagiere = gesamtTransportiertePassagiere;
  }

  public Integer getGesamtTransportiertesCargo() {
    return gesamtTransportiertesCargo;
  }

  public void setGesamtTransportiertesCargo(Integer gesamtTransportiertesCargo) {
    this.gesamtTransportiertesCargo = gesamtTransportiertesCargo;
  }

  public String getBankkontoBesitzer() {
    return bankkontoBesitzer;
  }

  public void setBankkontoBesitzer(String bankkontoBesitzer) {
    this.bankkontoBesitzer = bankkontoBesitzer;
  }

  public Double getLetzterSpritPreis() {
    return letzterSpritPreis;
  }

  public void setLetzterSpritPreis(Double letzterSpritPreis) {
    this.letzterSpritPreis = letzterSpritPreis;
  }

  public Boolean getIsAktive() {
    return isAktive;
  }

  public void setIsAktive(Boolean isAktive) {
    this.isAktive = isAktive;
  }

  public Integer getIdSitzKonfiguration() {
    return idSitzKonfiguration;
  }

  public void setIdSitzKonfiguration(Integer idSitzKonfiguration) {
    this.idSitzKonfiguration = idSitzKonfiguration;
  }

  public Boolean getIstInDerLuft() {
    return istInDerLuft;
  }

  public void setIstInDerLuft(Boolean istInDerLuft) {
    this.istInDerLuft = istInDerLuft;
  }

  public Integer getPfand() {
    return pfand;
  }

  public void setPfand(Integer pfand) {
    this.pfand = pfand;
  }

  public Integer getKostenstelle() {
    return kostenstelle;
  }

  public void setKostenstelle(Integer kostenstelle) {
    this.kostenstelle = kostenstelle;
  }

  public String getAirportName() {
    return airportName;
  }

  public void setAirportName(String airportName) {
    this.airportName = airportName;
  }

  public String getAirportCountry() {
    return airportCountry;
  }

  public void setAirportCountry(String airportCountry) {
    this.airportCountry = airportCountry;
  }

  public String getIcaoFlugzeugcode() {
    return icaoFlugzeugcode;
  }

  public void setIcaoFlugzeugcode(String icaoFlugzeugcode) {
    this.icaoFlugzeugcode = icaoFlugzeugcode;
  }

  public String getFluggesellschaftName() {
    return fluggesellschaftName;
  }

  public void setFluggesellschaftName(String fluggesellschaftName) {
    this.fluggesellschaftName = fluggesellschaftName;
  }

  public String getIcaoHomeFluggesellschaft() {
    return icaoHomeFluggesellschaft;
  }

  public void setIcaoHomeFluggesellschaft(String icaoHomeFluggesellschaft) {
    this.icaoHomeFluggesellschaft = icaoHomeFluggesellschaft;
  }

  public Integer getFluggesellschaftID() {
    return fluggesellschaftID;
  }

  public void setFluggesellschaftID(Integer fluggesellschaftID) {
    this.fluggesellschaftID = fluggesellschaftID;
  }

  public Integer getUserID() {
    return userID;
  }

  public void setUserID(Integer userID) {
    this.userID = userID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getErstflug() {
    return erstflug;
  }

  public void setErstflug(Integer erstflug) {
    this.erstflug = erstflug;
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

  public Double getZustand() {
    return zustand;
  }

  public void setZustand(Double zustand) {
    this.zustand = zustand;
  }
  
}
