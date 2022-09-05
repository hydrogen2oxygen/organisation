package net.hydrogen2oxygen.organisation.domain;

import java.util.ArrayList;
import java.util.List;

public class NominativJson {

    private NominativAddress address;
    private Double lat;
    private Double lon;
    private String display_name;
    private Double importance;
    private String osm_id;
    private String osm_type;
    private String place_id;
    private String svg;
    private String type;

    public NominativAddress getAddress() {
        return address;
    }

    public void setAddress(NominativAddress address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public Double getImportance() {
        return importance;
    }

    public void setImportance(Double importance) {
        this.importance = importance;
    }

    public String getOsm_id() {
        return osm_id;
    }

    public void setOsm_id(String osm_id) {
        this.osm_id = osm_id;
    }

    public String getOsm_type() {
        return osm_type;
    }

    public void setOsm_type(String osm_type) {
        this.osm_type = osm_type;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getSvg() {
        return svg;
    }

    public void setSvg(String svg) {
        this.svg = svg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private class NominativAddress {
        private String city;
        private String city_district;
        private String construction;
        private String continent;
        private String country;
        private String country_code;
        private String house_number;
        private String neighbourhood;
        private String postcode;
        private String public_building;
        private String state;
        private String suburb;
        private List<Double> boundingbox = new ArrayList<>();

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity_district() {
            return city_district;
        }

        public void setCity_district(String city_district) {
            this.city_district = city_district;
        }

        public String getConstruction() {
            return construction;
        }

        public void setConstruction(String construction) {
            this.construction = construction;
        }

        public String getContinent() {
            return continent;
        }

        public void setContinent(String continent) {
            this.continent = continent;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getHouse_number() {
            return house_number;
        }

        public void setHouse_number(String house_number) {
            this.house_number = house_number;
        }

        public String getNeighbourhood() {
            return neighbourhood;
        }

        public void setNeighbourhood(String neighbourhood) {
            this.neighbourhood = neighbourhood;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getPublic_building() {
            return public_building;
        }

        public void setPublic_building(String public_building) {
            this.public_building = public_building;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSuburb() {
            return suburb;
        }

        public void setSuburb(String suburb) {
            this.suburb = suburb;
        }

        public List<Double> getBoundingbox() {
            return boundingbox;
        }

        public void setBoundingbox(List<Double> boundingbox) {
            this.boundingbox = boundingbox;
        }
    }
}
