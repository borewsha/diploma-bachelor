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

// import React, { useState, useEffect } from 'react'
// import { MapContainer, TileLayer } from "react-leaflet";
// import "./App.css";
// import "leaflet/dist/leaflet.css";
// import TextField from '@material-ui/core/TextField';
// import Autocomplete from '@material-ui/lab/Autocomplete';
// import citiesData from './data.json';
// import Routing from "./Routing";
// import Paper from "@material-ui/core/Paper";
// import { makeStyles } from "@material-ui/core/styles";

// const useStyles = makeStyles(theme => ({
//     inputRoot: {
//         color: "yellow",
//         "& .MuiOutlinedInput-notchedOutline": {
//             borderColor: "white"
//         },
//         "&:hover .MuiOutlinedInput-notchedOutline": {
//             borderColor: "white"
//         },
//         "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
//             borderColor: "white"
//         }
//     },
// }));

// export function App() {
//     const position = [20.593683, 78.962883];
//
//     const [cities, setCities] = useState([]);
//     const [sourceCity, setSourceCity] = useState({});
//     const [destinationCity, setDestinationCity] = useState({});
//
//
//     return (
//         <div className="leaflet-container">
//             <MapContainer center={[57.74, 57]} zoom={6} style={{height: '100vh', width: '100%', position: 'relative'}}>
//                 <TileLayer
//                     attribution='&copy; <a href="https://stadiamaps.com/">Stadia Maps</a>, &copy; <a href="https://openmaptiles.org/">OpenMapTiles</a> &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors'
//                     url="http://localhost:8081/tile/{z}/{x}/{y}.png"
//                 />
//                 <Routing sourceCity={{
//                     'city': 'Tokyo',
//                     'city_ascii': 'Tokyo',
//                     'lat': '57.6897',
//                     'lng': '57.6922',
//                     'country': 'Japan',
//                     'iso2': 'JP',
//                     'iso3': 'JPN',
//                     'admin_name': 'Tōkyō',
//                     'capital': 'primary',
//                     'population': '37977000',
//                     'id': '1392685764'
//                 }} destinationCity={{
//                     'city': 'Delhi',
//                     'city_ascii': 'Delhi',
//                     'lat': '28.66',
//                     'lng': '77.23',
//                     'country': 'India',
//                     'iso2': 'IN',
//                     'iso3': 'IND',
//                     'admin_name': 'Delhi',
//                     'capital': 'admin',
//                     'population': '29617000',
//                     'id': '1356872604'
//                 }}/>
//             </MapContainer>
//         </div>
//     );
// }

export default App
