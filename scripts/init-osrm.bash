##!/bin/bash

set -e

source ../.env

docker volume create osrm
docker run \
  -e OSRM_GEOFABRIK_PATH="${OSM_REGION}-latest.osm.pbf" \
  -e OSRM_ALGORITHM="${OSRM_ALGORITHM}" \
  -e OSRM_MAP_NAME="${OSRM_CAR_MAP_NAME}" \
  -e OSRM_PROFILE="${OSRM_CAR_PROFILE}" \
  -v osrm:/data/ \
  -v ./osrm-entrypoint.bash:/home/osrm/entrypoint.sh \
  monogramm/docker-osrm-backend:5.24 \
  init
docker run \
  -e OSRM_GEOFABRIK_PATH="${OSM_REGION}-latest.osm.pbf" \
  -e OSRM_ALGORITHM="${OSRM_ALGORITHM}" \
  -e OSRM_MAP_NAME="${OSRM_FOOT_MAP_NAME}" \
  -e OSRM_PROFILE="${OSRM_FOOT_PROFILE}" \
  -v osrm:/data/ \
  -v ./osrm-entrypoint.bash:/home/osrm/entrypoint.sh \
  monogramm/docker-osrm-backend:5.24 \
  init
