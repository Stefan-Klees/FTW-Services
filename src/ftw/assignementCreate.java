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
import ftw.data.Feinabstimmung;
import ftw.data.Fluggesellschaft;
import ftw.data.Flugrouten;
import ftw.data.ViewAirportTransfers;
import java.io.Serializable;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Stefan Klees
 */
public class assignementCreate implements Serializable {

  private static EntityManagerFactory emf = null;
  private static EntityManager em = null;

  private static final long serialVersionUID = 1L;

  private static final Calendar c = Calendar.getInstance();

  private static String[] ZielICAOS;

  private static final Random Zufallszahl = new Random();

  private static Airport Airport_FromICAO, Airport_ToICAO, Airport_LocalICAO;
  private static Fluggesellschaft aktFg;
  private static Assignement assi;
  private static Feinabstimmung config;
  private static Flugrouten aktRoute;

  private static String vonIcao;
  private static String nachIcao;

  private static int maxPassengers;
  private static int maxPassengerTitle;
  private static int maxFlugziele;
  private static int maxPaxGroesse;
  private static int paxGroesse;
  private static double Paying;
  private static String Comment;
  private static String Commodity;
  private static String AirlineName;
  private static int idAirline;
  private static int idRoute;
  private static int isRoutePublic;
  private static double bonusClosed;
  private static double bonusOeffentlich;
  private static int AirlineLicense;
  private static String fromName;
  private static String toName;
  private static String flugrouteName;
  private static String toAirportLandCity;
  private static String fromAirportLandCity;
  private static String AirlineLogo;
  private static String CeoAirline;
  private static int Gepaeck;
  private static final int idOwner = -1;
  private static final int aktive = 1;
  private static final int userLock = 0;
  private static double provision;
  private static String IcaoCode;

  private static int idTerminal;
  private static int idFBO;

  private static int routenArt;
  private static String PassengerTitle;
  private static String[] PassengerNames;

  private static final NumberFormat numberformat = NumberFormat.getCurrencyInstance();
  private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
  private static final SimpleDateFormat sqldf = new SimpleDateFormat("yyyy-MM-dd");

  private static int MaxDays;

  private static Assignement newAssignement;

  private static double vonLongitude, vonLatitude, nachLongitude, nachLatitude;

  //Variablen fuer Rueckflug
  private static double SourcevonLongitude, SourcevonLatitude, SourcenachLongitude, SourcenachLatitude;
  private static String SourcevonIcao, SourcenachIcao;

  private static int Distanz[];

  private static boolean RandomJob;
  private static long RandomJobTime;
  private static String Storytext;
  private static boolean AssignmentLaufStart;
  private static int minAblauf;

  public static void onCreateZiele() throws java.sql.SQLException {

    emf = javax.persistence.Persistence.createEntityManagerFactory("ftwPU");
    em = emf.createEntityManager();

    AssignmentLaufStart = true;

    System.out.println("Auf verwaiste Flugrouten wird geprueft");
//
    flugroutenPruefen();
//
//    System.out.println("Flugroutenpruefung beendet");
    // ************************************** Flughaefen aus den Routen extrahieren
    System.out.println("Flughaefen aus den Routen extrahieren");

    List<Flugrouten> airportsRouten = getAirportsAusRouten();

    int freq = 0;

    config = readConfig();

    //minAblauf = config.getRoutenJobsAblauf().doubleValue() / 2.3;
    // Mindestablaufzeit in Minuten
    minAblauf = 4 * 24 * 60;

    System.out.println("ftw.assignementCreate.onCreateZiele() MinAblauf : " + minAblauf);

    do {
      System.out.println("ftw.assignementCreate.onCreateZiele() Freq : " + freq);

      for (Flugrouten route : airportsRouten) {
        System.out.println("ftw.assignementCreate.onCreateZiele() Icao : " + route.getVonIcao());
        routenAbarbeiten(route.getVonIcao());
      }

      System.out.println("Assignments erstellt");

      freq = freq + 1;
    } while (freq < 1);

    // ************************************** Routen ermitteln und abarbeiten
    System.out.println("Assignments erstellt");
    em.close();
    emf.close();
    AssignmentLaufStart = false;

  }

