/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashadvance_multicompany;

import static cashadvance_multicompany.CashAdvanAccounting.griddetailac;
import static cashadvance_multicompany.CashAdvanAccounting.txtAdvanceNo;
import static cashadvance_multicompany.CashAdvanRequest.cmbSupplierADV;
import static cashadvance_multicompany.CashAdvanRequest.cmb_IT1GP;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import static cashadvance_multicompany.CashAdvanRequest.cmbstaffcode;
import static cashadvance_multicompany.CashAdvanRequest.griddetail;
import static cashadvance_multicompany.CashAdvanRequest.rdoPTC;
import static cashadvance_multicompany.CashAdvanRequest.rdoReturnKBANK;
import static cashadvance_multicompany.CashAdvanRequest.rdoReturnSCB;
import static cashadvance_multicompany.CashAdvanRequest.rdoTransBank;
import static cashadvance_multicompany.CashAdvanRequest.txtAdvanceNo1;

import static cashadvance_multicompany.CashAdvanRequest.txtaccname;
import static cashadvance_multicompany.CashAdvanRequest.txtaccno;
import static cashadvance_multicompany.CashAdvanRequest.txtamount1;
import static cashadvance_multicompany.CashAdvanRequest.txtamt1;
import static cashadvance_multicompany.CashAdvanRequest.txtamtbfvat1;
import static cashadvance_multicompany.CashAdvanRequest.txtbranch;
import static cashadvance_multicompany.CashAdvanRequest.txtrefund1;
import static cashadvance_multicompany.CashAdvanRequest.txtreturn1;
import static cashadvance_multicompany.CashAdvanRequest.txtvat1;
import static cashadvance_multicompany.CashAdvanRequest.txtwhtax1;
import static cashadvance_multicompany.frmLogin.LoginCono;
import static cashadvance_multicompany.frmLogin.LoginDivision;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static cashadvance_multicompany.CashAdvanRequest.txtBCode;
import static cashadvance_multicompany.CashAdvanRequest.txtBName;
import java.text.DecimalFormat;
import static cashadvance_multicompany.CashAdvanRequest.importduty;
import static cashadvance_multicompany.CashAdvanRequest.modeforreceive;
import static cashadvance_multicompany.CashAdvanRequest.DateTransfer;
import static cashadvance_multicompany.CashAdvanRequest.jLabel15;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Monthon
 */
public class frmAddLineInv extends javax.swing.JFrame {

    public static DefaultTableModel model;
    public static String ProgramStep = "";
    public static DefaultTableModel model2;

