/*
 * Copyright 2011 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jayway.jsonpath.spi.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public abstract class HttpProviderFactory {

    public static HttpProviderFactory factory = new HttpProviderFactory() {
        @Override
        protected HttpProvider create() {
            return new HttpProvider() {
                @Override
                public InputStream get(URL url) throws IOException {
                    URLConnection connection = url.openConnection();
                    connection.setRequestProperty("Accept", "application/json");
                    return connection.getInputStream();
                }
            };
        }
    };

    public static HttpProvider getProvider() {
        return factory.create();
    }


    protected abstract HttpProvider create();
}
