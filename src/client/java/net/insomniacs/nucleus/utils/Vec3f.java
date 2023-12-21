package net.insomniacs.nucleus.utils;

public class Vec3f {
    public float x; public float y; public float z;

    public static Vec3f NONE = new Vec3f(0, 0, 0);

    public Vec3f(int[] xyz) {
        this.x = xyz[0];
        this.y = xyz[1];
        this.z = xyz[2];
    }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

}