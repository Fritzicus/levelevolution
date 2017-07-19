# levelevolution
A game that adjusts the level generation to player liking

The game is intended to be an experiment in regards to level generation adjusting to the players skill/preference. This will be initially applied to a platformer game. 

# How will it work?

After each run through of the level that was generated. The Player can rate the level, with a 1 through 5 star system. After the program receives the rating, it will adjust the DNA of the level generation to be closer to the player's liking. A Caviat from this, will be an ideal completion/death ratio, setting the difficulty of the level. There will be some mathematical balance to adjust to the player's skill, and to what the player prefers. 

There are 3 components to this:
1. How much the player liked the level
2. What is the current Completion/Death ratio?
3. What the player performs poorly at (if anything) ?

If the level appears to be too easy, then the deadliness of traps/enemies will be increased. This will likely target things the player is moderately good at, but still has challenges with.
If the level appears to be too difficult, the deadliness of the traps/enemies will be decreased. This will likely target the components of the DNA tha player does very poorly against. 

## What needs to be done?

1. Basic Game Engine, and Physics Engine(?) from Core Java 
2. Genetic Algorithm to Adjust after the gamestate changes from playing -> complete/dead, update the data after the player rates the playthrough, and their completion/death ratio. 
3. Procedural Level Generation(Likely to be a constraint-solving problem) with a generation of array 2d array of short values that can be translated into level blocks
4. Saving/Storing game data to remember the player and the associated data

## Trap brainstorming

What traps can we make? Whirling Blades of Death, pit traps? feel free to add to this. Think of variables that we can change with these traps.

For example, if we take the Whirling Blades of Death, we can adjust the RPM, Size of the Blade, Radius the blade travels around?

# Possible Future Endeavors:

Adding a global database to cluster players into various groups such as: Weak to Pit Traps, Terrible Platform Player, Master Platformer, etc...
