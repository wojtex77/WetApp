package pl.wojciechsiwek.controller;

public enum WeatherDataResult {
    SUCCESS,
    FAILED,
    FAILED_BY_TOO_MANY_CONNECTIONS, //429
    FAILED_NO_LOCATION_FOUND, //404
    FAILED_WRONG_KEY //401
}
