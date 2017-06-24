package it.polimi.ingsw.LM34.Network.Client.Socket;

import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
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
                e.printStackTrace();
            }
        }
    },
    SET_EXCOMMUNICATION_CARDS {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<ExcommunicationCard> excommunicationCards = (List<ExcommunicationCard>) socketConnection.getInputStream().readObject();

                socketConnection.setExcommunicationCards(excommunicationCards);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    },
    UPDATE_TOWERS {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<Tower> towers = (List<Tower>) socketConnection.getInputStream().readObject();

                socketConnection.updateTowers(towers);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    },
    UPDATE_COUNCIL_PALACE {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                CouncilPalace councilPalace = (CouncilPalace) socketConnection.getInputStream().readObject();

                socketConnection.updateCouncilPalace(councilPalace);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    },
    UPDATE_MARKET {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                Market market = (Market) socketConnection.getInputStream().readObject();

                socketConnection.updateMarket(market);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    },
    UPDATE_PRODUCTION_AREA {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                WorkingArea productionArea = (WorkingArea) socketConnection.getInputStream().readObject();

                socketConnection.updateProductionArea(productionArea);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    },
    UPDATE_HARVEST_AREA {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                WorkingArea harvestArea = (WorkingArea) socketConnection.getInputStream().readObject();

                socketConnection.updateHarvestArea(harvestArea);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    },
    UPDATE_PLAYERS_DATA {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<Player> players = (List<Player>) socketConnection.getInputStream().readObject();

                socketConnection.updatePlayersData(players);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    },
    UPDATE_DICE_VALUES {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                List<Dice> diceValues = (List<Dice>) socketConnection.getInputStream().readObject();

                socketConnection.updateDiceValues(diceValues);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    },
    TURN_MAIN_ACTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                Boolean lastActionValid = socketConnection.getInputStream().readBoolean();

                socketConnection.getOutputStream().writeObject(socketConnection.turnMainAction(lastActionValid));
                socketConnection.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },
    TURN_SECONDARY_ACTION {
        @Override
        void readAndHandle(SocketClient socketConnection) {
            try {
                Boolean lastActionValid = socketConnection.getInputStream().readBoolean();

                socketConnection.getOutputStream().writeObject(socketConnection.turnSecondaryAction(lastActionValid));
                socketConnection.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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
                e.printStackTrace();
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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
                e.printStackTrace();
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    abstract void readAndHandle(SocketClient socketConnection);
}
