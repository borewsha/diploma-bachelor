import React from 'react'
import Map from 'entities/Map'
import {useAppSelector} from 'shared/hooks'

const CreateTravelMap = () => {
    const center = useAppSelector(state => state.map.center)
    const attractions = useAppSelector(state => state.travel.attractions)
    const zoom = useAppSelector(state => state.map.zoom)
    const accommodation = useAppSelector(state => state.travel.accommodation)

    return (
        <Map
            style={{width: '100%', height: '100%', position: 'relative'}}
            center={center}
            places={attractions}
            zoom={zoom}
            accommodation={accommodation}
        />
    )
}

export default CreateTravelMap