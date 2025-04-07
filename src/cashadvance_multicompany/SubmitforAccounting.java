/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashadvance_multicompany;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import MForms.Utils.MNEHelper;
import MForms.Utils.MNEProtocol;
import static cashadvance_multicompany.CashAdvanAccounting.rdoReturnKBANK;
import static cashadvance_multicompany.CashAdvanAccounting.rdoReturnSCB;
import static cashadvance_multicompany.frmLogin.LoginAppPort;
import static cashadvance_multicompany.frmLogin.LoginAppServer;
import static cashadvance_multicompany.frmLogin.LoginCono;
import static cashadvance_multicompany.frmLogin.LoginDivision;
import static cashadvance_multicompany.frmLogin.LoginUrlConnectionM3;
import static cashadvance_multicompany.frmLogin.PasswordM3;
import static cashadvance_multicompany.frmLogin.UserM3;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Monthon
 */
public class SubmitforAccounting {

    DefaultTableModel modelM3;

//    public void callARS100(String Advance, String cusNO, String amt, String accdate, String settdate, String remark) {
//
//        try {
//
//            MNEHelper mne = new MNEHelper(LoginAppServer, LoginAppPort, LoginUrlConnectionM3);
//            mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3);
//            if (!mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3)) {
//                System.out.println("Can not login to M3 system");
//            }
//
//            String ARS100D = mne.runM3Pgm("ARS100");
//            
//            
//
//            if ((ARS100D).equals("")) {
//                System.out.println(" ไม่สามารถเปิดโปรแกรม ARS100 ได้");
//                JOptionPane.showMessageDialog(null, "ไม่สามารถเปิดโปรแกรม ARS100 ได้", "ไม่สามารถเปิดโปรแกรม ARS100 ได้", JOptionPane.WARNING_MESSAGE);
//                mne.closeProgram(ARS100D);
//            }
//
//            if (mne.panel.equals("ARS100/B")) {
//                mne.setField("CMDTP", "KEY");
//                mne.setField("CMDVAL", "ENTER");
//                mne.setField("FCS", "WAFNCN");
//                mne.setField("WAFNCN", "400");
//                mne.pressKey(MNEProtocol.KeyEnter);
//
////                mne.setField("CMDTP", "1"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
//                mne.setField("CMDTP", "LSTOPT"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
//
//                mne.setField("SELROWS", "R1"); //CMDTP
//                mne.setField("FCS", "WAFNCN"); //CMDVAL ENTER
//                mne.setField("WAFNCN", "400"); //FCS W1OBKV
////              mne.pressKey(MNEProtocol.KeyEnter);
//                mne.selectOption("1");
//                System.out.println(mne.getMsg());
//            }
//
//            if (mne.panel.equals("ARS100/E")) {
//                // System.out.println("CRS610/E");
//                mne.setField("CMDTP", "KEY"); //CMDTP KEY
//                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
//                mne.setField("CMDVAL", "ENTER"); //
//                mne.setField("FCS", "WWCUAM"); //
//                mne.setField("WWCUAM", amt); //amt
//                mne.setField("WWCUNO", cusNO); //customer
//                mne.setField("WWCINO", Advance); //invoice no
//                mne.setField("WWIVDT", accdate); //ACC date
//                mne.setField("WWACDT", accdate); //ACC date
//                mne.pressKey(MNEProtocol.KeyEnter);
//                System.out.println(mne.getMsg());
//                if (mne.getMsg() != null) {
//                    JOptionPane.showMessageDialog(null, mne.getMsg());
//                }
//
//            }
//
//            if (mne.panel.equals("ARS100/F")) {
//                // System.out.println("CRS610/E");
//                mne.setField("CMDTP", "KEY"); //CMDTP KEY
//                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
//                mne.setField("CMDVAL", "ENTER"); //
//                mne.setField("FCS", "WWAIT5"); //
//                mne.setField("WWGVTX", remark); //cash advance
//                mne.setField("WWCUCD", "THB"); //thb
//                mne.setField("WWDUDT", settdate); //due
//                mne.setField("WWAIT4", cusNO); //cus
//                mne.setField("WWAIT5", Advance); //advno
//                mne.pressKey(MNEProtocol.KeyEnter);
//                System.out.println(mne.getMsg());
//                // JOptionPane.showMessageDialog(null, mne.getMsg());
//            }
//
//            if (mne.panel.equals("TXS150/B")) {
//                // System.out.println("CRS610/E");
//                mne.setField("CMDTP", "KEY"); //CMDTP KEY
//                mne.setField("CMDVAL", "ENTER");
//                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
////                mne.setField("CMDVAL", "R1C1"); //
//                mne.setField("R1C1", ""); //
//                mne.setField("R1C2", ""); //
//                mne.setField("FCS", "R1C2"); //
//                mne.pressKey(MNEProtocol.KeyEnter);
//                mne.pressKey(MNEProtocol.KeyF03);
//                System.out.println(mne.getMsg());
//                //   JOptionPane.showMessageDialog(null, mne.getMsg());
//            }
//
//            if (mne.panel.equals("GLS120/J1")) {
//                // System.out.println("CRS610/E");
//                mne.setField("CMDTP", "KEY"); //CMDTP KEY
//                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
//                mne.setField("CMDVAL", "ENTER"); //
//                mne.setField("FCS", "WXAIT5"); //
//                mne.setField("WOPAVR", "STD01-01"); //
//                mne.setField("WWCUAM", "-" + amt);
////                mne.setField("WWQTTP", "1"); //1
//                mne.setField("WXAIT5", Advance);
//                mne.setField("WXAIT1", "8AA1301");
//                mne.setField("WXAIT4", cusNO);
//
//                mne.pressKey(MNEProtocol.KeyEnter);
//                mne.pressKey(MNEProtocol.KeyF03);
//
//                System.out.println(mne.getMsg());
//                String[] Vouchermss = mne.getMsg().toString().split(" ");
//                String Voucher = Vouchermss[2].trim();
//
//                ClassSetdata cgdt = new ClassSetdata();
//                cgdt.UpdateVoucher(Advance, Voucher);
//                JOptionPane.showMessageDialog(null, mne.getMsg());
//
//            }
//            mne.closeProgram(ARS100D);
//
//        } catch (NumberFormatException ex) {
//            Logger.getLogger(SubmitforAccounting.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(SubmitforAccounting.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    public void callARS100(String Advance, String cusNO, String amt, String accdate, String settdate, String remark, String payto) {

