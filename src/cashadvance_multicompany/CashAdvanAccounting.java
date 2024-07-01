/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashadvance_multicompany;

import static cashadvance_multicompany.frmLogin.LoginCompanyName;
import static cashadvance_multicompany.frmLogin.LoginUsername;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author aticha
 */
public class CashAdvanAccounting extends javax.swing.JFrame {

    public static String CashAdvanceRequestNo = "";
    DefaultTableModel model;
    DefaultTableModel model2;
    private JComboBox countryCombo;

    /**
     * Creates new form CashAdvanAccounting
     */
    public CashAdvanAccounting() {
        initComponents();
        jTabbedPane2.hide();
        
        rdoReturnKBANK.setVisible(false);
        SetNewCashAdvanAccounting();
        if (!CashAdvanceRequestNo.equals("")) {
            //CHECK STEP STATUS  CASH_STAT
            Classgetdata cgd = new Classgetdata();

            String CASH_STAT = cgd.GetDataCashAdvanStatus_WithCashNo(CashAdvanceRequestNo);
            if (CASH_STAT.equals("20")) {
                Set_ActiveStep1(true);
                Set_ActiveStep2(false);
                Set_ActiveStep3(false);
                SetFormDataWithSTEP1_Cash(CashAdvanceRequestNo);
                Get_DetailGridAcc(CashAdvanceRequestNo);
                txtCheckedBy.setText(LoginUsername);
            }
            if (CASH_STAT.equals("30")) {
                Set_ActiveStep1(false);
                Set_ActiveStep2(true);
                Set_ActiveStep3(false);

                SetFormDataWithSTEP1_Cash(CashAdvanceRequestNo);
                SetFormDataWithSTEP2_Cash(CashAdvanceRequestNo);
                txtrecord1.setText(LoginUsername);
            }
//            if (CASH_STAT.equals("45")) {
//                Set_ActiveStep1(false);
//                Set_ActiveStep2(false);
//                Set_ActiveStep3(false);
//                Set_ActiveStep2_CheckSettle(true);
//                SetFormDataWithSTEP1_Cash(CashAdvanceRequestNo);
//                SetFormDataWithSTEP2_Cash(CashAdvanceRequestNo);
//                SetFormDataWithCheckSettle_Cash(CashAdvanceRequestNo);
//                txtRefundBy3.setText(LoginUsername);
//
//            }

            if (CASH_STAT.equals("50")) {
                Set_ActiveStep1(false);
                Set_ActiveStep2(false);
                Set_ActiveStep3(true);
                SetFormDataWithSTEP1_Cash(CashAdvanceRequestNo);
                SetFormDataWithSTEP2_Cash(CashAdvanceRequestNo);
                txtRefundBy2.setText(LoginUsername);
                try {
                    countryCombo = new JComboBox();
                    countryCombo.removeAllItems();
                    countryCombo.addItem("");
                    Classgetdata getdataDatabase = new Classgetdata();
                    AutoCompleteDecorator.decorate(countryCombo);
//                    while (rs1.next()) {
//                        countryCombo.addItem(rs1.getString("idsuno").trim() + " : " + rs1.getString("idsunm").trim());
//                    }
//                    rs1.close();
                    TableColumn countryColumn = griddetailacstep5.getColumnModel().getColumn(1);
                    countryColumn.setCellEditor(new DefaultCellEditor(countryCombo));
                } catch (Exception ex) {
                    Logger.getLogger(CashAdvanAccounting.class.getName()).log(Level.SEVERE, null, ex);
                }

                SetFormDataWithSTEP5_Cash(CashAdvanceRequestNo);

            }

        }
    }

