## Empty Your Fridge App
###Feli Nicolaes, 10542442, felinicolaes@hotmail.com
---------------------

> This app is more than your average recipe book: it will make it possible to enter ingredients you want to use this day and will search for added recipes with these ingredients.

###Features (this will be my MVP)
- Searching recipes by ingredient
- Adding and editing your own recipes
- Adding pictures and notes to your recipes
- Adding optional ingredients and substitutions

#####Optional features
- Share recipes
- Set standard substitutions (soy sauce-ketjap, penne-farfalle)
- Automatically make shopping list
- Add tags: vegetarian, time, occasion etc.

#####Gap
Food waste is currently a big problem in the world. The average Dutch person throws away 50 kilos of food (150 euros or 14 procent of our bought food/30% of all food) every year. Making it easy to cook according to what you already have in your fridge will make cooking easier, better for the environment and cheaper!

###Challenges
I currently do not know SQL very well, so will work with that. I also have never worked with the sharing of files or taking pictures within an app. Another challenge is to make the app so that it will calculate the set of possible recipes within a short time period.

I have added several optional features which I will implement if there is time left.

###Sketches
![classes](/doc/classes.png)

mainActivity: Main activity, from which the user can search by ingredient <br/>
addIngredientActivity: The user can add an ingredient to add to their search, here the amount can also be added and choose whether it is an optional ingredient<br/>
addRecipeActivity: Add a new recipe, including a name, ingredients, the recipe (in text or picture) and other notes or pictures<br/>
seeRecipeActivity: Here, the user can see an overview of the whole recipe<br/>
recipeListActivity: A list with all the possible recipes<br/>
typeRecipeActivity: A place to type the recipe or notes<br/>

![sketches](/doc/sketches.png)

###Data set
All the recipes have to be added yourself, because people generally already have a lot of recipes they enjoy cooking and making recipes they already know they'll like. The adding of new recipes will be made as easy to use as possible.

The ingredients have to be typed by hand, but instead of typing out the whole recipe, a picture of the recipe will suffice. This will make the process of adding new recipes a lot easier. Pictures of the finished dish can also be added.

If there is time left, I will make a way to share recipes with friends.

###Example

An example of a recipe can be:<br />
<b>Spanish Tortilla</b> <br />
- 3 egg  <br />
- 2 potatoe <br />
- 1 garlic clove <br />
- salt <br />
- oil <br />
- (onion) <br />
- broccoli OR green beans <br />

This means that to make this recipe, you will at least need 3 eggs, 2 potatoes, 1 garlic clove, salt and oil. The ingredient between brackets is optional: you can add this if you have it, but don't have to. <br/>
The last item includes the OR, this means you can add broccoli or green beans or salad, depending on what you have. Based on the ingredients you have, the app will also be able to make a grocery list, so it will be even easier to go out to the shop to buy what you don't have at home yet.

###Similar applications
Similar applications work with a database of recipes, so not your own recipes. You can also not add optional ingredients or substitutes in these apps or websites, which I think are an important part of searching possible recipes.

