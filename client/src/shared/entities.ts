export type Place = {
    id: number | null
    imageId: number | null
    name: string | null
    cityId: number
    address: string
    lat: number
    lon: number
    type: 'tourism' | 'building'
}

export type Point = {

}

export type City = {
    id: number | null,
    imageId: number | null,
    name: string | null,
    region: string,
    lat: number,
    lon: number
}
