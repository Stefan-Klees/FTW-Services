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

import ftw.data.Bank;
import ftw.data.Flugzeugemietkauf;
import ftw.data.Hangarbelegung;
import ftw.data.Pinnwand;
import ftw.data.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Stefan Klees
 */
public class FlugzeugMietZeiten {

  private static EntityManagerFactory emf = null;
  private static EntityManager em = null;

  private static final SimpleDateFormat dfsql = new SimpleDateFormat("yyyy-MM-dd hh:mm");

  public static void onFlugzeugeMietzeitenPruefen() {

    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    List<Flugzeugemietkauf> MietListe = getFlugzeugeMietZeitAblauf(new Date());

    for (Flugzeugemietkauf fz : MietListe) {

      if (!fz.getIstInDerLuft()) {
        fz.setIstGesperrt(false);
        fz.setIstGesperrtBis(null);
        fz.setIstGesperrtSeit(null);
        fz.setIdUserDerFlugzeugGesperrtHat(-1);

        onFlugzeugMietKaufSpeichern(fz);
      }
    }
    em.close();
    emf.close();

  }

  public static void onHangarRaeumen() {
    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();
    long datum = new Date().getTime();

    em.getTransaction().begin();
    
    em.createNativeQuery("delete from hangarbelegung where ablaufzeit <= '" + dfsql.format(new Date()) + "'").executeUpdate();

    em.getTransaction().commit();

    em.close();
    emf.close();
  }

  private static List<Flugzeugemietkauf> getFlugzeugeMietZeitAblauf(Date DatumZeit) {
    // f.istInDerLuft=false am 02.05.2017 hinzugefügt
    return em.createQuery("SELECT f FROM Flugzeugemietkauf f WHERE f.istGesperrt=TRUE and f.istInDerLuft=false and f.istGesperrtBis < :zeit", Flugzeugemietkauf.class)
            .setParameter("zeit", DatumZeit)
            .getResultList();
  }

  private static void onFlugzeugMietKaufSpeichern(Flugzeugemietkauf entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();

  }

  public static void createBankbuchung(Bank entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
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

  private static double BankSaldoUeberBankKonto(String KontoNummer) {
    String Abfrage = "SELECT SUM( betrag ) FROM bank AS bank WHERE bankKonto = '" + KontoNummer + "'";
    return (double) em.createNativeQuery(Abfrage).getSingleResult();
  }

  private static double BankSaldoUeberUserID(int UserID) {
    String Abfrage = "SELECT SUM( betrag ) FROM bank AS bank WHERE userID = " + String.valueOf(UserID);
    return (double) em.createNativeQuery(Abfrage).getSingleResult();
  }

  private static String getBankKontoName(String BankKonto) {
    return em.createQuery("SELECT b from Bank b where b.bankKonto = :bankKonto", Bank.class)
            .setParameter("bankKonto", BankKonto)
            .setMaxResults(1)
            .getSingleResult().getKontoName();
  }

  private static User getUserData(int userid) {
    return em.createQuery("SELECT u FROM User u WHERE u.idUser = :userid", User.class)
            .setParameter("userid", userid)
            .getSingleResult();
  }
}
