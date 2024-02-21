using user_service.user.dto;
using user_service.common;
using user_service.common.exception;
using Google.Cloud.Storage.V1;
using Google.Apis.Auth.OAuth2;
using Castle.DynamicProxy;
using user_service.intercepter;
using user_service.Business.User.dto;
using user_service.common.dto;

namespace user_service.user.service;

public class UserService : IUserService
{
    private IUserRepository _userRepository;
    private ICommunityClient _communityClient;
    private string _bucketName;
    private string _keyPath;
    private string _defaultImage;
    public UserService(IUserRepository userRepository,
                        IConfiguration config,
                        ICommunityClient communityClient,
                        LogInterceptor interceptor)
    {
        var generator = new ProxyGenerator();
        _userRepository = generator.CreateInterfaceProxyWithTarget<IUserRepository>(userRepository, interceptor);
        _communityClient = generator.CreateInterfaceProxyWithTarget<ICommunityClient>(communityClient, interceptor);
        _bucketName = config["GoogleCloud:BucketName"];
        _keyPath = config["GoogleCloud:KeyPath"];
        _defaultImage = config["DefaultProfileImage"];
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
            Profile = user.Profile
        };
    }

    public async Task ChangeName(NameDTO nameDTO, string traceId, string userId)
    {
        if(userId != nameDTO.UserId.ToString())
            throw new ServiceException(4031);

        bool isSuccess = await _communityClient.ChangeNameAsync(new CommunityNameDTO()
        {
            userId = nameDTO.UserId,
            nickname = nameDTO.NewName
        }, traceId, userId);

        if (!isSuccess)
            throw new ServiceException(4031);

        if(!_userRepository.UpdateName(nameDTO.UserId, nameDTO.NewName))
            throw new ServiceException(4031);
    }

    public async Task<string> ChangeProfile(ProfileDTO profileDTO, string traceId, string userId)
    {
        if(userId != profileDTO.UserId.ToString())
            throw new ServiceException(4032);

        Console.WriteLine(profileDTO.NewProfile.ContentType);

        string ContentType = profileDTO.NewProfile.ContentType;
        if (!ContentType.StartsWith("image/"))
        {
            throw new ServiceException(4024);
        }

        UserModel? user = _userRepository.GetUserById(profileDTO.UserId);
        if (user == null)
            throw new ServiceException(4007);

        string fileName = $"{user.Id}/profile." + ContentType.Split('/')[1];
        string newProfilePath = UploadProfileToGCP(profileDTO.NewProfile, fileName);

        bool isSuccess = await _communityClient.ChangeProfileAsync(new CommunityProfileDTO()
        {
            userId = user.Id,
            profile = newProfilePath
        }, traceId, userId);

        if (!isSuccess)
        {
            DeleteProfileFromGCP(newProfilePath);
            throw new ServiceException(4024);
        }

        if(user.Profile != _defaultImage)
            DeleteProfileFromGCP(user.Profile);
        
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
            System.Console.WriteLine("UploadProfileToGCP Error");
            throw new ServiceException(4024);
        }
    }

    private bool DeleteProfileFromGCP(string profile)
    {
        try
        {
            var credential = GoogleCredential.FromFile(_keyPath);
            var storage = StorageClient.Create(credential);
            storage.DeleteObject(_bucketName, profile);

            return true;
        }
        catch (Exception)
        {
            return false;
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