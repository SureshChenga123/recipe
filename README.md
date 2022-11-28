## Technical stack requirements
* Maven
* Java 11
* Spring Boot 2.7.6
* Hibernate 5.3.7.Final

##Approach:

Created a Spring boot Application for exposing the API.
I have used H2 for data persistence.

##About : Recipe API

This API serves to expose below endpoints.

1) recipes - Fetch all the recipes
2) AddRecipe - Adding a new recipe
3) UpdateRecipe - Updating existing recipe
4) DeleteRecipe - Delete existing recipe


##Accessing RestAPI endpoints

1.Get /recipes - To search existing recipes

```PostMan 
curl --location --request GET 'http://localhost:8082/recipes?instructionContains=toga
&isVegetarian=true&presentIngredients=fruit&absentIngredients=chicken&noOfServings=4'
```

* If there is no request parameters, we return all the saved recipes
* Optional parameters
  - instructionContains (only recipes which contains the given word)
  - isVegetarian (filter by vegetarian)
  - presentIngredients (only recipes containing the ingredients)
  - absentIngredients (only recipes not containing the ingredients)
  - noOfServings (only recipes with equal number of servings)

```json5
{
    "recipesResponse": [
        {
            "id": 1,
            "recipeName": "Biryani",
            "isVegetarian": false,
            "noOfServings": 4,
            "ingredients": [
                "chicken", "Rice"
            ],
            "instruction": "Put chicken and rice together"
        },
        {
            "id": 2,
            "recipeName": "Muffin",
            "isVegetarian": true,
            "noOfServings": 2,
            "ingredients": [
                "chocolate",
                "Almond flour",
                "Sugar"
            ],
            "instruction": "Put chocolate and Almond flour in the oven and mix with sugar"
        }
    ]
}
```

2.POST /recipe - Create a new recipe,

 ```PostMan
 curl --header "Content-Type: application/json" --request POST http://localhost:8089/recipe
 ```

Example body:
```json5
{
    "recipeName" : "Muffin",
    "isVegetarian": false,
    "noOfServings": 4,
    "ingredients": [ 
    "chocolate", "Almond flour"
    
    ],
    "instruction": "Put chocolate and Almond flour together"
}
```
Example response:
```json5
{
  "id" : 1,
  "recipeName" : "Muffin",
  "isVegetarian": false,
  "noOfServings": 4,
  "ingredients": [
    "chocolate", "Almond flour"

  ],
  "instruction": "Put chocolate and Almond flour together"
}
```

3.PUT /recipe/{id} - To update an existing recipe,

id - Unique recipe Id (Created above),
```PostMan 
curl --header "Content-Type: application/json" --request PUT http://localhost:8089/recipe/{Id}
```

Example body:
```json5
{
    "ingredients": [ 
    "chocolate", "Almond flour", "Sugar"
    
    ],
    "instruction": "Put chocolate and Almond flour together. Then add sugar"
}
```
* Only the fields provided will be updates and for the rest the old value is retained
* Api returns an error if the recipe Id is not found

Example response:
```json5
{
    "id": 2,
    "recipeName": "Muffin",
    "isVegetarian": false,
    "noOfServings": 4,
    "ingredients": [
      "chocolate", "Almond flour", "Sugar"
    ],
    "instruction": "Put chocolate and Almond flour together. Then add sugar"
}
```

4.Delete /recipe/{id} - To delete an existing recipe,

id - Unique recipe Id (Created above),
```PostMan 
curl --header "Content-Type: application/json" --request Delete http://localhost:8082/recipes/{Id}
```

* The recipe is deleted if found and if not returns an error


# How to run
Download the source code in IDE  and execute:
```bash
mvn clean install
mvn spring-boot:run
```
