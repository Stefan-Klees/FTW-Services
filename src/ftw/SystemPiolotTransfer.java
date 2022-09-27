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

import ftw.data.Assignement;
import ftw.data.Bank;
import ftw.data.Fluggesellschaft;
import ftw.data.Flugzeugemietkauf;
import ftw.data.Flugzeugtransfer;
import ftw.data.Jobs;
import ftw.data.Pinnwand;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Stefan Klees
 */
public class SystemPiolotTransfer {

  private static EntityManagerFactory emf = null;
  private static EntityManager em = null;

  public static void onTransferEndePruefen() {

    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    List<Flugzeugtransfer> transferliste = getFlugzeugTransferListe(new Date());
    Flugzeugemietkauf flugzeug;

    for (Flugzeugtransfer transfer : transferliste) {

      flugzeug = getFlugzeug(transfer.getIdFlugzeugMietKauf());
      flugzeug.setAktuellePositionICAO(transfer.getZielicao());
      flugzeug.setIstGesperrtBis(null);
      flugzeug.setIstGesperrtSeit(null);
      flugzeug.setIstGesperrt(false);
      flugzeug.setIstInDerLuft(false);
      flugzeug.setIdUserDerFlugzeugGesperrtHat(-1);

      String VerwendungsZweck = "Transfer Aircraft " + flugzeug.getRegistrierung() + " - " + flugzeug.getAbgeflogenVonICAO() + " with Systempilot Otto to : " + transfer.getZielicao();

      System.out.println("ftw.SystemPiolotTransfer.onTransferEndePruefen(): " + VerwendungsZweck);

      Double Betrag = transfer.getBetrag() - (transfer.getBetrag() * 2);

      SaveBankbuchung(transfer.getBankkonto(), transfer.getKontoname(), transfer.getBankkonto(), transfer.getKontoname(),
              new Date(), Betrag, "500-1000002", "**** FTW Aircraft Stock ****",
              new Date(), VerwendungsZweck, transfer.getIduser(), -1, -1, -1, -1, -1, -1, "", -1, -1, -1, -1);

      onFlugzeugMietKaufSpeichern(flugzeug);
      onTransferLoeschen(transfer);

    }

    em.close();
    emf.close();

  }

  public static void UmbauSitzePruefen() {
    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    SimpleDateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String Datum = dfFull.format(new Date());

    // ****  Flugzeug OldMiles Eintragen wie beim Entmieten
    String SqlAbfrage = "UPDATE Flugzeuge_miet_kauf, umbauSitzplatz  "
            + "SET  "
            + "    Flugzeuge_miet_kauf.oldMiles = Flugzeuge_miet_kauf.gesamtEntfernung "
            + "WHERE "
            + "    Flugzeuge_miet_kauf.idMietKauf = umbauSitzplatz.idFlugzeugMietKauf "
            + "        AND umbauSitzplatz.sperrzeit <= {ts '" + Datum + "' } ";

    em.getTransaction().begin();
    System.out.println("Abgelaufene Umbauten : Meilen eintragen: " + em.createNativeQuery(SqlAbfrage).executeUpdate());
    em.getTransaction().commit();

    SqlAbfrage = "DELETE FROM umbauSitzplatz WHERE sperrzeit <= {ts '" + Datum + "' }";

    em.getTransaction().begin();
    System.out.println("Abgelaufene Umbauten : " + em.createNativeQuery(SqlAbfrage).executeUpdate());
    em.getTransaction().commit();

    em.close();
    emf.close();

  }

