DROP schema chat_gpt cascade;
CREATE SCHEMA IF NOT EXISTS chat_gpt;

create table if not exists chat_gpt.dict_urls_open_ai(
                                                          ID serial NOT NULL,
                                                          NAME varchar NOT NULL,
                                                          SNAME varchar,
                                                          VALUE varchar NOT NULL,
                                                          DESCRIPTION  varchar NOT NULL,
                                                          CREATED_AT timestamp without time zone NOT NULL,
                                                          UPDATED_AT timestamp without time zone NOT NULL,
                                                          CONSTRAINT pk_dict_urls_open_ai PRIMARY KEY (ID)
);
comment on table chat_gpt.dict_urls_open_ai is 'Словарь ссылок api open ai';
comment on column chat_gpt.dict_urls_open_ai.NAME is 'Наименование записи';
comment on column chat_gpt.dict_urls_open_ai.SNAME is 'Сокращённое наименование';
comment on column chat_gpt.dict_urls_open_ai.VALUE is 'Значение';
comment on column chat_gpt.dict_urls_open_ai.DESCRIPTION is 'Описание';
comment on column chat_gpt.dict_urls_open_ai.CREATED_AT is 'Дата создания записи';
comment on column chat_gpt.dict_urls_open_ai.UPDATED_AT is 'Дата обновления записи';


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

create table if not exists chat_gpt.message_settings(
                                                        ID SERIAL NOT NULL,
                                                        ID_MODEL INTEGER NOT NULL,
                                                        ID_URLS INTEGER NOT NULL,
                                                        PROMPT VARCHAR NOT NULL,

                                                        CREATED_AT timestamp without time zone NOT NULL,
                                                        UPDATED_AT timestamp without time zone NOT NULL,

                                                        CONSTRAINT pk_message_settings PRIMARY KEY (ID),
                                                        CONSTRAINT fk_message_settings_inbox_dict_open_ai FOREIGN KEY(ID_MODEL) REFERENCES chat_gpt.dict_model_open_ai(id)
);
comment on table chat_gpt.message_settings is 'Настройки запроса к open ai';
comment on column chat_gpt.message_settings.ID is 'Идентификатор сообщения';
comment on column chat_gpt.message_settings.ID_MODEL is 'Идентификатор модели';
comment on column chat_gpt.message_settings.ID_URLS is 'Идентификатор ссылки';
comment on column chat_gpt.message_settings.PROMPT is 'Запрос';
comment on column chat_gpt.message_settings.CREATED_AT is 'Дата создания записи';
comment on column chat_gpt.message_settings.UPDATED_AT is 'Дата обновления записи';

create table if not exists chat_gpt.message_tokens_setting(
                                                                  ID serial NOT NULL,
                                                                  SETTING_ID integer NOT NULL,
                                                                  TOKENS INTEGER NOT NULL DEFAULT 16,

                                                                  CONSTRAINT fk_message_tokens_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID)
);
comment on table chat_gpt.message_tokens_setting is 'Токены';
comment on column chat_gpt.message_tokens_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_tokens_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_tokens_setting.TOKENS is 'Токены.';


create table if not exists chat_gpt.message_logit_bias_setting(
                                                               ID serial NOT NULL,
                                                               SETTING_ID integer NOT NULL,
                                                               LOGIT_BIAS JSON NOT NULL,

                                                               CONSTRAINT fk_message_logit_bias_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID)
);
comment on table chat_gpt.message_logit_bias_setting is 'Вероятность появления указанных токенов в завершении';
comment on column chat_gpt.message_logit_bias_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_logit_bias_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_logit_bias_setting.LOGIT_BIAS is 'Вероятность появления указанных токенов в завершении.';


create table if not exists chat_gpt.message_best_of_setting(
                                                                         ID serial NOT NULL,
                                                                         SETTING_ID integer NOT NULL,
                                                                         BEST_OF INTEGER DEFAULT 1 NOT NULL,

                                                                         CONSTRAINT fk_message_best_of_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID)
);
comment on table chat_gpt.message_best_of_setting is 'Последовательности остановки';
comment on column chat_gpt.message_best_of_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_best_of_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_best_of_setting.BEST_OF is 'Генерирует несколько значений на стороне сервера и возвращает лучший';


create table if not exists chat_gpt.message_frequency_penalty_setting(
                                                                        ID serial NOT NULL,
                                                                        SETTING_ID integer NOT NULL,
                                                                        FREQUENCY_PENALTY DOUBLE PRECISION DEFAULT 0 NOT NULL,

                                                                        CONSTRAINT fk_message_presence_penalty_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID),
                                                                        CONSTRAINT max_min_check_frequency_penalty CHECK ( FREQUENCY_PENALTY >= -2.0 and FREQUENCY_PENALTY <= 2.0)
);
comment on table chat_gpt.message_frequency_penalty_setting is 'Последовательности остановки';
comment on column chat_gpt.message_frequency_penalty_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_frequency_penalty_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_frequency_penalty_setting.FREQUENCY_PENALTY is 'Положительные значения увеличивают вероятность получения новой строки';

