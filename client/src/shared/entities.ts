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

export type City = {
    id: number | null,
    imageId: number | null,
    name: string | null,
    region: string,
    lat: number,
    lon: number
}

export type Auth = {
    accessToken: string | null
    refreshToken: string | null
}

export type UserFormSignUp = {
    email: string,
    password: string,
    fullName: string
}

export type UserFormSignIn = {
    email: string,
    password: string
}

export type State = {
    data: Auth | null
    isLoading: boolean
    error: string | undefined
}

export type Error = {
    message: string
    errors?: Array<{field: string, message: string}>
}

export enum PlaceTypes {
    attraction = 'attraction',
    cinema = 'cinema',
    hotel = 'hotel',
    house = 'house',
    monument = 'monument',
    museum = 'museum',
    theatre = 'theatre',
    viewpoint = 'viewpoint',
    unknown = 'unknown'
}
