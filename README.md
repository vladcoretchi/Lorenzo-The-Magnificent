# LorenzoTheMagnificent
[Lorenzo il Magnifico](http://www.craniocreations.it/prodotto/lorenzo-il-magnifico/) board game developed in Java

Powered by [LM34] developers

### Team Members
Nome			|Student ID	|Politecnico Mail
----------------|-----------|----------------
Comi Giulio		|827172		|giulio1.comi@mail.polimi.it
Coretchi Vlad	|829603		|vlad.coretchi@mail.polimi.it
Dorata Roberto	|828144		|roberto.dorata@mail.polimi.it

Digital version of [Lorenzo il Magnifico](http://www.craniocreations.it/prodotto/lorenzo-il-magnifico/), a board game created by Cranio Creation and developed as Software Engineering Final Bachelor Project.


# Getting Started

These instruction will let you run Lorenzo il Magnifico on your local machine. 

### Prerequisites 

clone 

```
git clone https://github.com/vladcore/LM34.git
```

Inside `target` folder there will be `LM34Server.jar` and `LM34Client.jar` files.

> **_Note: the `target` directory is of an old build and the GUI does not find some resources files due to incorrect folder naming so the game will freeze before the initial Leader Cards choice. For a correct resource files names you will need to rebuild at least `LM34Client.jar`._**

## Run 

### RMI and Socket
RMI: port 20002
Socket: port 20001

move into `target` directory and launch `LM34Server.jar` (one instance only) and `LM34Client.jar` by double clicking or using the terminal:

### Run Server
```
cd target
java -jar LM34Server.jar
```
### Run Client
```
cd target
java -jar LM34Client.jar

```

After at least 2 players are connected, the server waits for other players for 2 minutes before starting the game. This timeout is configurable in target/classes/configurations/config.json line: 4.
If 4 player connect to the same game room, the game starts immediately.

## CLI
Command line interface is available as user interface in order to play this game in a fashion way.

## GUI

GUI developed in JavaFx is also an option :-).

Players will be ordered by Turn order (the highest will be the first player to move, and under him the second one, then the third and so on) 

Player's name will be showed into GUI's title bar 

Users' personal board will be visible once clicked onto a the name of a player

Hovering an action/tower slot will show the corresponding reward that will be collectable from the player that places his pawn in there  

To place a "family member" (pawn) inside an action slot, double click onto the desidered action slot and different kinds of popup  will be shown based on the availability of the move and the additional actions required to be able to occupy the slot correctly.

## Screenshots
Image           | Link
----------------|-----------
Login for CLI and GUI | https://ibb.co/hPJNVF
Waiting room for CLI and GUI |https://ibb.co/c9TZcv
Gameboard | https://ibb.co/jzYpsv
Turn | https://ibb.co/gnqpcv
Personal Board | https://ibb.co/bF9MqF

## License

This software is licensed under the MIT License

Lorenzo il Magnifico is a trademark of Cranio Creations s.r.l

Copyright (C) 2017 Giulio Comi, Vlad Coretchi, Roberto Dorata 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:


The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.


THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.



