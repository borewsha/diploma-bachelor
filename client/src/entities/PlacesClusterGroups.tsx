import React, {FC} from 'react'
import {Marker, Popup} from 'react-leaflet'
import {defaultPlaceIcon} from './defaultPlaceIcon'
import MarkerClusterGroup from 'react-leaflet-cluster'
import {Place} from 'shared/entities'

type PlacesClusterGroupsState = {
    places: Place[]
}

const PlacesClusterGroups: FC<PlacesClusterGroupsState> = ({places}) => {
    return (
        <MarkerClusterGroup>
            {
                places?.map(place => (
                    <Marker
                        key={place.osmId}
                        position={[place.lat, place.lon]}
                        icon={defaultPlaceIcon}
                    >
                        <Popup>{place.name && place.address
                            ? place.name + ' (' + place.address + ')'
                            : place.address}</Popup>
                    </Marker>)
                )
            }
        </MarkerClusterGroup>
    )
}

export default PlacesClusterGroups