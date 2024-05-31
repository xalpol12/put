WS:
- /join - add clientId to session memory, send all drawn frames in order to recreate the image history
- /drawing - on message sends new drawing frame to all sessions and saves in history
- /game-logic - send time frame each second, game logic implementation; after each rund send clientId that has a drawing
  permissions; block drawing on frontend
- /chat - users send guesses, server checks for the correct ones and adds points to sessionId that guessed in given time

REST:
`/sessions`
Join game endpoint  `/sessions/{gid}` POST `userid`
Create game endpoint `/sessions` POST returns `gid`

Lobby:
- Player not ready PUT
- Player ready -> if all ready start game

TODO:
Frontend
- [ ] Split routing into multiple htmls
- [ ] Mirror frame structure from backend
- [ ] Send POST for joining or creating
- [ ] Connect with ws
- [ ] Send HANDSHAKE to ws
- [ ] Deserialize DRAWING frames from ws
- [ ] Send DRAWING frames to ws

Flow:
Create (POST message to `/` creates )
Join (POST message to `/` adds )
- Client connects to ws endpoint -> nothing happens yet
- Client send handshake message -> gets added to a session