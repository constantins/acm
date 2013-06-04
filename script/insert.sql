USE demo;

insert into `users`(`USER_ID`,`username`) values (1,'user1');
insert into `users`(`USER_ID`,`username`) values (2,'user2');

insert into `accounts`(`ACCOUNT_ID`,`number`,`sold`,`USER_ID`) values (1,'accountno1',1,1);
insert into `accounts`(`ACCOUNT_ID`,`number`,`sold`,`USER_ID`) values (2,'accountno2',20000,1);
insert into `accounts`(`ACCOUNT_ID`,`number`,`sold`,`USER_ID`) values (3,'accountno3',300009,2);