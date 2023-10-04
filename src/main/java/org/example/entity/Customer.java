package org.example.entity;

public class Customer extends BaseEntity{
    private String name;
    private int rentalPoints;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getRentalPoints() {
        return rentalPoints;
    }

    public void setRentalPoints(int rentalPoints) {
        this.rentalPoints = rentalPoints;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", rentalPoints=" + rentalPoints +
                '}';
    }
}