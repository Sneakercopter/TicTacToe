# TicTacToe
Fun way for me to increase my understanding of search trees for my AI course.


# Tic-Tac-Toe AI
This AI makes use of the BFS (Breadth First Search) technique of traversing through a tree. This is a blind search technique and therefore uses no heuristics or domain knowledge to try and find the best solution, therefore it is not super efficent. However it does find the optimal solution and is also guaranteed to find a solution if one exists. Solution, in this case, being a game state where the computer wins. 

# Problems With BFS
BFS is by no means the best way of playing Tic-Tac-Toe, therefore this is not a perfect AI. The way this AI plays the game is directed towards the best way for it to be victorious. This means the AI plays the game in such a way that it always is trying to win, but never really trying to stop you from winning. As it is now, if the AI finds a scenario where the human player would be able to make a winning move in it's tree before it is able to find a winning move for itself, it will make that preventative move to stop the human from winning. However, this does not always work and in no way makes this AI hard to beat - but I still thought it was interesting and fun to make! :-). Another point is that if the AI finds no way for it to make a move towards winning, and also cannot find a way to prevent you from winning - it will make a random legal move. At the moment it does not play to try and influence a draw, but this could be interesting to test out. 

# Code
The code is messy, and BFS.java exists only because I built a BFS to test it's functionality before I implemented it into my Tic-Tac-Toe game. Feel free to comment on my poor code habits, this was all written in  a span of about 2 hours after a day of studying. I'll laugh with you!

# Future Improvements
Moving forward i'd like to implement the Minimax Algorithm into this. Minimax is much more suited to play a game like this (taking into account moves that prevent the other player from winning) and it is also moving into being a heuristic algorithm rather than a blind search as it is now.

Enjoy!
