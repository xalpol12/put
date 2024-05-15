-- Insert sample recipes
INSERT INTO recipes (recipe_name, estimated_time) VALUES
                                                    ('Spaghetti Carbonara', 30),
                                                    ('Chicken Alfredo', 45),
                                                    ('Vegetable Stir Fry', 20),
                                                    ('Beef Stroganoff', 40),
                                                    ('Mushroom Risotto', 35),
                                                    ('Grilled Salmon', 25),
                                                    ('Beef Tacos', 30),
                                                    ('Margherita Pizza', 25),
                                                    ('Caesar Salad', 15),
                                                    ('Chocolate Cake', 60),
                                                    ('Apple Pie', 50),
                                                    ('Classic Lasagna', 60),
                                                    ('Garlic Shrimp Scampi', 20),
                                                    ('Lemon Herb Roasted Chicken', 50),
                                                    ('Pumpkin Soup', 35),
                                                    ('Beef Burger', 35),
                                                    ('Fish and Chips', 40),
                                                    ('Vegetable Curry', 30),
                                                    ('Egg Fried Rice', 20),
                                                    ('Caprese Salad', 15);
-- Insert sample recipe collections
INSERT INTO recipe_collections (collection_name) VALUES
                                                    ('Pasta Recipes'),
                                                    ('Quick Meals'),
                                                    ('Seafood Delights'),
                                                    ('Classic Comfort Foods');

-- Insert sample relationships between recipes and collections
INSERT INTO recipe_collection_recipe (recipe_collection_id, recipe_id) VALUES
                                                                           (1, 1), (1, 2),
                                                                           (2, 2), (2, 3),
                                                                           (3, 6), (3, 13),
                                                                           (4, 4), (4, 5), (4, 12);