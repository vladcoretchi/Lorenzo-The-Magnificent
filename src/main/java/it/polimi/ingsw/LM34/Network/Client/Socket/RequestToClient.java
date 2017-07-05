package it.polimi.ingsw.LM34.Network.Client.Socket;

import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * Created by vladc on 5/28/2017.
 * inspired by https://stackoverflow.com/questions/12935709/call-a-specific-method-based-on-enum-type
 */
public enum RequestToClient {
    LOGIN {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                Boolean loginResult = socketConnection.getInputStream().readBoolean();

                socketConnection.getNetworkController().loginResult(loginResult);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    SET_EXCOMMUNICATION_CARDS {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<ExcommunicationCard> excommunicationCards = (List<ExcommunicationCard>) socketConnection.getInputStream().readObject();

                socketConnection.setExcommunicationCards(excommunicationCards);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    UPDATE_TOWERS {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<Tower> towers = (List<Tower>) socketConnection.getInputStream().readObject();

                socketConnection.updateTowers(towers);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    UPDATE_COUNCIL_PALACE {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                CouncilPalace councilPalace = (CouncilPalace) socketConnection.getInputStream().readObject();

                socketConnection.updateCouncilPalace(councilPalace);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    UPDATE_MARKET {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                Market market = (Market) socketConnection.getInputStream().readObject();

                socketConnection.updateMarket(market);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    UPDATE_PRODUCTION_AREA {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                WorkingArea productionArea = (WorkingArea) socketConnection.getInputStream().readObject();

                socketConnection.updateProductionArea(productionArea);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    UPDATE_HARVEST_AREA {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                WorkingArea harvestArea = (WorkingArea) socketConnection.getInputStream().readObject();

                socketConnection.updateHarvestArea(harvestArea);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    //TODO
    INFORM_IN_GAME_PLAYERS {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            /*try {

            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }*/
        }
    },
    UPDATE_PLAYERS_DATA {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<Player> players = (List<Player>) socketConnection.getInputStream().readObject();

                socketConnection.updatePlayersData(players);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    UPDATE_DICE_VALUES {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<Dice> diceValues = (List<Dice>) socketConnection.getInputStream().readObject();

                socketConnection.updateDiceValues(diceValues);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    TURN_MAIN_ACTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                Exception lastActionValid = (Exception) socketConnection.getInputStream().readObject();

                socketConnection.getOutputStream().writeObject(socketConnection.turnMainAction(lastActionValid));
                socketConnection.getOutputStream().flush();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    TURN_SECONDARY_ACTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                Exception lastActionValid = (Exception) socketConnection.getInputStream().readObject();

                socketConnection.getOutputStream().writeObject(socketConnection.turnSecondaryAction(lastActionValid));
                socketConnection.getOutputStream().flush();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    FAMILY_MEMBER_SELECTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<FamilyMember> familyMembers = (List<FamilyMember>) socketConnection.getInputStream().readObject();

                socketConnection.getOutputStream().writeInt(socketConnection.familyMemberSelection(familyMembers));
                socketConnection.getOutputStream().flush();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    SERVANTS_SELECTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                Integer servantsAvailable = socketConnection.getInputStream().readInt();
                Integer minimumServantsRequested = socketConnection.getInputStream().readInt();

                socketConnection.getOutputStream().writeInt(socketConnection.servantsSelection(servantsAvailable, minimumServantsRequested));
                socketConnection.getOutputStream().flush();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    RESOURCE_EXCHANGE_SELECTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<Pair<Resources, ResourcesBonus>> choices = (List<Pair<Resources, ResourcesBonus>>) socketConnection.getInputStream().readObject();

                socketConnection.getOutputStream().writeInt(socketConnection.resourceExchangeSelection(choices));
                socketConnection.getOutputStream().flush();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    LEADER_CARD_SELECTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<LeaderCard> leaderCards = (List<LeaderCard>) socketConnection.getInputStream().readObject();

                socketConnection.getOutputStream().writeObject(socketConnection.leaderCardSelection(leaderCards));
                socketConnection.getOutputStream().flush();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    CHURCH_SUPPORT {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                socketConnection.getOutputStream().writeBoolean(socketConnection.churchSupport());
                socketConnection.getOutputStream().flush();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    BONUS_TILE_SELECTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<BonusTile> availableBonusTiles = (List<BonusTile>) socketConnection.getInputStream().readObject();

                socketConnection.getOutputStream().writeInt(socketConnection.bonusTileSelection(availableBonusTiles));
                socketConnection.getOutputStream().flush();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    LEADER_CARD_SELECTION_PHASE {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<LeaderCard> availableLeaderCards = (List<LeaderCard>) socketConnection.getInputStream().readObject();

                socketConnection.getOutputStream().writeInt(socketConnection.leaderCardSelectionPhase(availableLeaderCards));
                socketConnection.getOutputStream().flush();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    },
    COUNCIL_PRIVILEGE_BONUS_SELECTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<Resources> availableBonuses = (List<Resources>) socketConnection.getInputStream().readObject();

                socketConnection.getOutputStream().writeInt(socketConnection.selectCouncilPrivilegeBonus(availableBonuses));
                socketConnection.getOutputStream().flush();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    };

    abstract void readAndHandle(SocketClient socketConnection);
}
