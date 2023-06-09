CREATE table roles
(
    role_id   int generated by default as identity primary key,
    role_name text unique
);
INSERT INTO roles (role_name)
values ('ADMIN'),
       ('EMPLOYEE'),
       ('USER');



CREATE table users
(
    user_id     int generated by default as identity primary key,
    telegram_id bigint unique,
    username    text,
    first_name  text
);

INSERT INTO users (telegram_id, username, first_name)
values (123123, 'test_323', 'Eli');



CREATE table users_roles
(
    user_id int REFERENCES users (user_id),

    role_id int REFERENCES roles (role_id),

    PRIMARY KEY (user_id, role_id)
);

INSERT INTO users_roles (user_id, role_id)
values (1, 3);

drop table restaurants cascade ;
drop table menus cascade ;
drop table menu_items cascade ;
drop table categories cascade ;