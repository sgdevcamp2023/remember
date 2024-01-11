

using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using user_service.filter;

namespace user_service
{
    namespace friend
    {
        [TraceIdCheckFilter]
        [Route("api/user/[controller]")]
        [ApiController]
        public class FriendController : ControllerBase
        {
            public FriendController()
            {
                
            }
            
            [HttpGet("list")]
            public IActionResult GetFriendList(
                [FromHeader(Name = "trace-id")] int traceId)
            {
                return Ok();
            }
            
            [HttpGet("request/list")]
            public IActionResult GetFriendRequestList(
                [FromHeader(Name = "trace-id")] int traceId)
            {
                return Ok();
            }

            [HttpPost("request/send")]
            public IActionResult SendFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] [Required] dto.RequestEmailDto request)
            {
                return Ok();
            }

            [HttpPost("request/accept")]
            public IActionResult AcceptFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] [Required] dto.RequestEmailDto request)
            {
                return Ok();
            }

            [HttpPost("request/refuse")]
            public IActionResult RefuseFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] [Required] dto.RequestEmailDto request)
            {
                return Ok();
            }

            [HttpGet("request/list")]
            public IActionResult ShowAllFriendRequest(
                [FromHeader(Name = "trace-id")] int traceId)
            {
                return Ok();
            }

            [HttpDelete("delete")]
            public IActionResult DeleteFriend(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] [Required] dto.RequestEmailDto request)
            {
                return Ok();
            }

        }
    }
}