module WetApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires unirest.java;
    requires java.desktop;
    requires jackson.annotations;
    requires jackson.core;
    requires jackson.databind;
    requires json;

    opens pl.wojciechsiwek;
    opens pl.wojciechsiwek.view;
    opens pl.wojciechsiwek.controller;
    opens pl.wojciechsiwek.model;

}