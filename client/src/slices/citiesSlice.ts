import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import axios from 'shared/api'
import {City} from 'shared/entities'

export const searchingCities = createAsyncThunk<City[], string>(
    'cities/search',
    async (city: string) =>
        await axios.get(`/cities?page=0&size=10&search=${city}`)
            .then(response => (response.data.data) as City[])
)

type State = {
    data: City[]
    isLoading: boolean
    error: string | undefined
}

const initialState: State = {
    data: [],
    isLoading: false,
    error: undefined
}

const citySlice = createSlice({
    name: 'city',
    initialState,
    reducers: {},
    extraReducers: builder => {
        builder
            .addCase(searchingCities.pending, (state) => {
                state.isLoading = true
            })
            .addCase(searchingCities.fulfilled, (state, action) => {
                state.isLoading = false
                state.data = action.payload
            })
            .addCase(searchingCities.rejected, (state) => {
                state.isLoading = false
            })
    }
})

export default citySlice.reducer