        try {

            MNEHelper mne = new MNEHelper(LoginAppServer, LoginAppPort, LoginUrlConnectionM3);
            mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3);
            if (!mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3)) {
                System.out.println("Can not login to M3 system");
            }

            String ARS100D = mne.runM3Pgm("ARS100");

            String getYear = accdate.substring(4, 6);
            Advance = getYear + Advance;
            String subAdvance = Advance.substring(2, 9);

            if ((ARS100D).equals("")) {
                System.out.println(" ไม่สามารถเปิดโปรแกรม ARS100 ได้");
                JOptionPane.showMessageDialog(null, "ไม่สามารถเปิดโปรแกรม ARS100 ได้", "ไม่สามารถเปิดโปรแกรม ARS100 ได้", JOptionPane.WARNING_MESSAGE);
                mne.closeProgram(ARS100D);
            }

            if (mne.panel.equals("ARS100/B")) {
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("FCS", "WAFNCN");
                mne.setField("WAFNCN", "400");
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "1"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
                mne.setField("SELROWS", "R1"); //CMDTP
                mne.setField("FCS", "WAFNCN"); //CMDVAL ENTER
                mne.setField("WAFNCN", "400"); //FCS W1OBKV
//              mne.pressKey(MNEProtocol.KeyEnter);
                mne.selectOption("1");
                System.out.println(mne.getMsg());
            }

            if (mne.panel.equals("ARS100/E")) {
                // System.out.println("CRS610/E");
                mne.setField("CMDTP", "KEY"); //CMDTP KEY
                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
                mne.setField("CMDVAL", "ENTER"); //
                mne.setField("FCS", "WWCUAM"); //
                mne.setField("WWCUAM", amt); //amt
                mne.setField("WWCUNO", cusNO); //customer
                mne.setField("WWCINO", Advance); //invoice no
                mne.setField("WWIVDT", accdate); //ACC date
                mne.setField("WWACDT", accdate); //ACC date

                mne.pressKey(MNEProtocol.KeyEnter);
                System.out.println(mne.getMsg());
                if (mne.getMsg() != null) {
                    JOptionPane.showMessageDialog(null, mne.getMsg());
                }

            }

            if (mne.panel.equals("ARS100/F")) {
                // System.out.println("CRS610/E");
                mne.setField("CMDTP", "KEY"); //CMDTP KEY
                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
                mne.setField("CMDVAL", "ENTER"); //
                mne.setField("FCS", "WWAIT5"); //
                mne.setField("WWGVTX", remark); //cash advance
                mne.setField("WWCUCD", "THB"); //thb
                mne.setField("WWDUDT", settdate); //due
                mne.setField("WWAIT4", cusNO); //cus
                mne.setField("WWAIT5", subAdvance); //advno
                mne.setField("WWAIT6", payto); //advno

                mne.pressKey(MNEProtocol.KeyEnter);
                System.out.println(mne.getMsg());
                // JOptionPane.showMessageDialog(null, mne.getMsg());
            }

            if (mne.panel.equals("TXS150/B")) {
                // System.out.println("CRS610/E");
                mne.setField("CMDTP", "KEY"); //CMDTP KEY
                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
                mne.setField("CMDVAL", "R1C1"); //
                mne.setField("R1C1", ""); //
                mne.setField("R1C2", ""); //
                mne.setField("FCS", "R1C1"); //
                mne.pressKey(MNEProtocol.KeyEnter);
                mne.pressKey(MNEProtocol.KeyF03);

                //System.out.println(mne.getMsg());
                //   JOptionPane.showMessageDialog(null, mne.getMsg());
            }

            if (mne.panel.equals("GLS120/J1")) {
                // System.out.println("CRS610/E");
                mne.setField("CMDTP", "KEY"); //CMDTP KEY
                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303"); //APPSRV BKRMVXM3.BANGKOKRANCH.COM:16303
                mne.setField("CMDVAL", "ENTER"); //
                mne.setField("FCS", "WXAIT5"); //
                mne.setField("WOPAVR", "STD01-01"); //
                mne.setField("WWCUAM", "-" + amt);
                mne.setField("WWQTTP", "1"); //1
                mne.setField("WXAIT5", subAdvance);
                mne.setField("WXAIT6", payto);
                mne.setField("WXAIT1", "8AA1301");
                mne.setField("WXAIT4", cusNO);

                mne.pressKey(MNEProtocol.KeyEnter);
                mne.pressKey(MNEProtocol.KeyF03);

                System.out.println(mne.getMsg());
                String[] Vouchermss = mne.getMsg().toString().split(" ");
                String Voucher = Vouchermss[2].trim();
                ClassSetdata cgdt = new ClassSetdata();
                cgdt.UpdateVoucher(subAdvance, Voucher);
                JOptionPane.showMessageDialog(null, mne.getMsg());

            }
            mne.closeProgram(ARS100D);

        } catch (NumberFormatException ex) {
            Logger.getLogger(SubmitforAccounting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SubmitforAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String callARS100_Offset(String Advance, String StaffCode, String DateAccountSettle) {
        String msg = "";

        try {
            MNEHelper mne = new MNEHelper(LoginAppServer, LoginAppPort, LoginUrlConnectionM3);
            mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3);
            if (!mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3)) {
                System.out.println("Can not login to M3 system");
            }
            String ARS110 = mne.runM3Pgm("ARS110");

            if ((ARS110).equals("")) {
                System.out.println(" ไม่สามารถเปิดโปรแกรม ARS110 ได้");
                JOptionPane.showMessageDialog(null, "ไม่สามารถเปิดโปรแกรม ARS110 ได้", "ไม่สามารถเปิดโปรแกรม ARS110 ได้", JOptionPane.WARNING_MESSAGE);
                mne.closeProgram(ARS110);
            }

            if (mne.panel.equals("ARS110/B")) {
                System.out.println(mne.panel);

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WAFNCN", "400");
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "1");
                mne.setField("SELROWS", "R1");
                mne.setField("FCS", "WAFNCN");
                mne.setField("WAFNCN", "400");
                mne.selectOption("1");

            }

            if (mne.panel.equals("ARS112/E")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("FCS", "WEAIT2");
                mne.setField("WEACDT", DateAccountSettle);
                mne.setField("WEAIT1", "9BC9999"); // Accode  8AA1301
                mne.setField("WEAIT2", ""); // Cost center
                mne.setField("WECUAM", ""); // InvAmt

                mne.setField("WEAIT4", StaffCode);// Cost center Ref1
                mne.setField("WEAIT5", Advance);
                mne.setField("WEAIT6", "*BLANK");
                mne.pressKey(MNEProtocol.KeyEnter);
                if (mne.getMsg() != null) {
                    System.out.println(mne.getMsg());
                }
            }

            if (mne.panel.equals("ARS110/F1")) {
                System.out.println(mne.panel);

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WFPYNO", StaffCode);//Staffcode
                mne.setField("FCS", "WFPYNO");
                mne.setField("WFQTTP", "1");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "1");
                mne.setField("WFPYNO", StaffCode);//Staffcode
                mne.setField("FCS", "WFCINO");
                mne.setField("SELROWS", "R1");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.selectOption("1");

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WFPYNO", StaffCode);//Staffcode
                mne.setField("FCS", "WFSLOP");
                mne.setField("WFSLOP", "17");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.pressKey(MNEProtocol.KeyEnter);

                if (mne.getMsg() != null) {
                    System.out.println(mne.getMsg());
                }

            }

            //////////////////////////////
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();

            Classgetdata cgd = new Classgetdata();
            ResultSet rsl = cgd.GetAdvanceDetailM3(Advance);
            String[] SETT_COST = new String[20], SETT_AMTB = new String[20], ACCODE = new String[20], ServiceCode = new String[20], VATCODE = new String[20], InvNO = new String[20], InvAccDate = new String[20],
                    InvDate = new String[20], InvAmt = new String[20], InvSupp = new String[20], GenVoucherText = new String[20], SETT_DUEDATE = new String[20],
                    EATX40 = new String[20], SETT_RETT = new String[20];
            Double[] VAT_C = new Double[20], VAT_UC = new Double[20];
            Double InvAmtAll = 0.00, AdvAMT = 0.00;
            int i = 0;
            int x = 0;
            while (rsl.next()) {
                VAT_C[i] = ccdp.Double2digitReturn(rsl.getDouble("VAT_C"));
                VAT_UC[i] = ccdp.Double2digitReturn(rsl.getDouble("VAT_UC"));
                SETT_COST[i] = rsl.getString("SETT_COST").trim();
                SETT_AMTB[i] = rsl.getString("SETT_AMTB").trim();
                ACCODE[i] = rsl.getString("ACCODE").trim();
                VATCODE[i] = rsl.getString("VATCODE").trim();
                InvNO[i] = rsl.getString("SETT_INVC").trim();
                InvAccDate[i] = ccdp.SubDateForM3(rsl.getString("SETT_INVD").trim());
                InvDate[i] = ccdp.SubDateForM3(rsl.getString("SETT_INVD").trim());
                InvAmt[i] = rsl.getString("SETT_AMTT").trim();
                InvSupp[i] = rsl.getString("SETT_SUPP").trim();
                GenVoucherText[i] = rsl.getString("SETT_VCTXT").trim();
                if (!rsl.getString("SETT_DUEDT").trim().equals("0")) {
                    SETT_DUEDATE[i] = ccdp.SubDateForM3(rsl.getString("SETT_DUEDT").trim());
                }
                EATX40[i] = rsl.getString("EATX40").trim();
                InvAmtAll += rsl.getDouble("SETT_AMTT");
                AdvAMT = rsl.getDouble("CASH_AMT");
                i++;
            }
            for (x = 0; x < i;) {
                if (EATX40[x].equalsIgnoreCase("UCN")) {
                    x++;
                    continue;
                } else {
                    if (mne.panel.equals("APS120/F1")) {
                        System.out.println(mne.panel);
                        mne.setField("CMDTP", "KEY");
                        mne.setField("CMDVAL", "ENTER");
                        mne.setField("FCS", "WFSPYN");
                        mne.setField("WFQTTP", "1");
                        mne.setField("WFSPYN", InvSupp[x]); //SETT_SUPP
                        mne.setField("W1SINO", InvNO[x]);// Advance REF2
                        mne.pressKey(MNEProtocol.KeyEnter);

                        mne.setField("CMDTP", "LSTOPT");
                        mne.setField("CMDVAL", "1");
                        mne.setField("FCS", "WFSPYN");
                        mne.setField("W1SINO", InvNO[x]);
                        mne.setField("WFQTTP", "1"); //SETT_SUPP
                        mne.setField("WFSPYN", InvSupp[x]);// Advance REF2
                        mne.setField("SELROWS", "R1");// Advance REF2
                        mne.selectOption("1");
                        if (mne.getMsg() != null) {
                            System.out.println(mne.getMsg());
                        }
                    }
                    x++;
                }

            }

            if (mne.panel.equals("APS120/F1")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "F3");
                mne.setField("FCS", "WFSPYN");
                mne.pressKey(MNEProtocol.KeyF03); // 92110012

                if (mne.getMsg() != null) {
                    System.out.println(mne.getMsg());
                }
            }

            ////
            if (AdvAMT > InvAmtAll || AdvAMT < InvAmtAll) {
                String CASH_RETOBANK = "";
                String VCTXT = "";
                if (rdoReturnSCB.isSelected() == true) {
                    CASH_RETOBANK = "1AA2105";
                    VCTXT = "Return to 1AA2105";
                } else if (rdoReturnKBANK.isSelected() == true) {
                    CASH_RETOBANK = "1AA2110";
                    VCTXT = "Return to 1AA2110";
                } else {
                    CASH_RETOBANK = "8AA1302";
                    VCTXT = "Return to 8AA1302";
                }
                Double Balance = AdvAMT - InvAmtAll;
                if (mne.panel.equals("ARS110/F1")) {
                    mne.setField("CMDTP", "KEY");
                    mne.setField("CMDVAL", "ENTER");
                    mne.setField("WFPYNO", StaffCode);//Staffcode
                    mne.setField("FCS", "WFSLOP");
                    mne.setField("WFSLOP", "41");
                    mne.setField("WFQTTP", "1");
                    mne.setField("WFCINO", Advance);// Advance REF2
                    mne.pressKey(MNEProtocol.KeyEnter);
                }

                if (mne.panel.equals("ARS110/G1")) {
                    System.out.println(mne.getMsg());
                    mne.setField("WGCUAM", Balance);
                    mne.setField("WGAIT3", "");
//                    mne.setField("WGAIT7", "ARS110");
                    mne.setField("WGVTCD", "");
                    mne.setField("WGAIT4", StaffCode);
                    mne.setField("FCS", "WGVTXT");
                    mne.setField("WGVTXT", VCTXT);
//                    mne.setField("WGECAR", "61");
                    mne.setField("WGAIT5", Advance); // REF2
                    mne.setField("WGAIT1", CASH_RETOBANK); // CODE BANK
                    mne.setField("WGFTCO", "TH");
                    mne.setField("WGAIT6", "*BLANK");
                    mne.setField("WGAIT2", ""); //COSTC.

                    mne.setField("CMDTP", "KEY");
                    mne.setField("CMDVAL", "ENTER");
                    mne.setField("WGCUAM", Balance);
                    mne.setField("WGAIT3", "");
//                    mne.setField("WGAIT7", "ARS110");
                    mne.setField("WGVTCD", "");
                    mne.setField("WGAIT4", StaffCode);
                    mne.setField("FCS", "WGVTXT");
                    mne.setField("WGVTXT", VCTXT);
//                    mne.setField("WGECAR", "61");
                    mne.setField("WGAIT5", Advance); // REF2
                    mne.setField("WGAIT1", CASH_RETOBANK); // CODE BANK
                    mne.setField("WGFTCO", "TH");
                    mne.setField("WGAIT6", "*BLANK");
                    mne.setField("WGAIT2", ""); //COSTC.

                    mne.pressKey(MNEProtocol.KeyEnter);
                }

            }

