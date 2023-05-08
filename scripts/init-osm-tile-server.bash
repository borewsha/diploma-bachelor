#!/bin/bash

set -e

source ../.env

docker volume create osm-database
docker volume create osm-tiles
docker run \
  -e DOWNLOAD_PBF="https://download.geofabrik.de/${REGION}-latest.osm.pbf" \
  -e DOWNLOAD_POLY="https://download.geofabrik.de/${REGION}.poly" \
  -v osm-database:/data/database/ \
  -v osm-tiles:/data/tiles/ \
  overv/openstreetmap-tile-server:2.3.0 \
  import