  public static void deleteExpiredAssignment() {

    System.out.println("ftw.SystemPiolotTransfer.deleteExpiredAssignment() --- Beginn");
    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    SimpleDateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String Datum = dfFull.format(new Date());

    //Regress ermitteln
    List<Assignement> assi = em.createQuery("SELECT a FROM Assignement a WHERE a.idAirline > 0 AND a.expires <= :datum AND a.userlock = 0", Assignement.class)
            .setParameter("datum", new Date())
            .getResultList();

    if (assi != null) {

      Fluggesellschaft fg = null;
      for (Assignement as : assi) {

        try {
          fg = em.createQuery("SELECT f FROM Fluggesellschaft f WHERE f.idFluggesellschaft = :id", Fluggesellschaft.class)
                  .setParameter("id", as.getIdAirline())
                  .getSingleResult();
        } catch (Exception e) {
          fg = null;
        }

        if (fg != null) {

          //RoutenArt 1 = Pax
          //RoutenArt 2 = Cargo
          if (as.getRoutenArt() == 1) {
            fg.setErzeugteJobs(fg.getErzeugteJobs() + as.getAmmount());
          } else if (as.getRoutenArt() == 2) {
            fg.setErzeugtesCargo(fg.getErzeugtesCargo() + as.getAmmount());
          }

          System.out.println("Fluggesellschaft: " + fg.getName() + " Besitzer: " + fg.getBesitzerName());

          onFluggesellschaftBuchen(fg);
          
          RegressBuchen(as, fg);
        }
      }
    }

    String SqlAbfrage = "DELETE FROM assignement WHERE expires <= {ts '" + Datum + "' } and userlock=0;";

    em.getTransaction().begin();
    System.out.println("Abgelaufene Auftraege : " + em.createNativeQuery(SqlAbfrage).executeUpdate());
    em.getTransaction().commit();

    em.close();
    emf.close();

    System.out.println("ftw.SystemPiolotTransfer.deleteExpiredAssignment() --- ENDE");
  }

  private static void RegressBuchen(Assignement assi, Fluggesellschaft fg) {

    String VerwendungsZweck = "Expired Assignments - Claims for recourse: " + assi.getFlugrouteName();
    String Auftraggeber = "**** FTW BANK *****";
    String AuftraggeberKonto = "500-1000001";
    String EmpfaengerKonto = fg.getBankKonto();
    String Empfaenger = fg.getBankKontoName();

    Double Betrag = (assi.getPay() * 0.5) * -1;

    SaveBankbuchung(EmpfaengerKonto, Empfaenger, AuftraggeberKonto, Auftraggeber,
            new Date(), Betrag, EmpfaengerKonto, Empfaenger, new Date(), VerwendungsZweck, fg.getUserid(), -1, -1, -1, -1, -1, -1, "", -1, -1, -1, -1);

  }

  public static void deleteCharterSperren() {
    //Auf Charter-Aufträge prüfen
    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    SimpleDateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String Datum = dfFull.format(new Date());
    String SqlAbfrage = "DELETE FROM charterAuftrag WHERE ablaufzeit <= {ts '" + Datum + "' };";

    em.getTransaction().begin();
    System.out.println("Abgelaufene Charter Aufräge : " + em.createNativeQuery(SqlAbfrage).executeUpdate());
    em.getTransaction().commit();

    em.close();
    emf.close();

  }

  public static void abgelaufeneJobsLoeschen() {
    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();
    System.out.println("ftw.SystemPiolotTransfer.abgelaufeneJobsLoeschen()");
    SimpleDateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    List<Jobs> jobliste = getAbgelaufeneJobs(new Date());
    for (Jobs jobitem : jobliste) {
      Flugzeugemietkauf flz = getFlugzeug(jobitem.getFlugzeugid());
      if (flz != null) {
        flz.setIstGesperrt(false);
        flz.setIdUserDerFlugzeugGesperrtHat(-1);
        flz.setIstGesperrtBis(null);
        flz.setIstGesperrtSeit(null);
        onFlugzeugMietKaufSpeichern(flz);
      }
      assignementsJobID(jobitem.getIdjobs());
      System.out.println("ftw.SystemPiolotTransfer.abgelaufeneJobsLoeschen() Job geloescht > ID: " + jobitem.getIdjobs() + "  Ablaufdatum: " + dfFull.format(jobitem.getAblaufdatum()));
      abgelaufenenJobLoeschen(jobitem);
    }
    em.close();
    emf.close();
  }

  public static void onPruefeDarfKonvertieren() {
    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    String SqlAbfrage = "update flugesellschaft_piloten set darfkonvertieren=false where isnull(darfkonvertieren)";

    em.getTransaction().begin();
    System.out.println("Darf Konvertieren Null : " + em.createNativeQuery(SqlAbfrage).executeUpdate());
    em.getTransaction().commit();

    em.close();
    emf.close();
  }

  private static void createBankbuchung(Bank entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
  }

  private static void onFlugzeugMietKaufSpeichern(Flugzeugemietkauf entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
  }

  private static void onFluggesellschaftBuchen(Fluggesellschaft entity) {
    em.getTransaction().begin();
    em.merge(entity);
    em.getTransaction().commit();
  }

