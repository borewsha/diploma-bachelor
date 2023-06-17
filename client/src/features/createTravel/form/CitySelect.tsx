import React, {useEffect} from 'react'
import {City, searchingCities} from '../../citiesSlice'
import {Select} from 'antd'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {setCity} from '../../travelSlice'
import {setCenter, setZoom} from '../../mapSlice'
import {LatLngExpression} from 'leaflet'

const CitySelect = () => {
    const dispatch = useAppDispatch()

    const cities = useAppSelector(state => state.city.data)
    const selectedCity = useAppSelector(state => state.travel.city)

    const getCityCoords = (cityName: string) => {
        const city = cities.filter(city => city.name === cityName)[0]
        return [city.lat, city.lon] as LatLngExpression
    }

    const citySearch = async (cityName: string) => {
        await dispatch(searchingCities(cityName))
    }

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
            showArrow={false}
            filterOption={false}
            onSearch={citySearch}
            notFoundContent={null}
            options={(cities || []).map((city: City) => ({
                    value: city.name,
                    label: city.name + ', ' + city.region
                })
            )}
            onSelect={value => dispatch(setCity(value))}
        />
    )
}

export default CitySelect