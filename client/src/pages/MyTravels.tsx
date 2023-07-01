import React, {useEffect} from 'react'
import {Card, Col, Row, Typography} from 'antd'
import {Link} from 'react-router-dom'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {getMyTravels} from 'slices/travelSlice'

const MyTravels = () => {
    const dispatch = useAppDispatch()

    const formatter = new Intl.DateTimeFormat('ru', {
        month: 'long',
        day: 'numeric'
    })

    const travelsData = [
        {
            id: 1,
            cityName: 'Владивосток',
            dates: ['2023-07-15', '2023-07-29'],
            dateEnd: '2023-06-28',
            img: 'https://lh5.googleusercontent.com/p/AF1QipNOMu2SBYxdt7OAGhtyX_TQIOKrFoXHVOT95DcJ=w450-h240-k-no'
        },
        {
            id: 2,
            cityName: 'Хабаровск',
            dates: ['2023-08-10', '2023-08-16'],
            dateEnd: '2023-07-13',
            img: 'https://lh5.googleusercontent.com/p/AF1QipN_fIIiU6skZaHqcEt4Hk5oxSJIH7GuInVZI6-c=w408-h306-k-no'
        },
        {
            id: 3,
            cityName: 'Комсомольск-на-Амуре',
            dates: ['2023-08-20', '2023-08-30'],
            dateEnd: '2023-07-15',
            img: 'https://lh5.googleusercontent.com/p/AF1QipO3WFZ-wcWPtgLOaKTgF6VSRYYhps__bGnef4eR=w408-h306-k-no'
        }
    ]

    useEffect(() => {
        dispatch(getMyTravels(undefined))
            .unwrap()
            .then(res => console.log(res))
    }, [])

    const trips = useAppSelector(state => state.travel.myTravels)

    return (
        <div style={{padding: 16, display: 'flex', alignItems: 'center', justifyContent: 'center', overflowY: 'auto'}}>
            <div style={{maxWidth: 1400, width: '100%'}}>
                <Typography.Title>Мои путешествия</Typography.Title>
                <Row gutter={[16, 16]}>
                    {
                        !!travelsData && travelsData?.map(data =>
                            <Col
                                key={data.id}
                                xs={{span: 24}}
                                sm={{span: 12}}
                                md={{span: 8}}
                                lg={{span: 6}}
                            >
                                <Link to={`/home/travel/${data.id}`}>
                                <Card
                                    bordered
                                    hoverable
                                    cover={<img style={{objectFit: 'cover', height: 250}} src={data.img}
                                                alt={data.cityName}/>}
                                >
                                    <Card.Meta
                                        title={data.cityName}
                                        description={`${formatter.format(Date.parse(data.dates[0]))} - ${formatter.format(Date.parse(data.dates[1]))}`}
                                    />
                                </Card>
                                </Link>
                            </Col>
                        )
                    }
                    {
                        !travelsData.length && <h2>Путешествий еще нет...</h2>
                    }
                </Row>
            </div>
        </div>
    )
}

export default MyTravels