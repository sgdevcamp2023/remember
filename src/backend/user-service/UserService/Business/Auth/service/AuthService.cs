using System.Net.Mail;
using System.Security.Cryptography;
using System.Text;
using MimeKit;
using MimeKit.Text;

using user_service.auth.dto;
using user_service.auth.entity;
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

                public void Register(RegisterDTO register)
                {
                    // 체크
                    SameEmailCheck(register.Email);
                    CheckEmailChecksum(register.Email, register.EmailChecksum);

                    _redisRepository.DeleteRedis(register.Email);

                    // 비밀번호 암호화
                    register.Password = Utils.SHA256Hash(register.Password);

                    if (_userRepository.InsertUser(register) == false)
                        throw new ServiceException(4010);
                }

                public void SendEmailChecksum(string email)
                {
                    SameEmailCheck(email);

                    int checksum = Utils.GenerateRandomNumber(100000, 999999);
                    try
                    {
                        // 이메일 전송
                        var message = new MimeMessage();
                        message.From.Add(new MailboxAddress("user-service", _config["Mail:Email"]));
                        message.To.Add(new MailboxAddress("", email));
                        message.Subject = "user-service 인증번호입니다.";
                        message.Body = new TextPart("plain") { Text = $"인증번호 : {checksum}" };

                        SendEMail(message);

                        _redisRepository.InsertRedis(new RedisModel(email, checksum.ToString()), new TimeSpan(0, 0, int.Parse(_config["Mail:ExpiredTime"])));
                    }
                    catch (Exception e)
                    {
                        _logger.Log(e.Message);

                        throw new ServiceException(4011);
                    }
                }

                public void SendMailResetPassword(string email)
                {
                    if(_userRepository.IsEmailExist(email) == true)
                    {
                        string resetPassword = Utils.GenerateRandomPassword();

                        var message = new MimeMessage();
                        message.From.Add(new MailboxAddress("user-service", _config["Mail:Email"]));
                        message.To.Add(new MailboxAddress("", email));
                        message.Subject = "user-service 비밀번호 재설정";
                        message.Body = new TextPart("plain") { Text = $"Reset Password : {resetPassword}" };

                        SendEMail(message);
                        
                        _userRepository.UpdatePassword(email, Utils.SHA256Hash(resetPassword.ToString()));
                    }
                    else
                        throw new ServiceException(4007);
                }

                public void CheckEmailChecksum(string email, string checksum)
                {
                    string? redisChecksum = _redisRepository.GetStringById(email);

                    if (redisChecksum == null)
                        throw new ServiceException(4012);

                    if (redisChecksum != checksum)
                        throw new ServiceException(4013);
                }

                private void SameEmailCheck(string email)
                {
                    if (_userRepository.IsEmailExist(email))
                    {
                        throw new ServiceException(4009);
                    }
                }

                private void SendEMail(MimeMessage message)
                {
                    try
                    {
                        using (var client = new MailKit.Net.Smtp.SmtpClient())
                        {
                            client.Connect("smtp.gmail.com", 587, MailKit.Security.SecureSocketOptions.StartTls);
                            client.Authenticate(_config["Mail:Id"], _config["Mail:Password"]);

                            client.Send(message);
                            client.Disconnect(true);
                        }
                    }
                    catch (Exception e)
                    {
                        _logger.Log(e.Message);

                        throw new ServiceException(4011);
                    }

                }
            }
        }
    }
}