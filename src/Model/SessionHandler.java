package Model;

import java.util.Locale;
import java.util.ResourceBundle;

public class SessionHandler {
    private static Locale location;
    private static ResourceBundle userLanguage;

    // set user's location
    public static void setLocation() {
        location = Locale.FRENCH;
//        location = Locale.getDefault();
    }

    // get user's location
    public static Locale getLocation() {
        return location;
    }

    // get user's language
    public static ResourceBundle getUserLanguage() {
        userLanguage = ResourceBundle.getBundle("Utils/Language", SessionHandler.getLocation());
        return userLanguage;
    }
}
