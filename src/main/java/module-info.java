module WetApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires unirest.java;

    opens pl.wojciechsiwek;
    opens pl.wojciechsiwek.view;
    opens pl.wojciechsiwek.controller;

}