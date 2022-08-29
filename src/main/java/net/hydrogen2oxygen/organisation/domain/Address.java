package net.hydrogen2oxygen.organisation.domain;

public class Address {

    private Position position;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String countryIsoCode3;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryIsoCode3() {
        return countryIsoCode3;
    }

    public void setCountryIsoCode3(String countryIsoCode3) {
        this.countryIsoCode3 = countryIsoCode3;
    }
}
