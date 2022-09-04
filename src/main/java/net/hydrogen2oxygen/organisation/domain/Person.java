package net.hydrogen2oxygen.organisation.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Person implements ITags {

    private String lastName;
    private String firstName;
    private ContactData contactData;
    private Address address;
    private Calendar birthDate;
    private Integer age;
    private boolean groupLead = false;
    private boolean groupLeadAssistant = false;
    private List<String> tags = new ArrayList<>();

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public ContactData getContactData() {
        return contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isGroupLead() {
        return groupLead;
    }

    public void setGroupLead(boolean groupLead) {
        this.groupLead = groupLead;
    }

    public boolean isGroupLeadAssistant() {
        return groupLeadAssistant;
    }

    public void setGroupLeadAssistant(boolean groupLeadAssistant) {
        this.groupLeadAssistant = groupLeadAssistant;
    }
}
