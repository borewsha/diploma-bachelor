import React, {useEffect, useMemo, useState} from 'react'
import {Place, PlaceTypes} from 'shared/entities'
import {addPlace, removePlace} from 'slices/travelSlice'
import {Checkbox, Form, Select} from 'antd'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {getAttractionPlaces} from 'slices/placesSlice'
import {setCenter} from 'slices/mapSlice'
import {LatLngExpression} from 'leaflet'
import debounce from 'lodash.debounce'

const AttractionsSelect = () => {
    const dispatch = useAppDispatch()
    const tourism = useAppSelector(state => state.places.tourism)
    const attractions = useAppSelector(state => state.travel.attractions)
    const city = useAppSelector(state => state.travel.city)
    const cities = useAppSelector(state => state.city.data)
    const isLoading = useAppSelector(state => state.places.isLoading)

    const [onlyAttractions, setOnlyAttractions] = useState(false)

    const attractionSearch = useMemo(
        () => debounce(async (cityId: number, place: string) => {
            let placeTypes = [PlaceTypes.cinema, PlaceTypes.theatre, PlaceTypes.monument, PlaceTypes.attraction, PlaceTypes.museum, PlaceTypes.viewpoint]
            if (onlyAttractions) {
                placeTypes = [PlaceTypes.monument, PlaceTypes.attraction, PlaceTypes.museum, PlaceTypes.viewpoint]
            }
            await dispatch(getAttractionPlaces({cityId, place, placeTypes}))
        }, 1000), [])

    const getPlaceByOsmId = (id: number) => {
        return tourism.filter(attraction => attraction.id === id)[0] as Place
    }

    const getPlaceCoords = (place: Place) => {
        return [place.lat, place.lon] as LatLngExpression
    }

    useEffect(() => {
        if (attractions.length > 0) {
            // @ts-ignore
            dispatch(setCenter(getPlaceCoords(attractions.at(-1))))
        }
    }, [attractions])

    return (<>
        {/*// @ts-ignore*/}
        <Checkbox value={onlyAttractions} onClick={(e) => setOnlyAttractions(!e.target.value)} style={{marginBottom: 8}}>Показывать только достопримечательности</Checkbox>
        <Form.Item
            name="attractions"
            label="Места для посещения"
        >
            <Select
                showSearch
                mode="multiple"
                placeholder="Начните вводить место..."
                defaultActiveFirstOption={false}
                loading={isLoading}
                showArrow={false}
                filterOption={false}
                // @ts-ignore
                onSearch={attraction => attractionSearch(city, attraction)}
                notFoundContent={null}
                options={(tourism || []).map((tourism: Place) => ({
                        value: tourism.id,
                        label: tourism.name ? tourism.name + ' (' + tourism.address + ')' : tourism.address
                    })
                )}
                onSelect={value => dispatch(addPlace(getPlaceByOsmId(value)))}
                onDeselect={value => dispatch(removePlace(value))}
                disabled={!cities?.filter(c => c.id === city)[0]}
            />
        </Form.Item>
    </>)
}

export default AttractionsSelect