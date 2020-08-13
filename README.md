# Flight Management

Flight Management is a little project that allows the toggling of flight, and other user's flight... it's also highly configurable!

*Currently limited to 1.15 - haven't tested on any other version*

# Commands

* **/flight** returns the help message 
* **/flight toggle (player)** toggles your flight, or others should you provide a username and have permission
* **/flight on** turns your flight on
* **/flight off** turns your flight off
* **/fight check (username)** returns player's flight stat

# Permissions

* **flight.use** allows access to player flight commands
* **flight.others** allows toggling of other's flight
* **flight.toggle-exempt** exempts user from having their flight toggled by another player

# To Do List
- [x] Re-organize permission handling (It's a bit of a mess)
- [x] Add other player option for **/flight on/off**
- [x] Add timed flight
- [x] Add multi-version support
