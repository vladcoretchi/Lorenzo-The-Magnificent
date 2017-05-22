package it.polimi.ingsw.LM34.UI.CLI;

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.Console;

/**
 * this interface remap I/O streams, and will be implemented in {@link it.polimi.ingsw.LM34.UI.TestCli}, {@link it.polimi.ingsw.LM34.UI.Launcher}
 */
public interface IOInterface {
     Scanner readUserInput = new Scanner(System.in);
     PrintWriter printToConsole = new PrintWriter(System.out, true);
}