  public static void routenAbarbeiten(String icao) {

    System.out.println("Assignments beginn");

    int MengeAmAirport = 0;

    boolean gezogen = false;

    MaxDays = (config.getRoutenJobsAblauf() / 60) / 24;

    Distanz = new int[2];

    int Durchlauf = 0;

    List<Flugrouten> AllRoutes = getAllFlugrouten(icao);

    do {
      Durchlauf = Durchlauf + 1;

      if (AllRoutes.size() > 0) {

        // ************** Route Ziehen
        //System.out.println("Allroutes Size : " + AllRoutes.size());
        int zz = CONF.zufallszahl(0, AllRoutes.size() - 1);

        aktRoute = AllRoutes.get(zz);

        // ************** Fluggesellscahft auslesen;
        aktFg = getFluggesellschaft(aktRoute.getIdFluggesellschaft());

        // ************** Airport auslesen;
        Airport aktAirport = getAirport(aktRoute.getVonIcao());

        Airport_ToICAO = getAirport(aktRoute.getNachicao());
        vonIcao = aktRoute.getVonIcao();
        //
        // Auslesen des Traffics am Flughafen für 21 Tage
        //
        int trafficTage = 21;
        int tmppaxe = 0;
        int tmpcargo = 0;

        long start = new Date().getTime() - (long) (trafficTage * 24 * 60 * 60 * 1000);

        List<ViewAirportTransfers> transfer = getTransfer(vonIcao, new Date(start), new Date());

        for (ViewAirportTransfers tran : transfer) {
          tmppaxe = tmppaxe + tran.getPassagiere().intValue();
          tmpcargo = tmpcargo + tran.getCargo().intValue();
        }

        //
        // Auslesen des Traffics am Flughafen ENDE
        //
        // Wieviel Passagiere warten schon am Flughafen auf den Abflug
        // RoutenArt 1=Passagiere, 2=Cargo, 3=Flugzeugtransfer, 4=Business-Class
        //**************** Passagiere behandeln
        if (aktRoute.getRoutenArt() == 1) {
          MengeAmAirport = GetMengeAmFlughafen("select sum(ammount) as Menge from assignement where routenArt=1 and NOT createdbyuser = 'FTW-System' and from_icao='" + vonIcao + "'");
          //
          // Hinzugefuegt am 30.04.2017        
          // es wurden bis dato Assignments ganz normal erstellt, jedoch ohne den zusätzlichen Traffic
          // maxPassengers wurde weiter unten zugewiesen
          //
          maxPassengers = aktAirport.getMaxpassagiereprotag();
          maxPassengers += tmppaxe / trafficTage;
        }
        //
        //**************** Cargo behandeln
        if (aktRoute.getRoutenArt() == 2) {
          MengeAmAirport = GetMengeAmFlughafen("select sum(ammount) as Menge from assignement where routenArt=2 and NOT createdbyuser = 'FTW-System' and from_icao='" + vonIcao + "'");
          maxPassengers = aktAirport.getMaxCargo();
          maxPassengers += tmpcargo / trafficTage;

        }

        //Testfluggesellschaft nicht behindern
        if (aktRoute.getIdFluggesellschaft() == 801) {
          MengeAmAirport = 0;
        }

//        if (MengeAmAirport >= maxPassengers) {
//          System.out.println("ftw.assignementCreate.routenAbarbeiten() MaxPassengers erreicht Anzahl: " + maxPassengers);
//          System.out.println("Flugroute wird nicht bedient : " + aktRoute.getName());
//        }
        if (MengeAmAirport < maxPassengers) {
          System.out.println("Flugroute wird bedient : " + aktRoute.getName());

          double tpiFaktorPax = (100.0 / aktFg.getErzeugteJobs()) * aktFg.getGeflogeneJobs();
          double tpiFaktorCargo = (100.0 / aktFg.getErzeugtesCargo()) * aktFg.getGeflogenesCargo();

          double tpiFaktorPaxRoute = 0.0;
          double tpiFaktorCargoRoute = 0.0;

          if (aktRoute.getRoutenArt() == 1) {
            tpiFaktorPaxRoute = (100.0 / aktRoute.getErzeugteMenge()) * aktRoute.getUmsatzmenge();
          } else {
            tpiFaktorCargoRoute = (100.0 / aktRoute.getErzeugteMenge()) * aktRoute.getUmsatzmenge();
          }

          if (tpiFaktorPaxRoute == 0.0) {
            tpiFaktorPaxRoute = 100.0;
          }

          if (tpiFaktorCargoRoute == 0.0) {
            tpiFaktorCargoRoute = 100;
          }

          if (aktFg.getErzeugteJobs() < 300) {
            tpiFaktorPax = 100.0;
          }
          if (aktFg.getErzeugtesCargo() < 2000) {
            tpiFaktorCargo = 100.0;
          }

          idAirline = aktFg.getIdFluggesellschaft();
          AirlineName = aktFg.getName();
          AirlineLicense = 10;
          AirlineLogo = aktFg.getLogoURL();
          CeoAirline = aktFg.getBesitzerName();
          IcaoCode = aktFg.getIcaoCode();

          vonIcao = aktRoute.getVonIcao();
          nachIcao = aktRoute.getNachicao();

          idRoute = aktRoute.getIdFlugrouten();
          bonusClosed = aktRoute.getBonusFuerAirlinePiloten();
          bonusOeffentlich = aktRoute.getBonusFuerPiloten();
          fromName = aktRoute.getFromName();
          toName = aktRoute.getToName();
          flugrouteName = aktRoute.getName();
          toAirportLandCity = aktRoute.getToAirportLandCity();
          fromAirportLandCity = aktRoute.getFromAirportLandCity();
          provision = aktRoute.getProvision();

          idFBO = aktRoute.getIdFbo();
          idTerminal = aktRoute.getIdTerminalDep();

          if (aktRoute.getOeffentlich()) {
            isRoutePublic = 1;
          } else {
            isRoutePublic = 0;
          }

          Airport_FromICAO = getAirport(vonIcao);
          Airport_ToICAO = getAirport(nachIcao);

          PassengerTitle = aktRoute.getPassengersTitle();
          PassengerNames = getPersonTitle(PassengerTitle);

          maxPassengerTitle = PassengerNames.length;

          Distanz[0] = aktRoute.getDistance();
          Distanz[1] = aktRoute.getDirect();

          routenArt = aktRoute.getRoutenArt();

          paxGroesse = 1;

          // Passagiere Min Paxgroesse auslesen
          if (routenArt == 1) {
            maxPaxGroesse = aktRoute.getMaxPaxgroesse();

            if (maxPaxGroesse < CONF.getMinPax(Airport_FromICAO.getKlasse())) {
              maxPaxGroesse = CONF.getMinPax(Airport_FromICAO.getKlasse());
            }

            if (maxPaxGroesse > CONF.getMaxPax(Airport_FromICAO.getKlasse())) {
              maxPaxGroesse = CONF.getMaxPax(Airport_FromICAO.getKlasse());
            }

            paxGroesse = CONF.zufallszahl(getMinPax(aktAirport.getKlasse()), maxPaxGroesse);

            //System.out.println("ftw.assignementCreate.routenAbarbeiten() Paxgroesse vor TPI : " + paxGroesse);
            // TPI Faktor anwenden 
            if (tpiFaktorPax <= 95.0) {
              paxGroesse = (int) (paxGroesse * tpiFaktorPax / 100.0);
              if (paxGroesse <= 0) {
                paxGroesse = 1;
              }
              //System.out.println("ftw.assignementCreate.routenAbarbeiten() Paxgroesse nach TPI : " + paxGroesse);
            }
          }

          if (routenArt == 2) {
            maxPaxGroesse = aktRoute.getMaxPaxgroesse();

            if (maxPaxGroesse < CONF.getMinCargo(Airport_FromICAO.getKlasse())) {
              maxPaxGroesse = CONF.getMinCargo(Airport_FromICAO.getKlasse());
            }

            if (maxPaxGroesse > CONF.getMaxCargo(Airport_FromICAO.getKlasse())) {
              maxPaxGroesse = CONF.getMaxCargo(Airport_FromICAO.getKlasse());
            }

            paxGroesse = CONF.zufallszahl(getMinCargo(aktAirport.getKlasse()), maxPaxGroesse);
            // TPI Faktor anwenden
            if (tpiFaktorCargo <= 95.0) {
              paxGroesse = (int) (paxGroesse * tpiFaktorCargo / 100.0);
              if (paxGroesse <= 0) {
                paxGroesse = 1;
              }
              //System.out.println("ftw.assignementCreate.routenAbarbeiten() Cargogroesse nach TPI : " + paxGroesse);
            }
          }

          if (routenArt == 1) {
            PreiskalkulationPassagiere();
          } else if (routenArt == 2) {
            PreiskalkulationCargo();
          }

          /*
           ***************************************************************************
           */
          if (Distanz[0] > 0) {

            boolean tpiOK = true;

            // **** Verschärfung TPI Pax
            if (tpiFaktorPax <= 50) {
              if (CONF.zufallszahl(1, 3) == 2) {
                paxGroesse = 1;
              } else {
                tpiOK = false;
              }
            }

            if (tpiOK) {
              if (aktRoute.getEcoAktiv()) {
                assi = creatAssignment(vonIcao, nachIcao, tpiFaktorPax);
                em.getTransaction().begin();
                em.persist(assi);
                em.getTransaction().commit();
              }
            }

            /*
                Buisness Class Passagiere
             */
            if (existBusinessLounge(vonIcao, aktFg.getUserid()) & tpiOK) {
              if (aktRoute.getUseBusinessLounge()) {
                if (routenArt == 1) {
                  int BP = CONF.zufallszahl(1, paxGroesse);
                  if (BP <= 0) {
                    BP = 1;
                  }
                  if (BP > 10) {
                    BP = 10;
                  }

                  Paying = (Paying / paxGroesse * zufallszahlDouble(1.3, 1.9)) * BP;
                  paxGroesse = BP;

                  // TPI Faktor anwenden 
                  //System.out.println("ftw.assignementCreate.routenAbarbeiten() Paxgroesse Business-Passagiere vor TPI : " + paxGroesse);
                  if (tpiFaktorPax <= 95.0) {
                    paxGroesse = (int) (paxGroesse * tpiFaktorPax / 100.0);
                    if (paxGroesse <= 0) {
                      paxGroesse = 1;
                    }
                    //System.out.println("ftw.assignementCreate.routenAbarbeiten() Paxgroesse Business-Passagiere nach TPI : " + paxGroesse);
                  }

                  // ******************* Bezeichnung der Business-Class Passagieren
                  Comment = "";
                  if (maxPassengerTitle > 1) {
                    Comment = PassengerNames[Zufallszahl.nextInt(maxPassengerTitle)];
                  } else {
                    Comment = PassengerNames[0];
                  }

                  if (Comment.equals("")) {
                    Comment = "John Doe";
                  }

                  Comment = Comment + " " + "BC";
                  // ******************* Bezeichnung der Business-Class Passagieren ENDE

                  routenArt = 4;

                  if (CONF.zufallszahl(1, 2) == 2) {

                    // **** Verschärfung TPI Pax, TPI Faktor wird mit Übergeben
                    assi = creatAssignment(vonIcao, nachIcao, tpiFaktorPax);

                    em.getTransaction().begin();
                    em.persist(assi);
                    em.getTransaction().commit();
                  }
                }
              }
            }
          }
        }// Aktuelle Passagiere < Maxpassagiere
      }// Allroutes.size > 0
    } while (Durchlauf < AllRoutes.size());

  }