    private void SetFormDataWithSTEP5_Cash(String CASH_CANO_) {
        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetDataCashAdvanRequest_HeaderResultData(CASH_CANO_);
        try {
            while (rs.next()) {
                txtAdvanceNo2.setText(rs.getString("CASH_CANO"));
                txtDateAdvance2.setText(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_DATE"))));
                cmbcostcenter2.setSelectedItem(cgd.Get_CostcenterNameWithCode(rs.getString("CASH_COST").trim()));
                cmbstaffcode2.setSelectedItem(cgd.Get_SupplierNameWithCode(rs.getString("CASH_EMPY").trim()));
                txtamount2.setText(rs.getString("CASH_AMT"));
                DateSettlement2.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_STDT")));
                if (rs.getInt("CASH_STDA") != 0) {
                    DateSettlemAccount2.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_STDA")));
                }
                DatePlanSettlemAccount2.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_STDP")));
                cmb_IT1GP_2.setText(cgd.GetExpensSubGroupDescWithCode(rs.getString("CASH_EXPG")));
                String Stattt = "";
                Stattt = rs.getString("CASH_STAT").trim();
                String WHTAX = "";
                WHTAX = rs.getString("CASH_WHTAX").trim();
                String WHTYPE = rs.getString("CASH_WHTYPE").trim();
                txtwhtax1.setText(WHTAX);
                if (WHTAX.equalsIgnoreCase("1")) {
                    radiotaxchec_1.setSelected(true);
                } else if (WHTAX.equalsIgnoreCase("2")) {
                    radiofrontwork_1.setSelected(true);
                    txtwhtax1.setEditable(true);
                } else {
                    radiotaxchec_1.setSelected(false);
                    radiofrontwork_1.setSelected(false);
                    radionontax_1.setSelected(true);

                }

                String Cash_Retobank = rs.getString("CASH_RETOBANK").trim();
                switch (Cash_Retobank) {
                    case "SCB":
                        rdoReturnSCB.setSelected(true);
                        break;
                    case "KBANK":
                        rdoReturnKBANK.setSelected(true);
                        break;
                    case "Petty Cash":
                        rdoPTC.setSelected(true);
                        break;
                    default:
                        rdoTransBank.setSelected(true);
                        ResultSet rsl = cgd.GetDataCashAdvanRequest_BankSupp(Cash_Retobank);
                        if (rsl.next()) {
                            txtBName.setText(rsl.getString("IDSUNM").trim());
                            txtBCode.setText(rsl.getString("IRYRE1").trim());
                        }
                        break;
                }

                if (null != Stattt) {
                    switch (Stattt) {
                        case "50":
                            radionormal3.setSelected(true);
                            radiosubmit3.setSelected(false);
                            break;
                        case "60":
                            radionormal3.setSelected(false);
                            radiosubmit3.setSelected(true);
                            break;
                        case "99":
                            radionormal3.setSelected(false);
                            radiosubmit3.setSelected(false);
                            break;
                        default:
                            break;
                    }
                }
            }
            rs.close();
            Get_DetailGrid(CASH_CANO_);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void Set_ActiveStep1(boolean Status) {
        radionormal.setEnabled(Status);
        radiocancel.setEnabled(Status);
        radiosubmit.setEnabled(Status);
        jRadioshowreport.setEnabled(Status);
        radioreturn.setEnabled(Status);
        btnSave.setEnabled(Status);
        btnCancel.setEnabled(Status);
        radionontax.setEnabled(Status);
        radiofrontwork.setEnabled(Status);

    }

    private void Set_ActiveStep2(boolean Status) {
        radionormal1.setEnabled(Status);
        radiocancel1.setEnabled(Status);
        radiosubmit1.setEnabled(Status);
        //  jRadioshowreport.setEnabled(Status);
        btnSave1.setEnabled(Status);
        btncancel1.setEnabled(Status);
        btchequenumber.setEnabled(Status);
    }

    private void Set_ActiveStep2_CheckSettle(boolean Status) {
        radionormal1.setEnabled(Status);
        radiocancel1.setEnabled(Status);
        radiosubmit1.setEnabled(Status);
        //  jRadioshowreport.setEnabled(Status);
        btnSave1.setEnabled(Status);
        btncancel1.setEnabled(Status);
        btchequenumber.setEnabled(Status);
    }

    private void Set_ActiveStep3(boolean Status) {
        radionormal3.setEnabled(Status);
        radiosubmit3.setEnabled(Status);
        radioreturn3.setEnabled(Status);
        // jRadioshowreport.setEnabled(Status);
        btnsave2.setEnabled(Status);
        btncancel2.setEnabled(Status);
    }

    private void SetFormDataWithSTEP1_Cash(String CASH_CANO_) {
        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();

        ResultSet rs = cgd.GetDataCashAdvanRequest_HeaderResultData(CASH_CANO_);
        try {
            while (rs.next()) {
                txtAdvanceNo.setText(rs.getString("CASH_CANO"));
                txtDateAdvance.setText(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_DATE"))));
                cmbcostcenter.setSelectedItem(cgd.Get_CostcenterNameWithCode(rs.getString("CASH_COST").trim()));
                cmbstaffcode.setSelectedItem(cgd.Get_SupplierNameWithCode(rs.getString("CASH_EMPY").trim()));
                txtcustomer.setText(cgd.Get_customerNameWithCode2(rs.getString("CASH_EMPY").trim()));
                txtpurpose_1.setText(rs.getString("CASH_PPS1"));
                txtpurpose_2.setText(rs.getString("CASH_PPS2"));
                txtpurpose_3.setText(rs.getString("CASH_PPS3"));
                DateOperationFrom.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_OPDF")));
                DateOperationTo.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_OPDT")));
                txtamount.setText(rs.getString("CASH_AMT"));
                txtRefPo.setText(rs.getString("CASH_REF1"));
                txt_PONO.setText(rs.getString("CASH_REF3"));
                DateReceive.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_REDP")));
                DateSettlement.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_STDP")));
                txtCheckedBy.setText(rs.getString("CASH_REQB"));
                if (rs.getString("CASH_PAYT").equalsIgnoreCase("TRANSFER")) {
                    radio_transfer.setSelected(true);
                    radio_cheque.setSelected(false);
                } else {
                    radio_transfer.setSelected(false);
                    radio_cheque.setSelected(true);
                }
//                cmbSupplierADV.setText(cgd.Get_SupplierNameWithCode(rs.getString("CASH_PAYSUP").trim()));
                cmb_IT1GP.setText(cgd.GetExpensSubGroupDescWithCode(rs.getString("CASH_EXPG")));
//                ResultSet Getsupp = cgd.GetDataCashAdvanRequest_BankSupp(rs.getString("CASH_PAYSUP").trim());
//                while (Getsupp.next()) {
//                    txtbranch.setText(Getsupp.getString("IRTFNO"));
//                    txtaccno.setText(Getsupp.getString("IRSUCM"));
//                    txtaccname.setText(Getsupp.getString("IRYRE1"));
//                }
                if (rs.getInt("CASH_TRAD") != 0) {
                    DateAccount.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_TRAD")));
                }
                String Stattt = "";
                Stattt = rs.getString("CASH_STAT").trim();

                String CASH_WHTAX = rs.getString("CASH_WHTYPE").trim();

                if (null != CASH_WHTAX) {
                    switch (CASH_WHTAX) {
                        case "0":
                            radionontax.setSelected(true);
                            radiotaxchec.setSelected(false);
                            radiofrontwork.setSelected(false);
                            break;
                        case "1":
                            radionontax.setSelected(false);
                            radiotaxchec.setSelected(true);
                            radiofrontwork.setSelected(false);
                            break;
                        case "2":
                            radionontax.setSelected(false);
                            radiotaxchec.setSelected(false);
                            radiofrontwork.setSelected(true);
                            break;
                    }
                }

                if (null != Stattt) {
                    switch (Stattt) {
                        case "20":
                            radionormal.setSelected(true);
                            radiocancel.setSelected(false);
                            radiosubmit.setSelected(false);
                            radioreturn.setSelected(false);
                            break;
                        case "30":
                            radionormal.setSelected(false);
                            radiocancel.setSelected(false);
                            radiosubmit.setSelected(true);
                            radioreturn.setSelected(false);
                            break;
                        case "99":
                            radionormal.setSelected(false);
                            radiocancel.setSelected(true);
                            radiosubmit.setSelected(false);
                            radioreturn.setSelected(false);
                            break;
                        default:
                            break;
                    }
                }
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void SetFormDataWithSTEP2_Cash(String CASH_CANO_) {
        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetDataCashAdvanRequest_HeaderResultData(CASH_CANO_);
        try {
            while (rs.next()) {
                txtAdvanceNo1.setText(rs.getString("CASH_CANO"));
                txtDateAdvance1.setText(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_DATE"))));

                cmbcostcenter1.setSelectedItem(cgd.Get_CostcenterNameWithCode(rs.getString("CASH_COST").trim()));
                cmbstaffcode1.setSelectedItem(cgd.Get_SupplierNameWithCode(rs.getString("CASH_EMPY").trim()));
                txtpurpose1_1.setText(rs.getString("CASH_PPS1"));
                txtpurpose1_2.setText(rs.getString("CASH_PPS2"));
                txtpurpose1_3.setText(rs.getString("CASH_PPS3"));
                DateOperationFrom1.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_OPDF")));
                DateOperationTo1.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_OPDT")));
                txtamount1.setText(rs.getString("CASH_AMT"));
                txtRefPo1.setText(rs.getString("CASH_REF1"));
                txt_PONO1.setText(rs.getString("CASH_REF3"));
                DatePlanReceive1.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_REDP")));
                DatePlanSettlement1.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_STDP")));
                DateAccount1.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_TRAD")));
                // DateCheqDate1.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_DUED")));
                DateReceive1.setDate(checkdataprogram.GetDecmalTodate(Integer.parseInt(checkdataprogram.GetDateDecmalCurrenttime())));
//                txtbank1.setText(rs.getString("CASH_BANK"));
//                //    txtrecord1.setText(rs.getString("CASH_RCRA"));
                //    txtrecord1.setText(rs.getString("CASH_RCRA"));
//                txtchqno1.setText(rs.getString("CASH_CHQN"));
                txtbank1.setText(rs.getString("CASH_BANK"));
                if (rs.getInt("CASH_DUED") != 0) {
                    DateCheqDate1.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_DUED")));
                }
                txtchqno1.setText(rs.getString("CASH_CHQN"));

                String Stattt = "";
                Stattt = rs.getString("CASH_STAT").trim();

                if (null != Stattt) {
                    switch (Stattt) {
                        case "30":
                            radionormal1.setSelected(true);
                            radiocancel1.setSelected(false);
                            radiosubmit1.setSelected(false);
                            break;
                        case "40":
                            radionormal1.setSelected(false);
                            radiocancel1.setSelected(false);
                            radiosubmit1.setSelected(true);
                            break;
                        case "99":
                            radionormal1.setSelected(false);
                            radiocancel1.setSelected(true);
                            radiosubmit1.setSelected(false);
                            break;
                        default:
                            break;
                    }
                }

            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void SetFormDataWithCheckSettle_Cash(String CASH_CANO_) {
        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetDataCashAdvanRequest_HeaderResultData(CASH_CANO_);
        try {
            while (rs.next()) {
                txtAdvanceNo3.setText(rs.getString("CASH_CANO"));
                txtDateAdvance3.setText(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_DATE"))));
                cmbcostcenter3.setText(cgd.Get_CostcenterNameWithCode(rs.getString("CASH_COST").trim()));
                cmbstaffcode3.setText(cgd.Get_SupplierNameWithCode(rs.getString("CASH_EMPY").trim()));
                txtamount3.setText(rs.getString("CASH_AMT"));
                DateSettlement3.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_STDT")));

                DatePlanSettlemAccount3.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CASH_STDP")));
                cmb_IT1GP_3.setText(cgd.GetExpensSubGroupDescWithCode(rs.getString("CASH_EXPG")));
                String Stattt = "";
                Stattt = rs.getString("CASH_STAT").trim();
                String WHTAX = "";
                WHTAX = rs.getString("CASH_WHTAX").trim();
                String WHTYPE = rs.getString("CASH_WHTYPE").trim();
                txtwhtax2.setText(WHTYPE);
                if (WHTAX.equalsIgnoreCase("1")) {
                    radiotaxchec_2.setSelected(true);
                    radionontax_2.setSelected(false);
                } else if (WHTAX.equalsIgnoreCase("2")) {
                    radiofrontwork_2.setSelected(false);
                    txtwhtax2.setEditable(true);
                    radionontax_2.setSelected(false);
                } else {
                    radiotaxchec_2.setSelected(false);
                    radiofrontwork_2.setSelected(false);
                    radionontax_2.setSelected(true);
                }

                String Cash_Retobank = rs.getString("CASH_RETOBANK").trim();
                switch (Cash_Retobank) {
                    case "SCB":
                        rdoReturnSCB1.setSelected(true);
                        break;
                    case "KBANK":
                        rdoReturnKBANK1.setSelected(true);
                        break;
                    case "Petty Cash":
                        rdoPTC1.setSelected(true);
                        break;
                    default:
                        rdoTransBank1.setSelected(true);
                        ResultSet rsl = cgd.GetDataCashAdvanRequest_BankSupp(Cash_Retobank);
                        if (rsl.next()) {
                            txtBName1.setText(rsl.getString("IDSUNM").trim());
                            txtBCode1.setText(rsl.getString("IRYRE1").trim());
                        }
                        break;

                }

                if (null != Stattt) {
                    switch (Stattt) {
                        case "45":
                            radionormal4.setSelected(true);
                            radiosubmit4.setSelected(false);
                            break;
                        case "50":
                            radionormal4.setSelected(false);
                            radiosubmit4.setSelected(true);
                            break;
                        case "99":
                            radionormal4.setSelected(false);
                            radiosubmit4.setSelected(false);
                            break;
                        default:
                            break;
                    }
                }
            }
            rs.close();
            Get_DetailGrid_StepCheck(CASH_CANO_);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbcostcenter = new javax.swing.JComboBox<>();
        cmbstaffcode = new javax.swing.JComboBox<>();
        DateAccount = new org.jdesktop.swingx.JXDatePicker();
        DateOperationTo = new org.jdesktop.swingx.JXDatePicker();
        txtpurpose_3 = new java.awt.TextField();
        txtCheckedBy = new java.awt.TextField();
        DateOperationFrom = new org.jdesktop.swingx.JXDatePicker();
        DateReceive = new org.jdesktop.swingx.JXDatePicker();
        txtamount = new java.awt.TextField();
        txtRefPo = new java.awt.TextField();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        txtpurpose_1 = new java.awt.TextField();
        txtpurpose_2 = new java.awt.TextField();
        jLabel21 = new javax.swing.JLabel();
        txtAdvanceNo = new java.awt.TextField();
        jLabel22 = new javax.swing.JLabel();
        txtDateAdvance = new java.awt.TextField();
        radioreturn = new javax.swing.JRadioButton();
        radionormal = new javax.swing.JRadioButton();
        radiosubmit = new javax.swing.JRadioButton();
        radiocancel = new javax.swing.JRadioButton();
        jLabel23 = new javax.swing.JLabel();
        DateSettlement = new org.jdesktop.swingx.JXDatePicker();
        jRadioshowreport = new javax.swing.JRadioButton();
        txtcustomer = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        cmb_IT1GP = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        radionontax = new javax.swing.JRadioButton();
        radiofrontwork = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        griddetailac = new javax.swing.JTable();
        btn_preview = new javax.swing.JButton();
        radiotaxchec = new javax.swing.JRadioButton();
        jLabel60 = new javax.swing.JLabel();
        txt_PONO = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        radio_transfer = new javax.swing.JRadioButton();
        radio_cheque = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        cmbcostcenter1 = new javax.swing.JComboBox<>();
        cmbstaffcode1 = new javax.swing.JComboBox<>();
        DateAccount1 = new org.jdesktop.swingx.JXDatePicker();
        DateOperationTo1 = new org.jdesktop.swingx.JXDatePicker();
        txtpurpose1_3 = new java.awt.TextField();
        txtrecord1 = new java.awt.TextField();
        DateOperationFrom1 = new org.jdesktop.swingx.JXDatePicker();
        DatePlanReceive1 = new org.jdesktop.swingx.JXDatePicker();
        txtamount1 = new java.awt.TextField();
        txtRefPo1 = new java.awt.TextField();
        btncancel1 = new javax.swing.JButton();
        btnSave1 = new javax.swing.JButton();
        txtpurpose1_1 = new java.awt.TextField();
        txtpurpose1_2 = new java.awt.TextField();
        jLabel34 = new javax.swing.JLabel();
        txtAdvanceNo1 = new java.awt.TextField();
        jLabel35 = new javax.swing.JLabel();
        txtDateAdvance1 = new java.awt.TextField();
        radionormal1 = new javax.swing.JRadioButton();
        radiosubmit1 = new javax.swing.JRadioButton();
        radiocancel1 = new javax.swing.JRadioButton();
        jLabel36 = new javax.swing.JLabel();
        DatePlanSettlement1 = new org.jdesktop.swingx.JXDatePicker();
        jLabel37 = new javax.swing.JLabel();
        txtchqno1 = new java.awt.TextField();
        jLabel38 = new javax.swing.JLabel();
        txtbank1 = new java.awt.TextField();
        jLabel39 = new javax.swing.JLabel();
        DateReceive1 = new org.jdesktop.swingx.JXDatePicker();
        txtchqno2 = new java.awt.TextField();
        jLabel40 = new javax.swing.JLabel();
        DateCheqDate1 = new org.jdesktop.swingx.JXDatePicker();
        btchequenumber = new javax.swing.JButton();
        jLabel73 = new javax.swing.JLabel();
        txt_PONO1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cmbstaffcode2 = new javax.swing.JComboBox<>();
        txtAdvanceNo2 = new java.awt.TextField();
        txtDateAdvance2 = new java.awt.TextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbcostcenter2 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        DateSettlemAccount2 = new org.jdesktop.swingx.JXDatePicker();
        btncancel2 = new javax.swing.JButton();
        btnsave2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtRefundBy2 = new java.awt.TextField();
        radioreturn3 = new javax.swing.JRadioButton();
        radionormal3 = new javax.swing.JRadioButton();
        radiosubmit3 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        griddetailacstep5 = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        DatePlanSettlemAccount2 = new org.jdesktop.swingx.JXDatePicker();
        jLabel20 = new javax.swing.JLabel();
        DateSettlement2 = new org.jdesktop.swingx.JXDatePicker();
        cmb_IT1GP_2 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtamount2 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtamtbfvat1 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtvat1 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtamt1 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        txtwhtax1 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtreturn1 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtrefund1 = new javax.swing.JTextField();
        rdoPTC = new javax.swing.JRadioButton();
        rdoReturnSCB = new javax.swing.JRadioButton();
        rdoReturnKBANK = new javax.swing.JRadioButton();
        rdoTransBank = new javax.swing.JRadioButton();
        txtBCode = new javax.swing.JTextField();
        txtBName = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        radiotaxchec_1 = new javax.swing.JRadioButton();
        radiofrontwork_1 = new javax.swing.JRadioButton();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        btn_preview1 = new javax.swing.JButton();
        radionontax_1 = new javax.swing.JRadioButton();
        lblCompanyName = new java.awt.Label();
        label17 = new java.awt.Label();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        panel1 = new java.awt.Panel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtAdvanceNo3 = new java.awt.TextField();
        txtDateAdvance3 = new java.awt.TextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        btncancel3 = new javax.swing.JButton();
        btnsave3 = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        txtRefundBy3 = new java.awt.TextField();
        radioreturn4 = new javax.swing.JRadioButton();
        radionormal4 = new javax.swing.JRadioButton();
        radiosubmit4 = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        griddetailacstep6 = new javax.swing.JTable();
        DatePlanSettlemAccount3 = new org.jdesktop.swingx.JXDatePicker();
        jLabel61 = new javax.swing.JLabel();
        DateSettlement3 = new org.jdesktop.swingx.JXDatePicker();
        cmb_IT1GP_3 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        txtamount3 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        txtamtbfvat2 = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        txtvat2 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        txtamt2 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        txtwhtax2 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        txtreturn2 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        txtrefund2 = new javax.swing.JTextField();
        rdoPTC1 = new javax.swing.JRadioButton();
        rdoReturnSCB1 = new javax.swing.JRadioButton();
        rdoReturnKBANK1 = new javax.swing.JRadioButton();
        rdoTransBank1 = new javax.swing.JRadioButton();
        txtBCode1 = new javax.swing.JTextField();
        txtBName1 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        radiotaxchec_2 = new javax.swing.JRadioButton();
        radionontax_2 = new javax.swing.JRadioButton();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        cmbcostcenter3 = new javax.swing.JTextField();
        cmbstaffcode3 = new javax.swing.JTextField();
        radiofrontwork_2 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(800, 800));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(800, 800));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 800));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("To");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(330, 110, 30, 17);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Customer");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 50, 70, 17);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Cost Center");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 80, 89, 17);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Acct Date");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 350, 80, 17);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Operation Date");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 110, 110, 17);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Purpose");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 150, 110, 17);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Paymeny type ");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(270, 250, 110, 20);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Received  Date");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(320, 320, 110, 20);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Settlement Date");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(10, 320, 110, 20);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Ref. PO No.");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(270, 280, 80, 30);

        cmbcostcenter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbcostcenter.setEnabled(false);
        AutoCompleteDecorator.decorate(cmbcostcenter);
        jPanel1.add(cmbcostcenter);
        cmbcostcenter.setBounds(120, 80, 361, 26);

        cmbstaffcode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbstaffcode.setEnabled(false);
        AutoCompleteDecorator.decorate(cmbstaffcode);
        jPanel1.add(cmbstaffcode);
        cmbstaffcode.setBounds(120, 10, 330, 20);
        jPanel1.add(DateAccount);
        DateAccount.setBounds(120, 350, 190, 28);

        DateOperationTo.setEditable(false);
        DateOperationTo.setEnabled(false);
        jPanel1.add(DateOperationTo);
        DateOperationTo.setBounds(370, 110, 190, 28);

        txtpurpose_3.setEditable(false);
        txtpurpose_3.setEnabled(false);
        jPanel1.add(txtpurpose_3);
        txtpurpose_3.setBounds(120, 180, 272, 20);

        txtCheckedBy.setEditable(false);
        jPanel1.add(txtCheckedBy);
        txtCheckedBy.setBounds(120, 380, 170, 20);

        DateOperationFrom.setEnabled(false);
        jPanel1.add(DateOperationFrom);
        DateOperationFrom.setBounds(120, 110, 190, 28);

        DateReceive.setEditable(false);
        jPanel1.add(DateReceive);
        DateReceive.setBounds(420, 320, 190, 28);

        txtamount.setEditable(false);
        txtamount.setEnabled(false);
        jPanel1.add(txtamount);
        txtamount.setBounds(120, 250, 140, 30);

        txtRefPo.setEditable(false);
        txtRefPo.setEnabled(false);
        jPanel1.add(txtRefPo);
        txtRefPo.setBounds(120, 280, 140, 30);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancel);
        btnCancel.setBounds(540, 570, 130, 40);

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave);
        btnSave.setBounds(380, 570, 130, 40);

        txtpurpose_1.setEditable(false);
        txtpurpose_1.setEnabled(false);
        jPanel1.add(txtpurpose_1);
        txtpurpose_1.setBounds(120, 150, 272, 20);

        txtpurpose_2.setEditable(false);
        txtpurpose_2.setEnabled(false);
        jPanel1.add(txtpurpose_2);
        txtpurpose_2.setBounds(410, 150, 272, 20);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Advance No.");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(660, 10, 78, 20);

        txtAdvanceNo.setEditable(false);
        jPanel1.add(txtAdvanceNo);
        txtAdvanceNo.setBounds(740, 10, 120, 20);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Date");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(660, 40, 53, 20);

        txtDateAdvance.setEditable(false);
        txtDateAdvance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateAdvanceActionPerformed(evt);
            }
        });
        jPanel1.add(txtDateAdvance);
        txtDateAdvance.setBounds(740, 40, 120, 20);

        radioreturn.setText("Return");
        radioreturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioreturnActionPerformed(evt);
            }
        });
        jPanel1.add(radioreturn);
        radioreturn.setBounds(870, 130, 140, 18);

        radionormal.setSelected(true);
        radionormal.setText("Normal");
        radionormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radionormalActionPerformed(evt);
            }
        });
        jPanel1.add(radionormal);
        radionormal.setBounds(870, 10, 140, 18);

        radiosubmit.setText("Submit");
        radiosubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiosubmitActionPerformed(evt);
            }
        });
        jPanel1.add(radiosubmit);
        radiosubmit.setBounds(870, 40, 140, 18);

        radiocancel.setText("Cancel");
        radiocancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiocancelActionPerformed(evt);
            }
        });
        jPanel1.add(radiocancel);
        radiocancel.setBounds(870, 100, 140, 18);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Checked By");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(10, 380, 80, 17);

        DateSettlement.setEditable(false);
        jPanel1.add(DateSettlement);
        DateSettlement.setBounds(120, 320, 190, 28);

        jRadioshowreport.setText("Show in report");
        jRadioshowreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioshowreportActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioshowreport);
        jRadioshowreport.setBounds(870, 70, 140, 18);

        txtcustomer.setEditable(false);
        txtcustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcustomerActionPerformed(evt);
            }
        });
        jPanel1.add(txtcustomer);
        txtcustomer.setBounds(120, 40, 330, 30);

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel41.setText("Staff Code");
        jPanel1.add(jLabel41);
        jLabel41.setBounds(10, 10, 70, 17);

        cmb_IT1GP.setEditable(false);
        jPanel1.add(cmb_IT1GP);
        cmb_IT1GP.setBounds(120, 210, 560, 30);

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setText("Expenses Group");
        jPanel1.add(jLabel42);
        jLabel42.setBounds(10, 210, 100, 20);

        jLabel1.setText("ภาษี หัก ณ ที่จ่าย");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(770, 160, 100, 30);

        radionontax.setText("ไม่หักภาษี ณ ที่จ่าย");
        radionontax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radionontaxActionPerformed(evt);
            }
        });
        jPanel1.add(radionontax);
        radionontax.setBounds(870, 160, 130, 30);

        radiofrontwork.setText(" หักหน้างาน");
        radiofrontwork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiofrontworkActionPerformed(evt);
            }
        });
        jPanel1.add(radiofrontwork);
        radiofrontwork.setBounds(870, 220, 83, 30);

        griddetailac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Invoice", "Supplier", "Name", "Invoice Date", "Desc", "Cost", "Amt", "VatC", "Vat Amt", "Expense", "Total Amt", "ShareService", "ServiceCode", "VCTXT", "DUEDT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        griddetailac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                griddetailacMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                griddetailacMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(griddetailac);
        if (griddetailac.getColumnModel().getColumnCount() > 0) {
            griddetailac.getColumnModel().getColumn(2).setMinWidth(2);
            griddetailac.getColumnModel().getColumn(2).setPreferredWidth(2);
            griddetailac.getColumnModel().getColumn(2).setMaxWidth(2);
            griddetailac.getColumnModel().getColumn(7).setPreferredWidth(50);
            griddetailac.getColumnModel().getColumn(8).setPreferredWidth(50);
            griddetailac.getColumnModel().getColumn(12).setPreferredWidth(100);
        }

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(20, 410, 1000, 150);

        btn_preview.setText("Preview");
        btn_preview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_previewActionPerformed(evt);
            }
        });
        jPanel1.add(btn_preview);
        btn_preview.setBounds(870, 260, 140, 28);

        radiotaxchec.setText(" หักหน้าเช็ค");
        radiotaxchec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiotaxchecActionPerformed(evt);
            }
        });
        jPanel1.add(radiotaxchec);
        radiotaxchec.setBounds(870, 190, 107, 30);

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel60.setText("Ref. Capex No.");
        jPanel1.add(jLabel60);
        jLabel60.setBounds(10, 280, 110, 30);

        txt_PONO.setEditable(false);
        txt_PONO.setBackground(new java.awt.Color(255, 255, 255));
        txt_PONO.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(txt_PONO);
        txt_PONO.setBounds(350, 280, 190, 30);

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel72.setText("Amount");
        jPanel1.add(jLabel72);
        jLabel72.setBounds(10, 250, 110, 30);

        radio_transfer.setSelected(true);
        radio_transfer.setText("TRANSFER");
        radio_transfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_transferActionPerformed(evt);
            }
        });
        jPanel1.add(radio_transfer);
        radio_transfer.setBounds(420, 250, 86, 18);

        radio_cheque.setText("CHEQUE");
        radio_cheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_chequeActionPerformed(evt);
            }
        });
        jPanel1.add(radio_cheque);
        radio_cheque.setBounds(530, 250, 100, 18);

        jTabbedPane1.addTab("Cash Advance Voucher", jPanel1);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("To");
        jPanel3.add(jLabel24);
        jLabel24.setBounds(330, 140, 30, 17);

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Staff Code");
        jPanel3.add(jLabel25);
        jLabel25.setBounds(10, 50, 89, 17);

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("Cost Center");
        jPanel3.add(jLabel26);
        jLabel26.setBounds(10, 90, 89, 17);

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("Acct Date");
        jPanel3.add(jLabel27);
        jLabel27.setBounds(10, 380, 110, 17);

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("Operation Date");
        jPanel3.add(jLabel28);
        jLabel28.setBounds(10, 140, 110, 17);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("Purpose");
        jPanel3.add(jLabel29);
        jLabel29.setBounds(10, 190, 110, 17);

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Amount");
        jPanel3.add(jLabel30);
        jLabel30.setBounds(10, 290, 110, 17);

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Plan Rec Date");
        jPanel3.add(jLabel31);
        jLabel31.setBounds(10, 340, 110, 17);

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setText("Received Date");
        jPanel3.add(jLabel32);
        jLabel32.setBounds(10, 420, 110, 17);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("Ref. Capex No.");
        jPanel3.add(jLabel33);
        jLabel33.setBounds(530, 340, 100, 30);

        cmbcostcenter1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbcostcenter1.setEnabled(false);
        AutoCompleteDecorator.decorate(cmbcostcenter1);
        cmbcostcenter1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cmbcostcenter1InputMethodTextChanged(evt);
            }
        });
        cmbcostcenter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbcostcenter1ActionPerformed(evt);
            }
        });
        jPanel3.add(cmbcostcenter1);
        cmbcostcenter1.setBounds(120, 90, 361, 26);

        cmbstaffcode1.setEnabled(false);
        AutoCompleteDecorator.decorate(cmbstaffcode1);
        jPanel3.add(cmbstaffcode1);
        cmbstaffcode1.setBounds(120, 50, 361, 26);

        DateAccount1.setEnabled(false);
        jPanel3.add(DateAccount1);
        DateAccount1.setBounds(120, 380, 140, 28);

        DateOperationTo1.setEditable(false);
        jPanel3.add(DateOperationTo1);
        DateOperationTo1.setBounds(370, 140, 190, 28);

        txtpurpose1_3.setEditable(false);
        txtpurpose1_3.setEnabled(false);
        jPanel3.add(txtpurpose1_3);
        txtpurpose1_3.setBounds(120, 250, 272, 20);

        txtrecord1.setEditable(false);
        jPanel3.add(txtrecord1);
        txtrecord1.setBounds(120, 460, 190, 20);

        DateOperationFrom1.setEnabled(false);
        jPanel3.add(DateOperationFrom1);
        DateOperationFrom1.setBounds(120, 140, 190, 28);

        DatePlanReceive1.setEditable(false);
        DatePlanReceive1.setEnabled(false);
        jPanel3.add(DatePlanReceive1);
        DatePlanReceive1.setBounds(120, 340, 140, 28);

        txtamount1.setEditable(false);
        txtamount1.setEnabled(false);
        jPanel3.add(txtamount1);
        txtamount1.setBounds(120, 290, 270, 20);

        txtRefPo1.setEditable(false);
        txtRefPo1.setEnabled(false);
        jPanel3.add(txtRefPo1);
        txtRefPo1.setBounds(630, 340, 120, 30);

        btncancel1.setText("Cancel");
        jPanel3.add(btncancel1);
        btncancel1.setBounds(480, 540, 130, 40);

        btnSave1.setText("Save");
        btnSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnSave1);
        btnSave1.setBounds(310, 540, 130, 40);

        txtpurpose1_1.setEditable(false);
        txtpurpose1_1.setEnabled(false);
        jPanel3.add(txtpurpose1_1);
        txtpurpose1_1.setBounds(120, 190, 272, 20);

        txtpurpose1_2.setEditable(false);
        txtpurpose1_2.setEnabled(false);
        jPanel3.add(txtpurpose1_2);
        txtpurpose1_2.setBounds(120, 220, 272, 20);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("Advance No.");
        jPanel3.add(jLabel34);
        jLabel34.setBounds(680, 10, 78, 17);

        txtAdvanceNo1.setEditable(false);
        jPanel3.add(txtAdvanceNo1);
        txtAdvanceNo1.setBounds(770, 10, 120, 20);

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setText("Date");
        jPanel3.add(jLabel35);
        jLabel35.setBounds(680, 40, 53, 17);

        txtDateAdvance1.setEditable(false);
        jPanel3.add(txtDateAdvance1);
        txtDateAdvance1.setBounds(770, 40, 120, 20);

        radionormal1.setSelected(true);
        radionormal1.setText("Normal");
        radionormal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radionormal1ActionPerformed(evt);
            }
        });
        jPanel3.add(radionormal1);
        radionormal1.setBounds(900, 10, 93, 18);

        radiosubmit1.setText("Submit");
        radiosubmit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiosubmit1ActionPerformed(evt);
            }
        });
        jPanel3.add(radiosubmit1);
        radiosubmit1.setBounds(900, 40, 93, 18);

        radiocancel1.setText("Cancel");
        radiocancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiocancel1ActionPerformed(evt);
            }
        });
        jPanel3.add(radiocancel1);
        radiocancel1.setBounds(900, 70, 93, 18);

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("Reccorder");
        jPanel3.add(jLabel36);
        jLabel36.setBounds(10, 460, 110, 17);

        DatePlanSettlement1.setEditable(false);
        DatePlanSettlement1.setEnabled(false);
        jPanel3.add(DatePlanSettlement1);
        DatePlanSettlement1.setBounds(380, 340, 140, 28);

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setText("Plan Settl Date");
        jPanel3.add(jLabel37);
        jLabel37.setBounds(280, 340, 100, 17);
        jPanel3.add(txtchqno1);
        txtchqno1.setBounds(380, 420, 140, 20);

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel38.setText("Bank");
        jPanel3.add(jLabel38);
        jLabel38.setBounds(340, 380, 40, 17);
        jPanel3.add(txtbank1);
        txtbank1.setBounds(380, 380, 140, 20);

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel39.setText("Chq.Date");
        jPanel3.add(jLabel39);
        jLabel39.setBounds(530, 420, 70, 17);
        jPanel3.add(DateReceive1);
        DateReceive1.setBounds(120, 420, 140, 28);
        jPanel3.add(txtchqno2);
        txtchqno2.setBounds(380, 420, 140, 20);

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setText("Chq.no");
        jPanel3.add(jLabel40);
        jLabel40.setBounds(330, 420, 50, 17);
        jPanel3.add(DateCheqDate1);
        DateCheqDate1.setBounds(600, 420, 140, 28);

        btchequenumber.setBackground(new java.awt.Color(204, 255, 255));
        btchequenumber.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btchequenumber.setText("Cheque Number");
        btchequenumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btchequenumberActionPerformed(evt);
            }
        });
        jPanel3.add(btchequenumber);
        btchequenumber.setBounds(600, 380, 130, 30);

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel73.setText("Ref.Po No.");
        jPanel3.add(jLabel73);
        jLabel73.setBounds(760, 340, 70, 30);

        txt_PONO1.setEditable(false);
        txt_PONO1.setBackground(new java.awt.Color(255, 255, 255));
        txt_PONO1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(txt_PONO1);
        txt_PONO1.setBounds(850, 340, 170, 30);

        jTabbedPane1.addTab("Received Advance Entry ", jPanel3);

        jPanel2.setEnabled(false);
        jPanel2.setMaximumSize(new java.awt.Dimension(900, 800));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Advance No.");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(690, 10, 78, 20);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Staff Code");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(430, 90, 70, 17);

        AutoCompleteDecorator.decorate(cmbstaffcode2);
        cmbstaffcode2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cmbstaffcode2InputMethodTextChanged(evt);
            }
        });
        cmbstaffcode2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbstaffcode2ActionPerformed(evt);
            }
        });
        jPanel2.add(cmbstaffcode2);
        cmbstaffcode2.setBounds(510, 90, 250, 26);

        txtAdvanceNo2.setEditable(false);
        txtAdvanceNo2.setEnabled(false);
        jPanel2.add(txtAdvanceNo2);
        txtAdvanceNo2.setBounds(780, 10, 120, 20);

        txtDateAdvance2.setEditable(false);
        txtDateAdvance2.setEnabled(false);
        jPanel2.add(txtDateAdvance2);
        txtDateAdvance2.setBounds(780, 40, 120, 20);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Costcenter");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(10, 90, 100, 17);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Date");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(720, 40, 53, 17);

        AutoCompleteDecorator.decorate(cmbcostcenter2);
        cmbcostcenter2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cmbcostcenter2InputMethodTextChanged(evt);
            }
        });
        cmbcostcenter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbcostcenter2ActionPerformed(evt);
            }
        });
        jPanel2.add(cmbcostcenter2);
        cmbcostcenter2.setBounds(180, 90, 240, 26);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Return Refund By");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(10, 360, 160, 17);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Amount");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(12, 150, 90, 17);
        jPanel2.add(DateSettlemAccount2);
        DateSettlemAccount2.setBounds(180, 120, 240, 28);

        btncancel2.setText("Cancel");
        btncancel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancel2ActionPerformed(evt);
            }
        });
        jPanel2.add(btncancel2);
        btncancel2.setBounds(500, 530, 110, 40);

        btnsave2.setText("Save");
        btnsave2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsave2ActionPerformed(evt);
            }
        });
        jPanel2.add(btnsave2);
        btnsave2.setBounds(350, 530, 110, 40);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Plan Settlement");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(10, 50, 110, 20);

        txtRefundBy2.setEditable(false);
        jPanel2.add(txtRefundBy2);
        txtRefundBy2.setBounds(180, 360, 120, 20);

        radioreturn3.setText("Return");
        radioreturn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioreturn3ActionPerformed(evt);
            }
        });
        jPanel2.add(radioreturn3);
        radioreturn3.setBounds(910, 70, 90, 18);

        radionormal3.setSelected(true);
        radionormal3.setText("Normal");
        radionormal3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radionormal3ActionPerformed(evt);
            }
        });
        jPanel2.add(radionormal3);
        radionormal3.setBounds(910, 10, 80, 18);

        radiosubmit3.setText("Submit");
        radiosubmit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiosubmit3ActionPerformed(evt);
            }
        });
        jPanel2.add(radiosubmit3);
        radiosubmit3.setBounds(910, 40, 80, 18);

        griddetailacstep5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Invoice", "Expense", "Supplier", "Invoice Date", "Desc", "Cost", "Amt", "VatC", "Vat Amt", "Total Amt", "ShareService", "ServiceCode", "VCTXT", "DUEDT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, true, false, false, false, false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        griddetailacstep5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                griddetailacstep5MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                griddetailacstep5MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(griddetailacstep5);
        if (griddetailacstep5.getColumnModel().getColumnCount() > 0) {
            griddetailacstep5.getColumnModel().getColumn(0).setMinWidth(70);
            griddetailacstep5.getColumnModel().getColumn(0).setMaxWidth(70);
            griddetailacstep5.getColumnModel().getColumn(1).setMinWidth(60);
            griddetailacstep5.getColumnModel().getColumn(1).setMaxWidth(60);
            griddetailacstep5.getColumnModel().getColumn(2).setMinWidth(170);
            griddetailacstep5.getColumnModel().getColumn(2).setMaxWidth(170);
            griddetailacstep5.getColumnModel().getColumn(3).setMinWidth(80);
            griddetailacstep5.getColumnModel().getColumn(3).setMaxWidth(80);
            griddetailacstep5.getColumnModel().getColumn(5).setMinWidth(50);
            griddetailacstep5.getColumnModel().getColumn(5).setMaxWidth(50);
            griddetailacstep5.getColumnModel().getColumn(7).setMinWidth(40);
            griddetailacstep5.getColumnModel().getColumn(7).setMaxWidth(40);
            griddetailacstep5.getColumnModel().getColumn(9).setMinWidth(70);
            griddetailacstep5.getColumnModel().getColumn(9).setMaxWidth(70);
            griddetailacstep5.getColumnModel().getColumn(10).setMinWidth(90);
            griddetailacstep5.getColumnModel().getColumn(10).setMaxWidth(90);
        }

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(10, 400, 990, 120);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Acct Settlement");
        jPanel2.add(jLabel19);
        jLabel19.setBounds(10, 120, 110, 20);
        jPanel2.add(DatePlanSettlemAccount2);
        DatePlanSettlemAccount2.setBounds(180, 50, 190, 28);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Settlement");
        jPanel2.add(jLabel20);
        jLabel20.setBounds(380, 50, 70, 20);
        jPanel2.add(DateSettlement2);
        DateSettlement2.setBounds(450, 50, 190, 28);

        cmb_IT1GP_2.setEditable(false);
        jPanel2.add(cmb_IT1GP_2);
        cmb_IT1GP_2.setBounds(430, 150, 420, 30);

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel47.setText("Expenses Group");
        jPanel2.add(jLabel47);
        jLabel47.setBounds(330, 150, 100, 20);

        txtamount2.setEditable(false);
        txtamount2.setBackground(new java.awt.Color(255, 255, 204));
        txtamount2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtamount2.setText("0.00");
        jPanel2.add(txtamount2);
        txtamount2.setBounds(180, 150, 120, 28);

        jLabel48.setText("จำนวนเงินก่อน Vat7%");
        jPanel2.add(jLabel48);
        jLabel48.setBounds(10, 180, 140, 20);

        txtamtbfvat1.setEditable(false);
        txtamtbfvat1.setBackground(new java.awt.Color(255, 255, 204));
        txtamtbfvat1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtamtbfvat1.setText("0.00");
        jPanel2.add(txtamtbfvat1);
        txtamtbfvat1.setBounds(180, 180, 120, 28);

        jLabel49.setText("Vta 7%");
        jPanel2.add(jLabel49);
        jLabel49.setBounds(10, 210, 140, 20);

        txtvat1.setEditable(false);
        txtvat1.setBackground(new java.awt.Color(255, 255, 204));
        txtvat1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtvat1.setText("0.00");
        jPanel2.add(txtvat1);
        txtvat1.setBounds(180, 210, 120, 28);

        jLabel50.setText("Total Amount");
        jPanel2.add(jLabel50);
        jLabel50.setBounds(10, 240, 140, 20);

        txtamt1.setEditable(false);
        txtamt1.setBackground(new java.awt.Color(255, 255, 204));
        txtamt1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtamt1.setText("0.00");
        jPanel2.add(txtamt1);
        txtamt1.setBounds(180, 240, 120, 28);

        jLabel51.setText("W/H");
        jPanel2.add(jLabel51);
        jLabel51.setBounds(10, 270, 140, 20);

        txtwhtax1.setEditable(false);
        txtwhtax1.setBackground(new java.awt.Color(204, 255, 255));
        txtwhtax1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtwhtax1.setText("0.00");
        txtwhtax1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtwhtax1ActionPerformed(evt);
            }
        });
        jPanel2.add(txtwhtax1);
        txtwhtax1.setBounds(180, 270, 120, 28);

        jLabel52.setText("Return to company");
        jPanel2.add(jLabel52);
        jLabel52.setBounds(10, 300, 140, 20);

        txtreturn1.setEditable(false);
        txtreturn1.setBackground(new java.awt.Color(255, 255, 204));
        txtreturn1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtreturn1.setText("0.00");
        jPanel2.add(txtreturn1);
        txtreturn1.setBounds(180, 300, 120, 28);

        jLabel53.setText("Claim back from company");
        jPanel2.add(jLabel53);
        jLabel53.setBounds(10, 330, 170, 20);

        txtrefund1.setEditable(false);
        txtrefund1.setBackground(new java.awt.Color(255, 255, 204));
        txtrefund1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtrefund1.setText("0.00");
        jPanel2.add(txtrefund1);
        txtrefund1.setBounds(180, 330, 120, 28);

        rdoPTC.setText("ไม่เกิน 1,000 บาท เบิก Petty Cash");
        rdoPTC.setEnabled(false);
        jPanel2.add(rdoPTC);
        rdoPTC.setBounds(310, 330, 220, 20);

        rdoReturnSCB.setText("SCB");
        rdoReturnSCB.setEnabled(false);
        rdoReturnSCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoReturnSCBActionPerformed(evt);
            }
        });
        jPanel2.add(rdoReturnSCB);
        rdoReturnSCB.setBounds(310, 300, 210, 18);

        rdoReturnKBANK.setText("KBANK-340-2-314428");
        rdoReturnKBANK.setEnabled(false);
        rdoReturnKBANK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoReturnKBANKActionPerformed(evt);
            }
        });
        jPanel2.add(rdoReturnKBANK);
        rdoReturnKBANK.setBounds(530, 300, 170, 18);

        rdoTransBank.setText("เกิน 1,000 บาท โอนเข้าบัญชี");
        rdoTransBank.setEnabled(false);
        jPanel2.add(rdoTransBank);
        rdoTransBank.setBounds(530, 330, 190, 20);

        txtBCode.setEditable(false);
        txtBCode.setBackground(new java.awt.Color(204, 255, 255));
        txtBCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtBCode);
        txtBCode.setBounds(700, 360, 270, 28);

        txtBName.setEditable(false);
        txtBName.setBackground(new java.awt.Color(204, 255, 255));
        txtBName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtBName);
        txtBName.setBounds(740, 330, 230, 28);

        jLabel54.setText("ภาษี หัก ณ ที่จ่าย");
        jPanel2.add(jLabel54);
        jLabel54.setBounds(800, 100, 100, 30);

        radiotaxchec_1.setForeground(new java.awt.Color(255, 0, 51));
        radiotaxchec_1.setText(" หักหน้าเช็ค");
        radiotaxchec_1.setEnabled(false);
        jPanel2.add(radiotaxchec_1);
        radiotaxchec_1.setBounds(910, 100, 110, 30);

        radiofrontwork_1.setForeground(new java.awt.Color(255, 0, 0));
        radiofrontwork_1.setText(" หักหน้างาน");
        radiofrontwork_1.setEnabled(false);
        jPanel2.add(radiofrontwork_1);
        radiofrontwork_1.setBounds(910, 130, 110, 30);

        jLabel43.setText("Name");
        jPanel2.add(jLabel43);
        jLabel43.setBounds(740, 310, 80, 20);

        jLabel44.setText("Bank");
        jPanel2.add(jLabel44);
        jLabel44.setBounds(660, 360, 40, 20);

        btn_preview1.setText("Preview");
        btn_preview1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_preview1ActionPerformed(evt);
            }
        });
        jPanel2.add(btn_preview1);
        btn_preview1.setBounds(920, 200, 100, 30);

        radionontax_1.setForeground(new java.awt.Color(255, 0, 0));
        radionontax_1.setText("ไม่หักภาษี ณ ที่จ่าย");
        radionontax_1.setEnabled(false);
        jPanel2.add(radionontax_1);
        radionontax_1.setBounds(910, 160, 130, 18);

        jTabbedPane1.addTab("Cash Advance Settlement", jPanel2);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(20, 90, 1030, 640);

        lblCompanyName.setAlignment(java.awt.Label.CENTER);
        lblCompanyName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblCompanyName.setText("Company Name");
        getContentPane().add(lblCompanyName);
        lblCompanyName.setBounds(10, 10, 940, 30);

        label17.setAlignment(java.awt.Label.CENTER);
        label17.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 24)); // NOI18N
        label17.setForeground(new java.awt.Color(204, 0, 0));
        label17.setText("Cash Advance For Accounting New");
        getContentPane().add(label17);
        label17.setBounds(10, 40, 1020, 40);

        panel1.setLayout(null);

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel45.setText("Advance No.");
        panel1.add(jLabel45);
        jLabel45.setBounds(690, 10, 78, 20);

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel46.setText("Staff Code");
        panel1.add(jLabel46);
        jLabel46.setBounds(420, 90, 64, 20);

        txtAdvanceNo3.setEditable(false);
        txtAdvanceNo3.setEnabled(false);
        panel1.add(txtAdvanceNo3);
        txtAdvanceNo3.setBounds(780, 10, 120, 20);

        txtDateAdvance3.setEditable(false);
        txtDateAdvance3.setEnabled(false);
        panel1.add(txtDateAdvance3);
        txtDateAdvance3.setBounds(780, 40, 120, 20);

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel55.setText("Costcenter");
        panel1.add(jLabel55);
        jLabel55.setBounds(20, 90, 90, 17);

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel56.setText("Date");
        panel1.add(jLabel56);
        jLabel56.setBounds(720, 40, 53, 17);

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel57.setText("Return Refund By");
        panel1.add(jLabel57);
        jLabel57.setBounds(20, 360, 120, 17);

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel58.setText("Amount");
        panel1.add(jLabel58);
        jLabel58.setBounds(20, 150, 82, 17);

        btncancel3.setText("Cancel");
        btncancel3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancel3ActionPerformed(evt);
            }
        });
        panel1.add(btncancel3);
        btncancel3.setBounds(500, 530, 110, 40);

        btnsave3.setText("Save");
        btnsave3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsave3ActionPerformed(evt);
            }
        });
        panel1.add(btnsave3);
        btnsave3.setBounds(350, 530, 110, 40);

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel59.setText("Plan Settlement");
        panel1.add(jLabel59);
        jLabel59.setBounds(20, 50, 100, 20);

        txtRefundBy3.setEditable(false);
        panel1.add(txtRefundBy3);
        txtRefundBy3.setBounds(150, 360, 120, 20);

        radioreturn4.setText("Return");
        radioreturn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioreturn4ActionPerformed(evt);
            }
        });
        panel1.add(radioreturn4);
        radioreturn4.setBounds(920, 70, 80, 18);

        radionormal4.setSelected(true);
        radionormal4.setText("Normal");
        radionormal4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radionormal4ActionPerformed(evt);
            }
        });
        panel1.add(radionormal4);
        radionormal4.setBounds(920, 10, 70, 18);

        radiosubmit4.setText("Submit");
        radiosubmit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiosubmit4ActionPerformed(evt);
            }
        });
        panel1.add(radiosubmit4);
        radiosubmit4.setBounds(920, 40, 70, 18);

        griddetailacstep6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Invoice", "Expense", "Supplier", "Invoice Date", "Desc", "Cost", "Amt", "VatC", "Vat Amt", "Total Amt", "ShareService", "ServiceCode", "VCTXT", "DUEDT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, true, false, false, false, false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(griddetailacstep6);
        if (griddetailacstep6.getColumnModel().getColumnCount() > 0) {
            griddetailacstep6.getColumnModel().getColumn(0).setMinWidth(70);
            griddetailacstep6.getColumnModel().getColumn(0).setMaxWidth(70);
            griddetailacstep6.getColumnModel().getColumn(1).setMinWidth(60);
            griddetailacstep6.getColumnModel().getColumn(1).setMaxWidth(60);
            griddetailacstep6.getColumnModel().getColumn(2).setMinWidth(200);
            griddetailacstep6.getColumnModel().getColumn(2).setMaxWidth(200);
            griddetailacstep6.getColumnModel().getColumn(3).setMinWidth(80);
            griddetailacstep6.getColumnModel().getColumn(3).setMaxWidth(80);
            griddetailacstep6.getColumnModel().getColumn(7).setMinWidth(50);
            griddetailacstep6.getColumnModel().getColumn(7).setMaxWidth(50);
            griddetailacstep6.getColumnModel().getColumn(9).setMinWidth(90);
            griddetailacstep6.getColumnModel().getColumn(9).setMaxWidth(90);
            griddetailacstep6.getColumnModel().getColumn(10).setMinWidth(90);
            griddetailacstep6.getColumnModel().getColumn(10).setMaxWidth(90);
        }

        panel1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 400, 990, 120);
        panel1.add(DatePlanSettlemAccount3);
        DatePlanSettlemAccount3.setBounds(150, 50, 190, 28);

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel61.setText("Settlement");
        panel1.add(jLabel61);
        jLabel61.setBounds(350, 50, 70, 20);
        panel1.add(DateSettlement3);
        DateSettlement3.setBounds(420, 50, 190, 28);

        cmb_IT1GP_3.setEditable(false);
        panel1.add(cmb_IT1GP_3);
        cmb_IT1GP_3.setBounds(150, 120, 640, 28);

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel62.setText("Expenses Group");
        panel1.add(jLabel62);
        jLabel62.setBounds(20, 120, 100, 20);

        txtamount3.setEditable(false);
        txtamount3.setBackground(new java.awt.Color(255, 255, 204));
        txtamount3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtamount3.setText("0.00");
        panel1.add(txtamount3);
        txtamount3.setBounds(150, 150, 120, 28);

        jLabel63.setText("จำนวนเงินก่อน Vat7%");
        panel1.add(jLabel63);
        jLabel63.setBounds(20, 180, 130, 20);

        txtamtbfvat2.setEditable(false);
        txtamtbfvat2.setBackground(new java.awt.Color(255, 255, 204));
        txtamtbfvat2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtamtbfvat2.setText("0.00");
        panel1.add(txtamtbfvat2);
        txtamtbfvat2.setBounds(150, 180, 120, 28);

        jLabel64.setText("Vta 7%");
        panel1.add(jLabel64);
        jLabel64.setBounds(20, 210, 130, 20);

        txtvat2.setEditable(false);
        txtvat2.setBackground(new java.awt.Color(255, 255, 204));
        txtvat2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtvat2.setText("0.00");
        panel1.add(txtvat2);
        txtvat2.setBounds(150, 210, 120, 28);

        jLabel65.setText("Total Amount");
        panel1.add(jLabel65);
        jLabel65.setBounds(20, 240, 130, 20);

        txtamt2.setEditable(false);
        txtamt2.setBackground(new java.awt.Color(255, 255, 204));
        txtamt2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtamt2.setText("0.00");
        panel1.add(txtamt2);
        txtamt2.setBounds(150, 240, 120, 28);

        jLabel66.setText("W/H");
        panel1.add(jLabel66);
        jLabel66.setBounds(20, 270, 130, 20);

        txtwhtax2.setEditable(false);
        txtwhtax2.setBackground(new java.awt.Color(204, 255, 255));
        txtwhtax2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtwhtax2.setText("0.00");
        txtwhtax2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtwhtax2ActionPerformed(evt);
            }
        });
        panel1.add(txtwhtax2);
        txtwhtax2.setBounds(150, 270, 120, 28);

        jLabel67.setText("Return to company");
        panel1.add(jLabel67);
        jLabel67.setBounds(20, 300, 130, 20);

        txtreturn2.setEditable(false);
        txtreturn2.setBackground(new java.awt.Color(255, 255, 204));
        txtreturn2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtreturn2.setText("0.00");
        panel1.add(txtreturn2);
        txtreturn2.setBounds(150, 300, 120, 28);

        jLabel68.setText("Refund from company");
        panel1.add(jLabel68);
        jLabel68.setBounds(20, 330, 130, 20);

        txtrefund2.setEditable(false);
        txtrefund2.setBackground(new java.awt.Color(255, 255, 204));
        txtrefund2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtrefund2.setText("0.00");
        panel1.add(txtrefund2);
        txtrefund2.setBounds(150, 330, 120, 28);

        rdoPTC1.setText("ไม่เกิน 1,000 บาท เบิก Petty Cash");
        rdoPTC1.setEnabled(false);
        panel1.add(rdoPTC1);
        rdoPTC1.setBounds(280, 330, 220, 20);

        rdoReturnSCB1.setText("SCB-331-2-431686");
        rdoReturnSCB1.setEnabled(false);
        rdoReturnSCB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoReturnSCB1ActionPerformed(evt);
            }
        });
        panel1.add(rdoReturnSCB1);
        rdoReturnSCB1.setBounds(280, 300, 210, 18);

        rdoReturnKBANK1.setText("KBANK-340-2-314428");
        rdoReturnKBANK1.setEnabled(false);
        rdoReturnKBANK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoReturnKBANK1ActionPerformed(evt);
            }
        });
        panel1.add(rdoReturnKBANK1);
        rdoReturnKBANK1.setBounds(500, 300, 170, 18);

        rdoTransBank1.setText("เกิน 1,000 บาท โอนเข้าบัญชี");
        rdoTransBank1.setEnabled(false);
        panel1.add(rdoTransBank1);
        rdoTransBank1.setBounds(500, 330, 190, 20);

        txtBCode1.setEditable(false);
        txtBCode1.setBackground(new java.awt.Color(204, 255, 255));
        txtBCode1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel1.add(txtBCode1);
        txtBCode1.setBounds(670, 360, 270, 28);

        txtBName1.setEditable(false);
        txtBName1.setBackground(new java.awt.Color(204, 255, 255));
        txtBName1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel1.add(txtBName1);
        txtBName1.setBounds(710, 330, 230, 28);

        jLabel69.setText("ภาษี หัก ณ ที่จ่าย");
        panel1.add(jLabel69);
        jLabel69.setBounds(820, 100, 100, 30);

        radiotaxchec_2.setForeground(new java.awt.Color(255, 0, 51));
        radiotaxchec_2.setText(" หักหน้าเช็ค");
        radiotaxchec_2.setEnabled(false);
        panel1.add(radiotaxchec_2);
        radiotaxchec_2.setBounds(920, 100, 100, 30);

        radionontax_2.setForeground(new java.awt.Color(255, 0, 0));
        radionontax_2.setText("ไม่หักภาษี");
        radionontax_2.setEnabled(false);
        radionontax_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radionontax_2ActionPerformed(evt);
            }
        });
        panel1.add(radionontax_2);
        radionontax_2.setBounds(920, 160, 100, 30);

        jLabel70.setText("Name");
        panel1.add(jLabel70);
        jLabel70.setBounds(710, 310, 80, 20);

        jLabel71.setText("Bank");
        panel1.add(jLabel71);
        jLabel71.setBounds(630, 360, 40, 20);

        cmbcostcenter3.setEditable(false);
        cmbcostcenter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbcostcenter3ActionPerformed(evt);
            }
        });
        panel1.add(cmbcostcenter3);
        cmbcostcenter3.setBounds(150, 90, 260, 28);

        cmbstaffcode3.setEditable(false);
        panel1.add(cmbstaffcode3);
        cmbstaffcode3.setBounds(490, 90, 300, 28);

        radiofrontwork_2.setForeground(new java.awt.Color(255, 0, 0));
        radiofrontwork_2.setText(" หักหน้างาน");
        radiofrontwork_2.setEnabled(false);
        panel1.add(radiofrontwork_2);
        radiofrontwork_2.setBounds(920, 130, 100, 30);

        jTabbedPane2.addTab("Cash Advance Check Settlement", panel1);

        getContentPane().add(jTabbedPane2);
        jTabbedPane2.setBounds(950, 0, 100, 30);

        setSize(new java.awt.Dimension(1073, 817));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void msbox(String strr) {
        int dialogButton = JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(null, strr, "Warning", dialogButton);
    }

    private void msboxok(String strr) {
        JOptionPane.showMessageDialog(null, strr);
    }

    private void SetNewCashAdvanAccounting() {
        ClassCheckDataProgram cdp = new ClassCheckDataProgram();

        //  txtDateAdvance.setText(cdp.GetDateCurrenttime());
        try {

            lblCompanyName.setText("Company Name : " + LoginCompanyName);
//            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
            this.setTitle("Cash Advance Accounting For " + lblCompanyName.getText());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    private boolean CheckDataStep2ToSave() //STEP 2 CHECK
    {
        ClassCheckDataProgram CcheckData = new ClassCheckDataProgram();
        Boolean DateAccounting = CcheckData.CheckdatainputDate(DateAccount.getDate());
        if (DateAccounting == false) {
            msbox("Please Check Date Accounting");
            return false;
        }
        return true;
    }

    private boolean CheckDataStep3ToSave() //STEP 2 CHECK
    {
        ClassCheckDataProgram CcheckData = new ClassCheckDataProgram();
        Boolean DateReceive = CcheckData.CheckdatainputDate(DateReceive1.getDate());
        Boolean DateCheque = CcheckData.CheckdatainputDate(DateCheqDate1.getDate());

        if (DateCheque == false) {
            msbox("Please Check Date Cheque");
            return false;
        }

        if (DateReceive == false) {
            msbox("Please Check Date Receive");
            return false;
        }
        if (txtbank1.getText().trim().equals("")) {
            msbox("Please Check Bank");
            return false;
        }
        if (txtchqno1.getText().trim().equals("")) {
            msbox("Please Check Cheque No.");
            return false;
        }

        return true;
    }

    //Account Save  Step1

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:  
        String statussetdata;
        ClassSetdata setdata = new ClassSetdata();
        ClassCheckDataProgram cdp = new ClassCheckDataProgram();
        Classgetdata cgd = new Classgetdata();

        String CASH_CANO = txtAdvanceNo.getText().trim();

        String CASH_STAT = "";

        if (CheckDataStep2ToSave() == false) {
            msbox("Please Check Data");
            return;
        }

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Save Cash Advan Accounting ?", "Warning",
                dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {

            String CASH_TRAD = cdp.GetDateFormatSet(DateAccount.getDate()).trim();
            String CASH_CHEB = txtCheckedBy.getText().trim();

            if (radionormal.isSelected() == true) {
                CASH_STAT = "20";
            }
            if (radiosubmit.isSelected() == true) {
                CASH_STAT = "30";
                if ((radionontax.isSelected() == true && cgd.CheckLineDuedate_APS100(CASH_CANO) > 0)) {
                    System.out.println(cgd.CheckLineDuedate_APS100(CASH_CANO));
                    msbox("Please Check Data of Invoice !!");
                    return;
                }
            }

            if (radiocancel.isSelected() == true) {
                CASH_STAT = "99";
            }

            if (radioreturn.isSelected() == true) {
                CASH_STAT = "10";
            }

            if (jRadioshowreport.isSelected() == true) {
                CASH_STAT = "50";
            }

            if (!CASH_CANO.isEmpty()) {
                setdata.SaveCashAdvanceAccountStep1Form(CASH_CANO, CASH_TRAD, CASH_CHEB, CASH_STAT);
            }
            if (CASH_CANO != "" && !CASH_CANO.isEmpty()) {
                SubmitforAccounting sbacc = new SubmitforAccounting();
                ClassCheckDataProgram ccdp = new ClassCheckDataProgram();

                if (CASH_STAT.equals("30")) {

                    String remark = txtpurpose_1.getText().trim() + txtpurpose_2.getText().trim() + txtpurpose_3.getText().trim();

                    String Advance = txtAdvanceNo.getText().trim();
                    String[] customer = txtcustomer.getText().toString().split(":");
                    String cusNO = customer[0].trim();
                    String amt = txtamount.getText().trim();
                    String accdate = ccdp.GetDateFormatSetsubmitacc(DateAccount.getDate());
                    String settdate = ccdp.GetDateFormatSetsubmitacc(DateSettlement.getDate());

                    if (radionontax.isSelected()) {
                        sbacc.callARS100(Advance, cusNO, amt, accdate, settdate, remark); // submitforacc

//                        try {
//                            ResultSet rsl = cgd.GetAdvanceDetailM3(Advance);
//                            String[] SETT_COST = new String[20], SETT_AMTB = new String[20], ACCODE = new String[20], ServiceCode = new String[20], VATCODE = new String[20], InvNO = new String[20], InvAccDate = new String[20],
//                                    InvDate = new String[20], InvAmt = new String[20], InvSupp = new String[20], GenVoucherText = new String[20], SETT_DUEDATE = new String[20],
//                                    EATX40 = new String[20];
//                            Double[] VAT_C = new Double[20], VAT_UC = new Double[20];
//                            Double AMT_VATC = 0.00, AMT_VATUC = 0.00;
//
//                            int i = 0;
//                            int x = 0;
//                            while (rsl.next()) {
//                                VAT_C[i] = ccdp.Double2digitReturn(rsl.getDouble("VAT_C"));
//                                VAT_UC[i] = ccdp.Double2digitReturn(rsl.getDouble("VAT_UC"));
//                                SETT_COST[i] = rsl.getString("SETT_COST").trim();
//                                SETT_AMTB[i] = rsl.getString("SETT_AMTB").trim();
//                                ACCODE[i] = rsl.getString("ACCODE").trim();
//                                VATCODE[i] = rsl.getString("SETT_CDVAT").trim();
//                                InvNO[i] = rsl.getString("SETT_INVC").trim();
////                                InvAccDate[i] = ccdp.SubDateForM3(rsl.getString("SETT_INVD").trim());
//                                InvDate[i] = ccdp.SubDateForM3(rsl.getString("SETT_INVD").trim());
//                                InvAmt[i] = rsl.getString("SETT_AMTT").trim();
//                                InvSupp[i] = rsl.getString("SETT_SUPP").trim();
//                                GenVoucherText[i] = rsl.getString("SETT_VCTXT").trim();
//                                SETT_DUEDATE[i] = ccdp.SubDateForM3(rsl.getString("SETT_DUEDT").trim());
//                                EATX40[i] = rsl.getString("EATX40").trim();
//                                ServiceCode[i] = rsl.getString("SETT_RETT").trim();
//                                i++;
//                            }
//                            for (x = 0; x < i;) {
//                                if (EATX40[x].equalsIgnoreCase("UCN")) {
//                                    x++;
//                                    continue;
//                                } else {
//                                    String msg = sbacc.callAPS100(VAT_C[x], VAT_UC[x], SETT_COST[x], SETT_AMTB[x], ACCODE[x], InvNO[x], accdate, InvDate[x], InvAmt[x], InvSupp[x], GenVoucherText[x],
//                                            SETT_DUEDATE[x], EATX40[x], VATCODE[x], Advance, ServiceCode[x], customer[0]);
//                                    msboxok(InvNO[x] + " : " + msg);
//                                    String[] Vouchermss = msg.toString().split(" ");
//                                    String Voucher = Vouchermss[2].trim();
//                                    if (Voucher.length() == 8) {
//                                        setdata.UpdateVoucher_APS(Advance, Voucher, InvNO[x]);
//                                    }
//                                    x++;
//                                }
//                            }
//                        } catch (SQLException ex) {
//                            Logger.getLogger(CashAdvanAccounting.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (Exception ex) {
//                            Logger.getLogger(CashAdvanAccounting.class.getName()).log(Level.SEVERE, null, ex);
//                        }

                    } else {
// call ars100           
                        sbacc.callARS100(Advance, cusNO, amt, accdate, settdate, remark); // submitforacc 
                    }

                    Set_ActiveStep1(false);
                    Set_ActiveStep2(true);
                    Set_ActiveStep3(false);
                    SetFormDataWithSTEP1_Cash(CashAdvanceRequestNo);
                    SetFormDataWithSTEP2_Cash(CashAdvanceRequestNo);
                    txtrecord1.setText(LoginUsername);
                }

                msboxok("Save Cash Advance Account Voucher Complete");
                dispose();
            } else {
                msboxok("Save Cash Advance Account Voucher Fail");
            }
        }


    }//GEN-LAST:event_btnSaveActionPerformed

    private void radioreturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioreturnActionPerformed
        // TODO add your handling code here:
        if (radioreturn.isSelected()) {
            radionormal.setSelected(!radioreturn.isSelected());
            radiosubmit.setSelected(!radioreturn.isSelected());
        }
    }//GEN-LAST:event_radioreturnActionPerformed

    private void radionormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radionormalActionPerformed
        // TODO add your handling code here:
        if (radionormal.isSelected()) {
            radiosubmit.setSelected(!radionormal.isSelected());
            radioreturn.setSelected(!radionormal.isSelected());
            jRadioshowreport.setSelected(!radionormal.isSelected());
        }
    }//GEN-LAST:event_radionormalActionPerformed

    private void radiosubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiosubmitActionPerformed
        // TODO add your handling code here:
        if (radiosubmit.isSelected()) {
            radionormal.setSelected(!radiosubmit.isSelected());
            radioreturn.setSelected(!radiosubmit.isSelected());
            jRadioshowreport.setSelected(!radiosubmit.isSelected());
        }
    }//GEN-LAST:event_radiosubmitActionPerformed

    private void radiocancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiocancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radiocancelActionPerformed

    private void cmbcostcenter1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cmbcostcenter1InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbcostcenter1InputMethodTextChanged

    private void cmbcostcenter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbcostcenter1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbcostcenter1ActionPerformed

    private void btnSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave1ActionPerformed
        // TODO add your handling code here:
        String CASH_STAT = "";
        String statussetdata;
        ClassSetdata setdata = new ClassSetdata();
        ClassCheckDataProgram cdp = new ClassCheckDataProgram();
        if (CheckDataStep3ToSave() == false) {
            return;
        }
        String CASH_CANO = txtAdvanceNo1.getText().trim();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Save Cash Advan Received ?", "Warning",
                dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {

            String CASH_REDA = cdp.GetDateFormatSet(DateReceive1.getDate()).trim();
            String CASH_BANK = txtbank1.getText();
            String CASH_CHQN = txtchqno1.getText();
            String CASH_DUED = cdp.GetDateFormatSet(DateCheqDate1.getDate()).trim();

            if (radionormal1.isSelected() == true) {
                CASH_STAT = "30";
            }

            if (radiosubmit1.isSelected() == true) {
                CASH_STAT = "40";
                btnSave1.setEnabled(false);
                btncancel1.setEnabled(false);
            }

            if (radiocancel1.isSelected() == true) {
                CASH_STAT = "99";
            }

            if (!CASH_CANO.isEmpty()) {
                setdata.SaveCashAdvanceAccountStep2Form(CASH_CANO, CASH_REDA, CASH_BANK, CASH_CHQN, CASH_DUED, CASH_STAT);
                msboxok("Save Cash Advance Complete");
                dispose();
            }
        }


    }//GEN-LAST:event_btnSave1ActionPerformed

    private void radionormal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radionormal1ActionPerformed
        // TODO add your handling code here:
        if (radionormal1.isSelected()) {
            radiocancel1.setSelected(!radionormal1.isSelected());
            radiosubmit1.setSelected(!radionormal1.isSelected());
        }


    }//GEN-LAST:event_radionormal1ActionPerformed

    private void radiosubmit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiosubmit1ActionPerformed
        // TODO add your handling code here:
        if (radiosubmit1.isSelected()) {
            radiocancel1.setSelected(!radiosubmit1.isSelected());
            radionormal1.setSelected(!radiosubmit1.isSelected());
        }
    }//GEN-LAST:event_radiosubmit1ActionPerformed

    private void radiocancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiocancel1ActionPerformed
        // TODO add your handling code here:
        if (radiocancel1.isSelected()) {
            radiosubmit1.setSelected(!radiocancel1.isSelected());
            radionormal1.setSelected(!radiocancel1.isSelected());
        }

    }//GEN-LAST:event_radiocancel1ActionPerformed

    private void cmbstaffcode2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cmbstaffcode2InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbstaffcode2InputMethodTextChanged

    private void cmbstaffcode2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbstaffcode2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbstaffcode2ActionPerformed

    private void cmbcostcenter2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cmbcostcenter2InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbcostcenter2InputMethodTextChanged

    private void cmbcostcenter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbcostcenter2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbcostcenter2ActionPerformed

    private boolean CheckDataStep5ToSave() //STEP 2 CHECK
    {
        ClassCheckDataProgram CcheckData = new ClassCheckDataProgram();
        Boolean DateSettlement = CcheckData.CheckdatainputDate(DateSettlemAccount2.getDate());
        if (DateSettlement == false) {
            msbox("Please Check Date Settlement");
            return false;
        }
        return true;
    }

    //STEP 5 ---------------------------SAVE----------------------------------//
    private void btnsave2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsave2ActionPerformed
        // TODO add your handling code here:
        String CASH_STAT = "";
        String statussetdata;
        ClassSetdata setdata = new ClassSetdata();
        ClassCheckDataProgram cdp = new ClassCheckDataProgram();
        Classgetdata cgd = new Classgetdata();
        String CASH_CANO = txtAdvanceNo1.getText().trim();
