/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgoundcoding;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author maulana fiqri
 */
public class bg96 extends JPanel{
    private Image gambar4;
 
    public bg96() {
        gambar4 = new ImageIcon(getClass().getResource("/gambarasset/960x540-4.jpg")).getImage();
    }
 
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
 
        Graphics2D gd = (Graphics2D) grphcs.create();
        gd.drawImage(gambar4, 0, 0, getWidth(), getHeight(), null);
        gd.dispose();
    }
}
