-- Insertion Dish
INSERT INTO dish (id, name, dish_type, selling_price) VALUES
                                                          (1, 'Salaide fraîche', 'STARTER', 3500.00),
                                                          (2, 'Poulet grillé', 'MAIN', 12000.00),
                                                          (3, 'Riz aux légumes', 'MAIN', NULL),
                                                          (4, 'Gâteau au chocolat', 'DESSERT', 8000.00),
                                                          (5, 'Salade de fruits', 'DESSERT', NULL),
                                                          (10, 'Café Gourmand', 'DESSERT', 5000.00);

-- Insertion Ingredient
-- Note: id 0 et 1 sont tous deux Avocat selon tes données
INSERT INTO ingredient (id, name, price, category) VALUES
                                                       (3, 'Poulet', 4500.00, 'ANIMAL'),
                                                       (4, 'Chocolat', 3000.00, 'OTHER'),
                                                       (5, 'Beurre', 2500.00, 'DAIRY'),
                                                       (1, 'Avocat', 1200.00, 'VEGETABLE'),
                                                       (0, 'Avocat', 1200.00, 'VEGETABLE'),
                                                       (2, 'Tomate', 600.00, 'VEGETABLE');

-- Insertion DishIngredient
INSERT INTO dishingredient (id, id_dish, id_ingredient, quantity_required, unit) VALUES
                                                                                      (1, 1, 1, 0.20, 'KG'),
                                                                                      (2, 1, 2, 0.15, 'KG'),
                                                                                      (3, 2, 3, 1.00, 'KG'),
                                                                                      (4, 4, 4, 0.30, 'KG'),
                                                                                      (5, 4, 5, 0.20, 'KG');

-- Insertion StockMovement
INSERT INTO stockmovement (id, id_ingredient, quantity, type, unit, creation_datetime) VALUES
                                                                                           (1, 1, 5.00, 'IN', 'KG', '2024-01-01 08:00:00+01'),
                                                                                           (2, 2, 4.00, 'IN', 'KG', '2024-01-01 08:00:00+01'),
                                                                                           (3, 3, 10.00, 'IN', 'KG', '2024-01-01 08:00:00+01'),
                                                                                           (4, 4, 3.00, 'IN', 'KG', '2024-01-01 08:00:00+01'),
                                                                                           (5, 5, 2.50, 'IN', 'KG', '2024-01-01 08:00:00+01'),
                                                                                           (6, 1, 0.20, 'OUT', 'KG', '2024-01-06 12:00:00+01'),
                                                                                           (7, 2, 0.15, 'OUT', 'KG', '2024-01-06 12:00:00+01');