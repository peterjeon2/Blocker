# Blocker

As an aspiring self- taught programmer, I was fortunate enough to come across 
CS61B - Data Structures, taught by Josh Hug at U.C. Berkeley. I learned about many different data structures and algorithms. This project was 
the most challenging and rewarding aspect of the course. It was originally intended 
to be a group project, but I had the awesome opportunity to design and implement 
the project on my own.

# Project Specifications:
https://sp18.datastructur.es/materials/proj/proj2/proj2.

The purpose of the project is to create an engine for explorable worlds. It is meant to emulate
a typical software-engineering project, as there is little to no starter code. 

The code I wrote can be found in the "Core" folder. With the exception of the randomUtils class, 
every class was written by me.

# Game Features

Blocker is a high-score based game in which the player must navigate a maze-like cavern
and find the portal to reach the next level of the game.
Each level, the player gains 2 special moves, which allows them to create or break down a wall. 
The game ends when the player is caught by a ghost.

I created a simple algorithm that randomly generates a new set of rooms and hallways
for each level. I utilized Serialization to allow the player to save and load games
and also implemented an algorithm that makes the enemy ghosts track the player's movements.

# Deployment Instructions 
You will need to import the StdDraw library from Princeton. Then, run the Test class to start 
the game.
