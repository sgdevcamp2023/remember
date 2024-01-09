using Microsoft.AspNetCore.Mvc.Filters;

namespace user_service
{
    namespace filter
    {
        public class TraceIdCheckFilter : ActionFilterAttribute
        {
            public override void OnActionExecuted(ActionExecutedContext context)
            {
                if(context.HttpContext.Request.Headers.ContainsKey("trace-id") == false)
                    throw new Exception("Trace id is required");
                
                throw new NotImplementedException();
            }

            public override void OnActionExecuting(ActionExecutingContext context)
            {
                throw new NotImplementedException();
            }
        }
    }
}
