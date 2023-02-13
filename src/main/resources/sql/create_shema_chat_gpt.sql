DROP schema chat_gpt cascade;
CREATE SCHEMA IF NOT EXISTS chat_gpt;

create table if not exists chat_gpt.dict_model_open_ai(
    ID serial NOT NULL,
    NAME varchar NOT NULL,
    SNAME varchar,
    MAX_TOKENS varchar NOT NULL,
    DESCRIPTION  varchar NOT NULL,
    CREATED_AT timestamp without time zone NOT NULL,
    UPDATED_AT timestamp without time zone NOT NULL,
    CONSTRAINT pk_dict_model_open_ai PRIMARY KEY (ID)

    );
comment on table chat_gpt.dict_model_open_ai is 'Словарь моделей open ai';
comment on column chat_gpt.dict_model_open_ai.NAME is 'Наименование записи';
comment on column chat_gpt.dict_model_open_ai.SNAME is 'Уникальный идентификатор сообщения';
comment on column chat_gpt.dict_model_open_ai.MAX_TOKENS is 'Максимальное количество токенов';
comment on column chat_gpt.dict_model_open_ai.DESCRIPTION is 'Описание';
comment on column chat_gpt.dict_model_open_ai.CREATED_AT is 'Дата создания записи';
comment on column chat_gpt.dict_model_open_ai.UPDATED_AT is 'Дата обновления записи';

-- Надо разбить таблицу на части (Слишком большая)
create table if not exists chat_gpt.message_settings(
                                                        ID serial NOT NULL,
                                                        ID_MODEL integer NOT NULL,
                                                        PROMPT VARCHAR NOT NULL,
                                                        SUFFIX varchar DEFAULT NULL,
                                                        TEMPERATURE INTEGER NOT NULL DEFAULT 1,
                                                        TOP_P DOUBLE PRECISION NOT NULL DEFAULT 1,
                                                        MAX_TOKENS INTEGER NOT NULL DEFAULT 16,
                                                        N INTEGER DEFAULT 1,
                                                        LOGPROBS INTEGER DEFAULT NULL,
                                                        STOP VARCHAR[] DEFAULT NULL,
                                                        PRESENCE_PENALTY DOUBLE PRECISION DEFAULT 0,
                                                        FREQUENCY_PENALTY DOUBLE PRECISION DEFAULT 0,
                                                        BEST_OF INTEGER DEFAULT 1,
                                                        LOGIT_BIAS JSON DEFAULT NULL,
                                                        CREATED_AT timestamp without time zone NOT NULL,
                                                        UPDATED_AT timestamp without time zone NOT NULL,

                                                        CONSTRAINT pk_message_settings PRIMARY KEY (ID),
                                                        CONSTRAINT max_min_check_presence_penalty CHECK ( PRESENCE_PENALTY >= -2.0 and PRESENCE_PENALTY <= 2.0),
                                                        CONSTRAINT max_min_check_frequency_penalty CHECK ( FREQUENCY_PENALTY >= -2.0 and FREQUENCY_PENALTY <= 2.0),
                                                        CONSTRAINT max_min_check_logprobs CHECK ( LOGPROBS >= 0 and LOGPROBS <= 5),


                                                        CONSTRAINT fk_message_settings_inbox_dict_open_ai FOREIGN KEY(ID_MODEL) REFERENCES chat_gpt.dict_model_open_ai(id)
);
comment on table chat_gpt.message_settings is 'Настройки запроса к open ai';
comment on column chat_gpt.message_settings.ID is 'Идентификатор сообщения';
comment on column chat_gpt.message_settings.ID_MODEL is 'Идентификатор модели';
comment on column chat_gpt.message_settings.PROMPT is 'Запрос';
comment on column chat_gpt.message_settings.SUFFIX is 'Суффиксы следующие за вставленным текстом';
comment on column chat_gpt.message_settings.TEMPERATURE is 'Температура выборки';
comment on column chat_gpt.message_settings.TOP_P is 'Альтернатива выборке с температурой, называемая выборкой ядра, где модель рассматривает результаты токенов с вероятностной массой top_p';
comment on column chat_gpt.message_settings.N is 'Количество генераций';
comment on column chat_gpt.message_settings.LOGPROBS is 'Количество верояных генераций токенов';
comment on column chat_gpt.message_settings.STOP is 'Последовательности остановки';
comment on column chat_gpt.message_settings.PRESENCE_PENALTY is 'Положительные значения увеличивает вероятность появления новых токенов';
comment on column chat_gpt.message_settings.FREQUENCY_PENALTY is 'Положительные значения увеличивают вероятность получения новой строки';
comment on column chat_gpt.message_settings.BEST_OF is 'Генерирует несколько значений на стороне сервера и возвращает лучший';
comment on column chat_gpt.message_settings.CREATED_AT is 'Дата создания записи';
comment on column chat_gpt.message_settings.UPDATED_AT is 'Дата обновления записи';


create table if not exists chat_gpt.message_inbox(
ID serial NOT NULL,
UUID uuid NOT NULL ,
VALUE varchar,
ID_SETTING integer,
CREATED_AT timestamp without time zone NOT NULL,
UPDATED_AT timestamp without time zone NOT NULL,

CONSTRAINT pk_message_inbox PRIMARY KEY (ID),
CONSTRAINT fk_message_inbox_message_settings FOREIGN KEY(ID_SETTING) REFERENCES chat_gpt.message_settings(ID),
CONSTRAINT unique_message_inbox_uuid  unique (UUID)
);

comment on table chat_gpt.message_inbox is 'Входящие сообщения';
comment on column chat_gpt.message_inbox.ID is 'Идентификатор сообщения';
comment on column chat_gpt.message_inbox.UUID is 'Уникальный идентификатор сообщения';
comment on column chat_gpt.message_inbox.VALUE is 'Значение';
comment on column chat_gpt.message_inbox.ID_SETTING is 'Настройки';
comment on column chat_gpt.message_inbox.CREATED_AT is 'Дата создания записи';
comment on column chat_gpt.message_inbox.UPDATED_AT is 'Дата обновления записи';

create table if not exists chat_gpt.message_outbox(
ID serial NOT NULL,
UUID uuid NOT NULL ,
VALUE varchar,
CREATED_AT timestamp without time zone NOT NULL,
UPDATED_AT timestamp without time zone NOT NULL,

CONSTRAINT pk_message_outbox PRIMARY KEY (ID),
CONSTRAINT unique_message_outbox_uuid  unique (UUID)
);
comment on table chat_gpt.message_outbox is 'Входящие сообщения';
comment on column chat_gpt.message_outbox.ID is 'Идентификатор сообщения';
comment on column chat_gpt.message_outbox.UUID is 'Уникальный идентификатор сообщения';
comment on column chat_gpt.message_outbox.VALUE is 'Значение';
comment on column chat_gpt.message_outbox.CREATED_AT is 'Дата создания записи';
comment on column chat_gpt.message_outbox.UPDATED_AT is 'Дата обновления записи';



