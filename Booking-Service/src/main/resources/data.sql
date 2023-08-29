INSERT INTO FACILITY (ID, NAME, COUNTRY, REGION, CITY, STREET, HOUSE_NUMBER)
VALUES
    (1, 'Barber VIP', 'Poland', 'Pomorskie', 'Gdansk', 'Gdanska', '1'),
    (2, 'Oil Change Deluxe', 'Poland', 'Pomorskie', 'Gdansk', 'Warszawska', '2'),
    (3, 'Massage Krakow', 'Poland', 'Malopolskie', 'Krakow', 'Krakowska', '14');


-- Insert sample tasks
-- Insert sample tasks
INSERT INTO task (city, region, category, name, facility_id, price, duration_in_minutes, currency)
VALUES
    ('Gdansk', 'Pomorskie', 'Barber', 'Barber VIP', 1, 80.0, 30, 'PLN'),
    ('Gdansk', 'Pomorskie', 'Mechanic', 'Oil Change Deluxe', 2, 150.0, 45, 'PLN'),
    ('Krakow', 'Lesser Poland', 'Spa', 'Massage', 3, 100.0, 60, 'PLN');
