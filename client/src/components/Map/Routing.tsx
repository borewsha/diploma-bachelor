import {useEffect} from 'react'
import L from 'leaflet'
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css'
import 'leaflet-routing-machine'
import {useMap} from 'react-leaflet'

L.Marker.prototype.options.icon = L.icon({
    iconUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png',
    iconSize: [0 ,0]
})

// @ts-ignore
const Routing = ({points}) => {
    const map = useMap()

    // points = [
    //     {
    //         'id': 131,
    //         'cityId': 1,
    //         'imageId': null,
    //         'type': 'tourism',
    //         'name': 'Триумфальная арка',
    //         'address': 'улица Петра Великого',
    //         'lat': 43.1139186,
    //         'lon': 131.8924323
    //     },
    //     {
    //         'id': 132,
    //         'cityId': 1,
    //         'imageId': null,
    //         'type': 'tourism',
    //         'name': 'Музей автомотостарины',
    //         'address': 'Сахалинская улица',
    //         'lat': 43.0969792,
    //         'lon': 131.9659974
    //     },
    //     {
    //         'id': 133,
    //         'cityId': 1,
    //         'imageId': null,
    //         'type': 'tourism',
    //         'name': 'Вид на мост на о. Русский',
    //         'address': 'Кипарисовая улица',
    //         'lat': 43.0864702,
    //         'lon': 131.9169909
    //     },
    //     {
    //         'id': 134,
    //         'cityId': 1,
    //         'imageId': null,
    //         'type': 'tourism',
    //         'name': 'Вид на залив "Золотой рог"',
    //         'address': 'улица Суханова',
    //         'lat': 43.1175183,
    //         'lon': 131.8982528
    //     }
    // ]
    const routeTypes = ['car', 'foot', 'car', 'foot', 'car', 'foot']

    // @ts-ignore
    useEffect(() => {
        if (!map) return

        let routingControls: any[] = []

        if (points) {
            // @ts-ignore
            let routingControl
            for (let i = 0; i < points.length - 1; i++) {
                if (routeTypes[i % routeTypes.length] === 'car') {
                    routingControl = L.Routing.control({
                        waypoints: [points[i], points[i + 1]],
                        show: false,
                        // @ts-ignore
                        lineOptions: {
                            styles: [{color: 'blue', opacity: 0.7, weight: 3}],
                        },
                        language: 'ru'
                    }).addTo(map)
                } else {
                    routingControl = L.Routing.control({
                        waypoints: [points[i], points[i + 1]],
                        show: false,
                        // @ts-ignore
                        lineOptions: {
                            styles: [{color: 'red', opacity: 0.7, weight: 3}],
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

    }, [map, points])

    return null
}

export default Routing