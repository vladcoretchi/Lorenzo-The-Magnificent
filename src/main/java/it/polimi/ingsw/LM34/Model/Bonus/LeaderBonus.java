package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 08/05/2017.
 */
public class LeaderBonus extends PermanentBonus {
    private boolean hasCopyAbility; //lorenzo de medici
    private boolean canPlaceFamilyMemberInOccupiedSpaces; //ludovico ariosto
    private boolean noMilitaryPointToTakeTerritoryLimit; //cesare borgia
    private Integer faithPointsReward = 1; //girolamo savonarola
    private boolean AdditionalHarvestAction; //francesco sforza
    private boolean saveCoinsInOccupiedTower; //filippo brunelleschi
    private Integer ValueAdditionToNeutralFamilyMember; //sigismondo malatesta
    private Integer CoinsReward = 3; //michelangelo buonarroti
    private Resources multipleResReward = new Resources(1,1,1,0,0,0,0); //giovanni dalle bande nere
    private boolean AdditionalProductionAction; //leonardo da vinci
    private Resources multipleResReward2 = new Resources(0,0,0,0,2,0,1); //sandro botticelli
    private Integer DiceValuesMaxed = 5; //ludovico il moro
    private Integer ColouredFamilyMembersAddValue = 2; //lucrezia borgia
    private Integer MaxDiceValue = 6; //federico di montefeltro
    private Integer addChurchSostegno = 5; //sisto IV
    private boolean doubledBonus; //santa rita
    private Resources multipleResReward3 = new Resources(0,0,0,3,0,0,1); //cosimo de medici
    private Integer victoryPointsReward = 4; //bartolomeo colleoni
    private Integer councilPrivilege = 1; //ludovico III gonzaga
    private Integer SaveCoinsInBoughtCards = 3; //pico della mirandola



}
