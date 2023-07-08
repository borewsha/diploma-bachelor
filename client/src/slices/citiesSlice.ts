import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import axios from 'shared/api'
import {City} from 'shared/entities'

export const searchingCities = createAsyncThunk<City[], string>(
    'cities/search',
    async (city: string) =>
        await axios.get(`/cities?page=0&size=10&search=${city}`)
            .then(response => (response.data.data) as City[])
)

export const getCitiesAdm = createAsyncThunk<any, { page: number, city: string }>(
    'cities/adm',
    async ({page, city}) =>
        await axios.get(`/cities?page=${page}&size=10&search=${city}`)
            .then(response => response.data)
)

type State = {
    data: City[]
    dataAdm: {
        size: number,
        current: number,
        data: City[]
    }
    isLoading: boolean
    error: string | undefined
}

const initialState: State = {
    data: [],
    dataAdm: {
        size: 0,
        current: 0,
        data: []
    },
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
            .addCase(getCitiesAdm.pending, (state) => {
                state.isLoading = true
            })
            .addCase(getCitiesAdm.fulfilled, (state, action) => {
                state.isLoading = false
                state.dataAdm = {
                    size: action.payload.totalElements,
                    current: action.payload.page,
                    data: action.payload.data
                }
            })
            .addCase(getCitiesAdm.rejected, (state) => {
                state.isLoading = false
            })
    }
})

export default citySlice.reducer