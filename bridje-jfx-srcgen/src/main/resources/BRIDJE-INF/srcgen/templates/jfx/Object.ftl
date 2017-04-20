<#include "./ObjectUtils.ftl" />

package ${object.package};

<#list model.includes![] as inc>
import ${inc.fullName};
</#list>
<#list object.includes![] as inc>
import ${inc.fullName};
</#list>
import javafx.beans.property.*;
import java.util.Objects;
import javafx.collections.ObservableList;
import javax.annotation.Generated;

/**
 * ${object.description!}
 */
@Generated(value = "org.bridje.jfx.srcgen.JFxSourceGenerator", date = "${.now?string("yyyy-MM-dd")}", comments = "Generated by Bridje JavaFx API")
public class ${object.name}
{
    <#list object.allProperties as property>
    private final ${property.propertyDec} ${property.name}Property = new ${property.propertyDimDec}();

    </#list>

    <#list object.allProperties as property>
    /**
     * Gets the property object for the ${property.name} field.
     * ${property.description!}
     *
     * @return The property objecto for the ${property.name} field
     */
    public ${property.propertyDec} ${property.name}Property()
    {
        return this.${property.name}Property;
    }

    /**
     * Gets the value of the ${property.name} field.
     * ${property.description!}
     *
     * @return The value of the ${property.name} field
     */
    public ${property.javaType} get${property.name?cap_first}()
    {
        return this.${property.name}Property.get();
    }

    /**
     * Sets the value of the ${property.name} field.
     * ${property.description!}
     *
     * @param ${property.name} The value of the ${property.name} field.
     */
    public void set${property.name?cap_first}(${property.javaType} ${property.name})
    {
        this.${property.name}Property.set(${property.name});
    }

    </#list>
    @Override
    public int hashCode()
    {
        if(get${object.keyProperty.name?cap_first}() == null)
        {
            return super.hashCode();
        }
        return get${object.keyProperty.name?cap_first}().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final ${object.name} other = (${object.name}) obj;
        return Objects.equals(this.get${object.keyProperty.name?cap_first}(), other.get${object.keyProperty.name?cap_first}());
    }

    <#if object.toStringProperty??>
    @Override
    public String toString()
    {
        <#if object.toStringProperty.type != "String">
        if(get${object.toStringProperty.name?cap_first}() != null) return get${object.toStringProperty.name?cap_first}().toString();
        return null;
        <#else>
        return get${object.toStringProperty.name?cap_first}();
        </#if>
    }

    </#if>
}