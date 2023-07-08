import React, {useEffect, useMemo} from 'react'
import {searchingCities} from 'slices/citiesSlice'
import {Form, Select} from 'antd'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {setCity} from 'slices/travelSlice'
import {setCenter, setZoom} from 'slices/mapSlice'
import {LatLngExpression} from 'leaflet'
import debounce from 'lodash.debounce'
import {City} from 'shared/entities'

const CitySelect = () => {
    const dispatch = useAppDispatch()

    const cities = useAppSelector(state => state.city.data)
    const selectedCity = useAppSelector(state => state.travel.city)
    const isLoading = useAppSelector(state => state.city.isLoading)

    const getCityCoords = (cityId: number) => {
        const city = cities.filter(city => city.id === cityId)[0]
        return [city.lat, city.lon] as LatLngExpression
    }

    const citySearch = useMemo(
        () => debounce(async (cityName: string) => {
            await dispatch(searchingCities(cityName))
        }, 500), [])

    useEffect(() => {
        citySearch('')
    }, [])

    useEffect(() => {
        if (selectedCity) {
            dispatch(setCenter(getCityCoords(selectedCity)))
            dispatch(setZoom(13))
        }
    }, [selectedCity])

    return (
        <Form.Item
            name="city"
            label="Город"
        >
            <Select
                showSearch
                placeholder="Начните вводить город..."
                defaultActiveFirstOption={false}
                filterOption={false}
                onSearch={citySearch}
                notFoundContent={null}
                options={(cities || []).map((city: City) => ({
                        value: city.id,
                        label: city.name + ', ' + city.region
                    })
                )}
                loading={isLoading}
                onSelect={value => dispatch(setCity(value))}
            />
        </Form.Item>
    )
}

export default CitySelect