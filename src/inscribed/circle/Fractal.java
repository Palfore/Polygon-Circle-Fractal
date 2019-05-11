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

import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author Nawar
 */
public abstract class Fractal {
    public String equationFile;
    public int n;

    protected int rotationOffSet = 1;
    
    public Fractal(int order) throws IllegalArgumentException {
        if (order < 3) {
            throw new IllegalArgumentException("The lowest order polygon possible is 3. " + order + " isn't valid.");
        }
        this.n = order;  
    }
    
    public void drawRegularPolygon(Graphics g, double x_0, double y_0, double r_0) {
        Polygon boundingShape = new Polygon();
        for (int i = 0; i < this.n; i++) {
            double O = 2 * Math.PI / this.n; // Angle between two vertices (from center)
            
            // The pi/2 offset rotates such that a point is always at the top.
            double x = x_0 + this.getVertexDistance(r_0)*Math.cos(i*O - Math.PI/2.0);
            double y = y_0 + this.getVertexDistance(r_0)*Math.sin(i*O - Math.PI/2.0);
            boundingShape.addPoint((int) x, (int) y);
        }
        g.drawPolygon(boundingShape);
    }
    
    public void drawNestedCircles(Graphics g, double x_0, double y_0, double r_0, int numCircles) {
        final double gamma = getGamma();
        
        /// Draw the Nested Circles
        for (int i = 0; i < numCircles; i++) {
            // Recursive relation results in powerlaw.
            double r = Math.pow(gamma, i) * r_0;
            double circleDistance = getCircleDistance(i, r_0, r);
            
            // Draw one stack for each vertex.
            for (int j = rotationOffSet; j <= 2*n; j += 2) { // j condition => j*pi/n <= 2pi (cover full circle)
                double x = x_0 - circleDistance * Math.sin(j*Math.PI/n);
                double y = y_0 - circleDistance * Math.cos(j*Math.PI/n);

                drawCircle(g, (int) x, (int) y, (int) r);
            }
        }
    }
    
    public double getTheta() {
        return Math.PI / (double) this.n;
    }
    
    
    public double getSumGamma(int circleIndex) {
        double sumGamma = 0; // The sum(gamma^i) gives the sum of nested circle radii.
        double gamma = getGamma();
        for (int j = 1; j <= circleIndex; j++) {
            sumGamma += Math.pow(gamma, j);
        }
        return sumGamma;
    }
    
    public abstract double getCircleDistance(int circleIndex, double r_0, double r);
    public abstract double getGamma();
    public abstract double getVertexDistance(double circleRadius);
    
    /** Draw a circle from the center of the screen.
     * @param g Graphics
     * @param x The x coordinate from the center of the screen.
     * @param y The y coordinate from the center of the screen.
     * @param r The radius of the circle
     */
    protected void drawCircle(Graphics g, int x, int y, int r) {
        g.drawOval(x-r, y-r, 2*r, 2*r);
    }
}