////
            if (mne.panel.equals("ARS110/F1")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "F3");
                mne.setField("FCS", "WFSPYN");
                mne.pressKey(MNEProtocol.KeyF03); // 92110012

                if (mne.getMsg() != null) {
                    System.out.println(mne.getMsg());
                }
            }
            msg = "Message from ARS110 : " + mne.getMsg();
            ClassSetdata csd = new ClassSetdata();

            String[] Vouchermss = mne.getMsg().toString().split(" ");
            String Voucher = Vouchermss[2].trim();

            if (Voucher.length() == 8) {
                csd.UpdateVoucher_SettleARS(Advance, Voucher);
            }

            mne.closeProgram(ARS110);
            return msg;
        } catch (Exception ex) {
            Logger.getLogger(SubmitforAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }

    public String callARS110_CaseRefund(String Advance, String StaffCode, String DateAccountSettle) {
        String msg = "";
        try {
            MNEHelper mne = new MNEHelper(LoginAppServer, LoginAppPort, LoginUrlConnectionM3);
            mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3);
            if (!mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3)) {
                System.out.println("Can not login to M3 system");
            }

            String ARS110 = mne.runM3Pgm("ARS110");

            if ((ARS110).equals("")) {
                System.out.println(" ไม่สามารถเปิดโปรแกรม ARS110 ได้");
                JOptionPane.showMessageDialog(null, "ไม่สามารถเปิดโปรแกรม ARS110 ได้", "ไม่สามารถเปิดโปรแกรม ARS110 ได้", JOptionPane.WARNING_MESSAGE);
                mne.closeProgram(ARS110);
            }

            Classgetdata cgd = new Classgetdata();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            String[] InvDate = new String[20], InvAmt = new String[20], InvSupp = new String[20],
                    GenVoucherText = new String[20], SETT_DUEDATE = new String[20], CASH_REF1 = new String[20];
            String[] SETT_CODE = new String[20], SETT_COST = new String[20];
            Double[] SETT_AMTB = new Double[20], SETT_AMTT = new Double[20], SETT_VATC = new Double[20], VAT_C = new Double[20], VAT_UC = new Double[20];
            int i = 0;
            ResultSet rsl = cgd.GetAdvanceDetailM3(Advance);
            Double InvAmtAll = 0.00, AdvAMT = 0.00, BalanceAMT = 0.00;
            String AdvCostc = "";

            while (rsl.next()) {
                AdvAMT = rsl.getDouble("CASH_AMT");
                SETT_CODE[i] = rsl.getString("ACCODE").trim();
                SETT_COST[i] = rsl.getString("SETT_COST").trim();
                SETT_AMTB[i] = rsl.getDouble("SETT_AMTB");
                SETT_AMTT[i] = rsl.getDouble("SETT_AMTT");
                SETT_VATC[i] = rsl.getDouble("SETT_VATC");
                VAT_C[i] = rsl.getDouble("VAT_C");
                VAT_UC[i] = rsl.getDouble("VAT_UC");
                InvAmtAll += rsl.getDouble("SETT_AMTT");
                AdvCostc = rsl.getString("CASH_COST").trim();
                i++;
            }

            if (mne.panel.equals("ARS110/B")) {
                System.out.println("ARS110/B");

                mne.setField("CMDTP", "KEY");
                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WAFNCN", "400");
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "1");
                mne.setField("SELROWS", "R1");
                mne.setField("FCS", "WAFNCN");
                mne.setField("WAFNCN", "400");
                mne.selectOption("1");

            }

            if (mne.panel.equals("ARS112/E")) {
                System.out.println("ARS112/E");
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("FCS", "WEAIT2");
                mne.setField("WEACDT", DateAccountSettle);
                mne.setField("FCS", "WEAIT2");

                mne.setField("WEAIT1", SETT_CODE[0]); // Accode
                mne.setField("WEAIT2", SETT_COST[0]); // Cost center
                mne.setField("WECUAM", InvAmtAll); // InvAmt
                System.out.println(InvAmtAll);
                mne.setField("WEAIT4", StaffCode);// Cost center Ref1
                mne.setField("WGAIT5", Advance);
                mne.setField("WGAIT6", "*BLANK");
                mne.pressKey(MNEProtocol.KeyEnter);
                if (mne.getMsg() != null) {
                    JOptionPane.showMessageDialog(null, mne.getMsg().toString());
                }
            }

            ////
            if (mne.panel.equals("ARS110/F1")) {
                System.out.println("ARS110/F1");
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WFPYNO", StaffCode);//Staffcode
                mne.setField("WFSLOP", "41");
                mne.setField("FCS", "WFSLOP");
                mne.setField("WFQTTP", "1");// Advance REF2
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.pressKey(MNEProtocol.KeyEnter);
            }

            if (mne.panel.equals("ARS110/G1")) {

                Double CreditBank = AdvAMT - InvAmtAll;

                mne.setField("CMDVAL", "ENTER");
                mne.setField("WGCUAM", CreditBank); // -500
                mne.setField("WGAIT3", "");
                mne.setField("CMDTP", "KEY");
                mne.setField("FCS", "WGAIT3");
                mne.setField("WGVTXT", "Advance Clearing");
                mne.setField("WGAIT4", StaffCode);
                mne.setField("WGAIT5", Advance);
                mne.setField("WGAIT6", "*BLANK");
                mne.setField("WGAIT1", "8AA1302");
                mne.setField("WGAIT2", "");
                mne.pressKey(MNEProtocol.KeyEnter);

            }

            ///
            if (mne.panel.equals("ARS110/F1")) {
                System.out.println("ARS110/F1");

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WFPYNO", StaffCode);//Staffcode
                mne.setField("FCS", "WFCINO");
                mne.setField("WFQTTP", "1");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "11");
                mne.setField("WFPYNO", StaffCode);//Staffcode
                mne.setField("FCS", "WFCINO");
                mne.setField("SELROWS", "R1");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.selectOption("11");

            }

            if (mne.getMsg() != null) {
                return msg = mne.getMsg().toString();
            }

            /// Partail 
            if (mne.panel.equals("ARS110/H")) {
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("FCS", "WHCUAM");
                mne.setField("WHCUAM", AdvAMT);
                mne.pressKey(MNEProtocol.KeyEnter);
            }

            if (i > 1) {

                Double CreditInv1 = 0.00;
                CreditInv1 = (InvAmtAll - SETT_AMTT[0]) * -1;

                for (i = 0; i < 12; i++) {

                    if (SETT_CODE[i] != null) {

                        if (mne.panel.equals("ARS110/F1")) {
                            System.out.println("ARS110/F1");
                            mne.setField("CMDTP", "KEY");
                            mne.setField("CMDVAL", "ENTER");
                            mne.setField("WFPYNO", StaffCode);//Staffcode
                            mne.setField("WFSLOP", "41");
                            mne.setField("FCS", "WFSLOP");
                            mne.setField("WFQTTP", "1");// Advance REF2
                            mne.setField("WFCINO", Advance);// Advance REF2
                            mne.pressKey(MNEProtocol.KeyEnter);
                        }

                        if (mne.panel.equals("ARS110/G1")) {
                            if (mne.getMsg() != null) {
                                mne.pressKey(MNEProtocol.KeyEnter);
                            }

                            SETT_AMTT[0] = CreditInv1;
                            mne.setField("CMDVAL", "ENTER");
                            mne.setField("WGCUAM", SETT_AMTT[i]);
                            mne.setField("WGAIT3", "");
                            mne.setField("CMDTP", "KEY");
                            mne.setField("FCS", "WGAIT3");
                            mne.setField("WGAIT4", StaffCode);
                            mne.setField("WGAIT5", Advance);
                            mne.setField("WGAIT6", "*BLANK");
                            mne.setField("WGVTXT", "Advance Clearing");
                            mne.setField("WGAIT1", SETT_CODE[i]);
                            mne.setField("WGAIT2", SETT_COST[i]);
                            mne.pressKey(MNEProtocol.KeyEnter);

                        }
                    } else {
                        System.out.println("Break");
                        break;
                    }
                }
            }

            if (mne.panel.equals("ARS110/F1")) {
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "3");
                mne.setField("FCS", "WFCINO");
                mne.pressKey(MNEProtocol.KeyF03); // 92110012
                System.out.println(mne.getMsg());
            }
            ClassSetdata csd = new ClassSetdata();
            msg = mne.getMsg();
            String[] Vouchermss = mne.getMsg().toString().split(" ");
            String Voucher = Vouchermss[2].trim();

            if (Voucher.length() == 8) {
                csd.UpdateVoucher_SettleARS(Advance, Voucher);
            }

            mne.closeProgram(ARS110);
            return msg;
        } catch (Exception ex) {
            return msg;
        }

    }

    public String callAPS100(Double VAT_C, Double VAT_UC, String SETT_COST, String SETT_AMTB, String ACCODE, String InvNO, String DateAccountSettle, String InvDate, String InvAmt,
            String InvSupp, String GenVoucherText, String SETT_DUEDATE, String EATX40, String VATCODE, String Advance, String ServiceCode, String StaffCode, String payto) throws Exception {
        String msg = "";
        MNEHelper mne = new MNEHelper(LoginAppServer, LoginAppPort, LoginUrlConnectionM3);
        mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3);
        if (!mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3)) {
            System.out.println("Can not login to M3 system");
        }
        String APS100 = mne.runM3Pgm("APS100");
        try {

            if ((APS100).equals("")) {
                System.out.println(" ไม่สามารถเปิดโปรแกรม ARS100 ได้");
                JOptionPane.showMessageDialog(null, "ไม่สามารถเปิดโปรแกรม APS100 ได้", "ไม่สามารถเปิดโปรแกรม APS100 ได้", JOptionPane.WARNING_MESSAGE);
                mne.closeProgram(APS100);
            }

            if (mne.panel.equals("APS100/B")) {
                System.out.println(mne.panel);
                System.out.println("APS100/B");
//                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303");
                mne.setField("FCS", "WAFNCN");
                mne.setField("WAFNCN", "608");
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.pressKey(MNEProtocol.KeyEnter);
                mne.setField("CMDVAL", "1");
                mne.setField("SELROWS", "R1");
                mne.setField("FCS", "WAFNCN");
                mne.setField("WAFNCN", "608");
                mne.selectOption("1");
            }

            if (mne.panel.equals("APS100/E")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
//                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WWACDT", DateAccountSettle);
                mne.setField("WWIVDT", InvDate);
                mne.setField("WWSINO", InvNO);
                mne.setField("FCS", "WWACDT");
                mne.setField("WWCUAM", InvAmt);
                mne.setField("WWSUNA", InvSupp);
                mne.pressKey(MNEProtocol.KeyEnter);
                mne.pressKey(MNEProtocol.KeyEnter);
            }

            if (mne.getMsg() != null) {
                return mne.getMsg().toString();
            }
            if (mne.panel.equals("APS100/F")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");

                mne.setField("WWFTCO", "TH");
                mne.setField("WWCRTP", "2");
                mne.setField("WWBSCD", "TH");
                if (!GenVoucherText.equalsIgnoreCase("") && !GenVoucherText.equalsIgnoreCase(null)) {
                    mne.setField("WWGVTX", GenVoucherText); // Gen Voucher text
                }
                mne.setField("WWECAR", "01");
                mne.setField("WWCUCD", "THB");
                mne.setField("WWAIT7", "AP10-200");
                mne.setField("FCS", "WWGVTX");
                mne.setField("WWDUDT", SETT_DUEDATE);
                System.out.println(SETT_DUEDATE);
                mne.setField("WWSERS", ServiceCode); // WH/TAX
                mne.setField("WWAIT1", "2AE2201");
                mne.setField("WWSPYN", InvSupp);
                mne.setField("WWVTCD", VATCODE);
                mne.setField("WWPYME", "HO");
                mne.setField("WWTEPY", "030");
                mne.setField("WWARAT", "1.000000");
                mne.pressKey(MNEProtocol.KeyEnter);

            }

            if (!mne.panel.equals("TXS150/B")) {
                System.out.println(mne.getMsg());
                // mne.pressKey(MNEProtocol.KeyF12);
                mne.pressKey("ENTER");

                if (mne.panel.equals("TXS150/B")) {
                    mne.pressKey(MNEProtocol.KeyF12);
                }
                if (mne.panel.equals("APS100/F")) {
                    mne.pressKey("ENTER");
                    System.out.println(mne.panel);
                    mne.setField("CMDTP", "KEY");
                    mne.setField("CMDVAL", "ENTER");

                    mne.setField("WWFTCO", "TH");
                    mne.setField("WWCRTP", "2");
                    mne.setField("WWBSCD", "TH");
                    if (!GenVoucherText.equalsIgnoreCase("") && !GenVoucherText.equalsIgnoreCase(null)) {
                        mne.setField("WWGVTX", GenVoucherText); // Gen Voucher text
                    }
                    mne.setField("WWECAR", "01");
                    mne.setField("WWCUCD", "THB");
                    mne.setField("WWAIT7", "AP10-200");
                    mne.setField("FCS", "WWGVTX");
                    mne.setField("WWDUDT", SETT_DUEDATE);
                    mne.setField("WWSERS", ServiceCode); // WH/TAX
                    mne.setField("WWAIT1", "2AE2201");
                    mne.setField("WWSPYN", InvSupp);
                    mne.setField("WWVTCD", VATCODE);
                    mne.setField("WWPYME", "HO");
                    mne.setField("WWTEPY", "030");
                    mne.setField("WWARAT", "1.000000");
                    mne.pressKey(MNEProtocol.KeyEnter);

                }
            }

            if (mne.panel.equals("TXS150/B")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
//                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("R1C1", VATCODE);//40
                mne.setField("R1C4", VAT_UC);//"65.42"
                mne.setField("R1C2", VAT_C);//"10.32"
                mne.setField("FCS", "R1C4");
                mne.pressKey(MNEProtocol.KeyEnter);
                mne.pressKey(MNEProtocol.KeyF03); // 92110012
            }
            if (mne.panel.equals("GLS120/J1")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WOPAVR", "STD01-01");
                mne.setField("WXAIT2", SETT_COST);//S8  A1
                mne.setField("WWCUAM", SETT_AMTB);//1000  934.58
                mne.setField("WWQTTP", "1");
                mne.setField("WXAIT1", ACCODE);//6BC9905
                mne.setField("WXAIT4", StaffCode);//6BC9905
                mne.setField("WXAIT5", Advance);
                mne.setField("WXAIT6", payto);
                mne.setField("FCS", "WWCUAM");
                mne.setField("WWSERS", ServiceCode); // Scd   21
                mne.setField("WWVTCD", VATCODE); //40
                mne.pressKey(MNEProtocol.KeyEnter);

            }

            if (mne.panel.equals("GLS120/J1")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WOPAVR", "STD01-01");
                mne.setField("WXAIT2", SETT_COST);//s8
                mne.setField("WXAIT4", StaffCode);//6BC9905
                mne.setField("WXAIT5", Advance);
                mne.setField("WXAIT6", payto);
                mne.setField("WWCUAM", VAT_UC);//59.68
                mne.setField("WWQTTP", "1");
                mne.setField("WWSERS", ""); // Scd   21
                mne.setField("WXAIT1", ACCODE);//6BC9905
                mne.setField("FCS", "WWCUAM");
                mne.setField("WWVTCD", VATCODE);//40
                mne.pressKey(MNEProtocol.KeyEnter);

            }

            if (mne.panel.equals("GLS120/J1")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "F3");
                mne.setField("WOPAVR", "STD01-01");
                mne.setField("WWQTTP", "1");
                mne.setField("FCS", "WXAIT1");
                mne.setField("WWVTCD", "40");
                mne.pressKey(MNEProtocol.KeyF03); // 92110012
            }

            System.out.println(mne.getMsg());
            msg = mne.getMsg();
            mne.closeProgram(APS100);
        } catch (Exception ex) {
            mne.closeProgram(APS100);
            msg = ex.toString();
            return msg;
        }
        return msg;
    }

    public String callARS110_V2(String Advance, String Staffcode, String DateAccountSettle) {
        String msg = "";
        try {
            MNEHelper mne = new MNEHelper(LoginAppServer, LoginAppPort, LoginUrlConnectionM3);
            mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3);
            if (!mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3)) {
                System.out.println("Can not login to M3 system");
            }

            String ARS110 = mne.runM3Pgm("ARS110");

            if ((ARS110).equals("")) {
                System.out.println(" ไม่สามารถเปิดโปรแกรม ARS110 ได้");
                JOptionPane.showMessageDialog(null, "ไม่สามารถเปิดโปรแกรม ARS110 ได้", "ไม่สามารถเปิดโปรแกรม ARS110 ได้", JOptionPane.WARNING_MESSAGE);
                mne.closeProgram(ARS110);
            }

            Classgetdata cgd = new Classgetdata();
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();
            String[] InvDate = new String[20], InvAmt = new String[20], InvSupp = new String[20],
                    GenVoucherText = new String[20], SETT_DUEDATE = new String[20], CASH_REF1 = new String[20];
            String[] SETT_CODE = new String[20], SETT_COST = new String[20];
            Double[] SETT_AMTB = new Double[20], SETT_AMTT = new Double[20], SETT_VATC = new Double[20], VAT_C = new Double[20], VAT_UC = new Double[20];
            int i = 0;
            ResultSet rsl = cgd.GetAdvanceDetailM3(Advance);
            Double InvAmtAll = 0.00, AdvAMT = 0.00, BalanceAMT = 0.00;
            String AdvCostc = "";

            while (rsl.next()) {
                AdvAMT = rsl.getDouble("CASH_AMT");
                SETT_CODE[i] = rsl.getString("ACCODE").trim();
                SETT_COST[i] = rsl.getString("SETT_COST").trim();
                SETT_AMTB[i] = rsl.getDouble("SETT_AMTB");
                SETT_AMTT[i] = rsl.getDouble("SETT_AMTT");
                SETT_VATC[i] = rsl.getDouble("SETT_VATC");
                VAT_C[i] = rsl.getDouble("VAT_C");
                VAT_UC[i] = rsl.getDouble("VAT_UC");
                InvAmtAll += rsl.getDouble("SETT_AMTT");
                AdvCostc = rsl.getString("CASH_COST").trim();
                i++;
            }

            if (mne.panel.equals("ARS110/B")) {
                System.out.println("ARS110/B");

                mne.setField("CMDTP", "KEY");
//                mne.setField("APPSRV", "BKRMVXM3.BANGKOKRANCH.COM:16303");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WAFNCN", "400");
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "1");
                mne.setField("SELROWS", "R1");
                mne.setField("FCS", "WAFNCN");
                mne.setField("WAFNCN", "400");
                mne.selectOption("1");

            }

            if (mne.panel.equals("ARS112/E")) {
                System.out.println("ARS112/E");
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("FCS", "WEAIT2");
                mne.setField("WEACDT", DateAccountSettle);
                mne.setField("WEAIT1", SETT_CODE[0]); // Accode
                mne.setField("WEAIT2", SETT_COST[0]); // Cost center
                mne.setField("WECUAM", AdvAMT); // InvAmt
                System.out.println(AdvAMT);
                mne.setField("WEAIT4", Staffcode);// Cost center Ref1
                mne.setField("WEAIT5", Advance);
                mne.setField("WEAIT6", "*BLANK");
                mne.pressKey(MNEProtocol.KeyEnter);
                if (mne.getMsg() != null) {
                    JOptionPane.showMessageDialog(null, mne.getMsg().toString());
                }
            }

            if (mne.panel.equals("ARS110/F1")) {
                System.out.println("ARS110/F1");

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WFPYNO", Staffcode);//Staffcode
                mne.setField("FCS", "WFCINO");
                mne.setField("WFQTTP", "1");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "11");
                mne.setField("WFPYNO", Staffcode);//Staffcode
                mne.setField("FCS", "WFCINO");
                mne.setField("SELROWS", "R1");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.selectOption("11");

            }
            /// Partail 
            if (mne.panel.equals("ARS110/H")) {
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("FCS", "WHCUAM");
                mne.setField("WHCUAM", AdvAMT);
                mne.pressKey(MNEProtocol.KeyEnter);
            }

            Double CreditInv1 = 0.00;
            CreditInv1 = (AdvAMT - SETT_AMTT[0]) * -1;
