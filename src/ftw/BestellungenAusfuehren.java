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
import ftw.data.Banktransfer;
import ftw.data.Bestellungen;
import ftw.data.FboObjekte;
import ftw.data.FboUserObjekte;
import ftw.data.Lagerbestellungservicehangar;
import ftw.data.Lagerservicehangar;
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
public class BestellungenAusfuehren {

  private static EntityManagerFactory emf = null;
  private static EntityManager em = null;
  private static final Calendar c = Calendar.getInstance();

  public static void onReparaturFTWFlugzeugeAusfuehren() {

    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();
    String SqlAbfrage = "update Flugzeuge_miet_kauf set zustand = 100.0, letzterCheckMinuten = 0 where (idflugzeugBesitzer = -300 or idflugzeugBesitzer = -320 or idflugzeugBesitzer = -310 ) and zustand <= 96  and istInDerLuft = false";

    em.getTransaction().begin();
    System.out.println("Reparierte Flugzeuge : " + em.createNativeQuery(SqlAbfrage).executeUpdate());
    em.getTransaction().commit();

    // C-Check setzen
    em.getTransaction().begin();
    SqlAbfrage = "update Flugzeuge_miet_kauf set naechsterTerminCcheck = DATE_ADD(now(), INTERVAL 6 MONTH) where (idflugzeugBesitzer = -300 or idflugzeugBesitzer = -310 or idflugzeugBesitzer = -320) and naechsterTerminCcheck < now() and istInDerLuft = false";
    System.out.println("Flugzeuge C-Check durchgeführt : " + em.createNativeQuery(SqlAbfrage).executeUpdate());
    em.getTransaction().commit();

    em.close();
    emf.close();

    System.out.println("Reparaturen beendet");

  }

