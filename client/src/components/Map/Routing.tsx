import {CSSProperties, FC, useEffect} from 'react'
import L, {LatLngExpression} from 'leaflet'
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css'
import 'leaflet-routing-machine'
import {useMap} from 'react-leaflet'
import {Place} from '../../shared/entities'
import {useAppSelector} from '../../shared/hooks'

L.Marker.prototype.options.icon = L.icon({
    iconUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png',
    iconSize: [0 ,0]
})

type RoutingProps = {
    points: { places: Place[], ways: { type: 'car' | 'foot', points: [number, number][] }[] }
}

// @ts-ignore
const Routing: FC<RoutingProps> = ({points}) => {
    const map = useMap()

    const selectedWay = useAppSelector(state => state.travel.selectedWay)

    // @ts-ignore
    useEffect(() => {
        if (!map) return

        let routingControls: any[] = []

        if (points.places) {
            // @ts-ignore
            let routingControl
            for (let i = 0; i < points.ways.length; i++) {
                if (points.ways[i].type === 'car') {
                    routingControl = L.Routing.control({
                        // @ts-ignore
                        waypoints: [[points.places[i].lat, points.places[i].lon], [points.places[i + 1].lat, points.places[i + 1].lon]],
                        show: false,
                        // @ts-ignore
                        lineOptions: {
                            styles: [{color: 'blue', opacity: 0.7, weight: i === selectedWay ? 7 : 3, dashArray: i === selectedWay ? '' : '30 10'}],
                        },
                        language: 'ru'
                    }).addTo(map)
                } else {
                    routingControl = L.Routing.control({
                        // @ts-ignore
                        waypoints: [[points.places[i].lat, points.places[i].lon], [points.places[i + 1].lat, points.places[i + 1].lon]],
                        show: false,
                        // @ts-ignore
                        lineOptions: {
                            styles: [{color: 'red', opacity: 0.7, weight: i === selectedWay ? 7 : 3, dashArray: i === selectedWay ? '' : '30 10'}, ],
                        },
                        language: 'ru',
                        serviceUrl: 'https://routing.openstreetmap.de/routed-foot/route/v1'
                    }).addTo(map)
                }
                // @ts-ignore
                routingControl._container.style.display = 'None'
                routingControls.push(routingControl)
            }
            // @ts-ignore
            return () => routingControls.map(a => map.removeControl(a))
        }

    }, [map, points, selectedWay])

    return null
}

export default Routing