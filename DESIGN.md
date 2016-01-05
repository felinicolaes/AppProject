## Empty Your Fridge App
###Feli Nicolaes, 10542442, felinicolaes@hotmail.com
---------------------

> This app is more than your average recipe book: it will make it possible to enter ingredients you want to use this day and will search for added recipes with these ingredients.

###Classes and public methods
![classes](/doc/classes.png)
#### Recipe
necIngredient: ArrayList &lt;String> containing all necessary ingredients for a certain recipe. Example: ["egg", "potatoe", "garlic", "salt", "oil", "green beans/broccoli"]<br/>
optIngredient: ArrayList&lt;String> containing all optional ingredients. Example: ["onion"]<br/>
name: String containing the name of the recipe. Example: "Spanish Tortilla"<br/>
pictArray: ArrayList&lt;Img> containing all pictures taken of the recipe<br/>
noteArray: ArrayList&lt;String> containing all notes from the recipe<br/>
recipe: String or Image containing the actual recipe or a picture thereof.

getIngredient() gets an Array&lt;String> containing the ingredients of the chosen recipe (both necessary and optional)<br/>
getNecIngredient() gets an ArrayList &lt;String> containing the necessary ingredients of the chosen recipe<br/>
getPosIngredient() gets an ArrayList &lt;String> containing the possible ingredients of the chosen recipe <br/>
getName() gets a String with the name of the chosen recipe <br/>
getPics() gets an ArrayList&lt;Img> with the pictures of the chosen recipe <br/>
getNotes() gets an ArrayList&lt;String> containing the notes of the chosen recipe <br/>
getRecipe() gets the Img or String containing the recipe of the chosen recipe <br/>

####search
posRecipe: ArrayList&lt;Recipe> of all the possible recipes

getAllRecipe() returns an ArrayList&lt;Recipe> containing all recipes<br/>
filterList(ArrayList&lt;Recipe> allRecipe, ArrayList&lt;String> necIngredient) returns an ArrayList&lt;Recipe> containing all recipes with these ingredients

###Sketches of UIs
**mainActivity**: Main activity, from which the user can search by ingredient. From here, the user goes to the recipeListActivity by clicking the search-button. The user will go to to the addIngredientActivity if they click the button for adding an ingredient.

**addIngredientActivity**: The user can add an ingredient to add to their search, here the amount can also be added and choose whether it is an optional ingredient. If they click add, they will go to the previous screen (mainActivity of addRecipeActivity).

**addRecipeActivity**: Add a new recipe, including a name, ingredients, the recipe (in text or picture) and other notes or pictures. From here, the the user can go to the addIngredientActivity if they click the add-ingredient-button or to the typeRecipeActivity if they want to type out the whole recipe. After they add the recipe, they will go to the seeRecipeActivity of the recipe they just added. If they long-click an ingredient, the ingredient will be deleted. If they click the or-button next to ingredient A, they can add ingredient B in the addIngredientActivity and the recipe will say you'll need ingredient A or B for making it.

**seeRecipeActivity**: Here, the user can see an overview of the whole recipe. They will go to the addRecipeActivity when they click edit. They can also go back to the mainActivity.

**recipeListActivity**: A list with all the possible recipes. From here, you can go to the seeRecipeActivity by clicking on a recipe on the list. You can also go back to the main screen.

**typeRecipeActivity**: A place to type the recipe or notes. This activity is only used from the addRecipeActivity and you will be brought back to this activity after clicking the add-button.

![sketches](/doc/sketches.png)

###APIs and frameworks
I will use an API for the optional feature that will able you to share your recipes. Because this is optional, I will not do much research yet.
