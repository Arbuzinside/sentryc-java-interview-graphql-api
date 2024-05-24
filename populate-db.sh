#!/bin/bash

# Docker container name
CONTAINER_NAME="postgres"

# Database connection details
DB_NAME="sentryc_interview"
DB_USER="postgres"

# Temporary SQL file
TEMP_SQL_FILE=$(mktemp)

# Generate a random UUID
generate_uuid() {
    uuidgen
}

# Generate random state
generate_random_state() {
    states=("REGULAR" "WHITELISTED" "GREYLISTED" "BLACKLISTED")
    echo "${states[$RANDOM % ${#states[@]}]}"
}

# Generate random data and append to SQL file

# Generate 50 marketplaces
for i in {1..50}; do
    marketplace_id=$(generate_uuid)
    marketplace_description="Description for Marketplace $i"
    echo "INSERT INTO marketplaces (id, description) VALUES ('$marketplace_id', '$marketplace_description');" >> $TEMP_SQL_FILE
done

# Generate 200 seller_infos
for i in {1..200}; do
    seller_info_id=$(generate_uuid)
    marketplace_id=$(shuf -i 1-50 -n 1) # Randomly assign one of the 50 marketplaces
    seller_info_name="SellerInfo_$i"
    seller_info_url="http://example.com/seller/$i"
    seller_info_country="Country_$i"
    seller_info_external_id="External_$i"
    echo "INSERT INTO seller_infos (id, marketplace_id, name, url, country, external_id) VALUES ('$seller_info_id', (SELECT id FROM marketplaces OFFSET $((marketplace_id-1)) LIMIT 1), '$seller_info_name', '$seller_info_url', '$seller_info_country', '$seller_info_external_id');" >> $TEMP_SQL_FILE
done

# Generate 200 producers
for i in {1..200}; do
    producer_id=$(generate_uuid)
    producer_name="Producer_$i"
    created_at=$(date +'%Y-%m-%d %H:%M:%S')
    echo "INSERT INTO producers (id, name, created_at) VALUES ('$producer_id', '$producer_name', '$created_at');" >> $TEMP_SQL_FILE
done

# Generate 1000 sellers
for i in {1..1000}; do
    seller_id=$(generate_uuid)
    producer_id=$(shuf -i 1-200 -n 1) # Randomly assign one of the 500 producers
    seller_info_id=$(shuf -i 1-200 -n 1) # Randomly assign one of the 200 seller_infos
    seller_state=$(generate_random_state)
    echo "INSERT INTO sellers (id, producer_id, seller_info_id, state) VALUES ('$seller_id', (SELECT id FROM producers OFFSET $((producer_id-1)) LIMIT 1), (SELECT id FROM seller_infos OFFSET $((seller_info_id-1)) LIMIT 1), '$seller_state');" >> $TEMP_SQL_FILE
done

# Execute all insert commands in a single docker exec call
docker cp $TEMP_SQL_FILE $CONTAINER_NAME:/temp_insert.sql
docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -f /temp_insert.sql

# Clean up
rm $TEMP_SQL_FILE
docker exec -i $CONTAINER_NAME rm /temp_insert.sql

echo "Database populated with random data."
