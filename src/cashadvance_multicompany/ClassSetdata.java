/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashadvance_multicompany;

import static cashadvance_multicompany.CashAdvanRequest.radiotaxchec_1;
import static cashadvance_multicompany.ConnectDB2.ConnectionDB;
import static cashadvance_multicompany.frmLogin.LoginCono;
import static cashadvance_multicompany.frmLogin.LoginDivision;
import static cashadvance_multicompany.frmLogin.LoginUsername;
import static cashadvance_multicompany.frmLogin.Table_fin_caadd;
import static cashadvance_multicompany.frmLogin.Table_fin_cact;
import static cashadvance_multicompany.frmLogin.Table_fin_sead;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.simple.parser.ParseException;

/**
 *
 * @author aticha
 */
public class ClassSetdata {

    DefaultTableModel model;
    public static String Para = "";

    public void UpdateVoucher(String Advance, String Voucher) {

        try {

            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();
            String StrInsertHeader = "UPDATE " + Table_fin_caadd + " \n";
            StrInsertHeader += " SET   CASH_REF2= '" + Voucher + "' \n";
            StrInsertHeader += " WHERE CASH_CANO =  '" + Advance + "' \n";
            StrInsertHeader += " AND  CASH_CONO =  '" + LoginCono + "' \n";
            StrInsertHeader += " AND  CASH_DIVI =  '" + LoginDivision + "' \n";

            PreparedStatement UpdateSrnNo = conn.prepareStatement(StrInsertHeader);
            UpdateSrnNo.executeUpdate(StrInsertHeader);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void UpdateVoucher_APS(String Advance, String Voucher, String InvoiceNo) {

        try {

            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();
            String StrInsertHeader = "UPDATE " + Table_fin_sead + " \n";
            StrInsertHeader += " SET   SETT_VCAPS= '" + Voucher.trim() + "' \n";
            StrInsertHeader += " WHERE SETT_CANO =  '" + Advance.trim() + "' ";
            StrInsertHeader += " AND SETT_CONO =  '" + LoginCono + "' ";
            StrInsertHeader += " AND  SETT_DIVI =  '" + LoginDivision + "' ";
            StrInsertHeader += " AND  SETT_INVC =  '" + InvoiceNo.trim() + "' ";

            PreparedStatement UpdateSrnNo = conn.prepareStatement(StrInsertHeader);
            UpdateSrnNo.executeUpdate(StrInsertHeader);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void UpdateVoucher_SettleARS(String Advance, String Voucher) {

        try {

            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();
            String StrInsertHeader = "UPDATE " + Table_fin_caadd + " \n";
            StrInsertHeader += " SET   CASH_VCSET= '" + Voucher.trim() + "' \n";
            StrInsertHeader += " WHERE CASH_CANO =  '" + Advance.trim() + "' ";
            StrInsertHeader += " AND CASH_CONO =  '" + LoginCono + "' ";
            StrInsertHeader += " AND  CASH_DIVI =  '" + LoginDivision + "' ";

            PreparedStatement UpdateSrnNo = conn.prepareStatement(StrInsertHeader);
            UpdateSrnNo.executeUpdate(StrInsertHeader);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public String SaveCashAdvanceRequesterHeaderStep1Form( //CASH ADVANCE REQUESTER  STEP 1
            String CASH_CANO, String CASH_DATE,
            String CASH_EMPY, String CASH_COST, String CASH_OPDF,
            String CASH_OPDT, String CASH_PPS1, String CASH_PPS2,
            String CASH_PPS3, double CASH_AMT, String CASH_REDP,
            String CASH_STDP, String REF_PO, String CASH_REQB, String CASH_STAT, String CASH_PAYSUP,
            String CASH_CONO, String CASH_DIVI, String CASH_EXPGP, String CASH_WHTAX_TYPE, String PO_NO, String CASH_PAYT) {

        try {

            Connection conn = ConnectionDB();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();
            String StrInsertHeader;
            String StrUpdateepnu_no = "";
            String StrUpdateHeader = "";

            if (CASH_CANO == "" || CASH_CANO.isEmpty()) {
                StrUpdateepnu_no = "UPDATE  " + Table_fin_cact + " "
                        + "SET  CASH_NO =(SELECT CASH_NO+1 FROM " + Table_fin_cact + " where cash_cono = '" + LoginCono.trim() + "' AND CASH_DIVI = '" + LoginDivision.trim() + "') "
                        + "where cash_cono = '" + LoginCono.trim() + "' "
                        + "AND CASH_DIVI = '" + LoginDivision.trim() + "'";
                PreparedStatement UpdatePrNo = conn.prepareStatement(StrUpdateepnu_no);
                UpdatePrNo.executeUpdate(StrUpdateepnu_no);
                CASH_CANO = cgd.Get_CASHREQ_CASHNO();

                PreparedStatement InsertHeader = null;
                StrInsertHeader = "INSERT INTO " + Table_fin_caadd;
                StrInsertHeader += " (CASH_CANO, CASH_DATE, CASH_EMPY,"
                        + "         CASH_COST, CASH_PPS1, CASH_PPS2, ";
                StrInsertHeader += "  CASH_PPS3, CASH_OPDF, CASH_OPDT,"
                        + "         CASH_REDP, CASH_STDP, CASH_AMT, ";
                StrInsertHeader += "  CASH_REQB, CASH_STAT,CASH_REF1, CASH_REF3, "
                        + "CASH_CONO, CASH_DIVI, CASH_EXPG, CASH_PAYSUP, CASH_WHTYPE, CASH_RETOBANK , CASH_PAYT , CASH_WHTAX) ";

                StrInsertHeader += "VALUES ('" + CASH_CANO + "','" + CASH_DATE + "','" + CASH_EMPY + "', "
                        + "'" + CASH_COST + "','" + CASH_PPS1.trim().replace("'", "") + "','" + CASH_PPS2.trim().replace("'", "") + "', "
                        + "'" + CASH_PPS3.trim().replace("'", "") + "','" + CASH_OPDF + "','" + CASH_OPDT + "', "
                        + CASH_REDP + "," + CASH_STDP + ",'" + CASH_AMT + "', "
                        + "'" + CASH_REQB.toUpperCase().trim() + "','" + CASH_STAT + "','" + REF_PO + "' , '" + PO_NO + "' "
                        + ",'" + CASH_CONO + "','" + CASH_DIVI + "','" + CASH_EXPGP.trim() + "','" + CASH_PAYSUP + "','" + CASH_WHTAX_TYPE.trim() + "',' ', '" + CASH_PAYT.trim() + "','0.00')";
                InsertHeader = conn.prepareStatement(StrInsertHeader);
                InsertHeader.executeUpdate(StrInsertHeader);
                System.out.println(StrInsertHeader);

            } else {
                PreparedStatement UpdateHeader = null;
                Connection con = ConnectionDB();
                StrUpdateHeader = "UPDATE " + Table_fin_caadd + "\n"
                        + "SET CASH_DATE= '" + CASH_DATE.trim() + "', CASH_EMPY='" + CASH_EMPY + "', CASH_COST='" + CASH_COST + "', CASH_PPS1='" + CASH_PPS1 + "', CASH_PPS2='" + CASH_PPS2 + "',"
                        + " CASH_PPS3='" + CASH_PPS3 + "',CASH_EXPG='" + CASH_EXPGP + "', CASH_PAYSUP='" + CASH_PAYSUP + "', CASH_WHTYPE ='" + CASH_WHTAX_TYPE + "', CASH_OPDF='" + CASH_OPDF + "',"
                        + " CASH_OPDT= '" + CASH_OPDT + "', CASH_REDP= '" + CASH_REDP + "', CASH_STDP= '" + CASH_STDP + "', CASH_AMT='" + CASH_AMT + "', "
                        + " CASH_STAT='" + CASH_STAT + "', CASH_REF1 = '" + REF_PO + "' , CASH_REF3 = '" + PO_NO.trim() + "' , CASH_PAYT = '" + CASH_PAYT.trim() + "'\n"
                        + "WHERE CASH_CANO = '" + CASH_CANO + "'";
                UpdateHeader = conn.prepareStatement(StrUpdateHeader);
                UpdateHeader.executeUpdate(StrUpdateHeader);
                System.out.println(StrUpdateHeader);
            }
            return CASH_CANO;

        } catch (Exception e) {
            System.out.print(e.toString());
            return "";
        }

    }

    public String SaveCashAdvanceAccountStep1Form( //CASH ADVANCE Account  STEP 1
            String CASH_CANO, String CASH_TRAD,
            String CASH_CHEB, String CASH_STAT) {

        try {

            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();

            if (CASH_STAT.equals("99")) {
                String StrInsertHeader = "UPDATE " + Table_fin_caadd;
                StrInsertHeader += " SET CASH_TRAD='" + CASH_TRAD + "' ,CASH_CHEB='" + CASH_CHEB + "',CASH_STAT='" + CASH_STAT + "'   ";
                StrInsertHeader += ",CASH_CANB='" + LoginUsername + "'   WHERE CASH_CANO = '" + CASH_CANO + "' "
                        + "AND CASH_CONO = '" + LoginCono.trim() + "' AND CASH_DIVI = '" + LoginDivision.trim() + "'";

                InsertHeader = conn.prepareStatement(StrInsertHeader);
                InsertHeader.executeUpdate(StrInsertHeader);
            } else {
                String StrInsertHeader = "UPDATE " + Table_fin_caadd;
                StrInsertHeader += " SET CASH_TRAD='" + CASH_TRAD + "' ,CASH_CHEB='" + CASH_CHEB + "',CASH_STAT='" + CASH_STAT + "'  ";
                StrInsertHeader += " WHERE CASH_CANO='" + CASH_CANO + "' ";
                StrInsertHeader += " AND  CASH_CONO ='" + LoginCono.trim() + "' ";
                StrInsertHeader += " AND  CASH_DIVI ='" + LoginDivision.trim() + "' ";

                InsertHeader = conn.prepareStatement(StrInsertHeader);
                InsertHeader.executeUpdate(StrInsertHeader);
            }

            return CASH_CANO;

        } catch (Exception e) {
            return "";
        }

    }

    public String SaveCashAdvanceAccountStep2Form( //CASH ADVANCE Account  STEP 1
            String CASH_CANO, String CASH_REDA,
            String CASH_BANK, String CASH_CHQN, String CASH_DUED, String CASH_STAT) {

        try {

            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();

            if (CASH_STAT.equals("99")) {

                String StrInsertHeader = "UPDATE " + Table_fin_caadd;
                StrInsertHeader += " SET CASH_REDA='" + CASH_REDA + "' ,CASH_BANK='" + CASH_BANK + "',CASH_CHQN='" + CASH_CHQN + "'  ";
                StrInsertHeader += " ,CASH_DUED='" + CASH_DUED + "',CASH_STAT='" + CASH_STAT + "',CASH_CANB='" + LoginUsername + "' ";
                StrInsertHeader += " WHERE CASH_CANO = '" + CASH_CANO + "' AND CASH_CONO = '" + LoginCono.trim() + "' AND CASH_DIVI = '" + LoginDivision.trim() + "' ";

                InsertHeader = conn.prepareStatement(StrInsertHeader);
                InsertHeader.executeUpdate(StrInsertHeader);
            } else {

                String StrInsertHeader = "UPDATE " + Table_fin_caadd;
                StrInsertHeader += " SET CASH_REDA='" + CASH_REDA + "' ,CASH_BANK='" + CASH_BANK + "',CASH_CHQN='" + CASH_CHQN + "'  ";
                StrInsertHeader += " ,CASH_DUED='" + CASH_DUED + "',CASH_STAT='" + CASH_STAT + "' ";
                StrInsertHeader += " WHERE CASH_CANO='" + CASH_CANO + "' AND CASH_CONO = '" + LoginCono.trim() + "' AND CASH_DIVI = '" + LoginDivision.trim() + "'";
                System.out.println(StrInsertHeader);
                InsertHeader = conn.prepareStatement(StrInsertHeader);
                InsertHeader.executeUpdate(StrInsertHeader);

            }

            return CASH_CANO;

        } catch (Exception e) {
            return "";
        }

    }

    public String SaveCashAdvanceAccountStep5Form( //CASH ADVANCE Account  STEP 1
            String CASH_CANO, String CASH_STDA, String CASH_RTFR, String CASH_STAT, String CASH_WHTYPE, String CASH_REAMT, String CASH_RETOBANK) {

        try {

            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();

            String StrInsertHeader = "";
            if (CASH_STAT.equals("99")) {

                StrInsertHeader = "UPDATE " + Table_fin_caadd;
                StrInsertHeader += " SET CASH_STDA='" + CASH_STDA + "' ,CASH_RTFR='" + CASH_RTFR + "' ";
                StrInsertHeader += " ,CASH_STAT='" + CASH_STAT + "',CASH_CANB='" + LoginUsername + "' ";
                StrInsertHeader += " WHERE CASH_CANO='" + CASH_CANO + "' AND CASH_CONO = '" + LoginCono.trim() + "' AND CASH_DIVI = '" + LoginDivision.trim() + "'";

            } else {
                StrInsertHeader = "UPDATE " + Table_fin_caadd;
                StrInsertHeader += " SET CASH_STDA='" + CASH_STDA + "' ,CASH_RTFR='" + CASH_RTFR + "',CASH_RTFB='" + LoginUsername + "' ";
                StrInsertHeader += " ,CASH_STAT='" + CASH_STAT + "' , CASH_WHTYPE = '" + CASH_WHTYPE + "', CASH_REAMT = '" + CASH_REAMT + "' , ";
                StrInsertHeader += " CASH_RETOBANK = '" + CASH_RETOBANK + "'";
                StrInsertHeader += " WHERE CASH_CANO='" + CASH_CANO + "' AND CASH_CONO = '" + LoginCono.trim() + "'  AND CASH_DIVI = '" + LoginDivision.trim() + "'";
            }

            InsertHeader = conn.prepareStatement(StrInsertHeader);
            InsertHeader.executeUpdate(StrInsertHeader);

            return CASH_CANO;

        } catch (Exception e) {
            return "";
        }

    }

    public String SaveCashAdvanceAccountStepCheckSettle(String CASH_CANO, String CASH_STAT) {

        try {

            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();

            String StrInsertHeader = "";

            StrInsertHeader = "UPDATE " + Table_fin_caadd;
            StrInsertHeader += " SET CASH_STAT='" + CASH_STAT + "' \n";
            StrInsertHeader += " WHERE CASH_CANO='" + CASH_CANO + "'";
            StrInsertHeader += " AND CASH_CONO = '" + LoginCono.trim() + "' \n";
            StrInsertHeader += " AND CASH_DIVI = '" + LoginDivision.trim() + "'";

            InsertHeader = conn.prepareStatement(StrInsertHeader);
            InsertHeader.executeUpdate(StrInsertHeader);

            return CASH_CANO;

        } catch (Exception e) {
            return "";
        }

    }

    public String SaveCashAdvanceAccountStep4Form( //CASH ADVANCE Account  STEP 1
            String CASH_CANO, String CASH_STDT, String CASH_STAT, DefaultTableModel models, String CASH_WHTAX, String CASH_REAMT, String CASH_RETOBANK, String Transferdate) {

        try {
            String Transferdatesubmission = "";
            if (Transferdate.equals("")) {
                Transferdatesubmission = "null";
            } else {
                Transferdatesubmission = "'" + Transferdate + "'";
            }

            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();

//            System.out.println(StrDeleteDetail);
            String StrInsertHeader = "";
            if (CASH_STAT.equals("99")) {

                StrInsertHeader = "UPDATE " + Table_fin_caadd;
                StrInsertHeader += " SET CASH_STDT='" + CASH_STDT + "', CASH_WHTAX= '" + CASH_WHTAX + "' , CASH_REAMT = '" + CASH_REAMT.trim() + "',CASH_RETOBANK= '" + CASH_RETOBANK.trim() + "'";
                StrInsertHeader += " ,CASH_STAT='" + CASH_STAT + "',CASH_CANB='" + LoginUsername + "',CASH_RCRA= " + Transferdatesubmission + "";
                StrInsertHeader += " WHERE CASH_CANO='" + CASH_CANO + "' AND CASH_CONO = '" + LoginCono + "'";

            } else {
                StrInsertHeader = "UPDATE " + Table_fin_caadd;
                StrInsertHeader += " SET CASH_STDT='" + CASH_STDT + "', CASH_WHTAX= '" + CASH_WHTAX + "' , CASH_REAMT = '" + CASH_REAMT.trim() + "',CASH_RETOBANK= '" + CASH_RETOBANK.trim() + "' ";
                StrInsertHeader += " ,CASH_STAT='" + CASH_STAT + "',CASH_RCRA= " + Transferdatesubmission + "";
                StrInsertHeader += " WHERE CASH_CANO = '" + CASH_CANO + "' AND CASH_CONO = '" + LoginCono + "'";

            }

            InsertHeader = conn.prepareStatement(StrInsertHeader);
            InsertHeader.executeUpdate(StrInsertHeader);

            if (!radiotaxchec_1.isSelected()) {
                System.out.println("Delete OOOOO");
                PreparedStatement DeleteDetail = null;
                String StrDeleteDetail = "DELETE FROM " + Table_fin_sead
                        + " WHERE SETT_CANO=" + CASH_CANO
                        + " AND SETT_CONO = '" + LoginCono.trim() + "' AND SETT_DIVI = '" + LoginDivision.trim() + "'";
                DeleteDetail = conn.prepareStatement(StrDeleteDetail);
                DeleteDetail.executeUpdate();
                SaveCashRequest4Detail(models, CASH_CANO);

            }

            return CASH_CANO;

        } catch (Exception e) {
            return "";
        }

    }

    private void SaveCashRequest4Detail(DefaultTableModel model,
            String CASH_CANO) {

        ClassCheckDataProgram cdp = new ClassCheckDataProgram();

        double TotalAmountReturn = 0;
        try {

            PreparedStatement InsertDetail = null;
            Connection conn = ConnectionDB();
            String StrInsertDetail;
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();

            for (int i = 0; i < model.getRowCount(); ++i) {
                if ((String) model.getValueAt(i, 0) != "" && (String) model.getValueAt(i, 1) != null) {

                    String SETT_CANO = CASH_CANO;
                    String SETT_INVC = (String) model.getValueAt(i, 0);
                    String SETT_SUPP = cdp.get_SemiColonValue0((String) model.getValueAt(i, 1));
                    String InvoiceDate = (String) model.getValueAt(i, 3);
                    InvoiceDate = InvoiceDate.replace("-", "");
                    String SETT_INVD = InvoiceDate;
                    String SETT_DESC = (String) model.getValueAt(i, 4);
                    String SETT_COST = (String) model.getValueAt(i, 5);

                    BigDecimal SETT_AMTB = new BigDecimal(0);
                    try {
                        SETT_AMTB = BigDecimal.valueOf((Double) model.getValueAt(i, 6));
                    } catch (Exception e) {
                        SETT_AMTB = new BigDecimal(0) ;
                    }
                    BigDecimal SETT_VATC = new BigDecimal(0);
                    try {
                        SETT_VATC = BigDecimal.valueOf((Double)model.getValueAt(i, 7));
                    } catch (Exception e) {
                        SETT_VATC = new BigDecimal(0) ;
                    }
                    BigDecimal SETT_VATA = new BigDecimal(0) ;
                    try {
                        SETT_VATA =BigDecimal.valueOf((Double)model.getValueAt(i, 8));
//                                (BigDecimal) model.getValueAt(i, 8);
                    } catch (Exception e) {
                        SETT_VATA = new BigDecimal(0) ;
                    }
                    String SETT_NODES = (String) model.getValueAt(i, 9);

                    BigDecimal SETT_AMTT = new BigDecimal(0) ;
                    try {
                        SETT_AMTT = BigDecimal.valueOf((Double)model.getValueAt(i, 10));
//                                (BigDecimal) model.getValueAt(i, 10);
                    } catch (Exception e) {
                        SETT_AMTT = new BigDecimal(0) ;
                    }
                    String SETT_BRAC = (String) model.getValueAt(i, 11);
                    String SETT_STAT = "10";

                    StrInsertDetail = "INSERT INTO " + Table_fin_sead + "  "
                            + "(SETT_CONO, SETT_DIVI, SETT_NODES, SETT_BRAC,"
                            + " SETT_CANO, SETT_INVC, SETT_SUPP  "
                            + ", SETT_INVD, SETT_DESC, SETT_COST "
                            + " , SETT_AMTB, SETT_VATC, SETT_VATA "
                            + ", SETT_AMTT, SETT_STAT,SETT_RETT,SETT_DUEDT,SETT_CDVAT )  ";

                    StrInsertDetail += "VALUES ('" + LoginCono.trim() + "','" + LoginDivision.trim() + "','" + SETT_NODES.trim() + "','" + SETT_BRAC.trim() + "',"
                            + "'" + SETT_CANO.trim().replace("'", "") + "','" + SETT_INVC + "','" + SETT_SUPP + " ', "
                            + "'" + SETT_INVD + "','" + SETT_DESC.trim().replace("'", "") + "','" + SETT_COST + " ', "
                            + "'" + SETT_AMTB + "','" + SETT_VATC + "','" + SETT_VATA + " ', "
                            + "'" + SETT_AMTT + "','" + SETT_STAT + "','-','0','40')";

                    InsertDetail = conn.prepareStatement(StrInsertDetail);
                    InsertDetail.executeUpdate(StrInsertDetail);
                    System.out.println(StrInsertDetail);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

//    
//     
    public void DeleLine(String form) {
        String INV = null;
        int selectedRowIndex = 0;
        DefaultTableModel model2;
        if (form.equals("frmAdvance")) {
            model2 = (DefaultTableModel) CashAdvanRequest.griddetail.getModel();
            // get the selected row index
            selectedRowIndex = CashAdvanRequest.griddetail.getSelectedRow();
            // set the selected row data into jtextfields
            INV = (model2.getValueAt(selectedRowIndex, 0).toString());
        } else {
            model2 = (DefaultTableModel) frmWHTAX.griddetail.getModel();
            // get the selected row index
            selectedRowIndex = frmWHTAX.griddetail.getSelectedRow();
            // set the selected row data into jtextfields
            INV = (model2.getValueAt(selectedRowIndex, 0).toString());
        }
        try {

            PreparedStatement InsertDetail = null;
            Connection conn = ConnectionDB();

            PreparedStatement DeleteDetail = null;
            String StrDeleteDetail = "DELETE FROM " + Table_fin_sead
                    + " WHERE SETT_CANO='" + CashAdvanRequest.txtAdvanceNo.getText().trim() + "' AND  SETT_INVC='" + INV + "' "
                    + " AND SETT_CONO = '" + LoginCono.trim() + "' AND SETT_DIVI = '" + LoginDivision.trim() + "'";
            DeleteDetail = conn.prepareStatement(StrDeleteDetail);
            System.out.println("StrDeleteDetail\n" + StrDeleteDetail);

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want delete inv no yes or no ?", "Delete", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                DeleteDetail.executeUpdate();
                model2.removeRow(selectedRowIndex);

            } else {
                // System.exit(0);
                System.out.print("");
            }

        } catch (Exception e) {
        }

    }

    public void InsertLine(String SETT_CONO, String SETT_DIVI, String SETT_NODES, String BranchAcc, String SETT_CANO, String SETT_INVC, String SETT_SUPP,
            String SETT_INVD, String SETT_DESC, String SETT_COST,
            String SETT_AMTB, String SETT_VATC, String SETT_VATA,
            String SETT_AMTT, String SETT_STAT, Boolean importduty) {

        try {

            PreparedStatement InsertDetail = null;
            Connection conn = ConnectionDB();

            String StrInsertDetail = "INSERT INTO " + Table_fin_sead + "  "
                    + "(SETT_CONO, SETT_DIVI,SETT_NODES, SETT_BRAC,"
                    + " SETT_CANO, SETT_INVC, SETT_SUPP  "
                    + ", SETT_INVD, SETT_DESC, SETT_COST "
                    + " , SETT_AMTB, SETT_VATC, SETT_VATA "
                    + ", SETT_AMTT, SETT_STAT, SETT_RETT )  ";
            if (!importduty) {
                StrInsertDetail += "VALUES ( '" + SETT_CONO.trim() + "','" + SETT_DIVI.trim() + "',(    SELECT CASH_EXPG\n"
                        + "    FROM " + Table_fin_caadd + "\n"
                        + "    WHERE CASH_CONO = '" + SETT_CONO.trim() + "'\n"
                        + "    AND CASH_DIVI = '" + SETT_DIVI.trim() + "'\n"
                        + "    AND CASH_CANO  = '" + SETT_CANO.trim().replace("'", "") + "'),'" + BranchAcc + "',\n"
                        + "'" + SETT_CANO.trim().replace("'", "") + "','" + SETT_INVC + "','" + SETT_SUPP + " ', "
                        + "'" + SETT_INVD + "','" + SETT_DESC.trim().replace("'", "") + "','" + SETT_COST + " ', "
                        + "'" + SETT_AMTB + "','" + SETT_VATC + "','" + SETT_VATA + " ', "
                        + "'" + SETT_AMTT + "','" + SETT_STAT + "' , '-')";
            } else {
                StrInsertDetail += "VALUES ( '" + SETT_CONO.trim() + "','" + SETT_DIVI.trim() + "',(    SELECT CASH_EXPG\n"
                        + "    FROM " + Table_fin_caadd + "\n"
                        + "    WHERE CASH_CONO = '" + SETT_CONO.trim() + "'\n"
                        + "    AND CASH_DIVI = '" + SETT_DIVI.trim() + "'\n"
                        + "    AND CASH_CANO  = '" + SETT_CANO.trim().replace("'", "") + "'),'" + BranchAcc + "',\n"
                        + "'" + SETT_CANO.trim().replace("'", "") + "','" + SETT_INVC + "','" + SETT_SUPP + " ', "
                        + "'" + SETT_INVD + "','" + SETT_DESC.trim().replace("'", "") + "','" + SETT_COST + " ', "
                        + "'" + SETT_AMTB + "','" + "0" + "','" + SETT_VATC + " ', "
                        + "'" + SETT_AMTT + "','" + SETT_STAT + "' , '-')";
            }
            System.out.println(StrInsertDetail);
            InsertDetail = conn.prepareStatement(StrInsertDetail);
            InsertDetail.executeUpdate(StrInsertDetail);
        } catch (Exception e) {
            System.out.println(e.toString());

        }

    }

    public void InsertLineEXtra(String SETT_CANO, String SETT_INVC, String SETT_SUPP,
            String SETT_INVD, String SETT_DESC, String SETT_COST,
            String SETT_AMTB, String SETT_VATA,
            String SETT_AMTT, String SETT_STAT) {

        try {

            PreparedStatement InsertDetail = null;
            Connection conn = ConnectionDB();

            String StrInsertDetail = "INSERT INTO " + Table_fin_sead + "  "
                    + "(SETT_CANO, SETT_INVC, SETT_SUPP  "
                    + ", SETT_INVD, SETT_DESC, SETT_COST "
                    + " , SETT_AMTB, SETT_VATC, SETT_VATA "
                    + ", SETT_AMTT, SETT_STAT )  ";

            StrInsertDetail += "VALUES ('" + SETT_CANO.trim().replace("'", "") + "','" + SETT_INVC + "','" + SETT_SUPP + " ', "
                    + "'" + SETT_INVD + "','" + SETT_DESC.trim().replace("'", "") + "','" + SETT_COST + " ', "
                    + "'" + SETT_AMTB + "','0','" + SETT_VATA + " ', "
                    + "'" + SETT_AMTT + "','" + SETT_STAT + "')";
            InsertDetail = conn.prepareStatement(StrInsertDetail);
            InsertDetail.executeUpdate(StrInsertDetail);

        } catch (Exception e) {
        }

    }

    public String SaveCashAdvanceWHTAX(String CASH_CANO, DefaultTableModel model, String CASH_WHTYPE, String CASH_REAMT, String CASH_RETOBANK) {

        try {
            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();

            PreparedStatement DeleteDetail = null;
            String StrDeleteDetail = "DELETE FROM " + Table_fin_sead
                    + " WHERE SETT_CANO=" + CASH_CANO
                    + " AND SETT_CONO = '" + LoginCono.trim() + "' AND SETT_DIVI = '" + LoginDivision.trim() + "'";
            DeleteDetail = conn.prepareStatement(StrDeleteDetail);
            DeleteDetail.executeUpdate();
            System.out.println(StrDeleteDetail);
            String StrInsertHeader = "";

            StrInsertHeader = "UPDATE " + Table_fin_caadd;
            StrInsertHeader += " SET CASH_WHTYPE= '" + CASH_WHTYPE + "' , CASH_REAMT = '" + CASH_REAMT.trim() + "',CASH_RETOBANK= '" + CASH_RETOBANK.trim() + "' ";
            StrInsertHeader += " WHERE CASH_CANO ='" + CASH_CANO + "' ";
            StrInsertHeader += " AND CASH_CONO = '" + LoginCono.trim() + "' ";
            StrInsertHeader += " AND CASH_DIVI = '" + LoginDivision.trim() + "' ";

            InsertHeader = conn.prepareStatement(StrInsertHeader);
            InsertHeader.executeUpdate(StrInsertHeader);

            SaveCashRequest4Detail(model, CASH_CANO);

            return CASH_CANO;

        } catch (Exception e) {
            return "";
        }

    }

    public void SaveCashAdvanceInvoice(String CASH_CANO, String NODES, String INVC, String shareservice, String Method, String SETT_VCTXT, String SETT_DUEDT, String SETT_CDVAT) {
        try {

            PreparedStatement UpdateLine = null;
            Connection conn = ConnectionDB();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();

            String StrUpdateLine = "";
            if (Method.trim().equalsIgnoreCase("Please select")) {
                Method = "-";
            }
            StrUpdateLine = "UPDATE " + Table_fin_sead
                    + " SET SETT_BRAC='" + shareservice.trim() + "', SETT_RETT = '" + Method.trim() + "' ,SETT_VCTXT = '" + SETT_VCTXT.trim() + "' , SETT_DUEDT = '" + SETT_DUEDT.trim() + "'\n"
                    + " ,SETT_CDVAT = '" + SETT_CDVAT.trim() + "'\n"
                    + "where SETT_CONO = '" + LoginCono.trim() + "'\n"
                    + "and SETT_DIVI = '" + LoginDivision.trim() + "'\n"
                    + "and SETT_CANO = '" + CASH_CANO.trim() + "'\n"
                    + "and SETT_NODES = '" + NODES.trim() + "'\n"
                    + "and SETT_INVC = '" + INVC.trim() + "'\n";

            System.out.println(StrUpdateLine);
            UpdateLine = conn.prepareStatement(StrUpdateLine);
            UpdateLine.executeUpdate(StrUpdateLine);

        } catch (Exception e) {

        }
    }

    public String SaveCashAdvanceWHTAX_AMT(String CASH_CANO, DefaultTableModel model, String CASH_WHTYPE, String CASH_REAMT, String CASH_RETOBANK) {

        try {
            PreparedStatement InsertHeader = null;
            Connection conn = ConnectionDB();

            String StrInsertHeader = "";

            StrInsertHeader = "UPDATE " + Table_fin_caadd;
            StrInsertHeader += " SET CASH_WHTYPE= '" + CASH_WHTYPE + "' ";
            StrInsertHeader += " WHERE CASH_CANO ='" + CASH_CANO + "' ";
            StrInsertHeader += " AND CASH_CONO = '" + LoginCono.trim() + "' ";
            StrInsertHeader += " AND CASH_DIVI = '" + LoginDivision.trim() + "' ";

            InsertHeader = conn.prepareStatement(StrInsertHeader);
            InsertHeader.executeUpdate(StrInsertHeader);

            SaveCashRequest4Detail(model, CASH_CANO);

            return CASH_CANO;

        } catch (Exception e) {
            return "";
        }

    }

}
