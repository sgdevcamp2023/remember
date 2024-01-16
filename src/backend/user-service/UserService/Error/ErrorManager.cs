
using System.Text.Json;
using System.Text.Json.Nodes;
using Microsoft.AspNetCore.Mvc;

namespace user_service
{
    namespace error
    {
        public static class ErrorManager
        {
            public static Dictionary<int, ErrorCodeModel> ErrorCode { get; private set; } = null!;
            private readonly static string _authPath = @"Error\ErrorCode.json";

            public static void Init()
            {
                ErrorCode = new Dictionary<int, ErrorCodeModel>();
                var json = JsonSerializer.Deserialize<List<ErrorCodeModel>>(File.ReadAllText(_authPath));
                if(json == null)
                    return;
                foreach (var item in json)
                {
                    ErrorCode.Add(item.code, item);
                }
            }

            public static JsonResult GetErrorCodeResult(int code, int statusCode = 400)
            {
                return new JsonResult(ErrorCode[code]) { StatusCode = statusCode };
            }

            public static string GetErrorCodeString(int code, int statusCode = 400)
            {
                var errorCodeJson = JsonSerializer.Serialize(ErrorCode[code]);
                return errorCodeJson;
            }
        }
    }
}