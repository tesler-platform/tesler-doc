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

import com.google.common.io.Files;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import io.tesler.config.SitemapConfiguration;
import io.tesler.core.config.APIConfig;
import io.tesler.core.service.impl.UIServiceImpl.UICache;
import java.io.File;
import java.net.MalformedURLException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Import(APIConfig.class)
@ComponentScan({"io.tesler.sitemap"})
@Slf4j
public class TESLERDOCSitemapConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private SitemapConfiguration config;

	@Autowired
	private UICache uiCache;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dir = generateSitemapInTempDir(config, uiCache);
		registry.addResourceHandler("/static/**").addResourceLocations(dir);
		super.addResourceHandlers(registry);
	}

	@SneakyThrows
	private String generateSitemapInTempDir(SitemapConfiguration config, UICache uiCache) {
		final String tempDirPath;
		File tempDir = Files.createTempDir();
		tempDirPath = tempDir.getAbsolutePath();
		WebSitemapGenerator wsg = new WebSitemapGenerator(config.getBaseUrl(), tempDir);
		uiCache.getViews().values().forEach(v -> {
			try {
				wsg.addUrl(new WebSitemapUrl.Options(config.getBaseUrl() + v.getUrl()).changeFreq(ChangeFreq.WEEKLY).build());
			} catch (MalformedURLException e) {
				log.error(e.getMessage());
			}
		});
		wsg.write();
		return tempDirPath;
	}

}
