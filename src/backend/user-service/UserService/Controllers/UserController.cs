
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using user_service.common;
using user_service.user.dto;
using user_service.user.service;

namespace user_service
{
    namespace user
    {
        [filter.TraceIdCheckFilter]
        [Route("api/user/[controller]")]
        [ApiController]
        public class UserController : ControllerBase
        {
            UserService _userService;
            public UserController(UserService userService)
            {
                _userService = userService;
            }

            [HttpGet("info")]
            public UserDTO GetInfo([FromHeader(Name = "trace-id")] int traceId)
            {
                return _userService.GetUserInfo(traceId);
            }
            
            [HttpPatch("change-password")]
            public IActionResult ChangePassword(
                [FromHeader(Name = "trace-id")] int traceId,
                [FromBody] PasswordDTO passwordDTO)
            {
                _userService.ChangePassword(traceId, passwordDTO);

                return Ok();
            }

            [HttpPatch("change-name")]
            public IActionResult ChangeName(
                [FromHeader(Name = "trace-id")] [Required] int traceId,
                [FromBody] string newName)
            {
                _userService.ChangeName(traceId, newName);
                
                return Ok();
            }
        }
    }
}