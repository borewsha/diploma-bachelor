import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import axios from 'shared/api'
import {City, Place} from 'shared/entities'
import dayjs from 'dayjs'

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
    city: string
    dates: any[]
    accommodation: Place | undefined
    attractions: Place[]
    isLoading: boolean
    error: string | undefined
}

const initialState: State = {
    data: {
        city: {
            'id': null,
            'imageId': null,
            'osmId': 'N27503886',
            'name': 'Владивосток',
            'region': 'Приморский край',
            'lat': 43.1150678,
            'lon': 131.8855768
        },
        accommodation: {
            'id': null,
            'imageId': null,
            'osmId': 'W23946365',
            'name': 'Океан',
            'address': 'Набережная улица, 3',
            'lat': 43.1164693,
            'lon': 131.87809636345293
        },
        dates: [dayjs().toString(), dayjs().add(7, 'days').toString()],
        days: [
            {
                date: dayjs().toString(),
                places: [
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W24164985',
                        'name': 'Морской Вокзал',
                        'address': 'Нижнепортовая улица, 1',
                        'lat': 43.1111941,
                        'lon': 131.88279728772915
                    },
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W26537156',
                        'name': 'Приморский драматический театр имени Максима Горького',
                        'address': 'Светланская улица, 49',
                        'lat': 43.11596115,
                        'lon': 131.89348644999998
                    },
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W26686828',
                        'name': 'Цирк',
                        'address': 'Светланская улица, 103',
                        'lat': 43.11610975,
                        'lon': 131.90617035277555
                    }
                ]
            },
            {
                date: dayjs().add(1, 'days').toString(),
                places: [
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W26826883',
                        'name': 'FESCO Hall',
                        'address': 'Верхнепортовая улица, 38',
                        'lat': 43.1050231,
                        'lon': 131.87141404034367
                    }
                ]
            },
            {
                date: dayjs().add(3, 'days').toString(),
                places: [
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W27014618',
                        'name': 'штаб Тихоокеанского флота',
                        'address': 'Корабельная набережная, 4',
                        'lat': 43.1138517,
                        'lon': 131.8898365461204
                    },
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W27152012',
                        'name': 'Серая Лошадь',
                        'address': 'Алеутская улица, 19',
                        'lat': 43.1155876,
                        'lon': 131.8816336579614
                    },
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W27152418',
                        'name': null,
                        'address': 'улица Калинина, 57',
                        'lat': 43.10227265,
                        'lon': 131.90802555989063
                    }
                ]
            },
            {
                date: dayjs().add(7, 'days').toString(),
                places: [
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W27152446',
                        'name': null,
                        'address': 'Харьковская улица, 10',
                        'lat': 43.101844650000004,
                        'lon': 131.9034274102836
                    },
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W27293140',
                        'name': null,
                        'address': 'Харьковская улица, 1',
                        'lat': 43.0983755,
                        'lon': 131.91101715347332
                    },
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W27419772',
                        'name': null,
                        'address': 'Харьковская улица, 3',
                        'lat': 43.0994255,
                        'lon': 131.90796416063557
                    },
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W27420114',
                        'name': null,
                        'address': 'Светланская улица, 37',
                        'lat': 43.115326350000004,
                        'lon': 131.88888823113516
                    },
                    {
                        'id': null,
                        'imageId': null,
                        'osmId': 'W27784256',
                        'name': null,
                        'address': 'Океанский проспект, 1',
                        'lat': 43.114147700000004,
                        'lon': 131.88524043838095
                    }
                ]
            }
        ]
    },
    city: '',
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
            state.attractions = state.attractions.filter(attraction => attraction.osmId !== action.payload)
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
    removePlace
} = travelSlice.actions

export default travelSlice.reducer