  public static Assignement creatAssignment(String vonIcao, String nachIcao, double TpiPax) {

    if (Distanz[0] > 0) {

      newAssignement = new Assignement();
      newAssignement.setActive(aktive);

      newAssignement.setAmmount(paxGroesse);
      newAssignement.setBearing(0);

      switch (routenArt) {
        case 1:
          newAssignement.setCommodity("Pax");
          break;
        case 2:
          newAssignement.setCommodity("Cargo");
          break;
        case 3:
          newAssignement.setCommodity("Transfer");
          break;
        case 4:
          newAssignement.setCommodity("Business-Class");
          break;
        default:
          newAssignement.setCommodity("N/A");
          break;
      }

      newAssignement.setComment(Comment);
      newAssignement.setCreatedbyuser(AirlineName);
      newAssignement.setCreation(c.getTime());
      newAssignement.setDaysclaimedactive(0);
      newAssignement.setDirect(Distanz[1]);
      newAssignement.setDistance(Distanz[0]);

      Calendar Tomorrow = Calendar.getInstance();

      Date jetzt = new Date();
      Date jobTime;
      long neueZeit;

      int GesamtGepaeck = 0;
      int GewichtPax = 0;

      if (routenArt == 1 || routenArt == 4) {
        for (int i = 0; i < paxGroesse; i++) {
          GesamtGepaeck = GesamtGepaeck + CONF.zufallszahl(1, config.getBasisGewichtGepaeck().intValue());
          GewichtPax = GewichtPax + CONF.zufallszahl(65, config.getBasisGewichtPassagier().intValue());
        }
      }

      // Ablaufzeit der Jobs festlegen
      long milli = CONF.zufallszahl((int) minAblauf, config.getRoutenJobsAblauf());
      // JobAblauf ist in Millisekunden
      neueZeit = jetzt.getTime() + (milli * 60 * 1000);

      // **** TPI Verschärfung
      // Ablaufzeit 1 Tag
      if (TpiPax <= 50) {
        neueZeit = jetzt.getTime() + (long) (24 * 60 * 60 * 1000);
      }

      newAssignement.setExpires(new Date(neueZeit));

      newAssignement.setFromIcao(vonIcao);
      newAssignement.setGruppe("");
      newAssignement.setIdaircraft(-1);
      newAssignement.setUnits("");
      newAssignement.setIdaircraft(0);
      newAssignement.setIdowner(idOwner);
      newAssignement.setLocationIcao(vonIcao);
      newAssignement.setMpttax(0);
      newAssignement.setNoext(0);
      newAssignement.setPay(Paying * zufallszahlDouble(0.7, 1.3));
      //newAssignement.setPay(Paying * zufallszahlDouble(0.9, 1.8)); // nur bei Event.
      newAssignement.setPilotfee(0);
      newAssignement.setPtassigment("");
      newAssignement.setToIcao(nachIcao);
      newAssignement.setUserlock(userLock);

      // Type 1=Routen Job, 2=Standard-Job, 3=Random-Job, 4=Linien-Job
      newAssignement.setType(1);
      newAssignement.setIdAirline(idAirline);
      newAssignement.setIdRoute(idRoute);
      newAssignement.setOeffentlich(isRoutePublic);
      newAssignement.setBonusclosed(bonusClosed);
      newAssignement.setBonusoeffentlich(bonusOeffentlich);
      newAssignement.setNameairline(AirlineName);
      newAssignement.setLizenz("");
      newAssignement.setToName(toName);
      newAssignement.setFromName(fromName);
      newAssignement.setFlugrouteName(flugrouteName);
      newAssignement.setToAirportLandCity(toAirportLandCity);
      newAssignement.setFromAirportLandCity(fromAirportLandCity);
      newAssignement.setAirlineLogo(AirlineLogo);
      newAssignement.setCeoAirline(CeoAirline);
      // Type 1=PAX, 2=CARGO, 3=TRF, 4=Business-PAX
      newAssignement.setRoutenArt(routenArt);
      newAssignement.setGepaeck(GesamtGepaeck);
      newAssignement.setGewichtPax(GewichtPax);

      if (provision > 100.0) {
        provision = 100.0;
      }

      if (provision < 0.0) {
        provision = 0.0;
      }

      newAssignement.setProvision(provision);

      newAssignement.setIdFBO(idFBO);
      newAssignement.setIdTerminal(idTerminal);

      newAssignement.setIcaoCodeFluggesellschaft(IcaoCode);
      newAssignement.setVerlaengert(false);
      newAssignement.setGesplittet(false);
      newAssignement.setIdjob(-1);
      newAssignement.setKonvertiert(false);

      //*********** Langstrecke eintragen
      if (Distanz[0] > 500) {
        newAssignement.setLangstrecke(true);
      } else {
        newAssignement.setLangstrecke(false);
      }

      // Daten für Fluggesellschaft schreiben
      int mengePax = aktFg.getErzeugteJobs();
      int mengeCargo = aktFg.getErzeugtesCargo();

      if (idAirline > 0) {
        if (routenArt == 1 || routenArt == 4) {
          aktFg.setErzeugteJobs(mengePax + paxGroesse);
        } else if (routenArt == 2) {
          aktFg.setErzeugtesCargo(mengeCargo + paxGroesse);
        }

        em.getTransaction().begin();
        em.merge(aktFg);
        em.getTransaction().commit();

      }

      // Daten für Route schreiben
      int mengeRoute = 0;
      try {
        mengeRoute = aktRoute.getErzeugteMenge();
      } catch (NullPointerException e) {
        mengeRoute = 0;
      }

      aktRoute.setErzeugteMenge(mengeRoute + paxGroesse);

      em.getTransaction().begin();
      em.merge(aktRoute);
      em.getTransaction().commit();

      return newAssignement;

      
    }

    return null;
  }

