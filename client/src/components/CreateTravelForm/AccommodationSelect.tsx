import React, {useEffect, useMemo, useState} from 'react'
import {Place, PlaceTypes} from 'shared/entities'
import {Checkbox, Form, Select} from 'antd'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {getAccommodationPlace} from 'slices/placesSlice'
import {setCenter, setZoom} from 'slices/mapSlice'
import {LatLngExpression} from 'leaflet'
import {setAccommodation} from 'slices/travelSlice'
import debounce from 'lodash.debounce'

const AccommodationSelect = () => {
    const dispatch = useAppDispatch()

    const places = useAppSelector(state => state.places.buildings)
    const city = useAppSelector(state => state.travel.city)
    const accommodation = useAppSelector(state => state.travel.accommodation)
    const cities = useAppSelector(state => state.city.data)
    const isLoading = useAppSelector(state => state.places.isLoading)

    const [onlyHotels, setOnlyHotels] = useState(false)

    const buildingSearch = useMemo(
        () => debounce(async (cityId: number, place: string) => {
            let placeTypes = [PlaceTypes.house, PlaceTypes.hotel]
            if (onlyHotels) {
                placeTypes = [PlaceTypes.hotel]
            }
            await dispatch(getAccommodationPlace({cityId, place, placeTypes}))
        }, 1000), [onlyHotels])

    const getPlaceGeo = (id: number) => {
        const place = places.filter(place => place.id === id)[0]
        return [place.lat, place.lon] as LatLngExpression
    }

    const getPlaceByOsmId = (id: number) => {
        return places.filter(place => place.id === id)[0] as Place
    }

    useEffect(() => {
        if (city) {
            buildingSearch(city, '')
        }
    }, [city, onlyHotels])

    useEffect(() => {
        if (accommodation) {
            // @ts-ignore
            dispatch(setCenter(getPlaceGeo(accommodation.id)))
            dispatch(setZoom(16))
        }
    }, [accommodation])


    return (<>
        <Checkbox
            checked={onlyHotels}
            // @ts-ignore
            onClick={(e) => setOnlyHotels(!onlyHotels)}
            style={{marginBottom: 8}}>Показывать только отели</Checkbox>
        <Form.Item
            name="accommodation"
            label="Ночлег"
        >
            <Select
                showSearch
                placeholder="Начните вводить название или улицу..."
                defaultActiveFirstOption={false}
                loading={isLoading}
                filterOption={false}
                // @ts-ignore
                onSearch={building => buildingSearch(city, building)}
                notFoundContent={null}
                options={(places || []).map((place: Place) => ({
                        value: place.id,
                        label: place.name ? place.name + ' (' + place.address + ')' : place.address
                    })
                )}
                onSelect={value => dispatch(setAccommodation(getPlaceByOsmId(value)))}
                disabled={!cities?.filter(c => c.id === city)[0]}
            />
        </Form.Item>
    </>)
}

export default AccommodationSelect