

CREATE TYPE dish_type AS ENUM ('STARTER', 'MAIN', 'DESSERT');
CREATE TYPE ingredient_category AS ENUM ('VEGETABLE', 'ANIMAL', 'MARINE', 'DAIRY', 'OTHER');
CREATE TYPE mouvement_type AS ENUM ('IN', 'OUT');
CREATE TYPE unit_type AS ENUM ('PCS', 'KG', 'L');

CREATE TABLE dish (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      dish_type dish_type NOT NULL,
                      selling_price DOUBLE PRECISION -- Peut être NULL pour Riz aux légumes
);

CREATE TABLE ingredient (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            price DOUBLE PRECISION NOT NULL,
                            category ingredient_category NOT NULL,
                            required_quantity DOUBLE PRECISION, -- Colonne du sujet
                            initial_stock DOUBLE PRECISION       -- Colonne du sujet
);

CREATE TABLE dishingredient (
                                 id SERIAL PRIMARY KEY,
                                 id_dish INTEGER REFERENCES dish(id),
                                 id_ingredient INTEGER REFERENCES ingredient(id),
                                 quantity_required DOUBLE PRECISION NOT NULL,
                                 unit unit_type NOT NULL
);

CREATE TABLE stockmovement (
                               id SERIAL PRIMARY KEY,
                               id_ingredient INTEGER REFERENCES ingredient(id),
                               quantity DOUBLE PRECISION NOT NULL,
                               type mouvement_type NOT NULL,
                               unit unit_type NOT NULL,
                               creation_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

select * from dishingredient;
