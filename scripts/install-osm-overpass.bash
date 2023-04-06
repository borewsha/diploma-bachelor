##!/bin/bash

set -e

docker volume create osm-overpass
docker run \
  -e OVERPASS_MODE=init \
  -е OVERPASS_META=yes \
  -е OVERPASS_PLANET_URL=http://download.geofabrik.de/russia/far-eastern-fed-district-latest.osm.bz2 \
  -е OVERPASS_UPDATE_SLEEP=3600 \
  -е OVERPASS_USE_AREAS=true \
  -е OVERPASS_STOP_AFTER_INIT=true \
  -v osm-overpass:/db/ \
  wiktorn/overpass-api:0.7.56.9