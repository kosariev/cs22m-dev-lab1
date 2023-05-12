CREATE TABLE document
(
    id int not null auto_increment,
    title varchar(64) not null,
    type enum('VACATION', 'JOB', 'OTHER') not null default 'OTHER',
    body varchar(255) not null,
    created date not null,
    signed date null,
    owner varchar(16) not null,
    PRIMARY KEY (id)
);

