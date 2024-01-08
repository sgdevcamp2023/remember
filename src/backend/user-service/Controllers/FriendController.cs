

using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;

namespace user_service
{
    namespace friend
    {
        [filter.TraceIdCheckFilter]
        [Route("api/user/[controller]")]
        [ApiController]
        public class FriendController : ControllerBase
        {
            public FriendController()
            {
                
            }
            
            [HttpGet("friend/list")]
            public IActionResult GetFriendList(
                [FromHeader(Name = "trace-id")] int traceId)
            {
                return Ok();
            }
            
            [HttpGet("friend/request/list")]
            public IActionResult GetFriendRequestList(
                [FromHeader(Name = "trace-id")] int traceId)
            {
                return Ok();
            }

            [HttpPost("friend/request/send")]
            public IActionResult SendFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] [Required] dto.RequestEmailDto request)
            {
                return Ok();
            }

            [HttpPost("friend/request/accept")]
            public IActionResult AcceptFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] [Required] dto.RequestEmailDto request)
            {
                return Ok();
            }

            [HttpPost("friend/request/refuse")]
            public IActionResult RefuseFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] [Required] dto.RequestEmailDto request)
            {
                return Ok();
            }

            [HttpGet("friend/request/list")]
            public IActionResult ShowAllFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId)
            {
                return Ok();
            }

            [HttpDelete("friend/delete")]
            public IActionResult DeleteFriend(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] [Required] dto.RequestEmailDto request)
            {
                return Ok();
            }

        }
    }
}