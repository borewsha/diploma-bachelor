import React from 'react'
import {Card, Col, Row, Typography} from 'antd'

const MyTravels = () => {
    const travelsData = [
        {
            id: 1,
            city: 'Владивосток',
            dateStart: '2023-06-13',
            dateEnd: '2023-06-28',
            img: 'https://lh5.googleusercontent.com/p/AF1QipNOMu2SBYxdt7OAGhtyX_TQIOKrFoXHVOT95DcJ=w450-h240-k-no'
        },
        {
            id: 2,
            city: 'Хабаровск',
            dateStart: '2023-06-29',
            dateEnd: '2023-07-13',
            img: 'https://lh5.googleusercontent.com/p/AF1QipN_fIIiU6skZaHqcEt4Hk5oxSJIH7GuInVZI6-c=w408-h306-k-no'
        },
        {
            id: 3,
            city: 'Комсомольск-на-Амуре',
            dateStart: '2023-07-14',
            dateEnd: '2023-07-15',
            img: 'https://lh5.googleusercontent.com/p/AF1QipO3WFZ-wcWPtgLOaKTgF6VSRYYhps__bGnef4eR=w408-h306-k-no'
        },
        {
            id: 4,
            city: 'Санкт-Петербург',
            dateStart: '2023-07-16',
            dateEnd: '2023-07-30',
            img: 'https://lh5.googleusercontent.com/p/AF1QipOge8QvEbtPAn_IzKsVMqgJGqGQLZ7D4PmvVrVh=w434-h240-k-no'
        },
        {
            id: 5,
            city: 'Москва',
            dateStart: '2023-08-01',
            dateEnd: '2023-08-12',
            img: 'https://lh5.googleusercontent.com/p/AF1QipNiOahF6c4mhIT0EQADvGGdTNNtPnPqNz7-SeW-=w408-h407-k-no'
        },
        {
            id: 6,
            city: 'Екатеринбург',
            dateStart: '2023-08-13',
            dateEnd: '2023-08-21',
            img: 'https://lh5.googleusercontent.com/p/AF1QipP-Bu4OoesX6mHtSJyTCJKOygO8ApsSQFc1svcx=w408-h408-k-no'
        },
        {
            id: 7,
            city: 'Сочи',
            dateStart: '2023-08-21',
            dateEnd: '2023-08-28',
            img: 'https://lh5.googleusercontent.com/p/AF1QipMwUVAO6Zy2Ws-bnLsTFzYt5t5tev-ekZn--gTX=w426-h240-k-no'
        }
    ]

    const formatter = new Intl.DateTimeFormat('ru', {
        month: 'long',
        day: 'numeric'
    })

    return (
        <div style={{padding: 16, display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
            <div style={{maxWidth: 1400}}>
                <Typography.Title>Мои путешествия</Typography.Title>
                <Row gutter={[16, 16]}>
                    {
                        travelsData.map(data =>
                            <Col
                                xs={{span: 24}}
                                sm={{span: 12}}
                                md={{span: 8}}
                                lg={{span: 6}}
                            >
                                <Card
                                    bordered
                                    hoverable
                                    cover={<img style={{objectFit: 'cover', height: 250}} src={data.img}
                                                alt={data.city}/>}
                                >
                                    <Card.Meta
                                        title={data.city}
                                        description={`${formatter.format(Date.parse(data.dateStart))} - ${formatter.format(Date.parse(data.dateEnd))}`}
                                    />
                                </Card>
                            </Col>
                        )
                    }
                </Row>
            </div>
        </div>
    )
}

export default MyTravels