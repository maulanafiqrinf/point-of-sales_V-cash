/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Owner;
import Master.Menu_Admin;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import com.toedter.calendar.JDateChooser;
import koneksi.koneksi;

/**
 *
 * @author maulana fiqri
 */
public class Tambah_Karyawan extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel();

    /**
     * Creates new form Tambah_Karyawan
     */
    public Tambah_Karyawan() {
        initComponents();
        
         
        Date now = new Date();  
        tgl_daftar.setDate(now); 
        
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        table_user.setModel(table);
        table.addColumn("ID");
        table.addColumn("Nama Karyawan");
        table.addColumn("Email");
        table.addColumn("Alamat");
        table.addColumn("Username");
        table.addColumn("Password");
        table.addColumn("Tanggal Pendaftaran");
        
        
        tampilData();
        
    }
    private void tampilData(){
        //untuk mengahapus baris setelah input
        int row = table_user.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        String query = "SELECT * FROM `tb_datapetugas` ";
        
        try{
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
            
            while (rslt.next()){
                //menampung data sementara
                   
                    String id= rslt.getString("id_petugas");
                    String nama = rslt.getString("nama_petugas");
                    String email = rslt.getString("email");
                    String alamat = rslt.getString("alamat");
                    String username = rslt.getString("username");
                    String password = rslt.getString("password");
                    String tanggal = rslt.getString("tanggal_pendaftaran");
                    
                //masukan semua data kedalam array
                String[] data = {id,nama,email,alamat,username,password,tanggal};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                table_user.setModel(table);
            
        }catch(Exception e){
            System.out.println(e);
        }
       
    }
    private void clear(){
//        txt_kodebarang.setText(null);
        txt_nama.setText(null);
        txt_email.setText(null);
        txt_alamat.setText(null);
        txt_username.setText(null);
        txt_password.setText(null);
//        tgl_daftar.setDate(null);
        
    }
    private void tambahData(){
//        String kode = txt_kodebarang.getText();
        String nama = txt_nama.getText();
        String email = txt_email.getText();
        String alamat = txt_alamat.getText();
        String username = txt_username.getText();
        String password = txt_password.getText();
        
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = date.format(tgl_daftar.getDate());
        
        //panggil koneksi
        Connection connect = koneksi.getKoneksi();
        //query untuk memasukan data
        String query = "INSERT INTO `tb_datapetugas` (`id_petugas`, `nama_petugas`, `email`,`alamat`, `username`, `password`, `tanggal_pendaftaran`) "
                     + "VALUES (NULL, '"+nama+"', '"+email+"','"+alamat+"', '"+username+"', '"+password+"','"+tanggal+"')";
        
        try{
            //menyiapkan statement untuk di eksekusi
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Data Berhasil Disimpan");
            
        }catch(SQLException | HeadlessException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null,"Data Gagal Disimpan");
            
        }finally{
            tampilData();
            clear();
            
        }
    }
    private void hapusData(){
        //ambill data no pendaftaran
        int i = table_user.getSelectedRow();
        
        String id = table.getValueAt(i, 0).toString();
        
        Connection connect = koneksi.getKoneksi();
        
        String query = "DELETE FROM `tb_datapetugas` WHERE `tb_datapetugas`.`id_petugas` = "+id+" ";
        try{
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
            ps.execute();
            JOptionPane.showMessageDialog(null , "Data Berhasil Dihapus");
        }catch(SQLException | HeadlessException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
        }finally{
            tampilData();
            clear();
        }
        
    }
    private void editData(){
        int i = table_user.getSelectedRow();
        
        String id = table.getValueAt(i, 0).toString();
        String nama = txt_nama.getText();
        String email = txt_email.getText();
        String alamat = txt_alamat.getText();
        String username = txt_username.getText();
        String password = txt_password.getText();
        
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = date.format(tgl_daftar.getDate());
        
        Connection connect = koneksi.getKoneksi();
        
        String query = "UPDATE `tb_datapetugas` SET `nama_petugas` = '"+nama+"', `email` = '"+email+"', `alamat` = '"+alamat+"', `tanggal_pendaftaran` = '"+tanggal+"', "
                + "`username` = '"+username+"', `password` = '"+password+"' "
                + "WHERE `tb_datapetugas`.`id_petugas` = '"+id+"';";

        try{
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null , "Data Update");
        }catch(SQLException | HeadlessException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Gagal Update");
        }finally{
            tampilData();
            clear();
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

        bg961 = new backgoundcoding.bg96();
        jLabel9 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_alamat = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        tgl_daftar = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_password = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_user = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nama Karyawan");

        txt_nama.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Email");

        txt_email.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Alamat");

        txt_alamat.setColumns(20);
        txt_alamat.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_alamat.setRows(5);
        jScrollPane4.setViewportView(txt_alamat);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tanggal Pendaftaran");

        tgl_daftar.setEnabled(false);
        tgl_daftar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Username");

        txt_username.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Password");

        txt_password.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/plus.png"))); // NOI18N
        jButton1.setText("  ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/minus.png"))); // NOI18N
        jButton7.setText("  CLEAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/edit1.png"))); // NOI18N
        jButton5.setText("  EDIT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/previous1.png"))); // NOI18N
        jButton3.setText("  BACK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        table_user.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        table_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_userMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_user);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/print1.png"))); // NOI18N
        jButton6.setText("  PRINT");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/trash-can (1).png"))); // NOI18N
        jButton4.setText("  DELETE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA KARYAWAN");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CAFE VEGAN WAY");

        javax.swing.GroupLayout bg961Layout = new javax.swing.GroupLayout(bg961);
        bg961.setLayout(bg961Layout);
        bg961Layout.setHorizontalGroup(
            bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg961Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bg961Layout.createSequentialGroup()
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bg961Layout.createSequentialGroup()
                                .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txt_nama, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_email, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(bg961Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tgl_daftar, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(bg961Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(43, 43, 43)
                                .addComponent(jButton7)
                                .addGap(45, 45, 45)
                                .addComponent(jButton5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bg961Layout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(bg961Layout.createSequentialGroup()
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        bg961Layout.setVerticalGroup(
            bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg961Layout.createSequentialGroup()
                .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bg961Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(19, 19, 19)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(tgl_daftar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton7)
                            .addComponent(jButton5)))
                    .addGroup(bg961Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jButton6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg961, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg961, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        editData();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Menu_Admin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void table_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_userMouseClicked
        int baris = table_user.getSelectedRow();

        String nama = table.getValueAt(baris, 1).toString();
        txt_nama.setText(nama);

        String email = table.getValueAt(baris, 2).toString();
        txt_email.setText(email);

        String alamat = table.getValueAt(baris, 3).toString();
        txt_alamat.setText(alamat);

        String username = table.getValueAt(baris, 4).toString();
        txt_username.setText(username);

        String password = table.getValueAt(baris, 5).toString();
        txt_password.setText(password);

        String tanggal = table.getValueAt(baris, 6).toString();

        Date convert = null;
        try {
            convert = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        } catch (Exception e) {
            System.out.println(e);
        }
        tgl_daftar.setDate(convert);
    }//GEN-LAST:event_table_userMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {
            String file = "/Laporan/data_petugas.jasper";
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file), null, koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Tambah_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tambah_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tambah_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tambah_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tambah_Karyawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private backgoundcoding.bg96 bg961;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable table_user;
    private com.toedter.calendar.JDateChooser tgl_daftar;
    private javax.swing.JTextArea txt_alamat;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
