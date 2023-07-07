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

export const getMyTravels = createAsyncThunk<any>(
    'travels/get',
    // @ts-ignore
    async () => await axios.get(`/users/${jwtDecode(getAccessToken()).sub}/trips?page=0&size=50&ascending=true&sortBy=id`)
        .then(response => response.data.data)
)

export const getTravel = createAsyncThunk<Travel, string>(
    'travel/get',
    async (travelId: string) => await axios.get(`/trips/${travelId}`)
        .then(response => response.data)
)

type Travel = {
    city: City
    accommodation: Place
    dates: string[]
    days: {
        date: string,
        places: Place[],
        ways: {
            type: 'car' | 'foot',
            points: [number, number][]
        }[]
    }[]
}

type State = {
    data: Travel | undefined
    selectedWay: number
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

    data: {
        city: {
            'id': 1,
            'imageId': 8,
            'name': 'Владивосток',
            'region': 'Приморский край',
            'lat': 43.1150678,
            'lon': 131.8855768
        },
        accommodation: {
            'id': 1050,
            'cityId': 1,
            'imageId': null,
            'type': 'hotel',
            'name': 'Версаль',
            'address': 'Светланская улица, 10',
            'lat': 43.1166993,
            'lon': 131.8797746
        },
        dates: ['2023-07-15', '2023-07-22'],
        days: [
            {
                date: '2023-07-16',
                places: [
                    {
                        'id': 157,
                        'cityId': 1,
                        'imageId': null,
                        'type': 'tourism',
                        'name': 'Музей трепанга',
                        'address': 'Верхнепортовая улица',
                        'lat': 43.1006922,
                        'lon': 131.8628391
                    },
                    {
                        'id': 143,
                        'cityId': 1,
                        'imageId': null,
                        'type': 'tourism',
                        'name': 'Скульптура Воспоминание о моряке загранплавания',
                        'address': 'улица Адмирала Фокина',
                        'lat': 43.1169684,
                        'lon': 131.8860766
                    }
                ],
                ways: [
                    {
                        type: 'car',
                        points: [
                            [43.1166993, 131.8797746],
                            [43.1006922, 131.8628391]
                        ]
                    },
                    {
                        type: 'foot',
                        points: [
                            [43.1006922, 131.8628391],
                            [43.1169684, 131.8860766]
                        ]
                    },
                    {
                        type: 'car',
                        points: [
                            [43.1169684, 131.8860766],
                            [43.1166993, 131.8797746]
                        ]
                    }
                ]
            },
            {
                date: '2023-07-17',
                places: [
                    {
                        'id': 136,
                        'cityId': 1,
                        'imageId': null,
                        'type': 'tourism',
                        'name': 'Музей им. Арсеньева',
                        'address': 'Светланская улица',
                        'lat': 43.1163613,
                        'lon': 131.8821714
                    },
                    {
                        'id': 31,
                        'cityId': 1,
                        'imageId': null,
                        'type': 'building',
                        'name': 'Морской Вокзал',
                        'address': 'Нижнепортовая улица, 1',
                        'lat': 43.1111941,
                        'lon': 131.88279728772915
                    }
                ],
                ways: [
                    {
                        type: 'car',
                        points: [
                            [43.1166993, 131.8797746],
                            [43.1163613, 131.8821714]
                        ]
                    },
                    {
                        type: 'foot',
                        points: [
                            [43.1163613, 131.8821714],
                            [43.1111941, 131.88279728772915]
                        ]
                    },
                    {
                        type: 'car',
                        points: [
                            [43.1111941, 131.88279728772915],
                            [43.1166993, 131.8797746]
                        ]
                    }
                ]
            }
        ]
    },
    selectedWay: 0,

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
        },
        selectWay(state, action) {
            state.selectedWay = action.payload
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
    clearPlaces,
    selectWay
} = travelSlice.actions

export default travelSlice.reducer