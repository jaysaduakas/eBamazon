CREATE DATABASE IF NOT EXISTS Ebamazon;

USE Ebamazon;

CREATE TABLE IF NOT EXISTS TaxRate(
	stateID varchar(2),
	rate double,
	PRIMARY KEY (stateID)
);

CREATE TABLE IF NOT EXISTS OrdinaryUser(
	username varchar(20),
	name varchar(50),
	password varchar(20),
	address varchar(255),
	stateID varchar(2),
	creditcard varchar(16),
	phoneNumber varchar(10),
	approvedStatus bit(1),
	bannedStatus bit(1),
	VIPStatus bit(1),
	dateTimeRegistered datetime,
	suspendedStatus bit(1),
	foreign key (stateID) REFERENCES TaxRate(stateID) on delete set null,
	PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS UserKeyword(
	username varchar(20),
	keyword varchar(100),
	FOREIGN KEY (username) REFERENCES OrdinaryUser(username) on delete cascade,
	PRIMARY KEY (username, keyword)
);

CREATE TABLE IF NOT EXISTS Rating(
	rater varchar(20),
	ratee varchar(20),
	rating int,
	dateTimeRated datetime,
	FOREIGN KEY (rater) REFERENCES OrdinaryUser(username) on delete cascade,
	FOREIGN KEY (ratee) REFERENCES OrdinaryUser(username) on delete cascade,
	PRIMARY KEY (rater,ratee,dateTimeRated)
);

CREATE TABLE IF NOT EXISTS SuperUser(
	username varchar(20),
	name varchar(20),
	password varchar(20),
	PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS Message(
	sender varchar(20),
	receiver varchar(20),
	subject varchar(50),
	messageContent varchar(2048),
	dateTimeSent datetime,
	PRIMARY KEY (sender, receiver, dateTimeSent)
);

CREATE TABLE IF NOT EXISTS Warning(
	usernameOrdinary varchar(20),
	usernameSuper varchar(20),
	reason varchar(1024),
	dateTimeIssued datetime,
	FOREIGN KEY (usernameOrdinary) REFERENCES OrdinaryUser(username) on delete cascade,
	FOREIGN KEY (usernameSuper) REFERENCES SuperUser(username) on delete cascade,
	PRIMARY KEY (usernameOrdinary, usernameSuper, dateTimeIssued)
);

CREATE TABLE IF NOT EXISTS Complaint(
	sender varchar(20),
	receiver varchar(20),
	complaint varchar(1024),
	dateTimeMade datetime,
	alreadyJustified bit(1),
	usernameSuper varchar(20),
	FOREIGN KEY (sender) REFERENCES OrdinaryUser(username) on delete cascade,
	FOREIGN KEY (usernameSuper) REFERENCES SuperUser(username) on delete set null,
	FOREIGN KEY (receiver) REFERENCES OrdinaryUser(username) on delete cascade,
	PRIMARY KEY (sender, receiver, dateTimeMade)
);

CREATE TABLE IF NOT EXISTS PendingApplication(
	username varchar(20),
	dateTimeSubmitted datetime,
	FOREIGN KEY (username) REFERENCES OrdinaryUser(username) on delete cascade,
	PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS Friend(
	usernameSuggesting varchar(20),
	usernameConfirming varchar(20),
	friendshipConfirmed bit(1),
	dateTimeConfirmed datetime,
	FOREIGN KEY (usernameSuggesting) REFERENCES OrdinaryUser(username) on delete cascade,
	FOREIGN KEY (usernameConfirming) REFERENCES OrdinaryUser(username) on delete cascade,
	PRIMARY KEY (usernameSuggesting, usernameConfirming)
);

CREATE TABLE IF NOT EXISTS Auction(
	auctionID int AUTO_INCREMENT,
	title varchar(20),
	creator varchar(20),
	dateTimeCreated datetime,
	dateTimeConfirmed datetime,
	approvalStatus bit(1),
	liveStatus bit(1),
	price DECIMAL(10,2),
	fixedAuction bit(1),
	description varchar(2048),
	FOREIGN KEY (creator) REFERENCES OrdinaryUser(username) on delete cascade,
	PRIMARY KEY (auctionID)
);

CREATE TABLE IF NOT EXISTS Bid(
	username varchar(20),
	auctionID int,
	winningBid bit(1),
	amount decimal(10, 2),
	dateTimeMade datetime,
	FOREIGN KEY (username) REFERENCES OrdinaryUser(username) on delete cascade,
	FOREIGN KEY (auctionID) REFERENCES Auction(auctionID) on delete cascade,
	PRIMARY KEY (auctionID, amount, username)
);

CREATE TABLE IF NOT EXISTS AuctionKeyword(
	auctionID int,
	keyword varchar(100),
	FOREIGN KEY (auctionID) REFERENCES Auction(auctionID) on delete cascade,
	PRIMARY KEY (auctionID, keyword)
);

CREATE TABLE IF NOT EXISTS AuctionImage(
	auctionID int,
	imageNumber int,
	image LONGBLOB,
	defaultPhoto bit(1),
	FOREIGN KEY (auctionID) REFERENCES Auction(auctionID) on delete cascade,
	PRIMARY KEY (auctionID, imageNumber)
);

CREATE TABLE IF NOT EXISTS BlackListItem(
	auctionID int,
	usernameSuper varchar(20),
	reason varchar(1024),
	dateTimeListed datetime,
	FOREIGN KEY (auctionID) REFERENCES Auction(auctionID) on delete cascade,
	PRIMARY KEY (auctionID)
);

CREATE TABLE IF NOT EXISTS Taboo(
	word varchar(255),
	usernameSuper varchar(20),
	dateTimeBanned datetime,
	PRIMARY KEY (word)
);

Alter Table Complaint
add complaineeResponded bit(1);

Alter Table Complaint
add complaineeResponse varchar(1024);

alter table auction
add kickedBack bit(1);

alter table auction
add denied bit(1);

INSERT into TaxRate values ('AL', 0.04),
('AK', 0.0),
('AZ', 0.056),
('AR', 0.065),
('CA', 0.0725),
('CO', 0.029),
('CT', 0.0635),
('DE', 0.0),
('DC', 0.06),
('FL', 0.06),
('GA', 0.04),
('HI', 0.04),
('ID', 0.06),
('IL', 0.0625),
('IN', 0.07),
('IA', 0.06),
('KS', 0.065),
('KY', 0.06),
('LA', 0.0445),
('ME', 0.055),
('MD', 0.06),
('MA', 0.0625),
('MI', 0.0625),
('MN', 0.06875),
('MS', 0.07),
('MO', 0.04225),
('MT', 0.0),
('NE', 0.06625),
('NV', 0.0685),
('NH', 0.0),
('NJ', 0.0625),
('NM', 0.05125),
('NY', 0.04),
('NC', 0.0475),
('ND', 0.05),
('OH', 0.0575),
('OK', 0.045),
('OR', 0.0),
('PA', 0.06),
('PR', 0.115),
('RI', 0.07),
('SC', 0.06),
('SD', 0.045),
('TN', 0.07),
('TX', 0.0625),
('UT', 0.0485),
('VT', 0.06),
('VA', 0.043),
('WA', 0.065),
('WV', 0.06),
('WI', 0.05),
('WY', 0.04);

insert into SuperUser values ("AutoMod", "AutoMod", null);

