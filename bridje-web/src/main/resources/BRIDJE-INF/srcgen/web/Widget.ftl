<#ftl encoding="UTF-8">

package ${widget.package};

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlValue;
import org.bridje.web.view.widgets.*;

<#if widget.rootElement != "">
@XmlRootElement( name = "${widget.rootElement}" )
</#if>
@XmlAccessorType(XmlAccessType.FIELD)
public class ${widget.name} extends ${widget.base}
{
    <#list widget.fields as f>
    <#if f.fieldType == "attribute">
    @XmlAttribute
    <#elseif f.fieldType == "value">
    @XmlValue
    <#elseif f.fieldType == "childrens">
    <#if f.wrapper != "">
    @XmlElementWrapper( name = "${f.wrapper}" )
    </#if>
    @XmlElements(
    {
        <#list f.childrens?keys as k>
        @XmlElement( name = "${k}", type = ${f.childrens[k]}.class ),
        </#list>
    })
    </#if>
    private ${f.javaType} ${f.name};

    </#list>
    <#list widget.fields as f>
    <#if f.javaType == "UIExpression">
    public ${f.javaType} get${f.name?cap_first}Expression()
    {
        return ${f.name};
    }

    public ${f.resultType} get${f.name?cap_first}()
    {
        return get(${f.name}, ${f.resultType}.class, null);
    }
    <#elseif f.javaType == "UIInputExpression">
    public ${f.javaType} get${f.name?cap_first}Expression()
    {
        return ${f.name};
    }

    public ${f.resultType} get${f.name?cap_first}()
    {
        return get(${f.name}, ${f.resultType}.class, null);
    }
    <#elseif f.javaType == "UIEvent">
    public ${f.javaType} get${f.name?cap_first}()
    {
        return ${f.name};
    }
    <#else>
    public ${f.javaType} get${f.name?cap_first}()
    {
        return ${f.name};
    }
    </#if>

    </#list>
}