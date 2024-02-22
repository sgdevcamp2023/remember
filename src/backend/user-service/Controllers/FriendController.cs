using Castle.DynamicProxy;
using Microsoft.AspNetCore.Mvc;
using user_service.Controllers.dto.friend;
using user_service.friend.service;
using user_service.intercepter;
using user_service.logger;

namespace user_service
{
    namespace friend
    {
        [filter.DefaultHeaderFilter]
        [Route("api/user/[controller]")]
        [ApiController]
        public class FriendController : ControllerBase
        {
            private IFriendService _friendService;
            public FriendController(IFriendService friendService, IBaseLogger logger, IHttpContextAccessor accessor)
            {
                var generator = new ProxyGenerator();
                _friendService = generator.CreateInterfaceProxyWithTarget<IFriendService>(friendService, new LogInterceptor(logger, accessor));
            }

            [HttpGet("list/{userId}")]
            public async Task<List<FriendInfoDTO>> GetFriendListAsync(
                long userId)
            {
                return await _friendService.GetFriendList(userId);
            }

            [HttpGet("request/send-list/{userId}")]
            public List<FriendInfoDTO> GetFriendSendRequestList(
                long userId)
            {
                return _friendService.GetSendRequestList(userId);
            }

            [HttpGet("request/receive-list/{userId}")]
            public List<FriendInfoDTO> GetFriendReceiveRequestList(
               long userId)
            {
                return _friendService.GetReceiveRequestList(userId);
            }

            [HttpPost("request/send")]
            public IActionResult SendFriendRequest(
                [FromBody] FriendDTO friendDTO)
            {
                _friendService.SendFriendAddRequest(friendDTO);

                return Ok();
            }

            [HttpPost("request/accept")]
            public async Task<IActionResult> AcceptFriendRequest(
                [FromBody] FriendDTO friendDTO)
            {
                await _friendService.AcceptFriendAddRequestAsync(friendDTO);

                return Ok();
            }

            [HttpPost("request/refuse")]
            public IActionResult RefuseFriendRequest(
                [FromBody] FriendDTO friendDTO)
            {
                _friendService.RefuseFriendAddRequest(friendDTO);
                return Ok();
            }

            [HttpDelete("request/cancle")]
            public IActionResult CancleFriendRequest(
                [FromBody] FriendDTO friendDTO)
            {
                _friendService.CancleFriendAddRequest(friendDTO);

                return Ok();
            }
            [HttpDelete("delete")]
            public IActionResult DeleteFriend(
                [FromBody] FriendDTO friendDTO)
            {
                _friendService.DeleteFriend(friendDTO);

                return Ok();
            }
        }
    }
}