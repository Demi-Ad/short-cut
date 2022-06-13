create table redirect_url
(
    id                  bigint auto_increment
        primary key,
    convert_url         varchar(6)   not null,
    created_date_time   datetime(6)  null,
    document_title      varchar(255) null,
    origin_url          longtext     not null,
    url_make_ip_address varchar(255) null,
    constraint UK_eaw9hiw3jb0lpjdixfkfi0vt9
        unique (convert_url)
);

create table redirect_information
(
    id             bigint auto_increment
        primary key,
    client_browser varchar(255) null,
    client_country varchar(255) null,
    client_device  varchar(255) null,
    client_os      varchar(255) null,
    redirect_date  date         null,
    referer        varchar(255) null,
    url_id         bigint       null,
    constraint redirect
        foreign key (url_id) references redirect_url (id)
);

create index i_convert_url
    on redirect_url (convert_url);

