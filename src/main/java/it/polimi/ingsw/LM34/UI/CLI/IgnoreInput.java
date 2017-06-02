package it.polimi.ingsw.LM34.UI.CLI;

import java.io.IOException;
import java.io.InputStreamReader;

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

            }
        }
    }

    public void terminate() {
        this.run = false;
    }
}
