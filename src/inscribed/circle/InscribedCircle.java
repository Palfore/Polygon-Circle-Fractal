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
public class InscribedCircle extends Fractal {
    public InscribedCircle(int order) throws IllegalArgumentException {
        super(order);
        this.rotationOffSet = 0;
        this.equationFile = "Equation.png";
    }
    
    @Override
    public double getCircleDistance(int circleIndex, double r_0, double r) {
        double sumGamma = getSumGamma(circleIndex);
        return r_0 * (1 + 2 * sumGamma) - r;
    }
    
    @Override
    public double getVertexDistance(double r_0) {
        return r_0 / Math.cos(getTheta());
    }
    
    @Override
    public double getGamma() {
        return (1 - Math.cos(getTheta())) / (1 + Math.cos(getTheta()));
    }
}
