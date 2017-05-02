package model.board;

/*this class has the aim to aggregate what resources and cards the player have collected*/
public class PersonalBoard {
private Resource resources;
//cards	
private ArrayList<DevelopmentCard> territories= new ArrayList<DevelopmentCard>();
private ArrayList<DevelopmentCard> characters= ArrayList<DevelopmentCard>();
private ArrayList<DevelopmentCard> buildings= ArrayList<DevelopmentCard>();
private ArrayList<DevelopmentCard> ventures= ArrayList<DevelopmentCard>();
private ArrayList<LeaderCard>  leaders= ArrayList<LeaderCard>();
//bonus tile
PersonalBonusTile personal= new PersonalBonusTile();

}
