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

- [x] Log things diagnostic
- [ ] Fix websocket endpoints to handle multiple sessions
- [ ] Delete /join and let /draw send all the previous frames

Message:

- Type