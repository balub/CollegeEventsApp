package com.example.balu.collegeapp;

public class Events {

    public Events(){}

    public Events(String event_img, String event_title, String event_details, String event_website, String event_contact, String event_actions) {
        this.event_img = event_img;
        this.event_title = event_title;
        this.event_details = event_details;
        this.event_website = event_website;
        this.event_contact = event_contact;
        this.event_actions = event_actions;
    }

    private String event_img;
    private String event_title;
    private String event_details;
    private String event_website;
    private String event_contact;

    public String getEvent_img() {
        return event_img;
    }

    public void setEvent_img(String event_img) {
        this.event_img = event_img;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_details() {
        return event_details;
    }

    public void setEvent_details(String event_details) {
        this.event_details = event_details;
    }

    public String getEvent_website() {
        return event_website;
    }

    public void setEvent_website(String event_website) {
        this.event_website = event_website;
    }

    public String getEvent_contact() {
        return event_contact;
    }

    public void setEvent_contact(String event_contact) {
        this.event_contact = event_contact;
    }

    public String getEvent_actions() {
        return event_actions;
    }

    public void setEvent_actions(String event_actions) {
        this.event_actions = event_actions;
    }

    private String event_actions;
}
