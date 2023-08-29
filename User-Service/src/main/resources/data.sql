-- Insert data for UserClass (shared properties)
INSERT INTO USER_CLASS (ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, PASSWORD)
VALUES
    (1, 'Adam', 'Sz', 'adam@example.com', '123456789', 'a1'),
    (2, 'John', 'Smith', 'john@example.com', '987654321', 'p1'),
     (3, 'Alice', 'Johnson', 'alice@example.com', '555555555', 'p1');

INSERT INTO CLIENT (POINTS, COUNTRY, REGION, CITY, ID)
VALUES
    (0, 'Poland', 'Pomorskie', 'Gdansk', 3);

INSERT INTO EMPLOYEE (START_TIME, END_TIME, ID)
VALUES
    ( '09:00:00', '18:00:00', 1),
    ( '09:00:00', '18:00:00', 2);  -- Assuming ID 1 corresponds to Jane Smith

-- Insert data for Facility
INSERT INTO FACILITY (ID, NAME, COUNTRY, REGION, CITY, STREET, HOUSE_NUMBER)
VALUES
    (1, 'Facility 1', 'Poland', 'Pomorskie', 'Gdansk', 'Gdanska', '1'),
    (2, 'Facility 2', 'Poland', 'Mazowieckie', 'Warszawa', 'Warszawska', '2');

-- ... (Insert data for other entities)

INSERT INTO OWNER (ID)
VALUES
    (1);

---- Insert data for Facility
--INSERT INTO FACILITY (NAME, COUNTRY, REGION, CITY, STREET, HOUSE_NUMBER)
--VALUES
--    ('Facility 1', 'USA', 'California', 'Los Angeles', 'Main St', '123'),
--    ('Facility 2', 'USA', 'New York', 'New York City', 'Broadway', '456');

---- Insert data for Task
--INSERT INTO TASK (CITY, REGION, CATEGORY, NAME, FACILITY_ID, PRICE, DURATION_IN_MINUTES, CURRENCY)
--VALUES
--    ('Los Angeles', 'California', 'Cleaning', 'Cleaning Service', 1, 50.0, 60, 'USD'),
--    ('New York City', 'New York', 'Repairs', 'Plumbing Service', 2, 100.0, 90, 'USD');
--
---- Insert data for Appointment (You will need to adjust the table names and column names)
--INSERT INTO APPOINTMENT (DATE_TIME, EMPLOYEE_ID, CLIENT_ID, TASK_ID)
--VALUES
--    ('2023-08-25 10:00:00', 2, 3, 1),
--    ('2023-08-26 15:00:00', 3, 3, 2);
