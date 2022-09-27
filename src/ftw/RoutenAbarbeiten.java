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
import ftw.data.Assignement;
import ftw.data.FboUserObjekte;
import ftw.data.Feinabstimmung;
import ftw.data.Fluggesellschaft;
import ftw.data.Flugrouten;
import ftw.data.Flugzeuge;
import ftw.data.ViewAirportTransfers;
import ftw.data.ViewFlugzeugeMietKauf;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Stefan Klees
 */
public class RoutenAbarbeiten implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
  private static final EntityManager em = emf.createEntityManager();

  private static final Calendar c = Calendar.getInstance();

  private static final NumberFormat numberformat = NumberFormat.getCurrencyInstance();
  private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
  private static final SimpleDateFormat sqldf = new SimpleDateFormat("yyyy-MM-dd");

  /*
  ****************** Auftragsvariablen ***********************************
   */
  private static String PassengerTitle;
  private static String[] PassengerNames;

  private static int GesamtGepaeck;
  private static int GewichtPax;
  private static int MengeEcoPax;
  private static int MengeBcPax;
  private static int DurchschnittsGewichtPax;
  private static int DurchschnittsGewichtGepaeck;
  private static int RestLadeKapazitaet;

  private static long Verfallszeit;

  /*
  ****************** Auftragsvariablen ENDE ***************************
   */
  public RoutenAbarbeiten() {
    Verfallszeit = 24 * 60 * 60 * 1000;
  }

  public static void TerminRoutenVerarbeiten() {

    /* 
    ************ Terminierte Routen abarbeiten
     */
    System.out.println("*** Terminierte Routen abarbeiten ***");

    Verfallszeit = 24 * 60 * 60 * 1000;

    for (Flugrouten fr : listAlleTerminiertenRouten()) {

      if (fr.getRoutenArt() == 1 || fr.getRoutenArt() == 3) {

        System.out.println(fr.getName() + "  Termin: " + df.format(fr.getAusfuehrungAm()));

        // Business-Class Sitze wieder mit zu den ECO addieren, wenn keine Business-Lounge genutzt wird.
        if (fr.getEcoAktiv() && (fr.getRoutenArt().equals(1) || fr.getRoutenArt().equals(3))  ) {
          MengenVerarbeitenECO(fr);
        }

        if (fr.getUseBusinessLounge() && (fr.getRoutenArt().equals(1) || fr.getRoutenArt().equals(3))) {
          if (fr.getMaxBusiness() > 0) {
            Fluggesellschaft fg = readFluggesellschaft(fr.getIdFluggesellschaft());
            if (fg != null) {
              if (existBusinessLounge(fr.getVonIcao(), fg.getUserid())) {
                MengenVerarbeitenBC(fr);
              }
            }
          }
        }

      }

      if (fr.getRoutenArt() == 2) {
        MengenVerarbeitenCargo(fr);
      }
      fr.setAktiv(Boolean.FALSE);
      speichereRoutenDurchlauf(fr);

    }//Ende For Schleife

    System.out.println("*** Terminierte Routen erstellt ***");
    em.close();
    emf.close();

  }

  public static void TagesRoutenVerarbeiten() {

    /* 
    ************ Tagesrouten abarbeiten
     */
    System.out.println("*** Tagesrouten abarbeiten ***");

    Verfallszeit = 96 * 60 * 60 * 1000;

    for (Flugrouten fr : listAlleTagesRouten()) {

      System.out.println(fr.getName() + " Letzte Ausführung: " + df.format(fr.getLetzteAusfuehrungAm()));

      if (fr.getEcoAktiv() && (fr.getRoutenArt().equals(1) || fr.getRoutenArt().equals(3))) {
        MengenVerarbeitenECO(fr);
      }

      if (fr.getUseBusinessLounge() && (fr.getRoutenArt().equals(1) || fr.getRoutenArt().equals(3))) {
        if (fr.getMaxBusiness() > 0) {
          Fluggesellschaft fg = readFluggesellschaft(fr.getIdFluggesellschaft());
          if (fg != null) {
            if (existBusinessLounge(fr.getVonIcao(), fg.getUserid())) {
              MengenVerarbeitenBC(fr);
            }
          }
        }
      }

      if (fr.getRoutenArt() == 2) {
        MengenVerarbeitenCargo(fr);
      }

      speichereRoutenDurchlauf(fr);
    }//Ende For Schleife

    System.out.println("*** Tagesrouten erstellt ***");
    em.close();
    emf.close();

  }

  private static void speichereRoutenDurchlauf(Flugrouten route) {

    int Zaehler = route.getAusfuehrungsZaehler() + 1;
    route.setAusfuehrungsZaehler(Zaehler);

    em.getTransaction().begin();
    em.persist(route);
    em.getTransaction().commit();

  }

  private static void speichereRoutenDaten(Flugrouten route, int Menge) {

//    route.setErzeugteMenge(Menge);
//
//    em.getTransaction().begin();
//    em.persist(route);
//    em.getTransaction().commit();
  }

  private static void MengenVerarbeitenECO(Flugrouten route) {

    double PaxPreis = PreisProPax(route);

    int Menge = 0;
    int Rest = MengeEcoPax;

    // ECO verarbeiten
    if (MengeEcoPax > 0) {

      do {
        if (Rest / 4 > 0) {
          Menge = CONF.zufallszahl(Rest / 4, Rest);
        } else {
          Menge = CONF.zufallszahl(1, Rest);
        }

        if (Rest < 1) {
          Rest = 0;
          Menge = Menge + Rest;
        }

        baueAuftrag(route, false, Menge, PaxPreis * Menge * CONF.zufallszahlDouble(1.0, 1.10), DurchschnittsGewichtPax * Menge, DurchschnittsGewichtGepaeck * Menge, false);

        Rest = Rest - Menge;

      } while (Rest > 0);

      if (RestLadeKapazitaet > 0 && route.getRoutenArt() == 3) {

        splitteRestCargo(route, RestLadeKapazitaet);

        //baueAuftragRestCargo(route, RestLadeKapazitaet);
      }

      //System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeitenECO() Rest Ladekapazität " + RestLadeKapazitaet);
    }

  }

  private static void MengenVerarbeitenBC(Flugrouten route) {

    double PaxPreis = PreisProPax(route);
    int Menge = 0;
    int Rest = MengeBcPax;

    System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeiten() beginn " + Rest);

    // BC verarbeiten
    if (MengeBcPax > 0) {

      do {
        Menge = CONF.zufallszahl(1, Rest);

        if (Rest < 1) {
          Rest = 0;
          Menge = Menge + Rest;
          System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeiten() if " + Rest);
        }

        System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeiten() " + Menge);
        baueAuftrag(route, true, Menge, PaxPreis * Menge * CONF.zufallszahlDouble(1.8, 2.8), DurchschnittsGewichtPax * Menge, DurchschnittsGewichtGepaeck * Menge, false);

        Rest = Rest - Menge;

      } while (Rest > 0);

      if (!route.getEcoAktiv()) {
        if (RestLadeKapazitaet > 0 && route.getRoutenArt() == 3) {
          baueAuftragRestCargo(route, RestLadeKapazitaet);
        }
      }

    }

  }

  private static void MengenVerarbeitenCargo(Flugrouten route) {

    double kosten = FixKosten(route, route.getMaxCargo(), 0);
    
    double Wert = 0.0;
    int minCargo = 0;
    int maxCargo = 0;

    if (kosten > 0) {
      double KostenProKiloFix = kosten / route.getMaxCargo();

      minCargo = (int) (route.getMaxCargo() * 0.90);
      maxCargo = CONF.zufallszahl(minCargo, route.getMaxCargo());

      Wert = (maxCargo * KostenProKiloFix) * CONF.zufallszahlDouble(1.1, 1.15);

    } else {
      Wert = 0;
    }

    baueAuftragCargo(route, maxCargo, Wert);

//
//    System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeitenCargo() Fixkosten " + kosten);
//    System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeitenCargo() Fixkosten / Kilo " + KostenProKiloFix);
//    System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeitenCargo() Min Menge " + minCargo);
//    System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeitenCargo() Max Menge " + route.getMaxCargo());
//    System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeitenCargo() gewählte Menge " + maxCargo);
    System.out.println("ftw.RoutenAbarbeiten.MengenVerarbeitenCargo() Transportwert " + Wert);
  }

  private static double PreisProPax(Flugrouten route) {

    GesamtGepaeck = 0;
    GewichtPax = 0;

    Feinabstimmung config = readConfig();

    // ********** Passagiere berechnen
    int MinPaxe = (int) (route.getMaxPax() * 0.8);

    if (!route.getUseBusinessLounge()) {
      MinPaxe = MinPaxe + route.getMaxBusiness();
    }

    if (MinPaxe <= 0) {
      MinPaxe = 1;
    }

//    System.out.println("ftw.RoutenAbarbeiten.PreisProPax() MaxPax = " + route.getMaxPax());
//    System.out.println("ftw.RoutenAbarbeiten.PreisProPax() MinPax = " + MinPaxe);
    if (MinPaxe >= route.getMaxPax()) {
      MengeEcoPax = CONF.zufallszahl(MinPaxe, MinPaxe);
    } else {
      MengeEcoPax = CONF.zufallszahl(MinPaxe, route.getMaxPax());
    }

    int Menge = MengeEcoPax;

    //Businesspassagiere holen 
    if (route.getUseBusinessLounge() && route.getMaxBusiness() > 0) {
      Fluggesellschaft fg = readFluggesellschaft(route.getIdFluggesellschaft());
      if (fg != null) {
        if (existBusinessLounge(route.getVonIcao(), fg.getUserid())) {
          MinPaxe = route.getMaxBusiness() / 2;
          if (MinPaxe <= 0) {
            MinPaxe = 1;
          }
          MengeBcPax = CONF.zufallszahl(MinPaxe, route.getMaxBusiness());
          //System.out.println("ftw.RoutenAbarbeiten.PreisProPax() Menge BC " + MengeBcPax);
          Menge = Menge + MengeBcPax;
        }
      }
    }

    for (int i = 0; i < Menge; i++) {
      GesamtGepaeck = GesamtGepaeck + CONF.zufallszahl(10, config.getBasisGewichtGepaeck().intValue());
      GewichtPax = GewichtPax + CONF.zufallszahl(75, config.getBasisGewichtPassagier().intValue());
    }

    DurchschnittsGewichtPax = GewichtPax / Menge;
    DurchschnittsGewichtGepaeck = GesamtGepaeck / Menge;

    double Auftragswert = FixKosten(route, GewichtPax, GesamtGepaeck);

//    System.out.println("ftw.RoutenAbarbeiten.PreisProPax() Menge:  " + Menge);
//    System.out.println("ftw.RoutenAbarbeiten.PreisProPax() Wert:  " + Auftragswert);
    return Auftragswert / Menge;
  }

  private static double PreisFuerRestCargo(Flugrouten route, int Menge, Feinabstimmung config) {

    double Faktor = 0.0;
    double Wert = 0.0;;

    switch (route.getFlugzeugLizenz()) {
      case "PPL-A":
        Faktor = 100;
        break;
      case "CPL":
        Faktor = 150;
        break;
      case "MPL":
        Faktor = 400;
        break;
      case "ATPL":
        Faktor = 600;
        break;
      default:
        break;
    }

    if (route.getDistance() > 1000 && route.getDistance() <= 2000) {
      Faktor = 700;
    } else if (route.getDistance() > 2000 && route.getDistance() <= 3000) {
      Faktor = 750;
    } else if (route.getDistance() > 3000 && route.getDistance() <= 4000) {
      Faktor = 800;
    } else if (route.getDistance() > 4000) {
      Faktor = 900;
    }

    Wert = (config.getPreisFuerCargokg() * Menge) + (config.getPreisFuerCargokg() * Menge) * (route.getDistance() / Faktor);

    return Wert;

  }

  private static void baueAuftrag(Flugrouten route, boolean BC, int Menge, double Wert, int GewichtPaxe, int GepaeckGewicht, boolean Cargo) {

    Date jetzt = new Date();
    Date jobTime;
    long neueZeit;
    Feinabstimmung config = readConfig();
    Airport vonAirport = readAirport(route.getVonIcao());
    Airport nachAirport = readAirport(route.getNachicao());
    Fluggesellschaft fg = readFluggesellschaft(route.getIdFluggesellschaft());

    Assignement neuerAuftrag = new Assignement();

    neuerAuftrag.setAirlineLogo(fg.getLogoURL());
    neuerAuftrag.setCeoAirline(fg.getBesitzerName());

    String[] Bezeichnungen = route.getPassengersTitle().split(",");
    String Bezeichnung = Bezeichnungen[CONF.zufallszahl(0, Bezeichnungen.length - 1)];

    neuerAuftrag.setComment(Bezeichnung);
    neuerAuftrag.setCommodity(route.getPassengersTitle());
    neuerAuftrag.setCreatedbyuser(fg.getName());
    neuerAuftrag.setCreation(jetzt);
    neuerAuftrag.setDaysclaimedactive(0);
    neuerAuftrag.setDirect(route.getDirect());
    neuerAuftrag.setDistance(route.getDistance());

    neueZeit = jetzt.getTime() + Verfallszeit;
    jobTime = new Date(neueZeit);
    neuerAuftrag.setExpires(jobTime);

    neuerAuftrag.setFlugrouteName(route.getName());
    neuerAuftrag.setFromAirportLandCity(vonAirport.getStadt() + " " + vonAirport.getLand());
    neuerAuftrag.setFromIcao(vonAirport.getIcao());
    neuerAuftrag.setLocationIcao(vonAirport.getIcao());
    neuerAuftrag.setFromName(vonAirport.getName());

    neuerAuftrag.setBonusclosed(route.getBonusFuerAirlinePiloten());
    neuerAuftrag.setBonusoeffentlich(route.getBonusFuerPiloten());
    neuerAuftrag.setProvision(route.getProvision());

    neuerAuftrag.setGruppe("");
    neuerAuftrag.setIdAirline(route.getIdFluggesellschaft());
    neuerAuftrag.setIdRoute(route.getIdFlugrouten());
    neuerAuftrag.setIdaircraft(route.getIdFlugzeugMietKauf());
    neuerAuftrag.setIdcommodity(-1);
    neuerAuftrag.setIdgroup(-1);

    neuerAuftrag.setIdowner(-1);
    neuerAuftrag.setActive(0);

    /*
      Ist die Route für einen Piloten reserviert dann Alles in den Warteraum schieben
     */
    if (route.getIdPilot() > 0) {
      neuerAuftrag.setIdowner(route.getIdPilot());
      neuerAuftrag.setActive(1);
    }

    neuerAuftrag.setIsBusinessClass(0);
    neuerAuftrag.setLizenz(route.getFlugzeugLizenz());
    neuerAuftrag.setMpttax(0);
    neuerAuftrag.setNameairline(fg.getName());
    neuerAuftrag.setNoext(0);

    if (route.getOeffentlich()) {
      neuerAuftrag.setOeffentlich(1);
    } else {
      neuerAuftrag.setOeffentlich(0);
    }

    neuerAuftrag.setAmmount(Menge);
    neuerAuftrag.setPay(Wert);
    neuerAuftrag.setGepaeck(GepaeckGewicht);
    neuerAuftrag.setGewichtPax(GewichtPaxe);

    // ************************************************** Passagiere 
    neuerAuftrag.setPilotfee(0);
    neuerAuftrag.setPtassigment("");

    // Type 1=PAX, 2=CARGO, 3=TRF, 4=Business-PAX, 5 Spritfaesser
    neuerAuftrag.setRoutenArt(1);

    if (BC) {
      System.out.println("ftw.RoutenAbarbeiten.baueAuftrag() BC" + BC);
      neuerAuftrag.setRoutenArt(4);
    } else if (Cargo) {
      neuerAuftrag.setRoutenArt(2);
      System.out.println("ftw.RoutenAbarbeiten.baueAuftrag() Cargo" + Cargo);
    }

    neuerAuftrag.setToAirportLandCity(nachAirport.getStadt() + " " + nachAirport.getLand());
    neuerAuftrag.setToIcao(nachAirport.getIcao());
    neuerAuftrag.setToName(nachAirport.getName());
    // Type 1=Routen Job, 2=Standard-Job, 3=Random-Job, 4=Linien-Job, 5=Airport Agent
    neuerAuftrag.setType(1);
    neuerAuftrag.setUnits("");
    neuerAuftrag.setUserlock(0);
    neuerAuftrag.setIdFBO(route.getIdUserFBO());
    neuerAuftrag.setIdTerminal(route.getIdTerminalDep());
    neuerAuftrag.setIcaoCodeFluggesellschaft(fg.getIcaoCode());
    neuerAuftrag.setVerlaengert(false);
    neuerAuftrag.setGesplittet(false);
    neuerAuftrag.setIdjob(-1);
    neuerAuftrag.setLangstrecke(route.getLangstrecke());

    speichereAuftrag(neuerAuftrag);
    speichereRoutenDaten(route, Menge);

  }

  private static void baueAuftragCargo(Flugrouten route, int Menge, double Wert) {

    Date jetzt = new Date();
    Date jobTime;
    long neueZeit;
    Feinabstimmung config = readConfig();
    Airport vonAirport = readAirport(route.getVonIcao());
    Airport nachAirport = readAirport(route.getNachicao());
    Fluggesellschaft fg = readFluggesellschaft(route.getIdFluggesellschaft());

    Assignement neuerAuftrag = new Assignement();

    neuerAuftrag.setActive(0);
    neuerAuftrag.setAirlineLogo(fg.getLogoURL());
    neuerAuftrag.setCeoAirline(fg.getBesitzerName());

    String[] Bezeichnungen = route.getPassengersTitle().split(",");
    String Bezeichnung = Bezeichnungen[CONF.zufallszahl(0, Bezeichnungen.length - 1)];

    neuerAuftrag.setComment(Bezeichnung);
    neuerAuftrag.setCommodity(route.getPassengersTitle());
    neuerAuftrag.setCreatedbyuser(fg.getName());
    neuerAuftrag.setCreation(jetzt);
    neuerAuftrag.setDaysclaimedactive(0);
    neuerAuftrag.setDirect(route.getDirect());
    neuerAuftrag.setDistance(route.getDistance());

    neueZeit = jetzt.getTime() + Verfallszeit;
    jobTime = new Date(neueZeit);
    neuerAuftrag.setExpires(jobTime);

    neuerAuftrag.setExpires(jobTime);
    neuerAuftrag.setFlugrouteName(route.getName());
    neuerAuftrag.setFromAirportLandCity(vonAirport.getStadt() + " " + vonAirport.getLand());
    neuerAuftrag.setFromIcao(vonAirport.getIcao());
    neuerAuftrag.setLocationIcao(vonAirport.getIcao());
    neuerAuftrag.setFromName(vonAirport.getName());

    neuerAuftrag.setBonusclosed(route.getBonusFuerAirlinePiloten());
    neuerAuftrag.setBonusoeffentlich(route.getBonusFuerPiloten());
    neuerAuftrag.setProvision(route.getProvision());

    neuerAuftrag.setGruppe("");
    neuerAuftrag.setIdAirline(route.getIdFluggesellschaft());
    neuerAuftrag.setIdRoute(route.getIdFlugrouten());
    neuerAuftrag.setIdaircraft(route.getIdFlugzeugMietKauf());
    neuerAuftrag.setIdcommodity(-1);
    neuerAuftrag.setIdgroup(-1);
    neuerAuftrag.setIdowner(-1);

    /*
      Ist die Route für einen Piloten reserviert dann Alles in den Warteraum schieben
     */
    if (route.getIdPilot() > 0) {
      neuerAuftrag.setIdowner(route.getIdPilot());
      neuerAuftrag.setActive(1);
    }

    neuerAuftrag.setIsBusinessClass(0);
    neuerAuftrag.setLizenz(route.getFlugzeugLizenz());
    neuerAuftrag.setMpttax(0);
    neuerAuftrag.setNameairline(fg.getName());
    neuerAuftrag.setNoext(0);

    if (route.getOeffentlich()) {
      neuerAuftrag.setOeffentlich(1);
    } else {
      neuerAuftrag.setOeffentlich(0);
    }

    neuerAuftrag.setAmmount(Menge);
    neuerAuftrag.setPay(Wert);
    neuerAuftrag.setGepaeck(0);
    neuerAuftrag.setGewichtPax(0);

    // ************************************************** Passagiere 
    neuerAuftrag.setPilotfee(0);
    neuerAuftrag.setPtassigment("");

    // Type 1=PAX, 2=CARGO, 3=TRF, 4=Business-PAX, 5 Spritfaesser
    neuerAuftrag.setRoutenArt(2);

    neuerAuftrag.setToAirportLandCity(nachAirport.getStadt() + " " + nachAirport.getLand());
    neuerAuftrag.setToIcao(nachAirport.getIcao());
    neuerAuftrag.setToName(nachAirport.getName());
    // Type 1=Routen Job, 2=Standard-Job, 3=Random-Job, 4=Linien-Job, 5=Airport Agent
    neuerAuftrag.setType(1);
    neuerAuftrag.setUnits("");
    neuerAuftrag.setUserlock(0);
    neuerAuftrag.setIdFBO(route.getIdUserFBO());
    neuerAuftrag.setIdTerminal(route.getIdTerminalDep());
    neuerAuftrag.setIcaoCodeFluggesellschaft(fg.getIcaoCode());
    neuerAuftrag.setVerlaengert(false);
    neuerAuftrag.setGesplittet(false);
    neuerAuftrag.setIdjob(-1);
    neuerAuftrag.setLangstrecke(route.getLangstrecke());

    speichereAuftrag(neuerAuftrag);
    speichereRoutenDaten(route, Menge);
  }

  private static void splitteRestCargo(Flugrouten route, int Menge) {

    int Rest = Menge;
    Menge = 0;

    if (Rest > 0) {

      do {
        Menge = CONF.zufallszahl(1, Rest);

        if (Rest < 1) {
          Rest = 0;
          Menge = Menge + Rest;
        }

        baueAuftragRestCargo(route, Menge);

//        baueAuftrag(route, false, Menge, PaxPreis * Menge * CONF.zufallszahlDouble(1.0, 1.10), DurchschnittsGewichtPax * Menge, DurchschnittsGewichtGepaeck * Menge, false);
        Rest = Rest - Menge;

      } while (Rest > 0);

    }
  }

  private static void baueAuftragRestCargo(Flugrouten route, int Menge) {
    Date jetzt = new Date();
    Date jobTime;
    long neueZeit;
    Feinabstimmung config = readConfig();
    Airport vonAirport = readAirport(route.getVonIcao());
    Airport nachAirport = readAirport(route.getNachicao());
    Fluggesellschaft fg = readFluggesellschaft(route.getIdFluggesellschaft());

    Assignement neuerAuftrag = new Assignement();

    neuerAuftrag.setActive(0);
    neuerAuftrag.setAirlineLogo(fg.getLogoURL());
    neuerAuftrag.setCeoAirline(fg.getBesitzerName());

    String Bezeichnung = "Cargo";

    neuerAuftrag.setComment(Bezeichnung);
    neuerAuftrag.setCommodity("Cargo");
    neuerAuftrag.setCreatedbyuser(fg.getName());
    neuerAuftrag.setCreation(jetzt);
    neuerAuftrag.setDaysclaimedactive(0);
    neuerAuftrag.setDirect(route.getDirect());
    neuerAuftrag.setDistance(route.getDistance());

    neueZeit = jetzt.getTime() + Verfallszeit;
    jobTime = new Date(neueZeit);
    neuerAuftrag.setExpires(jobTime);

    neuerAuftrag.setFlugrouteName(route.getName());
    neuerAuftrag.setFromAirportLandCity(vonAirport.getStadt() + " " + vonAirport.getLand());
    neuerAuftrag.setFromIcao(vonAirport.getIcao());
    neuerAuftrag.setLocationIcao(vonAirport.getIcao());
    neuerAuftrag.setFromName(vonAirport.getName());

    neuerAuftrag.setBonusclosed(route.getBonusFuerAirlinePiloten());
    neuerAuftrag.setBonusoeffentlich(route.getBonusFuerPiloten());
    neuerAuftrag.setProvision(route.getProvision());

    neuerAuftrag.setGruppe("");
    neuerAuftrag.setIdAirline(route.getIdFluggesellschaft());
    neuerAuftrag.setIdRoute(route.getIdFlugrouten());
    neuerAuftrag.setIdaircraft(route.getIdFlugzeugMietKauf());
    neuerAuftrag.setIdcommodity(-1);
    neuerAuftrag.setIdgroup(-1);
    neuerAuftrag.setIdowner(-1);

    /*
      Ist die Route für einen Piloten reserviert dann Alles in den Warteraum schieben
     */
    if (route.getIdPilot() > 0) {
      neuerAuftrag.setIdowner(route.getIdPilot());
      neuerAuftrag.setActive(1);
    }

    neuerAuftrag.setIsBusinessClass(0);
    neuerAuftrag.setLizenz(route.getFlugzeugLizenz());
    neuerAuftrag.setMpttax(0);
    neuerAuftrag.setNameairline(fg.getName());
    neuerAuftrag.setNoext(0);

    if (route.getOeffentlich()) {
      neuerAuftrag.setOeffentlich(1);
    } else {
      neuerAuftrag.setOeffentlich(0);
    }

    int MinMenge = Menge / 2;

    if (MinMenge <= 0) {
      MinMenge = 1;
    }

    Menge = CONF.zufallszahl(MinMenge, Menge);

    neuerAuftrag.setAmmount(Menge);

    neuerAuftrag.setPay(PreisFuerRestCargo(route, Menge, config));
    neuerAuftrag.setGepaeck(0);
    neuerAuftrag.setGewichtPax(0);

    // ************************************************** Passagiere 
    neuerAuftrag.setPilotfee(0);
    neuerAuftrag.setPtassigment("");

    // Type 1=PAX, 2=CARGO, 3=TRF, 4=Business-PAX, 5 Spritfaesser
    neuerAuftrag.setRoutenArt(2);
    neuerAuftrag.setToAirportLandCity(nachAirport.getStadt() + " " + nachAirport.getLand());
    neuerAuftrag.setToIcao(nachAirport.getIcao());
    neuerAuftrag.setToName(nachAirport.getName());
    // Type 1=Routen Job, 2=Standard-Job, 3=Random-Job, 4=Linien-Job, 5=Airport Agent
    neuerAuftrag.setType(1);
    neuerAuftrag.setUnits("");
    neuerAuftrag.setUserlock(0);
    neuerAuftrag.setIdFBO(route.getIdUserFBO());
    neuerAuftrag.setIdTerminal(route.getIdTerminalDep());
    neuerAuftrag.setIcaoCodeFluggesellschaft(fg.getIcaoCode());
    neuerAuftrag.setVerlaengert(false);
    neuerAuftrag.setGesplittet(false);
    neuerAuftrag.setIdjob(-1);
    neuerAuftrag.setLangstrecke(route.getLangstrecke());

    speichereAuftrag(neuerAuftrag);

  }

  private static void speichereAuftrag(Assignement assignement) {
    em.getTransaction().begin();
    em.persist(assignement);
    em.getTransaction().commit();

  }

  private static int PilotBezahlung(Flugrouten route) {
    int Bezahlung = 0;
    if (route.getFlugzeugLizenz().equals("PPL-A")) {
      Bezahlung = 7000;
    } else if (route.getFlugzeugLizenz().equals("CPL")) {
      Bezahlung = 7000;
    } else if (route.getFlugzeugLizenz().equals("MPL")) {
      Bezahlung = 6000;
    } else if (route.getFlugzeugLizenz().equals("ATPL")) {
      Bezahlung = 5500;
    }
    return Bezahlung;
  }

  private static double TaxiStd(Flugrouten route) {
    double Zeit = 0.0;
    if (route.getFlugzeugLizenz().equals("PPL-A")) {
      Zeit = 20;
    } else if (route.getFlugzeugLizenz().equals("CPL")) {
      Zeit = 20;
    } else if (route.getFlugzeugLizenz().equals("MPL")) {
      Zeit = 30;
    } else if (route.getFlugzeugLizenz().equals("ATPL")) {
      Zeit = 30;
    }
    return Zeit / 60;
  }

  private static double FixKosten(Flugrouten route, int GewichtPax, int GewichtGepaeck) {
    ViewFlugzeugeMietKauf mietFgz = readFlugzeugMietKauf(route.getIdFlugzeugMietKauf());

    Flugzeuge Stammfgz = null;
    Feinabstimmung config = readConfig();

    double Summe = 0.0;
    boolean FlugzeugOK = false;

    if (mietFgz != null) {

      try {
        FlugzeugOK = mietFgz.getFluggesellschaftID().equals(route.getIdFluggesellschaft());
      } catch (Exception e) {
        FlugzeugOK = false;
      }

      if (FlugzeugOK) {

        Stammfgz = readFlugzeugStamm(mietFgz.getIdFlugzeug());

        int maxPaxECO = route.getMaxPax();
        int maxPaxBC = route.getMaxBusiness();
        int maxCargo = route.getMaxCargo();

        int maxSpeed = Stammfgz.getReisegeschwindigkeitTAS();
        int SpritverbrauchStd = Stammfgz.getVerbrauchStunde();
        int maxReichweite = Stammfgz.getMaxReichweite();
        int CrewAnzahl = Stammfgz.getBesatzung() + Stammfgz.getFlugBegleiter();
        int Leergewicht = Stammfgz.getLeergewicht();
        int MaxMTOW = Stammfgz.getHoechstAbfluggewicht();
        int PayLoad = Stammfgz.getPayload();
        int RestTankMenge = mietFgz.getAktuelleTankfuellung();
        int GewichtSprit = 0;
        int Entfernung = route.getDistance();

        //********** Kostenberechnung
        double FlugDauerNetto = (double) Entfernung / (double) maxSpeed;
        double FlugdauerStd = FlugDauerNetto + TaxiStd(route);
        double SpritverbrauchKG = FlugdauerStd * SpritverbrauchStd;
        double SpritKosten = 0.0;

        if (mietFgz.getTreibstoffArt() == 1) {
          SpritKosten = SpritverbrauchKG * config.getPreisAVGASkg();
        } else {
          SpritKosten = SpritverbrauchKG * config.getPreisJETAkg();
        }

        double PilotGehalt = FlugdauerStd * PilotBezahlung(route);
//        System.out.println("ftw.RoutenAbarbeiten.FixKosten() Flugdauer Netto.: " + FlugDauerNetto);
//        System.out.println("ftw.RoutenAbarbeiten.FixKosten() Flugdauer Std.: " + FlugdauerStd);
//        System.out.println("ftw.RoutenAbarbeiten.FixKosten() Pilot Gehalt: " + PilotGehalt);
        double CrewGehalt = (FlugdauerStd * config.getAbrCrewgebuehren()) * CrewAnzahl;
        double KalkulatorischerStundensatz = Stammfgz.getKalkulatorischerStundensatz() * FlugdauerStd;
        double Startgebuehr = (Leergewicht + GewichtPax + GewichtGepaeck + GewichtSprit + RestTankMenge) * config.getAbrLandegebuehr();
        double Landegebuehr = (Leergewicht + GewichtPax + GewichtGepaeck + GewichtSprit) * config.getAbrLandegebuehr();
        double Zwischensumme = SpritKosten + PilotGehalt + KalkulatorischerStundensatz + CrewGehalt + Startgebuehr + Landegebuehr;

        double TerminalProzentDep = 0.05;
        double TerminalProzentArr = 0.05;

//        try {
//          TerminalProzentDep = readTerminal(route.getIdTerminalDep()).getTerminalGebuehrInProzent();
//          TerminalProzentDep = (TerminalProzentDep / 100.0);
//
//          TerminalProzentArr = readTerminal(route.getIdTerminalArr()).getTerminalGebuehrInProzent();
//          TerminalProzentArr = (TerminalProzentArr / 100.0);
//
//        } catch (NullPointerException e) {
//          System.out.println("ftw.RoutenAbarbeiten.FixKosten() Terminalkosten fehlgeschlagen");
//        }
        double Terminal = Zwischensumme * (TerminalProzentDep + TerminalProzentArr);
        double Flughafen = Zwischensumme * 0.02;
        double BodenPersonal = Zwischensumme * 0.05;

        Summe = Zwischensumme + Terminal + Flughafen + BodenPersonal;

        //System.out.println("ftw.RoutenAbarbeiten.FixKosten() Summe = " + Summe);
        RestLadeKapazitaet = getRestLadekapazitaet(PayLoad, maxCargo, Leergewicht, GewichtPax, GewichtGepaeck, GewichtSprit);

      }

    }

    return Summe;
  }

  private static int getRestLadekapazitaet(int maxPayload, int maxCargo, int maxAbfluggewicht, int GewichtPax, int GewichtGepaeck, int GewichtSprit) {

    int Beladung = GewichtPax + GewichtGepaeck + GewichtSprit;
    int Reserve = (int) (maxCargo * 0.10);

    if (Beladung <= maxAbfluggewicht) {

      if (maxPayload == maxCargo) {
        int Rest = maxPayload - Beladung;
        if (Rest - Reserve > 0) {
          return Rest;
        } else {
          return 0;
        }
      } else {
        int Rest = maxCargo - (GewichtGepaeck + Reserve);
        int GesGewicht = Rest + GewichtPax + GewichtGepaeck;
        int Abzug = maxPayload - GesGewicht;

        if (GesGewicht > maxPayload) {
          Rest = Rest + Abzug;
        }

        if (Rest > 0) {
          return Rest;
        } else {
          return 0;
        }
      }
    }

    return 0;
  }

  public static void AusfuehrungsZaehlerZuruecksetzen() {

    em.getTransaction().begin();
    int Result = getEntityManager().createQuery("UPDATE Flugrouten r SET r.ausfuehrungsZaehler=0 WHERE r.ausfuehrungPerDatum = FALSE").executeUpdate();
    em.getTransaction().commit();
  }

  /*
  ************** Setter and Getter
   */
  // Data Operation
  private static EntityManager getEntityManager() {
    return em;
  }

  // **************************************** JPA Abfragen beginn ***************************************************************************
  private static Feinabstimmung readConfig() {
    return getEntityManager().createQuery("SELECT c from Feinabstimmung c", Feinabstimmung.class)
            .setMaxResults(1)
            .getSingleResult();
  }

  private static List<Fluggesellschaft> listAllAirlines() {
    return getEntityManager().createQuery("SELECT a from Fluggesellschaft a", Fluggesellschaft.class)
            .getResultList();

  }

  private static List<Flugrouten> listAlleRouten() {
    return getEntityManager().createQuery("SELECT r FROM Flugrouten r WHERE r.aktiv = true", Flugrouten.class)
            .getResultList();
  }

  private static List<Flugrouten> listAlleTerminiertenRouten() {
    Date jetzt = new Date();
    try {
      String Datum = df.format(jetzt);
      jetzt = df.parse(Datum);
    } catch (ParseException e) {
      System.out.println("ftw.RoutenAbarbeiten.listAlleTerminiertenRouten() Parse Exeption ungültiges Datum erhalten");
    }
    return getEntityManager().createQuery("SELECT r FROM Flugrouten r WHERE r.aktiv = TRUE "
            + "AND r.ausfuehrungPerDatum = TRUE AND r.ausfuehrungAm <= :datum AND r.ausfuehrungsZaehler < r.wiederholungen ", Flugrouten.class)
            .setParameter("datum", jetzt)
            .getResultList();
  }

  private static List<Flugrouten> listAlleTagesRouten() {
    int Tag = c.get(GregorianCalendar.DAY_OF_WEEK);
    Date datum = new Date();

    String Abfrage = "";

    if (Tag == GregorianCalendar.SUNDAY) {
      Abfrage = " AND r.so = TRUE ";
    }
    if (Tag == GregorianCalendar.MONDAY) {
      Abfrage = " AND r.mo = TRUE ";
    }
    if (Tag == GregorianCalendar.TUESDAY) {
      Abfrage = " AND r.di = TRUE ";
    }
    if (Tag == GregorianCalendar.WEDNESDAY) {
      Abfrage = " AND r.mi = TRUE ";
    }
    if (Tag == GregorianCalendar.THURSDAY) {
      Abfrage = " AND r.don = TRUE ";
    }
    if (Tag == GregorianCalendar.FRIDAY) {
      Abfrage = " AND r.fr = TRUE ";
    }
    if (Tag == GregorianCalendar.SATURDAY) {
      Abfrage = " AND r.sa = TRUE ";
    }

    System.out.println("ftw.RoutenAbarbeiten.listAlleTagesRouten() Abfrage:  " + Abfrage);

    return getEntityManager().createQuery("SELECT r FROM Flugrouten r WHERE r.aktiv = TRUE AND r.ausfuehrungPerDatum = FALSE AND r.letzteAusfuehrungAm >= :datum  AND r.ausfuehrungsZaehler < r.wiederholungen "
            + Abfrage, Flugrouten.class)
            .setParameter("datum", datum)
            .getResultList();
  }

  private static List<Flugrouten> getAirportsAusRouten() {
    return em.createQuery("SELECT a FROM Flugrouten a WHERE a.aktiv = TRUE GROUP BY a.vonIcao", Flugrouten.class)
            .getResultList();
  }

  private static Fluggesellschaft readFluggesellschaft(int id) {
    return getEntityManager().createQuery("SELECT f from Fluggesellschaft f WHERE f.idFluggesellschaft = :id", Fluggesellschaft.class)
            .setParameter("id", id)
            .getSingleResult();
  }

  private static FboUserObjekte readTerminal(int id) {
    try {
      return getEntityManager().createQuery("SELECT t from FboUserObjekte t WHERE t.iduserfbo = :id", FboUserObjekte.class)
              .setParameter("id", id)
              .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  private static List<Flugrouten> listFlugroutenFluggesellschaft(int idFluggesellschaft) {
    return getEntityManager().createQuery("SELECT f FROM Flugrouten f WHERE f.idFluggesellschaft = :fgid", Flugrouten.class)
            .setParameter("fgid", idFluggesellschaft)
            .getResultList();
  }

  private static List<Flugrouten> listAllFlugrouten(String icao) {
    return getEntityManager().createQuery("SELECT f FROM Flugrouten f WHERE f.aktiv = TRUE AND f.vonIcao = :icao", Flugrouten.class)
            .setParameter("icao", icao)
            .getResultList();
  }

  private static Airport readAirport(String icao) {
    try {
      return getEntityManager().createQuery("SELECT a FROM Airport a WHERE a.icao =:icao", Airport.class)
              .setParameter("icao", icao)
              .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  private static ViewFlugzeugeMietKauf readFlugzeugMietKauf(int MietKaufID) {
    try {
      return getEntityManager().createQuery("SELECT f FROM ViewFlugzeugeMietKauf f WHERE f.idMietKauf = :id", ViewFlugzeugeMietKauf.class)
              .setParameter("id", MietKaufID)
              .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  private static Flugzeuge readFlugzeugStamm(int FlugzeugID) {
    try {
      return getEntityManager().createQuery("SELECT f FROM Flugzeuge f WHERE f.idFlugzeug = :id", Flugzeuge.class)
              .setParameter("id", FlugzeugID)
              .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  private static List<ViewAirportTransfers> getTransfer(String icao, Date startDatum, Date endDatum) {
    return getEntityManager().createQuery("SELECT v from ViewAirportTransfers v where v.datum BETWEEN :startDatum and :endDatum and v.icao = :icao", ViewAirportTransfers.class)
            .setParameter("startDatum", startDatum)
            .setParameter("endDatum", endDatum)
            .setParameter("icao", icao)
            .getResultList();
  }

  private static int GetMengeAmFlughafen(String Abfrage) {
    try {
      BigDecimal bd = (BigDecimal) getEntityManager().createNativeQuery(Abfrage).getSingleResult();
      return bd.intValue();
    } catch (Exception e) {
      return 0;
    }
  }

  private static boolean existBusinessLounge(String icao, int userid) {
    String Abfrage = "SELECT businessLounge FROM fboObjekte AS fboObjekte, fboUserObjekte AS fboUserObjekte "
            + "WHERE fboObjekte.idObjekt = fboUserObjekte.idfboObjekt AND fboUserObjekte.icao = '" + icao + "' AND fboObjekte.businessLounge = TRUE and fboUserObjekte.idUser = " + userid;
    return getEntityManager().createNativeQuery(Abfrage).getResultList().size() > 0;
  }

// **************************************** JPA Abfragen ENDE ***************************************************************************  
}
