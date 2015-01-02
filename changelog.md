# Changelog for Revenge of the Octocat

## Before 21/12/14

### Ethan Cheng

Pushed some starter code

### Jesse Elliott

Said hi to the repository page

## 21/12/14

### Jesse Elliott

Changelog initiated

## 25/12/14

### Ethan Cheng

Said Merry Christmas to the repo, gave it the present of backgrounds! Yay art!

## 27/12/14

### Ethan Cheng:

Restarted, overhauled old starter code, recreated the following files (in order, over a period of an hour and a half of straight coding):

	- BackGroundLoader.java

		Container for GfxRenderer, containing the background of the JFrame

	- Entity.java

		Superclass for all mobs, i.e. the Octocat , bugs , and segfaults

	- Octocat.java

		Octocat, containing sprites, movement algorithms (key listeners)

	- Controller.java

		Wrapper for Octocat and BackGroundLoader <-- Overhauled
		--> NEW --> Wrapper for GfxRenderer, controls the objects and storyline

	- Game.java

		Session of one Controller

	- GfxRenderer.java

		Handler for all background and sprite, in essense the entire JPanel

	- Bug.java

		Bug, contains sprites, movement algorithms (stalk the Octocat)

## 28/12/14

### Ethan Cheng

Created Segfault (projectiles) and necessary methods and handlers in other files
Debugged movement values, added 'facing direction' variable in Octocat.java, values defined in Segfault.java constructor (int dir)

(Later on during the day)
Added collision detection, unit collision + damaging, entity interactions

## 30/12/14

### Ethan Cheng

Finished (a baseline, granted) for the game over screen, started working on the victory screen


## 31/12/14

### Ethan Cheng

Added directional-based sprite framework (Still no art!).

***Notice: Planned: 
A) Add sprites and art 
B) Add music!
C) Add class Upgrade that extends Entity that when picked up boosts some random value of Octocat 'player' i.e. speed, lives, damage, etc.

***An hour later: Added melee attacks!

## 1/1/15

!!!!!! HAPPY NEW YEAR !!!!!!

### Jesse Elliott

Started work on pico font for splash screen.

### Ethan Cheng

Added Powerup.java and necessary changes in Controller.java and GfxRenderer.java to accomodate Powerup.java