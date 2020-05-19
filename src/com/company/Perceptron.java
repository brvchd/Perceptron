package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Perceptron {

    private static final double LEARNING_RATE = 0.1;

    static double theta = 0.6;
    static double localError, globalError;
    static int iteration;
    static double output;
    static int max;
    public static File test = new File("iris_test.txt");
    public static File train = new File("iris_training.txt");

    private static double[] weights = new double[5];
    private static List<Vector> trainList = new ArrayList<>();
    private static List<Vector> testList = new ArrayList<>();

    private static double[] fillWeights() {
        double[] weights = new double[5];
        Random r = new Random();

        for (int i = 0; i < weights.length; i++) {
            weights[i] = r.nextDouble();
        }

        return weights;
    }

    private static int calculateOutput(double theta, double weights[], double x1, double x2, double x3, double x4) {
        double sum = x1 * weights[0] + x2 * weights[1] + x3 * weights[2] + x4 * weights[3] + weights[4];

        return (sum >= theta) ? 1 : 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputParser trainParser = new InputParser(test);
        InputParser testParser = new InputParser(train);

        trainList = trainParser.parse(); //training file parse
        weights = fillWeights(); //generating random weights

        Scanner sc = new Scanner(System.in);
        System.out.println("Number of the iterations: ");
        max = sc.nextInt();

        testList = testParser.parse(); //test file parse
        for (int j = 0; j < 5; j++) {
            System.out.println("Weights: " + weights[j] + " ");
        }

        iteration = 0;
        do {
            iteration++;
            globalError = 0;

            for (int p = 0; p < trainList.size(); p++) {

                output = calculateOutput(theta, weights, trainList.get(p).data[0], trainList.get(p).data[1], trainList.get(p).data[2],
                        trainList.get(p).data[3]);

                localError = trainList.get(p).LType - output;
                weights[0] += LEARNING_RATE * localError * trainList.get(p).data[0];
                weights[1] += LEARNING_RATE * localError * trainList.get(p).data[1];
                weights[2] += LEARNING_RATE * localError * trainList.get(p).data[2];
                weights[3] += LEARNING_RATE * localError * trainList.get(p).data[3];
                weights[4] += LEARNING_RATE * localError;

                theta = theta - LEARNING_RATE * localError;
                globalError += (localError * localError);
            }

        } while (globalError != 0 && iteration <= max);

        System.out.println(weights[0] + "*x + " + weights[1] + "*y +  " + weights[2] + "*z + " + weights[3] + " = 0");

        double[] nValue = new double[4];
        double corr = 0;
        for (int i = 0; i < testList.size(); i++) {
            output = calculateOutput(theta, weights, testList.get(i).data[0], testList.get(i).data[1], testList.get(i).data[2],
                    testList.get(i).data[3]);
            System.out.println("New Test Point:");
            System.out.println("x1 = " + testList.get(i).data[0] + ",x2 = " + testList.get(i).data[1] + ",x3 = "
                    + testList.get(i).data[2] + ", x4 = " + testList.get(i).data[3]);
            if ((testList.get(i).LType) == output) {
                corr++;
            }
            System.out.println("class = " + output);
        }


        double result = (corr / testList.size()) * 100;
        System.out.println("Accuracy of perceptron: " + result + "%" + "\n");

        System.out.println("Would you like to put your own values?y/n");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if(answer.equals("y")) {
            for (int i = 0; i < nValue.length; i++) {
                System.out.println("Enter value" + i + ": ");
                nValue[i] = sc.nextDouble();
            }
            output = calculateOutput(theta, weights, nValue[0], nValue[1], nValue[2],
                    nValue[3]);
        } else {
            System.out.println("Bye-bye");
            System.exit(0);
        }
        String type;
        if(output == 1){
            type = "Iris-setosa";
        }else type = "not Iris-setosa";

        System.out.println("Type of this vector is: " + type);
    }
}