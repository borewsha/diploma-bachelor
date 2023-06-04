import { useEffect, useState } from 'react';
import L from 'leaflet';
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css';
import 'leaflet-routing-machine';
import { useMap } from 'react-leaflet';

L.Marker.prototype.options.icon = L.icon({
    iconUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png'
});

// @ts-ignore
const Routing = ({ sourceCity, destinationCity }) => {
    const map = useMap();
    // @ts-ignore
    useEffect(() => {
        if (!map) return;

        if ( sourceCity?.lat !== undefined && destinationCity?.lat !== undefined  ) {
            // @ts-ignore
            const routingControl = L.Routing.control({
                waypoints: [
                    L.latLng(43.74, 131.94),
                    L.latLng(44.6792, 132.949)
                ]
            }).addTo(map);

            return () => map.removeControl(routingControl);
        }



    }, [map, sourceCity, destinationCity]);

    return null;
}

export default Routing;