# Treasure Finder Multiplayer Game

Client-Server Architecture individual project. 

The project consists in a client and a server, which I have implemented during [Software Engineering 1][1] class at the University of Vienna. The network communication was already implemented by the [lecturer][2], and my task was to develop the client and the server. <br>

The client has an artificial intelligence that does not require human intervention. All decisions should be taken by the artificial intelligence. In the description below, there is more about that.

Summary of the tasks and phases of the projects are available at the end of this readme file.
Full project description, as received during class, can be found inside the Assignment folder in German language.

#### Highlights of what I have done:

-	Written requirements analyses. Documented typical scenario, alternative scenario and possible error cases of two of the functional requirements. (all of them can be found inside the Dokumentation/Teilaufgabe 1 - Planung folder)
-	Modeled class and sequential diagrams for client and server.
-	Implemented MVC pattern to separate client’s components.
-	Used SLF4J to log the behavior and errors of the client.
-	Used JUnit and Mockito to write 22 Unit Tests and achieve 77,5% coverage. (4895 of 6317 client’s total instructions)
-	Used Test Driven Development for the server implementation.
-	Server can run 999 concurrent games.
-	My AI implementation reached the semi-finals of Software Engineering 1 tournament at the University of Vienna.



#### Short description and my design decisions:


Short description of how the game works: Firstly, the client has to establish a connection to the server. Then, wait for the second player(client) to also register to the game. When it is first's client turn, generate a valid half map and send it to the server. The same will do the second client as well. The server will merge those two maps and place treasures randomly on each player's half. After that, the game starts. As mentioned earlier, the game is turn based and each client has to send moves to the server to go to another field. The client can only move to the left, right, up or down. The client can never move outside the map or into a water field (it looses instantly). While moving, the AI has to firstly discover the treasure and then the enemy's castle. From a mountain field, the AI can see if there is a treasure or a castle around the current position (8 fields around the current field). The first client to pick the treasure and reach the enemy castle wins.


* Used converters to receive and send the network data.
* Map is generated randomly, and then it is checked to see if the rules are fulfilled, if not, it is generated again.
* Used Floodfill Algorithm to check for islands.
* Instead of having at least 5 mountain fields, I have decided to have at least 15 so that my map is _harder_ so that the enemy requires more steps to find my castle.
* Castle is not randomly positioned, but it is placed on the most inaccessible grass field on the map. (None to little grass fields around it)
* Used strategy pattern to switch between treasure finding and castle finding.
* My AI will choose as target the most unexplored zone of the map, that means a position which has many neighbor fields that are unexplored yet.
* For the shortest path between my current AI's position and the target field, I have decided to use Dijkstra's Algorithm.

<br>

<p align="middle">
  <img src="https://github.com/arynor96/treasure-finder/blob/main/Dokumentation/Teilaufgabe%201%20-%20Planung/client_klassendiagramm_png.png?raw=true"/>
  Client Class Diagram
</p>

<br>


## UI

<p align="middle">
  <img src="https://github.com/arynor96/treasure-finder/blob/main/Dokumentation/Teilaufgabe%201%20-%20Planung/planed-UI.png?raw=true"/>
</p>

The final version of the UI looks similar to the one documented in the first phase of the project. Only small changes were made to the ASCI Codes of two elements, so it looks fine in any linux terminal.


## Summary of our tasks and phases:

We had to follow these steps during the class:

##### Phase 1: Planning & Designing
* Read about the project, game rules and do a requirements' analysis.
* Read how the already implemented network protocol works and how the XML bodies look like.
* Write requirements documentation.
* Design the architecture of the client and the server. For that, class and sequential diagrams were required for the client and for the server. (all of them can be found inside the Dokumentation/Teilaufgabe 1 - Planung folder)
<br>

##### Phase 2: Implement the client
* Start implementing the client, an online server was provided by the lecturer so that we can test our clients in a game against AI, against our own client or against the client of another student.
* Implement the network communication protocol using REST.
* Implement a CLI so that we can see the behavior of our AI (how it moves or how the generated map looks like).
* Implement the MVC pattern.
* Design an algorithm to generate a valid map before sending it to the server. It has to be random generated, contain no islands and at least 5 mountain fields, 24 grass fields, 7 water fields and a castle placed on a grass field.
* Implement an AI which is able to send correct moves to the server. The AI should find a treasure, pick the treasure up and go with the treasure to the enemy's castle to win the game.
* Use logging with the proper log levels.
* Write meaningful unit testing. Those should include parametrized tests, negative tests and Mockito. The test coverage should be at least 65%.
* Write checked and unchecked exceptions.
<br>

##### Phase 3: Implement the server
* Start implementing the server, which should be able to proof at least 8 business rules and run 999 concurrent games.
* Optional task for bonus points which I have done: Test Driven Development.


[1]: https://ufind.univie.ac.at/en/course.html?lv=051040&semester=2022W
[2]: https://ufind.univie.ac.at/en/person.html?id=54217
