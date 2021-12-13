package com.coursework.homeapp3_0.database;

public class Appliance {
    private String name;
    private String model;
    private String company;
    private int power;
    private String status;

    public Appliance(String appliance, String model, String company, int power, String status) {
        this.name = appliance;
        this.model = model;
        this.company = company;
        this.power = power;
        this.status = status;
    }

    public Appliance() {}

    public String showCharacteristic(){
        return "CHARACTERISTIC:\n" + name + "\n" + "MODEL: " + model + "\nCOMPANY: " + company + "\nPOWER: "
                + power + " W\n" + "STATUS: [" + status + "]";
    }

    @Override
    public String toString() {
        return name + " [" + model + "]";
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getCompany() {
        return company;
    }

    public int getPower() {
        return power;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
