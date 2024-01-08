using Microsoft.AspNetCore.Mvc.Filters;

namespace user_service
{
    namespace filter
    {
        public class TraceIdFilter : Attribute, IActionFilter
        {
            public void OnActionExecuted(ActionExecutedContext context)
            {
                if(context.HttpContext.Request.Headers.ContainsKey("trace-id") == false)
                    throw new Exception("Trace id is required");
                throw new NotImplementedException();
            }

            public void OnActionExecuting(ActionExecutingContext context)
            {
                throw new NotImplementedException();
            }
        }
    }
}