  public static void onBestellungenPruefen() {

    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    DecimalFormat df = new DecimalFormat("###,##0.00");

    String art = "";
    int MaxMenge = 0;
    int aktuelleMenge = 0;
    int UserID = 0;
    boolean bstAusfuehren = false;

    System.out.println("Bestellungen gestartet");

    List<Bestellungen> orders = getBestellungen(c.getTime());

    for (Bestellungen order : orders) {

      if (order.getTankstelle()) {
        FboUserObjekte objekt = getFboUserOjekt(order.getObjektID());
        ViewFBOUserObjekte viewobjekt = getViewFboUserOjekt(order.getObjektID());

        if (viewobjekt != null) {
          bstAusfuehren = true;
        } else {
          bstAusfuehren = false;
        }

        if (bstAusfuehren) {
          FboObjekte fbo = getFboOjekt(objekt.getIdfboObjekt());

          MaxMenge = fbo.getTankstelleMaxFuelKG();
          aktuelleMenge = objekt.getBestandJETAkg() + objekt.getBestandAVGASkg();

          UserID = objekt.getIdUser();

          double saldo = BankSaldoUeberBankKonto(order.getBankkonto());

          // Reicht der Banksaldo für die Bestellung zu bezahlen
          if (saldo >= order.getBestellsumme()) {
            bstAusfuehren = true;
          } else {
            bstAusfuehren = false;

            // Mail senden zu wenig Geld auf dem Bankkonto
            String Betreff = "Order Fuel " + viewobjekt.getName() + " " + viewobjekt.getIcao() + " - " + art + "  " + order.getMenge() + " (kg) " + "  " + df.format(order.getBestellsumme()) + " --  " + "Du hast nicht genug Geld um die Lieferung abzuschliessen";
            String Nachricht = "Order Fuel " + viewobjekt.getName() + " " + viewobjekt.getIcao() + " - " + art + "  " + order.getMenge() + " (kg) " + "  " + df.format(order.getBestellsumme()) + " --  " + "Du hast nicht genug Geld um die Lieferung abzuschliessen";
            saveMail("**** FTW OIL *****", order.getName(), Betreff, Nachricht);

            // Bestellposition löschen
            loescheBestellposition(order.getIdBestellungen());

          }

          //Reicht die Tankkapizität aus
          if (bstAusfuehren) {
            if ((aktuelleMenge + order.getMenge()) <= MaxMenge) {
              bstAusfuehren = true;
            } else {
              bstAusfuehren = false;

              // Pin senden zu wenig Kapazität
              String Betreff = "Order Fuel " + viewobjekt.getName() + " " + viewobjekt.getIcao() + " - " + art + "  " + order.getMenge() + " (kg) " + "  " + df.format(order.getBestellsumme()) + " --  " + " nicht genug Platz in den Tanks";
              String Nachricht = "Order Fuel " + viewobjekt.getName() + " " + viewobjekt.getIcao() + " - " + art + "  " + order.getMenge() + " (kg) " + "  " + df.format(order.getBestellsumme()) + " --  " + " nicht genug Platz in den Tanks";
              saveMail("**** FTW OIL *****", order.getName(), Betreff, Nachricht);

              // Bestellposition löschen
              loescheBestellposition(order.getIdBestellungen());

            }
          }

          if (bstAusfuehren) {

            System.out.println("Banksaldo :" + saldo);
            System.out.println("Bestell :" + order.getBestellsumme());

            if (order.getArt() == 1) {
              art = "AVGAS 100LL";
              objekt.setBestandAVGASkg(objekt.getBestandAVGASkg() + order.getMenge());
              objekt.setEinkaufsPreisAVGAS(order.getBestellsumme() / order.getMenge());
            } else if (order.getArt() == 2) {
              art = "JETA";
              objekt.setBestandJETAkg(objekt.getBestandJETAkg() + order.getMenge());
              objekt.setEinkaufsPreisJETA(order.getBestellsumme() / order.getMenge());
            }
            // Menge auf Objekt verbuchen
            verbuchen(objekt);

            //**** Bankbuchung 
            String UserName = order.getName();
            String UserKonto = order.getBankkonto();

            double betrag = order.getBestellsumme() - (order.getBestellsumme() * 2);

            String VerwendungsZweck = "Order Fuel " + viewobjekt.getName() + " " + viewobjekt.getIcao() + " - " + art + "  " + order.getMenge() + " (kg) " + "  " + df.format(order.getBestellsumme()) + "   wurde geliefert.";

            SaveBankbuchung(UserKonto, UserName, UserKonto, UserName, new Date(), betrag, "500-1000003", "**** FTW OIL *****",
                    new Date(), VerwendungsZweck, UserID, -1, -1, -1, -1, -1, -1, objekt.getIcao(), objekt.getIduserfbo(), -1, objekt.getKostenstelle(), -1);

            // Mail senden erfolgreich geliefert
            String Betreff = "Order Fuel " + viewobjekt.getName() + " " + viewobjekt.getIcao() + " - " + art + "  " + order.getMenge() + " (kg) " + "  " + df.format(order.getBestellsumme()) + "   wurde geliefert.";
            String Nachricht = "Order Fuel " + viewobjekt.getName() + " " + viewobjekt.getIcao() + " - " + art + "  " + order.getMenge() + " (kg) " + "  " + df.format(order.getBestellsumme()) + "   wurde geliefert.";
            saveMail("**** FTW OIL *****", order.getName(), Betreff, Nachricht);

            // Bestellposition löschen
            loescheBestellposition(order.getIdBestellungen());
          }// Bestellung ausführen True
        }// end If Objekt not null
      }// end if Tankstelle
    }// For Schleife

    em.close();
    emf.close();

    System.out.println("Bestellungen beendet");
  }

