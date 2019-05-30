package de.viadee.oauth2.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import de.viadee.oauth2.apigateway.oauth2.OAuth2AccessTokenAuthentication;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * ZUUL filter to be executed before request is dispatched to its target microservice.
 * This filter removes any provided HTTP authorization header and replaces it with appropriate
 * authorization header containing the OAuth2 access token from current security context.
 */
@Component
public class AuthorizationHeaderFilter extends ZuulFilter {

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();

        // Always remove provided authorization header to not pass through any authorization provided from outside.
        ctx.getZuulRequestHeaders().remove(HttpHeaders.AUTHORIZATION);

        // Do provide OAuth2 access token using authorization header,
        // in case there is such token available in current security context.
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AccessTokenAuthentication) {
            final OAuth2AccessToken accessToken = ((OAuth2AccessTokenAuthentication) authentication).getoAuth2AccessToken();

            if (accessToken != null) {
                ctx.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, accessToken.getTokenType() + ' ' + accessToken.getValue());
            }
        }

        return null;
    }

    @Override
    public boolean shouldFilter() {
        // Always execute filter
        return true;
    }

    @Override
    public String filterType() {
        // Execute before dispatching to microservice
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // Priority within filter chain
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }
}
