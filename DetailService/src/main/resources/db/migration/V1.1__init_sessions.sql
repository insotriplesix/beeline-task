-- Init 'sessions' table
-- Note: The maximum lengths for 'cell_id' and 'ctn' based on these articles:
--   'cell_id': https://en.wikipedia.org/wiki/GSM_Cell_ID
--   'ctn': https://en.wikipedia.org/wiki/Telephone_numbering_plan

CREATE TABLE IF NOT EXISTS ${schema}.sessions (
    cell_id varchar(9) NOT NULL,
    ctn varchar(15) NOT NULL
);

-- Insert test data

INSERT INTO ${schema}.sessions VALUES
    ('11111', '1234567890'),
    ('11111', '1234567891'),
    ('11111', '1234567892'),
    ('11111', '1234567893'),
    ('11111', '1234567894'),
    ('11111', '1234567895'),
    ('11111', '1234567896'),
    ('11111', '1234567897'),
    ('11111', '1234567898'),
    ('22222', '9876543210'),
    ('22222', '9876543211'),
    ('22222', '9876543212'),
    ('22222', '9876543213'),
    ('22222', '9876543214'),
    ('22222', '9876543215'),
    ('22222', '9876543216'),
    ('22222', '9876543217'),
    ('22222', '9876543218');