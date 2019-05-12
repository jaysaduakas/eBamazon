
Alter table Bid
add constraint deleteAuctionConstraint_bid
foreign key (auctionID) references Auction(auctionID)
on delete cascade;

Alter table AuctionKeyword
add constraint deleteAuctionConstraint_ak
foreign key (auctionID) references Auction(auctionID)
on delete cascade;

Alter table AuctionImage
add constraint deleteAuctionConstraint_ai
foreign key (auctionID) references Auction(auctionID)
on delete cascade;


ALTER table UserKeyword 
add constraint deleteUserConstraint_uk
foreign key (username) references OrdinaryUser(username)
on delete cascade;

Alter table Rating
add constraint deleteUserConstraint_ra
foreign key (rater) references OrdinaryUser(username)
on delete set null;

Alter table Rating
add constraint deleteUserConstraint2_ra
foreign key (ratee) references OrdinaryUser(username)
on delete cascade;

Alter table Warning
add constraint deleteUserConstraint_wa
foreign key (usernameOrdinary) references OrdinaryUser(username)
on delete cascade;

Alter table Complaint
add constraint deleteUserConstraint_c
foreign key (sender) references OrdinaryUser(username)
on delete set null;

Alter table Complaint
add constraint deleteUserConstraint2_c
foreign key (receiver) references OrdinaryUser(username)
on delete cascade;

alter table PendingApplication
add constraint deleteUserConstraint_pa
foreign key (username) references OrdinaryUser(username)
on delete cascade;

Alter table Friend
add constraint deleteUserConstraint_f
foreign key (usernameSuggesting) references OrdinaryUser(username)
on delete cascade;

Alter table Friend
add constraint deleteUserConstraint2_f
foreign key (usernameConfirming) references OrdinaryUser(username)
on delete cascade;

Alter table Auction
add constraint deleteUserConstraint_a
foreign key (creator) references OrdinaryUser(username)
on delete cascade;

alter table Bid
add constraint deleteUserConstraint_bid
foreign key (username) references OrdinaryUser(username)
on delete cascade;