  /*
  **************** Terminueberweisungen BEGINN
   */
  public static void onTerminUeberweisungen() {
    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    DecimalFormat df = new DecimalFormat("###,##0.00");

    System.out.println("Terminueberweisungen gestartet");

    List<Banktransfer> orders = getTerminUeberweisungen(c.getTime());

    // Liquiditätsprüfung
    if (orders != null) {

      for (Banktransfer order : orders) {

        boolean SaldoCheck = true;
        double Saldo = 0.0;

        double Summe = order.getBetrag() - (order.getBetrag() * 2);

        try {
          Saldo = BankSaldoUeberBankKonto(order.getAbsenderKonto());
        } catch (NullPointerException e) {
          SaldoCheck = false;
        }

        if (SaldoCheck) {

          if (!(Summe > Saldo)) {

            Bank newBuchung = new Bank();

            newBuchung.setUserID(order.getUserID());
            newBuchung.setAbsenderKonto(order.getAbsenderKonto());
            newBuchung.setAbsenderName(order.getAbsenderName());
            newBuchung.setKontoName(order.getAbsenderName());
            newBuchung.setAusfuehrungsDatum(order.getAusfuehrungsDatum());
            newBuchung.setBankKonto(order.getAbsenderKonto());
            newBuchung.setBetrag(order.getBetrag());
            newBuchung.setEmpfaengerKonto(order.getEmpfaengerKonto());
            newBuchung.setEmpfaengerName(order.getEmpfaengerName());
            newBuchung.setUeberweisungsDatum(new Date());
            newBuchung.setVerwendungsZweck(order.getVerwendungsZweck());
            newBuchung.setTransportID(-1);
            newBuchung.setLeasinggesellschaftID(-1);
            newBuchung.setIndustrieID(-1);
            newBuchung.setFlugzeugBesitzerID(-1);
            newBuchung.setFluggesellschaftID(-1);
            newBuchung.setAirportID(-1);
            newBuchung.setIcao("");
            newBuchung.setObjektID(-1);
            newBuchung.setFlugzeugID(-1);
            newBuchung.setKostenstelle(0);
            newBuchung.setPilotID(-1);

            createBankbuchung(newBuchung);

            //************** Gegenbuchung
            newBuchung = new Bank();
            newBuchung.setUserID(order.getEmpfaengerUserID());
            newBuchung.setBankKonto(order.getEmpfaengerKonto());
            newBuchung.setKontoName(order.getEmpfaengerName());

            newBuchung.setAbsenderKonto(order.getAbsenderKonto());
            newBuchung.setAbsenderName(order.getAbsenderName());

            newBuchung.setAusfuehrungsDatum(order.getAusfuehrungsDatum());

            newBuchung.setBetrag(order.getBetrag() - (order.getBetrag() * 2));
            newBuchung.setEmpfaengerKonto(order.getEmpfaengerKonto());
            newBuchung.setEmpfaengerName(order.getEmpfaengerName());
            newBuchung.setUeberweisungsDatum(new Date());
            newBuchung.setVerwendungsZweck(order.getVerwendungsZweck());
            newBuchung.setTransportID(-1);
            newBuchung.setLeasinggesellschaftID(-1);
            newBuchung.setIndustrieID(-1);
            newBuchung.setFlugzeugBesitzerID(-1);
            newBuchung.setFluggesellschaftID(-1);
            newBuchung.setAirportID(-1);
            newBuchung.setIcao("");
            newBuchung.setObjektID(-1);
            newBuchung.setFlugzeugID(-1);
            newBuchung.setKostenstelle(0);
            newBuchung.setPilotID(-1);

            createBankbuchung(newBuchung);

            removeTerminUeberweisung(order);

          } else {
            order.setVerwendungsZweck("Keine Deckung auf dem Bankkonto vorhanden, Ueberweisung wurde abgelehnt. Terminueberweisung bitte loeschen und bei ausreichender Deckung neu erstellen!");
            editOrder(order);
          }
        } else {
          removeTerminUeberweisung(order);
        }
      }

    }
    System.out.println("Terminueberweisungen beendet");
  }

  private static List<Banktransfer> getTerminUeberweisungen(Date datum) {
    return em.createQuery("SELECT t FROM Banktransfer t WHERE t.ausfuehrungsDatum <= :datum", Banktransfer.class)
            .setParameter("datum", datum)
            .getResultList();
  }

  private static void removeTerminUeberweisung(Banktransfer entity) {
    em.getTransaction().begin();
    em.remove(em.merge(entity));
    em.getTransaction().commit();
  }

  
  
  
  private static void editOrder(Banktransfer entity) {
    em.getTransaction().begin();
    em.merge(entity);
    em.getTransaction().commit();
  }

