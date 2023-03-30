#!/bin/bash

set -e

docker volume create osm-database
docker volume create osm-tiles
docker run \
  -e DOWNLOAD_PBF=https://download.geofabrik.de/russia/far-eastern-fed-district-latest.osm.pbf \
  -e DOWNLOAD_POLY=https://download.geofabrik.de/russia/far-eastern-fed-district.poly \
  -v osm-database:/data/database/ \
  -v osm-tiles:/data/tiles/ \
  overv/openstreetmap-tile-server \
  import
