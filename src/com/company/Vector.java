package com.company;

public class Vector {
    double[] data;
    int LType; // 0 or 1

    public Vector(double[] data, int LType) {
        this.LType = LType;
        this.data = data;
    }
}