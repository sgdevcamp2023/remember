using Microsoft.AspNetCore.Mvc.Filters;
using user_service.common.exception;

namespace user_service
{
    namespace filter
    {
        public class TraceIdCheckFilter : ActionFilterAttribute
        {
            public override void OnActionExecuting(ActionExecutingContext context)
            {
                if(context.HttpContext.Request.Headers.ContainsKey("trace-id") == false)
                    throw new ServiceException(4200);
            }

            public override void OnActionExecuted(ActionExecutedContext context)
            {
                
            }
        }
    }
}
