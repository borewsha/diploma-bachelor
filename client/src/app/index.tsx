import 'antd/dist/reset.css'
import Routing from 'pages'
import React, {FC} from 'react'
import {Provider} from 'react-redux'
import store from './store'

// import React, {useEffect, useState} from 'react'
// import {MapContainer, TileLayer} from 'react-leaflet'
// import Routing from '../Routering'
// import 'leaflet/dist/leaflet.css'

const App: FC = () => {
    return (
        <Provider store={store}>
            <Routing/>
        </Provider>
    )
}

export default App
