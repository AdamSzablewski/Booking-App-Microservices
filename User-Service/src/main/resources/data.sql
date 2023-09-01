-- Insert data for USER_CLASS (shared properties including country, region, and city)
INSERT INTO USER_CLASS (ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, PASSWORD, COUNTRY, REGION, CITY)
VALUES
    (1, 'Adam', 'Sz', 'adam@example.com', '123456789', 'a1', 'Poland', 'Pomorskie', 'Gdansk'),
    (2, 'John', 'Smith', 'john@example.com', '987654321', 'p1', 'Poland', 'Pomorskie', 'Gdansk'),
    (3, 'Alice', 'Johnson', 'alice@example.com', '555555555', 'p1', 'Poland', 'Pomorskie', 'Gdansk'),
    (4, 'test', 'Owner1', 'to1@example.com', '123456789', 'a1', 'Poland', 'Poland', 'Poland'),
    (5, 'test', 'Owner2', 'to2@example.com', '123456789', 'a1', 'Poland', 'Poland', 'Poland');

-- Insert data for CLIENT (with shared USER_CLASS properties)
INSERT INTO CLIENT (POINTS, ID)
VALUES

    (8, 4),
    (12, 5);

-- Insert data for EMPLOYEE (with shared USER_CLASS properties)
INSERT INTO EMPLOYEE (START_TIME, END_TIME, ID)
VALUES
    ('09:00:00', '18:00:00', 1),
    ('09:00:00', '18:00:00', 2),
    ('08:00:00', '17:00:00', 3);

-- Insert data for OWNER (with shared USER_CLASS properties)
INSERT INTO OWNER (ID)
VALUES
    (1);

