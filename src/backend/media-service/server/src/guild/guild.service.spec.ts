import { Test, TestingModule } from '@nestjs/testing';
import { GuildService } from './guild.service';

describe('GuildService', () => {
  let service: GuildService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [GuildService],
    }).compile();

    service = module.get<GuildService>(GuildService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
