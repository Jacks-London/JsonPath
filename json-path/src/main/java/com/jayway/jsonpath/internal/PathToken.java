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
package com.jayway.jsonpath.internal;

import com.jayway.jsonpath.internal.filter.Filter;
import com.jayway.jsonpath.internal.filter.FilterFactory;
import com.jayway.jsonpath.spi.JsonProvider;

/**
 * @author Kalle Stenflo
 */
public class PathToken {

    private String fragment;

    public PathToken(String fragment) {
        this.fragment = fragment;
    }

    public Filter getFilter(){
        return FilterFactory.createFilter(fragment);
    }

    public Object filter(Object model, JsonProvider jsonProvider){
        return FilterFactory.createFilter(fragment).filter(model, jsonProvider);
    }

    public Object apply(Object model, JsonProvider jsonProvider){
        return FilterFactory.createFilter(fragment).getRef(model, jsonProvider);
    }

    public String getFragment() {
        return fragment;
    }
}
