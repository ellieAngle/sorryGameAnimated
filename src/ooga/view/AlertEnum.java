package ooga.view;

import javafx.scene.control.Alert.AlertType;

/**
 * Enum class for AlertView
 */
public enum AlertEnum {
  SelectPiece(AlertType.INFORMATION, "Interaction Needed", "Pick a piece."),
  BadSelection(AlertType.ERROR, "Try Again", "Please select a valid option as designated by the highlighted pieces/squares."),
  BackendError(AlertType.ERROR, "Try Again", "Something failed. Please try again.");
  //UploadError(AlertType.ERROR, "Upload different file", "File without the correct information was inputted");


  private final AlertType alertType;
  private final String alertTitle;
  private final String alertMessage;

  AlertEnum(AlertType alertType, String alertTitle, String alertMessage){
    this.alertMessage = alertMessage;
    this.alertType = alertType;
    this.alertTitle = alertTitle;
  }

  /**
   * getter method for alert type
   * @return alertType variable
   */
  public AlertType getAlertType() {
    return alertType;
  }

  /**
   * Getter method for alert message
   * @return String alertMessage
   */
  public String getAlertMessage() {
    return alertMessage;
  }

  /**
   * Getter method for alert title
   * @return String alertTitle
   */
  public String getAlertTitle() {
    return alertTitle;
  }
}