//        if (CheckDataStep5ToSave() == false || (radiofrontwork_1.isSelected() == true && cgd.CheckLineDuedate_APS100(CASH_CANO) > 0)) {
//            msbox("Please check data of invoice !!");
//            return;
//        }

//        if (CheckDataStep5ToSave() == false || (Double.parseDouble(txtvat1.getText().trim()) > 0.00 && cgd.CheckLineDuedate_APS100(CASH_CANO) > 0)) {
        if (CheckDataStep5ToSave() == false) {
            msbox("Please check data  !!");
            return;
        }

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Save Cash Advan Settlement ?", "Warning",
                dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
            String CASH_STDA = cdp.GetDateFormatSet(DateSettlemAccount2.getDate()).trim();

            CASH_STAT = "50";
            if (radionormal3.isSelected() == true) {
                CASH_STAT = "50";
            }
            if (radiosubmit3.isSelected() == true) {
                CASH_STAT = "60";
            }
            if (radioreturn3.isSelected() == true) {
                CASH_STAT = "40";
            }

            String CASH_RTFR = txtRefundBy2.getText();

            String CASH_REAMT = "0.00";
            String CASH_RETOBANK = "";
            String WHTAXTYPE = "";

            WHTAXTYPE = txtwhtax1.getText().trim();
            if (rdoReturnSCB.isSelected() == true) {
                CASH_REAMT = txtreturn1.getText().trim();
                CASH_RETOBANK = "SCB";
            } else if (rdoReturnKBANK.isSelected() == true) {
                CASH_REAMT = txtreturn1.getText().trim();
                CASH_RETOBANK = "KBANK";
            } else if (rdoPTC.isSelected() == true) {
                CASH_REAMT = txtrefund1.getText().trim();
                CASH_RETOBANK = "Petty Cash";
            } else if (rdoTransBank.isSelected() == true) {
                String[] cmbstaffcode_h = cmbstaffcode2.getSelectedItem().toString().trim().split(":");
                CASH_RETOBANK = cmbstaffcode_h[0];
                CASH_REAMT = txtrefund1.getText().trim();
            } else {
                CASH_RETOBANK = "";
                CASH_REAMT = "0.00";
            }

            model = (DefaultTableModel) griddetailacstep5.getModel();

            if (CASH_CANO != "" && !CASH_CANO.isEmpty()) {

//                if (CASH_STAT.equals("60")) {
//                    Double VAT = Double.parseDouble(txtvat1.getText().trim());
//                    Double ReturnCompany = Double.parseDouble(txtreturn1.getText().trim());
//                    Double RefundCompany = Double.parseDouble(txtrefund1.getText().trim());
//                    String Advance = txtAdvanceNo2.getText().trim();
//                    String[] Staffcode = txtcustomer.getText().trim().split(":");
//                    String WHTAX = "NONE";
//                    if (radiotaxchec_1.isSelected()) {
//                        WHTAX = "CASH_CHECK";
//                    } else if (radiofrontwork_1.isSelected()) {
//                        WHTAX = "CASH_FRONT";
//                    }
//                    String DateAccountSettle = cdp.SubDateForM3(CASH_STDA);
//
//                    String Return_Refund = "NONE";
//                    if (ReturnCompany > 0.00) {
//                        Return_Refund = "ReturnCompany";
//                    } else if (RefundCompany > 0.00) {
//                        Return_Refund = "RefundCompany";
//                    }
//                    if (WHTAX.equals("NONE") && Return_Refund.equals("NONE") && VAT == 0.00)  {
//                    try {
//                        ResultSet rsl = cgd.GetAdvanceDetailM3(Advance);
//                        String[] SETT_COST = new String[20], SETT_AMTB = new String[20], ACCODE = new String[20], ServiceCode = new String[20], VATCODE = new String[20], InvNO = new String[20], InvAccDate = new String[20],
//                                InvDate = new String[20], InvAmt = new String[20], InvSupp = new String[20], GenVoucherText = new String[20], SETT_DUEDATE = new String[20],
//                                EATX40 = new String[20];
//                        Double[] VAT_C = new Double[20], VAT_UC = new Double[20];
//                        Double InvAmtAll = 0.00, AdvAMT = 0.00;
//                        int i = 0;
//                        int x = 0;
//                        while (rsl.next()) {
//                            VAT_C[i] = cdp.Double2digitReturn(rsl.getDouble("VAT_C"));
//                            VAT_UC[i] = cdp.Double2digitReturn(rsl.getDouble("VAT_UC"));
//                            SETT_COST[i] = rsl.getString("SETT_COST").trim();
//                            SETT_AMTB[i] = rsl.getString("SETT_AMTB").trim();
//                            ACCODE[i] = rsl.getString("ACCODE").trim();
//                            VATCODE[i] = rsl.getString("SETT_CDVAT").trim();
//                            InvNO[i] = rsl.getString("SETT_INVC").trim();
//                            InvAccDate[i] = cdp.SubDateForM3(rsl.getString("SETT_INVD").trim());
//                            InvDate[i] = cdp.SubDateForM3(rsl.getString("SETT_INVD").trim());
//                            InvAmt[i] = rsl.getString("SETT_AMTT").trim();
//                            InvSupp[i] = rsl.getString("SETT_SUPP").trim();
//                            GenVoucherText[i] = rsl.getString("SETT_VCTXT").trim();
//                            if (!rsl.getString("SETT_DUEDT").trim().equals("0")) {
//                                SETT_DUEDATE[i] = cdp.SubDateForM3(rsl.getString("SETT_DUEDT").trim());
//                            }
//                            EATX40[i] = rsl.getString("EATX40").trim();
//                            InvAmtAll += rsl.getDouble("SETT_AMTT");
//                            AdvAMT = rsl.getDouble("CASH_AMT");
//
//                            i++;
//                        }
//                        if (WHTAX.equals("NONE") && VAT == 0.00) {
//
//                            if ((rdoPTC.isSelected() || rdoTransBank.isSelected()) && Double.parseDouble(txtrefund1.getText().trim()) > 0.00) {
//                                String msg = SMFACC.callARS110_CaseRefund(Advance, Staffcode[0], DateAccountSettle);
//                                msbox(msg);
//                            } else {
//                                String msg = SMFACC.callARS110_V2(Advance, Staffcode[0], DateAccountSettle);
//                                msbox(msg);
//                            }
//
//                            System.out.println("Case 1 : Expenses with out VAT and W/H");
//                        } else if (WHTAX.equals("NONE") && VAT > 0.00) {
//                            for (x = 0; x < i;) {
//
//                                if (EATX40[x].equalsIgnoreCase("UCN")) {
//                                    x++;
//                                    continue;
//                                } else {
//
//                                    String msg = SMFACC.callAPS100(VAT_C[x], VAT_UC[x], SETT_COST[x], SETT_AMTB[x], ACCODE[x], InvNO[x], DateAccountSettle, InvDate[x], InvAmt[x], InvSupp[x], GenVoucherText[x],
//                                            SETT_DUEDATE[x], EATX40[x], VATCODE[x], Advance, "", Staffcode[0]);
//                                    msboxok(InvNO[x] + " " + msg);
//                                    String[] Vouchermss = msg.toString().split(" ");
//                                    String Voucher = Vouchermss[2].trim();
//                                    if (Voucher.length() == 8) {
//                                        setdata.UpdateVoucher_APS(Advance, Voucher, InvNO[x]);
//                                    }
//                                    x++;
//                                }
//                            }
//
//                            String msg = SMFACC.callARS100_Offset(Advance, Staffcode[0], DateAccountSettle);
//                            msboxok(msg);
//
//                            System.out.println("Case 2 : Expenses with VAT claim");
//                        } else if (WHTAX.equals("CASH_CHECK")) {
//
//                            String msg = SMFACC.Offset_Case_Normal(Advance, Staffcode[0], AdvAMT, DateAccountSettle);
//                            msboxok(msg);
//
//                            System.out.println("Case 3-4 : Expenses with VAT claim and W/H\n" + msg);
//                        } else if (WHTAX.equals("CASH_FRONT")) {
//
//                            for (x = 0; x < i;) {
//                                if (EATX40[x].equalsIgnoreCase("UCN")) {
//                                    x++;
//                                    continue;
//                                } else {
//                                    String msg = SMFACC.callAPS100(VAT_C[x], VAT_UC[x], SETT_COST[x], SETT_AMTB[x], ACCODE[x], InvNO[x], InvAccDate[x], InvDate[x], InvAmt[x], InvSupp[x], GenVoucherText[x],
//                                            SETT_DUEDATE[x], EATX40[x], VATCODE[x], Advance, "", Staffcode[0]);
//                                    msboxok(InvNO[x] + " " + msg);
//                                    String[] Vouchermss = msg.toString().split(" ");
//                                    String Voucher = Vouchermss[2].trim();
//                                    if (Voucher.length() == 8) {
//                                        setdata.UpdateVoucher_APS(Advance, Voucher, InvNO[x]);
//                                    }
//                                    x++;
//                                }
//                            }
//
//                            SMFACC.callARS100_Offset_CashFront(Advance, Staffcode[0], DateAccountSettle);
//                            System.out.println("Case 5 : Expenses with Return to Company");
//                        }
//                    } catch (Exception ex) {
//                        System.out.println(ex.toString());
//                    }
//                    this.setVisible(false);
//                }
                CASH_CANO = setdata.SaveCashAdvanceAccountStep5Form(CASH_CANO, CASH_STDA, CASH_RTFR, CASH_STAT, WHTAXTYPE, CASH_REAMT, CASH_RETOBANK);
                if (CASH_STAT.equals("60")) {
                    btnsave2.setEnabled(false);
                    btncancel2.setEnabled(false);
                    ClassgetReport cgr = new ClassgetReport();
                    cgr.getReportACC_1(CASH_STDA, CASH_STDA);
                }
                msboxok("Save Cash Advance Settlement Complete");
            } else {
                msboxok("Save Cash Advance Settlement Fail");
            }

        }


    }//GEN-LAST:event_btnsave2ActionPerformed

    private void Get_DetailGridAcc(String CashAdvanceNo) {

        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetDataAdvanceDetailResultData(CashAdvanceNo);
        try {

            model2 = (DefaultTableModel) griddetailac.getModel();
            int iiii = 0;
            Double Advamt = Double.parseDouble(txtamount2.getText().trim());
            while (rs.next()) {
                model2.setValueAt(rs.getString("SETT_INVC").trim(), iiii, 0);
                model2.setValueAt(rs.getString("SETT_SUPP").trim(), iiii, 1);
                model2.setValueAt(cgd.Get_SupplierNameWithCode(rs.getString("SETT_SUPP").trim()), iiii, 2);
                model2.setValueAt(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("SETT_INVD"))), iiii, 3);
                model2.setValueAt(rs.getString("SETT_DESC").trim(), iiii, 4);
                model2.setValueAt(rs.getString("SETT_COST").trim(), iiii, 5);
                model2.setValueAt(rs.getDouble("SETT_AMTB"), iiii, 6);
                model2.setValueAt(rs.getDouble("SETT_VATC"), iiii, 7);
                model2.setValueAt(rs.getDouble("SETT_VATA"), iiii, 8);
                model2.setValueAt(rs.getString("SETT_NODES"), iiii, 9);
                model2.setValueAt(rs.getDouble("SETT_AMTT"), iiii, 10);
                model2.setValueAt(rs.getString("SETT_BRAC"), iiii, 11);
                model2.setValueAt(cgd.Get_DescMethodPay(rs.getString("SETT_RETT")), iiii, 12);
//                String VCTXT = rs.getString("SETT_VCTXT").trim();
//                String Duedt = rs.getString("SETT_DUEDT").trim();
                if (rs.getString("SETT_VCTXT") == null) {
                    model2.setValueAt("-", iiii, 13);
                } else {
                    model2.setValueAt(rs.getString("SETT_VCTXT"), iiii, 13);
                }
                if (rs.getString("SETT_DUEDT").equals("0")) {
                    model2.setValueAt("-", iiii, 14);
                } else {
                    model2.setValueAt(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("SETT_DUEDT"))), iiii, 14);
                }
                iiii++;
            }
            rs.close();
            model2.fireTableDataChanged();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    private void Get_DetailGrid_StepCheck(String CashAdvanceNo) {

        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetDataAdvanceDetailResultData(CashAdvanceNo);
        try {
            //    SetModel();
            model = (DefaultTableModel) griddetailacstep6.getModel();
            int iiii = 0;
            Double amt = 0.00;
            Double vat = 0.00;
            Double amtBeforeVat = 0.00;
            Double Sum = 0.00;
            Double Advamt = Double.parseDouble(txtamount3.getText().trim());
            while (rs.next()) {
                //  String Nassme= cgd.GetItemName_MitMas(rs.getString("EPRL_ITNO").trim());
                model.setValueAt(rs.getString("SETT_INVC").trim(), iiii, 0);
                model.setValueAt(rs.getString("SETT_NODES").trim(), iiii, 1);
                model.setValueAt(cgd.Get_SupplierNameWithCode(rs.getString("SETT_SUPP").trim()), iiii, 2);
                model.setValueAt(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("SETT_INVD"))), iiii, 3);

                model.setValueAt(rs.getString("SETT_DESC").trim(), iiii, 4);
                model.setValueAt(rs.getString("SETT_COST").trim(), iiii, 5);

                model.setValueAt(rs.getDouble("SETT_AMTB"), iiii, 6);
                model.setValueAt(rs.getDouble("SETT_VATC"), iiii, 7);
                model.setValueAt(rs.getDouble("SETT_VATA"), iiii, 8);
                model.setValueAt(rs.getDouble("SETT_AMTT"), iiii, 9);
                model.setValueAt(rs.getString("SETT_BRAC"), iiii, 10);

                model.setValueAt(cgd.Get_DescMethodPay(rs.getString("SETT_RETT")), iiii, 11);
//                String VCTXT = rs.getString("SETT_VCTXT").trim();
//                String Duedt = rs.getString("SETT_DUEDT").trim();
                if (rs.getString("SETT_VCTXT") == null) {
                    model.setValueAt("-", iiii, 12);
                } else {
                    model.setValueAt(rs.getString("SETT_VCTXT"), iiii, 12);
                }
                if (rs.getString("SETT_DUEDT") == null || rs.getString("SETT_DUEDT").equalsIgnoreCase("0")) {
                    model.setValueAt("-", iiii, 13);
                } else {
                    model.setValueAt(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("SETT_DUEDT"))), iiii, 13);
                }
                amt += rs.getDouble("SETT_AMTT");
                vat += rs.getDouble("SETT_VATA");
                amtBeforeVat += rs.getDouble("SETT_AMTB");
                iiii++;

            }
            amtBeforeVat = checkdataprogram.Double2digitReturn(amtBeforeVat);
            amt = checkdataprogram.Double2digitReturn(amt);
            vat = checkdataprogram.Double2digitReturn(vat);
            Cal_AmountSettlementWithGrid_StepCheck(Advamt, amtBeforeVat, vat, amt);

            rs.close();
            model.fireTableDataChanged();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    private void Get_DetailGrid(String CashAdvanceNo) {

        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetDataAdvanceDetailResultData(CashAdvanceNo);
        try {
            //    SetModel();
            model = (DefaultTableModel) griddetailacstep5.getModel();
            int iiii = 0;
            Double amt = 0.00;
            Double vat = 0.00;
            Double amtBeforeVat = 0.00;
            Double Sum = 0.00;
            Double Advamt = Double.parseDouble(txtamount2.getText().trim());
            while (rs.next()) {
                //  String Nassme= cgd.GetItemName_MitMas(rs.getString("EPRL_ITNO").trim());
                model.setValueAt(rs.getString("SETT_INVC").trim(), iiii, 0);
                model.setValueAt(rs.getString("SETT_NODES").trim(), iiii, 1);
                model.setValueAt(cgd.Get_SupplierNameWithCode(rs.getString("SETT_SUPP").trim()), iiii, 2);
                model.setValueAt(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("SETT_INVD"))), iiii, 3);

                model.setValueAt(rs.getString("SETT_DESC").trim(), iiii, 4);
                model.setValueAt(rs.getString("SETT_COST").trim(), iiii, 5);

                model.setValueAt(rs.getDouble("SETT_AMTB"), iiii, 6);
                model.setValueAt(rs.getDouble("SETT_VATC"), iiii, 7);
                model.setValueAt(rs.getDouble("SETT_VATA"), iiii, 8);
                model.setValueAt(rs.getDouble("SETT_AMTT"), iiii, 9);
                model.setValueAt(rs.getString("SETT_BRAC"), iiii, 10);

                model.setValueAt(cgd.Get_DescMethodPay(rs.getString("SETT_RETT")), iiii, 11);
//                String VCTXT = rs.getString("SETT_VCTXT").trim();
//                String Duedt = rs.getString("SETT_DUEDT").trim();
                if (rs.getString("SETT_VCTXT") == null) {
                    model.setValueAt("-", iiii, 12);
                } else {
                    model.setValueAt(rs.getString("SETT_VCTXT"), iiii, 12);
                }
                if (rs.getString("SETT_DUEDT") == null || rs.getString("SETT_DUEDT").equalsIgnoreCase("0")) {
                    model.setValueAt("-", iiii, 13);
                } else {
                    model.setValueAt(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("SETT_DUEDT"))), iiii, 13);
                }
                amt += rs.getDouble("SETT_AMTT");
                vat += rs.getDouble("SETT_VATA");
                amtBeforeVat += rs.getDouble("SETT_AMTB");
                iiii++;

            }
            amtBeforeVat = checkdataprogram.Double2digitReturn(amtBeforeVat);
            amt = checkdataprogram.Double2digitReturn(amt);
            vat = checkdataprogram.Double2digitReturn(vat);
            Cal_AmountSettlementWithGrid(Advamt, amtBeforeVat, vat, amt);

            rs.close();
            model.fireTableDataChanged();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    private void Cal_WHTAX() {
        Double AdvAmt = Double.parseDouble(txtamount1.getText().trim());
        Double AmtBFV = Double.parseDouble(txtamtbfvat1.getText().trim());
        Double AmtVAT = Double.parseDouble(txtvat1.getText().trim());
        Double AmtWHTAX = Double.parseDouble(txtwhtax1.getText().trim());
        Double Sum = 0.00;
        txtreturn1.setText(String.valueOf(Sum).trim());
        txtrefund1.setText(String.valueOf(Sum).trim());
        Sum = AdvAmt - ((AmtBFV + AmtVAT) - AmtWHTAX);
        Set_RdoSettlement(false);
        if (Sum > 0.00) {
            txtreturn1.setText(String.valueOf(Sum).trim());
            rdoReturnSCB.setEnabled(true);
            rdoReturnKBANK.setEnabled(true);
            ClearBanktxt();
        } else {
            if (Sum != 0.00) {
                Sum = Sum * (-1);
            }
            txtrefund1.setText(String.valueOf(Sum).trim());
            if (Sum < 1000.00 && Sum != 0.00) {
                rdoPTC.setSelected(true);
                rdoPTC.setEnabled(true);
                ClearBanktxt();

            } else if (Sum >= 1000.00) {
                rdoTransBank.setSelected(true);
                rdoTransBank.setEnabled(true);
            } else {
                Set_RdoSettlement(false);
            }
        }
    }

    private void Set_RdoSettlement(boolean Status) {
        rdoReturnSCB.setEnabled(Status);
        rdoReturnKBANK.setEnabled(Status);
        rdoPTC.setEnabled(Status);
        rdoTransBank.setEnabled(Status);
        rdoPTC.setSelected(Status);
        rdoReturnSCB.setSelected(Status);
        rdoReturnKBANK.setSelected(Status);
        rdoTransBank.setSelected(Status);
    }

    private void ClearBanktxt() {
        txtBCode.setText("");
        txtBName.setText("");
    }

    private void Cal_AmountSettlementWithGrid(Double Advamt, Double AmtBfVat, Double AmtVat, Double TotalAmt) {
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        Double Sum = 0.00;

        txtamtbfvat1.setText(String.valueOf(AmtBfVat).trim());
        txtvat1.setText(String.valueOf(AmtVat).trim());
        txtamt1.setText(String.valueOf(TotalAmt).trim());

//        Cal_WHTAX();
        Double WHTAX = Double.parseDouble(txtwhtax1.getText().trim());
        txtwhtax1.setText(String.valueOf(WHTAX).trim());
        Sum = Advamt - (TotalAmt - WHTAX);
        Sum = checkdataprogram.Double2digitReturn(Sum);
//        Set_RdoSettlement(false);
        if (Sum > 0.00) {
            txtreturn1.setText(String.valueOf(Sum).trim());
            txtrefund1.setText("0.00");
            rdoReturnSCB.setEnabled(true);
            rdoReturnKBANK.setEnabled(true);
            ClearBanktxt();
        } else {
            if (Sum != 0.00) {
                Sum = Sum * (-1);
            }
            txtrefund1.setText(String.valueOf(Sum).trim());
            txtreturn1.setText("0.00");
            if (Sum < 1000.00 && Sum != 0.00) {
                rdoPTC.setSelected(true);
                rdoPTC.setEnabled(true);
                ClearBanktxt();

            } else if (Sum >= 1000.00) {
                rdoTransBank.setSelected(true);
                rdoTransBank.setEnabled(true);
            } else {
                Set_RdoSettlement(false);
            }
        }

    }

    private void Cal_AmountSettlementWithGrid_StepCheck(Double Advamt, Double AmtBfVat, Double AmtVat, Double TotalAmt) {
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        Double Sum = 0.00;

        txtamtbfvat2.setText(String.valueOf(AmtBfVat).trim());
        txtvat2.setText(String.valueOf(AmtVat).trim());
        txtamt2.setText(String.valueOf(TotalAmt).trim());

//        Cal_WHTAX();
        Double WHTAX = Double.parseDouble(txtwhtax2.getText().trim());
        txtwhtax2.setText(String.valueOf(WHTAX).trim());
        Sum = Advamt - (TotalAmt - WHTAX);
        Sum = checkdataprogram.Double2digitReturn(Sum);
//        Set_RdoSettlement(false);
        if (Sum > 0.00) {
            txtreturn2.setText(String.valueOf(Sum).trim());
            txtrefund2.setText("0.00");
            rdoReturnSCB1.setEnabled(true);
            rdoReturnKBANK1.setEnabled(true);
            ClearBanktxt();
        } else {
            if (Sum != 0.00) {
                Sum = Sum * (-1);
            }
            txtrefund2.setText(String.valueOf(Sum).trim());
            txtreturn2.setText("0.00");
            if (Sum < 1000.00 && Sum != 0.00) {
                rdoPTC1.setSelected(true);
                rdoPTC1.setEnabled(true);
                ClearBanktxt();

            } else if (Sum >= 1000.00) {
                rdoTransBank1.setSelected(true);
                rdoTransBank1.setEnabled(true);
            } else {
                Set_RdoSettlement(false);
            }
        }

    }
    private void radioreturn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioreturn3ActionPerformed
        // TODO add your handling code here:
        if (radioreturn3.isSelected()) {
            radionormal3.setSelected(!radioreturn3.isSelected());
            radiosubmit3.setSelected(!radioreturn3.isSelected());
        }
    }//GEN-LAST:event_radioreturn3ActionPerformed

    private void radionormal3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radionormal3ActionPerformed
        // TODO add your handling code here:
        if (radionormal3.isSelected()) {
            radiosubmit3.setSelected(!radionormal3.isSelected());
            radioreturn3.setSelected(!radionormal3.isSelected());
        }
    }//GEN-LAST:event_radionormal3ActionPerformed

    private void radiosubmit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiosubmit3ActionPerformed
        // TODO add your handling code here:
        if (radiosubmit3.isSelected()) {
            radionormal3.setSelected(!radiosubmit3.isSelected());
            radioreturn3.setSelected(!radiosubmit3.isSelected());
        }
    }//GEN-LAST:event_radiosubmit3ActionPerformed

    private void griddetailacstep5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_griddetailacstep5MouseReleased
        // TODO add your handling code here:
        //   SetCalculateDetailModel();

    }//GEN-LAST:event_griddetailacstep5MouseReleased

    private void jRadioshowreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioshowreportActionPerformed
        // TODO add your handling code here:
        if (jRadioshowreport.isSelected()) {
            radionormal.setSelected(!jRadioshowreport.isSelected());
            radiosubmit.setSelected(!jRadioshowreport.isSelected());
            radiocancel.setSelected(!jRadioshowreport.isSelected());
            radioreturn.setSelected(!jRadioshowreport.isSelected());
        }
    }//GEN-LAST:event_jRadioshowreportActionPerformed

    private void txtDateAdvanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateAdvanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateAdvanceActionPerformed

    private void txtcustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcustomerActionPerformed

    private void btchequenumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btchequenumberActionPerformed

        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetSqlDatachequenumber(txtAdvanceNo1.getText());

        try {

            while (rs.next()) {

                txtbank1.setText(rs.getString("CKBKID"));
                txtchqno1.setText(rs.getString("CKCHKN"));
                DateCheqDate1.setDate(checkdataprogram.GetDecmalTodate(rs.getInt("CKCADA")));

            }

            rs.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }//GEN-LAST:event_btchequenumberActionPerformed

    private void radionontaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radionontaxActionPerformed
        if (radionontax.isSelected()) {
            radiofrontwork.setSelected(!radionontax.isSelected());
            radiotaxchec.setSelected(!radionontax.isSelected());

        }
    }//GEN-LAST:event_radionontaxActionPerformed

    private void radiofrontworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiofrontworkActionPerformed
        if (radiofrontwork.isSelected()) {
            radionontax.setSelected(!radiofrontwork.isSelected());
            radiotaxchec.setSelected(!radiofrontwork.isSelected());
        }
    }//GEN-LAST:event_radiofrontworkActionPerformed

    private void btncancel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancel2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btncancel2ActionPerformed

    private void rdoReturnSCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoReturnSCBActionPerformed
        if (rdoReturnSCB.isSelected()) {
            rdoReturnKBANK.setSelected(!rdoReturnSCB.isSelected());
        }
    }//GEN-LAST:event_rdoReturnSCBActionPerformed

    private void rdoReturnKBANKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoReturnKBANKActionPerformed
        if (rdoReturnKBANK.isSelected()) {
            rdoReturnSCB.setSelected(!rdoReturnKBANK.isSelected());
        }
    }//GEN-LAST:event_rdoReturnKBANKActionPerformed

    private void griddetailacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_griddetailacMouseClicked
        frmAddLineInv.ProgramStep = "EditExpense";
        new frmAddLineInv().setVisible(true);
    }//GEN-LAST:event_griddetailacMouseClicked

    private void griddetailacMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_griddetailacMouseReleased
        // TODO add your handling code here:
