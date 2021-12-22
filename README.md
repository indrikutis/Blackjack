# Blackjack

## Goal
The purpose of a game is to have a hand that totals higher than the dealer's, but doesn't total higher than 21.

## Terminology
1. Hit - action, when you wish a dealer to give you another card
2. Stand - action to stop drawing cards
3. Bust - when the total points of a hand exceed 21

# Game setup

The game contains a number of players and a dealer.

1. Everyone besides a dealer places a bet.
2. The dealers deals 1 card face-up to all players and 1 card face up to themselves.
3. The dealers deals 1 more card face-up to all players and deals 1 card face-down to themselves.

# Game rules

1. If the two face-up cards total 21, the player automatically wins 1.5 times the bet from the dealer and is done for the round (chips are rounded to the player's advantage).
2. If the player doesn't total 21, player can hit or stand. There is no limit to how many cards the player can have.
3. When the dealer has gone around the table, they flip their second, face-down card. If the total is 16 or under, the dealer has to take another card. If it's 17 or higher, they have to stay with their hand. If the dealer busts, every player in the round wins twice their bet.
4. If the dealer doesn't bust, only players that have a hand higher than the dealers win twice their bet and everyone else looses their initial bet.
5. When the round is over, a new round begins.

## Points system:
-   Number cards are worth their face value (2-10) 
-	Jacks, queens, and kings are worth 10 each
-	Aces are worth either 1 or 11 (player chooses)

# Assumptions and other things

1. Every player starts with 5 chips and the dealer has 10000 chips (consider this is as an unlimited number of chips).
2. Points are calculated automatically. If it is possible, the value of ACE is 11, overwise, it's 1.
3. The player is removed from the game once they don't have enough chips to bet anything.


Not all the rules are implemented in this version of blackjack.

# Getting started

These instructions will help to get a copy of a game to be run on local machine.

## Installing

By following these simple steps, a game can be build and run on your local machine. 

Instructions for windows/linux OS:

1. Clone the project with the command: git clone https://github.com/indrikutis/Blackjack.git
2. Compile the files: jabac *.java
3. Run the game: java Driver
4. Follow directions on the screen

# Hope you enjoy playing!

