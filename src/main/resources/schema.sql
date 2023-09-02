create table if not exists tb_todo_item(
	`id` int(11) not null auto_increment,
	user_id varchar(50) not null,
	`name` varchar(255) not null,
	description varchar(255) not null,
	status tinyint(1) not null,
    create_time datetime not null default CURRENT_TIMESTAMP,
    update_time datetime not null default CURRENT_TIMESTAMP,
    is_deleted tinyint(1) not null,
	primary key (`id`)
);