  private static void PreiskalkulationPassagiere() {

    if (paxGroesse == 0) {
      paxGroesse = 1;
    }

    if (maxPassengerTitle > 1) {
      Comment = PassengerNames[Zufallszahl.nextInt(maxPassengerTitle)];
    } else {
      Comment = PassengerNames[0];
    }

    /*
            *********************** Preiskalkulation *********************************
     */
    double PriceForPassenger = config.getPreisFuerPassagier();;
    double betrag = PriceForPassenger;
    double SubventionsFaktor = CONF.getSubvention(Airport_FromICAO.getKlasse());
    double entfernung = Distanz[0];
    int Menge = paxGroesse;

    //vorläufig auser Kraft gesetzt für Routen
    SubventionsFaktor = 1;

    double MeilenPreis = (betrag / 100.0);

    if (Menge == 0) {
      Menge = 1;
    }

    if (entfernung <= 25) {
      MeilenPreis = (MeilenPreis * 6.0 * SubventionsFaktor);
    } else if (entfernung > 25 && entfernung <= 50) {
      MeilenPreis = (MeilenPreis * 6.0 * SubventionsFaktor);
    } else if (entfernung > 50 && entfernung <= 75) {
      MeilenPreis = (MeilenPreis * 5.0) * SubventionsFaktor;
    } else if (entfernung > 75 && entfernung <= 100) {
      MeilenPreis = (MeilenPreis * 3.5);
    } else if (entfernung > 100 && entfernung <= 150) {
      MeilenPreis = MeilenPreis * 3;
    } else if (entfernung > 150 && entfernung <= 250) {
      MeilenPreis = MeilenPreis * 2;
    } else if (entfernung > 150 && entfernung <= 250) {
      MeilenPreis = MeilenPreis * 1.9;
    } else if (entfernung > 250 && entfernung <= 350) {
      MeilenPreis = MeilenPreis * 1.85;
    } else if (entfernung > 350 && entfernung <= 500) {
      MeilenPreis = MeilenPreis * 1.6;
    } else if (entfernung > 500 && entfernung <= 750) {
      MeilenPreis = MeilenPreis * 0.9;
    } else if (entfernung > 750 && entfernung <= 850) {
      MeilenPreis = MeilenPreis * 0.95;
    } else if (entfernung > 850 && entfernung <= 1000) {
      MeilenPreis = MeilenPreis * 1;
    } else if (entfernung > 1000 && entfernung <= 2000) {
      MeilenPreis = MeilenPreis * 0.95;
    } else if (entfernung > 2000 && entfernung <= 3000) {
      MeilenPreis = MeilenPreis * 0.75;
    } else if (entfernung > 3000 && entfernung <= 3500) {
      MeilenPreis = MeilenPreis * 0.65;
    } else if (entfernung > 3500 && entfernung <= 5000) {
      MeilenPreis = MeilenPreis * 0.40;
    } else if (entfernung > 5000) {
      MeilenPreis = MeilenPreis * 0.35;
    }

    betrag = MeilenPreis;
    PriceForPassenger = betrag;

    if (Menge > 5 && Menge <= 10) {
      PriceForPassenger = betrag * 0.97;
    } else if (Menge > 10 && Menge <= 15) {
      PriceForPassenger = betrag * 0.96;
    } else if (Menge > 15 && Menge <= 20) {
      PriceForPassenger = betrag * 0.94;
    } else if (Menge > 20 && Menge <= 30) {
      PriceForPassenger = betrag * 0.92;
    } else if (Menge > 30 && Menge <= 50) {
      PriceForPassenger = betrag * 0.90;
    } else if (Menge > 50 && Menge <= 100) {
      PriceForPassenger = betrag * 0.90;
    }

    Paying = Math.round((PriceForPassenger * Menge * entfernung) * CONF.zufallszahlDouble(0.98, 1.05));

  }

