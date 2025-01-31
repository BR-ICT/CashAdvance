/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashadvance_multicompany;

import static cashadvance_multicompany.ConnectDB2.ConnectionDB;
import static cashadvance_multicompany.frmLogin.LoginCono;
import static cashadvance_multicompany.frmLogin.LoginDivision;
import static cashadvance_multicompany.frmLogin.Table_fin_caadd;
import static cashadvance_multicompany.frmLogin.Table_fin_cact;
import static cashadvance_multicompany.frmLogin.Table_fin_sead;
import static cashadvance_multicompany.frmLogin.dbname;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
//import org.apache.poi.hssf.record.formula.TblPtg;

/**
 *
 * @author Jilasak
 */
public class Classgetdata {

    private String EPRC_NO;
    private String EPRC_PO;
    private String EPRC_GN;
    private String CASH_NOREQ;
    public static ResultSet rsSupplier;
    public static DefaultComboBoxModel listSupplier;
    public static DefaultComboBoxModel listCostcenter;
    public static DefaultComboBoxModel listItems;

    public static List<String> DataListSupplier;
    public static List<String> DataListCostcenter;
    public static List<String> DataListItems;

    public ResultSet GetSqlDatachequenumber(String CASH_NO) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT CKBKID,RIGHT(CKCHKN,7) CKCHKN,EGACDT AS CKCADA\n"
                    + "FROM   M3FDBPRD.FGLEDG,M3FDBPRD.FAPCHK\n"
                    + "WHERE EGCONO = '" + LoginCono + "' \n"
                    + "   AND EGDIVI = '" + LoginDivision + "' \n"
                    + "   AND EGAIT1 = '8AA1301'\n"
                    + "   AND EGTRCD = 51\n"
                    + "   AND EGAIT5 = '" + CASH_NO + "'\n"
                    + "   AND EGCONO = CKCONO\n"
                    + "   AND EGDIVI = CKDIVI\n"
                    + "   AND EGVONO = CKVONO\n"
                    + "   AND EGYEA4 = CKYEA4 ";

