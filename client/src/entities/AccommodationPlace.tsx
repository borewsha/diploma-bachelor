import React, {FC} from 'react'
import {accommodationPlaceIcon} from './accommodationPlaceIcon'
import {Marker, Popup} from 'react-leaflet'
import {Place} from '../shared/entities'

type AccommodationPlaceMarkerState = {
    accommodation: Place
}

const AccommodationPlaceMarker: FC<AccommodationPlaceMarkerState> = ({accommodation}) => {
    return (
        <Marker
            position={[accommodation.lat, accommodation.lon]}
            icon={accommodationPlaceIcon}
        >
            <Popup>{
                accommodation.name && accommodation.address
                    ? accommodation.name + ' (' + accommodation.address + ')'
                    : accommodation.address
            }</Popup>
        </Marker>
    )
}

export default AccommodationPlaceMarker