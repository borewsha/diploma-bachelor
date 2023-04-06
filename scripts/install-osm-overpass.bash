##!/bin/bash

set -e

docker volume create osm-overpass
docker run \
  -e OVERPASS_MODE=init \
  -e OVERPASS_META=yes \
  -e OVERPASS_PLANET_URL=http://download.geofabrik.de/russia/far-eastern-fed-district-latest.osm.bz2 \
  -e OVERPASS_UPDATE_SLEEP=3600 \
  -e OVERPASS_USE_AREAS=true \
  -e OVERPASS_STOP_AFTER_INIT=true \
  -v osm-overpass:/db/ \
  wiktorn/overpass-api:0.7.56.9