import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import axios from 'shared/api'
import {City, Place} from 'shared/entities'

export const createTravel = createAsyncThunk<any, any>(
    'travel/create',
    async (data) => await axios.post('/trips', data)
)

export const getTravel = createAsyncThunk<any, any>(
    'travel/get',
    async (id: number) => await axios.get(`trips/${id}`)
)

type Travel = {
    city: City
    accommodation: Place
    dates: string[]
    days: {places: Place[], date: string}[]
}

type State = {
    data: Travel | undefined
    city: number | undefined
    dates: any[]
    accommodation: Place | undefined
    attractions: Place[]
    isLoading: boolean
    error: string | undefined
}

const initialState: State = {
    data: undefined,
    city: undefined,
    dates: [],
    accommodation: undefined,
    attractions: [],
    isLoading: false,
    error: undefined
}

const travelSlice = createSlice({
    name: 'travel',
    initialState,
    reducers: {
        setCity(state, action) {
            state.city = action.payload
        },
        setAccommodation(state, action) {
            state.accommodation = action.payload
        },
        addPlace(state, action) {
            state.attractions.push(action.payload)
        },
        removePlace(state, action) {
            state.attractions = state.attractions.filter(attraction => attraction.id !== action.payload)
        },
        clearPlaces(state) {
            state.attractions = []
        }
    },
    extraReducers: builder => {
        builder
            .addCase(createTravel.pending, (state) => {
                state.isLoading = true
            })
            .addCase(createTravel.fulfilled, (state) => {
                state.isLoading = false
            })
            .addCase(createTravel.rejected, (state) => {
                state.isLoading = false
            })
            .addCase(getTravel.pending, (state) => {
                state.isLoading = true
            })
            .addCase(getTravel.fulfilled, (state, action) => {
                state.data = action.payload
                state.isLoading = false
            })
            .addCase(getTravel.rejected, (state) => {
                state.isLoading = false
            })
    }
})

export const {
    setCity,
    setAccommodation,
    addPlace,
    removePlace,
    clearPlaces
} = travelSlice.actions

export default travelSlice.reducer