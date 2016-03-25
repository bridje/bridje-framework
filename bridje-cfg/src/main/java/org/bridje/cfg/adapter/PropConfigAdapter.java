package org.bridje.cfg.adapter;

import org.bridje.cfg.ConfigAdapter;
import org.bridje.ioc.Component;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PropConfigAdapter implements ConfigAdapter
{
    private static final Logger LOG = Logger.getLogger(PropConfigAdapter.class.getName());

    @Override
    public String findFileName(String name, Class<?> cls)
    {
       //The adapter must attach the file extension to the end of the given name.
        return name + ".properties";
    }

    @Override
    public String findDefaultFileName(Class<?> cls)
    {
        return cls.getSimpleName().toLowerCase() + ".properties";
    }

    @Override
    public void write(Object newConfig, Writer writer) throws IOException
    {
        //TODO write properties
    }

    @Override
    public Object read(Class<?> cls, Reader reader) throws IOException
    {
        Object obj;
        try
        {
            //only if it has default constructor
            obj = cls.newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex)
        {
            return null;
        }

        Properties props = new Properties();
        props.load(reader);

        Enumeration enumProps = props.propertyNames();
        while (enumProps.hasMoreElements())
        {
            String key = (String) enumProps.nextElement();
            String value = props.getProperty(key);

            try
            {
                Field field = cls.getDeclaredField(key);
                if(null != field)
                {
                    setValue(obj, value, field);
                }
            }
            catch (NoSuchFieldException | SecurityException | IllegalAccessException ex)
            {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return obj;
    }

    private void setValue(Object obj, String value, Field field) throws IllegalAccessException
    {
        switch (field.getType().getName())
        {
            case "Boolean":
            case "boolean":
            {
                boolean isTrue = !(value.toLowerCase().equals("false") ||
                        "0".equals(value));
                field.setBoolean(obj, isTrue);
                break;
            }
            case "Long":
            case "long":
            {
                field.setLong(obj, Long.valueOf(value));
                break;
            }
            case "Float":
            case "float":
            {
                field.setFloat(obj, Long.valueOf(value));
                break;
            }
            case "Integer":
            case "int":
            {
                field.setInt(obj, Integer.valueOf(value));
                break;
            }
            case "Double":
            case "double":
            {
                field.setDouble(obj, Double.valueOf(value));
                break;
            }
            case "Short":
            {
                field.setShort(obj, Short.valueOf(value));
                break;
            }
            case "String":
            {
                field.set(obj, value);
                break;
            }
            default:
            {
                throw new IllegalStateException("Cannot find a valid data type for the field " + field.getName());
            }
        }
    }
}
