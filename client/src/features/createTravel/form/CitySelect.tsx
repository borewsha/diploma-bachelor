import React, {useEffect, useMemo} from 'react'
import {searchingCities} from 'features/citiesSlice'
import {Select} from 'antd'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {setCity} from 'features/travelSlice'
import {setCenter, setZoom} from 'features/mapSlice'
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
        }, 1000), [])

    useEffect(() => {
        if (selectedCity) {
            dispatch(setCenter(getCityCoords(selectedCity)))
            dispatch(setZoom(13))
        }
    }, [selectedCity])

    return (
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
    )
}

export default CitySelect