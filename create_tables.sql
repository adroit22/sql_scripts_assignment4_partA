CREATE TABLE if not exists AUTHOR(EmailAddr varchar(40) primary key not null, FirstName varchar(25) not null, LastName varchar(25) not null);

CREATE TABLE  if not exists PAPER(Id integer(10) primary key not null, Title varchar(40), Abstract varchar(100),  FileName varchar(50), AuthorId varchar(40) not null,
  FOREIGN KEY (AuthorId) REFERENCES AUTHOR(EmailAddr));

CREATE TABLE  if not exists REVIEWER(EmailAddr varchar(40) primary key not null, FirstName varchar(25), LastName varchar(25), Affiliation varchar(25), PhoneNum int(20), AuthorFeedback varchar(100), CommiteeFeedback varchar(100));

CREATE TABLE  if not exists TOPIC(Id integer(10) primary key not null, TopicName varchar(20));

CREATE TABLE  if not exists REVIEW(Id integer(20) primary key not null, PaperId int(10) not null, MeritScore int(40), ReadabilityScore int(10), ReleveanceScore int(10), OriginalityScore int(10),  Recommendation varchar(20),  ReviewerId varchar(40) not null, foreign key(PaperId) references PAPER(Id), foreign key(ReviewerId) references REVIEWER(EmailAddr));