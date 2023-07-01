import React, {useEffect, useMemo} from 'react'
import {Place} from 'shared/entities'
import {Checkbox, Form, Select} from 'antd'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {buildingsSearch} from 'slices/placesSlice'
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

    const buildingSearch = useMemo(
        () => debounce(async (city: number, place: string) => {
            await dispatch(buildingsSearch({cityId: city, place}))
        }, 1000), [])

    const getPlaceGeo = (id: number) => {
        const place = places.filter(place => place.id === id)[0]
        return [place.lat, place.lon] as LatLngExpression
    }

    const getPlaceByOsmId = (id: number) => {
        return places.filter(place => place.id === id)[0] as Place
    }

    useEffect(() => {
        if (accommodation) {
            // @ts-ignore
            dispatch(setCenter(getPlaceGeo(accommodation.id)))
            dispatch(setZoom(16))
        }
    }, [accommodation])

    return (<>
        <Checkbox style={{marginBottom: 8}}>Показывать только отели</Checkbox>
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