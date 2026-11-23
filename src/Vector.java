public class Vector {
    int x;
    int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector subtract(Vector v) {
        return new Vector(x - v.x, y - v.y);
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }


    public Vector normalized() {
        double magnitude = Math.sqrt(x*x + y*y);
        if (magnitude == 0) {
            return new Vector(0, 0); // Prevent 0/0
        }
        return new Vector((int)Math.round(x / magnitude), (int)Math.round(y / magnitude));
    }

    public boolean equals(Object obj) { //normal equals is bad for vector
        if (this == obj) return true;
        if (!(obj instanceof Vector)) return false;
        Vector v = (Vector) obj;
        return x == v.x && y == v.y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
