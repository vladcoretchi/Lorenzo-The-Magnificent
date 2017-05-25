package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext  extends AbstractGameContext {


    public void initContext(ArrayList<Player> players) {

    }

    @Override
    public void interactWithPlayer(Player player) {
        //TODO: implement what player can do here and modify the model in this controller class
        //let the player choice if they wants to be excommunicated and assigned the negative effect to them



        //TODO:for each player that satisfied notify his activated observe
        setChanged(); //trigger sisto IV if is an observer
        notifyObservers();

        //phaseContext.interactWithPlayer();
    }


    @Override
    public ContextType getType() {
        return ContextType.CURCH_REPORT_CONTEXT;
    }





}


