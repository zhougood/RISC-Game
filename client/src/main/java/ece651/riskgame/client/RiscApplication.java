/*
* This Java source file was generated by the Gradle 'init' task.
*/
package ece651.riskgame.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;

import ece651.riskgame.client.controllers.GameController;
import ece651.riskgame.client.controllers.LoginController;
import ece651.riskgame.shared.UserInit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RiscApplication extends Application {
  // GUIPlayer
  GUIPlayer guiPlayer;

  GameController gameController;
  LoginController loginController;

  /**
   * Method called to launch the frontend application
   */
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws IOException, ClassNotFoundException {


    /*
    String ip = "0.0.0.0";
    int port = 1651;
    // connect to server
    Socket serverSocket = null;
    try {
      serverSocket = new Socket(ip, port);
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: " + ip);
      System.exit(1);
    } catch (ConnectException e) {
      System.err.println("Can not connect to server. Please contact 984-377-9836.");
      System.exit(1);
    }
    System.out.println("Connection Estabilished");
    */

    loginController = new LoginController(new UserInit());

    URL loginxml = getClass().getResource("/ui/login.fxml");
    FXMLLoader loginLoader = new FXMLLoader(loginxml);
    loadControllers(loginLoader);

    Parent loginPane = loginLoader.load();

    loginController.setLoginPane(loginPane);
    //loginController.setSocket(serverSocket);
    Scene scene = new Scene(loginPane, 1138, 823);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();

    /*
    guiPlayer = new GUIPlayer(serverSocket);
    guiPlayer.initializeGame();

    gameController = new GameController(guiPlayer);

    URL xmlResource = getClass().getResource("/ui/main.fxml");

    FXMLLoader loader = new FXMLLoader(xmlResource);
    loadControllers(loader);
    
    Pane gp = loader.load();

    Scene scene = new Scene(gp, 1138, 823);
    gameController.setScene(scene);
    
    URL cssResource = getClass().getResource("/ui/css/main.css");
    scene.getStylesheets().add(cssResource.toString());

    initialize(scene);
    
    stage.setScene(scene);
    stage.show();
    */
  }
  
  
  private void loadControllers(FXMLLoader loader) {
    HashMap<Class<?>, Object> controllers = new HashMap<>();    
    //controllers.put(GameController.class, gameController);
    controllers.put(LoginController.class, loginController);
    loader.setControllerFactory((c) -> {
      return controllers.get(c);
    });    
  }


}
