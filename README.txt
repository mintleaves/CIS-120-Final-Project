=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1.2D Array: Each Tetrimino shape is stored using a 2D array, and all 9 types 
  are stored in a TreeMap.I used a 2D array as it best represents the shape 
  using four coordinates, and was the best way of visualisng the shapes. It also made it easy
  for me to access the coordinates to move the shapes. 
   

  2. File I/O: I used File I/O for implementing a scoreboard. The player's username is taken in, 
  and their score is recorded onto a text file. The top three scores are displayed on the leaderboard.
  This was the best way to implement this as I needed the leaderboard to contain the scores even
  after exiting the application, and this is best stored on a text file.

  3. Collections - Stack: I used a stack to implement a booster. Every five times the user presses
  the space bar to speed up the mino, they earn a 5 point and shape change boost. Every level up, they earn a 
  ten point and a different shape change boost. They can use these boosts in a LIFO manner, which is why
  I used a stack to store and pop them.

  4. JUnit Testing: I implemented various test cases to check the functionality of my core game.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Tetriminos has all the functionality for the individual shape. It deals with rotating a block, creating it, etc.
  GameCourt is where the bulk of the functionality of the game resides. Here, I implemented what the game would do
  when boosts are added, when a line is cleared, when a level is risen, etc. 
  Game is where the GUI implementation lies.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  I really struggled with testing- I think I have not done that aspect well enough. I also spent
  a long time actually figuring out the orientation of my blocks.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  There is a decent separation of functionality. I wish GameCourt would have been smoother and well
  maintained. I think the private state is well encapsulated, and I have provided many getters and setters
  to make testing easy. If I had the chance, I would make my code testable.
  



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  I just googled the basic rules of tetris and the orientation of the various minos.
