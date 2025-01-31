/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashadvance_multicompany;

import static cashadvance_multicompany.ConnectDB2.ConnectionDB;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jilasak
 */
public class ClassCheckDataProgram {

    public String GetDateFormatSetsubmitacc(Date dateToset) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("ddMMyy", Locale.ENGLISH);
        String formatted = format1.format(dateToset);
        return formatted;
    }

    public String Round2digiReturn(Double Amount) {
        String digi4 = new DecimalFormat("0.0000").format(Amount);
        String digi3 = new DecimalFormat("0.000").format(Double.parseDouble(digi4));
        String formatted = new DecimalFormat("0.00").format(Double.parseDouble(digi3));
        return formatted;
    }

    public static String SubDateForM3(String date) {
        String DateForM3 = date.trim();
        DateForM3 = DateForM3.substring(6, 8) + "" + DateForM3.substring(4, 6) + "" + DateForM3.substring(2, 4);
        return DateForM3;
    }

    public double Double2digitReturn(double number) {

        try {
            String numberBeforeconvert = String.valueOf(new DecimalFormat("##.##").format(Math.round(number * 100.0) / 100.0));
            double numberreturn = Double.parseDouble(numberBeforeconvert);
            return numberreturn;
        } catch (Exception e) {
            return 0;
        }

    }

    public BigDecimal BigDicimal2digitReturn(BigDecimal num) {
//        BigDecimal originalValue = new BigDecimal("123.456789");

        // Set to 2 decimal places with rounding
        BigDecimal twoDecimalValue = num.setScale(2, RoundingMode.HALF_UP);

        System.out.println("Original Value: " + num);
        System.out.println("Two Decimal Places: " + twoDecimalValue);
        return twoDecimalValue;
    }

    public boolean CheckdatainputDouble(String TextData) {
        try {
            Double valuenumber = Double.parseDouble(TextData);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean CheckdatainputDate(Date TextData) {
        if (TextData != null) {
            return true;
        } else {
            return false;
        }

    }

    public boolean CheckdatainputString(String TextData) {
        if (TextData.trim() != null && !TextData.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    public String GetDateFormatSet(Date dateToset) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        String formatted = format1.format(dateToset);
        return formatted;
    }

    public String GetDateDecmalCurrenttime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    public String get_SemiColonValue0(String TextFieldto) {
        String TextFieldtos[] = TextFieldto.split(":");
        return TextFieldtos[0]; // GET COST CENTER
    }

    public String GetDateCurrenttime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    public Date GetDecmalTodate(int startDateString) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        Date startDate;
        try {
            String DateString = String.valueOf(startDateString);
            startDate = df.parse(DateString);
            String newDateString = df.format(startDate);
            //   System.out.println(newDateString);
            return startDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String GetDateFormatSetShowString(Date dateToset) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formatted = format1.format(dateToset);
        return formatted;
    }
}
