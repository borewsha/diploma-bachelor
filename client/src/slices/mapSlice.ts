import {createSlice} from '@reduxjs/toolkit'
import {LatLngExpression} from 'leaflet'
import {Place} from 'shared/entities'

type MapState = {
    center: LatLngExpression
    zoom: number
    places: Place[]
}

const initialState: MapState = {
    center: [63.3109245, 135],
    zoom: 4,
    places: []
}

const mapSlice = createSlice({
    name: 'map',
    initialState,
    reducers: {
        setCenter(state, action) {
            state.center = action.payload
        },
        setZoom(state, action) {
            state.zoom = action.payload
        }
    }
})

export const {
    setCenter,
    setZoom
} = mapSlice.actions

export default mapSlice.reducer