  private static void onTransferLoeschen(Flugzeugtransfer entity) {
    em.getTransaction().begin();
    em.remove(em.merge(entity));
    em.getTransaction().commit();
  }

  private static List<Flugzeugtransfer> getFlugzeugTransferListe(Date DatumZeit) {
    return em.createQuery("SELECT t from Flugzeugtransfer t where t.enddatum < :zeit", Flugzeugtransfer.class)
            .setParameter("zeit", DatumZeit)
            .getResultList();
  }

  private static Flugzeugemietkauf getFlugzeug(int id) {
    return em.createQuery("SELECT f from Flugzeugemietkauf f WHERE f.idMietKauf = :id", Flugzeugemietkauf.class)
            .setParameter("id", id)
            .getSingleResult();
  }

  private static void abgelaufenenJobLoeschen(Jobs entity) {
    em.getTransaction().begin();
    em.remove(em.merge(entity));
    em.getTransaction().commit();
  }

  private static void assignementsJobID(int jobid) {
    em.getTransaction().begin();
    em.createQuery("UPDATE Assignement a SET a.idjob = -1, a.idowner = -1 WHERE a.idjob = :jobid", Assignement.class)
            .setParameter("jobid", jobid)
            .executeUpdate();
    em.getTransaction().commit();
  }

  private static List<Jobs> getAbgelaufeneJobs(Date datum) {
    return em.createQuery("SELECT j from Jobs j WHERE j.ablaufdatum < :datum and j.freigabe = TRUE", Jobs.class)
            .setParameter("datum", datum)
            .getResultList();
  }

  /**
   *
   * @param Bankonto
   * @param Kontoname
   * @param AbsenderKontoNr
   * @param AbsenderKontoName
   * @param AusfuehrungsDatum
   * @param Betrag
   * @param EmpfaengerKontoNr
   * @param EmpfaengerKontoName
   * @param Ueberweisungsdatum
   * @param VerwendungsZweck
   * @param UserID
   * @param AirportID
   * @param FluggesellschaftID
   * @param FlugzeugBesitzerID
   * @param IndustrieID
   * @param LeasinggesellschaftID
   * @param TransportID
   * @param icao
   * @param objektID
   * @param FlugzeugID
   * @param kostenstelle
   * @param pilotID
   */
  private static void SaveBankbuchung(String Bankkonto, String KontoName, String AbsenderKontoNr, String AbsenderKontoName, Date AusfuehrungsDatum, double Betrag, String EmpfaengerKontoNr,
          String EmpfaengerKontoName, Date UeberweisungsDatum, String VerwendungsZweck,
          int userid, int AirportID, int FluggesellschaftID, int FlugzeugBesitzerID, int IndustrieID, int LeasinggesellschaftID, int TransportID,
          String icao, int objektID, int FlugzeugID, int Kostenstelle, int pilotID) {

    Bank newBuchung = new Bank();

    newBuchung.setBankKonto(Bankkonto);
    newBuchung.setKontoName(KontoName);
    newBuchung.setAbsenderKonto(AbsenderKontoNr);
    newBuchung.setAbsenderName(AbsenderKontoName);
    newBuchung.setEmpfaengerKonto(EmpfaengerKontoNr);
    newBuchung.setEmpfaengerName(EmpfaengerKontoName);
    newBuchung.setAusfuehrungsDatum(AusfuehrungsDatum);
    newBuchung.setUeberweisungsDatum(UeberweisungsDatum);
    newBuchung.setVerwendungsZweck(VerwendungsZweck);
    newBuchung.setBetrag(Betrag);

    newBuchung.setAirportID(AirportID);
    newBuchung.setFluggesellschaftID(FluggesellschaftID);
    newBuchung.setFlugzeugBesitzerID(FlugzeugBesitzerID);
    newBuchung.setIndustrieID(IndustrieID);
    newBuchung.setLeasinggesellschaftID(LeasinggesellschaftID);
    newBuchung.setTransportID(TransportID);
    newBuchung.setUserID(userid);
    newBuchung.setIcao(icao);
    newBuchung.setObjektID(objektID);
    newBuchung.setFlugzeugID(FlugzeugID);
    newBuchung.setKostenstelle(Kostenstelle);
    newBuchung.setPilotID(pilotID);

    createBankbuchung(newBuchung);

  }

}
