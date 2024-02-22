import { Test, TestingModule } from '@nestjs/testing';
import { GuildGateway } from './guild.gateway';

describe('GuildGateway', () => {
  let gateway: GuildGateway;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [GuildGateway],
    }).compile();

    gateway = module.get<GuildGateway>(GuildGateway);
  });

  it('should be defined', () => {
    expect(gateway).toBeDefined();
  });
});
