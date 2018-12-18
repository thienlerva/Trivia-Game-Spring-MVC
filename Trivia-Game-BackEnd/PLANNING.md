# Servlets:
### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Landing Page
###### LandingPageServlet: ['/new-user']()
- Client sends request to sever when user hits this page and new session id is assigned to the user. The app will not reconnect to an existing game that they have left.

###### JoinLobbyServlet: ['/connect-to-lobby']()
- Client sends request with **lobby key** to the server. 
- If the lobby is full or has already started the game the client will be notified with a modal and will not be redirected.

- If they key is valid the clients session will be connected to the appropriate lobby and the client will redirect them to the ['/wating']() page.

### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Select Lobby Page
###### GetAllLobbyInfo:['/get-all-lobby-info']()
- Client will call this request on a set interval (2 seconds)
- Client will send the 'category', server will send json array with info for all lobbies in that category
- Client will not refresh the list unless the data in the returned json object is different from the current on in use
- 
###### JoinLobbyServlet: ['/connect-to-lobby']()
- Client sends request with **lobby key** to the server. 
- If they key is valid the clients session will be connected to the appropriate lobby and the client will redirect them to the ['/wating']() page.

### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Create New Lobby Page

###### CreateNewLobbyServlet: ['/create-new-lobby']()
- Client sends category,difficulty,max-players,public/private, question-number,name to the server.
- Server will create the lobby on the backend
- Client will be redirected to the ['/waiting']() page. The client will have the ability to start the game when they want because they are the lobby leader.

### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Waiting In Pre Game Lobby Page

###### SetUserName: ['/set-lobby-username']()
- Client will send username to server, and the client will be connected to the waiting lobby

###### WaitingServlet: ['/waiting']()
- Client will send update requests at a certain interval.
- The client will only recive a new lobby object if a change was made.
- The lobby oject will contain info on connected players, messages, ready status, ect..

###### StartGameServlet: ['/start-game']()
- **Lobby Leader Only** Client sends request to server to start the game
- Server changes the game state signaling for all the associated clients to redirect to the ['/playing']() page.

### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Playing Game Page

###### : ['/update-active-game']()
- Client sends request to get the current game state on a set interval.
- If no changes were made since the last request then only the timer is updated

###### SendMessageServlet: ['/send-message']()
- Client sends a new message to the server to be added to the game chat
- Server adds this message to the chat

###### SubmitAnswerServlet: ['/submit-answer']()
- Client sends answer to the server
- Server adds this message to the chat

#Functionalitly Overview

##### LeaderBoardSpecs

* GlobalLeaderBoardsComponent
- Will be sorted by overall score and limited to 25 rows
- Uses ajax to request the leader boards table at load time
- Has a refresh button to request the current data
- Uses AngularDataTables
- Has a back button

* GlobalStatsDBTable
- Has a row for each player of each game
- Each column is a particular stat (score, max-streak, average-time, ect). Think of as many as you can within reason.

* GlobalLeaderBoardsBean
- Make a private variable for every column
- Auto generate getters and mutators

* GlobalLeaderBoardsDAO
- Write the function to get the data
- Write the function to add a record

* GlobalLeaderBoardsService
- This is where you write the function to call the DAO
- The function can be static

* GetGlobalLeaderBoardsServlet
- Get the leaderboards data from the service
- Map the Bean to a json string (like we've been doing)
- Send the json to the client


###### GlobalLeaderBoardsServlet: ['/global-leaderboards']()
- 
