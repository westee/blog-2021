create table blog (
    id int auto_increment primary key,
    user_id int,
    `desc` varchar(100),
    title varchar(100),
    content TEXT,
    created_at datetime default now(),
    updated_at datetime default now()
)