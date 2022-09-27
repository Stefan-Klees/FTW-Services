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
package ftw;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan Klees
 */
public class Ftw {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    if (args.length == 0) {
      System.out.println("Keine Argumente angegeben");
      System.out.println("Gültige Argumente:");
      System.out.println("Tagesrouten, Bestellungen, Mietvertraege, Systempilot, Flugzeugmietzeiten, AusfuehrungsZaehlerRouten, Terminrouten, DeleteAuftraege");
    } else {

      if (args[0].equals("AusfuehrungsZaehlerRouten")) {
        try {
          RoutenAbarbeiten.AusfuehrungsZaehlerZuruecksetzen();
        } catch (Exception ex) {
          Logger.getLogger(Ftw.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else if (args[0].equals("Terminrouten")) {
        try {
          RoutenAbarbeiten.TerminRoutenVerarbeiten();
        } catch (Exception ex) {
          Logger.getLogger(Ftw.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else if (args[0].equals("Tagesrouten")) {
        try {
          RoutenAbarbeiten.TagesRoutenVerarbeiten();
        } catch (Exception ex) {
          Logger.getLogger(Ftw.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else if (args[0].equals("Bestellungen")) {
        try {
          BestellungenAusfuehren.onBestellungenPruefen();
          BestellungenAusfuehren.onServiceHangarBestellungen();
          BestellungenAusfuehren.onReparaturFTWFlugzeugeAusfuehren();
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
          Logger.getLogger(Ftw.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else if (args[0].equals("Mietvertraege")) {
        try {
          MietvertraegeAbarbeiten.onMietverträgePruefen();
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
          Logger.getLogger(Ftw.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else if (args[0].equals("Systempilot")) {
        try {
          SystemPiolotTransfer.onTransferEndePruefen();
          SystemPiolotTransfer.UmbauSitzePruefen();
          SystemPiolotTransfer.abgelaufeneJobsLoeschen();
          SystemPiolotTransfer.onPruefeDarfKonvertieren();
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
          Logger.getLogger(Ftw.class.getName()).log(Level.SEVERE, null, ex);
        }

      } else if (args[0].equals("Flugzeugmietzeiten")) {
        try {
          FlugzeugMietZeiten.onFlugzeugeMietzeitenPruefen();
          FlugzeugMietZeiten.onHangarRaeumen();

          // ************ Terminueberweisungen
          BestellungenAusfuehren.onTerminUeberweisungen();

        } catch (NullPointerException | IndexOutOfBoundsException ex) {
          Logger.getLogger(Ftw.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else if (args[0].equals("DeleteAuftraege")) {
        try {
          SystemPiolotTransfer.deleteExpiredAssignment();
          SystemPiolotTransfer.deleteCharterSperren();
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
          Logger.getLogger(Ftw.class.getName()).log(Level.SEVERE, null, ex);
        }
      }

    }
    System.exit(0);
  }

}
