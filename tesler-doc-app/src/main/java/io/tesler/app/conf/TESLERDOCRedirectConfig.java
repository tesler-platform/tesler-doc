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

import io.tesler.core.controller.http.StaticRedirectFilter;
import javax.servlet.Filter;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@EnableWebSecurity
@Order(1)
public class TESLERDOCRedirectConfig extends WebSecurityConfigurerAdapter {

	private final TESLERDOCConfiguration configuration;

	public TESLERDOCRedirectConfig(TESLERDOCConfiguration configuration) {
		super(true);
		this.configuration = configuration;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.requestMatchers()
				.antMatchers("/").and()
				.addFilterBefore(defaultUrlFilter(), ChannelProcessingFilter.class)
			.authorizeRequests()
				.antMatchers("/").permitAll();
		// @formatter:on
	}

	private Filter defaultUrlFilter() {
		return new StaticRedirectFilter(String.format("%s/", configuration.getUiPath()));
	}

}
