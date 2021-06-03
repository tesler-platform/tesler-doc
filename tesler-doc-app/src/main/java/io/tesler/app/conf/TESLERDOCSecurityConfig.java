/*-
 * #%L
 * TESLERDOC - APP
 * %%
 * Copyright (C) 2020 Tesler Contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package io.tesler.app.conf;

import io.tesler.api.service.session.TeslerAuthenticationService;
import io.tesler.core.util.session.CustomBasicAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.firewall.StrictHttpFirewall;


@RequiredArgsConstructor
@EnableWebSecurity
@Order(100)
public class TESLERDOCSecurityConfig extends WebSecurityConfigurerAdapter {

	private final TeslerAuthenticationService teslerAuthenticationService;

	private final TESLERDOCConfiguration configuration;

	private final LogoutSuccessHandler logoutSuccessHandler;

	@Bean
	public StrictHttpFirewall httpFirewall() {
		StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
		strictHttpFirewall.setAllowUrlEncodedPercent(true);
		return strictHttpFirewall;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf().disable();
		http.cors();
		http.authorizeRequests()
				.antMatchers("/rest/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/favicon.ico").permitAll()
				.antMatchers("/sitemap.xml").permitAll()
				.antMatchers("/robots.txt").permitAll()
				.antMatchers(String.format("%s/**", configuration.getUiPath())).permitAll()
				.antMatchers("/api/v1/files/**").permitAll()
				.antMatchers("/api/v1/bc-registry/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/**").authenticated();
		http.logout()
				.invalidateHttpSession(true)
				.logoutUrl("/api/v1/logout")
				.logoutSuccessHandler(logoutSuccessHandler);
		http.headers().frameOptions().sameOrigin();
		http.httpBasic().authenticationEntryPoint(customBasicAuthenticationEntryPoint());
		// @formatter:on
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(teslerAuthenticationService);
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint() {
		return new CustomBasicAuthenticationEntryPoint("CustomRealm");
	}

	@Bean
	@ConditionalOnMissingBean(LogoutSuccessHandler.class)
	public static LogoutSuccessHandler logoutSuccessHandler() {
		return new HttpStatusReturningLogoutSuccessHandler();
	}


}
