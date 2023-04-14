package es.lamesa.parchis.util;

import java.util.Random;

public class RandomGenerator {

    public int generarEntradaTorneoNormal() {
        Random random = new Random();
        int numero = random.nextInt(100);
        if (numero < 10) {
            // 10% de probabilidad de que sea 300
            return 300;
        } 
        else if (numero < 40) {
            // 30% de probabilidad de que sea 200
            return 200;
        } 
        else if (numero < 70) {
            // 30% de probabilidad de que sea 150
            return 150;
        } 
        else {
            // 30% de probabilidad de que sea 100
            return 100;
        }
    }

    public int generarEntradaTorneoRapido() {
        Random random = new Random();
        int numero = random.nextInt(4);
        switch (numero) {
            case 0:
                return 25;
            case 1:
                return 50;
            case 2:
                return 75;
            case 3:
                return 100;
            default:
                return 0;
        }
    }

}
