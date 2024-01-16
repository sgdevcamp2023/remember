

using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using user_service.Controllers.dto.friend;
using user_service.filter;
using user_service.friend.service;
using user_service.user.dto;

namespace user_service
{
    namespace friend
    {
        [filter.TraceIdCheckFilter]
        [Route("api/user/[controller]")]
        [ApiController]
        public class FriendController : ControllerBase
        {
            private FriendService _friendService;
            public FriendController(FriendService friendService)
            {
                _friendService = friendService;
            }

            [HttpGet("list")]
            public List<UserDTO> GetFriendList(
                [FromHeader(Name = "trace-id")] int traceId)
            {
                return _friendService.GetFriendList(traceId);
            }

            [HttpGet("request/send-list")]
            public List<UserDTO> GetFriendSendRequestList(
                [FromHeader(Name = "trace-id")] int traceId)
            {
                return _friendService.GetSendRequestList(traceId);
            }

            [HttpGet("request/receive-list")]
            public List<UserDTO> GetFriendReceiveRequestList(
               [FromHeader(Name = "trace-id")] int traceId)
            {
                return _friendService.GetReceiveRequestList(traceId);
            }

            [HttpPost("request/send")]
            public IActionResult SendFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] FriendDTO friendDTO)
            {
                _friendService.SendFriendAddRequest(traceId, friendDTO);

                return Ok();
            }

            [HttpPost("request/accept")]
            public IActionResult AcceptFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] FriendDTO friendDTO)
            {
                _friendService.AcceptFriendAddRequest(traceId, friendDTO);

                return Ok();
            }

            [HttpPost("request/refuse")]
            public IActionResult RefuseFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] FriendDTO friendDTO)
            {
                _friendService.RefuseFriendAddRequest(traceId, friendDTO);
                return Ok();
            }

            [HttpDelete("delete")]
            public IActionResult DeleteFriend(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] FriendDTO friendDTO)
            {
                _friendService.DeleteFriend(traceId, friendDTO);

                return Ok();
            }

        }
    }
}