  private static void PreiskalkulationCargo() {

    if (paxGroesse == 0) {
      paxGroesse = 1;
    }

    if (maxPassengerTitle > 1) {
      Comment = PassengerNames[Zufallszahl.nextInt(maxPassengerTitle)];
    } else {
      Comment = PassengerNames[0];
    }

    if (paxGroesse > 40000) {
      paxGroesse = 40000;
    }

    /*
            *********************** Preiskalkulation *********************************
     */
    double PriceForCargo = config.getPreisFuerCargokg();
    double betrag = PriceForCargo;
    double SubventionsFaktor = CONF.getSubvention(Airport_FromICAO.getKlasse());
    double entfernung = Distanz[0];
    int Menge = paxGroesse;

    if (SubventionsFaktor <= 0) {
      SubventionsFaktor = 1;
    }

    SubventionsFaktor = 1;

    double MeilenPreis = (betrag / 100.0);

    if (Menge == 0) {
      Menge = 1;
    }

    if (entfernung <= 25) {
      MeilenPreis = MeilenPreis * 6 * SubventionsFaktor;
    } else if (entfernung > 25 && entfernung <= 50) {
      MeilenPreis = MeilenPreis * 5 * SubventionsFaktor;
    } else if (entfernung > 50 && entfernung <= 75) {
      MeilenPreis = MeilenPreis * 4 * SubventionsFaktor;
    } else if (entfernung > 75 && entfernung <= 100) {
      MeilenPreis = MeilenPreis * 3;
    } else if (entfernung > 100 && entfernung <= 150) {
      MeilenPreis = MeilenPreis * 2;
    } else if (entfernung > 150 && entfernung <= 250) {
      MeilenPreis = MeilenPreis * 1.5;
    } else if (entfernung > 250 && entfernung <= 350) {
      MeilenPreis = MeilenPreis * 1.0;
    } else if (entfernung > 350 && entfernung <= 500) {
      MeilenPreis = MeilenPreis * 0.75;

      /*
      ******** Ab hier greift die Langstrecke
       */
    } else if (entfernung > 500 && entfernung <= 600) {
      MeilenPreis = MeilenPreis * 0.4;
    } else if (entfernung > 600 && entfernung <= 700) {
      MeilenPreis = MeilenPreis * 0.35;
    } else if (entfernung > 700 && entfernung <= 850) {
      MeilenPreis = MeilenPreis * 0.5;
    } else if (entfernung > 850 && entfernung <= 1000) {
      MeilenPreis = MeilenPreis * 0.45;
    } else if (entfernung > 1000 && entfernung <= 2000) {
      MeilenPreis = MeilenPreis * 0.4;
    } else if (entfernung > 2000 && entfernung <= 3000) {
      MeilenPreis = MeilenPreis * 0.35;
    } else if (entfernung > 3000 && entfernung <= 3500) {
      MeilenPreis = MeilenPreis * 0.3;
    } else if (entfernung > 3500 && entfernung <= 5000) {
      MeilenPreis = MeilenPreis * 0.3;
    } else if (entfernung > 5000) {
      MeilenPreis = MeilenPreis * 0.2;
    }

    betrag = MeilenPreis;

    if (Menge > 0 && Menge <= 50) {
      PriceForCargo = betrag * 1.0;
    } else if (Menge > 50 && Menge <= 100) {
      PriceForCargo = betrag * 0.99;
    } else if (Menge > 100 && Menge <= 200) {
      PriceForCargo = betrag * 0.98;
    } else if (Menge > 200 && Menge <= 500) {
      PriceForCargo = betrag * 0.97;
    } else if (Menge > 500 && Menge <= 750) {
      PriceForCargo = betrag * 0.96;
    } else if (Menge > 750 && Menge <= 1000) {
      PriceForCargo = betrag * 0.90;
    } else if (Menge > 1000 && Menge <= 2000) {
      PriceForCargo = betrag * 0.89;
    } else if (Menge > 2000 && Menge <= 3000) {
      PriceForCargo = betrag * 0.80;
    } else if (Menge > 3000 && Menge <= 4000) {
      PriceForCargo = betrag * 0.80;
    } else if (Menge > 4000 && Menge <= 5000) {
      PriceForCargo = betrag * 0.75;
    } else if (Menge > 5000 && Menge <= 7500) {
      PriceForCargo = betrag * 0.73;
    } else if (Menge > 7500 && Menge <= 10000) {
      PriceForCargo = betrag * 0.70;
    } else if (Menge > 10000) {
      PriceForCargo = betrag * 0.65;
    }

    Paying = Math.round((betrag * Menge * entfernung) * CONF.zufallszahlDouble(0.98, 1.05));

  }

