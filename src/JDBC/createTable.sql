--CS5430 System Security Course Project
--DB Schema
------------------------------------------------------------------
--Create Tables:
------------------------------------------------------------------
CREATE TABLE Users(
	username VARCHAR(20),
	password VARCHAR(20),
	roleID INT, 
	did INT,
	name VARCHAR(20),
	phone VARCHAR(10),
	location VARCHAR(20), 
	currProj VARCHAR(50), 
	PRIMARY KEY (username)
);
CREATE TABLE Department(
	did INT, 
	dname VARCHAR(20), 
	dhead VARCHAR(20), 
	PRIMARY KEY (did),
	FOREIGN KEY (dhead) REFERENCES Users(username)
);
CREATE TABLE WorksFor(
	worker VARCHAR(20),
	master VARCHAR(20),
	did INT,
	PRIMARY KEY(worker, master),
	FOREIGN KEY (worker) REFERENCES Users(username),
	FOREIGN KEY (master) REFERENCES Users(username),
	FOREIGN KEY (did) REFERENCES Department(did)
);
CREATE TABLE Friends( 
	friend1 VARCHAR(20),
	friend2 VARCHAR(20),
	PRIMARY KEY(friend1, friend2),
	FOREIGN KEY (friend1) REFERENCES Users(username),
	FOREIGN KEY (friend2) REFERENCES Users(username)
);
CREATE TABLE DeptDiscBoard(
	deptID INT,
	msgID INT,
	msgContent VARCHAR(500),
	PRIMARY KEY (deptID, msgID), 
	FOREIGN KEY (deptID) REFERENCES Department(did)
);
CREATE TABLE CompanyMsgBoard(
	msgID INT,
	msgContent VARCHAR(500),
	PRIMARY KEY (msgID)
);