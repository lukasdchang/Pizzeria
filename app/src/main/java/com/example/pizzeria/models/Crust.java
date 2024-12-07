package com.example.pizzeria.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Enum representing the different crust types available for pizzas.
 * Each crust type corresponds to specific styles or types of pizzas.
 * This can vary between Chicago-style and New York-style pizzas.
 *
 * Implements Parcelable for compatibility with Android's inter-Activity communication.
 *
 * @author Yousef Naam & Lukas Chang
 */
public enum Crust implements Parcelable {
    DEEP_DISH,
    PAN,
    STUFFED,
    BROOKLYN,
    THIN,
    HAND_TOSSED;

    /**
     * Parcelable implementation: Write the enum value to a Parcel.
     *
     * @param dest  the Parcel in which the object should be written
     * @param flags additional flags about how the object should be written
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name());
    }

    /**
     * Parcelable implementation: Describe the contents (typically 0).
     *
     * @return an integer representing the contents
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable.Creator implementation to create instances from a Parcel.
     */
    public static final Creator<Crust> CREATOR = new Creator<Crust>() {
        @Override
        public Crust createFromParcel(Parcel in) {
            return Crust.valueOf(in.readString());
        }

        @Override
        public Crust[] newArray(int size) {
            return new Crust[size];
        }
    };
}