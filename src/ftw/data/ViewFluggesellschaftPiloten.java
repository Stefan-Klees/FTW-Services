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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stefan Klees
 */
@Entity
@Table(name = "ViewFluggesellschaftPiloten")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "ViewFluggesellschaftPiloten.findAll", query = "SELECT v FROM ViewFluggesellschaftPiloten v")})
public class ViewFluggesellschaftPiloten implements Serializable {

  private static final long serialVersionUID = 1L;
  @Basic(optional = false)
  @Column(name = "idIndex")
  @Id
  private int idIndex;
  @Basic(optional = false)
  @Column(name = "idFluggesellschaft")
  private int idFluggesellschaft;
  @Column(name = "name")
  private String name;
  @Column(name = "besitzerName")
  private String besitzerName;
  @Basic(optional = false)
  @Column(name = "idUser")
  private int idUser;

  public ViewFluggesellschaftPiloten() {
  }

  public int getIdIndex() {
    return idIndex;
  }

  public void setIdIndex(int idIndex) {
    this.idIndex = idIndex;
  }

  public int getIdFluggesellschaft() {
    return idFluggesellschaft;
  }

  public void setIdFluggesellschaft(int idFluggesellschaft) {
    this.idFluggesellschaft = idFluggesellschaft;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBesitzerName() {
    return besitzerName;
  }

  public void setBesitzerName(String besitzerName) {
    this.besitzerName = besitzerName;
  }

  public int getIdUser() {
    return idUser;
  }

  public void setIdUser(int idUser) {
    this.idUser = idUser;
  }
  
}
