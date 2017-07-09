package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Network.RMI.RMIConnection;
import it.polimi.ingsw.LM34.Network.RMI.RMIServer;
import it.polimi.ingsw.LM34.Network.Socket.SocketConnection;
import it.polimi.ingsw.LM34.Network.Socket.SocketServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class Server {
    private static final Integer SOCKET_PORT = 20001;
    private static final Integer RMI_PORT = 20002;

    private static Server serverInstance;

    private static SocketServer socketServer;
    private static RMIServer rmiServer;

    private static List<GameRoom> gameRooms;
    private static GameRoom waitingRoom;

    private Server() {
        waitingRoom = new GameRoom();
        gameRooms = new ArrayList<>();

        socketServer = new SocketServer(SOCKET_PORT);
        rmiServer = new RMIServer(RMI_PORT);
    }

    public static Server getInstance() {
        if(serverInstance == null)
            serverInstance = new Server();

        return serverInstance;
    }

    public static void terminate() {
        socketServer.terminate();
    }

    public static boolean login(String username, String password) {
        Boolean found = false;
        for (int i = 0; !found && i < gameRooms.size(); i++) {
            String[] gameRoomPlayers = gameRooms.get(i).getPlayers();
            for (int j = 0; !found && j < gameRoomPlayers.length; j++) {
                if (username == gameRoomPlayers[j])
                    if(gameRooms.get(i).getGameManager().getPlayers().stream()
                            .noneMatch(p -> p.getPlayerName().equals(username) && !p.isConnected()))
                        found = true;
            }
        }
        if (!found) {
            String[] gameRoomPlayers = waitingRoom.getPlayers();
            for (int j = 0; !found && j < gameRoomPlayers.length; j++) {
                if (gameRoomPlayers[j].equals(username))
                    if(Arrays.stream(waitingRoom.getPlayers()).noneMatch(p -> p.equals(username)))
                        found = true;
            }
        }
        return !found;
    }

    public static GameRoom addPlayerToGameRoom(String username, ServerNetworkController networkController) {
       Optional<GameRoom> gameRoom = gameRooms.stream().filter(gr -> Arrays.stream(gr.getPlayers()).anyMatch(p -> p.equals(username))).findFirst();
       if(gameRoom.isPresent()) {
           gameRoom.get().substitutePlayer(username, networkController);
           try {
               networkController.startGame();
           } catch (NetworkConnectionException ex) {
               LOGGER.log(Level.INFO, ex.getMessage(), ex);
           }
           Optional<Player> gameManagerPlayer = gameRoom.get().getGameManager().getPlayers().stream().filter(player -> player.getPlayerName().equals(username)).findFirst();
           gameManagerPlayer.ifPresent(Player::setConncted);
           return gameRoom.get();
       }
       else {
           if (Arrays.stream(waitingRoom.getPlayers()).anyMatch(p -> p.equals(username)))
               waitingRoom.substitutePlayer(username, networkController);
           else
               waitingRoom.addPlayer(username, networkController);
           return waitingRoom;
       }
    }

    public static void startWaitingGame() {
        gameRooms.add(waitingRoom);
        waitingRoom = new GameRoom();
    }

    public static void removeRMIConnection(RMIConnection connection) {
        rmiServer.removeClosedConnection(connection);
    }

    public static void removeSocketConnection(SocketConnection connection) {
        socketServer.removeClosedConnection(connection);
    }

    public static void removeGameRoom(GameRoom gameRoom) {
        Integer index = gameRooms.indexOf(gameRoom);
        if(index >= 0)
            gameRooms.remove(index.intValue());
    }
}
