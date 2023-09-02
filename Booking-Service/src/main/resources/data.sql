-- Insert data for FACILITY (including employee IDs)
INSERT INTO FACILITY (ID, NAME, COUNTRY, REGION, CITY, STREET, HOUSE_NUMBER, OWNER, EMPLOYEES)
VALUES
    (1, 'Barber VIP', 'Poland', 'Pomorskie', 'Gdansk', 'Gdanska', '1', '1', ARRAY[1,2,3]),
    (2, 'Oil Change Deluxe', 'Poland', 'Pomorskie', 'Gdansk', 'Warszawska', '2', '2',null),
    (3, 'Massage Krakow', 'Poland', 'Malopolskie', 'Krakow', 'Krakowska', '14', '3', null);

-- Insert data for TASK (including employee IDs)
INSERT INTO task (city, region, category, name, facility_id, price, duration_in_minutes, currency, employees)
VALUES
    ('Gdansk', 'Pomorskie', 'Barber', 'Simple haircut', 1, 80.0, 30, 'PLN', ARRAY[1, 2, 3]),
    ('Gdansk', 'Pomorskie', 'Mechanic', 'Oil Change Deluxe', 2, 150.0, 45, 'PLN', NULL), -- Add the appropriate employee IDs
    ('Krakow', 'Lesser Poland', 'Spa', 'Massage', 3, 100.0, 60, 'PLN', NULL); -- Add the appropriate employee IDs
