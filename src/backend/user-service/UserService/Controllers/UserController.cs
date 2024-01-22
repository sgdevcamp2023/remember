
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

            [HttpGet("info/{userId}")]
            public UserDTO GetInfo(int userId)
            {
                return _userService.GetUserInfo(userId);
            }
            
            [HttpPatch("change-password")]
            public IActionResult ChangePassword(
                [FromBody] PasswordDTO passwordDTO)
            {
                _userService.ChangePassword(passwordDTO);

                return Ok();
            }

            [HttpPatch("change-name")]
            public IActionResult ChangeName(
                [FromBody] NameDTO nameDTO)
            {
                _userService.ChangeName(nameDTO);
                
                return Ok();
            }

            [HttpPatch("change-profile")]
            public IActionResult ChangeProfile(
                [FromBody] ProfileDTO profileDTO)
            {
                _userService.ChangeProfile(profileDTO);
                
                return Ok();
            }
        }
    }
}