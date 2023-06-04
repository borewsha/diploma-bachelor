import React, {FC} from 'react'
import {MapContainer, Marker, Popup, TileLayer, useMap} from 'react-leaflet'
import 'leaflet/dist/leaflet.css'
import {Icon, LatLngExpression} from 'leaflet'
import {Building} from 'features/buildingsSlice'
import MarkerClusterGroup from 'react-leaflet-cluster'

interface MapProps {
    width?: number
    height?: number
    center?: LatLngExpression
    attractions?: Building[]
}

const Map: FC<MapProps> = ({
                               width,
                               height,
                               center = [43.1056, 131.874],
                               attractions = []
                           }) => {

    // @ts-ignore
    function SetView({center}) {
        const map = useMap()
        map.setView(center)
        return null
    }

    const customIcon = new Icon({
        iconUrl: require('shared/placeholder.png'),
        iconSize: [32, 32]
    })

    return (
        <>
            <MapContainer
                style={{width: '100%', height: '100%', position: 'relative'}}
                center={center}
                zoom={13}
                attributionControl={false}
            >
                <TileLayer
                    url="http://localhost:8081/tile/{z}/{x}/{y}.png"
                />
                <Marker
                    position={center}
                    icon={customIcon}
                >
                </Marker>
                <MarkerClusterGroup>
                    {
                        attractions?.map(attraction => (<Marker
                            key={attraction.osmId}
                            position={[attraction.lat, attraction.lon]}
                            icon={customIcon}
                        >
                            <Popup>{attraction.name && attraction.address
                                ? attraction.name + ' (' + attraction.address + ')'
                                : attraction.address}</Popup>
                        </Marker>))
                    }
                </MarkerClusterGroup>
                <SetView center={center}/>
            </MapContainer>
        </>
    )
}

export default Map