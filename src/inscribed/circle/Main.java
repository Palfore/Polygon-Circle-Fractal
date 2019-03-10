/*
 * Copyright (C) 2019 Nawar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package inscribed.circle;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/** This program creates an animation for the Inscribed & Excribed Circle Problem.
 * Each frame consists of a polygon of order n with the appropriate set of nested
 * circles. Successive orders are displayed over time.
 * The purpose of this animation is to validate the mathematical description.
 * @author Nawar
 */
public class Main extends Canvas implements Runnable {
    private static final int starting_polygon = 3;
    
    private int n = starting_polygon;
    
    public static final int width = 1400;
    public static final int height = 800;
    public static final int initial_radius = 200;
    public static final int numCircles = 10;
    
    private static final int first_transition_delay_milliseconds = 3000;
    private static final int transition_milliseconds = 2000;
    
    /** Creates an animation window and begins the animation.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Inscribed Circle");
        Main canvas = new Main();
        canvas.setSize(Main.width, Main.height);
        
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        
        Thread thread = new Thread(canvas);
        thread.start();
    }
    
    /** Runs the animation loop.
     * The first transition is delayed according to the class delay.
     * After each transition_milliseconds, the polygon order will increase.
     */
    @Override
    public void run() {
        if (first_transition_delay_milliseconds > 0) {
            try {
                Thread.sleep(first_transition_delay_milliseconds);
            } catch (InterruptedException e) {}
        }
            
        while (true) {
            try {
                Thread.sleep(transition_milliseconds);
                n++;
            } catch (InterruptedException e) {}
            
            repaint();
        }
    }
    
    private void drawStats(Graphics g, Fractal fractal, double x_0, double y_0) {
        try {
            final BufferedImage image = ImageIO.read(new File(fractal.equationFile));
            final int w = image.getWidth();
            g.drawImage(image, (int) (x_0 - 1.2*w), (int) y_0*2, null);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18)); 
        g.drawString("N="+n, (int) x_0, (int) (0.95*Main.height));
        g.drawString(String.format("gamma=%.4f", fractal.getGamma()), (int) x_0, (int) (0.95*Main.height)+20);
    }
    
    private void drawFractal(Graphics g, Fractal fractal, double x_0, double y_0) {
        final double r_0 = initial_radius;
        
        drawStats(g, fractal, x_0, y_0*0.9);
        fractal.drawRegularPolygon(g, x_0, y_0, r_0);
        fractal.drawNestedCircles(g, x_0, y_0, r_0, numCircles);
    }
    
    /** Draws a regular polygon of order n, with a set of nested inscribed circles.
     * The first circle is placed in the center, the nested circles are placed
     * between each vertex and the previous (adjacent) circle.
     * 
     * @param g The graphics.
     */
    @Override
    public void paint(Graphics g) {
        drawFractal(g, new InscribedCircle(n), width/4, height/2);        
        drawFractal(g, new ExcribedCircle(n), 3*width/4, height/2);
    }
}

