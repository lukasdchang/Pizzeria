package com.example.pizzeria.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Enum representing the available sizes for a pizza.
 *
 * Implements Parcelable for compatibility with Android's inter-Activity communication.
 * Each size can have associated pricing or other attributes depending
 * on the pizza implementation.
 *
 * @author Yousef Naam & Lukas Chang
 */
public enum Size implements Parcelable {
    SMALL,
    MEDIUM,
    LARGE;

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
    public static final Creator<Size> CREATOR = new Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel in) {
            return Size.valueOf(in.readString());
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };
}