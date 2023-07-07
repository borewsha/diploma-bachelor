import React, {CSSProperties, FC} from 'react'
import {MapContainer, Polyline, TileLayer} from 'react-leaflet'
import {Place} from 'shared/entities'
import AccommodationPlaceMarker from './AccommodationPlace'
import PlacesClusterGroups from './PlacesClusterGroups'
import {LatLngExpression} from 'leaflet'
import UpdatePosition from './UpdatePosition'
import Routing from './Routing'
import 'leaflet/dist/leaflet.css'
import {useAppSelector} from 'shared/hooks'

type MapProps = {
    center?: LatLngExpression
    zoom?: number
    style?: CSSProperties
    accommodation?: Place
    places?: Place[]
    routePoints?: {
        type: 'car' | 'foot',
        points: [number, number][]
    }[]
}

const Map: FC<MapProps> = ({
                               center = [63.3109245, 135],
                               zoom = 4,
                               style,
                               accommodation,
                               places,
                               routePoints
                           }) => {
    places = places?.filter(place => place.id !== accommodation?.id)
    const selectedWay = useAppSelector(state => state.travel.selectedWay)

    return (
        <MapContainer
            center={center}
            zoom={zoom}
            style={style}
            attributionControl={false}
        >
            <TileLayer url="http://localhost:8081/tile/{z}/{x}/{y}.png"/>
            {!!accommodation && <AccommodationPlaceMarker accommodation={accommodation}/>}
            {!!places?.length && <PlacesClusterGroups places={places}/>}
            {!!routePoints?.length && <Routing points={routePoints}/>}
            <UpdatePosition center={center} zoom={zoom}/>
            {
                !!routePoints && routePoints.map((way, i) => {
                    if (way.type === 'car') {
                        return <Polyline
                            key={way.points[0][0]}
                            positions={way.points}
                            pathOptions={{color: 'red', weight: i === selectedWay ? 7 : 3, opacity: 0.7}}
                        />
                    }
                    if (way.type === 'foot') {
                        return <Polyline
                            key={way.points[0][0]}
                            positions={way.points}
                            pathOptions={{color: 'blue', weight: i === selectedWay ? 7 : 3, opacity: 0.7}}
                        />
                    }
                    return null
                })
            }
        </MapContainer>
    )
}

export default Map