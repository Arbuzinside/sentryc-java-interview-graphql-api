-- Insert data
INSERT INTO producers (id, name, created_at) VALUES
('123e4567-e89b-12d3-a456-426614174001', 'Producer A', '2023-01-01 00:00:00');

INSERT INTO producers (id, name, created_at) VALUES
('123e4567-e89b-12d3-a456-426614174002', 'Producer B', '2023-01-01 00:00:00');

INSERT INTO marketplaces (id, description) VALUES
('mkt-001', 'Marketplace A');

INSERT INTO seller_infos (id, marketplace_id, name, url, country, external_id) VALUES
('123e4567-e89b-12d3-a456-426614174002', 'mkt-001', 'Seller A', 'http://seller-a.com', 'Country A', 'ext-001');

INSERT INTO seller_infos (id, marketplace_id, name, url, country, external_id) VALUES
('123e4567-e89b-12d3-a456-426614174024', 'mkt-001', 'Seller B', 'http://seller-a.com', 'Country A', 'ext-002');

INSERT INTO sellers (id, producer_id, seller_info_id, state) VALUES
('123e4567-e89b-12d3-a456-426614174003', '123e4567-e89b-12d3-a456-426614174001', '123e4567-e89b-12d3-a456-426614174002', 'REGULAR');

INSERT INTO sellers (id, producer_id, seller_info_id, state) VALUES
('123e4567-e89b-12d3-a456-426614174004', '123e4567-e89b-12d3-a456-426614174002', '123e4567-e89b-12d3-a456-426614174002', 'BLACKLISTED');

INSERT INTO sellers (id, producer_id, seller_info_id, state) VALUES
('123e4567-e89b-12d3-a456-426614174005', '123e4567-e89b-12d3-a456-426614174001', '123e4567-e89b-12d3-a456-426614174024', 'REGULAR');