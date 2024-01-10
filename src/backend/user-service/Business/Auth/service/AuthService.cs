using System.Net.Mail;
using MimeKit;
using MimeKit.Text;

using user_service.auth.dto;
using user_service.auth.repository;
using user_service.common;
using user_service.common.exception;
using user_service.logger;

namespace user_service
{
    namespace auth
    {
        namespace service
        {
            public class AuthService
            {
                private readonly IBaseLogger _logger;
                private readonly IUserRepository _userRepository;
                private IStringRedisRepository _redisRepository = null!;
                private IConfiguration _config = null!;
                public AuthService(IBaseLogger logger,
                                IUserRepository userRepository,
                                IStringRedisRepository redisRepository,
                                IConfiguration config)
                {
                    _logger = logger;
                    _userRepository = userRepository;
                    _redisRepository = redisRepository;
                    _config = config;
                }
                public void SameEmailCheck(string email)
                {
                    if (_userRepository.IsEmailExist(email))
                    {
                        throw new ServiceException(4009);
                    }
                }

                public void Register(RegisterDTO register)
                {
                    if (_userRepository.InsertUser(register) == false)
                        throw new ServiceException(4010);
                }

                public void SendEmail(string email)
                {
                    // 이메일 전송
                    var message = new MimeMessage();
                    message.From.Add(new MailboxAddress("user-service", _config["Mail:Email"]));
                    message.To.Add(new MailboxAddress("", email));
                    message.Subject = "Hello World";
                    message.Body = new TextPart("plain") { Text = "Hello from .NET!" };

                    using (var client = new MailKit.Net.Smtp.SmtpClient())
                    {
                        client.Connect("smtp.gmail.com", 587, MailKit.Security.SecureSocketOptions.StartTls);
                        client.Authenticate(_config["Mail:Id"], _config["Mail:Password"]);

                        client.Send(message);
                        client.Disconnect(true);
                    }
                }
            }
        }
    }
}