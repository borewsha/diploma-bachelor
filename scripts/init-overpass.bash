##!/bin/bash

set -e

source ../.env

docker volume create osm-overpass
docker run \
  -e OVERPASS_MODE="${OVERPASS_MODE}" \
  -e OVERPASS_META="${OVERPASS_META}" \
  -e OVERPASS_PLANET_URL="http://download.geofabrik.de/${OSM_REGION}-latest.osm.bz2" \
  -e OVERPASS_UPDATE_SLEEP="${OVERPASS_UPDATE_SLEEP}" \
  -e OVERPASS_USE_AREAS="${OVERPASS_USE_AREAS}" \
  -e OVERPASS_STOP_AFTER_INIT=true \
  -v osm-overpass:/db/ \
  wiktorn/overpass-api:0.7.56.9