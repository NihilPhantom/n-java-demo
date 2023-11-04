package com.nihil.oauth2.config;

import com.nihil.oauth2.JOPO.IDHolderUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
public final class FederatedIdentityIdTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

	@Override
	public void customize(JwtEncodingContext context) {

		if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())
		|| OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
			UsernamePasswordAuthenticationToken authentication = context.getPrincipal();
			IDHolderUserDetails user = (IDHolderUserDetails) authentication.getPrincipal();
			context.getClaims().claim("userId", user.getId());
		}
	}
}