  private static List<Fluggesellschaft> getAllAirlines() {
    return findAllAirlines();
  }

  private static List<Flugrouten> getAllFlugRouten(int idFluggesellschaft) {
    return findAllFlugroutenByIDFluggesellschaft(idFluggesellschaft);
  }

  private static Airport getAirport(String ICAO) {
    return findAllbyIcaoSingle(ICAO);
  }

  private static String[] getFlugziele(String Flugziele) {
    String[] Nullwert = {"System"};

    try {
      return Flugziele.split(",");
    } catch (NullPointerException e) {
    }

    return Nullwert;

  }

  private static String[] getPersonTitle(String PersonTitle) {
    String[] Nullwert = {"System"};

    try {
      return PersonTitle.split(",");
    } catch (NullPointerException e) {
    }

    return Nullwert;
  }

  private static int[] DistanzBerechnung(double vonLongitude, double vonLatitude, double nachLongitude, double nachLatitude) {

//    double dif_Long = 71.5 * (vonLongitude - nachLongitude);
//    double dif_Lati = 113.3 * (vonLatitude - nachLatitude);
//
//    double entfernung = Math.sqrt(dif_Long * dif_Long + dif_Lati * dif_Lati);
    double vonLati = Math.toRadians(vonLatitude);
    double vonLong = Math.toRadians(vonLongitude);
    double nachLati = Math.toRadians(nachLatitude);
    double nachLong = Math.toRadians(nachLongitude);

    double sin_vonLati = Math.sin(vonLati);
    double sin_nachLati = Math.sin(nachLati);
    double cos_vonLati = Math.cos(vonLati);
    double cos_nachLati = Math.cos(nachLati);

    double Entfernung_Radian = Math.acos(sin_vonLati * sin_nachLati + cos_vonLati * cos_nachLati * Math.cos(nachLong - vonLong));

    Double ZielKurs = Math.acos((sin_nachLati - sin_vonLati * Math.cos(Entfernung_Radian)) / (cos_vonLati * Math.sin(Entfernung_Radian)));
    ZielKurs = Math.toDegrees(ZielKurs);

    double dist = 6378.137 * acos(sin(vonLati) * sin(nachLati) + cos(vonLati) * cos(nachLati) * cos(nachLong - vonLong));

    if (Math.sin(nachLong - vonLong) < 0.0) {
      ZielKurs = 360 - ZielKurs;
    }

//    System.out.println("Distance in KM : " + String.valueOf(entfernung));
//    System.out.println("Distance in NM : " + String.valueOf(entfernung * 0.53995680345));
//    System.out.println("Kurs : " + String.valueOf(Math.round(ZielKurs)));
    int Ergebnis[] = new int[2];
    Ergebnis[0] = (int) Math.round(dist * 0.53995680345);
    Ergebnis[1] = (int) Math.round(ZielKurs);

    return Ergebnis;

  }

