export class ChannelMembersEntity {
  voiceChannelId: string; // 채널 ID ("guildPK + '/' + channelPK"형태)
  members: Map<string, string>; // 채널에 있는 사용자을의 ID집합

  constructor(voiceChannelId: string) {
    this.voiceChannelId = voiceChannelId;
    this.members = new Map();
  }

  join(userId: string, socketId: string): void {
    this.members.set(userId, socketId);
  }

  leave(memberId: string): void {
    this.members.delete(memberId);
  }
}
