/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashadvance_multicompany;

import static cashadvance_multicompany.ConnectDB2.ConnectionDB;
import static cashadvance_multicompany.ShowCashAdvanceAcc.txtReceivedate;
import static cashadvance_multicompany.frmLogin.LoginCompanyName;
import static cashadvance_multicompany.frmLogin.LoginCono;
import static cashadvance_multicompany.frmLogin.LoginDivision;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Jilasak
 */
public class ClassgetReport {

    public void getReportAdvReq1(String CASH_NO, String testornot) {
        // TODO add your handling code here:
        String PathFile = System.getProperty("user.dir").toString() + "\\report\\";
        String name = PathFile + "RptCashAdvanceReqS1Form" + testornot + ".jasper";

        try {
            Classgetdata cgd = new Classgetdata();

            Map parameterss = new HashMap();
            Connection conn = ConnectionDB();
            //JRResultSetDataSource resultSetDataSource = new   JRResultSetDataSource(Rs1);
            parameterss.put("CompannyName", LoginCompanyName.trim());
            parameterss.put("cono", LoginCono.trim());
            parameterss.put("divi", LoginDivision.trim());
            parameterss.put("cash_cano", CASH_NO);

            JasperPrint print = JasperFillManager.fillReport(name, parameterss, conn);

            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void getReportAdvReq4(String CASH_NO) {
        // TODO add your handling code here:
        String PathFile = System.getProperty("user.dir").toString() + "\\report\\";
                String name = PathFile + "RptCashAdvanceReqS4Form.jasper";
//        String name = PathFile + "RptCashAdvanceRequesterRep.jasper";

        try {
            Classgetdata cgd = new Classgetdata();
            String QueryMainString = cgd.GetSqlDataReportCashAdvReqStep4(CASH_NO);

            Map parameterss = new HashMap();
            Connection conn = ConnectionDB();
            //JRResultSetDataSource resultSetDataSource = new   JRResultSetDataSource(Rs1);
            parameterss.put("CompannyName", LoginCompanyName.trim());
            parameterss.put("QueryMainString", QueryMainString);
            JasperPrint print = JasperFillManager.fillReport(name, parameterss, conn);

            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void getReportPreviewStep1(String CASH_NO) {
        // TODO add your handling code here:
        String PathFile = System.getProperty("user.dir").toString() + "\\report\\";
        String name = PathFile + "CADV_PV01.jasper";

        try {
            Classgetdata cgd = new Classgetdata();

            Map parameterss = new HashMap();
            Connection conn = ConnectionDB();
            //JRResultSetDataSource resultSetDataSource = new   JRResultSetDataSource(Rs1);
            parameterss.put("CONO", Integer.parseInt(LoginCono.trim()));
            parameterss.put("DIVI", LoginDivision.trim());
            parameterss.put("CASH_CANO", CASH_NO);
            parameterss.put("CompannyName", LoginCompanyName.trim());
            JasperPrint print = JasperFillManager.fillReport(name, parameterss, conn);

            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void getReportADV1() {
        // TODO add your handling code here:

        String PathFile = System.getProperty("user.dir").toString() + "\\report\\";
        String name = PathFile + "RptcashadvanceOutStanding.jasper";

        String date = txtReceivedate.getText().trim().toString();
        String datesub = date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);
        try {
            Classgetdata cgd = new Classgetdata();

            Map parameterss = new HashMap();
            Connection conn = ConnectionDB();
            //JRResultSetDataSource resultSetDataSource = new   JRResultSetDataSource(Rs1);
            parameterss.put("CompannyName", LoginCompanyName.trim());
            parameterss.put("cono", LoginCono.trim());
            parameterss.put("divi", LoginDivision.trim());
            parameterss.put("date", datesub);
            parameterss.put("ReceiveDateAt", date);

            JasperPrint print = JasperFillManager.fillReport(name, parameterss, conn);

            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void getReportACC_1(String fdate, String tdate) {
        // TODO add your handling code here:

        String PathFile = System.getProperty("user.dir").toString() + "\\report\\";
        String name = PathFile + "RptCashAdvanceAccS4.jasper";

        try {
            Classgetdata cgd = new Classgetdata();
            Map parameterss = new HashMap();
            Connection conn = ConnectionDB();
            parameterss.put("CompannyName", LoginCompanyName.trim());
            parameterss.put("cono", LoginCono.trim());
            parameterss.put("divi", LoginDivision.trim());
            parameterss.put("fdate", fdate);
            parameterss.put("tdate", tdate);

            JasperPrint print = JasperFillManager.fillReport(name, parameterss, conn);

            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
