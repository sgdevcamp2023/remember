
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace user_service
{
    namespace user
    {
        [filter.TraceIdCheckFilter]
        [Route("api/user/[controller]")]
        [ApiController]
        public class UserController : ControllerBase
        {
            public UserController()
            {

            }

            [HttpGet("info")]
            public IActionResult GetInfo([FromHeader(Name = "trace-id")] int traceId)
            {
                return Ok();
            }
            
            [HttpPatch("change-password")]
            public IActionResult ChangePassword(
                [FromHeader(Name = "trace-id")] [Required] int traceId,
                [FromBody] [Required] dto.PasswordDTO request)
            {
                return Ok();
            }

            [HttpPatch("change-name")]
            public IActionResult ChangeName(
                [FromHeader(Name = "trace-id")] [Required] int traceId,
                [FromBody] [Required] dto.PasswordDTO request)
            {
                return Ok();
            }
        }
    }
}