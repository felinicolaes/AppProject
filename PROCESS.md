# Process book
###Feli Nicolaes, felinicolaes@hotmail.com

## Day 1
- Decided on app
- Chose and sketched the different activities

## Day 2
- Set up repository, had problems getting git and android studio to work together
- Started design document
- Chose the different classes

## Day 3
- research SQL lite, have to watch Jullians tutorial
- improve design sketches
- started mainActivity, addRecipeActivity, recipeListActivity and connections between those

##Day 4
- made all activities+design+transitions/connections between them
- To Do: make keyboard hidden at start in mainActivity and AddRecipeActivity
- To Do: fix 'sure you want to delete' alert in recipeListActivity

##Day 5
- keyboard no longer automatically pops up
- alert added in recipeListActivity
- read more SQLite tutorials
- made working database (not fully implemented, but a start)

##Day 6
- decided to put arraylists in gsons
- decided to put notes in String, not arraylistkj
- further implement database
    - can now add and edit recipes (recipe, name, notes) in the app
    - can show recipe, name and notes of existing recipe
    - can show list of all recipes
    - can delete recipes

##Day 7
- Implement gson/ingredients
    - change recipe-object
    - change database
    - can now add/edit/see ingredients
- To Do: make so that ingredients mainscreen are saved

##Day 8
- Implemented ingredient amounts
	- changed recipe-objects
	- changed database
	- can now add and see amounts of ingredients
- Can no longer add recipes with same name, but can edit name so it will be the same
- Can filter on ingredients

##Day 9
- Added 'back'-buttons
- Made design better (less overlap in text/buttons)
- Researched in-app-picture-taking

##Day 10
- Further researched in-app-picture-taking
- Installed emulator (had to change VT-x in BIOS)

##Day 11
- Implemented in-app-picture taking
- To Do: make so that picture can be made bigger (imageButton to new activity where you can zoom?)
- To Do: Do I want 'extra' picture(s)?

##Day 12
- Fixed bugs where picture from gallery was not shown

##Day 13
- Made custom listView
- Picture can now be enlarged
- To Do: can no longer delete from listview?

##Day 14
- Custom listView now bugless
- Added are-you-sure-dialog when changing recipe to picture etc
- Previous typed recipe will now be loaded when editing, so that user does not have to type whole recipe twice, but can just edit old one
- Made nicer design for seeRecipeActivity: 2 scollviews instead of 3
- Made nicer design for MainActtivity: clearer placement of buttons
- Made custom button xml rounded_button.xml
- Added custom theme to whole app
- TO DO:
	- picture not found error message/catch
	- horizontalview fix borders
	- fixed out of memory error?
	- onclicklistener on horizontalview images
	- delete extra pic

##Day 15
- Install Mobizen for mirroring screen
- Fixed onclicklistener in horizontal scrollview