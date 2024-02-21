using System.Text;
using Newtonsoft.Json;
using user_service.common;
using user_service.common.exception;
using user_service.common.dto;

namespace user_service.Common;

public class CommunityClient : ICommunityClient
{
    private readonly IConfiguration _configuration;
    private HttpClient _client = new HttpClient();
    private CancellationToken _cancellationToken;
    public CommunityClient(IConfiguration configuration)
    {
        _configuration = configuration;
        _client.BaseAddress = new Uri(_configuration["OutService:Community"]);

        CancellationTokenSource cts = new CancellationTokenSource();
        cts.CancelAfter(10000); // Cancel after 1 second
        _cancellationToken = cts.Token;
    }

    public async Task<bool> RegisterUserAsync(CommunityUserDTO communityDTO, string traceId)
    {
        SetDefaultHeader(traceId.ToString(), "");

        try
        {
            HttpResponseMessage response = await _client.PostAsync("registration/user", CreateJsonContent(communityDTO), _cancellationToken);
            string str = await response.Content.ReadAsStringAsync();
            var dto = JsonConvert.DeserializeObject<CommunityResponseDTO>(str);
            if (dto == null)
                System.Console.WriteLine("Response is null");
            if (dto!.ResultCode != 200)
            {
                var exception = JsonConvert.DeserializeObject<CommunityBaseException>(dto.ResultData);
                System.Console.WriteLine(exception!.Message);
                throw new ServiceException(4010);
            }
        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);

            return false;
        }

        return true;
    }

    public async Task<bool> ChangeNameAsync(CommunityNameDTO communityDTO, string traceId, string userId)
    {
        SetDefaultHeader(traceId, userId);
        System.Console.WriteLine(_client.BaseAddress + "change/user/nickname");

        StringContent content = new StringContent(
            JsonConvert.SerializeObject(communityDTO),
            Encoding.UTF8,
            "application/json"
        );

        try
        {
            HttpResponseMessage response = await _client.PatchAsync("change/user/nickname", content, _cancellationToken);
            // response.Content.
            string str = await response.Content.ReadAsStringAsync();
            var dto = JsonConvert.DeserializeObject<CommunityResponseDTO>(str);
            if (dto == null)
                System.Console.WriteLine("Response is null");
            if (dto!.ResultCode != 200)
            {
                var exception = JsonConvert.DeserializeObject<CommunityBaseException>(dto.ResultData);
                System.Console.WriteLine(exception!.Message);
                throw new ServiceException(4010);
            }
        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);
            return false;
        }

        return true;
    }

    public async Task<bool> ChangeProfileAsync(CommunityProfileDTO communityDTO, string traceId, string userId)
    {
        SetDefaultHeader(traceId, userId);

        StringContent content = new StringContent(
            JsonConvert.SerializeObject(communityDTO),
            Encoding.UTF8,
            "application/json"
        );

        try
        {
            HttpResponseMessage response = await _client.PatchAsync("change/user", content, _cancellationToken);
            string str = await response.Content.ReadAsStringAsync();
            var dto = JsonConvert.DeserializeObject<CommunityResponseDTO>(str);
            if (dto == null)
                System.Console.WriteLine("Response is null");
            if (dto!.ResultCode != 200)
            {
                var exception = JsonConvert.DeserializeObject<CommunityBaseException>(dto.ResultData);
                System.Console.WriteLine(exception!.Message);
                throw new ServiceException(4010);
            }
        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);

            return false;
        }

        return true;
    }

    public async Task<bool> CreateDMRoomAsync(CommunityRoomCreateDTO communityDTO, string traceId, string userId)
    {
        SetDefaultHeader(traceId, userId);

        StringContent content = new StringContent(
            JsonConvert.SerializeObject(communityDTO),
            Encoding.UTF8,
            "application/json"
        );

        try
        {
            HttpResponseMessage response = await _client.PatchAsync("registration/room", content, _cancellationToken);
            string str = await response.Content.ReadAsStringAsync();
            var dto = JsonConvert.DeserializeObject<CommunityResponseDTO>(str);
            if (dto == null)
                System.Console.WriteLine("Response is null");
            if (dto!.ResultCode != 200)
            {
                var exception = JsonConvert.DeserializeObject<CommunityBaseException>(dto.ResultData);
                System.Console.WriteLine(exception!.Message);
                throw new ServiceException(4010);
            }
        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);

            return false;
        }

        return true;
    }

    private void SetDefaultHeader(string traceId, string userId)
    {
        _client.DefaultRequestHeaders.Add("trace-id", traceId);
        _client.DefaultRequestHeaders.Add("user-id", userId);
    }

    private StringContent CreateJsonContent(object obj)
    {
        return new StringContent(
            JsonConvert.SerializeObject(obj),
            Encoding.UTF8,
            "application/json"
        );
    }
}