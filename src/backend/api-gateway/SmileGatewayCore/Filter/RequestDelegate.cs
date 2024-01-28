using SmileGatewayCore.Http.Context;
using SmileGatewayCore.Instance;

namespace SmileGatewayCore.Filter;

public delegate Task RequestDelegate(Adapter adapter, HttpContext context);