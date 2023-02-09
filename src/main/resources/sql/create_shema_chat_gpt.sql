DROP schema chat_gpt cascade;
CREATE SCHEMA IF NOT EXISTS chat_gpt;

create table if not exists chat_gpt.dict_model_open_ai(
    ID serial NOT NULL,
    NAME varchar NOT NULL,
    SNAME varchar,
    VALUE varchar NOT NULL,
    DESCRIPTION  varchar NOT NULL,
    CREATE_DATE timestamp without time zone default now(),
    UPDATE_DATE timestamp without time zone default NULL,
    CONSTRAINT pk_dict_model_open_ai PRIMARY KEY (ID)

    );
comment on table chat_gpt.dict_model_open_ai is 'Словарь моделей open ai';
comment on column chat_gpt.dict_model_open_ai.NAME is 'Наименование записи';
comment on column chat_gpt.dict_model_open_ai.SNAME is 'Уникальный идентификатор сообщения';
comment on column chat_gpt.dict_model_open_ai.VALUE is 'Значение';
comment on column chat_gpt.dict_model_open_ai.DESCRIPTION is 'Описание';
comment on column chat_gpt.dict_model_open_ai.CREATE_DATE is 'Дата создания записи';
comment on column chat_gpt.dict_model_open_ai.UPDATE_DATE is 'Дата обновления записи';

create table if not exists chat_gpt.message_inbox(
ID serial NOT NULL,
UUID uuid NOT NULL ,
VALUE varchar,
ID_MODEL integer,

CONSTRAINT pk_message_inbox PRIMARY KEY (ID),
CONSTRAINT fk_message_inbox_dict_open_ai FOREIGN KEY(ID_MODEL) REFERENCES chat_gpt.dict_model_open_ai(id),
CONSTRAINT unique_message_inbox_uuid  unique (UUID)
);

comment on table chat_gpt.message_inbox is 'Входящие сообщения';
comment on column chat_gpt.message_inbox.ID is 'Идентификатор сообщения';
comment on column chat_gpt.message_inbox.UUID is 'Уникальный идентификатор сообщения';
comment on column chat_gpt.message_inbox.VALUE is 'Значение';
comment on column chat_gpt.message_inbox.ID_MODEL is 'Тип модели';

create table if not exists chat_gpt.message_outbox(
ID serial NOT NULL,
UUID uuid NOT NULL ,
VALUE varchar,

CONSTRAINT pk_message_outbox PRIMARY KEY (ID),
CONSTRAINT unique_message_outbox_uuid  unique (UUID)
);
comment on table chat_gpt.message_outbox is 'Входящие сообщения';
comment on column chat_gpt.message_outbox.ID is 'Идентификатор сообщения';
comment on column chat_gpt.message_outbox.UUID is 'Уникальный идентификатор сообщения';
comment on column chat_gpt.message_outbox.VALUE is 'Значение';

