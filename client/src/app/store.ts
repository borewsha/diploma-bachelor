import {configureStore} from '@reduxjs/toolkit'
import authSlice from 'features/auth/authSlice'
import citySlice from 'features/city/citiesSlice'
import buildSlice from 'features/buildingsSlice'

const store = configureStore({
    reducer: {
        auth: authSlice,
        city: citySlice,
        building: buildSlice
    }
})

export default store

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch