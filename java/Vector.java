class Vector {
    double         x, y;
    double norm;    
    Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.norm = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }    
    Vector() {
        this(0, 0);
    }
    double angleTo(Vector that) {
        double cosTheta = ((this.x * that.x) + (this.y * that.y)) / (this.norm * that.norm);
        return Math.acos(cosTheta);
    }    
    static Vector[] getPerpendicularUnit(Vector v) {
        Vector[] vecs = new Vector[2];
        double constant;
        if(v.x == 0) { 
            constant = 1.0 / Math.sqrt(1.0 + ((v.x * v.x) / (v.y * v.y)));
            vecs[0] = new Vector(constant, -constant * (v.x / v.y));
        }
        else {
            constant = 1.0 / Math.sqrt(1.0 + ((v.y * v.y) / (v.x * v.x)));
            vecs[0] = new Vector(-constant * (v.y / v.x), constant);
        }
        vecs[1] = new Vector(-vecs[0].x, -vecs[0].y);
        
        return vecs;
    }
}
