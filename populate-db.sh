#!/bin/bash

# Docker container name
CONTAINER_NAME="postgres-P6zZ"

# Database connection details
DB_NAME="sentryc_interview"
DB_USER="postgres"

# Generate a random UUID
generate_uuid() {
    uuidgen
}

# Generate random data and insert into database
for i in {1..1000}; do
    # Generate random data for producers
    producer_id=$(generate_uuid)
    producer_name="Producer_$i"
    created_at=$(date +'%Y-%m-%d %H:%M:%S')

    # Insert into producers table
    docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -c "INSERT INTO public.producers (id, name, created_at) VALUES ('$producer_id', '$producer_name', '$created_at');"

    # Generate random data for marketplaces
    marketplace_id=$(generate_uuid)
    marketplace_description="Description for Marketplace $i"

    # Insert into marketplaces table
    docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -c "INSERT INTO public.marketplaces (id, description) VALUES ('$marketplace_id', '$marketplace_description');"

    # Generate random data for seller_infos
    seller_info_id=$(generate_uuid)
    seller_info_name="SellerInfo_$i"
    seller_info_url="http://example.com/seller/$i"
    seller_info_country="Country_$i"
    seller_info_external_id="External_$i"

    # Insert into seller_infos table
    docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -c "INSERT INTO public.seller_infos (id, marketplace_id, name, url, country, external_id) VALUES ('$seller_info_id', '$marketplace_id', '$seller_info_name', '$seller_info_url', '$seller_info_country', '$seller_info_external_id');"

    # Generate random data for sellers
    seller_id=$(generate_uuid)
    seller_state="REGULAR"

    # Insert into sellers table
    docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -c "INSERT INTO public.sellers (id, producer_id, seller_info_id, state) VALUES ('$seller_id', '$producer_id', '$seller_info_id', '$seller_state');"
done

echo "Database populated with 1000 entries of random data."
