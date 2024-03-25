package doris.dorisaccountservice.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException 
    {
        if (isByPassToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = jwtTokenProvider.resolveToken(request.getHeader("Authorization"));

        if (token == null || !jwtTokenProvider.validateToken(token))
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        request.setAttribute("userId", jwtTokenProvider.getUserIdFromJWT(token));
        filterChain.doFilter(request, response);
    }

    private boolean isByPassToken(@NonNull HttpServletRequest request) {
        @SuppressWarnings("null")
        final List<Pair<String, String>> bypassTokenUrls = Arrays.asList(
            Pair.of(String.format("%s/authentication/login", this.apiPrefix), "POST"),
            Pair.of(String.format("%s/authentication/register", this.apiPrefix), "POST")
        );

        return bypassTokenUrls.stream().anyMatch(
            pair -> pair.getFirst().equals(request.getRequestURI()) && pair.getSecond().equals(request.getMethod())
        );
    }
}
