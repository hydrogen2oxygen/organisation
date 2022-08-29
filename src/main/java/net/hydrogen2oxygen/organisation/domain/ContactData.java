package net.hydrogen2oxygen.organisation.domain;

import java.util.ArrayList;
import java.util.List;

public class ContactData {

    private String phoneNumber;
    private String cellPhone;
    private String email;
    private List<String> contactPersons = new ArrayList<>();

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<String> contactPersons) {
        this.contactPersons = contactPersons;
    }
}
