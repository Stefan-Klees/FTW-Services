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

import ftw.data.Airport;
import ftw.data.Bank;
import ftw.data.FboUserObjekte;
import ftw.data.Mail;
import ftw.data.Pinnwand;
import ftw.data.User;
import ftw.data.ViewFBOUserObjekte;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Stefan Klees
 */
public class MietvertraegeAbarbeiten {

  private static EntityManagerFactory emf = null;
  private static EntityManager em = null;

  public static void onMietverträgePruefen() {

    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    DecimalFormat df = new DecimalFormat("###,##0.00");

    Airport airport = null;
    double aufschlag = 0.0;

    System.out.println("ftw.MietvertraegeAbarbeiten.onMietverträgePruefen()");
    System.out.println("Datum : " + new Date());

    List<ViewFBOUserObjekte> mietObjekte = getFaelligeMietvertraege(new Date());

    System.out.println("Anzahl Datensätze : " + mietObjekte.size());

    Calendar naechsteMiete = Calendar.getInstance();
    naechsteMiete.add(Calendar.DATE, 30);

    for (ViewFBOUserObjekte objekte : mietObjekte) {

      int mahnStufe = 0;

      airport = getAirportData(objekte.getIcao());

      // Preiserhöhungen ermitteln
      if (airport != null) {
        //double prozente = CONF.getFlughafenAufschlag(airport.getKlasse());
        double prozente = 10.0;
        long exFbo = wievieleFBOAmFlughafen(objekte.getIcao());
        if (exFbo > 0) {
          exFbo = exFbo - 1;
        }
        aufschlag = prozente * exFbo;
      }

      if (BankSaldoUeberBankKonto(objekte.getBankKonto()) <= objekte.getMietPreis()) {
        // Kontostand reicht nicht aus um Miete zu Zahlen

        if (objekte.getMahnStufe() != null) {
          mahnStufe = objekte.getMahnStufe() + 1;
        } else {
          mahnStufe = 1;
        }

        String Betreff = "Kontostand reichte nicht aus um Miete für Objekt auszugleichen, Konto wurde überzogen";
        String Nachricht = "Kontostand reichte nicht aus um Miete für Objekt : " + objekte.getName() + " in " + objekte.getIcao() + " auszugleichen, Konto wurde überzogen und Mahnstufe auf " + objekte.getMahnStufe() + 1 + "  erhöht";
        String AnUser = objekte.getUserName();

        saveMail("**** FTW Financial Services ****", AnUser, Betreff, Nachricht);

      }

      //**** Bankbuchung 
      String UserName = objekte.getKontoName();
      String UserKonto = objekte.getBankKonto();

      double betrag = objekte.getMietPreis() - (objekte.getMietPreis() * 2);

      betrag = (betrag * ((100 + aufschlag) / 100));

      String VerwendungsZweck = "** Airport: " + objekte.getIcao() + " **  Mietzahlung für Objekt : " + objekte.getName() + " Mietaufschlag: " + Math.floor(aufschlag) + " %";

      SaveBankbuchung(UserKonto, UserName, UserKonto, UserName, new Date(), betrag, "500-1000005", "**** FTW Immobilien *****",
              new Date(), VerwendungsZweck, objekte.getIdUser(), -1, -1, -1, -1, -1, -1, "", objekte.getIduserfbo(), -1, objekte.getKostenstelle(), -1);

      FboUserObjekte userObjekt = getUserObjekt(objekte.getIduserfbo());

      userObjekt.setLetzteMietzahlung(new Date());
      userObjekt.setFaelligkeitNaechsteMiete(naechsteMiete.getTime());
      userObjekt.setMahnStufe(mahnStufe);

      onMietZahlungBuchen(userObjekt);

      String Betreff = "** Airport: " + objekte.getIcao() + " ** Mietzahlung für Objekt : " + objekte.getName() + " Betrag : " + df.format(betrag) + " €" + " Mietaufschlag: " + Math.floor(aufschlag) + " %";
      String Nachricht = Betreff;
      String AnUser = objekte.getUserName();

      saveMail("**** FTW Financial Services ****", AnUser, Betreff, Nachricht);

    }

//    System.out.println("Mietverträge abgearbeitet für : " + c.getTime());
    em.close();
    emf.close();

  }

  private static void verbuchen(FboUserObjekte entity) {
    em.getTransaction().begin();
    em.merge(entity);
    em.getTransaction().commit();
  }

  private static void createBankbuchung(Bank entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
  }

  private static void onMietZahlungBuchen(FboUserObjekte entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
  }

  private static List<ViewFBOUserObjekte> getFaelligeMietvertraege(Date Datum) {
    return em.createQuery("SELECT m FROM ViewFBOUserObjekte m WHERE m.faelligkeitNaechsteMiete between '2017-01-01' and  :datum", ViewFBOUserObjekte.class).
            setParameter("datum", Datum)
            .getResultList();
  }

  private static Airport getAirportData(String icao) {
    return em.createQuery("SELECT a FROM Airport a where a.icao =:icao", Airport.class)
            .setParameter("icao", icao)
            .getSingleResult();
  }

  private static long wievieleFBOAmFlughafen(String icao) {
    return (long) em.createQuery("SELECT COUNT (f) from ViewFBOUserObjekte f WHERE f.icao = :icao AND f.fbo = TRUE")
            .setParameter("icao", icao)
            .getSingleResult();
  }

  private static double BankSaldoUeberBankKonto(String KontoNummer) {
    System.out.println("ftw.MietvertraegeAbarbeiten.BankSaldoUeberBankKonto() " + KontoNummer);

    String Abfrage = "SELECT SUM( betrag ) FROM bank AS bank WHERE bankKonto = '" + KontoNummer + "'";
    return (double) em.createNativeQuery(Abfrage).getSingleResult();
  }

  private static FboUserObjekte getUserObjekt(int objektid) {
    return em.createQuery("SELECT o FROM FboUserObjekte o WHERE o.iduserfbo = :objektid", FboUserObjekte.class)
            .setParameter("objektid", objektid)
            .getSingleResult();
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

  private static void saveMail(String Von, String An, String Betreff, String Nachricht) {

    //Absendermail speichern
    User absender = getUserByName(Von);
    User empfaenger = getUserByName(An);

    if (empfaenger != null && absender != null) {
      Mail nm = new Mail();

      nm.setUserID(absender.getIdUser());
      nm.setAnID(empfaenger.getIdUser());
      nm.setAnUser(empfaenger.getName());
      nm.setBetreff(Betreff);
      nm.setDatumZeit(new Date());
      nm.setGelesen(false);
      nm.setKategorie("Posteingang");
      nm.setVonID(absender.getIdUser());
      nm.setVonUser(absender.getName());
      nm.setNachrichtText(Nachricht);
      saveUserMail(nm);

      //Empfaengermail speichern
      if (!An.equals(Von)) {
        nm = new Mail();
        nm.setUserID(empfaenger.getIdUser());
        nm.setAnID(empfaenger.getIdUser());
        nm.setAnUser(empfaenger.getName());
        nm.setBetreff(Betreff);
        nm.setDatumZeit(new Date());
        nm.setGelesen(false);
        nm.setKategorie("Posteingang");
        nm.setVonID(absender.getIdUser());
        nm.setVonUser(absender.getName());
        nm.setNachrichtText(Nachricht);
        saveUserMail(nm);
      }

    }

  }

  private static void saveUserMail(Mail entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
  }

  private static User getUserByName(String username) {
    try {
      return em.createQuery("SELECT u from User u WHERE u.name = :username", User.class)
              .setParameter("username", username)
              .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}
