import React, {FC, useContext, useState} from 'react'
import 'widgets/TravelCalendar/style.css'
import {Calendar, Modal} from 'antd'
import {DndProvider, useDrag, useDrop} from 'react-dnd'
import {HTML5Backend} from 'react-dnd-html5-backend'

const Ctx = React.createContext<any>(null)

const EventCell: FC<any> = ({d, event, eventIdx}) => {
    // @ts-ignore
    const [, drager] = useDrag({
        item: {type: 'event', event, eventIdx, d}
    })

    const {setShow} = useContext(Ctx)

    return (
        <li ref={drager} className="cell" onClick={() => setShow(true)}>
            {event.title}
        </li>
    )
}

const CalendarCell: FC<any> = ({date, events}) => {
    // @ts-ignore
    const {setShow, moveEvent} = useContext(Ctx)

    const [, droper] = useDrop({
        accept: 'event',
        drop: (item, monitor) => {
            setShow(true)
            // @ts-ignore
            moveEvent(item.d, item.eventIdx, date.format('D'))
        }
    })

    return (
        <div
            ref={droper}
            style={{
                cursor: 'default',
                margin: '0 4px',
                padding: '4px 0px 10px',
                border: 0,
                textAlign: 'left',
                backgroundColor: date.format('D') === '12' ? '#e6f7ff' : 'white',
                borderTop:
                    date.format('D') === '12' ? '2px solid #1890ff' : '2px solid #f0f0f0'
            }}
        >
            <div style={{fontSize: 16, fontWeight: 'bold', paddingLeft: 4}}>
                {date.format('D')}
            </div>
            <div
                style={{
                    minHeight: '90px'
                }}
            >
                <div>
                    <ul
                        style={{
                            listStyle: 'none',
                            padding: 0,
                            margin: '4px 0'
                        }}
                    >
                        {events.map((item: any, idx: any) => (
                            <EventCell
                                key={item.id}
                                d={date.format('D')}
                                event={item}
                                eventIdx={idx}
                            />
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    )
}

const data0 = {
    '10': [
        {id: 1, title: '课堂1'},
        {id: 2, title: '课堂2'}
    ]
}

export default function TravelCalendar() {
    const [show, setShow] = useState(false)

    const [data, setData] = useState(data0)
    const moveEvent = (oldD: any, oldIdx: any, newD: any) => {
        console.log(oldD, oldIdx, newD)
        setData((data) => {
            let newData = {...data}
            if (!newData.hasOwnProperty(newD)) {
                // @ts-ignore
                newData[newD] = []
            }
            // @ts-ignore
            newData[newD].push(data[oldD][oldIdx])
            console.log(newData)
            // @ts-ignore
            newData[oldD].splice(oldIdx, 1)
            return newData
        })
    }

    const cellRenderFunc = (date: any) => {
        const d = date.format('D')
        // @ts-ignore
        const evs = data.hasOwnProperty(d) ? data[d] : []
        return <CalendarCell date={date} events={evs}/>
    }

    return (
        <div className="App">
            <Modal open={show} onOk={() => setShow(false)}>
                课堂编辑框
            </Modal>

            <DndProvider backend={HTML5Backend}>
                <Ctx.Provider value={{setShow, moveEvent}}>
                    <Calendar onSelect={undefined} dateFullCellRender={cellRenderFunc}/>
                </Ctx.Provider>
            </DndProvider>
        </div>
    )
}
