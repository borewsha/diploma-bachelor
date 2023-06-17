import {useEffect} from 'react'
import L from 'leaflet'
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css'
import 'leaflet-routing-machine'
import {useMap} from 'react-leaflet'

L.Marker.prototype.options.icon = L.icon({
    iconUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png',
    iconSize: [0 ,0]
});

// @ts-ignore
const Routing = ({points}) => {
    const map = useMap()

    // @ts-ignore
    useEffect(() => {
        if (!map) return

        if (points) {
            const routingControl = L.Routing.control({
                waypoints: points.map((point: any) => L.latLng(point.lat, point.lon)),
                // show: false,
                // @ts-ignore
                lineOptions: {
                    styles: [{color: 'blue', opacity: 0.7, weight: 3}],

                },
                missingRouteStyles: {
                    style: [{color: 'black', opacity: 0.15, weight: 7},{color: 'white', opacity: 0.6, weight: 4},{color: 'gray', opacity: 0.8, weight: 2, dashArray: '7,12'}]
                },
                addWaypoints: false,
                language: 'ru'
                // serviceUrl: 'https://routing.openstreetmap.de/routed-foot/route/v1'
            }).addTo(map)

            // @ts-ignore
            // routingControl._container.style.display = 'None'

            return () => map.removeControl(routingControl)
        }

    }, [map, points])

    return null
}

export default Routing