insert into User (username, encryptedpassword, enabled)
values ('razan@hotmail.com', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into User (username, encryptedpassword, enabled)
values ('Simon', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into Role (rolename)
values ('ROLE_CHEF');
 
insert into Role (rolename)
values ('ROLE_USER');

insert into user_roles (users_id, roles_id)
values (1, 1);

insert into user_roles (users_id, roles_id)
values (1, 2);

INSERT INTO end_user (first_name, last_name, email, password) VALUES
('Razan','alsaddi','razan@hotmail.com','1234'),
('Portia','Ocran','portia@hotmail.com','1234'),
('Billal','Rashid','billal@hotmail.com','1234'),
('Kowsiya','Srikantharajah','kowsi@hotmail.com','1234');

INSERT INTO chef (Description, enduser_id) VALUES
('Home Cook',1),
('Professional Chef',2),
('Studying at Chef School in canada',3),
('Home Cook',4);

INSERT INTO cuisine (Cuisine_Name) VALUES
('Chinese'),
('Japanese'),
('Spanish'),
('Mediterranean'),
('Cajun'),
('Mexican'),
('Caribbean'),
('Ainu'),
('Middle Eastern'),
('Assyrian'),
('Balochi'),
('Bashkir'),
('Belarusian'),
('Bangladeshi'),
('Bengali'),
('Berber'),
('Brazilian'),
('British'),
('Buddhist'),
('Bulgarian'),
('Cantonese'),
('Chechen'),
('Chinese Islamic'),
('Circassian'),
('Crimean Tatar'),
('Cypriot'),
('Czech'),
('Danish'),
('Egyptian'),
('English'),
('Ethiopian'),
('Eritrean'),
('Estonian'),
('French'),
('Filipino'),
('Georgian'),
('German'),
('Goan'),
('Goan Catholic'), 
('Greek'),
('Gujarati'),
('Hyderabad'),
('Indian cuisine'),
('Indian Chinese'),
('Indian Singaporean cuisine'),
('Indonesian'),
('Inuit'),
('Irish'),
('Italian-American'),
('Italian cuisine'),
('Jamaican'),
('Jewish'),
('Karnataka'),
('Kazakh');


INSERT INTO Measurement(Measurement_Type, Plural) VALUES
	('teaspoon', 'teaspoons'),
    ('tablespoon', 'tablespoons'),
    ('fluid ounze', 'fluid ounzes'),
    ('gill', 'gills'),
    ('cup', 'cups'),
    ('pint', 'pints'),
    ('quart', 'quarts'),
    ('gallon', 'gallons'),
    ('milliliter', 'milliliters'),
    ('liter', 'liters'),
	('deciliter', 'deciliters'),
    ('millimeter', 'millimeters'),
    ('centimeter', 'centimeters'),
    ('meter', 'meters'),
    ('inch', 'inches'),
	('pound', 'pounds'),
    ('ounce', 'ounces'),
    ('milligram', 'miligrams'),
    ('gram', 'grams'),
    ('kilogram', 'kilograms'),
    ('pinch', 'pinches'),
    ('to taste', ''),
    ('piece', 'pieces'),
    ('colve', 'cloves'),
    ('as needed', '');


INSERT INTO protein(Protein_Type) VALUES
	('Chicken'),
    ('Turkey'),
    ('Pork'),
    ('Lamb'),
    ('Mutton'),
    ('Beef'),
    ('Venison'),
    ('Duck'),
    ('Boar'),
    ('Goat'),
    ('Bison'),
    ('Goose'),
    ('Rabbit'),
    ('Pheasant'),
    ('Fish'),
    ('Shellfish'),
    ('Molluscs'),
    ('Egg');

INSERT INTO diet (diet_type) VALUES
('VEGAN'),
('VEGETARIAN'),
('PLANT BASED'),
('LOW FAT'),
('LOW CARB'),
('KETO'),
('SUGAR FREE'),
('PESCATARIAN'),
('PALEO');

INSERT INTO meal_type (meal_name)VALUES 
('APPETIZER'),
('BREAKFAST'),
('BRUNCH'),
('SNACK'),
('LUNCH'),
('DINNER'),
('DESSERT'),
('DRINK');

INSERT INTO country (country_code, Latitude, Longitude, Name) VALUES
	    ('AD', 42.546245, 1.601554, 'Andorra'),
            ('AE', 23.424076, 53.847818, 'United Arab Emirates'),
            ('AF', 33.93911, 67.709953, 'Afghanistan'),
            ('AG', 17.060816, -61.796428, 'Antigua and Barbuda'),
            ('AI', 18.220554, -63.068615, 'Anguilla'),
            ('AL', 41.153332, 20.168331, 'Albania'),
            ('AM', 40.069099, 45.038189, 'Armenia'),
            ('AN', 12.226079, -69.060087, 'Netherlands Antilles'),
            ('AO', -11.202692, 17.873887, 'Angola'),
            ('AQ', -75.250973, -0.071389, 'Antarctica'),
            ('AR', -38.416097, -63.616672, 'Argentina'),
            ('AS', -14.270972, -170.132217, 'American Samoa'),
            ('AT', 47.516231, 14.550072, 'Austria'),
            ('AU', -25.274398, 133.775136, 'Australia'),
            ('AW', 12.52111, -69.968338, 'Aruba'),
            ('AZ', 40.143105, 47.576927, 'Azerbaijan'),
            ('BA', 43.915886, 17.679076, 'Bosnia and Herzegovina'),
            ('BB', 13.193887, -59.543198, 'Barbados'),
            ('BD', 23.684994, 90.356331, 'Bangladesh'),
            ('BE', 50.503887, 4.469936, 'Belgium'),
            ('BF', 12.238333, -1.561593, 'Burkina Faso'),
            ('BG', 42.733883, 25.48583, 'Bulgaria'),
            ('BH', 25.930414, 50.637772, 'Bahrain'),
            ('BI', -3.373056, 29.918886, 'Burundi'),
            ('BJ', 9.30769, 2.315834, 'Benin'),
            ('BM', 32.321384, -64.75737, 'Bermuda'),
            ('BN', 4.535277, 114.727669, 'Brunei'),
            ('BO', -16.290154, -63.588653, 'Bolivia'),
            ('BR', -14.235004, -51.92528, 'Brazil'),
            ('BS', 25.03428, -77.39628, 'Bahamas'),
            ('BT', 27.514162, 90.433601, 'Bhutan'),
            ('BV', -54.423199, 3.413194, 'Bouvet Island'),
            ('BW', -22.328474, 24.684866, 'Botswana'),
            ('BY', 53.709807, 27.953389, 'Belarus'),
            ('BZ', 17.189877, -88.49765, 'Belize'),
            ('CA', 56.130366, -106.346771, 'Canada'),
            ('CC', -12.164165, 96.870956, 'Cocos [Keeling] Islands'),
            ('CD', -4.038333, 21.758664, 'Congo [DRC]'),
            ('CF', 6.611111, 20.939444, 'Central African Republic'),
            ('CG', -0.228021, 15.827659, 'Congo Republic'),
            ('CH', 46.818188, 8.227512, 'Switzerland'),
            ('CI', 7.539989, -5.54708, 'Côte d''Ivoire'),
            ('CK', -21.236736, -159.777671, 'Cook Islands'),
            ('CL', -35.675147, -71.542969, 'Chile'),
            ('CM', 7.369722, 12.354722, 'Cameroon'),
            ('CN', 35.86166, 104.195397, 'China'),
            ('CO', 4.570868, -74.297333, 'Colombia'),
            ('CR', 9.748917, -83.753428, 'Costa Rica'),
            ('CU', 21.521757, -77.781167, 'Cuba'),
            ('CV', 16.002082, -24.013197, 'Cape Verde'),
            ('CX', -10.447525, 105.690449, 'Christmas Island'),
            ('CY', 35.126413, 33.429859, 'Cyprus'),
            ('CZ', 49.817492, 15.472962, 'Czech Republic'),
            ('DE', 51.165691, 10.451526, 'Germany'),
            ('DJ', 11.825138, 42.590275, 'Djibouti'),
            ('DK', 56.26392, 9.501785, 'Denmark'),
            ('DM', 15.414999, -61.370976, 'Dominica'),
            ('DO', 18.735693, -70.162651, 'Dominican Republic'),
            ('DZ', 28.033886, 1.659626, 'Algeria'),
            ('EC', -1.831239, -78.183406, 'Ecuador'),
            ('EE', 58.595272, 25.013607, 'Estonia'),
            ('EG', 26.820553, 30.802498, 'Egypt'),
            ('EH', 24.215527, -12.885834, 'Western Sahara'),
            ('ER', 15.179384, 39.782334, 'Eritrea'),
            ('ES', 40.463667, -3.74922, 'Spain'),
            ('ET', 9.145, 40.489673, 'Ethiopia'),
            ('FI', 61.92411, 25.748151, 'Finland'),
            ('FJ', -16.578193, 179.414413, 'Fiji'),
            ('FK', -51.796253, -59.523613, 'Falkland Islands [Islas Malvinas]'),
            ('FM', 7.425554, 150.550812, 'Micronesia'),
            ('FO', 61.892635, -6.911806, 'Faroe Islands'),
            ('FR', 46.227638, 2.213749, 'France'),
            ('GA', -0.803689, 11.609444, 'Gabon'),
            ('GB', 55.378051, -3.435973, 'United Kingdom'),
            ('GD', 12.262776, -61.604171, 'Grenada'),
            ('GE', 42.315407, 43.356892, 'Georgia'),
            ('GF', 3.933889, -53.125782, 'French Guiana'),
            ('GG', 49.465691, -2.585278, 'Guernsey'),
            ('GH', 7.946527, -1.023194, 'Ghana'),
            ('GI', 36.137741, -5.345374, 'Gibraltar'),
            ('GL', 71.706936, -42.604303, 'Greenland'),
            ('GM', 13.443182, -15.310139, 'Gambia'),
            ('GN', 9.945587, -9.696645, 'Guinea'),
            ('GP', 16.995971, -62.067641, 'Guadeloupe'),
            ('GQ', 1.650801, 10.267895, 'Equatorial Guinea'),
            ('GR', 39.074208, 21.824312, 'Greece'),
            ('GS', -54.429579, -36.587909, 'South Georgia and the South Sandwich Islands'),
            ('GT', 15.783471, -90.230759, 'Guatemala'),
            ('GU', 13.444304, 144.793731, 'Guam'),
            ('GW', 11.803749, -15.180413, 'Guinea-Bissau'),
            ('GY', 4.860416, -58.93018, 'Guyana'),
            ('GZ', 31.354676, 34.308825, 'Gaza Strip'),
            ('HK', 22.396428, 114.109497, 'Hong Kong'),
            ('HM', -53.08181, 73.504158, 'Heard Island and McDonald Islands'),
            ('HN', 15.199999, -86.241905, 'Honduras'),
            ('HR', 45.1, 15.2, 'Croatia'),
            ('HT', 18.971187, -72.285215, 'Haiti'),
            ('HU', 47.162494, 19.503304, 'Hungary'),
            ('ID', -0.789275, 113.921327, 'Indonesia'),
            ('IE', 53.41291, -8.24389, 'Ireland'),
            ('IL', 31.046051, 34.851612, 'Israel'),
            ('IM', 54.236107, -4.548056, 'Isle of Man'),
            ('IN', 20.593684, 78.96288, 'India'),
            ('IO', -6.343194, 71.876519, 'British Indian Ocean Territory'),
            ('IQ', 33.223191, 43.679291, 'Iraq'),
            ('IR', 32.427908, 53.688046, 'Iran'),
            ('IS', 64.963051, -19.020835, 'Iceland'),
            ('IT', 41.87194, 12.56738, 'Italy'),
            ('JE', 49.214439, -2.13125, 'Jersey'),
            ('JM', 18.109581, -77.297508, 'Jamaica'),
            ('JO', 30.585164, 36.238414, 'Jordan'),
            ('JP', 36.204824, 138.252924, 'Japan'),
            ('KE', -0.023559, 37.906193, 'Kenya'),
            ('KG', 41.20438, 74.766098, 'Kyrgyzstan'),
            ('KH', 12.565679, 104.990963, 'Cambodia'),
            ('KI', -3.370417, -168.734039, 'Kiribati'),
            ('KM', -11.875001, 43.872219, 'Comoros'),
            ('KN', 17.357822, -62.782998, 'Saint Kitts and Nevis'),
            ('KP', 40.339852, 127.510093, 'North Korea'),
            ('KR', 35.907757, 127.766922, 'South Korea'),
            ('KW', 29.31166, 47.481766, 'Kuwait'),
            ('KY', 19.513469, -80.566956, 'Cayman Islands'),
            ('KZ', 48.019573, 66.923684, 'Kazakhstan'),
            ('LA', 19.85627, 102.495496, 'Laos'),
            ('LB', 33.854721, 35.862285, 'Lebanon'),
            ('LC', 13.909444, -60.978893, 'Saint Lucia'),
            ('LI', 47.166, 9.555373, 'Liechtenstein'),
            ('LK', 7.873054, 80.771797, 'Sri Lanka'),
            ('LR', 6.428055, -9.429499, 'Liberia'),
            ('LS', -29.609988, 28.233608, 'Lesotho'),
            ('LT', 55.169438, 23.881275, 'Lithuania'),
            ('LU', 49.815273, 6.129583, 'Luxembourg'),
            ('LV', 56.879635, 24.603189, 'Latvia'),
            ('LY', 26.3351, 17.228331, 'Libya'),
            ('MA', 31.791702, -7.09262, 'Morocco'),
            ('MC', 43.750298, 7.412841, 'Monaco'),
            ('MD', 47.411631, 28.369885, 'Moldova'),
            ('ME', 42.708678, 19.37439, 'Montenegro'),
            ('MG', -18.766947, 46.869107, 'Madagascar'),
            ('MH', 7.131474, 171.184478, 'Marshall Islands'),
            ('MK', 41.608635, 21.745275, 'Macedonia [FYROM]'),
            ('ML', 17.570692, -3.996166, 'Mali'),
            ('MM', 21.913965, 95.956223, 'Myanmar [Burma]'),
            ('MN', 46.862496, 103.846656, 'Mongolia'),
            ('MO', 22.198745, 113.543873, 'Macau'),
            ('MP', 17.33083, 145.38469, 'Northern Mariana Islands'),
            ('MQ', 14.641528, -61.024174, 'Martinique'),
            ('MR', 21.00789, -10.940835, 'Mauritania'),
            ('MS', 16.742498, -62.187366, 'Montserrat'),
            ('MT', 35.937496, 14.375416, 'Malta'),
            ('MU', -20.348404, 57.552152, 'Mauritius'),
            ('MV', 3.202778, 73.22068, 'Maldives'),
            ('MW', -13.254308, 34.301525, 'Malawi'),
            ('MX', 23.634501, -102.552784, 'Mexico'),
            ('MY', 4.210484, 101.975766, 'Malaysia'),
            ('MZ', -18.665695, 35.529562, 'Mozambique'),
            ('NA', -22.95764, 18.49041, 'Namibia'),
            ('NC', -20.904305, 165.618042, 'New Caledonia'),
            ('NE', 17.607789, 8.081666, 'Niger'),
            ('NF', -29.040835, 167.954712, 'Norfolk Island'),
            ('NG', 9.081999, 8.675277, 'Nigeria'),
            ('NI', 12.865416, -85.207229, 'Nicaragua'),
            ('NL', 52.132633, 5.291266, 'Netherlands'),
            ('NO', 60.472024, 8.468946, 'Norway'),
            ('NP', 28.394857, 84.124008, 'Nepal'),
            ('NR', -0.522778, 166.931503, 'Nauru'),
            ('NU', -19.054445, -169.867233, 'Niue'),
            ('NZ', -40.900557, 174.885971, 'New Zealand'),
            ('OM', 21.512583, 55.923255, 'Oman'),
            ('PA', 8.537981, -80.782127, 'Panama'),
            ('PE', -9.189967, -75.015152, 'Peru'),
            ('PF', -17.679742, -149.406843, 'French Polynesia'),
            ('PG', -6.314993, 143.95555, 'Papua New Guinea'),
            ('PH', 12.879721, 121.774017, 'Philippines'),
            ('PK', 30.375321, 69.345116, 'Pakistan'),
            ('PL', 51.919438, 19.145136, 'Poland'),
            ('PM', 46.941936, -56.27111, 'Saint Pierre and Miquelon'),
            ('PN', -24.703615, -127.439308, 'Pitcairn Islands'),
            ('PR', 18.220833, -66.590149, 'Puerto Rico'),
            ('PS', 31.952162, 35.233154, 'Palestinian Territories'),
            ('PT', 39.399872, -8.224454, 'Portugal'),
            ('PW', 7.51498, 134.58252, 'Palau'),
            ('PY', -23.442503, -58.443832, 'Paraguay'),
            ('QA', 25.354826, 51.183884, 'Qatar'),
            ('RE', -21.115141, 55.536384, 'Réunion'),
            ('RO', 45.943161, 24.96676, 'Romania'),
            ('RS', 44.016521, 21.005859, 'Serbia'),
            ('RU', 61.52401, 105.318756, 'Russia'),
            ('RW', -1.940278, 29.873888, 'Rwanda'),
            ('SA', 23.885942, 45.079162, 'Saudi Arabia'),
            ('SB', -9.64571, 160.156194, 'Solomon Islands'),
            ('SC', -4.679574, 55.491977, 'Seychelles'),
            ('SD', 12.862807, 30.217636, 'Sudan'),
            ('SE', 60.128161, 18.643501, 'Sweden'),
            ('SG', 1.352083, 103.819836, 'Singapore'),
            ('SH', -24.143474, -10.030696, 'Saint Helena'),
            ('SI', 46.151241, 14.995463, 'Slovenia'),
            ('SJ', 77.553604, 23.670272, 'Svalbard and Jan Mayen'),
            ('SK', 48.669026, 19.699024, 'Slovakia'),
            ('SL', 8.460555, -11.779889, 'Sierra Leone'),
            ('SM', 43.94236, 12.457777, 'San Marino'),
            ('SN', 14.497401, -14.452362, 'Senegal'),
            ('SO', 5.152149, 46.199616, 'Somalia'),
            ('SR', 3.919305, -56.027783, 'Suriname'),
            ('ST', 0.18636, 6.613081, 'São Tomé and Príncipe'),
            ('SV', 13.794185, -88.89653, 'El Salvador'),
            ('SY', 34.802075, 38.996815, 'Syria'),
            ('SZ', -26.522503, 31.465866, 'Swaziland'),
            ('TC', 21.694025, -71.797928, 'Turks and Caicos Islands'),
            ('TD', 15.454166, 18.732207, 'Chad'),
            ('TF', -49.280366, 69.348557, 'French Southern Territories'),
            ('TG', 8.619543, 0.824782, 'Togo'),
            ('TH', 15.870032, 100.992541, 'Thailand'),
            ('TJ', 38.861034, 71.276093, 'Tajikistan'),
            ('TK', -8.967363, -171.855881, 'Tokelau'),
            ('TL', -8.874217, 125.727539, 'Timor-Leste'),
            ('TM', 38.969719, 59.556278, 'Turkmenistan'),
            ('TN', 33.886917, 9.537499, 'Tunisia'),
            ('TO', -21.178986, -175.198242, 'Tonga'),
            ('TR', 38.963745, 35.243322, 'Turkey'),
            ('TT', 10.691803, -61.222503, 'Trinidad and Tobago'),
            ('TV', -7.109535, 177.64933, 'Tuvalu'),
            ('TW', 23.69781, 120.960515, 'Taiwan'),
            ('TZ', -6.369028, 34.888822, 'Tanzania'),
            ('UA', 48.379433, 31.16558, 'Ukraine'),
            ('UG', 1.373333, 32.290275, 'Uganda'),
            ('US', 37.09024, -95.712891, 'United States'),
            ('UY', -32.522779, -55.765835, 'Uruguay'),
            ('UZ', 41.377491, 64.585262, 'Uzbekistan'),
            ('VA', 41.902916, 12.453389, 'Vatican City'),
            ('VC', 12.984305, -61.287228, 'Saint Vincent and the Grenadines'),
            ('VE', 6.42375, -66.58973, 'Venezuela'),
            ('VG', 18.420695, -64.639968, 'British Virgin Islands'),
            ('VI', 18.335765, -64.896335, 'U.S. Virgin Islands'),
            ('VN', 14.058324, 108.277199, 'Vietnam'),
            ('VU', -15.376706, 166.959158, 'Vanuatu'),
            ('WF', -13.768752, -177.156097, 'Wallis and Futuna'),
            ('WS', -13.759029, -172.104629, 'Samoa'),
            ('XK', 42.602636, 20.902977, 'Kosovo'),
            ('YE', 15.552727, 48.516388, 'Yemen'),
            ('YT', -12.8275, 45.166244, 'Mayotte'),
            ('ZA', -30.559482, 22.937506, 'South Africa'),
            ('ZM', -13.133897, 27.849332, 'Zambia'),
            ('ZW', -19.015438, 29.154857, 'Zimbabwe');
            
/* Recipe 1 */ 
INSERT INTO Recipe(recipe_img , icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES
		('teriyaki_chicken',1,'3 Ingredient Teriyaki Chicken', 30, 4, 4, 20 , 10 , 'Easy Recipe Under 30 mins. Tastes Great!',4, 5, 227,null, 5 );

INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('Chicken Thighs', 1),
    	('Soy Sauce', null),
    	('Brown Sugar', null);

INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(910, 1, 19, 1),
    	(1, 1, 5, 2),
    	(110, 1, 19, 3);

INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Sear the chicken evenly until cooked.'),
		(2, 'Add in soy sauce and brown sugar. Stir till you bring it to a boil.'),
		(3, 'Stir until sauce has reduced and evenly glazes the chicken.'),
		(4, 'Serve with rice and enjoy!');
    
/* Recipe 2 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id) 
	VALUES 
		('teriyaki_beef',1,'3 Ingredient Teriyaki Beef', 30, 3.5, 4, 20.0, 10.0, 'Easy Beef Recipe Under 30 mins. Tastes Great!',4, 5, 227,null, 5);
 
/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
	 	('Beef Chunks', 6);

/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES 
		(910, 2, 19, 4),
		(1, 2, 5, 2),
		(110, 2, 19, 3);

/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
	(1, 'Sear the beef evenly until cooked.'),
	(2, 'Add in soy sauce and brown sugar. Stir till you bring it to a boil.'),
	(3, 'Stir until sauce has reduced and evenly glazes the beef.'),
	(4, 'Serve with rice and enjoy!');

/* Recipe 3 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('teriyaki_pork', 1,'3 Ingredient Teriyaki Pork',30, 4, 4, 20.0, 10.0, 'Easy Pork Recipe Teriyaki!',4, 5, 227,null, 5);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('Pork', 3);
		
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(910, 3, 19, 5),
		(1, 3, 5, 2),
		(110, 3, 19, 3);
	
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Sear the pork evenly until cooked.'),
		(2, 'Add in soy sauce and brown sugar. Stir till you bring it to a boil.'),
		(3, 'Stir until sauce has reduced and evenly glazes the chicken.'),
		(4, 'Serve with rice and enjoy!');
		
/* Recipe 4 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('teriyaki_salmon',1,'3 Ingredient Teriyaki Salmon', 30, 3.5, 4, 20.0, 10.0, 'Simple teriyaki salmon!',4, 5, 227,null, 5);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('Salmon', 15);
		
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(910, 4, 19, 6),
		(1, 4, 5, 2),
		(110, 4, 19, 3);
	
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Sear the salmon evenly until cooked.'),
		(2, 'Add in soy sauce and brown sugar. Stir till you bring it to a boil.'),
		(3, 'Stir until sauce has reduced and evenly glazes the chicken.'),
		(4, 'Serve with rice and enjoy!');

/* Recipe 5 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('teriyaki_shrimp',1,'3 Ingredient Teriyaki Shrimp', 30, 4.5, 4, 20.0, 10.0, 'Simple teriyaki Shrimp recipe!',4, 5, 227,null, 5);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('Shrimp', 15);
		
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(910, 5, 19, 7),
		(1, 5, 5, 2),
		(110, 5, 19, 3);
	
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Sear the shrimp evenly until cooked.'),
		(2, 'Add in soy sauce and brown sugar. Stir till you bring it to a boil.'),
		(3, 'Stir until sauce has reduced and evenly glazes the chicken.'),
		(4, 'Serve with rice and enjoy!');
		
/* Recipe 6 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('green_beans',1,'3 Ingredient Green Beans',12.0,5,6,7.0,5.0,'Easy and healthy meal',1, 4,40,6,2);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('green beans', null),
	    ('water',null),
	    ('salt', null),
	    ('butter', null),
	    ('savory Spice Blend', null); 
		
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(680, 6, 19, 8), -- green beans
		(0.25, 6, 12, 9), -- water
		(0.75, 6, 1, 10), -- salt
		(4, 6, 1, 11), --  butter
	    (1.25, 6, 1, 12); -- savory Spice Blend  
	
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'In a large pan over high heat, combine the green beans, water, and ¼ teaspoon salt. Cover and steam for 5–7 minutes, until the green beans 
			are just barely tender.'),
	    (2, 'Remove the lid and add the remaining ½ teaspoon salt, the butter, and Savory spice blend. Cook, tossing every 30 seconds, until the green 
			beans are tender and charred in a few spots, 1–2 minutes.'),
	    (3, 'Serve immediately.'),
	    (4, 'Enjoy!');
	    
/* Recipe 7 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('cola_wings', 1,'10 Minute Cola Wing',10.0,4,1,7.0,2.0,'Chinese meal',2, 6,46,1, null);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('olive oil', null),
	    ('chicken wings', 1),
	    ('cola soda', null),
	    ('scallion', null);
		
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(1, 7, 1, 13), -- olive oil
		(6, 7, 23, 14), -- chicken wings
		(2, 7, 1, 2), -- soy sauce
		(3.25, 7, 12, 15), -- cola soda
	    (1, 7, 23, 16); -- scallion  
	
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Heat the olive oil in a medium nonstick skillet over medium heat. Add the chicken wings and sear for 1–2 minute per side, until just crispy.'),
	    (2, 'Add the soy sauce and cola and increase the heat to high. Bring to boil and cook, stirring constantly, until the sauce is reduced and syrupy and 
			the wings are warmed through and well coated, 5–7 minutes.'),
	    (3, 'Garnish with scallions and serve immediatel'),
	    (4, 'Enjoy!');
	    
/* Recipe 8 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('afghan_eggplant', 5, 'Afghan Eggplant With Yogurt Sauce', 35, 4.15, 2, 15, 20, 'Eggplants cooked with tomatoes.', 1, 6, 3, 1, null);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('small eggplants', null),
		('garlic', null),
		('tomatoes ', null),
		('turmeric powder', null),
		('vegetable oil', null),
		('yogurt', null),
		('grated garlic', null),
		('dried mint', null);
		
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(6, 8, null, 17), -- small eggplants
		(2, 8, 24, 18), -- garlic
		(3, 8, null, 19), -- tomatoes
		(0.25, 8, 1, 20), -- turmeric powder
		(0.25, 8, 5, 9), -- water
		(1, 8, 2, 21), -- vegetable oil
		(1, 8, 2, 10), -- salt
		(1, 8, 5, 22), -- yogurt
		(1, 8, 1, 23), -- grated garlic
		(0.25,8 , 1, 24); -- dried mint  
	
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Take yogurt in a bowl and whisk it. Add grated garlic to it and mix. Keep it in the fridge while you cook the eggplants.'),
		(2, 'Cut the eggplants into thin slices. You can remove the outer covering of the eggplant if you want.'),
		(3, 'Heat oil in a pan on medium flame. Once hot, add the eggplants slices and fry till they are golden brown in color. Do not over-fry it else 
			the slices will become very soggy, you still want it little firm in the center.'),
		(4, 'Place the eggplants slices on a kitchen towel to drain excess oil. Set aside.'),
		(5, 'In another pan, heat 2-3 teaspoon of oil and add chopped garlic to it. Saute till it become light golden brown in color.'),
		(6, 'Add the tomatoes, turmeric powder, cayenne pepper, salt and cook till tomatoes become soft and mushy. This will take 6-7 minutes.'),
		(7, 'Next add the fried eggplants, around 1/4 cup of water and cover and cook at low heat for around 10 minutes.Switch off the flame.'),
		(8, 'To serve, take a plate and put half of the yogurt sauce. Then place the tomato sauce and eggplants over it and finally pour the remaining 
			yogurt sauce on top of the eggplants and sprinkle lots of dried mint.'),
		(9, 'Serve with kabuli pulao, pita bread or naan.');
		

/* Recipe 9 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('kabli_pulao', 1, 'Afghani Kabli Pulao', 90, 4.84, 4, 45, 45,'Lightly sweetened Vegetarian Afghani Pulao with carrots and raisins makes a great meal!', 1, 6, 3, 1, 4);

/* Add Ingredient */
INSERT INTO Ingredient( ingredient_name, protein_id) 
	VALUES
		('basmati rice', null),
		('cardamom powder', null),
		('cumin powder', null),
		('carrot shredded', null),
		('raisins', null),
		('granulated white sugar', null);
		
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(2, 9, 5, 25), -- basmati rice
		(2, 9, 1, 10), -- salt
		(0.25, 9, 1, 26), -- cardamom powder
		(0.25, 9, 1, 27), -- cumin powder
		(2, 9, 5, 9), -- water
		(1.25, 9, 5, 28), -- carrot shredded
		(0.33, 9, 5, 29), -- raisins
		(5, 9, 2, 21), -- vegetable oil
		(2, 9, 2, 30); -- granulated white sugar
	
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Soak basmati rice in enough water for 45 minutes. Drain the rice and set aside.'),
		(2, 'In a large pan, bring water to boil, add around 2-3 teaspoons of salt to the water as it boils.'),
		(3, 'When water comes to a boil, add the soaked & drained rice to it. Cook for 10 minutes, stirring at regular intervals in between. We do not want to 
			fully cook the rice and it must not turn mushy. Just like we cook the pasta till al dente (firm to bite), the rice should also be cooked to a point 
			when it is still firm. If you soak the rice for 45 minutes to 1 hour, it should not take more than 10 minutes for rice to reach that stage.'),
		(4, 'Remove the pan from heat as soon as rice is done and then drain. Set aside.'),
		(5, 'While the rice was cooking in step 1, heat vegetable oil in a pan. Once oil is hot, add the shredded carrots.'),
		(6, 'Cook for 1-2 minutes and then add 2 tablespoons of sugar and mix.'),
		(7, 'Once the color of the carrot starts changing a bit, add raisins and cook for another minute or two.'),
		(8, 'Remove from heat and drain on a paper towel. Do not drain the oil that you used for frying the carrots and raisins, we will use it the next steps.'),
		(9, 'Now take a large sheet of aluminium foil and place the fried carrot and raisins in the center.'),
		(10, 'Close all sides to form an aluminium foil packet with the carrot and raisins inside.'),
		(11, 'In the meanwhile, transfer the cooked rice back to the pan again.'),
		(12, 'Place the pan on low heat with a skillet underneath (see picture above).'),
		(13, 'Sprinkle cardamom powder on top of the rice and then place the prepared aluminium packet on one side of the rice.'),
		(14, 'Now add the oil that was leftover after frying with carrots and raisins to the rice. Just drop it all over.'),
		(15, 'Cover the pan tightly and let the rice steam with the carrots and raisins on low heat for 20-30 minutes. In the meanwhile, in a small bowl, soak 
				some saffron in a tablespoon of milk.'),
		(16, 'Remove rice from the pan and transfer to serving bowl. Pour the soaked saffron all over the rice. Sprinkle some cumin powder.'),
		(17, 'Open the aluminium foil packet and top the rice with the carrots and raisins.'),
		(18, 'Enjoy Afghani Pulao as such or serve with a side of borani banjan.');
		
/* Recipe 10 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('pumpkin_bread',3, 'Pumpkin Chocolate Chip Bread', 70, 4.32, 10, 55, 10, 'Grated fresh ginger stirred right into the batter makes this plush, moist pumpkin bread 
		recipe extra special — and spicy!', 1, 7, 227, 21, null);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('all-purpose flour', null),
		('baking powder', null),
		('baking soda', null),
		('pumpkin pie spice', null),
		('kosher salt', null),
		('(1 stick) unsalted butter, melted', null),
		('canned pure pumpkin', null),
		('granulated sugar', null),
		('packed brown sugar', null),
		('large eggs', null),
		('milk', null),
		('grated fresh ginger', null),
		('pure vanilla extract', null),
		('bittersweet chocolate chips', null);
		
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(1.75, 10, 5, 31), -- all-purpose flour
		(1, 10, 1, 32), -- baking powder
		(0.5, 10, 1, 33), -- baking soda
		(1.5, 10, 1, 34), -- pumpkin pie spice
		(0.5, 10, 1, 35), -- kosher salt
		(0.5, 10, 5, 36), -- (1 stick) unsalted butter, melted
		(1, 10, 5, 37), -- canned pure pumpkin
		(0.5, 10, 5, 38), -- granulated sugar
		(0.25, 10, 5, 39), -- packed brown sugar
		(2, 10, null, 40), -- large eggs
		(2, 10, 5, 41), -- milk
		(2, 10, 2, 42), -- grated fresh ginger
		(1, 10, 1, 43), -- pure vanilla extract
		(0.5, 10, 5, 44); -- bittersweet chocolate chips
	
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Heat oven to 350°F. Lightly coat 81/2- by 4 1/2-in. loaf pan with cooking spray. Line with parchment, leaving an overhang on the two long sides; 
			lightly coat paper.'),
		(2, 'In large bowl, whisk together flour, baking powder, baking soda, pumpkin pie spice and salt.'),
		(3, 'Transfer melted butter to large bowl and whisk in pumpkin and sugars (this will help cool it down if it is still hot). Whisk in eggs, milk, ginger 
			and vanilla. Add flour mixture and mix to combine; fold in 1/2 cup chocolate chips.'),
		(4, 'Transfer mixture to prepared pan, scatter remaining 2 Tbsp chips on top and bake until a wooden pick inserted into the center comes out clean, 45 
			to 55 minutes.'),
		(5, 'Transfer pan to wire rack and let cool 10 minutes before using parchment overhangs to transfer bread to rack to cool completely.');


/* Recipe 11 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('cream_roll', 2, 'Cream Roll', 30, 4, 8, 20, 10, 'Take a stroll down memory lane with Cream Roll. Try this easy cream roll recipe with step by step instructions.',  
         1, 7, 3, 1, null);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('puff pastry sheet', null),
		('unsalted butter', null),
		('powdered sugar', null),
		('vanilla essence', null);
	
/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(20, 11, null, 45), -- puff pastry sheet
		(8, 11, 2, 41), -- milk
		(2, 11, 5, 46), -- unsalted butter
		(2, 11, 5, 47), -- powdered sugar
		(2, 11, 1, 48); -- vanilla essence
	
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'To start with, take a puff pastry sheet and cut it into a long ribbon-like piece. Cut as many pieces you want. Make sure that all the pieces 
			are neither too wide nor too thin in width.'),
		(2, 'Now, take wood dowels and roll a piece of puff pastry sheet around it. Roll other pieces around the dowels as well. Now, cook them in the oven 
			at 180 degree Celsius for about 30 minutes.'),
		(3, 'When done, remove from oven and let it cool. When cooled slightly, take the wood out of the cooked pastry rolls.'),
		(4, 'Meanwhile, take a bowl and add vanilla essence, powdered sugar, milk and butter. Mix it and add milk slowly-slowly to the mixture while mixing 
			it.'),
		(5, 'When done, transfer the mixture in a piping bag and roll the piping bag. Cut the tip of the piping bag. Take puff pastry roll and pipe in the 
			mixture inside the opening/centre of the roll.'),
		(6, 'Once done, serve the cream roll fresh.');	


/* Recipe 12 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('french_omlette',1,'French Omelette',7.0,5,1,5.0,2.0,'Easy and healthy meal',1, 2,72,48,5);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('eggs', null),
	    ('fresh chive', null);

/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(3, 12, 1, 49),-- eggs
		(1, 12, 22, 10), -- salt
		(1, 12, 1, 11),-- Butter
		(1, 12, 23, 50);-- fresh chive
		
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Beat the eggs with the salt until the whites and the yolks are completely combined, with no spots of egg white remaining.'),
	    (2, 'Over medium-low heat, melt the butter in a skillet, then pour in the eggs.'),
	    (3, 'Using a rubber spatula, constantly scrape the bottom of the pan while shaking the pan in a circular motion to ensure that the eggs cook slowly, 
			forming only small curds, about 1-2 minutes.'),
	    (4, 'Once you start to see the bottom of the pan for more than a second after scraping, push the eggs into a round circular shape. Cook until the edges 
			solidify, then tilt the pan and carefully roll the omelette on itself.');

/* Recipe 13 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('samosa', 4, 'Vegetable Samosas', 100, 4.82, 24, 70, 30, 'Serve up these crisp vegan samosas as a tasty starter or side dish with your favourite curry.', 1, 1, 
		103, 43, 1);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('onion, finely chopped', null),
		('garlic, crushed', null),
		('potato (about 150g) finely diced', null),
		('carrot (about 100g) finely diced', null),
		('frozen peas', null),
		('curry powder', null),
		('vegetable stock', null),
		('plain flour', null),
		('sea salt', null);

/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(1, 13, 2, 21), -- vegetable oil
		(1, 13, null, 51), -- onion, finely chopped
		(2, 13, 24, 52), -- garlic, crushed
		(1, 13, null, 53), -- potato (about 150g) finely diced
		(1, 13, null, 54), -- carrot (about 100g) finely diced
		(100, 13, 19, 55), -- frozen peas
		(2, 13, 1, 56), -- curry powder
		(100, 13, 9, 57), -- vegetable stock
		(225, 13, 19, 58), -- plain flour
		(2, 13, 2, 59); -- sea salt
		
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'To make the filling, heat the oil in a frying pan, add the onion and garlic, mix in the spices and fry for 10 mins until soft. Add the vegetables, 
			seasoning and stir well until coated. Add the stock, cover and simmer for 30 mins until cooked. Leave to cool.'),
		(2, 'To make the pastry, mix flour and salt into a bowl. Make a well in the centre, add the oil and 100ml water to make a firm dough. Knead the dough 
			on a floured surface for 5-10 mins until smooth and roll into a ball. Cover in cling film and set aside at room temperature for 30 mins.'),
		(3, 'Divide the pastry into 12 equal pieces. Roll each piece into a ball and roll out into a circle of 15cm. Divide this circle into two equal pieces 
			with a knife.'),
		(4, 'Brush each edge with a little water and form a cone shape around your fingers, sealing the dampened edge. Fill with 1 tbsp mixture and press the 
			two dampened edges together to seal the top of the cone. Repeat with the remaining pastry.'),
		(5, 'Heat the oil in a large deep saucepan to 180C. The oil should come 1/3rd of the way up the pan. Deep fry the samosas in batches for 8-10 mins 
			until crisp and brown. Take out and drain on kitchen paper.');
				
/* Recipe 14 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('hotdog',1,'New York City Dog',30.0,2,4,25.0,5.0,'Hot dog',3,5,227,13,null);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('ketchup',null),
	    ('kosher beef hot dogs', 6),
	    ('hot dog buns', null),
	    ('french yellow mustard', null);

/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(2, 14, 1,21), -- vegetable oil
		(0.25, 14, 23, 9), -- water
	    (1, 14, 1, 10), -- salt
		(2, 14, 1, 60),  -- ketchup
		(4, 14, 23, 61), -- 'kosher beef hot dogs
		(4, 14, 23, 62), -- hot dog buns
	    (1, 14, 22, 63);  -- mustard
		
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Onion sauce: Heat oil in a skillet over low heat. Add in onion and cook for a few minutes until softened. Stir in water, ketchup, and salt. 
			Cover pan and cook for about 20 minutes, stirring occasionally, until onions are completely soft and saucy.'),
	    (2, 'Boil hot dogs until cooked. Place in buns and then layer sauerkraut, onion sauce, and French’s spicy brown mustard on top.'),
	    (3, 'Enjoy!');	
			
/* Recipe 15 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('corn', 1,'Yaki Tomorokoshi',10.0,3,3,7.0,3.0,'Corn on the cob',1,4,112,2, 1);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('Corn', null),
	    ('Canola oil',null),
	    ('sugar', null);

/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(3, 15,23, 64), -- Corn
		(1, 15, 1, 65),-- Canola oil
		(3, 15, 1, 2), -- Soy sauce
		(1, 15, 1, 66);-- sugar
		
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Blanch the corn in a large pot of boiling water for 3 minutes. Remove the corn from the pot.'),
	    (2, 'Heat the canola oil in a medium pan over medium-high heat. Add the corn and cook, turning occasionally, until nicely browned on all sides.'),
	    (3, 'In a small bowl, combine the soy sauce and sugar. Pour over the corn in the pan and brush over the corn until well coated. Transfer to a serving 
			plate.'),
	    (4, 'Enjoy!');			
			
/* Recipe 16 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('pork_stew',1, 'Pork Stew', 40, 5, 4, 20, 20, 'Wonderful dish of beans with tomatoes and a touch of chile and curry.', 2, 6, 79, 1, 4);

/* Add Ingredient */
INSERT INTO Ingredient(ingredient_name, protein_id) 
	VALUES
		('beef eye round', 6),
		('chicken thigh',  1),
		('pork shoulder', 3),
		('black eyed peas', null),
		('red palm oil', null),
		('red onion', null),
		('ginger', null),
		('cayenne pepper', null),
		('plum tomatoes', null),
		('tomato puree', null),
		('tomato paste', null),
		('pepper', null);

/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(1.5, 16, 5, 70), /*black eyed peas*/
		(.25, 16, 5, 71), /*red palm oil*/
		(1, 16, 23, 72), /*red onion*/
		(1, 16, 23, 73),/*ginger*/
		(1, 16, 23, 69),/*smoked pork*/
		(1.5, 16, 1, 74), /*cayenne pepper*/
		(1, 16, 1, 56),/*curry powder */
		(4, 16, 23, 75), /*plum tomatoes*/
		(1, 16, 5, 76), /* tomato puree*/
		(1, 16, 2, 78), /* tomatoe paste*/
		(1, 16, 1, 10), /* salt*/
		(1, 16, 1, 77); /* pepper*/
		
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Cook the cowpeas in plain water until they are tender. Add a big pinch of salt, cover the pot and remove it from the heat. Set aside for now.'),
		(2, 'In a medium pot, heat the palm oil over medium heat. When it is hot, saute the onions for about 3 minutes, then add the Scotch bonnet and the 
			chopped, smoked pork. Saute for another minute or three. Stir in the cayenne and the curry powder, then the chopped fresh tomatoes.'),
		(3, 'Add the remaining ingredients and the cowpeas. Remove the cowpeas from their water with a slotted spoon. It is OK if some cooking water gets into 
			the pot. Stir well to combine and simmer for 30 minutes. Serve with fried plantains or simple white rice.');			
	
/* Recipe 17 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('veg_stew',1, 'Vegetarian Bean Stew', 40, 5, 4, 20, 20, 'Wonderful dish of beans with tomatoes and a touch of chile and curry.', 2, 6, 79, 1, 2);

/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(1.5, 17, 5, 70), /*black eyed peas*/
		(.25, 17, 5, 71), /*red palm oil*/
		(1, 17, 23, 72), /*red onion*/
		(1, 17, 23, 73),/*ginger*/
		(1.5, 17, 1, 74), /*cayenne pepper*/
		(1, 17, 1, 56),/*curry powder */
		(4, 17, 23, 75), /*plum tomatoes*/
		(1, 17, 5, 76), /* tomator puree*/
		(1, 17, 2, 78), /* tomator paste*/
		(1, 17, 1, 10), /* salt*/
		(1, 17, 1, 77); /* pepper*/
		
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Cook the black eyed peas in plain water until they are tender. Add a big pinch of salt, cover the pot and remove it from the heat. Set aside 
			for now.'),
		(2, 'In a medium pot, heat the palm oil over medium heat. When it is hot, saute the onions for about 3 minutes, then add the Scotch bonnet and the 
			chopped, smoked pork. Saute for another minute or three. Stir in the cayenne and the curry powder, then the chopped fresh tomatoes.'),
		(3, 'Add the remaining ingredients and the black eyed peas. Remove the black eyed peas from their water with a slotted spoon. It is OK if some cooking 
			water gets into the pot. Stir well to combine and simmer for 30 minutes. Serve with fried plantains or simple white rice.');    
	    
/* Recipe 18 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('chicken_stew', 1, 'Chicken Bean Stew', 40, 5, 4, 20, 20, 'Wonderful dish of beans with tomatoes and a touch of chile and curry.', 2, 6, 79, 1, 4);

/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(1.5, 18, 5, 70), /*black eyed peas*/
		(.25, 18, 5, 71), /*red palm oil*/
		(1, 18, 23, 72), /*red onion*/
		(1, 18, 23, 73),/*ginger*/
		(1, 18, 23, 68),/*chicken*/
		(1.5, 18, 1, 74), /*cayenne pepper*/
		(1, 18, 1, 56),/*curry powder */
		(4, 18, 23, 75), /*plum tomatoes*/
		(1, 18, 5, 76), /* tomator puree*/
		(1, 18, 2, 78), /* tomator paste*/
		(1, 18, 1, 10), /* salt*/
		(1, 18, 1, 77); /* pepper*/
		
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Cook the cowpeas in plain water until they are tender. Add a big pinch of salt, cover the pot and remove it from the heat. Set aside for now.'),
		(2, 'In a medium pot, heat the palm oil over medium heat. When it is hot, saute the onions for about 3 minutes, then add the Scotch bonnet and the 
			chopped, smoked pork. Saute for another minute or three. Stir in the cayenne and the curry powder, then the chopped fresh tomatoes.'),
		(3, 'Add the remaining ingredients and the cowpeas. Remove the cowpeas from their water with a slotted spoon. It is OK if some cooking water gets into 
			the pot. Stir well to combine and simmer for 30 minutes. Serve with fried plantains or simple white rice.');
					
/* Recipe 19 */ 
INSERT INTO Recipe(recipe_img, icon_pic, title, duration, rating, serving_size, cook_time, prep_time, description, chef_id, mealtype_id, country_id, cuisine_id, diet_id)
	VALUES 
		('beef_bean',1, 'Beef Bean Stew', 40, 5, 4, 20, 20, 'Wonderful dish of beans with tomatoes and a touch of chile and curry.', 2, 6, 79, 1, 4);

/* Add Recipe Ingredient */
INSERT INTO Recipe_Ingredient(quantity, recipe_id, measurement_iD, ingredient_id) 
	VALUES
		(1.5, 19, 5, 70), /*black eyed peas*/
		(.25, 19, 5, 71), /*red palm oil*/
		(1, 19, 23, 72), /*red onion*/
		(1, 19, 23, 73),/*ginger*/
		(1, 19, 23, 67),/*beef*/
		(1.5, 19, 1, 74), /*cayenne pepper*/
		(1, 19, 1, 56),/*curry powder */
		(4, 19, 23, 75), /*plum tomatoes*/
		(1, 19, 5, 76), /* tomator puree*/
		(1, 19, 2, 78), /* tomator paste*/
		(1, 19, 1, 10), /* salt*/
		(1, 19, 1, 77); /* pepper*/
		
/* Add Instructions */
INSERT INTO Instruction(step_number, description) 
	VALUES
		(1, 'Cook the cowpeas in plain water until they are tender. Add a big pinch of salt, cover the pot and remove it from the heat. Set aside for now.'),
		(2, 'In a medium pot, heat the palm oil over medium heat. When it is hot, saute the onions for about 3 minutes, then add the Scotch bonnet and the 
			chopped, smoked pork. Saute for another minute or three. Stir in the cayenne and the curry powder, then the chopped fresh tomatoes.'),
		(3, 'Add the remaining ingredients and the cowpeas. Remove the cowpeas from their water with a slotted spoon. It is OK if some cooking water gets into 
			the pot. Stir well to combine and simmer for 30 minutes. Serve with fried plantains or simple white rice.');			
			
	    
/* Add Recipe to Instructions */		
INSERT INTO RECIPE_INSTRUCTIONS(recipe_id, instructions_id) 
	VALUES 
		-- Recipe 1
		(1,1),
		(1,2),
		(1,3),
		(1,4),
		-- Recipe 2
		(2,5),
		(2,6),
		(2,7),
		(2,8),
		-- Recipe 3
		(3,9),
		(3,10),
		(3,11),
		(3,12),
		-- Recipe 4
		(4,13),
		(4,14),
		(4,15),
		(4,16),
		-- Recipe 5
		(5,17),
		(5,18),
		(5,19),
		(5,20),
		-- Recipe 6
		(6,21),
		(6,22),
		(6,23),
		(6,24),
		-- Recipe 7
		(7,25),
		(7,26),
		(7,27),
		(7,28),
		-- Recipe 8
		(8,29),
		(8,30),
		(8,31),
		(8,32),
		(8,33),
		(8,34),
		(8,35),
		(8,36),
		(8,37),
		-- Recipe 9
		(9,38),
		(9,39),
		(9,40),
		(9,41),
		(9,42),
		(9,43),
		(9,44),
		(9,45),
		(9,46),
		(9,47),
		(9,48),
		(9,49),
		(9,50),
		(9,51),
		(9,52),
		(9,53),
		(9,54),
		(9,55),
		-- Recipe 10
		(10,56),
		(10,57),
		(10,58),
		(10,59),
		(10,60),
		-- Recipe 11
		(11,61),
		(11,62),
		(11,63),
		(11,64),
		(11,65),
		(11,66),
		-- Recipe 12
		(12,67),
		(12,68),
		(12,69),
		(12,70),
		-- Recipe 13
		(13,71),
		(13,72),
		(13,73),
		(13,74),
		(13,75),
		-- Recipe 14
		(14,76),
		(14,77),
		(14,78),
		-- Recipe 15
		(15,79),
		(15,80),
		(15,81),
		(15,82),
		-- Recipe 16
		(16,83),
		(16,84),
		(16,85),
		-- Recipe 17
		(17,86),
		(17,87),
		(17,88),
		-- Recipe 18
		(18,89),
		(18,90),
		(18,91),
		-- Recipe 19
		(19,92),	
		(19,93),
		(19,94);