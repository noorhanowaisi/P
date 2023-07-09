package com.example.project;

public class Home implements Comparable<Home> {
    private String gender,ownerName , homeName , ownerNumber , price , location ;
    double lat,lng;
    Home()
    {

    }
    Home(String gender ,String ownerName ,String homeName , String ownerNumber , String price , String location,double lat,double lng)
    {
        this.gender = gender ;
        this.ownerName = ownerName ;
        this.ownerNumber = ownerNumber ;
        this.homeName = homeName ;
        this.location = location ;
        this.price = price ;
        this.lat=lat;
        this.lng=lng;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(String ownerNumber) {
        this.ownerNumber = ownerNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return ownerName + "   "  +homeName + "   "  + ownerNumber + "   "  + price + "   "  + location ;
    }

    @Override
    public int compareTo(Home o) {
        this.price.replaceAll("$","");
        o.price.replaceAll("$","");
        return Integer.compare(Integer.parseInt(this.price),Integer.parseInt(o.getPrice()));
    }
}
