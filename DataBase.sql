CREATE TABLE Movies(
	title varchar(250) not null,
	releaseYear int not null,
	unique (title, releaseYear),
	mid int not null Identity(1,1),
	primary key(mid)
);

CREATE TABLE Ratings(
	value int not null,
	id int not null Identity(1,1),
	idMovie int not null,
	foreign key(idMovie) references Movies(mid),
	check(value>0 and value<6),
	primary key(id)
);

CREATE TABLE Reviews(
	review varchar(250),
	reviewName varchar(50),
	reviewSummary varchar(250),
	idMovie int not null,
	foreign key(idMovie) references Movies(mid),
	idRatting int not null,
	foreign key(idRatting) references Ratings(id),
	rid int not null Identity(1,1),
	primary key(rid)
);


DROP TABLE Reviews
DROP TABLE Ratings
DROP TABLE Movies

INSERT INTO Movies VALUES ('Anaone2',2012)

SELECT * FROM Movies

INSERT INTO Ratings VALUES(5,1)

SELECT * FROM Ratings

INSERT INTO Reviews VALUES('Review', 'ReviewName', 'ReviewSummary',1,1)

SELECT * FROM Reviews
