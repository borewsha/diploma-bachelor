import React, {CSSProperties, FC} from 'react'
import {MapContainer, TileLayer} from 'react-leaflet'
import {Place} from 'shared/entities'
import AccommodationPlaceMarker from './AccommodationPlace'
import PlacesClusterGroups from './PlacesClusterGroups'
import {LatLngExpression} from 'leaflet'
import UpdatePosition from './UpdatePosition'
import Routing from './Routing'
import 'leaflet/dist/leaflet.css'

type MapProps = {
    center?: LatLngExpression
    zoom?: number
    style?: CSSProperties
    accommodation?: Place
    places?: Place[]
    routePoints?: Place[]
}

const Map: FC<MapProps> = ({
                               center = [63.3109245, 135],
                               zoom = 4,
                               style,
                               accommodation,
                               places,
                               routePoints
                           }) => {
    return (
        <MapContainer
            center={center}
            zoom={zoom}
            style={style}
            attributionControl={false}
        >
            <TileLayer url="http://localhost:8081/tile/{z}/{x}/{y}.png"/>
            {accommodation && <AccommodationPlaceMarker accommodation={accommodation}/>}
            {places && <PlacesClusterGroups places={places}/>}
            {routePoints && <Routing points={routePoints}/>}
            <UpdatePosition center={center} zoom={zoom}/>
        </MapContainer>
    )
}

export default Map