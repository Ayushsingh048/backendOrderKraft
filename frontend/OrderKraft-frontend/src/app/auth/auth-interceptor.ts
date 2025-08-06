import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Clone the request and ensure 'withCredentials' is set to true
  const modifiedReq = req.clone({
    withCredentials: true
  });

  // Log request to verify 'withCredentials' is included
  console.log('Modified request with credentials:', modifiedReq);

  return next(modifiedReq);
};
