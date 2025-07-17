CREATE TABLE IF NOT EXISTS response_log(
    id_response_log SERIAL PRIMARY KEY,
    origin_ip VARCHAR(45) NOT NULL,
    request_date TIMESTAMP NOT NULL,
    request_duration BIGINT NOT NULL,
    executed_method VARCHAR NOT NULL,
    request TEXT,
    response TEXT
)