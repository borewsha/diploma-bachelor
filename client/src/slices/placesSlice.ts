import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import axios from 'shared/api'
import {Place} from 'shared/entities'


export const buildingsSearch = createAsyncThunk<Place[], { cityId: number, place: string }>(
    'places/buildings/search',
    async ({place, cityId}) =>
        await axios.get(`/places/buildings?page=0&size=30&cityId=${cityId}&search=${place}`)
            .then(response => (response.data.data) as Place[])
)

export const tourismSearch = createAsyncThunk<Place[], { cityId: number, tourism: string }>(
    'places/tourism/search',
    async ({tourism, cityId}) =>
        await axios.get(`/places/tourism?cityId=${cityId}&search=${tourism}`)
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
            .addCase(buildingsSearch.pending, (state) => {
                state.isLoading = true
            })
            .addCase(buildingsSearch.fulfilled, (state, action) => {
                state.isLoading = false
                state.buildings = action.payload
            })
            .addCase(buildingsSearch.rejected, (state) => {
                state.isLoading = false
            })
            .addCase(tourismSearch.pending, (state) => {
                state.isLoading = true
            })
            .addCase(tourismSearch.fulfilled, (state, action) => {
                state.isLoading = false
                state.tourism = action.payload
            })
            .addCase(tourismSearch.rejected, (state) => {
                state.isLoading = false
            })
    }
})

export default placesSlice.reducer