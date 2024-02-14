using user_service.user.dto;
using user_service.common;
using user_service.common.exception;
using Google.Cloud.Storage.V1;
using Google.Apis.Auth.OAuth2;
using Castle.DynamicProxy;
using user_service.intercepter;
using user_service.Business.User.dto;

namespace user_service.user.service;

public class UserService : IUserService
{
    private IUserRepository _userRepository;
    private string _bucketName;
    private string _keyPath;
    public UserService(IUserRepository userRepository,
                        IConfiguration config,
                        LogInterceptor interceptor)
    {
        var generator = new ProxyGenerator();
        _userRepository = generator.CreateInterfaceProxyWithTarget<IUserRepository>(userRepository, interceptor);
        _bucketName = config["GoogleCloud:BucketName"];
        _keyPath = config["GoogleCloud:KeyPath"];
    }

    public void ChangePassword(PasswordDTO passwords)
    {
        long userId = passwords.UserId;
        UserModel? user = _userRepository.GetUserById(userId);
        if (user == null)
            throw new ServiceException(4007);

        string currentPassword = Utils.SHA256Hash(passwords.Password);

        if (user.Password != currentPassword)
            throw new ServiceException(4008);

        if(passwords.NewPassword == currentPassword)
            throw new ServiceException(4029);

        _userRepository.UpdatePassword(userId, Utils.SHA256Hash(passwords.NewPassword));
    }

    public UserDTO GetUserInfo(long userId)
    {
        UserModel? user = _userRepository.GetUserById(userId);
        if (user == null)
            throw new ServiceException(4007);

        return new UserDTO()
        {
            Id = user.Id,
            Email = user.Email,
            Name = user.Name,
            ProfileUrl = user.Profile
        };
    }

    public void ChangeName(NameDTO nameDTO)
    {
        _userRepository.UpdateName(nameDTO.UserId, nameDTO.NewName);
    }

    public string ChangeProfile(ProfileDTO profileDTO)
    {
        string ContentType = profileDTO.NewProfile.ContentType;
        if (!ContentType.StartsWith("image/"))
        {
            throw new ServiceException(4024);
        }

        UserModel? user = _userRepository.GetUserById(long.Parse(profileDTO.UserId));
        if (user == null)
            throw new ServiceException(4007);

        string fileName = $"{user.Id}/profile." + ContentType.Split('/')[1];

        string newProfilePath = UploadProfileToGCP(profileDTO.NewProfile, fileName);
        _userRepository.UpdateProfile(user.Id, newProfilePath);

        return newProfilePath;
    }

    public void LogOut(long headerUserId, LogoutDTO logoutDTO)
    {
        System.Console.WriteLine(headerUserId + " " + logoutDTO);
        
        if (headerUserId != logoutDTO.UserId)
            throw new ServiceException(4028);

        var userInfo = _userRepository.GetUserByEmail(logoutDTO.Email);
        if (userInfo == null)
            throw new ServiceException(4028);

        if (userInfo.Id != headerUserId)
            throw new ServiceException(4028);

        // 성공 행동?
    }

    private string UploadProfileToGCP(IFormFile profile, string fileName)
    {
        try
        {
            byte[] profileBytes = MakeProfileToByte(profile);

            var credential = GoogleCredential.FromFile(_keyPath);
            var storage = StorageClient.Create(credential);
            using (var stream = new MemoryStream(profileBytes))
            {
                storage.UploadObject(_bucketName, fileName, null, stream);
            }
            return storage.GetObject(_bucketName, fileName).MediaLink;
        }
        catch (Exception)
        {
            throw new ServiceException(4024);
        }
    }

    private byte[] MakeProfileToByte(IFormFile file)
    {
        using (var memovyStream = new MemoryStream())
        {
            file.CopyTo(memovyStream);
            return memovyStream.ToArray();
        }
    }
}