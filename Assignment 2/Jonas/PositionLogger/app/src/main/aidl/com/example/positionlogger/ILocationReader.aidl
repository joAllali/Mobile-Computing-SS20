// ILocationReader.aidl
package com.example.positionlogger;

// Declare any non-default types here with import statements

interface ILocationReader {
    /**
     * Returns the data of the location manager
     */
    double getLatitude();
    double getLongitude();
    double getDistance();
    double getAverageSpeed();
}
