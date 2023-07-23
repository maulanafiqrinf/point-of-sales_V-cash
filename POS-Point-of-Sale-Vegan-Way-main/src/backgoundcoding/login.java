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
public class login extends JPanel{
    private Image gambar3;
 
    public login () {
        gambar3 = new ImageIcon(getClass().getResource("/gambarasset/loginn.png")).getImage();
    }
 
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
 
        Graphics2D gd = (Graphics2D) grphcs.create();
        gd.drawImage(gambar3, 0, 0, getWidth(), getHeight(), null);
        gd.dispose();
    }
}
