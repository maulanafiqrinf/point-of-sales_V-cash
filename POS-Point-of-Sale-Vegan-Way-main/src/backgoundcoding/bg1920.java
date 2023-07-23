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
 * @author Lenovo
 */
public class bg1920 extends JPanel{
    private Image gambar5;
 
    public bg1920() {
        gambar5 = new ImageIcon(getClass().getResource("/gambarasset/newbackgrnd (1).png")).getImage();
    }
 
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
 
        Graphics2D gd = (Graphics2D) grphcs.create();
        gd.drawImage(gambar5, 0, 0, getWidth(), getHeight(), null);
        gd.dispose();
    }
}
