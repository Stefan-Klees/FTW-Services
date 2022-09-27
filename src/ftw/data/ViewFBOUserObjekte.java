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
@Table(name = "viewFBOUserObjekte")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "ViewFBOUserObjekte.findAll", query = "SELECT v FROM ViewFBOUserObjekte v")})
public class ViewFBOUserObjekte implements Serializable {

  private static final long serialVersionUID = 1L;
  @Column(name = "idUser")
  private Integer idUser;
  @Basic(optional = false)
  @Column(name = "userName")
  private String userName;
  @Column(name = "name")
  private String name;
  @Column(name = "icao")
  private String icao;
  @Column(name = "objektName")
  private String objektName;
  @Column(name = "anzahlRouten")
  private Integer anzahlRouten;
  @Column(name = "anzahlStellplaetze")
  private Integer anzahlStellplaetze;
  @Column(name = "tankstelle")
  private Boolean tankstelle;
  @Column(name = "fbo")
  private Boolean fbo;
  @Column(name = "mietPreis")
  private Integer mietPreis;
  @Column(name = "anzahlPersonal")
  private Integer anzahlPersonal;
  @Column(name = "servicehangar")
  private Boolean servicehangar;
  @Column(name = "tankstelleMaxFuelKG")
  private Integer tankstelleMaxFuelKG;
  @Column(name = "abfertigungsTerminal")
  private Boolean abfertigungsTerminal;
  @Column(name = "terminalMaxPax")
  private Integer terminalMaxPax;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "preisAVGAS")
  private Double preisAVGAS;
  @Column(name = "einkaufsPreisAVGAS")
  private Double einkaufsPreisAVGAS;
  @Column(name = "bestandAVGASkg")
  private Integer bestandAVGASkg;
  @Column(name = "preisJETA")
  private Double preisJETA;
  @Column(name = "bestandJETAkg")
  private Integer bestandJETAkg;
  @Column(name = "einkaufsPreisJETA")
  private Double einkaufsPreisJETA;
  @Column(name = "mietbeginn")
  @Temporal(TemporalType.DATE)
  private Date mietbeginn;
  @Column(name = "letzteMietzahlung")
  @Temporal(TemporalType.DATE)
  private Date letzteMietzahlung;
  @Column(name = "faelligkeitNaechsteMiete")
  @Temporal(TemporalType.DATE)
  private Date faelligkeitNaechsteMiete;
  @Column(name = "preisArbeitseinheit")
  private Double preisArbeitseinheit;
  @Basic(optional = false)
  @Column(name = "iduserfbo")
  @Id
  private int iduserfbo;
  @Column(name = "bankKonto")
  private String bankKonto;
  @Column(name = "kontoName")
  private String kontoName;
  @Column(name = "mahnStufe")
  private Integer mahnStufe;
  @Column(name = "terminalGebuehrInProzent")
  private Double terminalGebuehrInProzent;
  @Column(name = "kostenstelle")
  private Integer kostenstelle;

  public ViewFBOUserObjekte() {
  }

  public Integer getIdUser() {
    return idUser;
  }

  public void setIdUser(Integer idUser) {
    this.idUser = idUser;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public String getObjektName() {
    return objektName;
  }

  public void setObjektName(String objektName) {
    this.objektName = objektName;
  }

  public Integer getAnzahlRouten() {
    return anzahlRouten;
  }

  public void setAnzahlRouten(Integer anzahlRouten) {
    this.anzahlRouten = anzahlRouten;
  }

  public Integer getAnzahlStellplaetze() {
    return anzahlStellplaetze;
  }

  public void setAnzahlStellplaetze(Integer anzahlStellplaetze) {
    this.anzahlStellplaetze = anzahlStellplaetze;
  }

  public Boolean getTankstelle() {
    return tankstelle;
  }

  public void setTankstelle(Boolean tankstelle) {
    this.tankstelle = tankstelle;
  }

  public Boolean getFbo() {
    return fbo;
  }

  public void setFbo(Boolean fbo) {
    this.fbo = fbo;
  }

  public Integer getMietPreis() {
    return mietPreis;
  }

  public void setMietPreis(Integer mietPreis) {
    this.mietPreis = mietPreis;
  }

  public Integer getAnzahlPersonal() {
    return anzahlPersonal;
  }

  public void setAnzahlPersonal(Integer anzahlPersonal) {
    this.anzahlPersonal = anzahlPersonal;
  }

  public Boolean getServicehangar() {
    return servicehangar;
  }

  public void setServicehangar(Boolean servicehangar) {
    this.servicehangar = servicehangar;
  }

  public Integer getTankstelleMaxFuelKG() {
    return tankstelleMaxFuelKG;
  }

  public void setTankstelleMaxFuelKG(Integer tankstelleMaxFuelKG) {
    this.tankstelleMaxFuelKG = tankstelleMaxFuelKG;
  }

  public Boolean getAbfertigungsTerminal() {
    return abfertigungsTerminal;
  }

  public void setAbfertigungsTerminal(Boolean abfertigungsTerminal) {
    this.abfertigungsTerminal = abfertigungsTerminal;
  }

  public Integer getTerminalMaxPax() {
    return terminalMaxPax;
  }

  public void setTerminalMaxPax(Integer terminalMaxPax) {
    this.terminalMaxPax = terminalMaxPax;
  }

  public Double getPreisAVGAS() {
    return preisAVGAS;
  }

  public void setPreisAVGAS(Double preisAVGAS) {
    this.preisAVGAS = preisAVGAS;
  }

  public Double getEinkaufsPreisAVGAS() {
    return einkaufsPreisAVGAS;
  }

  public void setEinkaufsPreisAVGAS(Double einkaufsPreisAVGAS) {
    this.einkaufsPreisAVGAS = einkaufsPreisAVGAS;
  }

  public Integer getBestandAVGASkg() {
    return bestandAVGASkg;
  }

  public void setBestandAVGASkg(Integer bestandAVGASkg) {
    this.bestandAVGASkg = bestandAVGASkg;
  }

  public Double getPreisJETA() {
    return preisJETA;
  }

  public void setPreisJETA(Double preisJETA) {
    this.preisJETA = preisJETA;
  }

  public Integer getBestandJETAkg() {
    return bestandJETAkg;
  }

  public void setBestandJETAkg(Integer bestandJETAkg) {
    this.bestandJETAkg = bestandJETAkg;
  }

  public Double getEinkaufsPreisJETA() {
    return einkaufsPreisJETA;
  }

  public void setEinkaufsPreisJETA(Double einkaufsPreisJETA) {
    this.einkaufsPreisJETA = einkaufsPreisJETA;
  }

  public Date getMietbeginn() {
    return mietbeginn;
  }

  public void setMietbeginn(Date mietbeginn) {
    this.mietbeginn = mietbeginn;
  }

  public Date getLetzteMietzahlung() {
    return letzteMietzahlung;
  }

  public void setLetzteMietzahlung(Date letzteMietzahlung) {
    this.letzteMietzahlung = letzteMietzahlung;
  }

  public Date getFaelligkeitNaechsteMiete() {
    return faelligkeitNaechsteMiete;
  }

  public void setFaelligkeitNaechsteMiete(Date faelligkeitNaechsteMiete) {
    this.faelligkeitNaechsteMiete = faelligkeitNaechsteMiete;
  }

  public Double getPreisArbeitseinheit() {
    return preisArbeitseinheit;
  }

  public void setPreisArbeitseinheit(Double preisArbeitseinheit) {
    this.preisArbeitseinheit = preisArbeitseinheit;
  }

  public int getIduserfbo() {
    return iduserfbo;
  }

  public void setIduserfbo(int iduserfbo) {
    this.iduserfbo = iduserfbo;
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

  public Integer getMahnStufe() {
    return mahnStufe;
  }

  public void setMahnStufe(Integer mahnStufe) {
    this.mahnStufe = mahnStufe;
  }

  public Double getTerminalGebuehrInProzent() {
    return terminalGebuehrInProzent;
  }

  public void setTerminalGebuehrInProzent(Double terminalGebuehrInProzent) {
    this.terminalGebuehrInProzent = terminalGebuehrInProzent;
  }

  public Integer getKostenstelle() {
    return kostenstelle;
  }

  public void setKostenstelle(Integer kostenstelle) {
    this.kostenstelle = kostenstelle;
  }
  
}
