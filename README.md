# Oneiron
Oneiron is a minecraft RPG plugin, including many features found in real RPG games! The plugin requires a working MySQL connection, 
which can be configured in the Config file.

There are many different features:

## Custom Mobs:

Within this plugin, it is possible to create your own mobs. You can change their AI, their health, damage and level. Each mob
has its own drops, consisting of custom items and a configurable amount of XP. 
To spawn mobs either use the command </cspawn MOB> or create spawners with </cspawner MOB>, which will be saved in your MySQL Database.

## Custom player classes:

On their first join, each player chooses a custom class. At the moment only the mage class is implemented, more will be added in the future.
These classes each have their own attacks, that can be activated by using certain click combos, while holding a weapon suitable for 
that class. All of these attacks cost a certain amount of mana, except for the basic attack, that can be used at any time. The players
mana level is indicated by their hunger bar.

## Example for the mage class:

Right click:        This is the mage's basic attack, a simple flame. It doesn't do a lot of damage and has a rather small range, but it doesn't
                    cost any mana!
Rage (R-R-R):       Aim at a point to throw all nearby enemies into the air, spawning a damaging explosion.
Lightning (R-R-L):  Spawn a destructive lightning bolt.
Heal (R-L-R):       Heal yourself.
Slam (R-L-L):       Launch yourself forward and deal damage to all opponents in your way.

Players can level up by killing enough mobs, indicated by their XP bar. Up until now, this doesn't influence their stats in any way.

## Custom Items:

A real RPG contains many different items, which is why this plugin also adds a whole lot of new stuff!

## Custom Currency:

All mobs can also drop certain amounts of currency, either Screws or Scrapmetal. One piece of Scrapmetal is worth 64 Screws and they
be exchanged at the Exchange Merchant, a custom entity.

## Custom Weapons:

New weapons can be obtained through the killing of enteties. They will deal a certain amount of damage and have different rarities,
depending on how good they are.

## Custom Armor:

This feature is work in progress!
There is a lot of new armor planned, which is able to grant the player boni to their stats.

## Other Information:

This plugin prevents normal regeneration and hunger loss to use its own health and mana system. It also prevents fall damage, pvp,
the spawning of spider jockes, the splitting of slimes and the normal death of players.

