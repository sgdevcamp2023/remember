import {create} from 'zustand'

const ChannelsStore = create(set => ({
  channels: [],
  registerChannels: (channelList) => set({channels: channelList}),
  removeChannels: () => set({channels: []})
}))

export default ChannelsStore;