    /**
     * Creates new form frmsearch
     */
    public frmAddLineInv() {
        initComponents();
        if (importduty) {
            jLabel23.setText("Vat");
        }
        this.setTitle("Cash Advance Request : Add Invoice");
//        sharesv1.setVisible(true);
        if (ProgramStep.equals("EditExpense")) {
            SETT_BRAC.setVisible(false);
            ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();
            String CashAdvNo = CashAdvanAccounting.txtAdvanceNo.getText().trim();
            int selectedRowIndex = 0;
            String Expense = null;
            DefaultTableModel model2;
            model2 = (DefaultTableModel) CashAdvanAccounting.griddetailac.getModel();
            selectedRowIndex = CashAdvanAccounting.griddetailac.getSelectedRow();

            Expense = (model2.getValueAt(selectedRowIndex, 9).toString());
            Expense = Expense.replaceAll("\\.", ":");
            String NoGroup[] = Expense.trim().split(":");
            Integer date = Integer.parseInt(model2.getValueAt(selectedRowIndex, 3).toString().replaceAll("-", ""));
            String[] SuppCode = (model2.getValueAt(selectedRowIndex, 2).toString()).split(":");
//            Integer duedate = Integer.parseInt(model2.getValueAt(selectedRowIndex, 14).toString().replaceAll("-", ""));
//            if (duedate != 0) {
//                DueDate_Invoiceline.setDate(checkdataprogram.GetDecmalTodate(duedate));
//
//            }
            Integer duedate = Integer.parseInt(model2.getValueAt(selectedRowIndex, 14).toString().replaceAll("-", "0"));
            if (duedate != 0) {
                Integer date2 = Integer.parseInt(model2.getValueAt(selectedRowIndex, 14).toString().replaceAll("-", ""));
                DueDate_Invoiceline.setDate(checkdataprogram.GetDecmalTodate(date2));
            }
            txtsupline.setText(SuppCode[0]);
            txtname.setText(SuppCode[1]);
            dateInvoiceline.setDate(checkdataprogram.GetDecmalTodate(date));
            txtinvoiceline.setText((model2.getValueAt(selectedRowIndex, 0).toString()));
            txtdescline.setText((model2.getValueAt(selectedRowIndex, 4).toString()));
            txtcostline.setText((model2.getValueAt(selectedRowIndex, 5).toString()));
            txtamtline.setText((model2.getValueAt(selectedRowIndex, 6).toString()));
            txtvatcline.setText((model2.getValueAt(selectedRowIndex, 7).toString()));
            txtSETT_VCTXT.setText((model2.getValueAt(selectedRowIndex, 13).toString()));

            Set_FormforAc(false);

            GetExpenseGroupAccount(NoGroup[0].trim());
            GetExpenseSubGroupAccount(NoGroup[0].trim() + "." + NoGroup[1].trim());
            GetExpenseSubGroupDescAccount(NoGroup[0].trim() + "." + NoGroup[1].trim() + "." + NoGroup[2].trim());
            String shareservice = (model2.getValueAt(selectedRowIndex, 11).toString());
//            if (shareservice.trim().equals("2")) {
//                sharesv1.setSelected(true);
//            } else {
//                sharesv1.setSelected(false);
//            }
            String SETT_CDVAT = cgd.get_codevat(CashAdvNo, model2.getValueAt(selectedRowIndex, 0).toString());
            if (SETT_CDVAT.equalsIgnoreCase("40")) {
                rdo_ITVAT.setSelected(true);
            } else {
                rdo_IPVAT.setSelected(true);
            }

            try {
                ResultSet rsl1 = cgd.GetComboBoxPaymethod();
                cmb_paymethod.removeAllItems();
                cmb_paymethod.addItem("Please select");
                while (rsl1.next()) {
                    cmb_paymethod.addItem(rsl1.getString("DESC") + " : " + String.valueOf(Double.parseDouble(rsl1.getString("percent")) + " %"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(frmAddLineInv.class.getName()).log(Level.SEVERE, null, ex);
            }
//            cmb_paymethod.setSelectedIndex(1);
            try {
                String method = model2.getValueAt(selectedRowIndex, 12).toString();
                if (!method.equals(null)) {
                    String[] paymethod = method.split(":");
                    int index = Integer.parseInt(paymethod[0].trim());
                    cmb_paymethod.setSelectedIndex(index + 1);
                } else {
                    cmb_paymethod.setSelectedIndex(0);
                }
            } catch (Exception e) {
            }

        } else if (ProgramStep.equals("EditExpenseStep4")) {
            SETT_BRAC.setVisible(false);
            ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
            Classgetdata cgd = new Classgetdata();
            String CashAdvNo = CashAdvanAccounting.txtAdvanceNo.getText().trim();
            int selectedRowIndex = 0;
            String Expense = null;
            DefaultTableModel model2;
            model2 = (DefaultTableModel) CashAdvanAccounting.griddetailacstep5.getModel();
            selectedRowIndex = CashAdvanAccounting.griddetailacstep5.getSelectedRow();

            Expense = (model2.getValueAt(selectedRowIndex, 1).toString());
            Expense = Expense.replaceAll("\\.", ":");
            String NoGroup[] = Expense.trim().split(":");
            Integer date = Integer.parseInt(model2.getValueAt(selectedRowIndex, 3).toString().replaceAll("-", ""));
            String[] SuppCode = (model2.getValueAt(selectedRowIndex, 2).toString()).split(":");
            Integer duedate = Integer.parseInt(model2.getValueAt(selectedRowIndex, 13).toString().replaceAll("-", "0"));
            if (duedate != 0) {
                Integer date2 = Integer.parseInt(model2.getValueAt(selectedRowIndex, 13).toString().replaceAll("-", ""));
                DueDate_Invoiceline.setDate(checkdataprogram.GetDecmalTodate(date2));
            }

            txtsupline.setText(SuppCode[0]);
            txtname.setText(SuppCode[1]);
            dateInvoiceline.setDate(checkdataprogram.GetDecmalTodate(date));
            txtinvoiceline.setText((model2.getValueAt(selectedRowIndex, 0).toString()));
            txtdescline.setText((model2.getValueAt(selectedRowIndex, 4).toString()));
            txtcostline.setText((model2.getValueAt(selectedRowIndex, 5).toString()));
            txtamtline.setText((model2.getValueAt(selectedRowIndex, 6).toString()));
            txtvatcline.setText((model2.getValueAt(selectedRowIndex, 7).toString()));
            txtSETT_VCTXT.setText((model2.getValueAt(selectedRowIndex, 12).toString()));

            Set_FormforAc(false);

            GetExpenseGroupAccount(NoGroup[0].trim());
            GetExpenseSubGroupAccount(NoGroup[0].trim() + "." + NoGroup[1].trim());
            GetExpenseSubGroupDescAccount(NoGroup[0].trim() + "." + NoGroup[1].trim() + "." + NoGroup[2].trim());
            String shareservice = (model2.getValueAt(selectedRowIndex, 10).toString());
//            if (shareservice.trim().equals("2")) {
//                sharesv1.setSelected(true);
//            } else {
//                sharesv1.setSelected(false);
//            }
            String SETT_CDVAT = cgd.get_codevat(CashAdvNo, model2.getValueAt(selectedRowIndex, 0).toString());
            if (SETT_CDVAT.equalsIgnoreCase("40")) {
                rdo_ITVAT.setSelected(true);
            } else {
                rdo_IPVAT.setSelected(true);
            }
            try {
                ResultSet rsl1 = cgd.GetComboBoxPaymethod();
                cmb_paymethod.removeAllItems();
                cmb_paymethod.addItem("Please select");
                while (rsl1.next()) {
                    cmb_paymethod.addItem(rsl1.getString("DESC") + " : " + String.valueOf(Double.parseDouble(rsl1.getString("percent")) + " %"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(frmAddLineInv.class.getName()).log(Level.SEVERE, null, ex);
            }
//            cmb_paymethod.setSelectedIndex(1);
            try {
                String method = model2.getValueAt(selectedRowIndex, 11).toString();
                if (!method.equals(null)) {
                    String[] paymethod = method.split(":");
                    int index = Integer.parseInt(paymethod[0].trim());
                    cmb_paymethod.setSelectedIndex(index + 1);
                } else {
                    cmb_paymethod.setSelectedIndex(0);
                }
            } catch (Exception e) {
            }

        } else {
            SETT_BRAC.setVisible(false);
            lbl_invdudt.setVisible(false);
            DueDate_Invoiceline.setVisible(false);
            lbl_svcode.setVisible(false);
            cmb_paymethod.setVisible(false);
            lbl_genvctxt.setVisible(false);
            txtSETT_VCTXT.setVisible(false);
            rdo_IPVAT.setVisible(false);
            rdo_ITVAT.setVisible(false);
            GetExpenseGroup();
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

        cmdSGEXP1 = new javax.swing.JComboBox<>();
        cmdHGEXP1 = new javax.swing.JComboBox<>();
        cmdI3desc1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lbl_genvctxt = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtdescline = new javax.swing.JTextField();
        txtinvoiceline = new javax.swing.JTextField();
        dateInvoiceline = new org.jdesktop.swingx.JXDatePicker();
        txtvatcline = new javax.swing.JTextField();
        txtsupline = new javax.swing.JTextField();
        txtcostline = new javax.swing.JTextField();
        txtamtline = new javax.swing.JTextField();
        txtname = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        SETT_BRAC = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        btnSearchSupp = new javax.swing.JButton();
        lbl_svcode = new javax.swing.JLabel();
        cmb_paymethod = new javax.swing.JComboBox<>();
        DueDate_Invoiceline = new org.jdesktop.swingx.JXDatePicker();
        jLabel27 = new javax.swing.JLabel();
        txtSETT_VCTXT = new javax.swing.JTextField();
        lbl_invdudt = new javax.swing.JLabel();
        rdo_ITVAT = new javax.swing.JRadioButton();
        rdo_IPVAT = new javax.swing.JRadioButton();
        rdoManualVatInput = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        cmdSGEXP1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmdSGEXP1.setEnabled(false);
        cmdSGEXP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSGEXP1ActionPerformed(evt);
            }
        });
        getContentPane().add(cmdSGEXP1);
        cmdSGEXP1.setBounds(90, 180, 530, 30);

        cmdHGEXP1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmdHGEXP1.setEnabled(false);
        cmdHGEXP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHGEXP1ActionPerformed(evt);
            }
        });
        getContentPane().add(cmdHGEXP1);
        cmdHGEXP1.setBounds(90, 140, 530, 30);

        cmdI3desc1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select Sub Group" }));
        cmdI3desc1.setEnabled(false);
        cmdI3desc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdI3desc1ActionPerformed(evt);
            }
        });
        getContentPane().add(cmdI3desc1);
        cmdI3desc1.setBounds(90, 220, 530, 30);

        jLabel1.setText("Sub Group :");
        jLabel1.setEnabled(false);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 180, 70, 30);

        jLabel2.setText("Group Desc :");
        jLabel2.setEnabled(false);
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 220, 80, 30);

        jLabel3.setText("Group :");
        jLabel3.setEnabled(false);
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 140, 50, 30);

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(300, 390, 130, 30);

        jLabel23.setForeground(new java.awt.Color(255, 0, 51));
        jLabel23.setText("Vat%");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(630, 10, 70, 20);

        jLabel24.setForeground(new java.awt.Color(255, 0, 51));
        jLabel24.setText("INVOICE");
        getContentPane().add(jLabel24);
        jLabel24.setBounds(10, 10, 50, 21);

        jLabel25.setForeground(new java.awt.Color(255, 0, 51));
        jLabel25.setText("Name");
        getContentPane().add(jLabel25);
        jLabel25.setBounds(300, 70, 50, 21);

        lbl_genvctxt.setForeground(new java.awt.Color(255, 0, 51));
        lbl_genvctxt.setText("Gen Voucher Text");
        lbl_genvctxt.setEnabled(false);
        getContentPane().add(lbl_genvctxt);
        lbl_genvctxt.setBounds(0, 330, 110, 30);

        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("Cost Center");
        getContentPane().add(jLabel28);
        jLabel28.setBounds(10, 70, 80, 20);

        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("Amt");
        getContentPane().add(jLabel29);
        jLabel29.setBounds(520, 10, 100, 20);

        txtdescline.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtdescline);
        txtdescline.setBounds(300, 30, 210, 30);

        txtinvoiceline.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtinvoiceline);
        txtinvoiceline.setBounds(10, 30, 120, 30);

        dateInvoiceline.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(dateInvoiceline);
        dateInvoiceline.setBounds(140, 30, 150, 30);

        txtvatcline.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtvatcline.setText("0");
        getContentPane().add(txtvatcline);
        txtvatcline.setBounds(630, 30, 70, 30);

        txtsupline.setEditable(false);
        txtsupline.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtsupline);
        txtsupline.setBounds(140, 90, 150, 30);

        txtcostline.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtcostline);
        txtcostline.setBounds(10, 90, 120, 30);

        txtamtline.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtamtline);
        txtamtline.setBounds(520, 30, 100, 30);

        txtname.setEditable(false);
        txtname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txtname);
        txtname.setBounds(300, 90, 210, 30);

        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("Supplier");
        getContentPane().add(jLabel30);
        jLabel30.setBounds(140, 70, 100, 21);
        getContentPane().add(SETT_BRAC);
        SETT_BRAC.setBounds(630, 90, 69, 30);

        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("Description");
        getContentPane().add(jLabel33);
        jLabel33.setBounds(300, 10, 210, 21);

        btnSearchSupp.setText("Search");
        btnSearchSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchSuppActionPerformed(evt);
            }
        });
        getContentPane().add(btnSearchSupp);
        btnSearchSupp.setBounds(520, 90, 100, 30);

        lbl_svcode.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_svcode.setText("Service Code :");
        lbl_svcode.setEnabled(false);
        getContentPane().add(lbl_svcode);
        lbl_svcode.setBounds(270, 290, 90, 30);

        cmb_paymethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_paymethod.setEnabled(false);
        getContentPane().add(cmb_paymethod);
        cmb_paymethod.setBounds(370, 290, 230, 30);

        DueDate_Invoiceline.setEnabled(false);
        DueDate_Invoiceline.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(DueDate_Invoiceline);
        DueDate_Invoiceline.setBounds(110, 290, 160, 30);

        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("Invoice Date");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(140, 10, 80, 21);

        txtSETT_VCTXT.setEnabled(false);
        getContentPane().add(txtSETT_VCTXT);
        txtSETT_VCTXT.setBounds(110, 330, 500, 30);

        lbl_invdudt.setForeground(new java.awt.Color(255, 0, 51));
        lbl_invdudt.setText("Invoice Due Date");
        lbl_invdudt.setEnabled(false);
        getContentPane().add(lbl_invdudt);
        lbl_invdudt.setBounds(10, 290, 100, 30);

        rdo_ITVAT.setText("Inter VAT ");
        rdo_ITVAT.setEnabled(false);
        rdo_ITVAT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_ITVATActionPerformed(evt);
            }
        });
        getContentPane().add(rdo_ITVAT);
        rdo_ITVAT.setBounds(220, 250, 90, 40);

        rdo_IPVAT.setText("Input VAT");
        rdo_IPVAT.setEnabled(false);
        rdo_IPVAT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_IPVATActionPerformed(evt);
            }
        });
        getContentPane().add(rdo_IPVAT);
        rdo_IPVAT.setBounds(320, 250, 95, 40);

        rdoManualVatInput.setText("Manual Vat Input");
        rdoManualVatInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdoManualVatInputStateChanged(evt);
            }
        });
        rdoManualVatInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoManualVatInputActionPerformed(evt);
            }
        });
        getContentPane().add(rdoManualVatInput);
        rdoManualVatInput.setBounds(40, 390, 180, 27);

        setSize(new java.awt.Dimension(729, 478));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdHGEXP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHGEXP1ActionPerformed
        if (!ProgramStep.equals("EditExpense")) {
            try {
                String Nosub = (String) cmdHGEXP1.getSelectedItem().toString();
                if (!Nosub.equalsIgnoreCase("null") && !Nosub.equalsIgnoreCase("")) {
                    String[] SubGroup = Nosub.split(":");
                    GetExpenseSubGroup(SubGroup[0].trim());
                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }//GEN-LAST:event_cmdHGEXP1ActionPerformed

    private void cmdSGEXP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSGEXP1ActionPerformed
        if (!ProgramStep.equals("EditExpense")) {
            try {
                String Nosub = (String) cmdSGEXP1.getSelectedItem();
                if (!Nosub.equalsIgnoreCase("null") && !Nosub.equalsIgnoreCase("")) {
                    String[] SubGroup = Nosub.split(":");
                    GetExpenseSubGroupDesc(SubGroup[0].trim());
                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }//GEN-LAST:event_cmdSGEXP1ActionPerformed
    private void Get_DetailGridAcc(String CashAdvanceNo) {

        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetDataAdvanceDetailResultData(CashAdvanceNo);
        try {

            model2 = (DefaultTableModel) griddetailac.getModel();
            int iiii = 0;
            while (rs.next()) {
                model2.setValueAt(rs.getString("SETT_INVC").trim(), iiii, 0);
                model2.setValueAt(rs.getString("SETT_NODES").trim(), iiii, 1);
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
//                model2.setValueAt(rs.getString("SETT_VCTXT"), iiii, 13);
//                model2.setValueAt(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("SETT_DUEDT"))), iiii, 14);

                if (rs.getString("SETT_VCTXT") == null) {
                    model2.setValueAt("-", iiii, 13);
                } else {
                    model2.setValueAt(rs.getString("SETT_VCTXT"), iiii, 13);
                }
                if (rs.getString("SETT_DUEDT") == null) {
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

    private void Get_DetailGridAccStep5(String CashAdvanceNo) {

        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetDataAdvanceDetailResultData(CashAdvanceNo);
        try {

            model = (DefaultTableModel) CashAdvanAccounting.griddetailacstep5.getModel();
            int iiii = 0;
            while (rs.next()) {
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
                if (rs.getString("SETT_DUEDT") == null) {
                    model.setValueAt("-", iiii, 13);
                } else {
                    model.setValueAt(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("SETT_DUEDT"))), iiii, 13);
                }

                iiii++;
            }
            rs.close();
            model.fireTableDataChanged();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (ProgramStep.equalsIgnoreCase("EditExpense") || ProgramStep.equalsIgnoreCase("EditExpenseStep4")) {

            SETT_BRAC.setText("1");
            String BranchAcc = SETT_BRAC.getText().trim();
            ClassSetdata cds = new ClassSetdata();
            ClassCheckDataProgram cdp = new ClassCheckDataProgram();
            String CASH_CANO = CashAdvanAccounting.txtAdvanceNo.getText().trim();
            String[] NODES = cmdI3desc1.getSelectedItem().toString().split(":");
            String INVC = txtinvoiceline.getText().trim();
            String[] method = cmb_paymethod.getSelectedItem().toString().split(":");
//            System.out.println(method[0].trim());
            String SETT_VCTXT = txtSETT_VCTXT.getText().trim();
            String SETT_DUEDT = cdp.GetDateFormatSet(DueDate_Invoiceline.getDate());
            String SETT_CDVAT = "40";
            if (rdo_IPVAT.isSelected()) {
                SETT_CDVAT = "07";
            }

            cds.SaveCashAdvanceInvoice(CASH_CANO, NODES[0], INVC, BranchAcc, method[0].trim(), SETT_VCTXT, SETT_DUEDT, SETT_CDVAT);
            msbox("Update Complete !");

            if (ProgramStep.equalsIgnoreCase("EditExpense")) {
                Get_DetailGridAcc(CashAdvanAccounting.txtAdvanceNo.getText().trim());
            } else {
                Get_DetailGridAccStep5(CashAdvanAccounting.txtAdvanceNo.getText().trim());
            }

            dispose();

        } else {
            try {
                Classgetdata cgd = new Classgetdata();
                if (txtinvoiceline.getText().length() >= 16) {
                    msbox("INVOICE maxximum 15");
                    return;
                }
                if (txtdescline.getText().length() >= 61) {
                    msbox("Description maxximum 60");
                    return;
                }

                if (cgd.CheckInvoice(txtinvoiceline.getText().trim()) > 0) {
                    msbox("Invoice " + txtinvoiceline.getText().trim() + " is already in system.");
                    return;
                }

                if (txtsupline.getText().trim().equals("")) {
                    msbox("Supplier must be entered");
                    return;
                }

                ClassCheckDataProgram cdp = new ClassCheckDataProgram();
                double amtqty = 0;
                model = (DefaultTableModel) griddetail.getModel();
                String EPRH_RQSDT = cdp.GetDateFormatSet(dateInvoiceline.getDate());

                BigDecimal amt = new BigDecimal(txtamtline.getText());
                try {
                    
//                     amt = amt.add(); amt = cdp.Double2digitReturn(Double.parseDouble());
                } catch (Exception e) {
//                    amt = 0;
                }

//                double vatc = 0;
                
                BigDecimal vatc = new BigDecimal(txtvatcline.getText());
//                try {
//                    vatc = cdp.Double2digitReturn(Double.parseDouble(txtvatcline.getText()));
//                } catch (Exception e) {
//                    vatc = 0;
//                }
//                double vatamt_1 = 0;
                 BigDecimal vatamt_1 = new BigDecimal(0);
//                vatamt_1
                if (rdoManualVatInput.isSelected()) {
                    vatamt_1 = vatc;
//                    vatc = 7;
                } else {
                    vatamt_1 =  amt.multiply(vatc)
                                 .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                }

                // double vatamt=Classcheck.Double2digitReturn((double)(model.getValueAt(i, 7)));// BANK EDIT FOR RACHANEEWAN
//                String vatamt_2 = cdp.Round2digiReturn(vatamt_1);
                 String vatamt_2 = vatamt_1.setScale(2, RoundingMode.HALF_UP).toPlainString();
                BigDecimal totalamt;
                if (!importduty) {
                    totalamt = amt.add(new BigDecimal(vatamt_2));
                } else {
                    totalamt = amt.add(vatc);
                }

                String costcentergrid = txtcostline.getText().trim().toUpperCase();

                String NoDesc = "1.1";

                SETT_BRAC.setText("1");
                String BranchAcc = SETT_BRAC.getText().trim();
                if (!costcentergrid.equals("")) {
                    int countcost = cgd.GetCostcenterWithCodeData(costcentergrid);
                    if (countcost == 0) {
                        msbox("Costcenter Not Found");
                        return;
                    } else {
                        if (rdoManualVatInput.isSelected()) {
                            vatamt_1 = vatc;
                            ClassSetdata cds = new ClassSetdata();
                            cds.InsertLine(LoginCono, LoginDivision, NoDesc, BranchAcc, txtAdvanceNo1.getText().trim(), txtinvoiceline.getText().trim(), txtsupline.getText(), EPRH_RQSDT,
                                    txtdescline.getText(), txtcostline.getText().toUpperCase(), txtamtline.getText(), "7",
                                    vatamt_2, String.valueOf(totalamt), "10", importduty
                            );
                        } else {
                            ClassSetdata cds = new ClassSetdata();
                            cds.InsertLine(LoginCono, LoginDivision, NoDesc, BranchAcc, txtAdvanceNo1.getText().trim(), txtinvoiceline.getText().trim(), txtsupline.getText(), EPRH_RQSDT,
                                    txtdescline.getText(), txtcostline.getText().toUpperCase(), txtamtline.getText(), txtvatcline.getText(),
                                    vatamt_2, String.valueOf(totalamt), "10", importduty
                            );
                        }

                        Get_DetailGrid(txtAdvanceNo1.getText().trim());
                    }
                }
                dispose();
            } catch (Exception e) {
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void Get_DetailGrid(String CashAdvanceNo) {

        Classgetdata cgd = new Classgetdata();
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        ResultSet rs = cgd.GetDataAdvanceDetailResultData(CashAdvanceNo);
        try {
            //    SetModel();
            model = (DefaultTableModel) griddetail.getModel();
            int iiii = 0;
//         
            Double amt = 0.00;
            Double vat = 0.00;
            Double amtBeforeVat = 0.00;
            Double Sum = 0.00;
            Double Advamt = Double.parseDouble(txtamount1.getText().trim());
            while (rs.next()) {
                //  String Nassme= cgd.GetItemName_MitMas(rs.getString("EPRL_ITNO").trim());
                model.setValueAt(rs.getString("SETT_INVC").trim(), iiii, 0);
                model.setValueAt(rs.getString("SETT_SUPP").trim(), iiii, 1);
                model.setValueAt(cgd.Get_SupplierNameWithCode2(rs.getString("SETT_SUPP").trim()), iiii, 2);
                model.setValueAt(checkdataprogram.GetDateFormatSetShowString(checkdataprogram.GetDecmalTodate(rs.getInt("SETT_INVD"))), iiii, 3);
                model.setValueAt(rs.getString("SETT_DESC").trim(), iiii, 4);
                model.setValueAt(rs.getString("SETT_COST").trim(), iiii, 5);
                model.setValueAt(rs.getDouble("SETT_AMTB"), iiii, 6);
                model.setValueAt(rs.getDouble("SETT_VATC"), iiii, 7);
                model.setValueAt(rs.getDouble("SETT_VATA"), iiii, 8);
                model.setValueAt(rs.getString("SETT_NODES"), iiii, 9);
                model.setValueAt(rs.getDouble("SETT_AMTT"), iiii, 10);
                model.setValueAt(rs.getString("SETT_BRAC"), iiii, 11);
                amt += rs.getDouble("SETT_AMTT");
                vat += rs.getDouble("SETT_VATA");
                amtBeforeVat += rs.getDouble("SETT_AMTB");
                iiii++;
            }
            amtBeforeVat = checkdataprogram.Double2digitReturn(amtBeforeVat);
            amt = checkdataprogram.Double2digitReturn(amt);
            vat = checkdataprogram.Double2digitReturn(vat);
//            
            Cal_AmountSettlementWithGrid(Advamt, amtBeforeVat, vat, amt);
            DateTransfer.setEnabled(modeforreceive);
            jLabel15.setEnabled(modeforreceive);
            if (!modeforreceive) {
                DateTransfer.setDate(null);
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    private void Cal_WHTAX() {
        Double AdvAmt = Double.parseDouble(txtamtbfvat1.getText().trim());
//        if (rdo1per.isSelected()) {
//            AdvAmt = (AdvAmt * 1) / 100;
//            txtwhtax1.setText(String.valueOf(AdvAmt));
//        } else if (rdo2per.isSelected()) {
//            AdvAmt = (AdvAmt * 2) / 100;
//            txtwhtax1.setText(String.valueOf(AdvAmt));
//        } else if (rdo3per.isSelected()) {
//            AdvAmt = (AdvAmt * 3) / 100;
//            txtwhtax1.setText(String.valueOf(AdvAmt));
//        } else if (rdo5per.isSelected()) {
//            AdvAmt = (AdvAmt * 5) / 100;
//            txtwhtax1.setText(String.valueOf(AdvAmt));
//        } else {
//            AdvAmt = (AdvAmt * 0) / 100;
//            txtwhtax1.setText(String.valueOf(AdvAmt));
//        }
    }

    private void Set_RdoSettlement(boolean Status) {
        rdoManualVatInput.setEnabled(Status);
        rdoReturnKBANK.setEnabled(Status);
        rdoPTC.setEnabled(Status);
        rdoTransBank.setEnabled(Status);
        rdoPTC.setSelected(Status);
        rdoManualVatInput.setSelected(Status);
        rdoReturnKBANK.setSelected(Status);
        rdoPTC.setSelected(Status);
        rdoTransBank.setSelected(Status);

    }

    private void Set_FormforAc(boolean Status) {
        txtinvoiceline.setEditable(Status);
        dateInvoiceline.setEnabled(Status);
        txtdescline.setEditable(Status);
        txtamtline.setEditable(Status);
        txtvatcline.setEditable(Status);
        txtcostline.setEditable(Status);
        txtsupline.setEditable(Status);
        txtname.setEditable(Status);
        btnSearchSupp.setEnabled(Status);
        cmdHGEXP1.setEnabled(Status);
        cmdSGEXP1.setEnabled(Status);
        cmdI3desc1.setEnabled(Status);
    }

    private void ClearBanktxt() {
        txtBName.setText("");
        txtBCode.setText("");
    }

    private void Cal_AmountSettlementWithGrid(Double Advamt, Double AmtBfVat, Double AmtVat, Double TotalAmt) {
        ClassCheckDataProgram checkdataprogram = new ClassCheckDataProgram();
        Double Sum = 0.00;
        ClassCheckDataProgram cgp = new ClassCheckDataProgram();

        txtamtbfvat1.setText(String.valueOf(AmtBfVat).trim());
        txtvat1.setText(String.valueOf(AmtVat).trim());
        txtamt1.setText(String.valueOf(TotalAmt).trim());
        Cal_WHTAX();
        Double WHTAX = Double.parseDouble(txtwhtax1.getText().trim());
        Sum = Advamt - (TotalAmt - WHTAX);
        Set_RdoSettlement(false);
        if (Sum > 0.00) {
            txtreturn1.setText(String.valueOf(Sum).trim());
            txtrefund1.setText("0.00");
            rdoManualVatInput.setEnabled(true);
            rdoManualVatInput.setSelected(true);
            rdoReturnKBANK.setEnabled(true);
            ClearBanktxt();
            modeforreceive = true;
        } else {
            if (Sum != 0.0) {
                Sum = Sum * (-1);
                modeforreceive = false;
            }
//            txtrefund1.setText(String.valueOf(Sum).trim());
            txtrefund1.setText(String.valueOf(cgp.Double2digitReturn(Sum)).trim());
            txtreturn1.setText("0.00");
            if (Sum < 1000.00 && Sum != 0.00) {
                if (LoginCono.equalsIgnoreCase("10")) {
                    rdoPTC.setSelected(true);
                    rdoPTC.setEnabled(true);
                    modeforreceive = false;
                } else {
                    rdoTransBank.setSelected(true);
                    rdoTransBank.setEnabled(true);
                    modeforreceive = false;
                }
                rdoManualVatInput.setSelected(false);
            } else if (Sum > 1000.00) {
                rdoTransBank.setSelected(true);
                rdoTransBank.setEnabled(true);
                modeforreceive = false;
            } else {
                Set_RdoSettlement(false);
                modeforreceive = false;
            }
        }

    }
    private void btnSearchSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchSuppActionPerformed
        frmsearch.ProgramStep = "cashadvstep2";
        new frmsearch().setVisible(true);
    }//GEN-LAST:event_btnSearchSuppActionPerformed

    private void cmdI3desc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdI3desc1ActionPerformed

        try {
            String check = cmdI3desc1.getSelectedItem().toString();
            Classgetdata cgd = new Classgetdata();
            String I3DS_I3AIT1 = "", I3DS_I3AIT2 = "";
            System.out.println(check);
//            sharesv1.setEnabled(true);
//            sharesv1.setSelected(false);
            if (!check.equalsIgnoreCase("Please Select Sub Group")) {
                String[] SubGroup = check.split(":");
                ResultSet rsl = cgd.Check_BranchAC(SubGroup[0]);
                while (rsl.next()) {
                    I3DS_I3AIT1 = rsl.getString("I3DS_I3AIT1").trim();
                    I3DS_I3AIT2 = rsl.getString("I3DS_I3AIT2").trim();
                }
                if (I3DS_I3AIT1.equals("-")) {
//                    frmAddLineInv.sharesv1.setEnabled(false);
//                    frmAddLineInv.sharesv1.setSelected(true);
                    System.out.println("I3DS_I3AIT1 = " + I3DS_I3AIT1);
                } else if (I3DS_I3AIT2.equals("-")) {
//                    frmAddLineInv.sharesv1.setEnabled(false);
//                    frmAddLineInv.sharesv1.setSelected(false);
                    System.out.println("I3DS_I3AIT2 = " + I3DS_I3AIT2);
                }
            }
        } catch (Exception e) {

        }

    }//GEN-LAST:event_cmdI3desc1ActionPerformed

    private void rdo_IPVATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_IPVATActionPerformed
        if (rdo_IPVAT.isSelected()) {
            rdo_ITVAT.setSelected(!rdo_IPVAT.isSelected());
        }
    }//GEN-LAST:event_rdo_IPVATActionPerformed

    private void rdo_ITVATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_ITVATActionPerformed
        if (rdo_ITVAT.isSelected()) {
            rdo_IPVAT.setSelected(!rdo_ITVAT.isSelected());
        }


    }//GEN-LAST:event_rdo_ITVATActionPerformed

    private void rdoManualVatInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoManualVatInputActionPerformed
        if (rdoManualVatInput.isSelected()) {
            System.out.println("Manual VAT is enabled");
            jLabel23.setText("VatAmount");
        } else {
            System.out.println("Manual VAT is disabled");
            jLabel23.setText("Vat%");
        }
    }//GEN-LAST:event_rdoManualVatInputActionPerformed

    private void rdoManualVatInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoManualVatInputStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_rdoManualVatInputStateChanged

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
            java.util.logging.Logger.getLogger(frmAddLineInv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAddLineInv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAddLineInv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAddLineInv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmAddLineInv().setVisible(true);
            }
        });
    }

    private void msbox(String strr) {
        int dialogButton = JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(null, strr, "Warning", dialogButton);
    }

    public void GetExpenseGroupAccount(String Expense) {

        cmdHGEXP1.removeAllItems();
        try {
            Classgetdata cgr = new Classgetdata();
            Classgetdata cgd = new Classgetdata();

            ResultSet rs1 = cgr.GetExpensHeadGroupWithNo(Expense.trim());
            while (rs1.next()) {
                cmdHGEXP1.addItem(rs1.getString("I1GP_NOGP").trim() + " : " + rs1.getString("I1GP_DESGP").trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmAddLineInv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GetExpenseSubGroupAccount(String NoSub) {

        cmdSGEXP1.removeAllItems();
        try {
            Classgetdata cgr = new Classgetdata();
            ResultSet rs1 = cgr.GetExpensSubGroupAccount(NoSub);
            while (rs1.next()) {
                cmdSGEXP1.addItem(rs1.getString("I2SG_NOSG").trim() + " : " + rs1.getString("I2SG_DESSG").trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmAddLineInv.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GetExpenseSubGroupDescAccount(String NoSub) {

        cmdI3desc1.removeAllItems();
        try {
            Classgetdata cgr = new Classgetdata();
            ResultSet rs1 = cgr.GetExpensSubGroupDescAccount(NoSub);
            while (rs1.next()) {
                cmdI3desc1.addItem(rs1.getString("I3DS_NODES").trim() + " : " + rs1.getString("I3DS_DESC").trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmAddLineInv.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GetExpenseGroup() {

        cmdHGEXP1.removeAllItems();
        try {
            Classgetdata cgr = new Classgetdata();
            String[] NoGroup = cmb_IT1GP.getText().split(":");
            ResultSet rs1 = cgr.GetExpensHeadGroupWithNo(NoGroup[0]);
            cmdHGEXP1.addItem("Please Select Group");
            while (rs1.next()) {
                cmdHGEXP1.addItem(rs1.getString("I1GP_NOGP").trim() + " : " + rs1.getString("I1GP_DESGP").trim());

            }
        } catch (SQLException ex) {
            Logger.getLogger(frmAddLineInv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GetExpenseSubGroup(String NoSub) {

        cmdSGEXP1.removeAllItems();
        try {
            Classgetdata cgr = new Classgetdata();
            ResultSet rs1 = cgr.GetExpensSubGroup(NoSub);
            cmdSGEXP1.addItem("Please Select Sub Group");
            while (rs1.next()) {
                cmdSGEXP1.addItem(rs1.getString("I2SG_NOSG").trim() + " : " + rs1.getString("I2SG_DESSG").trim());

            }
        } catch (SQLException ex) {
            Logger.getLogger(frmAddLineInv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GetExpenseSubGroupDesc(String NoSub) {

        cmdI3desc1.removeAllItems();
        try {
            Classgetdata cgr = new Classgetdata();
            ResultSet rs1 = cgr.GetExpensSubGroupDesc(NoSub);
            cmdI3desc1.addItem("Please Select Sub Group");
            while (rs1.next()) {
                cmdI3desc1.addItem(rs1.getString("I3DS_NODES").trim() + " : " + rs1.getString("I3DS_DESC").trim());

            }
        } catch (SQLException ex) {
            Logger.getLogger(frmAddLineInv.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker DueDate_Invoiceline;
    public static javax.swing.JTextField SETT_BRAC;
    private javax.swing.JButton btnSearchSupp;
    private javax.swing.JComboBox<String> cmb_paymethod;
    public static javax.swing.JComboBox<String> cmdHGEXP1;
    public static javax.swing.JComboBox<String> cmdI3desc1;
    public static javax.swing.JComboBox<String> cmdSGEXP1;
    private org.jdesktop.swingx.JXDatePicker dateInvoiceline;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel lbl_genvctxt;
    private javax.swing.JLabel lbl_invdudt;
    private javax.swing.JLabel lbl_svcode;
    public static javax.swing.JRadioButton rdoManualVatInput;
    public static javax.swing.JRadioButton rdo_IPVAT;
    public static javax.swing.JRadioButton rdo_ITVAT;
    public static javax.swing.JTextField txtSETT_VCTXT;
    private javax.swing.JTextField txtamtline;
    private javax.swing.JTextField txtcostline;
    private javax.swing.JTextField txtdescline;
    private javax.swing.JTextField txtinvoiceline;
    public static javax.swing.JTextField txtname;
    public static javax.swing.JTextField txtsupline;
    private javax.swing.JTextField txtvatcline;
    // End of variables declaration//GEN-END:variables
}
