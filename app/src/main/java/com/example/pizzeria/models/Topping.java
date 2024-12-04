package com.example.pizzeria.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Enum representing the various toppings available for pizzas.
 *
 * Implements Parcelable for compatibility with Android's inter-Activity communication.
 * Each topping can be passed as part of a Pizza object using Intents or Bundles.
 */
public enum Topping implements Parcelable {
    SAUSAGE,
    PEPPERONI,
    GREEN_PEPPER,
    ONION,
    MUSHROOM,
    BBQ_CHICKEN,
    PROVOLONE,
    CHEDDAR,
    BEEF,
    HAM,
    OLIVE,
    SPINACH,
    PINEAPPLE;

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
    public static final Creator<Topping> CREATOR = new Creator<Topping>() {
        @Override
        public Topping createFromParcel(Parcel in) {
            return Topping.valueOf(in.readString());
        }

        @Override
        public Topping[] newArray(int size) {
            return new Topping[size];
        }
    };
}