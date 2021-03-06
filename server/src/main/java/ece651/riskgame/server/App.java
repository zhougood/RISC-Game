/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ece651.riskgame.server;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class App {
  RoomManager roomManager;
  Login login;
  Map<Integer, serverRoom> gameRooms;

  public App() throws IOException {
    roomManager = new RoomManager(1653);
    login = new Login("userTable.txt");
    gameRooms = new ConcurrentHashMap<>();
    roomManager.setGameRooms(gameRooms);
    login.setGameRooms(gameRooms);
  }

  public static void main(String[] args) {
    /*
    if ((args.length != 1) || (args[0].length() != 1) || args[0].charAt(0) < '1' || args[0].charAt(0) > '5') {
      System.out.println("Player number 2-5");
      return;
    }
    int playerNum = Integer.parseInt(args[0]);
    */
    try {
      App app = new App();
      Thread roomManageThread = new Thread(app.roomManager);
      roomManageThread.start();
      app.login.startSever(1651);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
