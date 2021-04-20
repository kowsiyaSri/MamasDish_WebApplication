USE mamasdish2;

INSERT INTO Recipe(icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id) VALUES
	(1,'3 Ingredient Teriyaki Chicken', 30, 4, 4, 20 , 10 , 'Easy Recipe Under 30 mins. Tastes Great!',4, 7, 227,null,null );

INSERT INTO Ingredient(ingredient_name, protein_id) VALUES
	('Chicken Thighs', 1),
    ('Soy Sauce', null),
    ('Brown Sugar', null);

INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) VALUES
	(910, 1, 19, 1),
    (1, 1, 5, 2),
    (110, 1, 19, 3);

INSERT INTO Instruction(step_number, description, recipe_id) VALUES
	(1, 'Sear the chicken evenly until cooked.', 1),
	(2, 'Add in soy sauce and brown sugar. Stir till you bring it to a boil.', 1),
	(3, 'Stir until sauce has reduced and evenly glazes the chicken.', 1),
	(4, 'Serve with rice and enjoy!', 1);
    
INSERT INTO Ingredient(ingredient_name, protein_id) VALUES
 ('small eggplants', null),
 ('garlic', null),
 ('tomatoes ', null),
 ('turmeric powder', null),
 ('water', null),
 ('vegetable oil', null),
 ('salt', null),
 ('yogurt', null),
 ('grated garlic', null),
 ('dried mint', null);

/* Add Recipe */ 
INSERT INTO Recipe(icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id) VALUES
	(5, 'Afghan Eggplant With Yogurt Sauce', 35, 4.15, 2, 15, 20, 
	'Eggplants cooked with tomatoes. Served with yogurt-garlic sauce and topped with lots of dried mint. Traditional Afghan recipe.',  
	1, 7, 3, 1, null);
    
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) VALUES
(6, 2, null, 4), -- small eggplants
(2, 2, 24, 5), -- garlic
(3, 2, null, 6), -- tomatoes
(0.25, 2, 1, 7), -- turmeric powder
(0.25, 2, 5, 8), -- water
(1, 2, 2, 9), -- vegetable oil
(1, 2, 2, 10), -- salt
(1, 2, 5, 11), -- yogurt
(1, 2, 1, 12), -- grated garlic
(0.25, 2 , 1, 13); -- dried mint

/* Add Instructions */
INSERT INTO Instruction(step_number, description, recipe_id) VALUES
(1, 'Take yogurt in a bowl and whisk it. Add grated garlic to it and mix. Keep it in the fridge while you cook the eggplants.', 2),
(2, 'Cut the eggplants into thin slices. You can remove the outer covering of the eggplant if you want.', 2),
(3, 'Heat oil in a pan on medium flame. Once hot, add the eggplants slices and fry till they are golden brown in color. 
	Do not over-fry it else the slices will become very soggy, you still want it little firm in the center.', 2),
(4, 'Place the eggplants slices on a kitchen towel to drain excess oil. Set aside.', 2),
(5, 'In another pan, heat 2-3 teaspoon of oil and add chopped garlic to it. Saute till it become light golden brown in color.', 2),
(6, 'Add the tomatoes, turmeric powder, cayenne pepper, salt and cook till tomatoes become soft and mushy. This will take 6-7 minutes.', 2),
(7, 'Next add the fried eggplants, around 1/4 cup of water and cover and cook at low heat for around 10 minutes.Switch off the flame.', 2),
(8, 'To serve, take a plate and put half of the yogurt sauce. 
	Then place the tomato sauce and eggplants over it and finally pour the remaining yogurt sauce on top of the eggplants and sprinkle lots of dried mint.', 2),
(9, 'Serve with kabuli pulao, pita bread or naan.', 2);