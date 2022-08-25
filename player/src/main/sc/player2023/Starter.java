package sc.player2023;

import jargs.gnu.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sc.api.plugins.IGamePlugin;
import sc.networking.clients.LobbyClient;
import sc.player.IPlayerClient;
import sc.player2023.logic.IPersistentGameHandler;
import sc.player2023.logic.Logic;
import sc.shared.SharedConfiguration;

import java.io.File;

/**
 * Hauptklasse des Clients, die über Konsolenargumente gesteuert werden kann.
 * Sie veranlasst eine Verbindung zum Spielserver.
 */
public class Starter {
  private static final Logger logger = LoggerFactory.getLogger(Starter.class);

  public Starter(String host, int port, String reservation, String roomId, int runCount) {
    // Strategie zuweisen
    IPersistentGameHandler logic = new Logic();

    for(int i = 0; i < runCount; i++) {
      try {
        IPlayerClient client = new LobbyClient(host, port).asPlayer(logic, false);

        // einem Spiel beitreten
        if(reservation != null && !reservation.isEmpty()) {
          client.joinGameWithReservation(reservation);
        } else if(roomId != null) {
          client.joinGameRoom(roomId);
        } else {
          client.joinGame(IGamePlugin.loadPluginId());
        }
        logic.reset();

      } catch(Exception e) {
        logger.error("Beim Starten des Clients ist ein Fehler aufgetreten:", e);
        e.printStackTrace();
        System.exit(1);
      }
    }
  }

  public static void main(String[] args) {
    System.setProperty("file.encoding", "UTF-8");

    // Parameter definieren
    CmdLineParser parser = new CmdLineParser();
    CmdLineParser.Option hostOption = parser.addStringOption('h', "host");
    CmdLineParser.Option portOption = parser.addIntegerOption('p', "port");
    CmdLineParser.Option reservationOption = parser.addStringOption('r', "reservation");
    CmdLineParser.Option runCountOption = parser.addIntegerOption('n', "number");
    CmdLineParser.Option roomOption = parser.addStringOption("room");
    CmdLineParser.Option verifyOption = parser.addBooleanOption("verify");

    try {
      // Parameter auslesen
      parser.parse(args);
    } catch(CmdLineParser.OptionException e) {
      // bei einem Fehler die Hilfe anzeigen
      showHelp(e.getMessage());
      System.exit(2);
    }

    if(parser.getOptionValue(verifyOption) == Boolean.TRUE)
      System.exit(0);

    // Parameter laden
    String host = (String) parser.getOptionValue(hostOption, "localhost");
    int port = (Integer) parser.getOptionValue(portOption, SharedConfiguration.DEFAULT_PORT);
    String reservation = (String) parser.getOptionValue(reservationOption);
    int runCount = (Integer) parser.getOptionValue(runCountOption, 1);
    String room = (String) parser.getOptionValue(roomOption);

    // einen neuen Starter erzeugen, der den/die Client(s) erzeugt
    new Starter(host, port, reservation, room, runCount);
  }

  private static void showHelp(String errorMsg) {
    String jarName = new File(Starter.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getName();
    System.out.println("\n" + errorMsg);
    System.out.println("\nFolgende Parameter sind erlaubt: \n"
        + "java -jar " + jarName + " [{-h,--host} hostname]\n"
        + "                               [{-p,--port} port]\n"
        + "                               [{-r,--reservation} reservierung]\n"
        + "                               [{-n,--number} run count]\n"
        + "                               [--room raumnummer]\n");
    System.out.println("Beispiel: \n"
        + "java -jar " + jarName + " --host 127.0.0.1 --port 10500 --reservation 1234\n");
  }

}
