import React, {useEffect, useState} from 'react'
import {Calendar, Typography} from 'antd'
import dayjs, {Dayjs} from 'dayjs'
import Map from 'components/Map/Map'
import {Place} from 'shared/entities'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {useLocation} from 'react-router'

const TravelDetail = () => {
    const {pathname} = useLocation()
    console.log(pathname)

    const [way, setWay] = useState<any>([])
    const travel = useAppSelector(state => state.travel.data)

    const dispatch = useAppDispatch()

    useEffect(() => {
        // dispatch()
    }, [])

    const travels = [
        {
            date: '2023-07-16',
            attractions: [
                {
                    'id': 31,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'building',
                    'name': 'Морской Вокзал',
                    'address': 'Нижнепортовая улица, 1',
                    'lat': 43.1111941,
                    'lon': 131.88279728772915
                },
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
                    'id': 147,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Тигр',
                    'address': 'Тигровая улица',
                    'lat': 43.1151632,
                    'lon': 131.8779315
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
                },
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
                },
            ],
            types: [
                'car',
                'car',
                'foot',
                'foot',
                'foot'
            ]
        },
        {
            date: '2023-07-17',
            attractions: [
                {
                    'id': 31,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'building',
                    'name': 'Морской Вокзал',
                    'address': 'Нижнепортовая улица, 1',
                    'lat': 43.1111941,
                    'lon': 131.88279728772915
                },
                {
                    'id': 131,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Триумфальная арка',
                    'address': 'улица Петра Великого',
                    'lat': 43.1139186,
                    'lon': 131.8924323
                },
                {
                    'id': 150,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Музей города',
                    'address': 'улица Петра Великого',
                    'lat': 43.11368,
                    'lon': 131.8928335
                },
                {
                    'id': 134,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Вид на залив "Золотой рог"',
                    'address': 'улица Суханова',
                    'lat': 43.1175183,
                    'lon': 131.8982528
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
                },
            ],
            types: [
                'car',
                'foot',
                'foot',
                'foot',
                'car'
            ]
        },
        {
            date: '2023-07-19',
            attractions: [
                {
                    'id': 31,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'building',
                    'name': 'Морской Вокзал',
                    'address': 'Нижнепортовая улица, 1',
                    'lat': 43.1111941,
                    'lon': 131.88279728772915
                },
                {
                    'id': 135,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Военно-исторический музей Тихоокеанского флота',
                    'address': 'Светланская улица',
                    'lat': 43.1144566,
                    'lon': 131.9009151
                },
                {
                    'id': 149,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'вид на город',
                    'address': 'улица Металлистов',
                    'lat': 43.1163564,
                    'lon': 131.9141718
                },
                {
                    'id': 146,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Самурай',
                    'address': 'Дальзаводская улица',
                    'lat': 43.112373,
                    'lon': 131.9141648
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
            types: [
                'car',
                'foot',
                'foot',
                'car'
            ]
        },
        {
            date: '2023-07-21',
            attractions: [
                {
                    'id': 31,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'building',
                    'name': 'Морской Вокзал',
                    'address': 'Нижнепортовая улица, 1',
                    'lat': 43.1111941,
                    'lon': 131.88279728772915
                },
                {
                    'id': 133,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Вид на мост на о. Русский',
                    'address': 'Кипарисовая улица',
                    'lat': 43.0864702,
                    'lon': 131.9169909
                },
                {
                    'id': 159,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Вид на Русский мост',
                    'address': 'Русский мост',
                    'lat': 43.0677064,
                    'lon': 131.9159453
                },
                {
                    'id': 145,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Вид на Патрокл',
                    'address': 'п. Новый — Де-Фриз — Седанка — Патрокл',
                    'lat': 43.0794659,
                    'lon': 131.9645108
                },
                {
                    'id': 132,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Музей автомотостарины',
                    'address': 'Сахалинская улица',
                    'lat': 43.0969792,
                    'lon': 131.9659974
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
            types: [
                'car',
                'car',
                'car',
                'car',
                'car'
            ]
        },
        {
            date: '2023-07-24',
            attractions: [
                {
                    'id': 31,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'building',
                    'name': 'Морской Вокзал',
                    'address': 'Нижнепортовая улица, 1',
                    'lat': 43.1111941,
                    'lon': 131.88279728772915
                },
                {
                    'id': 139,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Приморская государственная картинная галерея',
                    'address': 'Партизанский проспект',
                    'lat': 43.1245336,
                    'lon': 131.8934406
                },
                {
                    'id': 140,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Вид на водохранилище',
                    'address': 'улица Семирадского',
                    'lat': 43.2121705,
                    'lon': 131.9767074
                },
                {
                    'id': 142,
                    'cityId': 1,
                    'imageId': null,
                    'type': 'tourism',
                    'name': 'Сосновый бор',
                    'address': 'улица Айвазовского',
                    'lat': 43.2133545,
                    'lon': 131.9920213
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
            types: [
                'car',
                'car',
                'foot',
                'car'
            ]
        }
    ]

    const getListData = (value: Dayjs) => {
        let listData
        listData = travels?.filter(day => dayjs(`${value.year()}-${value.month() + 1}-${value.date()}`).toString() === dayjs(day.date).toString())

        return listData[0]?.attractions || []
    }

    const dateCellRender = (value: Dayjs) => {
        const listData = getListData(value)

        return (
            <div className="events">
                {listData.map((item, index) => (
                    <li key={'item.id' + index}>
                        <Typography.Text
                            ellipsis
                        >{item.name ? item.name + ' (' + item.address + ')' : item.address}
                        </Typography.Text>
                    </li>
                ))}
            </div>
        )
    }

    fetch('http://api.weatherunlocked.com/api/forecast/43.10562,131.87353?lang=ru&app_id=574f72d9&app_key=cf87cfa8db059c6be1c5543dfd4d7f74')
        .then(res => res.json())
        .then(res => res.Days.forEach((day: any) => console.log(`
            day: ${day.date}
            temperature: ${day.temp_max_c}C
            rain: ${day.rain_total_mm}mm
            rain percent: ${day.prob_precip_pct}%
            humidity: ${day.humid_max_pct}%
            wind: ${day.windspd_max_kmh}km/h
        `)))

    console.log(way)

    return (
        <div style={{display: 'flex'}}>
            <div style={{width: '50%', padding: 16}}>
                <Typography.Title>{travel?.city.name}</Typography.Title>
                <Calendar
                    dateCellRender={dateCellRender}
                    // @ts-ignore
                    validRange={travel?.dates.map(date => dayjs(date))}
                    // validRange={[dayjs('2023-07-15'), dayjs('2023-07-29')]}
                    onSelect={date => setWay(getListData(date))}
                />
            </div>
            <div style={{width: '50%'}}>
                <div style={{height: window.innerHeight - 64, width: '100%'}}>
                    <Map
                        style={{width: '100%', height: '100%', position: 'relative'}}
                        center={[travel?.accommodation.lat || 63.3109245, travel?.accommodation.lon || 135]}
                        zoom={13}
                        accommodation={travel?.accommodation}
                        places={way}
                        routePoints={way}
                    />
                </div>
            </div>
        </div>
    )
}

export default TravelDetail