/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Owner;

import Master.Menu_Admin;
import com.toedter.calendar.JDateChooser;
import koneksi.koneksi;
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

import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author maulana fiqri
 */
public class Input_Minuman extends javax.swing.JFrame {
 DefaultTableModel table = new DefaultTableModel();
    /**
     * Creates new form Input_Minuman
     */
    public Input_Minuman() {
        initComponents();
               tanggal();

        koneksi conn = new koneksi();
        koneksi.getKoneksi();

        table_barang1.setModel(table);
        table.addColumn("Kode Minuman");
        table.addColumn("Nama Minuman");
        table.addColumn("Harga");
        table.addColumn("Stok");
        table.addColumn("Tanggal Masuk");

        tampilData();
    }
    private void tampilData() {
        //untuk mengahapus baris setelah input
        int row = table_barang1.getRowCount();
        for (int a = 0; a < row; a++) {
            table.removeRow(0);
        }

        String query = "SELECT * FROM `tb_databarang` ";

        try {
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query

            while (rslt.next()) {
                //menampung data sementara

                String kode = rslt.getString("kode_minuman");
                String nama = rslt.getString("nama_minuman");
                String harga = rslt.getString("harga");
                String stok = rslt.getString("stok");
                String tanggal = rslt.getString("tanggal");

                //masukan semua data kedalam array
                String[] data = {kode, nama, harga, stok, tanggal};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
            //mengeset nilai yang ditampung agar muncul di table
            table_barang1.setModel(table);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void clear() {
//        txt_kodebarang.setText(null);
        txt_namabarang.setText(null);
        txt_harga.setText(null);
        txt_stok.setText(null);
//        txt_tanggal.setDate(null);

    }

    private void tambahData() {
//        String kode = txt_kodebarang.getText();
        String nama = txt_namabarang.getText();
        String harga = txt_harga.getText();
        String stok = txt_stok.getText();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = date.format(txt_tanggal.getDate());

        //panggil koneksi
        Connection connect = koneksi.getKoneksi();
        //query untuk memasukan data
        String query = "INSERT INTO `tb_databarang` (kode_minuman, `nama_minuman`, `harga`, `stok`, `tanggal`) "
                + "VALUES (NULL, '" + nama + "', '" + harga + "', '" + stok + "', '" + tanggal + "')";

        try {
            //menyiapkan statement untuk di eksekusi
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");

        } catch (SQLException | HeadlessException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");

        } finally {
            tampilData();
            clear();

        }
    }

    public void tanggal() {
        Date now = new Date();
        txt_tanggal.setDate(now);
    }

    private void hapusData() {
        //ambill data no pendaftaran
        int i = table_barang1.getSelectedRow();

        String kode = table.getValueAt(i, 0).toString();

        Connection connect = koneksi.getKoneksi();

        String query = "DELETE FROM `tb_databarang` WHERE `tb_databarang`.`kode_minuman` = " + kode + " ";
        try {
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        } catch (SQLException | HeadlessException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
        } finally {
            tampilData();
            clear();
        }

    }

    private void editData() {
        int i = table_barang1.getSelectedRow();

        String kode = table.getValueAt(i, 0).toString();
        String nama = txt_namabarang.getText();
        String harga = txt_harga.getText();
        String stok = txt_stok.getText();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = String.valueOf(date.format(txt_tanggal.getDate()));

        Connection connect = koneksi.getKoneksi();

        String query = "UPDATE `tb_databarang` SET `nama_minuman` = '" + nama + "', `harga` = '" + harga + "', `stok` = '" + stok + "', `tanggal` = '" + tanggal + "' "
                + "WHERE `tb_databarang`.`kode_minuman` = '" + kode + "';";

        try {
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Data Update");
        } catch (SQLException | HeadlessException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Gagal Update");
        } finally {
            tampilData();
            clear();
        }
    }

    private void cari() {
        int row = table_barang1.getRowCount();
        for (int a = 0; a < row; a++) {
            table.removeRow(0);
        }

        String cari = txt_search.getText();

        String query = "SELECT * FROM `tb_databarang` WHERE `kode_minuman`  LIKE '%" + cari + "%' OR `nama_minuman` LIKE '%" + cari + "%' ";

        try {
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query

            while (rslt.next()) {
                //menampung data sementara

                String kode = rslt.getString("kode_minuman");
                String nama = rslt.getString("nama_minuman");
                String harga = rslt.getString("harga");
                String stok = rslt.getString("stok");
                String tanggal = rslt.getString("tanggal");

                //masukan semua data kedalam array
                String[] data = {kode, nama, harga, stok, tanggal};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
            //mengeset nilai yang ditampung agar muncul di table
            table_barang1.setModel(table);

        } catch (Exception e) {
            System.out.println(e);
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
        txt_namabarang = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_stok = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_tanggal = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_barang1 = new javax.swing.JTable();
        txt_search = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nama Barang");

        txt_namabarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_namabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namabarangActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Harga");

        txt_harga.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Stok");

        txt_stok.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stokActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tanggal Masuk");

        txt_tanggal.setEnabled(false);
        txt_tanggal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/plus.png"))); // NOI18N
        jButton1.setText("  ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/minus.png"))); // NOI18N
        jButton4.setText("CLEAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/edit1.png"))); // NOI18N
        jButton6.setText("  EDIT");
        jButton6.setPreferredSize(new java.awt.Dimension(117, 40));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
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

        table_barang1.setModel(new javax.swing.table.DefaultTableModel(
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
        table_barang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_barang1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_barang1);

        txt_search.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/find1.png"))); // NOI18N
        jButton7.setText("  CARI");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/reload.png"))); // NOI18N
        jButton5.setText("  REFRESH");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/print1.png"))); // NOI18N
        jButton8.setText("  PRINT");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambarasset/trash-can (1).png"))); // NOI18N
        jButton2.setText("  DELETE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA MINUMAN");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg961Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bg961Layout.createSequentialGroup()
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bg961Layout.createSequentialGroup()
                                .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg961Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(49, 49, 49)))
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg961Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bg961Layout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton8)
                                .addGap(76, 76, 76)
                                .addComponent(jButton2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)))
                    .addGroup(bg961Layout.createSequentialGroup()
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jButton3))
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
                        .addGap(101, 101, 101)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_stok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton4)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(bg961Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(bg961Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton2)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
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

    private void txt_namabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarangActionPerformed

    private void txt_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaActionPerformed

    private void txt_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stokActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clear();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        editData();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Menu_Admin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void table_barang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_barang1MouseClicked
        // TODO add your handling code here:
        int baris = table_barang1.getSelectedRow();

        String nama = table.getValueAt(baris,1).toString();
        txt_namabarang.setText(nama);

        String harga = table.getValueAt(baris, 2).toString();
        txt_harga.setText(harga);

        String stok = table.getValueAt(baris, 3).toString();
        txt_stok.setText(stok);

        String tanggal = table.getValueAt(baris, 4).toString();

        Date convert = null;
        try{
            convert = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        }catch(Exception e){
            System.out.println(e);
        }
        txt_tanggal.setDate(convert);

    }//GEN-LAST:event_table_barang1MouseClicked

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //        table_barang.setModel(table);
        tampilData();

        //        dispose();
        //        new stok_barang().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        //        try{
            //            String file = "src/report/report_barang.jasper";
            //            JasperPrint print = JasperFillManager.fillReport(file,null,koneksi.getKoneksi());
            //            JasperViewer view = new JasperViewer(print,false);
            //            view.setVisible(true);
            ////            JasperViewer.viewReport(print, false);
            //
            //        }catch(Exception e){
            //            JOptionPane.showMessageDialog(rootPane, e);
            //        }
        try {
            String file = "/Laporan/data_barang.jasper";
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file), null, koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Input_Minuman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Input_Minuman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Input_Minuman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Input_Minuman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Input_Minuman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private backgoundcoding.bg96 bg961;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_barang1;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_namabarang;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_stok;
    private com.toedter.calendar.JDateChooser txt_tanggal;
    // End of variables declaration//GEN-END:variables
}
