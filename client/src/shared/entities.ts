export type Place = {
    id: number | null
    imageId: number | null
    osmId: string
    name: string | null
    address: string
    lat: number
    lon: number
}

export type Point = {

}

export type City = {
    id: number | null,
    imageId: number | null,
    osmId: string,
    name: string | null,
    region: string,
    lat: number,
    lon: number
}
