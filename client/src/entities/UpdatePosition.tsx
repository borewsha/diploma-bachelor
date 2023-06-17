import {FC} from 'react'
import {LatLngExpression} from 'leaflet'
import {useMap} from 'react-leaflet'

type UpdatePositionProps = {
    center: LatLngExpression,
    zoom: number
}

const UpdatePosition: FC<UpdatePositionProps> = ({center, zoom}) => {
    const map = useMap()
    map.setView(center, zoom)

    return null
}

export default UpdatePosition