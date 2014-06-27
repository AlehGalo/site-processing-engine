/**
 * 
 */
package com.jdev.ui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.PropertiesPersister;
import org.yaml.snakeyaml.Dumper;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.ScalarStyle;
import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;

/**
 * @author Aleh
 * 
 */
@Deprecated
public class YamlPropertiesPersister implements PropertiesPersister {

    @Override
    public void load(final Properties props, final InputStream is) throws IOException {
        load(props, new InputStreamReader(is));
    }

    /**
     * We want to traverse map representing Yaml object and each time we find
     * String=String pair we want to save it as Property. As we are going deeper
     * into map we generate compound key as path-like String
     * 
     * @param props
     * @param reader
     * @throws IOException
     * @see org.springframework.util.PropertiesPersister#load(java.util.Properties,
     *      java.io.Reader)
     */
    @Override
    public void load(final Properties props, final Reader reader) throws IOException {
        Yaml yaml = instanceOfYaml();
        Map<String, Object> map = (Map<String, Object>) yaml.load(reader);
        // now we can populate supplied props
        assignProperties(props, map, null);
    }

    /**
     * @param props
     * @param map
     */
    public void assignProperties(final Properties props, final Map<String, Object> map,
            final String path) {
        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.isNotEmpty(path)) {
                key = path + "." + key;
            }
            Object val = entry.getValue();
            if (val instanceof String) {
                // see if we need to create a compound key
                props.put(key, val);
            } else if (val instanceof Map) {
                assignProperties(props, (Map<String, Object>) val, key);
            }
        }
    }

    @Override
    public void store(final Properties props, final OutputStream os, final String header)
            throws IOException {
        throw new NotImplementedException("Current implementation is a read-only");
    }

    @Override
    public void store(final Properties props, final Writer writer, final String header)
            throws IOException {
        throw new NotImplementedException("Current implementation is a read-only");
    }

    @Override
    public void loadFromXml(final Properties props, final InputStream is) throws IOException {
        throw new NotImplementedException(
                "Use DefaultPropertiesPersister if you want to read/write XML");
    }

    @Override
    public void storeToXml(final Properties props, final OutputStream os, final String header)
            throws IOException {
        throw new NotImplementedException(
                "Use DefaultPropertiesPersister if you want to load/store to XML");
    }

    @Override
    public void storeToXml(final Properties props, final OutputStream os, final String header,
            final String encoding) throws IOException {
        throw new NotImplementedException(
                "Use DefaultPropertiesPersister if you want to read/write XML");
    }

    static Yaml instanceOfYaml() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setDefaultScalarStyle(ScalarStyle.DOUBLE_QUOTED);
        @SuppressWarnings("deprecation")
        final Yaml yaml = new Yaml(new Loader(), new Dumper(options), new Resolver() {
            /**
             * @see org.yaml.snakeyaml.resolver.Resolver#addImplicitResolvers()
             */
            @Override
            protected void addImplicitResolvers() {
                addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO");
                addImplicitResolver(Tag.MERGE, MERGE, "<");
                addImplicitResolver(Tag.NULL, NULL, "~nN\0");
                addImplicitResolver(Tag.NULL, EMPTY, null);
                addImplicitResolver(Tag.TIMESTAMP, TIMESTAMP, "0123456789");
                addImplicitResolver(Tag.VALUE, VALUE, "=");
            }
        });
        return yaml;
    }
}