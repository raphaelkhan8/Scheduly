package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class SignInLogger {
    public static final String filename = "login_activity.txt";

    public SignInLogger() { }

    public static void trackLog(String user, boolean loggedIn) {
        LocalDateTime localTime = LocalDateTime.now();
        String logMessage = "";
        if (loggedIn) {
            logMessage = user + " successfully logged in at " + localTime;
        } else {
            logMessage = user + " was unable to login at " + localTime;
        }
        try {
            FileWriter fw = new FileWriter(filename, true);
            PrintWriter logFile = new PrintWriter(fw);
            logFile.println(logMessage);
            logFile.close();
            System.out.println(user + " - has been written to the log");
        } catch (IOException throwables) {
            throwables.printStackTrace();
        }
    }

}
