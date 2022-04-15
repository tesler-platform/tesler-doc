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

package io.tesler.conf;

import io.minio.MinioClient;
import io.tesler.core.file.service.TeslerFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeslerFileMinioConfig {

	@Bean
	public TeslerFileService fileService(
			MinioClient minioClient,
			@Value("${minio.bucket.name}") String defaultBucketName) {
		return new TeslerFileServiceMinio(minioClient, defaultBucketName);
	}

	@Bean
	public MinioClient minioClient(
			@Value("${minio.access.name}") String accessKey,
			@Value("${minio.access.secret}") String accessSecret,
			@Value("${minio.url}") String minioUrl) {
		return MinioClient.builder()
				.endpoint(minioUrl)
				.credentials(accessKey, accessSecret)
				.build();
	}

}
