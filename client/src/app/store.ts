import {configureStore} from '@reduxjs/toolkit'
import authSlice from 'slices/authSlice'
import citySlice from 'slices/citiesSlice'
import placesSlice from 'slices/placesSlice'
import mapSlice from 'slices/mapSlice'
import travelSlice from 'slices/travelSlice'

const store = configureStore({
    reducer: {
        auth: authSlice,
        city: citySlice,
        places: placesSlice,
        map: mapSlice,
        travel: travelSlice
    }
})

export default store

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch