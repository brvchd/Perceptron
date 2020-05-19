package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParser {

    public InputParser(File fname) {
        this.fname = fname;
    }

    File fname;

    public List<Vector> parse() throws FileNotFoundException {

        List<Vector> list = new ArrayList<>();
        int type = 0; // 1 or 0
        Scanner input = new Scanner(this.fname);
        input.nextLine();
        while (input.hasNextLine()) {
            String line1 = input.nextLine();
            String[] row = line1.split(",");

            double[] x = new double[4];
            for (int i = 0; i < row.length - 2; i++) {
                    x[i] = Double.parseDouble(row[i]);
            }
            if (row[4].equals("Iris-setosa")) {
                type = 1;
            } else if (row[4].equals("Iris-versicolor") || row[4].equals("Iris-virginica"))
                type = 0;
            list.add(new Vector(x, type));
        }
        input.close();
        return list;
    }



}



