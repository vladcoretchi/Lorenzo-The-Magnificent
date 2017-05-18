package interfacce;

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.Console;

public interface IOInterface {
     Scanner readUserInput = new Scanner(System.in);
     PrintWriter printToConsole = new PrintWriter(System.out, true);

}
