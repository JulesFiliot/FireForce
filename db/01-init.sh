#!/bin/bash
set -e
export PGPASSWORD=$POSTGRES_PASSWORD;
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE DATABASE "$APP_DB_NAME" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';
  ALTER DATABASE "$APP_DB_NAME" OWNER TO "$POSTGRES_USER";
  \connect -reuse-previous=on "dbname='$APP_DB_NAME'"
  CREATE EXTENSION postgis;
  COMMIT;
EOSQL