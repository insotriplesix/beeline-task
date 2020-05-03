CREATE TABLE IF NOT EXISTS sessions (
    cell_id varchar(9) NOT NULL,
    ctn varchar(15) NOT NULL
);

INSERT INTO sessions VALUES
    ('11111', '1234567890'),
    ('11111', '1234567891'),
    ('11111', '1234567898'),
    ('22222', '9876543210'),
    ('22222', '9876543211');