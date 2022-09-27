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

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Random;

/**
 *
 * @author Stefan Klees
 */
public class CONF {

  public static double Basis_Gallon_nach_KG_AVGAS = 2.74583597;      // Multiplikator
  public static double Basis_Gallon_nach_KG_JETA = 3.0661835;      // Multiplikator

  public static boolean RandomJob = false;                  // 

  public static double getFlughafenAufschlag(int klasse) {
    switch (klasse) {
      case 1:
        return 30;
      case 2:
        return 25;
      case 3:
        return 20;
      case 4:
        return 15;
      case 5:
        return 10;
      case 6:
        return 10;
      case 7:
        return 5;
      case 8:
        return 2.5;
      case 10:
        return 10;
      case 11:
        return 10;
      default:
        break;
    }
    return 0;
  }

  public static double getSubvention(int klasse) {
    switch (klasse) {
      case 1:
        return 0;
      case 2:
        return 0;
      case 3:
        return 0;
      case 4:
        return 0;
      case 5:
        return 0;
      case 6:
        return 10;
      case 7:
        return 20;
      case 8:
        return 30;
      case 10:
        return 0;
      case 11:
        return 0;
      default:
        break;
    }
    return 0;
  }

  public static int[] DistanzBerechnung(double vonLongitude, double vonLatitude, double nachLongitude, double nachLatitude) {

    double dif_Long = 71.5 * (vonLongitude - nachLongitude);
    double dif_Lati = 113.3 * (vonLatitude - nachLatitude);

    double entfernung = Math.sqrt(dif_Long * dif_Long + dif_Lati * dif_Lati);

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
//    Ergebnis[0] = (int) Math.round(entfernung * 0.53995680345);
    Ergebnis[1] = (int) Math.round(ZielKurs);

    return Ergebnis;

  }

  public static double zufallszahlDouble(double min, double max) {
    Random r = new Random();
    return (double) min + (max - min) * r.nextDouble();
  }

  /**
   * Zufallszahl von "min"(einschliesslich) bis "max"(einschliesslich) Beispiel:zufallszahl(4,10); Moegliche
   * Zufallszahlen 4,5,6,7,8,9,10
   *
   * @param min
   * @param max
   * @return
   */
  public static int zufallszahl(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min + 1) + min;
  }

  public static int getMinCargo(int Klasse) {

    switch (Klasse) {
      case 1:
        return 1000;
      case 2:
        return 750;
      case 3:
        return 500;
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
        return 10;
      case 10:
        return 10;
      case 11:
        return 10;
      case 12:
        return 1;
      default:
        break;
    }

    return 0;
  }

  public static int getMaxCargo(int Klasse) {
    switch (Klasse) {
      case 1:
        return 5000;
      case 2:
        return 3000;
      case 3:
        return 2000;
      case 4:
        return 1000;
      case 5:
        return 500;
      case 6:
        return 100;
      case 7:
        return 50;
      case 8:
        return 10;
      case 9:
        return 10;
      case 10:
        return 3000;
      case 11:
        return 1000;
      default:
        return 0;
    }
  }

  public static int getMinPax(int Klasse) {

    switch (Klasse) {
      case 1:
        return 30;
      case 2:
        return 20;
      case 3:
        return 15;
      case 4:
        return 5;
      case 5:
        return 3;
      case 6:
        return 2;
      case 7:
        return 1;
      case 8:
        return 1;
      case 9:
        // wie der Klasse 6
        return 1;
      case 10:
        return 1;
      case 11:
        return 1;
      default:
        return 1;
    }

  }

  public static int getMaxPax(int Klasse) {
    switch (Klasse) {
      case 1:
        return 50;
      case 2:
        return 35;
      case 3:
        return 25;
      case 4:
        return 15;
      case 5:
        return 10;
      case 6:
        return 5;
      case 7:
        return 2;
      case 8:
        return 1;
      case 9:
        return 3;
      case 10:
        return 10;
      case 11:
        return 7;
      default:
        return 0;
    }
  }

}
