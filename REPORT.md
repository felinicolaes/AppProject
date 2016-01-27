## Empty Your Fridge
###Feli Nicolaes, feli.nicolaes@student.uva.nl
---------------------

###App Description
The Empty Your Fridge app is a recipe book where the user can search by ingredient. The user can add and edit recipes to put in their recipe book. Recipes consist of a name, necessary ingredients, optional ingredients, the how to (either a text or a picture) and some optional extra notes or pictures.


###Code Design
The code is divided into several classes and xml-files. All classes, except for the database class, recipe object class and a custom listview class, are activities.
	
<b>Activities:</b>
>
- addIngredientActivity: is responsible for adding ingredients to a recipe or adding ingredients to filter on
- addRecipeActivity: is responsible for adding and editing recipes
- MainActivity: is the homescreen, where the user can filter on ingredients and gets the chance to add new recipes
- recipeListActivity: shows the user all the recipes in the database
- seeRecipeActivity: shows all information about a recipe
- typeRecipeActivity: shows all information about a recipe

<b>Other classes:</b>
>
- DatabaseHandler: contains all functions for creating a database and working with it
- IngredientList: is a custom ListView showing ingredients with their amounts and optionality
- Recipe: is a custom object containing all information about a recipe

###Challenges
While making this app, I ran into several challenges, the first of which had to do with the database. SQLlite does not support ArrayLists, while these are used in this app for storing pictures and strings. This problem was solved by using json. Json is a data format used to encode ArrayLists into Strings.

All encoding and decoding is done in the Recipe class. The Recipe object contains strings (encoded with json), but I have written functions like getIngredientsList() so I can use this object without having to encode and decode in classes other than the Recipe class. 
<br />  <br />

I also decided to add two extra ArrayLists, being necAmount and optAmount. These arraylists contain the amount of an ingredient. The index of this amount is the same as the index of the corresponding ingredient in necIngredient and necAmount.

This decision was made because seperating the ingredient and amount into two different list made for easier searching in ingredientlists.
<br />  <br />

When adding optional extra images, I decided to work with imageviews in a linearlayout in a horizontal scrollview. The imageviews are made dynamically for all the pictures that have to be shown. 

Showing an unlimited number of images, brought some problems, like out of memory errors. I also had problems with the dynamic imageviews where the borders would be way too large for no clear reason.
If I had to do this project again, I wouldn't have chosen for showing all pictures in a scrollable view. I would have probably chosen to show just one picture at a time, with a previous- and nextbutton. This would mean the imageviews would not have to made dynamically and because only one picture is shown, there wouldn't be any out of memory errors. Currently, all images in this scrollview are resized a lot before saving them, which might make them hard to read. I chose to resize the 'main' recipe image to a larger size then the images in the scrollview, since it is more important for this image to be readable.
<br />  <br />

I also changed the design of the mainActivity, after asking several people how to make my app design clearer. I changed the placement of several buttons as well as making the text on the buttons easier to understand.
<br/> <br/>

Another problem I encountered was an error if a certain order of activities was chosen. If you went from the seeRecipeActivity to the recipeListActivity, deleted this recipe, then clicked the android back-button (so you will go back to the seeRecipeActivity of the deleted recipe) and then click the edit-button. This means you are now attempting to edit a recipe that no longer exists. This would quit the app.

I overwrote the android back-button, so the user will always go back to the mainActivity when pressing this button. This does mean the user will no longer be able to press the back-button to go back to a previously shown recipe.
