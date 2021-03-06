package ece651.riskgame.server;

import ece651.riskgame.shared.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class RoomManager implements Runnable{

    private int port;
    private serverRoom latestRoom;
    private int latestRoomNum;
    private Map<Integer, serverRoom> gameRooms;
    private int playerNum = 2;
    private Logger logger = Logger.getInstance();

    public RoomManager(int port) throws IOException {
        this.port = port;
        latestRoom = new serverRoom();
        latestRoomNum = 0;
        RiskGame game = new RiskGame(playerNum, "new_territories.csv", "new_adj_list.csv", latestRoom);
        latestRoom.setRiskgame(game);
        Thread t = new Thread(game);
        t.start();
    }

    public void setGameRooms(Map<Integer, serverRoom> gameRooms) {
        this.gameRooms = gameRooms;
        gameRooms.put(latestRoomNum, latestRoom);

    }


    @Override
    public void run() {
        try {
            ServerSocket roomManager = new ServerSocket(port);
            while(true) {
                Socket player = roomManager.accept();
                ObjectInputStream ois = new ObjectInputStream(player.getInputStream());
                int roomnumber = (int) ois.readObject();
                String username = (String) ois.readObject();
                if(roomnumber < 0) { //start a new room
                    if(latestRoom.getNumber() != playerNum) {
                        ObjectOutputStream oos = new ObjectOutputStream(player.getOutputStream());
                        oos.writeObject(latestRoomNum);
                        logger.writeLog("[RoomManager] : " + username + " participates the RISC ROOM " + latestRoomNum);
                        latestRoom.addNewPlayer(player, username);
                        latestRoom.oisMap.put(player, ois);
                        latestRoom.oosMap.put(player, oos);
                    } else {
                        latestRoom = new serverRoom();
                        latestRoomNum++;
                        ObjectOutputStream oos = new ObjectOutputStream(player.getOutputStream());
                        oos.writeObject(latestRoomNum);
                        logger.writeLog("[RoomManager] : " + username + " participates the RISC ROOM " + latestRoomNum);
                        latestRoom.addNewPlayer(player, username);
                        latestRoom.oisMap.put(player, ois);
                        latestRoom.oosMap.put(player, oos);
                        gameRooms.put(latestRoomNum, latestRoom);
                        RiskGame game = new RiskGame(playerNum, "new_territories.csv", "new_adj_list.csv", latestRoom);
                        latestRoom.setRiskgame(game);
                        Thread t = new Thread(game);
                        t.start();
                    }

                } else {
                    if(gameRooms.containsKey(roomnumber) && !gameRooms.get(roomnumber).close_status) {
                        ObjectOutputStream oos = new ObjectOutputStream(player.getOutputStream());
                        String result = "succ";
                        oos.writeObject(result);

                        serverRoom temp = gameRooms.get(roomnumber);
                        String tempcolor = temp.nameColorMap.get(username);

                        for(Map.Entry<Socket, String> i: temp.sockets.entrySet()) {
                            if(i.getValue().equals(tempcolor)) temp.sockets.remove(i.getKey());
                        }
                        temp.sockets.put(player, tempcolor);
                        temp.oisMap.put(player, ois);
                        temp.oosMap.put(player, oos);
                        temp.online.put(tempcolor, true);
                        logger.writeLog("[RoomManager] : " + username + " return to the RISC ROOM " + roomnumber);

                    } else {
                        ObjectOutputStream oos = new ObjectOutputStream(player.getOutputStream());
                        String result = "fail";
                        oos.writeObject(result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