  public static String getVonIcao() {
    return vonIcao;
  }

  public static void setVonIcao(String vonIcao) {
    vonIcao = vonIcao;
  }

  public static String getNachIcao() {
    return nachIcao;
  }

  public static void setNachIcao(String nachIcao) {
    nachIcao = nachIcao;
  }

  public static String getStorytext() {
    return Storytext;
  }

  public static void setStorytext(String Storytext) {
    Storytext = Storytext;
  }

  public static double zufallszahlDouble(double min, double max) {
    Random r = new Random();
    return (double) min + (max - min) * r.nextDouble();
  }

  // Data Operation
  private static EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  private static void create(Assignement assignement) {
    EntityManager em = null;
    try {
      em.getTransaction().begin();
      em.persist(assignement);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  private static int getMinCargo(int Klasse) {

    switch (Klasse) {
      case 1:
        return 1000;
      case 2:
        return 500;
      case 3:
        return 250;
      case 4:
        return 100;
      case 5:
        return 50;
      case 6:
        return 10;
      case 7:
        return 5;
      case 8:
        return 1;
      case 9:
        // wie der Klasse 6
        return 10;
      default:
        break;
    }

    return 1;
  }

  private static int getMinPax(int Klasse) {
    return CONF.getMinPax(Klasse);
  }

  private static int getMaxPax(int Klasse) {
    return CONF.getMaxPax(Klasse);
  }

  private static void flugroutenPruefen() {

    int Anzahl = 0;

    // nach 90 Tagen ohne traffic wird die Route deaktiviert
    long datum = new Date().getTime() - 7776000000L; // 7776000000 = 90 Tage

    em.getTransaction().begin();
    Anzahl = em.createNativeQuery("update Flugrouten set aktiv = false where letzterFlug <= '" + sqldf.format(new Date(datum)) + "' and aktiv = true;").executeUpdate();
    System.out.println("update Flugrouten set aktiv = false where letzterFlug <= '" + sqldf.format(new Date(datum)) + "' and aktiv = true;");
    em.getTransaction().commit();

    System.out.println("Anzahl verwaiste Flugrouten : " + Anzahl);

  }

// **************************************** JPA Abfragen beginn ***************************************************************************
  private static Feinabstimmung readConfig() {
    return getEntityManager().createQuery("SELECT c from Feinabstimmung c", Feinabstimmung.class
    ).setMaxResults(1).getSingleResult();

  }

  private static List<Fluggesellschaft> findAllAirlines() {
    return getEntityManager().createQuery("SELECT a from Fluggesellschaft a", Fluggesellschaft.class
    ).getResultList();

  }

  private static List<Flugrouten> getAirportsAusRouten() {
    return em.createQuery("SELECT a FROM Flugrouten a WHERE a.aktiv = TRUE GROUP BY a.vonIcao", Flugrouten.class
    ).getResultList();

  }

  private static Fluggesellschaft getFluggesellschaft(int id) {
    return getEntityManager().createQuery("SELECT f from Fluggesellschaft f WHERE f.idFluggesellschaft = :id", Fluggesellschaft.class
    )
            .setParameter("id", id)
            .getSingleResult();

  }

  private static List<Flugrouten> findAllFlugroutenByIDFluggesellschaft(int idFluggesellschaft) {
    return getEntityManager().createQuery("SELECT f FROM Flugrouten f WHERE f.idFluggesellschaft = :fgid", Flugrouten.class
    )
            .setParameter("fgid", idFluggesellschaft)
            .getResultList();

  }

  private static List<Flugrouten> getAllFlugrouten(String icao) {
    return getEntityManager().createQuery("SELECT f FROM Flugrouten f WHERE f.aktiv = TRUE AND f.vonIcao = :icao", Flugrouten.class
    )
            .setParameter("icao", icao)
            .getResultList();

  }

  private static Airport findAllbyIcaoSingle(String icao) {
    try {
      return getEntityManager().createQuery("SELECT a FROM Airport a WHERE a.icao =:icao", Airport.class
      ).setParameter("icao", icao).getSingleResult();
    } catch (NoResultException e) {
      return null;

    }
  }

  private static List<ViewAirportTransfers> getTransfer(String icao, Date startDatum, Date endDatum) {
    return getEntityManager().createQuery("SELECT v from ViewAirportTransfers v where v.datum BETWEEN :startDatum and :endDatum and v.icao = :icao", ViewAirportTransfers.class
    )
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