            ResultSet rs1 = sta.executeQuery(Sql1);
            System.out.print(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(CashAdvanAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String Get_customerNameWithCode2(String SupplierCode) {

        String Customer2 = "";

        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT iioucn,okcunm\n"
                    + "  from M3FDBPRD.cidven,M3FDBPRD.ocusma\n"
                    + "  WHERE iioucn = okcuno\n"
                    + "  AND iisuno = '" + SupplierCode + "'\n"
                    + "  AND iicono = '" + LoginCono.trim() + "'\n"
                    + "  AND iicono = okcono";
            ResultSet rs1 = sta.executeQuery(Sql1);
//            System.err.println(Sql1);
            while (rs1.next()) {
                Customer2 = (rs1.getString("iioucn").trim() + " : " + rs1.getString("okcunm").trim());
            }
            return Customer2;

        } catch (Exception ex) {

        }

        return null;

    }

    public ResultSet Get_CashAccountingUserListUserResultData(String UserRequest) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT CASH_CANO,CASH_PPS1,CASH_REQB,CASH_STAT  ";
            Sql1 += " FROM " + Table_fin_caadd;
            Sql1 += " WHERE CASH_STAT IN('20','30','45','50')";
            Sql1 += " AND CASH_CONO = '" + LoginCono.trim() + "'\n";
            Sql1 += " AND CASH_DIVI = '" + LoginDivision.trim() + "'\n";
            Sql1 += " ORDER BY CASH_CANO";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceAcc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet Check_BranchAC(String I3DS_NODES) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT I3DS_CONO,I3DS_DIVI,I3DS_NOSG,I3DS_NODES,I3DS_DESC,I3DS_I3AIT1,I3DS_I3AIT2\n"
                    + "       FROM " + dbname + ".CASH_IT3DES\n"
                    + "       WHERE I3DS_CONO = '" + LoginCono.trim() + "'\n"
                    + "       AND I3DS_DIVI = '" + LoginDivision.trim() + "'\n"
                    + "       AND I3DS_NODES = '" + I3DS_NODES.trim() + "'";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceAcc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String Get_CostcenterNameWithCode(String Costcenter) //ITEM NAME WITH ITEM CODE
    {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select eaaitm , eatx40    ";
            Sql1 += " FROM  m3fdbprd.fchacc ";
            Sql1 += " WHERE eaaitp =  2 and eacono='" + LoginCono + "'  and   eadivi = '" + LoginDivision + "' AND  eaaitm='" + Costcenter + "' ";
            Sql1 += " Order by eaaitm";
            ResultSet rs1 = sta.executeQuery(Sql1);
            String CostcenterName = "";
            while (rs1.next()) {
                CostcenterName = (rs1.getString("eaaitm").trim() + " : " + rs1.getString("eatx40").trim());
            }
            return CostcenterName;

        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceRequester2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetCostCenterResultwithsearchData(String searchlike) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String UpperSearch = searchlike.toUpperCase();
            String Sql1 = " select eaaitm , eatx40    \n"
                    + " FROM  m3fdbprd.fchacc \n"
                    + " WHERE eaaitp =  2 and eacono='" + LoginCono.trim() + "'  and   eadivi = '" + LoginDivision.trim() + "' \n"
                    + " AND  (UPPER(eaaitm) Like '%" + UpperSearch.trim() + "%' OR  UPPER(eatx40)  Like '%" + UpperSearch.trim() + "%' ) \n"
                    + " Order by eaaitm";
            ResultSet Costcenter = sta.executeQuery(Sql1);

            return Costcenter;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetSupplierResultwithsearchData_INVC(String searchlike) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String UpperSearch = searchlike.toUpperCase();
            String Sql1 = "select idsuno, idsunm   ";
            Sql1 += " FROM M3FDBPRD.CIDMAS ";
            Sql1 += " where  idstat = '20' AND idcono='" + LoginCono + "' "
                    + " and (UPPER(idsuno) Like '%" + UpperSearch + "%' OR  UPPER(idsunm)  Like '%" + UpperSearch + "%' ) ";
            Sql1 += " Order by idsuno";
            rsSupplier = sta.executeQuery(Sql1);

            return rsSupplier;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetSupplierResultwithsearchData(String searchlike) {
        try {
            ResultSet rsl = null;
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String UpperSearch = searchlike.toUpperCase();
            String Sql1 = " SELECT IRSUNO,IRYRE1,IRPHNO,IRTFNO,IRSUCM,IDSUNM,IDSUNO,IIOUCN\n"
                    + " FROM M3FDBPRD.CIDREF,M3FDBPRD.CIDMAS,M3FDBPRD.cidven\n"
                    + " WHERE IRCONO = '" + LoginCono.trim() + "'\n"
                    + " AND IRCONO = IDCONO\n"
                    + " AND IRRFID = 'BANK'\n"
                    + " AND IDSTAT = '20'\n"
                    + " AND IRSUNO = IDSUNO\n"
                    + " AND IISUNO = IRSUNO\n"
                    + " AND IISUNO = IDSUNO\n"
                    + " AND IICONO = IDCONO\n"
                    + " AND IICONO = IRCONO\n"
                    + " AND IRSUCM NOT IN ('','-')\n"
                    + " and (UPPER(IDSUNO) Like '%" + UpperSearch + "%' OR UPPER(IDSUNM) Like '%" + UpperSearch + "%') \n"
                    + " ORDER BY IDSUNO";
            boolean count = checkRsl(Sql1);
            if (count == true) {
                rsl = sta.executeQuery(Sql1);
                return rsl;
            } else {
                Sql1 = "select IDSUNO, IDSUNM   \n"
                        + " FROM M3FDBPRD.CIDMAS\n"
                        + " where  idstat = '20' AND idcono='" + LoginCono.trim() + "'\n"
                        + " and (UPPER(IDSUNO) Like '%" + UpperSearch + "%' OR UPPER(IDSUNM) Like '%" + UpperSearch + "%') \n"
                        + " Order by idsuno \n"
                        + "LIMIT 0,100 ";
                rsl = sta.executeQuery(Sql1);
                return rsl;
            }
//            if (!rsl.next()) {
//
//                Sql1 = "select IDSUNO, IDSUNM   \n"
//                        + " FROM M3FDBPRD.CIDMAS\n"
//                        + " where  idstat = '20' AND idcono='" + LoginCono.trim() + "'\n"
//                        + " and (UPPER(IDSUNO) Like '%" + UpperSearch + "%' OR UPPER(IDSUNM) Like '%" + UpperSearch + "%') \n"
//                        + " Order by idsuno \n"
//                        + "LIMIT 0,100 ";
////                System.err.println(Sql1);
//                rsl = sta.executeQuery(Sql1);
//            }
//            return rsl;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean checkRsl(String sql) throws Exception {
        Boolean chk = false;
        try {
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            ResultSet rsl = sta.executeQuery(sql);
            if (rsl.next()) {
                chk = true;
            }
            return chk;
        } catch (SQLException ex) {
            Logger.getLogger(Classgetdata.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chk;
    }

    public String Get_CASHREQ_CASHNO() {
        try {
            String EPNU_NO = "";

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select CASH_NO   ";
            Sql1 += " FROM " + Table_fin_cact + " ";
            Sql1 += " where cash_cono = '" + LoginCono.trim() + "'";
            Sql1 += " and cash_divi = '" + LoginDivision.trim() + "'";

            ResultSet rs1 = sta.executeQuery(Sql1);
            while (rs1.next()) {
                CASH_NOREQ = rs1.getString("CASH_NO");
            }

            return CASH_NOREQ;
        } catch (Exception ex) {

            return "";
        }

    }

//    public ResultSet GetSupplierResultDatashow() {
//        try {
//
//            Connection conn = ConnectionDB();
//            Statement sta = conn.createStatement();
//            String Sql1 = "select idsuno, idsunm   ";
//            Sql1 += " FROM M3FDBPRD.CIDMAS ";
//            Sql1 += " where  idstat = '20' AND idcono='" + LoginCono + "'   ";
//            Sql1 += " Order by idsuno ";
//            Sql1 += "LIMIT 0,100 ";
//            rsSupplier = sta.executeQuery(Sql1);
//
//            return rsSupplier;
//
//        } catch (Exception ex) {
//            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    public ResultSet GetSupplierResultDatashow() {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT IRSUNO,IRYRE1,IRPHNO,IRTFNO,IRSUCM,IDSUNM,IDSUNO,IIOUCN\n"
                    + " FROM M3FDBPRD.CIDREF,M3FDBPRD.CIDMAS,M3FDBPRD.cidven\n"
                    + " WHERE IRCONO = '" + LoginCono.trim() + "'\n"
                    + " AND IRCONO = IDCONO\n"
                    + " AND IRRFID = 'BANK'\n"
                    + " AND IDSTAT = '20'\n"
                    + " AND IRSUNO = IDSUNO\n"
                    + " AND IISUNO = IRSUNO\n"
                    + " AND IISUNO = IDSUNO\n"
                    + " AND IICONO = IDCONO\n"
                    + " AND IICONO = IRCONO\n"
                    + " AND IIOUCN != ''\n"
                    + " AND IRSUCM NOT IN ('','-')\n"
                    //                    + " and (UPPER(IDSUNO) Like '%WEET%' OR UPPER(IDSUNM) Like '%WEET%') \n"
                    + " ORDER BY IDSUNO\n"
                    + " LIMIT 0,50 ";
            rsSupplier = sta.executeQuery(Sql1);

            return rsSupplier;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet Get_CashRequestUserListUserResultData(String UserRequest) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT CASH_CANO,CASH_PPS1,CASH_REQB,CASH_STAT  ";
            Sql1 += " FROM " + Table_fin_caadd;
            Sql1 += " WHERE upper(CASH_REQB) like upper('%" + UserRequest + "%') AND CASH_STAT IN('10','40')\n";
            Sql1 += " AND CASH_CONO = '" + LoginCono.trim() + "' AND CASH_DIVI = '" + LoginDivision.trim() + "'\n";

            ResultSet rs1 = sta.executeQuery(Sql1);
//            System.out.println(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceRequester2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetCostcenterResultData() {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select eaaitm,eatx40   ";
            Sql1 += " FROM m3fdbprd.fchacc ";
            Sql1 += " WHERE eaaitp =  2  and   eadivi = '" + LoginDivision + "'  ";
            Sql1 += " Order by eaaitm";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceRequester2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetExpensHeadGroup() {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT trim(I1GP_NOGP) as I1GP_NOGP,TRIM(I1GP_DESGP) as I1GP_DESGP\n"
                    + " FROM " + dbname + ".CASH_IT1GP\n"
                    + " WHERE I1GP_CONO = '" + LoginCono + "'\n"
                    + " AND I1GP_DIVI = '" + LoginDivision + "'\n";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceRequester2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetExpensHeadGroupWithNo(String NoGroup) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT trim(I1GP_NOGP) as I1GP_NOGP,TRIM(I1GP_DESGP) as I1GP_DESGP\n"
                    + " FROM " + dbname + ".CASH_IT1GP\n"
                    + " WHERE I1GP_CONO = '" + LoginCono + "'\n"
                    + " AND I1GP_DIVI = '" + LoginDivision + "'\n"
                    + " AND I1GP_NOGP = '" + NoGroup.trim() + "'";
            ResultSet rs1 = sta.executeQuery(Sql1);
//            System.out.println(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceRequester2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetExpensSubGroup(String NoSub) {
        try {
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT trim(I2SG_NOSG) as I2SG_NOSG,TRIM(I2SG_DESSG) as I2SG_DESSG,I2SG_DATE\n"
                    + "FROM " + dbname + ".CASH_IT2SG\n"
                    + "WHERE I2SG_CONO = '" + LoginCono + "'\n"
                    + "AND I2SG_DIVI = '" + LoginDivision + "'\n"
                    + "AND I2SG_NOGP = '" + NoSub.trim() + "'\n"
                    + "ORDER BY I2SG_NOGP,CAST(SUBSTRING(I2SG_NOSG,3,4) AS INTEGER)";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceRequester2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetExpensSubGroupAccount(String NoSub) {
        try {
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT trim(I2SG_NOSG) as I2SG_NOSG,TRIM(I2SG_DESSG) as I2SG_DESSG\n"
                    + "            FROM " + dbname + ".CASH_IT2SG\n"
                    + "            WHERE I2SG_CONO = '" + LoginCono + "'\n"
                    + "            AND I2SG_DIVI = '" + LoginDivision + "'\n"
                    + " AND I2SG_NOSG = '" + NoSub.trim() + "'\n"
                    + "ORDER BY I2SG_NOGP,CAST(SUBSTRING(I2SG_NOSG,3,4) AS INTEGER)";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceRequester2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetExpensSubGroupDesc(String NoSub) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT trim(I3DS_NODES) as I3DS_NODES,TRIM(I3DS_DESC) as I3DS_DESC\n"
                    + "            FROM " + dbname + ".CASH_IT3DES\n"
                    + "            WHERE I3DS_CONO = '" + LoginCono + "'\n"
                    + "            AND I3DS_DIVI = '" + LoginDivision + "'\n"
                    + " AND I3DS_NOSG = '" + NoSub.trim() + "'";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceRequester2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetExpensSubGroupDescAccount(String NoSub) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT trim(I3DS_NODES) as I3DS_NODES,TRIM(I3DS_DESC) as I3DS_DESC,trim(I3DS_NODES) || ' : ' || TRIM(I3DS_DESC) as DESCSG\n"
                    + "            FROM " + dbname + ".CASH_IT3DES\n"
                    + "            WHERE I3DS_CONO = '" + LoginCono + "'\n"
                    + "            AND I3DS_DIVI = '" + LoginDivision + "'\n"
                    + " AND I3DS_NODES = '" + NoSub.trim() + "'";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {

        }
        return null;
    }

    public String GetExpensSubGroupDescWithCode(String NoSub) {
        try {
            String SubGroup = "";
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT I2SG_CONO, I2SG_DIVI, I2SG_NOGP, I2SG_NOSG,trim(I2SG_NOSG) || ' : ' || TRIM(I2SG_DESSG) as DESCSG\n"
                    + "FROM " + dbname + ".CASH_IT2SG \n"
                    + "WHERE I2SG_CONO = '" + LoginCono.trim() + "'\n"
                    + "AND I2SG_DIVI = '" + LoginDivision.trim() + "'\n"
                    + "AND I2SG_NOSG  = '" + NoSub.trim() + "'\n"
                    + "ORDER BY I2SG_NOGP,CAST(SUBSTRING(I2SG_NOSG,3,4) AS INTEGER)";
            ResultSet rs1 = sta.executeQuery(Sql1);
            while (rs1.next()) {
                SubGroup = rs1.getString("DESCSG").trim();
            };
            return SubGroup;
        } catch (Exception ex) {

        }
        return null;
    }

    public String GetExpensHeadGroupWithCode(String CODE) {
        try {
            String Group = "";
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT trim(I1GP_NOGP) as I1GP_NOGP,TRIM(I1GP_DESGP) as I1GP_DESGP,trim(I1GP_NOGP) || ' : ' || TRIM(I1GP_DESGP) as DESCG\n"
                    + "            FROM " + dbname + ".CASH_IT1GP\n"
                    + "            WHERE I1GP_CONO = '" + LoginCono + "'\n"
                    + "            AND I1GP_DIVI = '" + LoginDivision + "'\n"
                    + "AND I1GP_NOGP = '" + CODE.trim() + "'";
            ResultSet rs1 = sta.executeQuery(Sql1);
            while (rs1.next()) {
                Group = rs1.getString("DESCG").trim();
            };
            return Group;
        } catch (Exception ex) {
            Logger.getLogger(ShowCashAdvanceRequester2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetDataCashAdvanRequest_HeaderResultData(String CASH_CANO_) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select *   ";
            Sql1 += " FROM  " + Table_fin_caadd;
            Sql1 += " where CASH_CANO='" + CASH_CANO_ + "'";
            Sql1 += " AND CASH_CONO = '" + LoginCono.trim() + "' AND CASH_DIVI = '" + LoginDivision.trim() + "'\n";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetDataCashAdvanRequest_BankSupp(String SuppCode) {
        try {
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT IRSUNO,IRYRE1,IRPHNO,IRTFNO,IRSUCM,IDSUNM\n"
                    + "FROM M3FDBPRD.CIDREF,M3FDBPRD.CIDMAS\n"
                    + "WHERE IRCONO = '" + LoginCono.trim() + "'\n"
                    + "AND IRRFID = 'BANK'\n"
                    + "AND IRSUCM NOT IN ('','-')\n"
                    + "AND IRSUNO = '" + SuppCode.trim() + "'\n"
                    + "AND ircono  = idcono\n"
                    + "AND IRSUNO = idsuno\n"
                    + "AND IDSTAT = '20'";
            System.out.println(Sql1);
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String GetDataCashAdvanStatus_WithCashNo(String CASH_CANO_) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select CASH_CANO,CASH_STAT   ";
            Sql1 += " FROM " + Table_fin_caadd;
            Sql1 += " where CASH_CANO='" + CASH_CANO_ + "'";
            Sql1 += " AND CASH_CONO = '" + LoginCono.trim() + "' AND CASH_DIVI = '" + LoginDivision.trim() + "'\n";
            String Cash_Stat = "";
            ResultSet rs1 = sta.executeQuery(Sql1);
            while (rs1.next()) {
                Cash_Stat = rs1.getString("CASH_STAT");

            }
            return Cash_Stat;
        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

    }

    public String Get_SupplierNameWithCode(String SupplierCode) //ITEM NAME WITH ITEM CODE
    {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select idsuno,idsunm  ";
            Sql1 += " FROM M3FDBPRD.CIDMAS ";
            Sql1 += " where  idstat = '20' AND idcono='" + LoginCono + "' AND idsuno='" + SupplierCode + "'  ";
            Sql1 += " Order by idsuno";
            ResultSet rs1 = sta.executeQuery(Sql1);
            String SupplierName = "";
            while (rs1.next()) {
                SupplierName = (rs1.getString("idsuno").trim() + " : " + rs1.getString("idsunm").trim());
            }
            return SupplierName;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String Get_SupplierNameWithCodeAccount(String SupplierCode) //ITEM NAME WITH ITEM CODE
    {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select idsunm  ";
            Sql1 += " FROM M3FDBPRD.CIDMAS ";
            Sql1 += " where  idstat = '20' AND idcono='" + LoginCono + "' AND idsuno='" + SupplierCode + "'  ";
            Sql1 += " Order by idsuno";
            ResultSet rs1 = sta.executeQuery(Sql1);
            String SupplierName = "";
            while (rs1.next()) {
                SupplierName = (rs1.getString("idsunm").trim());
            }
            return SupplierName;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetDataAdvanceDetailResultData(String CASH_NO) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select *   ";
            Sql1 += " FROM " + Table_fin_sead;
            Sql1 += " where SETT_CANO='" + CASH_NO + "'   ";
            Sql1 += " AND SETT_CONO ='" + LoginCono.trim() + "'  AND SETT_DIVI = '" + LoginDivision.trim() + "' ";
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet GetAdvanceDetailM3(String CASH_CANO) {
        try {
//
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = " SELECT A.CASH_CANO,A.SETT_INVC,A.SETT_SUPP,A.SETT_INVD,A.SETT_CDVAT,COALESCE(A.SETT_VCTXT,'') AS SETT_VCTXT,COALESCE(A.SETT_DUEDT,'0') AS SETT_DUEDT,A.ACCODE,A.SETT_COST,A.SETT_AMTB,A.SETT_VATC,A.SETT_VATA,A.SETT_AMTT,ROUND(A.SETT_VATA * B.C_VTCHRG,2) AS VAT_UC,A.SETT_VATA - ROUND(A.SETT_VATA * B.C_VTCHRG,2) AS VAT_C\n"
                    + "       ,B.EATX40,CASE WHEN B.EATX40 = 'UCP' THEN '40' WHEN B.EATX40 = 'UCN' THEN '45' ELSE '07' END AS VATCODE,A.SETT_RETT,A.CASH_AMT,A.CASH_COST\n"
                    + "       FROM (\n"
                    + "       SELECT CASH_CONO,CASH_DIVI,CASH_CANO,CASH_DATE,CASH_COST,CASH_EMPY,CASH_WHTAX,CASH_AMT,\n"
                    + "       SETT_CDVAT,SETT_NODES,CASE WHEN SETT_BRAC = '1' THEN I3DS_I3AIT1 ELSE I3DS_I3AIT2 END AS ACCODE,SETT_INVC,SETT_INVD,SETT_SUPP,SETT_COST,SETT_AMTB,SETT_VATC,SETT_VATA,SETT_AMTT,SETT_DUEDT,SETT_VCTXT,SETT_RETT\n"
                    + "       FROM " + dbname + ".FIN_CASHADALL," + dbname + ".FIN_SETTADALL," + dbname + ".CASH_IT3DES\n"
                    + "       WHERE CASH_CONO = '" + LoginCono.trim() + "'\n"
                    + "       AND CASH_DIVI = '" + LoginDivision.trim() + "'\n"
                    + "       AND CASH_CANO = '" + CASH_CANO.trim() + "'\n"
                    + "       AND CASH_CANO = SETT_CANO\n"
                    + "       AND CASH_CONO = SETT_CONO\n"
                    + "       AND CASH_DIVI = SETT_DIVI\n"
                    + "       AND I3DS_CONO = SETT_CONO\n"
                    + "       AND I3DS_DIVI = SETT_DIVI\n"
                    + "       AND SETT_NODES = I3DS_NODES\n"
                    + "       ) A LEFT JOIN (\n"
                    + "       SELECT EACONO,EADIVI,EAAITM,C_VTCHRG,SUBSTR(EATX40,38,3) AS EATX40\n"
                    + "       FROM M3FDBPRD.FCHACC ," + dbname + ".M3_CLAIMEPR \n"
                    + "       WHERE EACONO = '" + LoginCono.trim() + "'  \n"
                    + "       AND EADIVI = '" + LoginDivision.trim() + "' \n"
                    + "       AND EAAITP = 2  \n"
                    + "       AND EACONO = C_IBCONO\n"
                    + "       AND EADIVI = C_IBDIVI\n"
                    + "       AND TRIM(SUBSTR(EATX40,38,3)) = C_VTCLM\n"
                    + "       ) B ON B.EACONO = A.CASH_CONO AND B.EADIVI = A.CASH_DIVI AND B.EAAITM = A.SETT_COST";
//            System.out.println(Sql1);
            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Double GetAmountInvM3(String cash_cano) {
        Double Amount = 0.00;
        try {
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT SETT_CONO,SETT_DIVI,SETT_CANO,SUM(SETT_AMTT) AS amtinv\n"
                    + " FROM " + dbname + ".FIN_SETTADALL\n"
                    + " WHERE SETT_CONO = '" + LoginCono.trim() + "'\n"
                    + " AND SETT_DIVI = '" + LoginDivision.trim() + "'\n"
                    + " AND SETT_CANO = '" + cash_cano.trim() + "'\n"
                    + " GROUP BY SETT_CONO,SETT_DIVI,SETT_CANO";
            ResultSet rs1 = sta.executeQuery(Sql1);
            while (rs1.next()) {
                Amount = rs1.getDouble("amtinv");
            }
            return Amount;
        } catch (Exception ex) {
            Logger.getLogger(Classgetdata.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Amount;
    }

    public int GetCostcenterWithCodeData(String Costcenter) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select eaaitm,eatx40   ";
            Sql1 += " FROM M3FDBPRD.FCHACC ";
            Sql1 += " WHERE eaaitp =  2  and   eadivi = '" + LoginDivision + "' AND eaaitm='" + Costcenter.toUpperCase().trim() + "'  ";
            Sql1 += " Order by eaaitm";
            ResultSet rs1 = sta.executeQuery(Sql1);

            int CountCost = 0;
            while (rs1.next()) {
                CountCost = CountCost + 1;
            }

            return CountCost;
        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public String Get_SupplierNameWithCode2(String SupplierCode) //ITEM NAME WITH ITEM CODE
    {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "select idsuno,idsunm  ";
            Sql1 += " FROM M3FDBPRD.CIDMAS ";
            Sql1 += " where  idstat = '20' AND idcono='" + LoginCono + "' AND idsuno='" + SupplierCode + "'  ";
            Sql1 += " Order by idsuno";
            ResultSet rs1 = sta.executeQuery(Sql1);
            String SupplierName = "";
            while (rs1.next()) {
                SupplierName = (rs1.getString("idsunm").trim());
            }
            return SupplierName;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String GetSqlDataReportCashAdvReqStep1(String CASH_NO) {  //REPORT GRN  RESULT 1
        try {

            String Sql1 = "SELECT trim (IRYRE1) AS IRYRE1,trim(h.CASH_WHTAX) as CASH_WHTAX, h.CASH_CANO,SUBSTR(h.CASH_DATE,7,2) CONCAT '/' CONCAT SUBSTR(h.CASH_DATE,5,2)  CONCAT '/' CONCAT SUBSTR(h.CASH_DATE,1,4)as CASH_DATE,"
                    + "h.CASH_EMPY, h.CASH_COST, h.CASH_PPS1, h.CASH_PPS2, h.CASH_PPS3 ,SUBSTR( h.CASH_OPDF,7,2) CONCAT '/' CONCAT SUBSTR(h.CASH_OPDF,5,2)  CONCAT '/' CONCAT SUBSTR( h.CASH_OPDF,1,4)as CASH_OPDF ,"
                    + "SUBSTR( h.CASH_OPDT,7,2) CONCAT '/' CONCAT SUBSTR(h.CASH_OPDT,5,2)  CONCAT '/' CONCAT SUBSTR( h.CASH_OPDT,1,4) as CASH_OPDT ,"
                    + "SUBSTR( h.CASH_REDP,7,2) CONCAT '/' CONCAT SUBSTR(h.CASH_REDP,5,2)  CONCAT '/' CONCAT SUBSTR( h.CASH_REDP,1,4) as CASH_REDP    ,"
                    + "SUBSTR( h.CASH_STDP,7,2) CONCAT '/' CONCAT SUBSTR(h.CASH_STDP,5,2)  CONCAT '/' CONCAT SUBSTR( h.CASH_STDP,1,4)as  CASH_STDP     ,"
                    + " h.CASH_AMT, h.CASH_REQB ,sup.idsunm  as StaffName,costcenter.EATX40 as CostCenterName,h.cash_ref1  FROM " + dbname + ".FIN_CASHADALL as h   "
                    + "LEFT JOIN  (SELECT IRSUNO,IRYRE1,IRPHNO,IRTFNO,IRSUCM,idsuno,idsunm  "
                    + "FROM M3FDBPRD.CIDMAS,M3FDBPRD.CIDREF "
                    + "WHERE idstat = '20'  "
                    + "AND idcono = '" + LoginCono.trim() + "'  "
                    + "AND IRRFID = 'BANK' "
                    + "AND IRSUCM NOT IN ('','-') "
                    + "AND ircono  = idcono "
                    + "AND IRSUNO = idsuno )as sup ON sup.idsuno=h.cash_empy    "
                    + "LEFT JOIN  (SELECT  eaaitm,eatx40  FROM M3FDBPRD.FCHACC WHERE eaaitp =  2  "
                    + "and   eadivi = '" + LoginDivision.trim() + "') as costcenter ON costcenter.eaaitm=h.cash_cost  "
                    + "WHERE h.CASH_CANO = '" + CASH_NO.trim() + "'";
            System.out.println(Sql1);
            return Sql1;
        } catch (Exception ex) {
            System.err.println("ReprotStep 1 Error");
        }
        return null;
    }

    public String GetSqlDataReportCashAdvReqStep4(String CASH_NO) {  //REPORT GRN  RESULT 1
        try {

            String Sql1 = "    select SUBSTR(h.cash_stdt,7,2) || '/'  || SUBSTR(h.cash_stdt,5,2)  ||  '/' || SUBSTR(h.cash_stdt,1,4)  as cash_stdt\n"
                    + "    ,h.cash_empy,cd.idsunm, h.cash_cano ,SUBSTR(cash_date,7,2) || '/'    || SUBSTR(cash_date,5,2)  || '/' || SUBSTR(h.cash_date,1,4) as cash_date\n"
                    + "    , h.cash_cost, h.cash_amt,sett_invc \n"
                    + "    ,SUBSTR(sett_invd,7,2) || '/' || SUBSTR(sett_invd,5,2)|| '/' || SUBSTR(sett_invd,1,4) as DateInvoice    \n"
                    + "    ,sett_supp,sett_desc,sett_cost,sett_amtb,sett_vata,sett_amtt \n"
                    + "    ,CASE WHEN h.CASH_RETOBANK = 'SCB' then 'Return to ' ||  trim(CCTX15) \n"
                    //                    + "    WHEN h.CASH_RETOBANK = 'KBANK' then 'Return to ' || trim(CCTX15) \n"
                    + "    WHEN h.CASH_RETOBANK = '' then 'No Return ' \n"
                    + "    WHEN h.CASH_RETOBANK != '' THEN 'Claim back from  ' || trim(CCTX15) END as NameAmtPercash  \n"
                    + "    ,eatx40 as CostCenterName,COALESCE(trim(IRYRE1),BA.FBA_NAME  || ' : ' ||BA.FBA_ACCNO) AS Payment,TRIM(CASH_RETOBANK) as CASH_RETOBANK \n"
                    + "    ,cg.IDSUNM as PYNO_BACK,DEC(h.CASH_WHTAX,15,2) AS CASH_WHTAX,h.CASH_PAYT,h.CASH_REF1,h.CASH_REF2,h.CASH_REF3,BA.FBA_NAME  || ' : ' || BA.FBA_ACCNO AS BANK,CCTX15\n"
                    + "    ,CASE WHEN CASH_RCRA = '' THEN ''\n"
                    + "	   ELSE  'On '||SUBSTR(CASH_RCRA,7,2) || '/' || SUBSTR(CASH_RCRA,5,2)|| '/' || SUBSTR(CASH_RCRA,3,2)\n"
                    + "	   END as TRANSFERDATE\n"
                    + "    from  " + dbname + ".FIN_CASHADALL as h \n"
                    + "    LEFT JOIN M3FDBPRD.CMNDIV ON CCDIVI NOT IN  ('','120','130') AND CASH_CONO = CCCONO AND CASH_DIVI = CCDIVI\n"
                    + "    LEFT JOIN M3FDBPRD.CIDREF ON CASH_CONO = IRCONO AND IRSUNO = CASH_RETOBANK AND IRRFID = 'BANK' AND IRSUCM NOT IN ('','-')\n"
                    + "    LEFT JOIN M3FDBPRD.CIDMAS as cd ON  cash_empy= cd.idsuno   and cd.idcono = '" + LoginCono + "'\n"
                    + "    LEFT JOIN M3FDBPRD.CIDMAS AS cg on  CASH_PAYSUP = cg.idsuno   and cg.idcono = '" + LoginCono + "'\n"
                    + "    LEFT JOIN M3FDBPRD.FCHACC as ea ON  ea.eaaitp =  2  and   ea.eacono = cd.idcono    and ea.eaaitm=h.cash_cost AND  ea.eadivi='" + LoginDivision + "'\n"
                    + "    LEFT JOIN " + dbname + ".FIN_SETTADALL ON  cash_cano = sett_cano   \n"
                    + "    LEFT JOIN " + dbname + ".fin_bankall BA ON BA.FBA_CONO = h.CASH_CONO AND BA.FBA_DIVI = h.CASH_DIVI \n"
                    + "    where cash_cano = '" + CASH_NO.trim() + "' \n"
                    + "    and cash_cono = '" + LoginCono + "'\n"
                    + "    and cash_divi = '" + LoginDivision + "'\n"
                    + "    order by sett_invd ";
            System.out.println(Sql1);
            return Sql1;
        } catch (Exception ex) {
            System.err.println("ReprotStep 4 Error");
        }
        return null;
    }

    public ResultSet GetComboBoxPaymethod() {
        try {
            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT TRIM(CTSTKY) || ' : ' || TRIM(CTTX15) AS DESC,SUBSTR(CTPARM,1,2) || '.' || SUBSTR(CTPARM,3,2) as percent\n"
                    + "FROM M3FDBPRD.CSYTAB\n"
                    + "WHERE CTCONO = '" + LoginCono.trim() + "'\n"
                    + "AND CTSTCO = 'SERS'\n"
                    + "ORDER BY CTSTKY";
            ResultSet rs1 = sta.executeQuery(Sql1);
//            System.out.println(Sql1);
            return rs1;
        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String Get_DescMethodPay(String Method) //ITEM NAME WITH ITEM CODE
    {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT TRIM(CTSTKY) || ' : ' || TRIM(CTTX15) AS DESC,SUBSTR(CTPARM,1,2) || '.' || SUBSTR(CTPARM,3,2) as percent\n"
                    + "FROM M3FDBPRD.CSYTAB\n"
                    + "WHERE CTCONO = '" + LoginCono.trim() + "'\n"
                    + "AND CTSTCO = 'SERS'\n"
                    + "AND CTSTKY = '" + Method.trim() + "'\n"
                    + "ORDER BY CTSTKY";
            ResultSet rs1 = sta.executeQuery(Sql1);
            String DESC = "";
            while (rs1.next()) {
                DESC = (rs1.getString("DESC").trim());
            }

            return DESC;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet Cal_WHTAX(String Advance) //ITEM NAME WITH ITEM CODE
    {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT CASH_CONO,CASH_DIVI,CASH_CANO,SETT_INVC,SETT_AMTB,SETT_VATC,SETT_VATA,SETT_AMTT,SETT_RETT,\n"
                    + "       ROUND((CAST(SUBSTR(CTPARM,1,2) || '.' || SUBSTR(CTPARM,3,2)as DECIMAL(8,2)) * SETT_AMTB) / 100,2) as WHTAX\n"
                    + "       FROM " + dbname + ".FIN_CASHADALL," + dbname + ".FIN_SETTADALL,M3FDBPRD.CSYTAB,M3FDBPRD.FCHACC\n"
                    + "       WHERE CASH_CONO = '" + LoginCono + "'\n"
                    + "       AND CASH_DIVI = '" + LoginDivision + "'\n"
                    + "       AND CASH_CANO = '" + Advance.trim() + "'\n"
                    + "       AND CASH_CANO = SETT_CANO\n"
                    + "       AND CASH_CONO = SETT_CONO\n"
                    + "       AND CASH_DIVI = SETT_DIVI\n"
                    + "	      AND CTCONO = CASH_CONO\n"
                    + "	      AND CTCONO = SETT_CONO\n"
                    + "       AND CTSTCO = 'SERS'\n"
                    + "       AND CTSTKY = SETT_RETT\n"
                    + "       AND EACONO = CASH_CONO\n"
                    + "       AND EADIVI = CASH_DIVI\n"
                    + "       AND EAAITM = SETT_COST\n"
                    + "       AND TRIM(SUBSTR(EATX40,38,3))  != 'UCN'";

            ResultSet rs1 = sta.executeQuery(Sql1);
            return rs1;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String get_codevat(String Advance, String Invno) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = " SELECT SETT_CDVAT\n"
                    + " FROM " + dbname + ".FIN_SETTADALL\n"
                    + " WHERE SETT_CONO = '" + LoginCono + "'\n"
                    + " AND SETT_DIVI = '" + LoginDivision + "'\n"
                    + " AND SETT_INVC = '" + Invno.trim() + "'\n"
                    + " AND SETT_CANO = '" + Advance.trim() + "'";
            ResultSet rs1 = sta.executeQuery(Sql1);
            System.out.println(Sql1);
            String CODE_VAT = "";
            while (rs1.next()) {
                CODE_VAT = (rs1.getString("SETT_CDVAT").trim());
            }

            return CODE_VAT;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int CheckLineDuedate_APS100(String Advance) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT COUNT(*) AS CheckDuedate\n"
                    + " FROM " + dbname + ".FIN_SETTADALL\n"
                    + " WHERE SETT_CONO = '" + LoginCono + "'\n"
                    + " AND SETT_DIVI = '" + LoginDivision + "'\n"
                    + " AND SETT_CANO = '" + Advance.trim() + "'\n";
//                    + " AND (SETT_DUEDT = 0 OR SETT_RETT = '-')";
            ResultSet rs1 = sta.executeQuery(Sql1);
//            System.out.println(Sql1);
            int CheckDuedate = 9;
            while (rs1.next()) {
                CheckDuedate = (rs1.getInt("CheckDuedate"));
            }

            return CheckDuedate;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 9;
    }

    public int CheckInvoice(String InvoiceNo) {
        try {

            Connection conn = ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = "SELECT COUNT(*) AS CheckInvoice\n"
                    + " FROM " + dbname + ".FIN_SETTADALL\n"
                    + " WHERE SETT_INVC = '" + InvoiceNo.trim() + "'\n";
            ResultSet rs1 = sta.executeQuery(Sql1);
//            System.out.println(Sql1);
            int CheckInvoice = 0;
            while (rs1.next()) {
                CheckInvoice = (rs1.getInt("CheckInvoice"));
            }

            return CheckInvoice;

        } catch (Exception ex) {
            Logger.getLogger(CashAdvanRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

}
