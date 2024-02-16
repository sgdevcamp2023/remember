using System.Text;
using Newtonsoft.Json;
using user_service.common.dto;

namespace user_service.common;

public class StateClient : IStateClient
{
    private readonly IConfiguration _configuration;
    private HttpClient _client = new HttpClient();
    private CancellationToken _cancellationToken;
    public StateClient(IConfiguration configuration)
    {
        _configuration = configuration;
        _client.BaseAddress = new Uri(_configuration["OutService:State"]);

        CancellationTokenSource cts = new CancellationTokenSource();
        cts.CancelAfter(1000); // Cancel after 1 second
        _cancellationToken = cts.Token;
    }

    public async Task<ConnectsStatusDTO?> GetFriendOnlineList(UserIdsDTO userIds, string traceId, string userId)
    {
        SetDefaultHeader(traceId, userId);

        try
        {
            HttpResponseMessage response = await _client.PostAsync("direct/user/info", CreateJsonContent(userIds), _cancellationToken);
            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                var result = await response.Content.ReadAsStringAsync();
                ConnectsStatusDTO? data = JsonConvert.DeserializeObject<ConnectsStatusDTO>(result);
                return data;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.Message);

            return null;
        }
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