  /*
  **************** Terminueberweisungen ENDE
   */
  public static void onServiceHangarBestellungen() {

    System.out.println("Service Hangar Bestellungen gestartet");

    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    List<Lagerbestellungservicehangar> bestellungen = getBestellungenHangar(c.getTime());

    for (Lagerbestellungservicehangar items : bestellungen) {

      Lagerservicehangar lager = getServiceHangagerLager(items.getIdfbouserobjekt());

      FboUserObjekte serviceHangar = getFboServiceHangar(items.getIdfbouserobjekt());

      System.out.println("ftw.BestellungenAusfuehren.onServiceHangarBestellungen() " + serviceHangar.getName());

      //Lagerbestand ergaenzen
      if (lager != null) {

        Lagerservicehangar artikel = LagerProdukt(items.getPaketname(), items.getPaketart(), items.getIdfbouserobjekt());

        if (artikel != null) {
          artikel.setEingelagertam(new Date());

          double menge = artikel.getMenge() + items.getMenge();

          artikel.setMenge(menge);
          artikel.setPaketekpreis(items.getPaketekpreis() + items.getLieferkosten());
          artikel.setZustellungam(new Date());
          HangarLieferungVerbuchen(artikel);

          String Betreff = "Delivery : " + items.getPaketname();
          String Nachricht = "Delivered : " + items.getPaketname() + " - " + serviceHangar.getName();
          String AnUser = getUser(items.getIduser()).getName();

          saveMail("**** FTW Aircraft Stock ****", AnUser, Betreff, Nachricht);

        } else {
          Lagerservicehangar sh = new Lagerservicehangar();
          sh.setBestellungoffen(false);
          sh.setEingelagertam(new Date());
          sh.setIdfbouserobjekt(items.getIdfbouserobjekt());
          sh.setIduser(items.getIduser());
          sh.setMenge(items.getMenge());
          sh.setPaketart(items.getPaketart());
          sh.setPaketekpreis((items.getPaketekpreis() + items.getLieferkosten()));

          double EinzelPreis = ((items.getPaketekpreis() + items.getLieferkosten()) * 1.3) / items.getMenge();
          sh.setPaketvkpreis(EinzelPreis);
          sh.setPaketname(items.getPaketname());
          sh.setZustellungam(new Date());

          HangarErstLieferungVerbuchen(sh);

          String Betreff = "New Stock Delivery : " + items.getPaketname();
          String Nachricht = "New Stock Delivery : " + items.getPaketname() + " - " + serviceHangar.getName();
          String AnUser = getUser(items.getIduser()).getName();

          saveMail("**** FTW Aircraft Stock ****", AnUser, Betreff, Nachricht);

        }

      } else {
        Lagerservicehangar sh = new Lagerservicehangar();
        sh.setBestellungoffen(false);
        sh.setEingelagertam(new Date());
        sh.setIdfbouserobjekt(items.getIdfbouserobjekt());
        sh.setIduser(items.getIduser());
        sh.setMenge(items.getMenge());
        sh.setPaketart(items.getPaketart());
        sh.setPaketekpreis((items.getPaketekpreis() + items.getLieferkosten()));

        double EinzelPreis = ((items.getPaketekpreis() + items.getLieferkosten()) * 1.3) / items.getMenge();
        sh.setPaketvkpreis(EinzelPreis);

        sh.setPaketname(items.getPaketname());
        sh.setZustellungam(new Date());

        HangarErstLieferungVerbuchen(sh);

        String Betreff = "New Stock Delivery : " + items.getPaketname();
        String Nachricht = "New Stock Delivery : " + items.getPaketname() + " - " + serviceHangar.getName();
        String AnUser = getUser(items.getIduser()).getName();

        saveMail("**** FTW Aircraft Stock ****", AnUser, Betreff, Nachricht);

      }

      if (serviceHangar != null) {
        if (!items.getDirektverkauf()) {
          //**** Bankbuchung 
          String UserName = serviceHangar.getKontoName();
          String UserKonto = serviceHangar.getBankkonto();

          double betrag = items.getPaketekpreis() + items.getLieferkosten();
          betrag = betrag - (betrag * 2);

          String VerwendungsZweck = "Delivery : " + items.getPaketname() + " - " + serviceHangar.getName();

          SaveBankbuchung(UserKonto, UserName, UserKonto, UserName, new Date(), betrag, "500-1000002", "**** FTW Aircraft Stock ****",
                  new Date(), VerwendungsZweck, items.getIduser(), -1, -1, -1, -1, -1, -1, serviceHangar.getIcao(), serviceHangar.getIduserfbo(), -1, serviceHangar.getKostenstelle(), -1);
        }

        onBestellungHangarLoeschen(items);

      }
    }

    em.close();
    emf.close();

    System.out.println("Service Hangar Bestellungen beendet");

  }