//            CreditInv1 = (InvAmtAll - SETT_AMTT[0]) * -1;

            if (InvAmtAll < AdvAMT) {

                String CASH_REAMT = "0.00";
                String CASH_RETOBANK = "";
                if (rdoReturnSCB.isSelected() == true) {
                    CASH_RETOBANK = "1AA2105";
                } else if (rdoReturnKBANK.isSelected() == true) {
                    CASH_RETOBANK = "1AA2110";
                }

                BalanceAMT = AdvAMT - InvAmtAll;

                if (mne.panel.equals("ARS110/F1")) {
                    System.out.println("ARS110/F1");
                    mne.setField("CMDTP", "KEY");
                    mne.setField("CMDVAL", "ENTER");
                    mne.setField("WFPYNO", Staffcode);//Staffcode
                    mne.setField("WFSLOP", "41");
                    mne.setField("FCS", "WFSLOP");
                    mne.setField("WFQTTP", "1");// Advance REF2
                    mne.setField("WFCINO", Advance);// Advance REF2
                    mne.pressKey(MNEProtocol.KeyEnter);
                }

                if (mne.panel.equals("ARS110/G1")) {

                    mne.setField("CMDVAL", "ENTER");
                    mne.setField("WGCUAM", BalanceAMT);
                    mne.setField("WGAIT3", "");
                    mne.setField("CMDTP", "KEY");
                    mne.setField("FCS", "WGAIT3");
                    mne.setField("WGVTXT", "Advance Clearing");
                    mne.setField("WGAIT4", Staffcode);
                    mne.setField("WGAIT5", Advance);
                    mne.setField("WGAIT6", "*BLANK");
                    mne.setField("WGAIT1", CASH_RETOBANK);
                    mne.setField("WGAIT2", "");
                    mne.pressKey(MNEProtocol.KeyEnter);

                }
            };

            if (i > 1) {

                for (i = 0; i < 12; i++) {

                    if (SETT_CODE[i] != null) {

                        if (mne.panel.equals("ARS110/F1")) {
                            System.out.println("ARS110/F1");
                            mne.setField("CMDTP", "KEY");
                            mne.setField("CMDVAL", "ENTER");
                            mne.setField("WFPYNO", Staffcode);//Staffcode
                            mne.setField("WFSLOP", "41");
                            mne.setField("FCS", "WFSLOP");
                            mne.setField("WFQTTP", "1");// Advance REF2
                            mne.setField("WFCINO", Advance);// Advance REF2
                            mne.pressKey(MNEProtocol.KeyEnter);
                        }

                        if (mne.panel.equals("ARS110/G1")) {
                            SETT_AMTT[0] = CreditInv1;
                            mne.setField("CMDVAL", "ENTER");
                            mne.setField("WGCUAM", SETT_AMTT[i]);
                            mne.setField("WGAIT3", "");
                            mne.setField("CMDTP", "KEY");
                            mne.setField("FCS", "WGAIT3");
                            mne.setField("WGAIT4", Staffcode);
                            mne.setField("WGAIT5", Advance);
                            mne.setField("WGAIT6", "*BLANK");
                            mne.setField("WGVTXT", "Advance Clearing");
                            mne.setField("WGAIT1", SETT_CODE[i]);
                            mne.setField("WGAIT2", SETT_COST[i]);
                            mne.pressKey(MNEProtocol.KeyEnter);

                        }
                    } else {
                        System.out.println("Break");
                        break;
                    }
                }
            }

            if (mne.panel.equals("ARS110/F1")) {
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "3");
                mne.setField("FCS", "WFCINO");
                mne.pressKey(MNEProtocol.KeyF03); // 92110012
                System.out.println(mne.getMsg());
            }

            ClassSetdata csd = new ClassSetdata();

            String[] Vouchermss = mne.getMsg().toString().split(" ");
            String Voucher = Vouchermss[2].trim();
            msg = mne.getMsg();
            if (Voucher.length() == 8) {
                csd.UpdateVoucher_SettleARS(Advance, Voucher);
            }

            mne.closeProgram(ARS110);
            return msg;
        } catch (Exception ex) {
            return msg;
        }

    }

    public String Offset_Case_Normal(String Advance, String StaffCode, Double AdvAMT, String DateAccountSettle) {
        String msg = "";

        try {
            MNEHelper mne = new MNEHelper(LoginAppServer, LoginAppPort, LoginUrlConnectionM3);
            mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3);
            if (!mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3)) {
                System.out.println("Can not login to M3 system");
            }
            String ARS110 = mne.runM3Pgm("ARS110");

            if ((ARS110).equals("")) {
                System.out.println(" ไม่สามารถเปิดโปรแกรม ARS110 ได้");
                JOptionPane.showMessageDialog(null, "ไม่สามารถเปิดโปรแกรม ARS110 ได้", "ไม่สามารถเปิดโปรแกรม ARS110 ได้", JOptionPane.WARNING_MESSAGE);
                mne.closeProgram(ARS110);
            }

            if (mne.panel.equals("ARS110/B")) {
                System.out.println(mne.panel);

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WAFNCN", "400");
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "1");
                mne.setField("SELROWS", "R1");
                mne.setField("FCS", "WAFNCN");
                mne.setField("WAFNCN", "400");
                mne.selectOption("1");

            }

            if (mne.panel.equals("ARS112/E")) {
                System.out.println("ARS112/E");
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WEAIT4", StaffCode);
                mne.setField("WEACDT", DateAccountSettle);
                mne.setField("FCS", "WEACDT");
                mne.setField("WEAIT1", "8AA1301");
                mne.setField("WEAIT5", Advance);
                mne.setField("WECUAM", AdvAMT);// 
                mne.pressKey(MNEProtocol.KeyEnter);
                if (mne.getMsg() != null) {
                    JOptionPane.showMessageDialog(null, mne.getMsg().toString());
                }
            }

            if (mne.panel.equals("ARS110/F1")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WFPYNO", StaffCode);
                mne.setField("FCS", "WFPYNO");
                mne.setField("WFCINO", Advance);
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "1");
                mne.setField("WFPYNO", StaffCode);
                mne.setField("FCS", "WFCINO");
                mne.setField("WFQTTP", "1");
                mne.setField("SELROWS", "R1");
                mne.setField("WFCINO", Advance);
                mne.selectOption("1");

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "F3");
                mne.setField("WFPYNO", StaffCode);
                mne.setField("FCS", "WFCINO");
                mne.setField("WFCINO", Advance);
                mne.pressKey(MNEProtocol.KeyF3);
                msg = mne.getMsg();
            }

            msg = mne.getMsg();
            ClassSetdata csd = new ClassSetdata();

            String[] Vouchermss = mne.getMsg().toString().split(" ");
            String Voucher = Vouchermss[2].trim();

            if (Voucher.length() == 8) {
                csd.UpdateVoucher_SettleARS(Advance, Voucher);
            }

            mne.closeProgram(ARS110);
            return msg;
        } catch (Exception ex) {

            return msg;
        }
    }

    public String callARS100_Offset_CashFront(String Advance, String StaffCode, String DateAccountSettle) {
        String msg = "";

        try {
            MNEHelper mne = new MNEHelper(LoginAppServer, LoginAppPort, LoginUrlConnectionM3);
            mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3);
            if (!mne.logInToM3(Integer.valueOf(LoginCono), LoginDivision, UserM3, PasswordM3)) {
                System.out.println("Can not login to M3 system");
            }
            String ARS110 = mne.runM3Pgm("ARS110");

            if ((ARS110).equals("")) {
                System.out.println(" ไม่สามารถเปิดโปรแกรม ARS110 ได้");
                JOptionPane.showMessageDialog(null, "ไม่สามารถเปิดโปรแกรม ARS110 ได้", "ไม่สามารถเปิดโปรแกรม ARS110 ได้", JOptionPane.WARNING_MESSAGE);
                mne.closeProgram(ARS110);
            }

            if (mne.panel.equals("ARS110/B")) {
                System.out.println(mne.panel);

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WAFNCN", "400");
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "1");
                mne.setField("SELROWS", "R1");
                mne.setField("FCS", "WAFNCN");
                mne.setField("WAFNCN", "400");
                mne.selectOption("1");

            }

            if (mne.panel.equals("ARS112/E")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("FCS", "WEAIT2");
                mne.setField("WEAIT1", "9BC9999"); // Accode  8AA1301
                mne.setField("WEAIT2", ""); // Cost center
                mne.setField("WECUAM", ""); // InvAmt
                mne.setField("WEACDT", DateAccountSettle);
                mne.setField("WEAIT4", StaffCode);// Cost center Ref1
                mne.setField("WEAIT5", Advance);
                mne.setField("WEAIT6", "*BLANK");
                mne.pressKey(MNEProtocol.KeyEnter);
                if (mne.getMsg() != null) {
                    System.out.println(mne.getMsg());
                }
            }

            if (mne.panel.equals("ARS110/F1")) {
                System.out.println(mne.panel);

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WFPYNO", StaffCode);//Staffcode
                mne.setField("FCS", "WFPYNO");
                mne.setField("WFQTTP", "1");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.pressKey(MNEProtocol.KeyEnter);

                mne.setField("CMDTP", "LSTOPT");
                mne.setField("CMDVAL", "1");
                mne.setField("WFPYNO", StaffCode);//Staffcode
                mne.setField("FCS", "WFCINO");
                mne.setField("SELROWS", "R1");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.selectOption("1");

                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "ENTER");
                mne.setField("WFPYNO", StaffCode);//Staffcode
                mne.setField("FCS", "WFSLOP");
                mne.setField("WFSLOP", "17");
                mne.setField("WFCINO", Advance);// Advance REF2
                mne.pressKey(MNEProtocol.KeyEnter);

                if (mne.getMsg() != null) {
                    System.out.println(mne.getMsg());
                }

            }
            ClassCheckDataProgram ccdp = new ClassCheckDataProgram();

            Classgetdata cgd = new Classgetdata();
            ResultSet rsl = cgd.GetAdvanceDetailM3(Advance);
            String[] SETT_COST = new String[20], SETT_AMTB = new String[20], ACCODE = new String[20], ServiceCode = new String[20], VATCODE = new String[20], InvNO = new String[20], InvAccDate = new String[20],
                    InvDate = new String[20], InvAmt = new String[20], InvSupp = new String[20], GenVoucherText = new String[20], SETT_DUEDATE = new String[20],
                    EATX40 = new String[20], SETT_RETT = new String[20];
            Double[] VAT_C = new Double[20], VAT_UC = new Double[20];
            Double InvAmtAll = 0.00, AdvAMT = 0.00;
            int i = 0;
            int x = 0;
            while (rsl.next()) {
                VAT_C[i] = ccdp.Double2digitReturn(rsl.getDouble("VAT_C"));
                VAT_UC[i] = ccdp.Double2digitReturn(rsl.getDouble("VAT_UC"));
                SETT_COST[i] = rsl.getString("SETT_COST").trim();
                SETT_AMTB[i] = rsl.getString("SETT_AMTB").trim();
                ACCODE[i] = rsl.getString("ACCODE").trim();
                VATCODE[i] = rsl.getString("VATCODE").trim();
                InvNO[i] = rsl.getString("SETT_INVC").trim();
                InvAccDate[i] = ccdp.SubDateForM3(rsl.getString("SETT_INVD").trim());
                InvDate[i] = ccdp.SubDateForM3(rsl.getString("SETT_INVD").trim());
                InvAmt[i] = rsl.getString("SETT_AMTT").trim();
                InvSupp[i] = rsl.getString("SETT_SUPP").trim();
                GenVoucherText[i] = rsl.getString("SETT_VCTXT").trim();
                if (!rsl.getString("SETT_DUEDT").trim().equals("0")) {
                    SETT_DUEDATE[i] = ccdp.SubDateForM3(rsl.getString("SETT_DUEDT").trim());
                }
                EATX40[i] = rsl.getString("EATX40").trim();
                InvAmtAll += rsl.getDouble("SETT_AMTT");
                AdvAMT = rsl.getDouble("CASH_AMT");
                i++;
            }
            for (x = 0; x < i;) {
                if (EATX40[x].equalsIgnoreCase("UCN")) {
                    x++;
                    continue;
                } else {
                    if (mne.panel.equals("APS120/F1")) {
                        System.out.println(mne.panel);
                        mne.setField("CMDTP", "KEY");
                        mne.setField("CMDVAL", "ENTER");
                        mne.setField("FCS", "WFSPYN");
                        mne.setField("WFQTTP", "1");
                        mne.setField("WFSPYN", InvSupp[x]); //SETT_SUPP
                        mne.setField("W1SINO", InvNO[x]);// Advance REF2
                        mne.pressKey(MNEProtocol.KeyEnter);

                        mne.setField("CMDTP", "LSTOPT");
                        mne.setField("CMDVAL", "1");
                        mne.setField("FCS", "WFSPYN");
                        mne.setField("W1SINO", InvNO[x]);
                        mne.setField("WFQTTP", "1"); //SETT_SUPP
                        mne.setField("WFSPYN", InvSupp[x]);// Advance REF2
                        mne.setField("SELROWS", "R1");// Advance REF2
                        mne.selectOption("1");
                        if (mne.getMsg() != null) {
                            System.out.println(mne.getMsg());
                        }
                    }
                    x++;
                }

            }

            if (mne.panel.equals("APS120/F1")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "F3");
                mne.setField("FCS", "WFSPYN");
                mne.pressKey(MNEProtocol.KeyF03); // 92110012

                if (mne.getMsg() != null) {
                    System.out.println(mne.getMsg());
                }
            }

            ////
            if (AdvAMT > InvAmtAll || AdvAMT < InvAmtAll) {
                String CASH_RETOBANK = "";
                String VCTXT = "";
                if (rdoReturnSCB.isSelected() == true) {
                    CASH_RETOBANK = "1AA2105";
                    VCTXT = "Return to 1AA2105";
                } else if (rdoReturnKBANK.isSelected() == true) {
                    CASH_RETOBANK = "1AA2110";
                    VCTXT = "Return to 1AA2110";
                } else {
                    CASH_RETOBANK = "8AA1302";
                    VCTXT = "Refund to 8AA1302";
                }
                Double WHTAX = 0.00;

                ResultSet rs2 = cgd.Cal_WHTAX(Advance);
                while (rs2.next()) {
                    WHTAX += rs2.getDouble("WHTAX");
                };
                WHTAX = ccdp.Double2digitReturn(WHTAX);
                Double Balance = (AdvAMT + WHTAX) - InvAmtAll;
                WHTAX = WHTAX * -1;
                if (mne.panel.equals("ARS110/F1")) {
                    mne.setField("CMDTP", "KEY");
                    mne.setField("CMDVAL", "ENTER");
                    mne.setField("WFPYNO", StaffCode);//Staffcode
                    mne.setField("FCS", "WFSLOP");
                    mne.setField("WFSLOP", "41");
                    mne.setField("WFQTTP", "1");
                    mne.setField("WFCINO", Advance);// Advance REF2
                    mne.pressKey(MNEProtocol.KeyEnter);
                }

                if (mne.panel.equals("ARS110/G1")) {
                    System.out.println(mne.getMsg());
                    mne.setField("WGCUAM", Balance);
                    mne.setField("WGAIT3", "");
//                    mne.setField("WGAIT7", "ARS110");
                    mne.setField("WGVTCD", "");
                    mne.setField("WGAIT4", StaffCode);
                    mne.setField("FCS", "WGVTXT");
                    mne.setField("WGVTXT", VCTXT);
//                    mne.setField("WGECAR", "61");
                    mne.setField("WGAIT5", Advance); // REF2
                    mne.setField("WGAIT1", CASH_RETOBANK); // CODE BANK
                    mne.setField("WGFTCO", "TH");
                    mne.setField("WGAIT6", "*BLANK");
                    mne.setField("WGAIT2", ""); //COSTC.

                    mne.setField("CMDTP", "KEY");
                    mne.setField("CMDVAL", "ENTER");
                    mne.setField("WGCUAM", Balance);
                    mne.setField("WGAIT3", "");
                    mne.setField("WGVTCD", "");
                    mne.setField("WGAIT4", StaffCode);
                    mne.setField("FCS", "WGVTXT");
                    mne.setField("WGVTXT", VCTXT);
                    mne.setField("WGAIT5", Advance); // REF2
                    mne.setField("WGAIT1", CASH_RETOBANK); // CODE BANK
                    mne.setField("WGFTCO", "TH");
                    mne.setField("WGAIT6", "*BLANK");
                    mne.setField("WGAIT2", ""); //COSTC.

                    mne.pressKey(MNEProtocol.KeyEnter);
                }

                if (mne.panel.equals("ARS110/F1")) {
                    mne.setField("CMDTP", "KEY");
                    mne.setField("CMDVAL", "ENTER");
                    mne.setField("WFPYNO", StaffCode);//Staffcode
                    mne.setField("FCS", "WFSLOP");
                    mne.setField("WFSLOP", "41");
                    mne.setField("WFQTTP", "1");
                    mne.setField("WFCINO", Advance);// Advance REF2
                    mne.pressKey(MNEProtocol.KeyEnter);
                }

                if (mne.panel.equals("ARS110/G1")) {
                    System.out.println(mne.getMsg());
                    mne.setField("WGCUAM", WHTAX);
                    mne.setField("WGAIT3", "");
                    mne.setField("WGVTCD", "");
                    mne.setField("WGAIT4", StaffCode);
                    mne.setField("FCS", "WGVTXT");
                    mne.setField("WGVTXT", "");
                    mne.setField("WGAIT5", Advance); // REF2
                    mne.setField("WGAIT1", "2AF1101"); // CODE BANK
                    mne.setField("WGFTCO", "TH");
                    mne.setField("WGAIT6", "*BLANK");
                    mne.setField("WGAIT2", ""); //COSTC.

                    mne.setField("CMDTP", "KEY");
                    mne.setField("CMDVAL", "ENTER");
                    mne.setField("WGCUAM", WHTAX);
                    mne.setField("WGAIT3", "");
                    mne.setField("WGVTCD", "");
                    mne.setField("WGAIT4", StaffCode);
                    mne.setField("FCS", "WGVTXT");
                    mne.setField("WGVTXT", "");
                    mne.setField("WGAIT5", Advance); // REF2
                    mne.setField("WGAIT1", "2AF1101"); // CODE BANK
                    mne.setField("WGFTCO", "TH");
                    mne.setField("WGAIT6", "*BLANK");
                    mne.setField("WGAIT2", ""); //COSTC.
                    mne.pressKey(MNEProtocol.KeyEnter);
                }
            }

////
            if (mne.panel.equals("ARS110/F1")) {
                System.out.println(mne.panel);
                mne.setField("CMDTP", "KEY");
                mne.setField("CMDVAL", "F3");
                mne.setField("FCS", "WFSPYN");
                mne.pressKey(MNEProtocol.KeyF03); // 92110012

                if (mne.getMsg() != null) {
                    System.out.println(mne.getMsg());
                }
            }
            ClassSetdata csd = new ClassSetdata();

            String[] Vouchermss = mne.getMsg().toString().split(" ");
            String Voucher = Vouchermss[2].trim();

            if (Voucher.length() == 8) {
                csd.UpdateVoucher_SettleARS(Advance, Voucher);
            }

            msg = mne.getMsg();
            mne.closeProgram(ARS110);
            return msg;
        } catch (Exception ex) {
            return msg;
        }
    }

}
