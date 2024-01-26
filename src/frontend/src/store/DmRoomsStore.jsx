import {create} from 'zustand'

const DmRoomsStore = create(set => ({
  rooms: [],
  registerRooms: (roomList) => set({rooms: roomList}),
  removeRooms: () => set({rooms: []})
}))

export default DmRoomsStore;