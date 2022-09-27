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
@Table(name = "flugzeugSperrzeiten")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "FlugzeugSperrzeiten.findAll", query = "SELECT f FROM FlugzeugSperrzeiten f")})
public class FlugzeugSperrzeiten implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idflugzeugSperrzeiten")
  private Integer idflugzeugSperrzeiten;
  @Column(name = "idFlugzeug")
  private Integer idFlugzeug;
  @Column(name = "grund")
  private String grund;
  @Column(name = "gesperrtBis")
  @Temporal(TemporalType.TIMESTAMP)
  private Date gesperrtBis;

  public FlugzeugSperrzeiten() {
  }

  public FlugzeugSperrzeiten(Integer idflugzeugSperrzeiten) {
    this.idflugzeugSperrzeiten = idflugzeugSperrzeiten;
  }

  public Integer getIdflugzeugSperrzeiten() {
    return idflugzeugSperrzeiten;
  }

  public void setIdflugzeugSperrzeiten(Integer idflugzeugSperrzeiten) {
    this.idflugzeugSperrzeiten = idflugzeugSperrzeiten;
  }

  public Integer getIdFlugzeug() {
    return idFlugzeug;
  }

  public void setIdFlugzeug(Integer idFlugzeug) {
    this.idFlugzeug = idFlugzeug;
  }

  public String getGrund() {
    return grund;
  }

  public void setGrund(String grund) {
    this.grund = grund;
  }

  public Date getGesperrtBis() {
    return gesperrtBis;
  }

  public void setGesperrtBis(Date gesperrtBis) {
    this.gesperrtBis = gesperrtBis;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idflugzeugSperrzeiten != null ? idflugzeugSperrzeiten.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof FlugzeugSperrzeiten)) {
      return false;
    }
    FlugzeugSperrzeiten other = (FlugzeugSperrzeiten) object;
    if ((this.idflugzeugSperrzeiten == null && other.idflugzeugSperrzeiten != null) || (this.idflugzeugSperrzeiten != null && !this.idflugzeugSperrzeiten.equals(other.idflugzeugSperrzeiten))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ftw.data.FlugzeugSperrzeiten[ idflugzeugSperrzeiten=" + idflugzeugSperrzeiten + " ]";
  }
  
}
