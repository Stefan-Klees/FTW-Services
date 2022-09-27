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
@Table(name = "Story")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Story.findAll", query = "SELECT s FROM Story s")})
public class Story implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idStory")
  private Integer idStory;
  @Lob
  @Column(name = "storyText")
  private String storyText;
  @Column(name = "verfasser")
  private String verfasser;
  @Column(name = "bezeichnung")
  private String bezeichnung;
  @Column(name = "datumzeit")
  @Temporal(TemporalType.TIMESTAMP)
  private Date datumzeit;
  @Column(name = "storyklasse")
  private Integer storyklasse;
  @Column(name = "sprache")
  private String sprache;
  @Column(name = "ablaufzeit")
  private Integer ablaufzeit;
  @Column(name = "artdestransports")
  private Integer artdestransports;
  @Column(name = "flugzeuglizenz")
  private Integer flugzeuglizenz;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "entferungInGrad")
  private Double entferungInGrad;
  @Column(name = "mindestEnfernung")
  private Integer mindestEnfernung;

  public Story() {
  }

  public Story(Integer idStory) {
    this.idStory = idStory;
  }

  public Integer getIdStory() {
    return idStory;
  }

  public void setIdStory(Integer idStory) {
    this.idStory = idStory;
  }

  public String getStoryText() {
    return storyText;
  }

  public void setStoryText(String storyText) {
    this.storyText = storyText;
  }

  public String getVerfasser() {
    return verfasser;
  }

  public void setVerfasser(String verfasser) {
    this.verfasser = verfasser;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public void setBezeichnung(String bezeichnung) {
    this.bezeichnung = bezeichnung;
  }

  public Date getDatumzeit() {
    return datumzeit;
  }

  public void setDatumzeit(Date datumzeit) {
    this.datumzeit = datumzeit;
  }

  public Integer getStoryklasse() {
    return storyklasse;
  }

  public void setStoryklasse(Integer storyklasse) {
    this.storyklasse = storyklasse;
  }

  public String getSprache() {
    return sprache;
  }

  public void setSprache(String sprache) {
    this.sprache = sprache;
  }

  public Integer getAblaufzeit() {
    return ablaufzeit;
  }

  public void setAblaufzeit(Integer ablaufzeit) {
    this.ablaufzeit = ablaufzeit;
  }

  public Integer getArtdestransports() {
    return artdestransports;
  }

  public void setArtdestransports(Integer artdestransports) {
    this.artdestransports = artdestransports;
  }

  public Integer getFlugzeuglizenz() {
    return flugzeuglizenz;
  }

  public void setFlugzeuglizenz(Integer flugzeuglizenz) {
    this.flugzeuglizenz = flugzeuglizenz;
  }

  public Double getEntferungInGrad() {
    return entferungInGrad;
  }

  public void setEntferungInGrad(Double entferungInGrad) {
    this.entferungInGrad = entferungInGrad;
  }

  public Integer getMindestEnfernung() {
    return mindestEnfernung;
  }

  public void setMindestEnfernung(Integer mindestEnfernung) {
    this.mindestEnfernung = mindestEnfernung;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idStory != null ? idStory.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Story)) {
      return false;
    }
    Story other = (Story) object;
    if ((this.idStory == null && other.idStory != null) || (this.idStory != null && !this.idStory.equals(other.idStory))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.Story[ idStory=" + idStory + " ]";
  }
  
}
