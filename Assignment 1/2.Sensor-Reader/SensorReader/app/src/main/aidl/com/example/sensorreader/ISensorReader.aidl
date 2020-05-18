// ISensorReader.aidl
package com.example.sensorreader;

interface ISensorReader {
    /**
     * Returns the data of the accelerometer and gyroscope in a single float array
     */
    float[] readData();
}
