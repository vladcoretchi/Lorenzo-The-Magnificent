package it.polimi.ingsw.LM34.Enums.Model;

/**
 * Created by GiulioComi on 02/06/2017.
 */
public enum LeaderNames {
    FRANCESCO_SFORZA("Francesco Sforza"),
    LUDOVICO_ARIOSTO("Ludovico ariosto"),
    FILIPPO_BRUNELLESCHI("Filippo Brunelleschi"),
    SIGISMONDO_MALATESTA("Sigismondo Malatesta"),
    GIROLAMO_SAVONAROLA("Girolamo Savonarola"),
    LUCREZIA_BORGIA("Lucrezia Borgia"),
    FEDERICO_DA_MONTEFELTRO("Federico da Montefeltro"),
    LORENZO_DE_MEDICI("Lorenzo de Medici"),
    SISTO_IV("Sisto IV"),
    CESARE_BORGIA("Cesare Borgia"),
    MICHELANGELO_BUONAROTTI("Michele Buonarotti"),
    SANTA_RITA("Santa Rita"),
    GIOVANNI_DALLE_BANDE_NERE("Giovanni dalle Bande Nere"),
    COSIMO_DE_MEDICI("Cosimo de Medici"),
    LEONARDO_DA_VINCI("Leonardo da Vinci"),
    BARTOLOMEO_COLLEONI("Bartolomeo Colleoni"),
    SANDRO_BOTTICELLI("Sandro Botticelli"),
    LUDOVICO_III_GONZAGA("Ludovico III Gonzaga"),
    LUDOVICO_IL_MORO("Ludovico il Moro"),
    PICO_DELLA_MIRANDOLA("Pico della Mirandola");


    /*The contextName is used in CLI and GUI for showing a beautified and human friendly name of the leaders*/
    private String leaderName;

    LeaderNames(String contextName) {
        this.leaderName = contextName;
    }

    public String getLeaderName() {
        return leaderName;
    }

}