create table if not exists chat_gpt.message_presence_penalty_setting(
                                                            ID serial NOT NULL,
                                                            SETTING_ID integer NOT NULL,
                                                            PRESENCE_PENALTY DOUBLE PRECISION DEFAULT 0,
                                                            CONSTRAINT fk_message_presence_penalty_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID),
                                                            CONSTRAINT max_min_check_presence_penalty CHECK ( PRESENCE_PENALTY >= -2.0 and PRESENCE_PENALTY <= 2.0)
                                                                    );
comment on table chat_gpt.message_presence_penalty_setting is 'Последовательности остановки';
comment on column chat_gpt.message_presence_penalty_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_presence_penalty_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_presence_penalty_setting.PRESENCE_PENALTY is 'Положительные значения увеличивает вероятность появления новых токенов';

create table if not exists chat_gpt.message_stop_setting(
                                                                ID serial NOT NULL,
                                                                SETTING_ID integer NOT NULL,
                                                                STOP VARCHAR[] DEFAULT NULL,
                                                                CONSTRAINT fk_message_stop_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID)

);
comment on table chat_gpt.message_stop_setting is 'Последовательности остановки';
comment on column chat_gpt.message_stop_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_stop_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_stop_setting.STOP is 'Последовательности остановки';


create table if not exists chat_gpt.message_logprobs_setting(
                                                             ID serial NOT NULL,
                                                             SETTING_ID integer NOT NULL,
                                                             LOGPROBS INTEGER DEFAULT NULL,
                                                             CONSTRAINT fk_message_logprobs_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID),

                                                             CONSTRAINT max_min_check_logprobs CHECK ( LOGPROBS >= 0 and LOGPROBS <= 5)
                                                            );
comment on table chat_gpt.message_logprobs_setting is 'Количество верояных генераций токенов';
comment on column chat_gpt.message_logprobs_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_logprobs_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_logprobs_setting.LOGPROBS is 'Количество верояных генераций токенов';

create table if not exists chat_gpt.message_n_setting(
                                                         ID serial NOT NULL,
                                                         SETTING_ID integer NOT NULL,
                                                         N INTEGER DEFAULT 1,
                                                         CONSTRAINT fk_message_n_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID)

);
comment on table chat_gpt.message_n_setting is 'Количество генераций';
comment on column chat_gpt.message_n_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_n_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_n_setting.N is 'Количество генераций';


create table if not exists chat_gpt.message_top_p_setting(
                                                                   ID serial NOT NULL,
                                                                   SETTING_ID integer NOT NULL,
                                                                   TOP_P DOUBLE PRECISION NOT NULL DEFAULT 1,
                                                                   CONSTRAINT fk_message_top_p_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID)

);
comment on table chat_gpt.message_top_p_setting is 'Альтернатива выборке с температурой, называемая выборкой ядра, где модель рассматривает результаты токенов с вероятностной массой top_p';
comment on column chat_gpt.message_top_p_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_top_p_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_top_p_setting.TOP_P is 'Альтернатива выборке с температурой, называемая выборкой ядра, где модель рассматривает результаты токенов с вероятностной массой top_p';



create table if not exists chat_gpt.message_temperature_setting(
                                                              ID serial NOT NULL,
                                                              SETTING_ID integer NOT NULL,
                                                              TEMPERATURE INTEGER NOT NULL DEFAULT 1,
                                                              CONSTRAINT fk_message_temperature_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID)

);
comment on table chat_gpt.message_temperature_setting is 'Температура выборки';
comment on column chat_gpt.message_temperature_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_temperature_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_temperature_setting.TEMPERATURE is 'Температура выборки';

create table if not exists chat_gpt.message_suffix_setting(
                                                              ID serial NOT NULL,
                                                              SETTING_ID integer NOT NULL,
                                                              SUFFIX varchar,
                                                              CONSTRAINT fk_message_suffix_message_settings FOREIGN KEY(SETTING_ID) REFERENCES chat_gpt.message_settings(ID)

);
comment on table chat_gpt.message_suffix_setting is 'Суффиксы следующие за вставленным текстом';
comment on column chat_gpt.message_suffix_setting.ID is 'Идентификатор';
comment on column chat_gpt.message_suffix_setting.SETTING_ID is 'Идентификатор настройки';
comment on column chat_gpt.message_suffix_setting.SUFFIX is 'Суффиксы следующие за вставленным текстом';


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



