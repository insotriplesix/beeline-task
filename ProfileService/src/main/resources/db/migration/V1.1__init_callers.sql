-- Init 'callers' table
-- Note: The maximum lengths for 'ctn' and 'caller_id' based on these articles:
--   'ctn': https://en.wikipedia.org/wiki/Telephone_numbering_plan
--   'caller_id': (based on UUID) https://en.wikipedia.org/wiki/Universally_unique_identifier

CREATE TABLE IF NOT EXISTS ${schema}.callers (
    ctn varchar(15) NOT NULL,
    caller_id varchar(36) NOT NULL
);

-- Insert test data

INSERT INTO ${schema}.callers VALUES
    ('1234567890', '03e17537-30de-4598-a816-108945fa68b4'),
    ('1234567891', '3ad35c07-12ff-42de-a36d-e4cc21e9c500'),
    ('1234567892', '37ebfda5-acc4-44f9-9060-37bd023bd426'),
    ('1234567893', '9b69aeb5-ced5-4bf0-ba77-ee547288bb15'),
    ('1234567894', 'e95cf4fd-a206-4152-b062-bac14262cf5e'),
    ('1234567895', '6aa54ad1-184d-467f-9a9f-d4b433965c84'),
    ('1234567896', 'cc8b08fb-2af8-409f-a38f-ed2435600e43'),
    ('1234567897', 'f2cd50f8-176d-4426-9f55-b69c1346fc4f'),
    ('1234567898', '6ea48872-53ed-4590-8a35-5d2af49f8211'),
    ('9876543210', '26da9893-8753-4335-8f2e-d760e13ede27'),
    ('9876543211', '2a1d1242-c760-45f7-adc6-11d1bd388d0b'),
    ('9876543212', '2203e828-8489-4709-a95b-5e77aa6ba8fa'),
    ('9876543213', 'b6bd7e82-4e8e-4bc4-b882-7e0f39c4ff22'),
    ('9876543214', '4baecf23-a42d-4d76-b007-89f1a6c1885c'),
    ('9876543215', '60c2ead5-b906-479b-846b-ab4836da22aa'),
    ('9876543216', 'a32ca277-7c06-47e9-8b13-f69953d84cb2'),
    ('9876543217', '38a9c261-0df1-4273-9030-2bd5cf18ae85'),
    ('9876543218', '86bffd42-4a6b-490d-9c66-fa1d54b15500');