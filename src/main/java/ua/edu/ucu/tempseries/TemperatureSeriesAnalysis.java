package ua.edu.ucu.tempseries;

import lombok.Getter;

import java.util.Arrays;
import java.util.InputMismatchException;

@Getter
public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int seriesSize;

    public TemperatureSeriesAnalysis() {
        temperatureSeries = null;
        seriesSize = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        for (double temperature : temperatureSeries) {
            if (temperature < -273) {
                throw new InputMismatchException();
            }
        }
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
        seriesSize = temperatureSeries.length;
    }

    public double average() {
        if (seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double tempSum = 0;
        double temperature;
        for (int i = 0; i < seriesSize; i++) {
            temperature = temperatureSeries[i];
            tempSum += temperature;
        }
        return tempSum / seriesSize;
    }

    public double deviation() {
        if (seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double average = this.average();
        double squareSum = 0;
        double temperature;
        for (int i = 0; i < seriesSize; i++) {
            temperature = temperatureSeries[i];
            squareSum += Math.pow(temperature - average, 2);
        }
        return Math.sqrt(squareSum / seriesSize);
    }

    public double min() {
        if (seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double minTemp = temperatureSeries[0];
        double temperature;
        for (int i = 0; i < seriesSize; i++) {
            temperature = temperatureSeries[i];
            if (temperature < minTemp) {
                minTemp = temperature;
            }
        }
        return minTemp;
    }

    public double max() {
        if (seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double maxTemp = temperatureSeries[0];
        double temperature;
        for (int i = 0; i < seriesSize; i++) {
            temperature = temperatureSeries[i];
            if (temperature > maxTemp) {
                maxTemp = temperature;
            }
        }
        return maxTemp;
    }

    public double findTempClosestToZero() {
        return this.findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double closestTemp = temperatureSeries[0];
        double minDiff = Math.abs(closestTemp - tempValue);
        double diff = 0;
        double temperature;
        for (int i = 0; i < seriesSize; i++) {
            temperature = temperatureSeries[i];
            diff = Math.abs(temperature - tempValue);
            if (diff < minDiff) {
                minDiff = diff;
                closestTemp = temperature;
            } else if (diff == minDiff && temperature > closestTemp) {
                closestTemp = temperature;
            }
        }
        return closestTemp;
    }

    public double[] findTempsLessThen(double tempValue) {
        if (seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double smallerTemp[] = new double[seriesSize];
        int ind = 0;
        double temperature;
        for (int i = 0; i < seriesSize; i++) {
            temperature = temperatureSeries[i];
            if (temperature < tempValue) {
                smallerTemp[ind] = temperature;
                ind++;
            }
        }
        return Arrays.copyOfRange(smallerTemp, 0, ind);
    }

    public double[] findTempsGreaterThen(double tempValue) {
        if (seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double greaterTemp[] = new double[seriesSize];
        int ind = 0;
        double temperature;
        for (int i = 0; i < seriesSize; i++) {
            temperature = temperatureSeries[i];
            if (temperature >= tempValue) {
                greaterTemp[ind] = temperature;
                ind++;
            }
        }
        return Arrays.copyOfRange(greaterTemp, 0, ind);
    }

    public TempSummaryStatistics summaryStatistics() {
        if (seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        TempSummaryStatistics tempSummaryStatistics = new TempSummaryStatistics();
        tempSummaryStatistics.setAvgTemp(this.average());
        tempSummaryStatistics.setDevTemp(this.deviation());
        tempSummaryStatistics.setMaxTemp(this.max());
        tempSummaryStatistics.setMinTemp(this.min());
        return tempSummaryStatistics;
    }

    public int addTemps(double... temps) {
        System.out.println(temps.length);
        for (double temperature : temps) {
            if (temperature < -273) {
                throw new InputMismatchException();
            }
        }
        if (seriesSize == 0) {
            temperatureSeries = Arrays.copyOf(temps, temps.length);
            seriesSize = temperatureSeries.length;
            return seriesSize;
        }

        int newSize = temperatureSeries.length;
        while (newSize < seriesSize + temps.length) {
            newSize *= 2;
        }
        if (newSize != temperatureSeries.length) {
            double newTempSeries[] = new double[newSize];
            System.arraycopy(temperatureSeries, 0, newTempSeries, 0, seriesSize);
            temperatureSeries = newTempSeries;
        }
        System.arraycopy(temps, 0, temperatureSeries, seriesSize, temps.length);
        seriesSize += temps.length;
        return seriesSize;
    }
}
