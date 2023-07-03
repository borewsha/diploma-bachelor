import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import axios from 'shared/api'
import {Place} from 'shared/entities'


export const getAccommodationPlace = createAsyncThunk<Place[], { cityId: number, place: string, placeTypes: string[] }>(
    'accommodation',
    async ({cityId, place, placeTypes}) =>
        await axios.get(`/cities/${cityId}/places?page=0&size=50&search=${place}&type=${placeTypes.join(',')}`)
            .then(response => (response.data.data) as Place[])
)

export const getAttractionPlaces = createAsyncThunk<Place[], { cityId: number, place: string, placeTypes: string[] }>(
    'places/tourism/search',
    async ({place, cityId, placeTypes}) =>
        await axios.get(`/cities/${cityId}/places?page=0&size=50&search=${place}&type=${placeTypes.join(',')}`)
            .then(response => (response.data.data) as Place[])
)

type State = {
    buildings: Place[]
    tourism: Place[]
    isLoading: boolean
    error: string | undefined
}

const initialState: State = {
    buildings: [],
    tourism: [],
    isLoading: false,
    error: undefined
}

const placesSlice = createSlice({
    name: 'places',
    initialState,
    reducers: {},
    extraReducers: builder => {
        builder
            .addCase(getAccommodationPlace.pending, (state) => {
                state.isLoading = true
            })
            .addCase(getAccommodationPlace.fulfilled, (state, action) => {
                state.isLoading = false
                state.buildings = action.payload
            })
            .addCase(getAccommodationPlace.rejected, (state) => {
                state.isLoading = false
            })
            .addCase(getAttractionPlaces.pending, (state) => {
                state.isLoading = true
            })
            .addCase(getAttractionPlaces.fulfilled, (state, action) => {
                state.isLoading = false
                state.tourism = action.payload
            })
            .addCase(getAttractionPlaces.rejected, (state) => {
                state.isLoading = false
            })
    }
})

export default placesSlice.reducer