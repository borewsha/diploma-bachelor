import React from 'react'
import Map from 'components/Map/Map'

const MapPage = () => {
    return (
        <div style={{height: window.innerHeight - 64, width: '100%'}}>
            <Map style={{width: '100%', height: '100%', position: 'relative'}} routePoints={[{
                'id': 131,
                'cityId': 1,
                'imageId': null,
                'type': 'tourism',
                'name': 'Триумфальная арка',
                'address': 'улица Петра Великого',
                'lat': 43.1139186,
                'lon': 131.8924323
            }]}/>
        </div>
    )
}

export default MapPage