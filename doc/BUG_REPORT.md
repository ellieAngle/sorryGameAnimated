## Description

Player Homes and Bases get written before player count selection is made which causes a missing key error when attempting to run from the properties file made.

## Expected Behavior

Can select more than 4 players and write to the properties file after the player selection.

## Current Behavior

Throws a error because a key is missing for the players larger than 4.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. in a customization screen select number of players larger than 4
 2. attempt to play game

## Failure Logs

    Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
	at ooga.view.GameScreen.setUpInitialVariables(GameScreen.java:123)
	at ooga.view.GameScreen.makeScene(GameScreen.java:70)
	at ooga.view.StartScreen.beginGame(StartScreen.java:305)
	at ooga.view.StartScreen.lambda$makeChoicesAndStart$1(StartScreen.java:122)

## Hypothesis for Fixing the Bug

testPlayerNumOver4Bug() test method in StartScreen Tests verifies the error. To fix the bug, move setFileParameter() to be called in the playernumchoice button action. 
