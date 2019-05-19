# Part B
Each query is called using a seperate method inside the PaperReviewDriver class and it is called from the main method. There is no additional class apart from this. We use preparedstatement to execute the query.
### Step 1 -
Run the code by running the main method inside the PaperReviewDriver class.
### Queries Used are -

#### Query1
select p.Id as PaperId, p.Title, p.Abstract, a.EmailAddr as EmailAddress, a.FirstName, a.LastName from paper as p join author as a on a.EmailAddr= p.AuthorId where p.AuthorId= 'Daenerys@got.com';

#### Query2
select r.* from review as r where r.Recommendation = 'PUBLISH' and r.PaperId= 103;

#### Query3	
select count(Id) as count from paper;

#### Query4
insert into AUTHOR(EmailAddr, FirstName, LastName) Values('petyr@gmail.com', 'Petyr', 'Baelish');

insert INTO PAPER(Id, Title, Abstract , FileName, AuthorId) VALUES (105, 'The Dragon and the Wolf', 'Based on story of Master of Coin', 'Littlefinger', 'petyr@gmail.com');

#### Query5	
delete from author where EmailAddr = 'Daenerys@got.com';
