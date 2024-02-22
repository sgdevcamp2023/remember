import { Test, TestingModule } from '@nestjs/testing';
import { GuildController } from './guild.controller';
import { GuildService } from './guild.service';

describe('GuildController', () => {
  let controller: GuildController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [GuildController],
      providers: [GuildService],
    }).compile();

    controller = module.get<GuildController>(GuildController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
