package it.polimi.ingsw.LM34.UI.CLI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class IgnoreInput implements Runnable{

    private boolean run;

    @Override
    public void run() {
        this.run = true;
        InputStreamReader in = new InputStreamReader(System.in);

        while(this.run) {

            try {
                in.read();
            }
            catch (IOException e) {
                LOGGER.log(Level.INFO, "Error in ignoring unexpected input");
            }
        }
    }

    public void terminate() {
        this.run = false;
    }
}
