import React, {useEffect} from 'react'
import {Place} from 'shared/entities'
import {Select} from 'antd'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {buildingsSearch} from '../../placesSlice'
import {setCenter, setZoom} from '../../mapSlice'
import {LatLngExpression} from 'leaflet'
import {setAccommodation} from '../../travelSlice'

const AccommodationSelect = () => {
    const dispatch = useAppDispatch()

    const places = useAppSelector(state => state.places.buildings)
    const city = useAppSelector(state => state.travel.city)
    const accommodation = useAppSelector(state => state.travel.accommodation)
    const cities = useAppSelector(state => state.city.data)

    const buildingSearch = async (city: string, place: string) => {
        await dispatch(buildingsSearch({city, place}))
    }

    const getPlaceGeo = (osmId: string) => {
        const place = places.filter(place => place.osmId === osmId)[0]
        return [place.lat, place.lon] as LatLngExpression
    }

    const getPlaceByOsmId = (osmId: string) => {
        return places.filter(place => place.osmId === osmId)[0] as Place
    }

    useEffect(() => {
        if (accommodation) {
            dispatch(setCenter(getPlaceGeo(accommodation.osmId)))
            dispatch(setZoom(16))
        }
    }, [accommodation])

    return (
        <Select
            showSearch
            placeholder="Начните вводить место..."
            defaultActiveFirstOption={false}
            showArrow={false}
            filterOption={false}
            onSearch={building => buildingSearch(city, building)}
            notFoundContent={null}
            options={(places || []).map((place: Place) => ({
                    value: place.osmId,
                    label: place.name ? place.name + ' (' + place.address + ')' : place.address
                })
            )}
            onSelect={value => dispatch(setAccommodation(getPlaceByOsmId(value)))}
            disabled={!cities?.filter(c => c.name === city)[0]}
        />
    )
}

export default AccommodationSelect