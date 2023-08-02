public class DataTypes {
    public static class Vector {
        double x = 0, y = 0, m = 0, d = 0;

        public Vector calcXY() {
            x = m * Math.cos(d);
            y = m * Math.sin(d);
            return this;
        }

        public Vector calcMD() {
            m = Math.sqrt((x*x)+(y*y));
            d = Math.atan2(y, x);
            return this;
        }

        public Vector setXY(double x, double y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Vector setMD(double m, double d) {
            this.m = m;
            this.d = Math.toRadians(d);
            return this;
        }
    }
}
