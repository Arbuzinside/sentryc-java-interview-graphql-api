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

# Generate random data and append to SQL file
for i in {1..1000}; do
    # Generate random data for producers
    producer_id=$(generate_uuid)
    producer_name="Producer_$i"
    created_at=$(date +'%Y-%m-%d %H:%M:%S')

    # Append to SQL file
    echo "INSERT INTO producers (id, name, created_at) VALUES ('$producer_id', '$producer_name', '$created_at');" >> $TEMP_SQL_FILE

    # Generate random data for marketplaces
    marketplace_id=$(generate_uuid)
    marketplace_description="Description for Marketplace $i"

    # Append to SQL file
    echo "INSERT INTO marketplaces (id, description) VALUES ('$marketplace_id', '$marketplace_description');" >> $TEMP_SQL_FILE

    # Generate random data for seller_infos
    seller_info_id=$(generate_uuid)
    seller_info_name="SellerInfo_$i"
    seller_info_url="http://example.com/seller/$i"
    seller_info_country="Country_$i"
    seller_info_external_id="External_$i"

    # Append to SQL file
    echo "INSERT INTO seller_infos (id, marketplace_id, name, url, country, external_id) VALUES ('$seller_info_id', '$marketplace_id', '$seller_info_name', '$seller_info_url', '$seller_info_country', '$seller_info_external_id');" >> $TEMP_SQL_FILE

    # Generate random data for sellers
    seller_id=$(generate_uuid)
    seller_state="REGULAR"

    # Append to SQL file
    echo "INSERT INTO sellers (id, producer_id, seller_info_id, state) VALUES ('$seller_id', '$producer_id', '$seller_info_id', '$seller_state');" >> $TEMP_SQL_FILE
done

# Execute all insert commands in a single docker exec call
docker cp $TEMP_SQL_FILE $CONTAINER_NAME:/temp_insert.sql
docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -f /temp_insert.sql

# Clean up
rm $TEMP_SQL_FILE
docker exec -i $CONTAINER_NAME rm /temp_insert.sql

echo "Database populated with 1000 entries of random data."
