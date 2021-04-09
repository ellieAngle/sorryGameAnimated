package ooga.view;


import javafx.scene.control.Alert;

/**
 *AlertView.java extends Alert and handles the Front-end alerts that informs the user of errors, incorrect selections, and of interactions
 */
public class AlertView extends Alert {
  /**
   * Constructor utilizes alertEnum to set Alert values
   * @param alertEnum
   */
  public AlertView(AlertEnum alertEnum){
    super(alertEnum.getAlertType());
    this.setTitle(alertEnum.getAlertTitle());
    this.setContentText(alertEnum.getAlertMessage());
  }
}
