package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.concurrent.atomic.AtomicBoolean;

/** Commonly used alert messages */
public class AlertMessages {

    /** Alert to inform user of status or change
     *
     * @param message - the String message to display to user
     */
    public static void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** Alert to inform user of error
     *
     * @param message - the String error message to display to user
     */
    public static void errorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** Alert to confirm deletion of data
     *
     * @param message - the String confirmation message to display to the user
     * @return - AtomicBoolean return value which states whether user wants to move forward or not
     */
    public static AtomicBoolean confirmMessage(String message) {
        AtomicBoolean proceed = new AtomicBoolean(false);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                proceed.set(true);
            }});
        return proceed;
    }
}
