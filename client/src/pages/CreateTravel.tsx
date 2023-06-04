import React, {useState} from 'react'
import {Button, DatePicker, Form, message, Select, Typography} from 'antd'
import Map from 'pages/Map'
import dayjs from 'dayjs'
import 'dayjs/locale/ru'
import {useAppDispatch, useAppSelector} from '../shared/hooks'
import {City, searchingCities} from 'features/city/citiesSlice'
import {addAttraction, Building, createTravel, removeAttraction, searchingBuildings} from 'features/buildingsSlice'

const CreateTravel = () => {
    const [cities, setCities] = useState<{ value: string, label: string }[]>([])
    const [buildings, setBuildings] = useState<any[]>([])
    const [center, setCenter] = useState<any>([43.1056, 131.874])
    const [city, setCity] = useState<any>('')
    const dispatch = useAppDispatch()

    const isLoading = useAppSelector(state => state.city.isLoading)
    const citiesStore = useAppSelector(state => state.city.data)
    const buildingsStore = useAppSelector(state => state.building.data)
    const attractions = useAppSelector(state => state.building.attractions)

    const citySearch = async (cityName: string) => {
        await dispatch(searchingCities(cityName))
            .unwrap()
            .then(res => res.map((city: City) =>
                ({
                    value: city.name,
                    label: city.name + ', ' + city.region
                })))
            .then(res => setCities(res))
    }

    const buildingSearch = async (city: string, building: string) => {
        await dispatch(searchingBuildings({city, building}))
            .unwrap()
            .then(res => setBuildings(res))
    }

    const onFinish = async (data: any) => {
        dispatch(createTravel(data))
    }

    const onFinishFailed = async () => {
        await message.error('Ошибка при заполнении формы!')
    }

    const getCityCoords = (cityName: string) => {
        const city = citiesStore.filter(city => city.name === cityName)[0]
        return [city.lat, city.lon]
    }

    const getBuildingCoords = (osmId: number) => {
        const building = buildingsStore.filter(building =>
            building.osmId === osmId)[0]
        return [building.lat, building.lon]
    }

    const getBuilding = (osmId: number) => {
        return buildings.filter(building => building.osmId === osmId)[0]
    }

    return (
        <div style={{display: 'flex'}}>
            <div style={{
                padding: 16,
                width: '50%',
                overflowY: 'auto',
                height: window.innerHeight - 64
            }}>
                <Typography.Title>Создать поездку</Typography.Title>
                <Form
                    name="createTravel"
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    layout='vertical'
                >
                    <Form.Item
                        name="city"
                        label="Город"
                    >
                        <Select
                            showSearch
                            placeholder="Начните вводить город..."
                            defaultActiveFirstOption={false}
                            showArrow={false}
                            filterOption={false}
                            onSearch={citySearch}
                            notFoundContent={null}
                            options={cities}
                            onSelect={(value) => {
                                setCenter(getCityCoords(value))
                                setCity(value)
                            }}
                        />
                    </Form.Item>
                    <Form.Item
                        name="dates"
                        label="Даты поездки"
                    >
                        <DatePicker.RangePicker
                            style={{width: '100%'}}
                            placeholder={['Начало', 'Конец']}
                            disabledDate={(date) => date < dayjs(new Date()).subtract(1, 'day')}
                        />
                    </Form.Item>
                    <Form.Item
                        name="overnightStay"
                        label="Место ночлега"
                    >
                        <Select
                            showSearch
                            placeholder="Начните вводить место..."
                            defaultActiveFirstOption={false}
                            showArrow={false}
                            filterOption={false}
                            onSearch={building => buildingSearch(city, building)}
                            notFoundContent={null}
                            options={(buildings || []).map((building: Building) => ({
                                value: building.osmId,
                                label: building.name ? building.name + ' (' + building.address + ')' : building.address})
                            )}
                            onSelect={value => setCenter(getBuildingCoords(value))}
                        />
                    </Form.Item>
                    <Form.Item
                        name="attractions"
                        label="Места для посещения"
                    >
                        <Select
                            showSearch
                            mode="multiple"
                            placeholder="Начните вводить место..."
                            defaultActiveFirstOption={false}
                            showArrow={false}
                            filterOption={false}
                            onSearch={building => buildingSearch(city, building)}
                            notFoundContent={null}
                            options={(buildings || []).map((building: Building) => ({
                                value: building.osmId,
                                label: building.name ? building.name + ' (' + building.address + ')' : building.address})
                            )}
                            onSelect={value => {
                                setCenter(getBuildingCoords(value))
                                dispatch(addAttraction(getBuilding(value)))
                            }}
                            onDeselect={value => {
                                dispatch(removeAttraction(value))
                            }}
                        />
                    </Form.Item>
                    <Button
                        type="primary"
                        style={{width: '100%', margin: '10px 0'}}
                        htmlType="submit"
                        loading={isLoading}
                    >Создать</Button>
                </Form>
            </div>

            <div style={{height: window.innerHeight - 64, width: '100%'}}>
                <Map center={center} attractions={attractions}/>
            </div>
        </div>
    )
}

export default CreateTravel