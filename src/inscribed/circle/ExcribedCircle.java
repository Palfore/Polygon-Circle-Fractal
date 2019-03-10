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

/**
 *
 * @author Nawar
 */
public class ExcribedCircle extends Fractal {
    public ExcribedCircle(int order) throws IllegalArgumentException {
        super(order);
        this.rotationOffSet = 1;
        this.equationFile = "ExcribedEquation.png";
    }
    
    @Override
    public double getCircleDistance(int circleIndex, double r_0, double r) {
        // The initial apothem + sum(radius+apothem) - final radius, gives the center of the i'th circle.
        double sumGamma = getSumGamma(circleIndex);
        return r_0 * (sumGamma + Math.cos(getTheta())*(1 + sumGamma)) - r*Math.cos(getTheta());
    }
    
    @Override
    public double getVertexDistance(double circleRadius) {
        return circleRadius;
    }
    
    @Override
    public double getGamma() {
        return (1 - Math.cos(this.getTheta())) / 2;
    }
}
