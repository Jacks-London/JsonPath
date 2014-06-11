package com.jayway.jsonpath.internal;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ParseContext;
import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.spi.http.HttpProviderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static com.jayway.jsonpath.internal.Utils.notEmpty;
import static com.jayway.jsonpath.internal.Utils.notNull;

public class JsonReader implements ParseContext, ReadContext {

    private final Configuration configuration;
    private Object json;

    public JsonReader() {
        this(Configuration.defaultConfiguration());
    }

    public JsonReader(Configuration configuration) {
        notNull(configuration, "configuration can not be null");
        this.configuration = configuration;
    }

    //------------------------------------------------
    //
    // ParseContext impl
    //
    //------------------------------------------------
    @Override
    public ReadContext parse(Object json) {
        notNull(json, "json object can not be null");
        this.json = json;
        return this;
    }

    @Override
    public ReadContext parse(String json) {
        notEmpty(json, "json string can not be null or empty");
        this.json = configuration.getProvider().parse(json);
        return this;
    }

    @Override
    public ReadContext parse(InputStream json) {
        notNull(json, "json input stream can not be null");
        this.json = configuration.getProvider().parse(json);
        return this;
    }

    @Override
    public ReadContext parse(File json) throws IOException {
        notNull(json, "json file can not be null");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(json);
            parse(fis);
        } finally {
            Utils.closeQuietly(fis);
        }
        return this;
    }

    @Override
    public ReadContext parse(URL json) throws IOException {
        notNull(json, "json url can not be null");
        InputStream is = HttpProviderFactory.getProvider().get(json);
        return parse(is);
    }

    //------------------------------------------------
    //
    // ReadContext impl
    //
    //------------------------------------------------
    @Override
    public Object json() {
        return json;
    }

    @Override
    public <T> T read(String path, Filter... filters) {
        notEmpty(path, "path can not be null or empty");
        return read(JsonPath.compile(path, filters));
    }

    @Override
    public <T> T read(JsonPath path) {
        notNull(path, "path can not be null");
        return path.read(json, configuration);
    }

    @Override
    public List<String> readPathList(String path, Filter... filters) {
        notEmpty(path, "path can not be null or empty");
        return readPathList(JsonPath.compile(path, filters));
    }

    @Override
    public List<String> readPathList(JsonPath path) {
        notNull(path, "path can not be null");
        return path.readPathList(json, configuration);
    }
}
