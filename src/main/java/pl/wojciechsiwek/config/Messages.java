package pl.wojciechsiwek.config;

public class Messages {
    private static final String noLocationInputMessage = "Pole miejscowości nie może bć puste";
    private static final String dataActualizationFailedMessage = "Aktualizacja danych nie powiodła się";
    private static final String noLocationFoundMessage = "Aktualizacja danych nie powiodła się - nie znaleziono lokalizacji";
    private static final String tooManyConectionsMessage = "Aktualizacja danych nie powiodła się - zbyt wiele połączeń";
    private static final String incorrectAPIMessage = "Aktualizacja danych nie powiodła się - niepoprawny klucz API";

    public String getNoLocationInputMessage() {
        return noLocationInputMessage;
    }

    public String getDataActualizationFailedMessage() {
        return dataActualizationFailedMessage;
    }

    public String getNoLocationFoundMessage() {
        return noLocationFoundMessage;
    }

    public String getTooManyConectionsMessage() {
        return tooManyConectionsMessage;
    }

    public String getIncorrectAPIMessage() {
        return incorrectAPIMessage;
    }
}
