using ApiGatewayCore.Http.Context;

namespace ApiGatewayCore.Filter;

public delegate Task RequestDelegate(HttpContext context);