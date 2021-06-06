create table user (
     id int auto_increment unique primary key ,
     username varchar(20) unique ,
     avatar varchar(200),
     encrypted_password varchar(100),
     created_at timestamp default now(),
     updated_at timestamp default now()
)