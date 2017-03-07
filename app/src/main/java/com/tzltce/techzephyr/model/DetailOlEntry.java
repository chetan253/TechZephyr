package com.tzltce.techzephyr.model;

/**
 * Created by Chetan on 18-12-2015.
 */
public class DetailOlEntry {

    String id, name, college, event, workshop, phone, email;

    public DetailOlEntry(){

    }

    public DetailOlEntry(String id, String name, String college){
        this.id = id;
        this.name = name;
        this.college = college;
    }

    public DetailOlEntry(String id, String name, String college, String event, String workshop, String phone, String email){
        this.id = id;
        this.name = name;
        this.college = college;
        this.event = event;
        this.workshop = workshop;
        this.phone = phone;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getCollege() {
        return this.college;
    }
    public String getEvent() {
        return this.event;
    }
    public String getWorkshop() {
        return this.workshop;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getEmail() {
        return this.email;
    }



}