//        SetCalculateDetailModel();
    }//GEN-LAST:event_griddetailacMouseReleased

    private void txtwhtax1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtwhtax1ActionPerformed
        if (txtwhtax1.getText().trim().equalsIgnoreCase("") || txtwhtax1.getText().trim().equalsIgnoreCase(null)) {
            txtwhtax1.setText("0.00");
        }
        Cal_WHTAX();
    }//GEN-LAST:event_txtwhtax1ActionPerformed

    private void btn_previewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previewActionPerformed
        ClassgetReport cgr = new ClassgetReport();
        cgr.getReportPreviewStep1(txtAdvanceNo.getText().trim());
    }//GEN-LAST:event_btn_previewActionPerformed

    private void griddetailacstep5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_griddetailacstep5MouseClicked
        frmAddLineInv.ProgramStep = "EditExpenseStep4";
        new frmAddLineInv().setVisible(true);
    }//GEN-LAST:event_griddetailacstep5MouseClicked

    private void btncancel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancel3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btncancel3ActionPerformed

    private void btnsave3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsave3ActionPerformed
        String CASH_CANO = txtAdvanceNo3.getText().trim();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Save Cash Advan Settlement ?", "Warning",
                dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {

            String CASH_STAT = "45";

            if (radionormal4.isSelected() == true) {
                CASH_STAT = "45";
            }
            if (radiosubmit4.isSelected() == true) {
                CASH_STAT = "60";
            }
            if (radioreturn4.isSelected() == true) {
                CASH_STAT = "40";
            }
            ClassSetdata setdata = new ClassSetdata();
            msboxok("Save Cash Advance Check Settlement Complete");
            CASH_CANO = setdata.SaveCashAdvanceAccountStepCheckSettle(CASH_CANO, CASH_STAT);
            btnsave3.setEnabled(false);
            btncancel3.setEnabled(false);

        }
    }//GEN-LAST:event_btnsave3ActionPerformed

    private void radioreturn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioreturn4ActionPerformed
        if (radioreturn4.isSelected()) {
            radionormal4.setSelected(!radioreturn4.isSelected());
            radiosubmit4.setSelected(!radioreturn4.isSelected());
        }
    }//GEN-LAST:event_radioreturn4ActionPerformed

    private void radionormal4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radionormal4ActionPerformed
        if (radionormal4.isSelected()) {
            radiosubmit4.setSelected(!radionormal4.isSelected());
            radioreturn4.setSelected(!radionormal4.isSelected());
        }
    }//GEN-LAST:event_radionormal4ActionPerformed

    private void radiosubmit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiosubmit4ActionPerformed
        if (radiosubmit4.isSelected()) {
            radionormal4.setSelected(!radiosubmit4.isSelected());
            radioreturn4.setSelected(!radiosubmit4.isSelected());
        }
    }//GEN-LAST:event_radiosubmit4ActionPerformed

    private void txtwhtax2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtwhtax2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtwhtax2ActionPerformed

    private void rdoReturnSCB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoReturnSCB1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoReturnSCB1ActionPerformed

    private void rdoReturnKBANK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoReturnKBANK1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoReturnKBANK1ActionPerformed

    private void cmbcostcenter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbcostcenter3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbcostcenter3ActionPerformed

    private void btn_preview1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_preview1ActionPerformed
        ClassgetReport cgr = new ClassgetReport();
        cgr.getReportPreviewStep1(txtAdvanceNo.getText().trim());
    }//GEN-LAST:event_btn_preview1ActionPerformed

    private void radiotaxchecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiotaxchecActionPerformed
        if (radiotaxchec.isSelected()) {
            radiofrontwork.setSelected(!radiotaxchec.isSelected());
            radionontax.setSelected(!radiotaxchec.isSelected());

        }

    }//GEN-LAST:event_radiotaxchecActionPerformed

    private void radio_transferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_transferActionPerformed
        if (radio_transfer.isSelected() == true) {
            radio_cheque.setSelected(false);
            radio_transfer.setSelected(true);

        }
    }//GEN-LAST:event_radio_transferActionPerformed

    private void radio_chequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_chequeActionPerformed
        if (radio_cheque.isSelected() == true) {
            radio_cheque.setSelected(true);
            radio_transfer.setSelected(false);
        }
    }//GEN-LAST:event_radio_chequeActionPerformed

    private void radionontax_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radionontax_2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radionontax_2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CashAdvanAccounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CashAdvanAccounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CashAdvanAccounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CashAdvanAccounting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CashAdvanAccounting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker DateAccount;
    private org.jdesktop.swingx.JXDatePicker DateAccount1;
    private org.jdesktop.swingx.JXDatePicker DateCheqDate1;
    private org.jdesktop.swingx.JXDatePicker DateOperationFrom;
    private org.jdesktop.swingx.JXDatePicker DateOperationFrom1;
    private org.jdesktop.swingx.JXDatePicker DateOperationTo;
    private org.jdesktop.swingx.JXDatePicker DateOperationTo1;
    private org.jdesktop.swingx.JXDatePicker DatePlanReceive1;
    private org.jdesktop.swingx.JXDatePicker DatePlanSettlemAccount2;
    private org.jdesktop.swingx.JXDatePicker DatePlanSettlemAccount3;
    private org.jdesktop.swingx.JXDatePicker DatePlanSettlement1;
    private org.jdesktop.swingx.JXDatePicker DateReceive;
    private org.jdesktop.swingx.JXDatePicker DateReceive1;
    private org.jdesktop.swingx.JXDatePicker DateSettlemAccount2;
    private org.jdesktop.swingx.JXDatePicker DateSettlement;
    private org.jdesktop.swingx.JXDatePicker DateSettlement2;
    private org.jdesktop.swingx.JXDatePicker DateSettlement3;
    private javax.swing.JButton btchequenumber;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSave1;
    private javax.swing.JButton btn_preview;
    private javax.swing.JButton btn_preview1;
    private javax.swing.JButton btncancel1;
    private javax.swing.JButton btncancel2;
    private javax.swing.JButton btncancel3;
    private javax.swing.JButton btnsave2;
    private javax.swing.JButton btnsave3;
    public static javax.swing.JTextField cmb_IT1GP;
    public static javax.swing.JTextField cmb_IT1GP_2;
    public static javax.swing.JTextField cmb_IT1GP_3;
    private javax.swing.JComboBox<String> cmbcostcenter;
    private javax.swing.JComboBox<String> cmbcostcenter1;
    private javax.swing.JComboBox<String> cmbcostcenter2;
    private javax.swing.JTextField cmbcostcenter3;
    private javax.swing.JComboBox<String> cmbstaffcode;
    private javax.swing.JComboBox<String> cmbstaffcode1;
    public static javax.swing.JComboBox<String> cmbstaffcode2;
    private javax.swing.JTextField cmbstaffcode3;
    public static javax.swing.JTable griddetailac;
    public static javax.swing.JTable griddetailacstep5;
    public static javax.swing.JTable griddetailacstep6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioshowreport;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private java.awt.Label label17;
    private java.awt.Label lblCompanyName;
    private java.awt.Panel panel1;
    private javax.swing.JRadioButton radio_cheque;
    private javax.swing.JRadioButton radio_transfer;
    private javax.swing.JRadioButton radiocancel;
    private javax.swing.JRadioButton radiocancel1;
    public static javax.swing.JRadioButton radiofrontwork;
    public static javax.swing.JRadioButton radiofrontwork_1;
    public static javax.swing.JRadioButton radiofrontwork_2;
    public static javax.swing.JRadioButton radionontax;
    private javax.swing.JRadioButton radionontax_1;
    public static javax.swing.JRadioButton radionontax_2;
    private javax.swing.JRadioButton radionormal;
    private javax.swing.JRadioButton radionormal1;
    private javax.swing.JRadioButton radionormal3;
    private javax.swing.JRadioButton radionormal4;
    private javax.swing.JRadioButton radioreturn;
    private javax.swing.JRadioButton radioreturn3;
    private javax.swing.JRadioButton radioreturn4;
    private javax.swing.JRadioButton radiosubmit;
    private javax.swing.JRadioButton radiosubmit1;
    private javax.swing.JRadioButton radiosubmit3;
    private javax.swing.JRadioButton radiosubmit4;
    public static javax.swing.JRadioButton radiotaxchec;
    public static javax.swing.JRadioButton radiotaxchec_1;
    public static javax.swing.JRadioButton radiotaxchec_2;
    public static javax.swing.JRadioButton rdoPTC;
    public static javax.swing.JRadioButton rdoPTC1;
    public static javax.swing.JRadioButton rdoReturnKBANK;
    public static javax.swing.JRadioButton rdoReturnKBANK1;
    public static javax.swing.JRadioButton rdoReturnSCB;
    public static javax.swing.JRadioButton rdoReturnSCB1;
    public static javax.swing.JRadioButton rdoTransBank;
    public static javax.swing.JRadioButton rdoTransBank1;
    public static java.awt.TextField txtAdvanceNo;
    private java.awt.TextField txtAdvanceNo1;
    private java.awt.TextField txtAdvanceNo2;
    private java.awt.TextField txtAdvanceNo3;
    public static javax.swing.JTextField txtBCode;
    public static javax.swing.JTextField txtBCode1;
    public static javax.swing.JTextField txtBName;
    public static javax.swing.JTextField txtBName1;
    private java.awt.TextField txtCheckedBy;
    private java.awt.TextField txtDateAdvance;
    private java.awt.TextField txtDateAdvance1;
    private java.awt.TextField txtDateAdvance2;
    private java.awt.TextField txtDateAdvance3;
    private java.awt.TextField txtRefPo;
    private java.awt.TextField txtRefPo1;
    private java.awt.TextField txtRefundBy2;
    private java.awt.TextField txtRefundBy3;
    public static javax.swing.JTextField txt_PONO;
    public static javax.swing.JTextField txt_PONO1;
    private java.awt.TextField txtamount;
    private java.awt.TextField txtamount1;
    public static javax.swing.JTextField txtamount2;
    public static javax.swing.JTextField txtamount3;
    public static javax.swing.JTextField txtamt1;
    public static javax.swing.JTextField txtamt2;
    public static javax.swing.JTextField txtamtbfvat1;
    public static javax.swing.JTextField txtamtbfvat2;
    private java.awt.TextField txtbank1;
    private java.awt.TextField txtchqno1;
    private java.awt.TextField txtchqno2;
    public static javax.swing.JTextField txtcustomer;
    private java.awt.TextField txtpurpose1_1;
    private java.awt.TextField txtpurpose1_2;
    private java.awt.TextField txtpurpose1_3;
    private java.awt.TextField txtpurpose_1;
    private java.awt.TextField txtpurpose_2;
    private java.awt.TextField txtpurpose_3;
    private java.awt.TextField txtrecord1;
    public static javax.swing.JTextField txtrefund1;
    public static javax.swing.JTextField txtrefund2;
    public static javax.swing.JTextField txtreturn1;
    public static javax.swing.JTextField txtreturn2;
    public static javax.swing.JTextField txtvat1;
    public static javax.swing.JTextField txtvat2;
    public static javax.swing.JTextField txtwhtax1;
    public static javax.swing.JTextField txtwhtax2;
    // End of variables declaration//GEN-END:variables
}
