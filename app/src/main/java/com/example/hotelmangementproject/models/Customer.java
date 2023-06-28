package com.example.hotelmangementproject.models;

public class Customer {
    private String id;
    private String name;
    private String dateOfBirth;
    private String personalIndentifier;
    private String address;

    public Customer(){
        this.id = "";
        this.name = "";
        this.dateOfBirth = "";
        this.personalIndentifier = "";
        this.address = "";
    }
    public Customer(String id, String name, String dateOfBirth, String personalIndentifier, String address) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.personalIndentifier = personalIndentifier;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPersonalIndentifier() {
        return personalIndentifier;
    }

    public void setPersonalIndentifier(String personalIndentifier) {
        this.personalIndentifier = personalIndentifier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
