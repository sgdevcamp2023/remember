using ApiGatewayCore.Http.Context;
using ApiGatewayCore.Instance;

namespace ApiGatewayCore.Filter;

public delegate Task RequestDelegate(Adapter adapter, HttpContext context);