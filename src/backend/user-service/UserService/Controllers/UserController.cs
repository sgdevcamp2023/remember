using Castle.DynamicProxy;
using Microsoft.AspNetCore.Mvc;
using user_service.Business.User.dto;
using user_service.common.exception;
using user_service.intercepter;
using user_service.logger;
using user_service.user.dto;
using user_service.user.service;

namespace user_service
{
    namespace user
    {
        // [filter.TraceIdCheckFilter]
        [Route("api/[controller]")]
        [ApiController]
        public class UserController : ControllerBase
        {
            IUserService _userService;
            public UserController(IUserService userService, IBaseLogger logger, IHttpContextAccessor accessor)
            {
                var generator = new ProxyGenerator();
                _userService = generator.CreateInterfaceProxyWithTarget<IUserService>(userService, new LogInterceptor(logger, accessor));
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
            public ProfileResponseDTO ChangeProfile(
                [FromForm] ProfileDTO file)
            {
                string profileUrl = _userService.ChangeProfile(file);

                return new ProfileResponseDTO()
                {
                    ProfileUrl = profileUrl
                };
            }

            [HttpPost("logout")]
            public IActionResult Logout(
                [FromHeader(Name = "user-id")] long userId,
                [FromBody] LogoutDTO logoutDTO)
            {
                _userService.LogOut(userId, logoutDTO);

                return Ok();
            }
        }
    }
}