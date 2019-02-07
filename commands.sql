-- INSERT INTO dbo.Employees (EmployeesId, Name, Location) VALUES (6, 'Grace', 'Korea')
-- INSERT INTO dbo.Employees (EmployeesId, Name, Location) VALUES (8, 'Chadwick', 'United States')
SELECT * FROM dbo.Inventory FOR JSON AUTO;
-- SELECT TOP 10 * FROM dbo.Employees FOR JSON AUTO
-- SELECT MAX(EmployeesId) FROM dbo.Employees; -- Use this + 1 to calculate at what ID to put next item