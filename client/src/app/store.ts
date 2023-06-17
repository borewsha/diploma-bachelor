import {configureStore} from '@reduxjs/toolkit'
import authSlice from 'features/auth/authSlice'
import citySlice from 'features/citiesSlice'
import placesSlice from 'features/placesSlice'
import mapSlice from 'features/mapSlice'
import travelSlice from 'features/travelSlice'

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