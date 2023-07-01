import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import axios from 'shared/api'
import {City, Place} from 'shared/entities'
import {getAccessToken} from 'shared/helpers/jwt'
import jwtDecode from 'jwt-decode'

export const createTravel = createAsyncThunk<any, any>(
    'travel/create',
    async (data) => await axios.post('/trips', {
        cityId: data.city,
        // @ts-ignore
        userId: jwtDecode(getAccessToken()).sub,
        accommodationId: data.accommodation,
        dates: data.dates,
        attractions: data.attractions
    })
)

export const getMyTravels = createAsyncThunk<any, any>(
    'travels/get',
    // @ts-ignore
    async () => await axios.get(`/users/${jwtDecode(getAccessToken()).sub}/trips?page=0&size=50&ascending=true&sortBy=id`)
        .then(response => response.data.data)
)

export const getTravel = createAsyncThunk<any, any>(
    'travel/get',
    async (travelId: number) => await axios.get(`/trips/${travelId}`)
        .then(response => response.data)
)

type Travel = {
    city: City
    accommodation: Place
    dates: string[]
    days: { places: Place[], date: string }[]
}

type State = {
    data: Travel | undefined
    myTravels: {
        'id': number,
        'userId': number,
        'cityId': number,
        'accommodationId': number,
        'cityImageId': number,
        'cityName': string,
        'dates': string[]
    }[] | [],
    currentTravel: {
        id: number,
        userId: number,
        city: City,
        accommodation: Place,
        attractions: Place[]
    } | undefined,
    city: number | undefined
    dates: any[]
    accommodation: Place | undefined
    attractions: Place[]
    isLoading: boolean
    error: string | undefined
}

const initialState: State = {
    data: undefined,
    myTravels: [],
    currentTravel: undefined,
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
            .addCase(getMyTravels.pending, (state) => {
                state.isLoading = true
            })
            .addCase(getMyTravels.fulfilled, (state, action) => {
                state.myTravels = action.payload
                state.isLoading = false
            })
            .addCase(getMyTravels.rejected, (state) => {
                state.isLoading = false
            })
            .addCase(getTravel.pending, (state) => {
                state.isLoading = true
            })
            .addCase(getTravel.fulfilled, (state, action) => {
                state.currentTravel = action.payload
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