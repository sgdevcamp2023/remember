export class ChannelGuildEntity {
  channelId: string; // 채널 ID ("guildPK + '/' + channelPK"형태)
  guildId: string; // 채널에 있는 사용자을의 ID집합
  userId: string;

  constructor(channelId: string, guildId: string, userId: string) {
    this.channelId = channelId;
    this.guildId = guildId;
    this.userId = userId;
  }
}
