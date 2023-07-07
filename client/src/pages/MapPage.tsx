import React from 'react'
import Map from 'components/Map/Map'

const MapPage = () => {
    return (
        <div style={{height: window.innerHeight - 64, width: '100%'}}>
            <Map style={{width: '100%', height: '100%', position: 'relative'}} routePoints={[]}/>
        </div>
    )
}

export default MapPage