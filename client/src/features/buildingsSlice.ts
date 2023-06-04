import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import axios from 'shared/api'

export type Building = {
    id: number,
    imageId: number,
    osmId: number,
    name: string,
    address: string,
    lat: number,
    lon: number
}

export const searchingBuildings = createAsyncThunk<Building[], { city: string, building: string }>(
    'buildings/search',
    async ({building, city}) =>
        await axios.get(`/places/buildings?page=0&size=10&city=${city}&search=${building}`)
            .then(response => (response.data.data) as Building[])
)

export const createTravel = createAsyncThunk<any, any>(
    'trip/create',
    async (data) =>
        await axios.post('/trips', data)
)

type State = {
    data: Building[]
    attractions: Building[]
    isLoading: boolean
    error: string | undefined
}

const initialState: State = {
    data: [],
    attractions: [],
    isLoading: false,
    error: undefined
}

const buildingSlice = createSlice({
    name: 'building',
    initialState,
    reducers: {
        addAttraction(state, action) {
            state.attractions.push(action.payload)
        },
        removeAttraction(state, action) {
            state.attractions = state.attractions.filter(building => building.osmId !== action.payload)
        }
    },
    extraReducers: builder => {
        builder
            .addCase(searchingBuildings.pending, (state) => {
                state.isLoading = true
            })
            .addCase(searchingBuildings.fulfilled, (state, action) => {
                state.isLoading = false
                state.data = action.payload
            })
            .addCase(searchingBuildings.rejected, (state) => {
                state.isLoading = false
            })
    }
})

export const {addAttraction, removeAttraction} = buildingSlice.actions

export default buildingSlice.reducer