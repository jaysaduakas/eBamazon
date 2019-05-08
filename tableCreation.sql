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
	foreign key (stateID) REFERENCES TaxRate(stateID),
	PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS UserKeyword(
	username varchar(20),
	keyword varchar(100),
	FOREIGN KEY (username) REFERENCES OrdinaryUser(username),
	PRIMARY KEY (username, keyword)
);

CREATE TABLE IF NOT EXISTS Rating(
	rater varchar(20),
	ratee varchar(20),
	rating int,
	dateTimeRated datetime,
	FOREIGN KEY (rater) REFERENCES OrdinaryUser(username),
	FOREIGN KEY (ratee) REFERENCES OrdinaryUser(username),
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
	FOREIGN KEY (usernameOrdinary) REFERENCES OrdinaryUser(username),
	FOREIGN KEY (usernameSuper) REFERENCES SuperUser(username),
	PRIMARY KEY (usernameOrdinary, usernameSuper, dateTimeIssued)
);

CREATE TABLE IF NOT EXISTS Complaint(
	sender varchar(20),
	receiver varchar(20),
	complaint varchar(1024),
	dateTimeMade datetime,
	alreadyJustified bit(1),
	usernameSuper varchar(20),
	FOREIGN KEY (sender) REFERENCES OrdinaryUser(username),
	FOREIGN KEY (usernameSuper) REFERENCES SuperUser(username),
	FOREIGN KEY (receiver) REFERENCES OrdinaryUser(username),
	PRIMARY KEY (sender, receiver, dateTimeMade)
);

CREATE TABLE IF NOT EXISTS PendingApplication(
	username varchar(20),
	dateTimeSubmitted datetime,
	FOREIGN KEY (username) REFERENCES OrdinaryUser(username),
	PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS Friend(
	usernameSuggesting varchar(20),
	usernameConfirming varchar(20),
	friendshipConfirmed bit(1),
	dateTimeConfirmed datetime,
	FOREIGN KEY (usernameSuggesting) REFERENCES OrdinaryUser(username),
	FOREIGN KEY (usernameConfirming) REFERENCES OrdinaryUser(username),
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
	FOREIGN KEY (creator) REFERENCES OrdinaryUser(username),
	PRIMARY KEY (auctionID)
);

CREATE TABLE IF NOT EXISTS Bid(
	username varchar(20),
	auctionID int,
	winningBid bit(1),
	amount decimal(10, 2),
	dateTimeMade datetime,
	FOREIGN KEY (username) REFERENCES OrdinaryUser(username),
	FOREIGN KEY (auctionID) REFERENCES Auction(auctionID),
	PRIMARY KEY (auctionID, amount, username)
);

CREATE TABLE IF NOT EXISTS AuctionKeyword(
	auctionID int,
	keyword varchar(100),
	FOREIGN KEY (auctionID) REFERENCES Auction(auctionID),
	PRIMARY KEY (auctionID, keyword)
);

CREATE TABLE IF NOT EXISTS AuctionImage(
	auctionID int,
	imageNumber int,
	image LONGBLOB,
	defaultPhoto bit(1),
	FOREIGN KEY (auctionID) REFERENCES Auction(auctionID),
	PRIMARY KEY (auctionID, imageNumber)
);

CREATE TABLE IF NOT EXISTS BlackListItem(
	auctionID int,
	usernameSuper varchar(20),
	reason varchar(1024),
	dateTimeListed datetime,
	FOREIGN KEY (auctionID) REFERENCES Auction(auctionID),
	PRIMARY KEY (auctionID)
);

CREATE TABLE IF NOT EXISTS Taboo(
	word varchar(255),
	usernameSuper varchar(20),
	dateTimeBanned datetime,
	PRIMARY KEY (word)
);