  private static EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  private static void verbuchen(FboUserObjekte entity) {
    em.getTransaction().begin();
    em.merge(entity);
    em.getTransaction().commit();
  }

  public static void createBankbuchung(Bank entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
  }

  private static List<ftw.data.Bestellungen> getBestellungen(Date datum) {
    return em.createQuery("SELECT b FROM Bestellungen b where b.ausfuehrungsdatum <= :datum", Bestellungen.class)
            .setParameter("datum", datum)
            .getResultList();
  }

  /*
  Service Hangar Bestellungen BEGINN
   */
  private static List<ftw.data.Lagerbestellungservicehangar> getBestellungenHangar(Date datum) {
    return em.createQuery("SELECT b FROM Lagerbestellungservicehangar b where b.zustellungam <= :datum", Lagerbestellungservicehangar.class)
            .setParameter("datum", datum)
            .getResultList();
  }

  private static Lagerservicehangar getServiceHangagerLager(int fboUserObjektID) {
    try {
      return em.createQuery("SELECT s FROM Lagerservicehangar s WHERE s.idfbouserobjekt = :fboid", Lagerservicehangar.class)
              .setParameter("fboid", fboUserObjektID)
              .setMaxResults(1)
              .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }

  }

  private static Lagerservicehangar LagerProdukt(String produkt, int art, int fboUserObjekt) {
    try {
      return em.createQuery("SELECT p FROM Lagerservicehangar p WHERE p.paketname = :produkt AND p.paketart = :art AND p.idfbouserobjekt = :fboUserObjekt", Lagerservicehangar.class)
              .setParameter("produkt", produkt)
              .setParameter("art", art)
              .setParameter("fboUserObjekt", fboUserObjekt)
              .getSingleResult();

    } catch (NoResultException e) {
      return null;
    }
  }

  private static FboUserObjekte getFboServiceHangar(int id) {
    try {
      return em.createQuery("SELECT h FROM FboUserObjekte h WHERE h.iduserfbo = :id", FboUserObjekte.class)
              .setParameter("id", id)
              .getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  private static void HangarLieferungVerbuchen(Lagerservicehangar entity) {
    em.getTransaction().begin();
    em.merge(entity);
    em.getTransaction().commit();

  }

  private static void HangarErstLieferungVerbuchen(Lagerservicehangar entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
  }

  private static void onBestellungHangarLoeschen(Lagerbestellungservicehangar entity) {
    em.getTransaction().begin();
    em.remove(em.merge(entity));
    em.getTransaction().commit();
  }

  /*
  Service Hangar Bestellungen ENDE
   */
  private static FboUserObjekte getFboUserOjekt(int objektID) {
    try {
      return em.createQuery("SELECT o from FboUserObjekte o WHERE o.iduserfbo = :objektID", FboUserObjekte.class)
              .setParameter("objektID", objektID)
              .setMaxResults(1)
              .getSingleResult();

    } catch (NoResultException e) {
      return null;
    }
  }

  private static FboObjekte getFboOjekt(int objektID) {
    return em.createQuery("SELECT o from FboObjekte o WHERE o.idObjekt = :objektID", FboObjekte.class)
            .setParameter("objektID", objektID)
            .getSingleResult();
  }

  private static FboUserObjekte getUserObjekt(int objektid) {
    return em.createQuery("SELECT o FROM FboUserObjekte o WHERE o.iduserfbo = :objektid", FboUserObjekte.class)
            .setParameter("objektid", objektid)
            .getSingleResult();
  }

  private static ViewFBOUserObjekte getViewFboUserOjekt(int objektID) {
    try {
      return em.createQuery("SELECT o from ViewFBOUserObjekte o WHERE o.iduserfbo = :objektID", ViewFBOUserObjekte.class)
              .setParameter("objektID", objektID)
              .setMaxResults(1)
              .getSingleResult();

    } catch (NoResultException e) {
      return null;
    }
  }

  private static User getUser(int id) {
    try {
      return em.createQuery("SELECT u from User u WHERE u.idUser = :id", User.class)
              .setParameter("id", id)
              .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  private static void loescheBestellposition(int bestellID) {
    em.getTransaction().begin();
    em.createNativeQuery("delete from Bestellungen where idBestellungen = " + bestellID).executeUpdate();
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
